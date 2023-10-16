package watcher

import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries

fun main(args: Array<String>) {
    if (args.size != 2) throw IllegalArgumentException("args nix gut")
    val dirPath = Path.of(args[0])
    if (!dirPath.isDirectory()) throw IllegalArgumentException("args[0] nix directory")
    val delay = args[1].toLongOrNull() ?: throw IllegalArgumentException("args[1] nix zahl")

    val files = dirPath.listDirectoryEntries().map { it.toFile() }
    files.map { Thread(FileWatcher(it, delay)) }
        .forEach { it.start() }
}