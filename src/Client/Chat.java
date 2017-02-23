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
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//����һ��ʱ���ʽ
	JTextArea re=new JTextArea("��ʼ����\n");//������Ϣ��ʾ��
	JTextArea se=new JTextArea();//������Ϣ�����
	int x=(Toolkit.getDefaultToolkit().getScreenSize().width)/2-250;
	int y=(Toolkit.getDefaultToolkit().getScreenSize().height)/2-250;
	ImageIcon sent_n=new ImageIcon("material/sent_n.jpg");
	ImageIcon sent_a=new ImageIcon("material/sent_a.jpg");
	ImageIcon sent_p=new ImageIcon("material/sent_p.jpg");
	JLabel bsent=new JLabel(sent_n);//���Ͱ�ť
	JButton bclose;
	Point p;
	String toname=null;
	public Chat(String user,String touser){
		this.toname=touser;
		//�������촰�ڱ���
		JLabel title=new JLabel(user+" to "+toname);
		title.setFont(new Font("",Font.PLAIN,15));//��������
		title.setForeground(Color.WHITE);//������ɫΪ��ɫ
		title.setBounds(90, 5,300, 30);//�������Сλ������
		frame.add(title);
		//�����ı��������Լ��Զ�����
		re.setFont(new Font("",Font.PLAIN,20));
		re.setLineWrap(true);
		se.setFont(new Font("",Font.PLAIN,20));
		se.setLineWrap(true);
		//���ı������ڹ�������
		JScrollPane sre=new JScrollPane(re);
		JScrollPane sse=new JScrollPane(se);
		sre.setBounds(10, 50, 480, 300);
		sse.setBounds(10, 380, 360, 100);
		re.setEditable(false);//������ʾ��Ϣ�ı���ֻ��
		frame.add(sre);
		frame.add(sse);
		//������С���͹رհ�ť
		JButton bmin=new JButton();
		bclose=new JButton();
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
		//��Ӱ�ť�¼�����С��
		bmin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
			
		});
		//��ӱ���ͼƬ
		ImageIcon bg=new ImageIcon("material/c_bg.jpg");
		JLabel labbg=new JLabel(bg);//��ͼƬ�����ǩ
		labbg.setBounds(0, 0,bg.getIconWidth(), bg.getIconHeight());//���ô�С
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
		//�������Ͱ�ť��������
		bsent.addMouseListener(new MouseAdapter() {
			//��������뿪�ı䷢�Ͱ�ť��ǩ
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
		//���ô����Сλ�ò�������
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
