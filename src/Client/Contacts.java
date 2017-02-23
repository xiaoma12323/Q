package Client;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class Contacts {
	DefaultMutableTreeNode root, no1, no2;
	DefaultTreeModel model; // ��ģ��
	JTree tree; // ��
	JTree tr2;
	JButton bclose;
	ImageIcon ad_n=new ImageIcon("material/ad_n.jpg");
	ImageIcon ad_a=new ImageIcon("material/ad_a.jpg");
	ImageIcon ad_p=new ImageIcon("material/ad_p.jpg");
	JLabel ad=new JLabel(ad_n);
	String name;
	JFrame frame = new JFrame("Contacts");
	// ���ô����ʼλ�þ���
	int x = (Toolkit.getDefaultToolkit().getScreenSize().width) - 300;
	int y = (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 300;
	Point p;// ����һ�������洢������������λ�ã��Ա��϶����

	public Contacts(String name) {
		this.name = name;
		Id id = new Id();
		ImageIcon f_o = new ImageIcon("material/f_o.jpg");
		ImageIcon f_c = new ImageIcon("material/f_c.jpg");
		ImageIcon team = new ImageIcon("material/team.jpg");
		root = new DefaultMutableTreeNode("�����б�");
		model = new DefaultTreeModel(root);
		no1 = new DefaultMutableTreeNode("���ߺ���");
		no2 = new DefaultMutableTreeNode("���ߺ���");
		model.insertNodeInto(no1, root, root.getChildCount());
		model.insertNodeInto(no2, root, root.getChildCount());
		tree = new JTree(model);
		tree.setFont(new Font("", Font.PLAIN, 20));// ���ú���������
		tree.setRootVisible(false);// ���ø��ڵ㲻�ɼ�
		// ����Ҷ�ڵ��ͼ��

		IconNodeRenderer renderer = new IconNodeRenderer();
		renderer.setClosedIcon(f_c);
		renderer.setOpenIcon(f_o);
		tree.setCellRenderer(renderer);
		JTabbedPane jtbf = new JTabbedPane();
		JScrollPane st1 = new JScrollPane(tree);// �������ڹ�������
		st1.setName("����");// ���ù�����������
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("������");
		tr2 = new JTree(node);
		DefaultTreeCellRenderer renderer2=new DefaultTreeCellRenderer();
		renderer2.setLeafIcon(team);
		tr2.setCellRenderer(renderer2);
		tr2.setFont(new Font("", Font.PLAIN, 20));
		JScrollPane st2 = new JScrollPane(tr2);
		st2.setName("Ⱥ��");
		jtbf.add(st1);
		jtbf.add(st2);
		// ������С�����رյİ�ť
		JButton bmin = new JButton();
		bclose = new JButton();
		// ����С�����رհ�ť�ı�������
		ImageIcon iclo = new ImageIcon("material/close.jpg");
		JLabel labiclo = new JLabel(iclo);// ��ͼƬ�����ǩ
		labiclo.setBounds(1, 1, iclo.getIconWidth(), iclo.getIconHeight());// ���ô�С
		ImageIcon imin = new ImageIcon("material/min.jpg");
		JLabel labimin = new JLabel(imin);// ��ͼƬ�����ǩ
		labimin.setBounds(1, 1, imin.getIconWidth(), imin.getIconHeight());// ���ô�С
		// ���ùرհ�ť�Ĳ����뱳��ͼƬ
		bclose.setLayout(null);
		bclose.add(labiclo);
		// ������С����ť�Ĳ����뱳��ͼƬ
		bmin.setLayout(null);
		bmin.add(labimin);
		// ��Ӱ�ť�¼�����С��
		bmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}

		});
		// �Դ�����������������Ա��ƶ�����
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
		ImageIcon bg = new ImageIcon("material/co_bg.jpg");
		JLabel labbg = new JLabel(bg);// ��ͼƬ�����ǩ
		labbg.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());// ���ô�С
		frame.setBounds(x, y, 220, 500);
		// ���ò��֣�������
		frame.setLayout(null);
		bmin.setBounds(120, 0, 50, 20);
		bclose.setBounds(170, 0, 50, 20);
		id.setLocation(25, 50);
		jtbf.setBounds(10, 140, 200, 350);
		ad.setBounds(160, 130, 30, 30);
		frame.add(ad);
		frame.add(id);
		frame.add(jtbf);
		frame.add(bmin);
		frame.add(bclose);
		frame.add(labbg);
		frame.setUndecorated(true);// ȡ�����ڱ߿�
		frame.setVisible(true);// ��ֹ�ı�߿��С
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// ����һ��Id�����ڷ���ͷ�������
	class Id extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Id() {
			// ���ͷ��ͼƬ
			ImageIcon pic = new ImageIcon("material/tx1.jpg");
			JLabel labpic = new JLabel(pic);// ��ͼƬ�����ǩ
			labpic.setBounds(0, 0, pic.getIconWidth(), pic.getIconHeight());// ���ô�С
			JLabel lname = new JLabel(name);// ��������
			lname.setFont(new Font("", Font.PLAIN, 15));// ��������
			lname.setForeground(Color.WHITE);// ������ɫΪ��ɫ
			lname.setBounds(60, 10, 150, 30);// �������Сλ������
			this.setSize(180, 50);// id Panel��С����
			this.setOpaque(false);// Panel����Ϊ͸��
			this.setLayout(null);// ����Panel����ΪNULL
			// ���ͷ������
			this.add(labpic);
			this.add(lname);
		}
	}
}

class IconNodeRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		ImageIcon image_f_c = new ImageIcon("material/f_c.jpg");
		ImageIcon image_tx_on = new ImageIcon("material/tx_on.jpg");
		ImageIcon image_tx_off = new ImageIcon("material/tx_off.jpg");
		if (node.getLevel() == 1 && node.getChildCount()==0) {
			this.setIcon(image_f_c);
		}
		if (node.getLevel() == 2) {
			if (node.getParent().toString().equals("���ߺ���")) {
				this.setIcon(image_tx_on);
			} else if (node.getParent().toString().equals("���ߺ���")) {
				this.setIcon(image_tx_off);
			}

		}
		return this;
	}

}
