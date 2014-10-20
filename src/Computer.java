import java.io.IOException;


public class Computer {
	
	public static final String SHUTDOWN = "shutdown -s";
	public static final String RESTART = "shutdown -r";
	public static final String HIBERNATE = "shutdown -h";
	private Runtime runtime = Runtime.getRuntime();
	private Process process;
	

	public void shutdown() {
		// TODO Auto-generated method stub
		
		try {
			process = runtime.exec(SHUTDOWN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void hibernate() {
		// TODO Auto-generated method stub
		
		try {
			process = runtime.exec(HIBERNATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void restart() {
		// TODO Auto-generated method stub
		
		try {
			process = runtime.exec(RESTART);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
