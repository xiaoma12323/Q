package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Warning {
	JFrame frame=new JFrame("Warning");
	int x=(Toolkit.getDefaultToolkit().getScreenSize().width)/2-130;
	int y=(Toolkit.getDefaultToolkit().getScreenSize().height)/2-100;
	ImageIcon ibc_n=new ImageIcon("material/w_b_n.jpg");
	ImageIcon ibc_a=new ImageIcon("material/w_b_a.jpg");
	ImageIcon ibc_p=new ImageIcon("material/w_b_p.jpg");
	Point p;
	JLabel bcon=new JLabel(ibc_n);//��½��ť
	public Warning(String wa){
		JLabel title=new JLabel(wa);
		title.setFont(new Font("", Font.PLAIN,18));
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(JLabel.CENTER);
		//��Ӵ��屳��ͼƬ
		ImageIcon bg=new ImageIcon("material/warning_bg.jpg");
		JLabel labbg=new JLabel(bg);//��ͼƬ�����ǩ
		labbg.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());//���ô�С
		bcon.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				bcon.setIcon(ibc_a);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				bcon.setIcon(ibc_p);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				bcon.setIcon(ibc_n);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				bcon.setIcon(ibc_a);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		
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
		title.setBounds(30, 80, 200, 60);
		bcon.setBounds(0, 156, 260, 44);
		frame.setBounds(x, y, 260, 200);
		frame.setLayout(null);
		frame.add(title);
		frame.add(bcon);
		frame.add(labbg);
		frame.setUndecorated(true);//ȡ�����ڱ߿�
		frame.setResizable(false);//��ֹ�ı�߿��С
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
