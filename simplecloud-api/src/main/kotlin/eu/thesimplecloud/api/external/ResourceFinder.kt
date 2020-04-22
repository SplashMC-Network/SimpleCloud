package eu.thesimplecloud.api.external

import sun.misc.URLClassPath
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.lang.reflect.Field
import java.net.MalformedURLException
import java.net.URL
import java.net.URLClassLoader
import java.util.*


class ResourceFinder {


    companion object {

        @Throws(MalformedURLException::class)
        fun findResource(file: File, pathToResource: String): InputStream? {
            val newClassLoader = URLClassLoader(arrayOf(file.toURI().toURL()))
            return newClassLoader.getResourceAsStream(pathToResource)
        }

        fun addToClassLoader(url: URL, urlClassLoader: URLClassLoader = getThreadClassLoader()) {
            val method = URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java)
            method.isAccessible = true
            if (!urlClassLoader.urLs.contains(url))
                method.invoke(urlClassLoader, url)
        }

        fun addToClassLoader(file: File, urlClassLoader: URLClassLoader = getThreadClassLoader()) {
            addToClassLoader(file.toURI().toURL(), urlClassLoader)
        }

        fun removeFromClassLoader(file: File, urlClassLoader: URLClassLoader = getThreadClassLoader()) {
            val url = file.toURI().toURL()
            val urlClass: Class<*> = URLClassLoader::class.java
            val ucpField: Field = urlClass.getDeclaredField("ucp")
            ucpField.isAccessible = true
            val ucp: URLClassPath = ucpField.get(urlClassLoader) as URLClassPath
            val ucpClass: Class<*> = URLClassPath::class.java
            val urlsField: Field = ucpClass.getDeclaredField("urls")
            urlsField.isAccessible = true
            val urls = urlsField.get(ucp) as Stack<*>
            urls.remove(url)
        }

        private fun getThreadClassLoader() = Thread.currentThread().contextClassLoader as URLClassLoader



        @Throws(FileNotFoundException::class)
        fun getFileFromClass(clazz: Class<*>): File {
            val resource = clazz.getResource("/${clazz.name.replace('.', '/')}.class").toString()
            if (!resource.contains("!")) throw FileNotFoundException("Unable to find file by class ${clazz.name}")
            val path = try {
                val split = resource.split(":")
                split.drop(2).joinToString(":").split("!")[0]
            } catch (ex: Exception) {
                throw FileNotFoundException("Unable to find file by class ${clazz.name}")
            }
            return File(path)
        }

        fun createClassLoaderByFiles(vararg files: File): URLClassLoader {
            return URLClassLoader(files.map { it.toURI().toURL() }.toTypedArray())
        }

    }
}