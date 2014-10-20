
public class ShutdownCommand implements Command{

	Computer computer;
	
	public ShutdownCommand(Computer computer){
		this.computer = computer;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		computer.shutdown();
	}
	
}
