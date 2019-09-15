package com.ivoberger.statikgmapsapi.core

/**
 * Images may be returned in several common web graphics formats: GIF, JPEG and PNG.
 * Available options: png, png32, gif, jpg, jpg-baseline
 * For more detailed information see the individual formats or @see <a href="https://developers.google.com/maps/documentation/maps-static/dev-guide#ImageFormats">the official docs</a>
 */
enum class ImageFormat {
    /**
     * standard 8-bit PNG format
     */
    png,
    /**
     * 32-bit PNG format
     */
    png32,
    /**
     * GIF formart
     */
    gif,
    /**
     * JPEG compression format
     */
    jpg,
    /**
     * non-progressive JPEG compression format
     */
    jpg_baseline
}
