/** Installs the project dependencies. **/
function main() {
	Sys.command("lix download");
	Sys.command("ivy", ["-cache", "lib", "-cachepath", ".classpath"]);
}
