import sys.FileSystem;
import sys.io.File;

/** Packages the project. **/
function main() {
	for (script in ["Clean", "Build", "Version"]) Sys.command('lix $script');
	FileSystem.createDirectory("lib/META-INF");
	File.copy("LICENSE.md", "lib/META-INF/LICENSE.md");
	FileSystem.createDirectory("bin");
	Sys.command('jar --create --file=bin/${Tools.javaPackage}.jar --manifest=etc/manifest.properties -C lib .');
}
