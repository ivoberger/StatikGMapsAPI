package com.ivoberger.statikgmapsapi.core

/**
 * Represents an URL for Google's Static Map API.
 * All parameters follow Google's API specs: https://developers.google.com/maps/documentation/maps-static/dev-guide
 * @param apiKey: Google Maps API key with access to the static maps API
 * @property baseUrl: base url of the API just here so it can be set in case Google changes it
 * @property setUp: Set up function to assign all necessary parameters. Is called at the beginning of [toString]
 * @constructor creates a basic url which still required size and map center to be set
 */
class StatikGMapsUrl(
    private val apiKey: String,
    var baseUrl: String = "maps.googleapis.com/maps/api/staticmap",
    val setUp: StatikGMapsUrl.() -> Unit
) {

    /**
     * Specifies if a secure https connection is used, defaults to true
     */
    var https: Boolean = true
    /**
     *  (optional) defines the language to use for display of labels on map tiles.
     *  Note that this parameter is only supported for some country tiles; if the
     *  specific language requested is not supported for the tile set,
     *  then the default language for that tileset will be used.
     */
    var language: String? = null
    /**
     * (optional) defines the appropriate borders to display, based on geo-political sensitivities.
     * Accepts a region code specified as a two-character ccTLD ('top-level domain') value.
     */
    var region: String? = null
    var style: String? = null
    /**
     * Specifies if the api key belongs to a Google Maps APIs Premium Plan, thus allowing larger image sizes
     */
    var premiumPlan: Boolean = false
    /**
     * Specifies if the size should be automatically scaled down while preserving the aspect ration to fit the API restrictions
     * For example, a size of 1000x500 would be scaled down to 640x320. Takes [premiumPlan] into account.
     */
    var downscale = true

    /**
     * (optional) defines the type of map to construct.
     * For more information, see [MapType]
     */
    var mapType: MapType? = null
    /**
     * (optional) defines the format of the resulting image.
     * By default, the Maps Static API creates PNG images.
     * For more information, see [ImageFormat].
     */
    var imageFormat: ImageFormat? = null

    /*** image size ***/
    /**
     *  (required) defines the rectangular dimensions of the map image.
     *  This parameter takes a pair of the form {horizontal_value} to {vertical_value}.
     *  For example, 500 to 400 defines a map 500 pixels wide by 400 pixels high.
     *  Maps smaller than 180 pixels in width will display a reduced-size Google logo.
     *  This parameter is affected by the [scale] parameter;
     *  the final output size is the product of the size and scale values.
     */
    var size: Pair<Int, Int>? = null
    /**
     * (optional) affects the number of pixels that are returned.
     * scale=2 returns twice as many pixels as scale=1 while retaining
     * the same coverage area and level of detail (i.e. the contents of the map don't change).
     * This is useful when developing for high-resolution displays, or when generating a map for printing.
     * The default value is 1. Accepted values are 2 and 4.
     * See https://developers.google.com/maps/documentation/maps-static/dev-guide?#scale_values for more information.
     */
    var scale: Int = 1

    /**
     * (required if markers, path or visible not present)
     * defines the center of the map, equidistant from all edges of the map.
     * This parameter takes a location as either latitude to longitude pair
     * For more information, see TODO Create location class to accommodate both coordinates and addresses.
     */
    var center: Pair<Double, Double>? = null
    /**
     * (required if markers, path or visible not present)
     * defines the zoom level of the map, which determines the magnification level of the map.
     * This parameter takes a numerical value corresponding to the zoom level of the region desired.
     * For more information, see https://developers.google.com/maps/documentation/maps-static/dev-guide#Zoomlevels.
     */
    var zoom: Int? = null

    var markers: List<Pair<Double, Double>> = listOf()
    var path: List<Pair<Double, Double>> = listOf()
    var visible: List<Pair<Double, Double>> = listOf()
    var encodePath: Boolean = false

    private val maxSizeStandard = 640
    private val maxSizePremium = 2048

    override fun toString(): String {
        setUp()
        require(size != null) { "Size parameter is required" }
        require(
            downscale
                    || (size!!.first <= maxSizeStandard && size!!.second <= maxSizeStandard)
                    || (premiumPlan && size!!.first * scale <= maxSizePremium && size!!.second * scale <= maxSizePremium)
        ) { "Allow downscaling or follow the size limitation as specified by Google (https://developers.google.com/maps/documentation/maps-static/dev-guide#Imagesizes)" }
        require(scale in 1..4 && scale != 3) { "Scale must be one of 1, 2 or 4" }
        require(
            (center != null && zoom != null)
                    || markers.isNotEmpty()
                    || path.isNotEmpty()
                    || visible.isNotEmpty()
        ) { "Values for center and zoom or markers, path or visible are required" }
        require(zoom == null || zoom!! in 0..20) { "zoom values are required to be >= 0 and <= 20" }

        val params: MutableList<Pair<String, Any?>> = mutableListOf()

        params += "key" to apiKey


        if (downscale
            && ((!premiumPlan && (size!!.first > maxSizeStandard || size!!.second > maxSizeStandard))
                    || (premiumPlan && (size!!.first * scale > maxSizePremium || size!!.second * scale > maxSizePremium)))
        ) downscale()
        params += "size" to "${size!!.first}x${size!!.second}"

        if (scale != 1) params += "scale" to scale
        params += "language" to language
        params += "region" to region
        params += "style" to style

        params += "maptype" to mapType
        params += "format" to imageFormat

        if (center != null && zoom != null) {
            params += "center" to center
            params += "zoom" to zoom
        }

        if (markers.isNotEmpty()) params += "markers" to markers.toUrlParam()
        if (path.isNotEmpty()) if (encodePath) params += "path" to "enc:${path.encode()}" else params += "path" to path.toUrlParam()
        if (visible.isNotEmpty()) params += "visible" to visible.toUrlParam()

        var url = "${if (https) "https" else "http"}://$baseUrl"
        url = "$url${params.filter { it.second != null }.joinToString(
            "&",
            "?"
        ) { paramPair ->
            "${paramPair.first}=${
            if (paramPair.second is Pair<*, *>) {
                val param = (paramPair.second as Pair<*, *>)
                "${param.first},${param.second}"
            } else paramPair.second}"
        }}"

        return url
    }

    private fun downscale() {
        val maxAllowedSize: Int = if (premiumPlan) maxSizePremium else maxSizeStandard
        val maxActualSize = size!!.toList().max()!!
        val scaleFactor: Float = maxAllowedSize / maxActualSize.toFloat()
        size = (size!!.first * scaleFactor).toInt() to (size!!.second * scaleFactor).toInt()
    }

    private fun List<Pair<Double, Double>>.toUrlParam(): String = fold("") { param, pair ->
        "${if (param.isNotBlank()) "$param|" else ""}${pair.first},${pair.second}"
    }
}
