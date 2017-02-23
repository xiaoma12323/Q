package Client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login{
	Socket s=null;
	JFrame frame=new JFrame("Login");
	//设置窗体初始位置居中
	int x=(Toolkit.getDefaultToolkit().getScreenSize().width)/2-200;
	int y=(Toolkit.getDefaultToolkit().getScreenSize().height)/2-100;
	In input=new In();//创建一个输入文本框的对象
	//添加登陆按钮背景图片
	ImageIcon iblo_n=new ImageIcon("material/login_n.jpg");
	ImageIcon iblo_a=new ImageIcon("material/login_a.jpg");
	ImageIcon iblo_p=new ImageIcon("material/login_p.jpg");
	ImageIcon ibri_n=new ImageIcon("material/register_n.jpg");
	ImageIcon ibri_a=new ImageIcon("material/register_a.jpg");
	ImageIcon ibri_p=new ImageIcon("material/register_p.jpg");
	JLabel blogin=new JLabel(iblo_n);//登陆按钮
	JLabel bregister=new JLabel(ibri_n);
	Point p;//声明一个点对象存储鼠标对组件的相对位置，以便拖动组件
	public Login() {
		//创建最小化、关闭的按钮
		JButton bmin=new JButton();
		JButton bclose=new JButton();
		//将最小化、关闭按钮的背景加入
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
				System.exit(1);
			}
		});
		
		//添加登陆按钮鼠标监听器
		blogin.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(!(input.id.getText().equals(""))&&!new String(input.pwd.getPassword()).equals("")){
					try {
						Properties po=new Properties();
						po.load(getClass().getClassLoader().getResourceAsStream("connect.properties"));
						String address=po.getProperty("address");
						int port=new Integer(po.getProperty("port"));
						s=new Socket(address,port);
						PrintWriter out = new PrintWriter(s.getOutputStream(),true);
						out.println("[LOGIN]::"+input.id.getText()+"::"+new String(input.pwd.getPassword()));
						BufferedReader read=new BufferedReader(new InputStreamReader(
								s.getInputStream()));
						String str=read.readLine();
						if(str.startsWith("[OK]")){
							new Client(str.split("::")[1],s);
							frame.dispose();
						}
						else if(str.equals("[WRONG]")){
							new Warning("密码错误！");
							s.close();	
						}
						else if(str.equals("[NULL]")){
							new Warning("用户不存在！");
							s.close();	
						}
						else if(str.equals("[NO]")){
							new Warning("用户已登录！");;
							s.close();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}//判断帐号框是否为空
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				blogin.setIcon(iblo_a);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				blogin.setIcon(iblo_n);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				blogin.setIcon(iblo_p);	
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				blogin.setIcon(iblo_a);	
			}
		});
		bregister.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				new Register();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				bregister.setIcon(ibri_a);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				bregister.setIcon(ibri_n);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				bregister.setIcon(ibri_p);	
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				bregister.setIcon(ibri_a);	
			}
		});
		//添加窗体背景图片
		ImageIcon bg=new ImageIcon("material/l_bg.jpg");
		JLabel labbg=new JLabel(bg);//将图片放入标签
		labbg.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());//设置大小
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
		frame.setBounds(x, y, 400, 200);//设置窗体大小位置
		//设置布局，添加组件
		frame.setLayout(null);
		input.setBounds(200, 50, 170, 75);
		frame.add(input);
		bmin.setBounds(300, 0, 50, 20);
		bclose.setBounds(350, 0, 50, 20);
		blogin.setBounds(290, 140, 80, 30);
		bregister.setBounds(180, 140, 80, 30);
		frame.add(bmin);
		frame.add(bclose);
		frame.add(blogin);
		frame.add(bregister);
		frame.add(labbg);
		frame.setUndecorated(true);//取消窗口边框
		frame.setResizable(false);//禁止改变边框大小
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args){
		new Login();
	}
}
class In extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel labid=new JLabel("UID:");//提示输入用户名
	JLabel lapwd=new JLabel("密码:");
	JTextField id=new JTextField();//创建用户名输入框
	JPasswordField pwd=new JPasswordField();//创建密码输入框
	public In(){
		labid.setForeground(Color.WHITE);//设为白色字
		lapwd.setForeground(Color.WHITE);
		this.setLayout(new GridLayout(4,1));//Panel设置为4*1网格布局
		this.setOpaque(false);//Panel设置为透明
		//添加组件
		this.add(labid);
		this.add(id);
		this.add(lapwd);
		this.add(pwd);
	}
}

