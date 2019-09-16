package de.Kurfat.Java.SimpleWebserver;

public class Start {
	
	public static void main(String[] args) {
		if(args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
			System.out.println("Example: <command> -dir <pathToDirectory> -port <serverport> -filename <index.html or another> -debug <true/false>");
			return;
		}
		Integer port = 8080;
		String dir = null;
		String fileName = "index.html";
		boolean debug = false;
		
		String last = null;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if(arg.startsWith("-")) {
				if(last != null) {
					System.out.println("Agrument needs value: " + last);
					return;
				}
				last = arg;
				continue;
			}
			if(last == null) {
				System.out.println("Value needs agrument: " + arg);
				return;
			}
			if(last.equalsIgnoreCase("-port")) {
				try {
					port = Integer.parseInt(arg);
					if(port < 1 || port > 65535) {
						System.out.println("Port range is 1-65535!");
						return;
					}
				}catch (Exception e) {
					System.out.println("Port musst be integer!");
					return;
				}
			}
			if(last.equalsIgnoreCase("-dir")) {
				dir = arg;
			}
			if(last.equalsIgnoreCase("-fileName")) {
				fileName = arg;
			}
			if(last.equalsIgnoreCase("-debug")) {
				try {
					debug = Boolean.parseBoolean(arg);
				}catch (Exception e) {
					System.out.println("Debug musst be \"true\" or \"false\"");
					return;
				}
			}
			last = null;
		}
		if(dir == null) {
			System.out.println("Directory musst be set!");
			return;
		}
		System.out.println("Starts the webserver on " + port + ". Close with \"CTRL + C\"");
		Webserver webserver = new Webserver(port, dir, fileName, debug);
		webserver.start();
	}
}
