package Server;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Server {
	private ServerSocket ss;
	private Socket s;
	private Map<String, Socket> map; //在线用户	
	private Set<String> offuser;  //离线用户
	Log log;

	public Server() {
		try {
			ss = new ServerSocket(10000);
			map = new HashMap<String, Socket>();
			offuser = JDBC.getAlluser();
			log = new Log();
			log.bsent.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String msg = log.se.getText();
					if (msg != null && !msg.equals("")) {
						log.se.setText(null);
						log.re.append("[SYSTEM]:" + msg + "\n");
						Collection<Socket> coll = map.values();
						for (Socket s : coll) {
							PrintWriter pw1;
							try {
								pw1 = new PrintWriter(s.getOutputStream(), true);
								pw1.println("[SYSTEM]::" + msg);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			});
			log.re.append("服务器启动...\n");
			while (true) {
				s = ss.accept();
				log.re.append("接收到客户端:" + s + "\n");
				log.re.setCaretPosition(log.re.getText().length());
				new Thread(new Reader(s)).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class Reader implements Runnable {
		private Socket socket;

		public Reader(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				String str = null;
				while ((str = br.readLine()) != null) {
					if (str.startsWith("[REGISTER]")) {
						String[] arr = str.split("::");
						String usname = arr[1];
						String uspwd = arr[2];
						String usid = new Integer(JDBC.register(usname, uspwd))
								.toString();
						offuser = JDBC.getAlluser();
						pw.println("[REGISTER]::" + usid);
					} else if (str.startsWith("[LOGIN]")) {
						String[] arr = str.split("::");
						String confirm = JDBC
								.login(new Integer(arr[1]), arr[2]);
						if (confirm.equals("OK")) {
							Socket s1 = map.get(JDBC
									.getName(new Integer(arr[1]))
									+ "(" + arr[1] + ")");
							if (s1 == null) {
								pw.println("[OK]::"
										+ JDBC.getName(new Integer(arr[1]))
										+ "(" + arr[1] + ")");
								map.put(JDBC.getName(new Integer(arr[1])) + "("
										+ arr[1] + ")", socket);
								offuser.remove(JDBC
										.getName(new Integer(arr[1]))
										+ "(" + arr[1] + ")");
								log.re.append(onlineUser());
								log.re.append("\n[OFFLINE]:" + offlineUser()
										+ "\n");
								log.re.setCaretPosition(log.re.getText()
										.length());
								pw.println("[FRIEND]::"
										+ JDBC.getFriend(new Integer(arr[1])));
								sendMessage(onlineUser() + offlineUser());
							} else {
								pw.println("[NO]");
							}
						} else if (confirm.equals("WRONG")) {
							pw.println("[WRONG]");
						} else if (confirm.equals("NULL")) {
							pw.println("[NULL]");
						}
					} else if (str.startsWith("[SEARCH]")) {
						System.out.println(str);
						String[] arr = str.split("::");
						if (arr[1].startsWith("ADD:")) {
							if (JDBC.getName(new Integer(arr[1].split(":")[2])) != null) {
								String friend = JDBC.getFriend(new Integer(
										arr[1].split(":")[1]));
								if (!(friend.contains(arr[1].split(":")[2]))) {
									friend += arr[1].split(":")[2] + "%";
									JDBC.updateFriend(new Integer(arr[1]
											.split(":")[1]), friend);
									pw.println("[SYSTEM]::添加成功!");
								}else
									pw.println("[SYSTEM]::你已添加该好友!");
								    pw.println("[FRIEND]::"+ JDBC.getFriend(new Integer(arr[1].split(":")[1])));
								    pw.println(onlineUser() + offlineUser());
							} else
								pw.println("[SYSTEM]::用户不存在!");
						} else {
							if (JDBC.getName(new Integer(arr[1])) != null) {
								pw.println("[SEARCH]::" + arr[1] + ":"
										+ JDBC.getName(new Integer(arr[1])));
							}
							 else
									pw.println("[SYSTEM]::用户不存在!");
						}
					} else if (str.startsWith("[CHAT]")) {
						String[] arr = str.split("::");
						String usrname = arr[1];
						String toUsr = arr[2];
						String msg = "";
						if (arr.length == 4) {
							msg = arr[3];
						}
						if (arr.length > 4) {
							for (int i = 3; i < arr.length; i++) {
								msg += arr[i];
							}
						}
						if (toUsr.equals("T:所有人")) {
							sendMessage("[CHAT]::" + usrname + "::" + toUsr
									+ "::" + msg);
						} else {
							Socket toS = map.get(toUsr);
							PrintWriter toPw = new PrintWriter(toS
									.getOutputStream(), true);
							toPw.println("[CHAT]::" + usrname + "::" + toUsr
									+ "::" + msg);
						}
					} else if (str.startsWith("[EXIT]")) {
						String[] arr = str.split("::");
						map.remove(arr[1]);
						offuser.add(arr[1]);
						sendMessage("[EXIT]::" + arr[1]);
						sendMessage(onlineUser() + offlineUser());
						pw.println("[SYSTEMEXIT]");
						log.re.append(onlineUser());
						log.re.append("\n[OFFLINE]:" + offlineUser() + "\n");
						log.re.setCaretPosition(log.re.getText().length());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public String onlineUser() throws Exception { // 拿到所有的在线用户。
			String users = "[ONLINE]::";
			Set<String> set = map.keySet();
			for (String s : set) {
				users += s + "%";
			}
			return users;

		}

		public String offlineUser() {
			String users = ":";
			for (String s : offuser) {
				users += s + "%";
			}
			return users;
		}

		public void sendMessage(String msg) { // 拿到所有的Socket，并将消息一一转发。
			Collection<Socket> coll = map.values();
			for (Socket s : coll) {
				try {
					PrintWriter pw1 = new PrintWriter(s.getOutputStream(), true);
					pw1.println(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {
		new Server();
	}
}
