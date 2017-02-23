package Server;

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

public class Log {
	JFrame frame=new JFrame("Server Log");
	JTextArea re=new JTextArea();//创建信息显示框
	JTextField se=new JTextField();//创建信息输入框
	int x=(Toolkit.getDefaultToolkit().getScreenSize().width)/2-250;
	int y=(Toolkit.getDefaultToolkit().getScreenSize().height)/2-250;
	ImageIcon sent_n=new ImageIcon("material/s_s_n.jpg");
	ImageIcon sent_a=new ImageIcon("material/s_s_a.jpg");
	ImageIcon sent_p=new ImageIcon("material/s_s_p.jpg");
	JLabel bsent=new JLabel(sent_n);
	Point p;
	public Log(){
		re.setFont(new Font("",Font.PLAIN,13));
		re.setLineWrap(true);
		se.setFont(new Font("",Font.PLAIN,20));
		JScrollPane sre=new JScrollPane(re);
		sre.setBounds(10, 40, 480, 350);
		se.setBounds(10, 400, 360, 30);
		re.setEditable(false);//设置显示信息文本框只读
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
		//添加按钮事件，最小化
		bmin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
			
		});
		bclose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
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
		bsent.addMouseListener(new MouseAdapter() {
			//鼠标进入或离开改变发送按钮标签
			@Override
			public void mouseEntered(MouseEvent e) {
				bsent.setIcon(sent_a);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				bsent.setIcon(sent_n);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				bsent.setIcon(sent_p);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				bsent.setIcon(sent_a);
			}
		});
		ImageIcon bg=new ImageIcon("material/s_bg.jpg");
		JLabel labbg=new JLabel(bg);//将图片放入标签
		labbg.setBounds(0, 0,bg.getIconWidth(), bg.getIconHeight());//设置大小
		bmin.setBounds(400, 0, 50, 20);
		bclose.setBounds(450, 0, 50, 20);
		bsent.setBounds(400, 400, 80, 30);
		frame.setLayout(null);
		frame.add(bsent);
		frame.add(bmin);
		frame.add(bclose);
		frame.add(sre);
		frame.add(se);
		frame.add(labbg);
		frame.setBounds(x, y, 500, 450);
		frame.setUndecorated(true);//取消窗口边框
		frame.setVisible(true);//禁止改变边框大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
