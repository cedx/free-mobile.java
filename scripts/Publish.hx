import haxe.xml.Access;
import sys.io.File;

/** Publishes the package. **/
function main() {
	final version = new Access(Xml.parse(File.getContent("ivy.xml")).firstElement()).node.info.att.revision;
	Sys.command("lix Dist");
	for (action in ["tag", "push origin"]) Sys.command('git $action v$version');
}
