import sys.FileSystem;
import sys.io.File;

/** Packages the project. **/
function main() {
	for (script in ["Clean", "Build", "Version"]) Sys.command('lix $script');
	FileSystem.createDirectory("bin/META-INF");
	File.copy("LICENSE.md", "bin/META-INF/LICENSE.md");
	Sys.command('jar --create --file=bin/${Tools.javaPackage}.jar --manifest=etc/manifest.properties -C bin .');
}
