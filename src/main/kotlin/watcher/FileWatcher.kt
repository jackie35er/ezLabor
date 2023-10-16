package watcher

import java.io.File

class FileWatcher(
    private val file: File,
    private val intervalDelayTime: Long,
    private val modifiedFunction: (File) -> Unit = { println("File changed: ${it.absolutePath}")},
    private val removedFunction: (File) -> Unit = { println("File gone, this is so sad: ${it.absolutePath}")}
): Runnable {
    private var lastModified = file.lastModified();

    override fun run() {
        while (file.exists()) {
            if(file.lastModified() != lastModified) {
                modifiedFunction.invoke(file)
                lastModified = file.lastModified()
            }
            Thread.sleep(intervalDelayTime)
        }
        removedFunction.invoke(file)
    }
}