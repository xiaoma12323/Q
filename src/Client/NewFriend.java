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
	JTextArea re=new JTextArea("���������������Ҫ���ҵĺ���ID");//������Ϣ��ʾ��
	JTextField se=new JTextField();//������Ϣ�����
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
		frame=new JFrame("���Һ���");
		JButton bmin=new JButton();
		JButton bclose=new JButton();
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
		ser.addMouseListener(new MouseAdapter() {
			//��������뿪�ı䷢�Ͱ�ť��ǩ
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
			//��������뿪�ı䷢�Ͱ�ť��ǩ
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
		JLabel labbg = new JLabel(bg);// ��ͼƬ�����ǩ
		labbg.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());// ���ô�С
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
		frame.setUndecorated(true);//ȡ�����ڱ߿�
		frame.setResizable(false);//��ֹ�ı�߿��С
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new NewFriend();
	}
}
