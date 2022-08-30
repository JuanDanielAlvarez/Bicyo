package alvarez.juan.cazarpatos.storage

import android.app.Activity
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class ExternalFileManager(val actividad: Activity) : FileHandler {
    fun checkPermissions(): Boolean {
        var isAvailable = false
        var isWritable = false
        var isReadable = false
        val state = Environment.getExternalStorageState()

        if (Environment.MEDIA_MOUNTED == state) {
            // Operation possible - Read and Write
            isAvailable = true
            isWritable = true
            isReadable = true
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            // Operation possible - Read Only
            isAvailable = true
            isWritable = false
            isReadable = true
        } else {
            // SD card not available
            isAvailable = false
            isWritable = false
            isReadable = false
        }
        return isAvailable and isWritable and isReadable
    }

    override fun SaveInformation(datosAGrabar: Pair<String, String>) {
        if (!checkPermissions()) {
            return
        }
        val filename = "SampleFile.txt"
        val filepath = actividad.getExternalFilesDir(null)
        var myExternalFile = File(filepath, filename)
        val texto = "${datosAGrabar.first}\n${datosAGrabar.second}"

        try {
            val fos = FileOutputStream(myExternalFile)
            fos.write(texto.toByteArray())
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun ReadInformation(): Pair<String, String> {
        if (!checkPermissions()) {
            return ("" to "")
        }
        val filename = "SampleFile.txt"
        val filepath = actividad.getExternalFilesDir(null)
        var file = File(filepath, filename)
        if (!file.exists()) {
            return ("" to "")
        }
        val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
        val textArray = inputAsString.split("\n")
        val email = textArray[0]
        val clave = textArray[1]
        return (email to clave)
    }
}