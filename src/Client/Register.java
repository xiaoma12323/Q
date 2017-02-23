package Client;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register {
	JFrame frame = new JFrame("Register");
	ImageIcon r_r_n = new ImageIcon("material/r_r_n.jpg");
	ImageIcon r_r_a = new ImageIcon("material/r_r_a.jpg");
	ImageIcon r_r_p = new ImageIcon("material/r_r_p.jpg");
	ImageIcon r_s_n = new ImageIcon("material/r_s_n.jpg");
	ImageIcon r_s_a = new ImageIcon("material/r_s_a.jpg");
	ImageIcon r_s_p = new ImageIcon("material/r_s_p.jpg");
	int x = (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 200;
	int y = (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 150;
	JLabel br_r = new JLabel(r_r_n);
	JLabel br_s = new JLabel(r_s_n);
	JTextField nickname = new JTextField();
	JPasswordField pwd = new JPasswordField();
	JPasswordField cpwd = new JPasswordField();
	JButton bclose = new JButton();
	Point p;

	public Register() {
		JButton bmin = new JButton();
		ImageIcon bg = new ImageIcon("material/r_bg.jpg");
		JLabel labbg = new JLabel(bg);// 将图片放入标签
		labbg.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());// 设置大小
		ImageIcon iclo = new ImageIcon("material/close.jpg");
		JLabel labiclo = new JLabel(iclo);// 将图片放入标签
		labiclo.setBounds(1, 1, iclo.getIconWidth(), iclo.getIconHeight());// 设置大小
		ImageIcon imin = new ImageIcon("material/min.jpg");
		JLabel labimin = new JLabel(imin);// 将图片放入标签
		labimin.setBounds(1, 1, imin.getIconWidth(), imin.getIconHeight());// 设置大小
		bclose.setLayout(null);
		bclose.add(labiclo);
		bmin.setLayout(null);
		bmin.add(labimin);
		// 添加按钮事件，最小化
		bmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}

		});
		bclose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}

		});
		// 对窗体添加鼠标监听器，以便移动窗体
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				p = e.getPoint();
			}
		});
		frame.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				frame.setLocation((e.getXOnScreen() - p.x),
						(e.getYOnScreen() - p.y));
			}
		});
		br_r.addMouseListener(new MouseAdapter() {
			// 鼠标进入或离开改变重填按钮标签
			@Override
			public void mouseEntered(MouseEvent e) {
				br_r.setIcon(r_r_a);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				br_r.setIcon(r_r_n);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				br_r.setIcon(r_r_p);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				br_r.setIcon(r_r_a);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				nickname.setText(null);
				pwd.setText(null);
				cpwd.setText(null);
			}
		});
		br_s.addMouseListener(new MouseAdapter() {
			// 鼠标进入或离开改变提交按钮标签
			@Override
			public void mouseEntered(MouseEvent e) {
				br_s.setIcon(r_s_a);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				br_s.setIcon(r_s_n);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				br_s.setIcon(r_s_p);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				br_s.setIcon(r_s_a);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (nickname.getText() != null
						&& !nickname.getText().equals("")
						&& new String(pwd.getPassword()) != null
						&& !(new String(pwd.getPassword()).equals(""))) {
					if (new String(pwd.getPassword()).equals(new String(cpwd
							.getPassword()))) {
						Properties po = new Properties();
						try {
							po.load(getClass().getClassLoader()
									.getResourceAsStream("connect.properties"));
							String address = po.getProperty("address");
							int port = new Integer(po.getProperty("port"));
							Socket s = new Socket(address, port);
							PrintWriter out = new PrintWriter(s
									.getOutputStream(), true);
							out.println("[REGISTER]::" + nickname.getText()
									+ "::" + new String(pwd.getPassword()));
							BufferedReader read = new BufferedReader(
									new InputStreamReader(s.getInputStream()));
							String str = read.readLine();
							if (str.startsWith("[REGISTER]::")) {
								new Warning("您的帐号为：" + str.split("::")[1]);
								s.close();
								frame.dispose();
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					new Warning("两次输入的密码不同！");
				}
			}
		});
		frame.setBounds(x, y, 400, 300);
		bmin.setBounds(300, 0, 50, 20);
		bclose.setBounds(350, 0, 50, 20);
		frame.setLayout(null);

		nickname.setBounds(180, 70, 150, 25);
		pwd.setBounds(180, 115, 150, 25);
		cpwd.setBounds(180, 160, 150, 25);
		br_r.setBounds(90, 215, 80, 55);
		br_s.setBounds(230, 215, 80, 55);
		frame.add(nickname);
		frame.add(pwd);
		frame.add(cpwd);
		frame.add(br_s);
		frame.add(br_r);

		frame.add(bmin);
		frame.add(bclose);
		frame.add(labbg);
		frame.setUndecorated(true);// 取消窗口边框
		frame.setVisible(true);// 禁止改变边框大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
