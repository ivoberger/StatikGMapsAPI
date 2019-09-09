com.ivoberger:StatikGMapsAPI# StatikGMapsAPI
Kotlin library to generate URLs for Google's Static Maps API.
This library allow to set all paramters like markers in a type-safe way and generates a valid static maps url.
The implementation follows [Google's officical specifications][google-api-specs] and checks that all requirements are met before returning a url.

## Features

* [x] Ensure that Google's requirements for a valid url are met (except URL length)
* [x] Support all possbile [parameters][google-api-params]
* [x] Automatic check that map size is within [bounds][google-api-imagesize] (supports [premium plan][google-maps-premium])
* [x] Typesafe paremters: center, markers, path, viewport, zoom level, scale, map type, image format
* [x] Encode path using [Google's algorithm][google-enc-algo]
* [ ] Automatically downscale image size preserving the ratio (default API behavior is to cut it off)
* [ ] [Specify locations][google-api-locations] using city names and other addresses
* [ ] Style markers and paths
* [ ] Set a [custom map stype][google-maps-styling]
* [ ] Automatic path compression if the [URL length limit][google-api-url] is surpassed

## Sample usage
```kotlin
// supply your API key followed by a lambda to set the options
val staticMap = StatikGMapsUrl("yourApiKey") {
  size = 500 to 250
  center = .0 to .0
  markers = listOf(51.507222 to -0.1275, 52.5069704 to 13.2846501, 48.8589507 to 2.2770204)
  zoom = 4
  scale = 2
}
// get the url, this is where all specification checks are performed
val mapUrl = staticMap.toString()
// Result: https://maps.googleapis.com/maps/api/staticmap?key=yourApiKey&size=500x250&scale=2&center=0.0,0.0&zoom=4&markers=51.507222,-0.1275|52.5069704,13.2846501|48.8589507,2.2770204
```

## Download [![](https://jitpack.io/v/com.ivoberger/StatikGMapsAPI.svg)](https://jitpack.io/#com.ivoberger/StatikGMapsAPI)

#### Step 1: Add the JitPack repository to your build file 
<details><summary>Groovy</summary>

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

</details>
<details><summary>Kotlin</summary>

```kotlin
allprojects {
  repositories {
    ...
    maven { url =  "https://jitpack.io" }
  }
}
```

</details>

#### Step 2: Add dependency

<details><summary>Groovy</summary>

```groovy
dependencies {
  implementation 'com.ivoberger:StatikGMapsAPI:latestVersion'
}
```

</details>
<details><summary>Kotlin</summary>

```kotlin
dependencies {
  implementation("com.ivoberger:StatikGMapsAPI:latestVersion")
}
```

</details>





[google-api-specs]: https://developers.google.com/maps/documentation/maps-static/dev-guide
[google-api-params]: https://developers.google.com/maps/documentation/maps-static/dev-guide#URL_Parameters
[google-api-locations]: https://developers.google.com/maps/documentation/maps-static/dev-guide#Locations
[google-api-url]: https://developers.google.com/maps/documentation/maps-static/dev-guide#url-size-restriction
[google-maps-styling]: https://developers.google.com/maps/documentation/maps-static/styling
[google-api-imagesize]: https://developers.google.com/maps/documentation/maps-static/dev-guide#Imagesizes
[google-enc-algo]: https://developers.google.com/maps/documentation/utilities/polylinealgorithm
[google-maps-premium]: https://developers.google.com/maps/premium/
