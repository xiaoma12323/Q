package Client;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Client {
	private Contacts cf;
	private PrintWriter pw;
	private BufferedReader br;
	private String name;
	private String friend;
	NewFriend newfriend;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Map<String, Chat> chat;

	public Client(String name, Socket s) {
		this.cf = new Contacts(name);
		this.name = name;
		chat = new HashMap<String, Chat>();
		try {
			pw = new PrintWriter(s.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(new Reader(s)).start(); // 创建线程，用来读取服务器发来的信息。
		listener();
	}

	public void listener() {
		// 树添加节点选择监听器
		cf.tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (((DefaultMutableTreeNode) cf.tree
							.getLastSelectedPathComponent()).getParent()
							.toString().equals("在线好友")
							&& !(cf.tree.getLastSelectedPathComponent()
									.toString().equals("空"))) {
						final String n = cf.tree.getLastSelectedPathComponent()
								.toString();
						if (!chat.containsKey(n)) {
							chat.put(n, new Chat(cf.name, n));
							chat.get(n).bsent
									.addMouseListener(new MouseAdapter() {
										@Override
										public void mouseClicked(MouseEvent e) {
											String msg = chat.get(n).se
													.getText();
											if (msg != null && !msg.equals("")) {
												pw
														.println("[CHAT]::"
																+ name + "::"
																+ n + "::"
																+ msg);
												chat.get(n).re.append(name
														+ "("
														+ df.format(new Date())
														+ ")" + ":\r\n" + msg
														+ "\r\n");
												chat.get(n).re
														.setCaretPosition(chat
																.get(n).re
																.getText()
																.length());
												chat.get(n).se.setText(null);
											}
										}
									});
							chat.get(n).bclose
									.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent e) {
											chat.get(n).frame.dispose();
											chat.remove(n);
										}
									});
						}// 双击的时候打开一个聊天窗口
						chat.get(n).frame.setVisible(true);
					}
				}
			}
		});
		// 群组聊天窗口
		cf.tr2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					final String n = "T:"
							+ cf.tr2.getLastSelectedPathComponent().toString();
					if (!chat.containsKey(n)) {
						chat.put(n, new Chat(cf.name, n));
						chat.get(n).bsent.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								String msg = chat.get(n).se.getText();
								if (msg != null && !msg.equals("")) {
									pw.println("[CHAT]::" + name + "::" + n
											+ "::" + msg);
									chat.get(n).se.setText(null);
								}
							}
						});
						chat.get(n).bclose
								.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										chat.get(n).frame.dispose();
										chat.remove(n);
									}
								});
					}// 双击的时候打开一个聊天窗口
					chat.get(n).frame.setVisible(true);
				}
			}
		});
		cf.bclose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pw.println("[EXIT]::" + cf.name);
			}

		});
		cf.ad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				newfriend = new NewFriend();
				newfriend.ser.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (newfriend.se.getText() != null
								&& !newfriend.se.getText().equals("")) {
							pw.println("[SEARCH]::" + newfriend.se.getText());
						}
					}
				});
				newfriend.ad.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (newfriend.se.getText() != null
								&& !newfriend.se.getText().equals("")) {
							pw.println("[SEARCH]::ADD:"
									+ name.split("\\(")[1].split("\\)")[0]
									+ ":" + newfriend.se.getText());
						}
					}
				});
			}
		});
	}

	public class Reader implements Runnable {
		private Socket socket;

		public Reader(Socket socket) {
			super();
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				String str = null;
				while ((str = br.readLine()) != null) { // 程序到这里阻塞，等待服务器发送信息。
					if (str.startsWith("[SYSTEM]")) {// 接收的消息如果是以[SYSTEM]开头，表示发来的是用户登录信息。
						new Warning(str.split("::")[1]);
					} else if (str.startsWith("[FRIEND]")) {
						friend = str.split("::")[1];
					} else if (str.startsWith("[ONLINE]")) { // 接收的消息如果是以[ONLINE]开头，表示发来的是所有登陆用户的列表,表示需要刷新树。
						String[] s = str.split("::");
						flushTree(s[1]); // 刷新树。
					} else if (str.startsWith("[SEARCH]")) {
						String[] arr = str.split("::");
						newfriend.re.setText("UID:" + arr[1].split(":")[0]
								+ "\nNickname:" + arr[1].split(":")[1]);
					} else if (str.startsWith("[CHAT]")) {
						String[] arr = str.split("::");
						final String user = arr[1];
						final String touser = arr[2];
						String msg = "";
						if (touser.startsWith("T:")) {
							if (!chat.containsKey(touser)) {
								chat.put(touser, new Chat(name, touser));
								new Warning("群消息：" + touser).frame.setLocation(
										(Toolkit.getDefaultToolkit()
												.getScreenSize().width) - 260,
										(Toolkit.getDefaultToolkit()
												.getScreenSize().height) - 250);
								chat.get(touser).bsent
										.addMouseListener(new MouseAdapter() {
											@Override
											public void mouseClicked(
													MouseEvent e) {
												String msg = chat.get(touser).se
														.getText();
												if (msg != null
														&& !msg.equals("")) {
													pw.println("[CHAT]::"
															+ name + "::"
															+ touser + "::"
															+ msg);
													chat.get(touser).se
															.setText(null);
												}
											}
										});
								chat.get(touser).bclose
										.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(
													ActionEvent e) {
												chat.get(touser).frame
														.dispose();
												chat.remove(touser);
											}
										});
							}
							if (arr.length == 4) {
								msg = arr[3];
							}
							if (arr.length > 4) {
								for (int i = 3; i < arr.length; i++) {
									msg += arr[i] + "::";
								}
							}
							chat.get(touser).re.append(user + "("
									+ df.format(new Date()) + ")" + ":\r\n"
									+ msg + "\r\n");
							chat.get(touser).re.setCaretPosition(chat
									.get(touser).re.getText().length());
						} else {
							if (!chat.containsKey(user)) {
								chat.put(user, new Chat(name, user));
								new Warning("新消息：" + user).frame.setLocation(
										(Toolkit.getDefaultToolkit()
												.getScreenSize().width) - 260,
										(Toolkit.getDefaultToolkit()
												.getScreenSize().height) - 250);
								chat.get(user).bsent
										.addMouseListener(new MouseAdapter() {
											@Override
											public void mouseClicked(
													MouseEvent e) {
												String msg = chat.get(user).se
														.getText();
												if (msg != null
														&& !msg.equals("")) {
													pw
															.println("[CHAT]::"
																	+ name
																	+ "::"
																	+ user
																	+ "::"
																	+ msg);
													chat.get(user).re
															.append(name
																	+ "("
																	+ df
																			.format(new Date())
																	+ ")"
																	+ ":\r\n"
																	+ msg
																	+ "\r\n");
													chat.get(user).re
															.setCaretPosition(chat
																	.get(user).re
																	.getText()
																	.length());
													chat.get(user).se
															.setText(null);
												}
											}
										});
								chat.get(user).bclose
										.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(
													ActionEvent e) {
												chat.get(user).frame.dispose();
												chat.remove(user);
											}
										});
							}
							if (arr.length == 4) {
								msg = arr[3];
							}
							if (arr.length > 4) {
								for (int i = 3; i < arr.length; i++) {
									msg += arr[i] + "::";
								}
							}
							chat.get(user).re.append(user + "("
									+ df.format(new Date()) + ")" + ":\r\n"
									+ msg + "\r\n");
							chat.get(user).re
									.setCaretPosition(chat.get(user).re
											.getText().length());
						}
					} else if (str.startsWith("[EXIT]")) {
						// String[] arr=str.split("::");
						// cf.getJta().append("[系统消息]"+arr[1]+"下线了"+"\r\n");
					} else if (str.startsWith("[SYSTEMEXIT]")) {
						socket.close();
						System.exit(0);
						// break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void flushTree(String online) { // 刷新树的方法。
			DefaultMutableTreeNode root = cf.root;
			DefaultMutableTreeNode no1 = cf.no1;
			DefaultMutableTreeNode no2 = cf.no2;
			DefaultTreeModel model = cf.model;
			root.removeAllChildren();
			no1.removeAllChildren();
			no2.removeAllChildren();
			for (String s : online.split(":")[0].split("%")) {
				if (!s.equals(cf.name)) {
					if ((friend.contains(s.split("\\(")[1].split("\\)")[0]))){
					no1.insert(new DefaultMutableTreeNode(s), no1
							.getChildCount());
					}
				}
			}
			for (String s : online.split(":")[1].split("%")) {
				if ((friend.contains(s.split("\\(")[1].split("\\)")[0]))) {
					no2.insert(new DefaultMutableTreeNode(s), no2
							.getChildCount());
				}
			}
			model.insertNodeInto(no1, root, root.getChildCount());
			model.insertNodeInto(no2, root, root.getChildCount());
			model.reload();
			cf.tree.setModel(model);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			expandTree(cf.tree, new TreePath(root));

		}
		private void expandTree(JTree tree, TreePath parent) {

		    TreeNode node = (TreeNode) parent.getLastPathComponent();

		    if (node.getChildCount() >= 0) {
		       for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
		           TreeNode n = (TreeNode) e.nextElement();
		           TreePath path = parent.pathByAddingChild(n);
		           expandTree(tree, path);
		       }
		    }
		    tree.expandPath(parent);
		}
	}
}
