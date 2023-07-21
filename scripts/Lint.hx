/** Performs the static analysis of source code. **/
function main() {
	Tools.setClassPath();
	Sys.command("lix run checkstyle --config etc/checkstyle.json --exitcode --source scripts");
	Sys.command("java net.sourceforge.pmd.cli.PmdCli cpd --dir=example,src,test --minimum-tokens=100");
	Sys.command("java", ["net.sourceforge.pmd.cli.PmdCli", "check",
		"--cache=var/pmd.cache",
		"--dir=example,src,test",
		"--no-progress",
		"--rulesets=etc/pmd.xml",
		"--use-version=java-17"
	]);
}
