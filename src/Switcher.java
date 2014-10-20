
public class Switcher {
	private Command command;
	
	public void setCommand(Command command){
		this.command = command;
	}
	
	public void switchCommand(){
		command.execute();
	}
}
