package Client;

import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NewFriend {
	JFrame frame;
	JTextArea re=new JTextArea("请在左边栏内输入要查找的好友ID");//创建信息显示框
	JTextField se=new JTextField();//创建信息输入框
	int x=(Toolkit.getDefaultToolkit().getScreenSize().width)/2-150;
	int y=(Toolkit.getDefaultToolkit().getScreenSize().height)/2-100;
	ImageIcon ser_n=new ImageIcon("material/ser_n.jpg");
	ImageIcon ser_a=new ImageIcon("material/ser_a.jpg");
	ImageIcon ser_p=new ImageIcon("material/ser_p.jpg");
	ImageIcon ad_n=new ImageIcon("material/add_n.jpg");
	ImageIcon ad_a=new ImageIcon("material/add_a.jpg");
	ImageIcon ad_p=new ImageIcon("material/add_p.jpg");
	JLabel ser=new JLabel(ser_n);
	JLabel ad=new JLabel(ad_n);
	Point p;
	public NewFriend(){
		frame=new JFrame("查找好友");
		JButton bmin=new JButton();
		JButton bclose=new JButton();
		ImageIcon iclo=new ImageIcon("material/close.jpg");
		JLabel labiclo=new JLabel(iclo);//将图片放入标签
		labiclo.setBounds(1, 1, iclo.getIconWidth(), iclo.getIconHeight());//设置大小
		ImageIcon imin=new ImageIcon("material/min.jpg");
		JLabel labimin=new JLabel(imin);//将图片放入标签
		labimin.setBounds(1, 1, imin.getIconWidth(), imin.getIconHeight());//设置大小
		bclose.setLayout(null);
		bclose.add(labiclo);
		bmin.setLayout(null);
		bmin.add(labimin);
		//添加按钮事件，最小化和关闭
		bmin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
			
		});
		bclose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		re.setFont(new Font("",Font.PLAIN,13));
		re.setLineWrap(true);
		re.setEditable(false);
		JScrollPane sre=new JScrollPane(re);
		se.setFont(new Font("",Font.PLAIN,20));
		sre.setBounds(150, 50, 130, 70);
		se.setBounds(30, 90, 100, 30);
		ser.setBounds(80, 130, 40, 50);
		ad.setBounds(180, 130, 50, 60);
		//对窗体添加鼠标监听器，以便移动窗体
		frame.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				p=e.getPoint();
			}
		});
		frame.addMouseMotionListener(new MouseMotionAdapter(){

			@Override
			public void mouseDragged(MouseEvent e) {
				frame.setLocation((e.getXOnScreen()-p.x),(e.getYOnScreen()-p.y));
			}
		});
		ser.addMouseListener(new MouseAdapter() {
			//鼠标进入或离开改变发送按钮标签
			@Override
			public void mouseEntered(MouseEvent e) {
				ser.setIcon(ser_a);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ser.setIcon(ser_n);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ser.setIcon(ser_p);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				ser.setIcon(ser_a);
			}
		});
		ad.addMouseListener(new MouseAdapter() {
			//鼠标进入或离开改变发送按钮标签
			@Override
			public void mouseEntered(MouseEvent e) {
				ad.setIcon(ad_a);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ad.setIcon(ad_n);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ad.setIcon(ad_p);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				ad.setIcon(ad_a);
			}
		});
		ImageIcon bg = new ImageIcon("material/nf_bg.jpg");
		JLabel labbg = new JLabel(bg);// 将图片放入标签
		labbg.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());// 设置大小
		bmin.setBounds(200, 0, 50, 20);
		bclose.setBounds(250, 0, 50, 20);
		frame.setLayout(null);
		frame.add(bmin);
		frame.add(bclose);
		frame.add(sre);
		frame.add(se);
		frame.add(ser);
		frame.add(ad);
		frame.add(labbg);
		frame.setBounds(x, y, 300,200);
		frame.setUndecorated(true);//取消窗口边框
		frame.setResizable(false);//禁止改变边框大小
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new NewFriend();
	}
}
