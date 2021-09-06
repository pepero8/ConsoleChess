package server.src;
//invoker
class Executecmd {
	Command cmd;

	void setCmd(Command cmd) {
		this.cmd = cmd;
	}

	void execute() {
		cmd.execute();
	}
}