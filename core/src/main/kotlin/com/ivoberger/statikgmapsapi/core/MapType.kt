package com.ivoberger.statikgmapsapi.core

/**
 * The Maps Static API creates maps in several formats, available are roadmap, satellite, terrain and hybrid.
 * For more detailed information see the individual types or @see <a href="https://developers.google.com/maps/documentation/maps-static/dev-guide#MapType">the official docs</a>
 */
enum class MapType {
    /**
     * standard roadmap image, as is normally shown on the Google Maps website
     */
    roadmap,
    /**
     * satellite image.
     */
    satellite,
    /**
     * physical relief map image, showing terrain and vegetation.
     */
    terrain,
    /**
     * hybrid of the satellite and roadmap image, showing a transparent layer of major streets and place names on the satellite image.
     */
    hybrid
}
