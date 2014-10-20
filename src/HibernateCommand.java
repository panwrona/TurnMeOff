
public class HibernateCommand implements Command {

	Computer computer;
	
	public HibernateCommand(Computer computer){
		this.computer = computer;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		computer.hibernate();
	}

}
