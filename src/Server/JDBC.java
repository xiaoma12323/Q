package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public class JDBC {
	public static int register(String usname,String uspwd) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=new JDBCConnection().getConnection();
		String sql="select max(uid) from user";
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		rs.next();
		int usid=rs.getInt(1)+1;
		sql="insert into user (uid,uname,upwd,friend)values (?,?,?,?)";
		ps=conn.prepareStatement(sql);
		ps.setInt(1, usid);
		ps.setString(2, usname);
		ps.setString(3, uspwd);
		ps.setString(4, usid+"%");
		ps.executeUpdate();
		ps.close();
		conn.close();
		return usid;
	}
	public static String login(int usid,String uspwd) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=new JDBCConnection().getConnection();
		String sql="select upwd from user where uid=?";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setInt(1, usid);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			String re=rs.getString(1);
			ps.close();
			conn.close();
			if(re.equals(uspwd)){
				return "OK";
				}
			else return "WRONG";
		}
		else {
			ps.close();
			conn.close();
			return "NULL";
		}
	}
	public static String getName(int usid) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=new JDBCConnection().getConnection();
		String sql="select uname from user where uid=?";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setInt(1, usid);
		ResultSet rs=ps.executeQuery();
		String re=null;
		if(rs.next()){
			re=rs.getString(1);
		}
		ps.close();
		conn.close();
		return re;
	}
	public static Set<String> getAlluser() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=new JDBCConnection().getConnection();
		String sql="select uid,uname from user";
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		Set<String> set=new HashSet<String>();
		while(rs.next()){
			set.add(rs.getString("uname")+"("+rs.getInt("uid")+")");
		}
		ps.close();
		conn.close();
		return set;
	}
	public static String getFriend(int usid) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=new JDBCConnection().getConnection();
		String sql="select friend from user where uid=?";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setInt(1, usid);
		ResultSet rs=ps.executeQuery();
		rs.next();
		String friend=rs.getString("friend");
		ps.close();
		conn.close();
		return friend;
	}
	public static void updateFriend(int usid,String friend) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=new JDBCConnection().getConnection();
		String sql="update user set friend =? where uid=?;";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, friend);
		ps.setInt(2, usid);
		ps.executeUpdate();
		ps.close();
		conn.close();
	}
}
class JDBCConnection{
	String url;
	String user;
	String password;
	Connection coon;
	public Connection getConnection() throws Exception{
		Properties po=new Properties();
		po.load(getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
		url=po.getProperty("url");
		user=po.getProperty("user");
		password=po.getProperty("password");
		coon=DriverManager.getConnection(url, user, password);
		return coon;
	}
}