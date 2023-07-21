import sys.FileSystem;

/** Deletes all generated files. **/
function main() {
	if (FileSystem.exists("bin")) Tools.removeDirectory("bin");
	Tools.cleanDirectory("var");
}
