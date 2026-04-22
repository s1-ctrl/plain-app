package com.ismartcoding.plain.tunnel

import android.content.Context
import com.ismartcoding.lib.logcat.LogCat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object AssetExtractor {
    private const val ASSET_NAME = "cloudflared"
    private const val BINARY_NAME = "cloudflared"

    fun extractBinary(context: Context): File? {
        val filesDir = context.filesDir
        val binaryFile = File(filesDir, BINARY_NAME)

        // Check if binary already exists and is executable
        if (binaryFile.exists() && binaryFile.canExecute()) {
            LogCat.d("Cloudflared binary already extracted and executable")
            return binaryFile
        }

        return try {
            // Copy from assets to files directory
            context.assets.open(ASSET_NAME).use { input ->
                FileOutputStream(binaryFile).use { output ->
                    input.copyTo(output)
                }
            }

            // Make executable
            binaryFile.setExecutable(true)

            LogCat.d("Cloudflared binary extracted and made executable: ${binaryFile.absolutePath}")
            binaryFile
        } catch (e: IOException) {
            LogCat.e("Failed to extract cloudflared binary: ${e.message}")
            null
        }
    }
}