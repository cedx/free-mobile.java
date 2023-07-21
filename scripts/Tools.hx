import sys.FileSystem;
import sys.io.File;
using StringTools;
using haxe.io.Path;

/** The root package of the Java library. **/
final javaPackage = "io.belin.free_mobile";

/** Recursively deletes all files in the specified `directory`. **/
function cleanDirectory(directory: String) for (entry in FileSystem.readDirectory(directory).filter(entry -> entry != ".gitkeep")) {
	final path = Path.join([directory, entry]);
	FileSystem.isDirectory(path) ? removeDirectory(path) : FileSystem.deleteFile(path);
}

/** Recursively deletes the specified `directory`. **/
function removeDirectory(directory: String) {
	cleanDirectory(directory);
	FileSystem.deleteDirectory(directory);
}

/** Replaces in the specified `file` the substring which the `pattern` matches with the given `replacement`. **/
function replaceInFile(file: String, pattern: EReg, replacement: String)
	File.saveContent(file, pattern.replace(File.getContent(file), replacement));

/** Sets the Java class path. **/
function setClassPath(path = "bin") {
	final paths = (FileSystem.exists(path) ? [path] : []).concat([File.getContent(".classpath").rtrim()]);
	Sys.putEnv("CLASSPATH", paths.join(Sys.systemName() == "Windows" ? ";" : ":"));
}
