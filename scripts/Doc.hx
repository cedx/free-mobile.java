import sys.FileSystem;
import sys.io.File;
using Lambda;

/** Builds the documentation. **/
function main() {
	["CHANGELOG.md", "LICENSE.md"].iter(file -> File.copy(file, 'docs/${file.toLowerCase()}'));
	if (FileSystem.exists("docs/api")) Tools.removeDirectory("docs/api");
	Sys.command("javadoc -d docs/api --source-path=src io.belin.free_mobile");
	File.copy("docs/favicon.ico", "docs/api/favicon.ico");
}
