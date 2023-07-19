import sys.FileSystem;

/** Runs a Java main class. **/
function main() {
	final jar = "bin/io.belin.free_mobile.jar";
	Tools.setClassPath();
	Sys.command("java", (FileSystem.exists(jar) ? ['--class-path=$jar'] : []).concat(Sys.args()));
}
