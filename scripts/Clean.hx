import sys.FileSystem;

/** Deletes all generated files. **/
function main() {
	final jar = 'bin/${Tools.javaPackage}.jar';
	if (FileSystem.exists(jar)) FileSystem.deleteFile(jar);
	if (FileSystem.exists("lib")) Tools.removeDirectory("lib");
	Tools.cleanDirectory("var");
}
