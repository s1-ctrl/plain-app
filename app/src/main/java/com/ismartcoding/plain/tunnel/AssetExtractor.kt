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

        // If the binary already exists, make sure it is executable
        if (binaryFile.exists()) {
            if (binaryFile.canExecute()) {
                LogCat.d("Cloudflared binary already extracted and executable")
                return binaryFile
            }

            if (binaryFile.setExecutable(true, false) || binaryFile.canExecute()) {
                LogCat.d("Cloudflared binary marked executable after existence check")
                return binaryFile
            }

            try {
                Runtime.getRuntime().exec(arrayOf("chmod", "755", binaryFile.absolutePath)).waitFor()
            } catch (e: IOException) {
                LogCat.e("Failed to chmod existing cloudflared binary: ${e.message}")
            }

            if (binaryFile.canExecute()) {
                LogCat.d("Cloudflared binary executable after chmod")
                return binaryFile
            }
        }

        return try {
            // Copy from assets to files directory
            context.assets.open(ASSET_NAME).use { input ->
                FileOutputStream(binaryFile).use { output ->
                    input.copyTo(output)
                }
            }

            // Make executable
            if (!binaryFile.setExecutable(true, false)) {
                Runtime.getRuntime().exec(arrayOf("chmod", "755", binaryFile.absolutePath)).waitFor()
            }

            if (!binaryFile.canExecute()) {
                throw IOException("Failed to set executable permission on cloudflared")
            }

            LogCat.d("Cloudflared binary extracted and made executable: ${binaryFile.absolutePath}")
            binaryFile
        } catch (e: IOException) {
            LogCat.e("Failed to extract cloudflared binary: ${e.message}")
            null
        }
    }
}