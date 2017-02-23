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
	DefaultTreeModel model; // 树模型
	JTree tree; // 树
	JTree tr2;
	JButton bclose;
	ImageIcon ad_n=new ImageIcon("material/ad_n.jpg");
	ImageIcon ad_a=new ImageIcon("material/ad_a.jpg");
	ImageIcon ad_p=new ImageIcon("material/ad_p.jpg");
	JLabel ad=new JLabel(ad_n);
	String name;
	JFrame frame = new JFrame("Contacts");
	// 设置窗体初始位置居中
	int x = (Toolkit.getDefaultToolkit().getScreenSize().width) - 300;
	int y = (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 300;
	Point p;// 声明一个点对象存储鼠标对组件的相对位置，以便拖动组件

	public Contacts(String name) {
		this.name = name;
		Id id = new Id();
		ImageIcon f_o = new ImageIcon("material/f_o.jpg");
		ImageIcon f_c = new ImageIcon("material/f_c.jpg");
		ImageIcon team = new ImageIcon("material/team.jpg");
		root = new DefaultMutableTreeNode("好友列表");
		model = new DefaultTreeModel(root);
		no1 = new DefaultMutableTreeNode("在线好友");
		no2 = new DefaultMutableTreeNode("离线好友");
		model.insertNodeInto(no1, root, root.getChildCount());
		model.insertNodeInto(no2, root, root.getChildCount());
		tree = new JTree(model);
		tree.setFont(new Font("", Font.PLAIN, 20));// 设置好友树字体
		tree.setRootVisible(false);// 设置根节点不可见
		// 设置叶节点的图标

		IconNodeRenderer renderer = new IconNodeRenderer();
		renderer.setClosedIcon(f_c);
		renderer.setOpenIcon(f_o);
		tree.setCellRenderer(renderer);
		JTabbedPane jtbf = new JTabbedPane();
		JScrollPane st1 = new JScrollPane(tree);// 将树至于滚动窗格
		st1.setName("好友");// 设置滚动窗格名称
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("所有人");
		tr2 = new JTree(node);
		DefaultTreeCellRenderer renderer2=new DefaultTreeCellRenderer();
		renderer2.setLeafIcon(team);
		tr2.setCellRenderer(renderer2);
		tr2.setFont(new Font("", Font.PLAIN, 20));
		JScrollPane st2 = new JScrollPane(tr2);
		st2.setName("群组");
		jtbf.add(st1);
		jtbf.add(st2);
		// 创建最小化、关闭的按钮
		JButton bmin = new JButton();
		bclose = new JButton();
		// 将最小化、关闭按钮的背景加入
		ImageIcon iclo = new ImageIcon("material/close.jpg");
		JLabel labiclo = new JLabel(iclo);// 将图片放入标签
		labiclo.setBounds(1, 1, iclo.getIconWidth(), iclo.getIconHeight());// 设置大小
		ImageIcon imin = new ImageIcon("material/min.jpg");
		JLabel labimin = new JLabel(imin);// 将图片放入标签
		labimin.setBounds(1, 1, imin.getIconWidth(), imin.getIconHeight());// 设置大小
		// 设置关闭按钮的布局与背景图片
		bclose.setLayout(null);
		bclose.add(labiclo);
		// 设置最小化按钮的布局与背景图片
		bmin.setLayout(null);
		bmin.add(labimin);
		// 添加按钮事件，最小化
		bmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
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
		ImageIcon bg = new ImageIcon("material/co_bg.jpg");
		JLabel labbg = new JLabel(bg);// 将图片放入标签
		labbg.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());// 设置大小
		frame.setBounds(x, y, 220, 500);
		// 设置布局，添加组件
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
		frame.setUndecorated(true);// 取消窗口边框
		frame.setVisible(true);// 禁止改变边框大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// 构造一个Id类用于放置头像和名字
	class Id extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Id() {
			// 添加头像图片
			ImageIcon pic = new ImageIcon("material/tx1.jpg");
			JLabel labpic = new JLabel(pic);// 将图片放入标签
			labpic.setBounds(0, 0, pic.getIconWidth(), pic.getIconHeight());// 设置大小
			JLabel lname = new JLabel(name);// 设置名字
			lname.setFont(new Font("", Font.PLAIN, 15));// 设置字体
			lname.setForeground(Color.WHITE);// 字体颜色为白色
			lname.setBounds(60, 10, 150, 30);// 姓名板大小位置设置
			this.setSize(180, 50);// id Panel大小设置
			this.setOpaque(false);// Panel设置为透明
			this.setLayout(null);// 设置Panel布局为NULL
			// 添加头像、姓名
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
			if (node.getParent().toString().equals("在线好友")) {
				this.setIcon(image_tx_on);
			} else if (node.getParent().toString().equals("离线好友")) {
				this.setIcon(image_tx_off);
			}

		}
		return this;
	}

}
