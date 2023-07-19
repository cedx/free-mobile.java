import haxe.xml.Access;
import sys.io.File;

/** Updates the version number in the sources. **/
function main() {
	final version = new Access(Xml.parse(File.getContent("ivy.xml")).firstElement()).node.info.att.revision;
	Tools.replaceInFile("README.md", ~/project\/v\d+(\.\d+){2}/, 'project/v$version');
	Tools.replaceInFile("etc/manifest.properties", ~/Bundle-Version: \d+(\.\d+){2}/, 'Bundle-Version: $version');
}
