package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Chat{
	JFrame frame=new JFrame("Chat");
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//创建一个时间格式
	JTextArea re=new JTextArea("开始聊天\n");//创建信息显示框
	JTextArea se=new JTextArea();//创建信息输入框
	int x=(Toolkit.getDefaultToolkit().getScreenSize().width)/2-250;
	int y=(Toolkit.getDefaultToolkit().getScreenSize().height)/2-250;
	ImageIcon sent_n=new ImageIcon("material/sent_n.jpg");
	ImageIcon sent_a=new ImageIcon("material/sent_a.jpg");
	ImageIcon sent_p=new ImageIcon("material/sent_p.jpg");
	JLabel bsent=new JLabel(sent_n);//发送按钮
	JButton bclose;
	Point p;
	String toname=null;
	public Chat(String user,String touser){
		this.toname=touser;
		//创建聊天窗口标题
		JLabel title=new JLabel(user+" to "+toname);
		title.setFont(new Font("",Font.PLAIN,15));//设置字体
		title.setForeground(Color.WHITE);//字体颜色为白色
		title.setBounds(90, 5,300, 30);//姓名板大小位置设置
		frame.add(title);
		//设置文本框字体以及自动换行
		re.setFont(new Font("",Font.PLAIN,20));
		re.setLineWrap(true);
		se.setFont(new Font("",Font.PLAIN,20));
		se.setLineWrap(true);
		//将文本框至于滚动窗体
		JScrollPane sre=new JScrollPane(re);
		JScrollPane sse=new JScrollPane(se);
		sre.setBounds(10, 50, 480, 300);
		sse.setBounds(10, 380, 360, 100);
		re.setEditable(false);//设置显示信息文本框只读
		frame.add(sre);
		frame.add(sse);
		//创建最小化和关闭按钮
		JButton bmin=new JButton();
		bclose=new JButton();
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
		//添加背景图片
		ImageIcon bg=new ImageIcon("material/c_bg.jpg");
		JLabel labbg=new JLabel(bg);//将图片放入标签
		labbg.setBounds(0, 0,bg.getIconWidth(), bg.getIconHeight());//设置大小
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
		//创建发送按钮鼠标监听器
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
		//设置窗体大小位置并添加组件
		frame.setBounds(x, y, 500, 500);
		bmin.setBounds(400, 0, 50, 20);
		bclose.setBounds(450, 0, 50, 20);
		bsent.setBounds(370, 380, 129, 98);
		frame.setLayout(null);
		frame.add(bmin);
		frame.add(bclose);
		frame.add(bsent);
		frame.add(labbg);
		frame.setUndecorated(true);
		//frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
