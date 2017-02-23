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
	//���ô����ʼλ�þ���
	int x=(Toolkit.getDefaultToolkit().getScreenSize().width)/2-200;
	int y=(Toolkit.getDefaultToolkit().getScreenSize().height)/2-100;
	In input=new In();//����һ�������ı���Ķ���
	//��ӵ�½��ť����ͼƬ
	ImageIcon iblo_n=new ImageIcon("material/login_n.jpg");
	ImageIcon iblo_a=new ImageIcon("material/login_a.jpg");
	ImageIcon iblo_p=new ImageIcon("material/login_p.jpg");
	ImageIcon ibri_n=new ImageIcon("material/register_n.jpg");
	ImageIcon ibri_a=new ImageIcon("material/register_a.jpg");
	ImageIcon ibri_p=new ImageIcon("material/register_p.jpg");
	JLabel blogin=new JLabel(iblo_n);//��½��ť
	JLabel bregister=new JLabel(ibri_n);
	Point p;//����һ�������洢������������λ�ã��Ա��϶����
	public Login() {
		//������С�����رյİ�ť
		JButton bmin=new JButton();
		JButton bclose=new JButton();
		//����С�����رհ�ť�ı�������
		ImageIcon iclo=new ImageIcon("material/close.jpg");
		JLabel labiclo=new JLabel(iclo);//��ͼƬ�����ǩ
		labiclo.setBounds(1, 1, iclo.getIconWidth(), iclo.getIconHeight());//���ô�С
		ImageIcon imin=new ImageIcon("material/min.jpg");
		JLabel labimin=new JLabel(imin);//��ͼƬ�����ǩ
		labimin.setBounds(1, 1, imin.getIconWidth(), imin.getIconHeight());//���ô�С
		bclose.setLayout(null);
		bclose.add(labiclo);
		bmin.setLayout(null);
		bmin.add(labimin);
		//��Ӱ�ť�¼�����С���͹ر�
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
		
		//��ӵ�½��ť��������
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
							new Warning("�������");
							s.close();	
						}
						else if(str.equals("[NULL]")){
							new Warning("�û������ڣ�");
							s.close();	
						}
						else if(str.equals("[NO]")){
							new Warning("�û��ѵ�¼��");;
							s.close();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}//�ж��ʺſ��Ƿ�Ϊ��
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
		//��Ӵ��屳��ͼƬ
		ImageIcon bg=new ImageIcon("material/l_bg.jpg");
		JLabel labbg=new JLabel(bg);//��ͼƬ�����ǩ
		labbg.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());//���ô�С
		//�Դ�����������������Ա��ƶ�����
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
		frame.setBounds(x, y, 400, 200);//���ô����Сλ��
		//���ò��֣�������
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
		frame.setUndecorated(true);//ȡ�����ڱ߿�
		frame.setResizable(false);//��ֹ�ı�߿��С
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
	JLabel labid=new JLabel("UID:");//��ʾ�����û���
	JLabel lapwd=new JLabel("����:");
	JTextField id=new JTextField();//�����û��������
	JPasswordField pwd=new JPasswordField();//�������������
	public In(){
		labid.setForeground(Color.WHITE);//��Ϊ��ɫ��
		lapwd.setForeground(Color.WHITE);
		this.setLayout(new GridLayout(4,1));//Panel����Ϊ4*1���񲼾�
		this.setOpaque(false);//Panel����Ϊ͸��
		//������
		this.add(labid);
		this.add(id);
		this.add(lapwd);
		this.add(pwd);
	}
}

