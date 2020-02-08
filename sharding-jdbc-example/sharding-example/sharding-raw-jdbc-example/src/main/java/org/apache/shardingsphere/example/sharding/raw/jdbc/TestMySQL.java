package org.apache.shardingsphere.example.sharding.raw.jdbc;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
* @author 
* cobar 使用实例，和访问mysql没区别！！！！，用ftp 227下/home/itsp/document_ITSP3.0/Daas/demo mysql-connector-java-5.1.24-bin.jar 
*/
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import org.apache.shardingsphere.core.constant.properties.ShardingProperties;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;

import com.mysql.jdbc.MySQLConnection;

import ch.qos.logback.classic.Logger;
import junit.framework.TestCase;


/**
* 
*/
public class TestMySQL {
	
	/**
	* 
	*/
	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static String url= null;
	static  ResultSet set= null;
	
	/**
	* @param args
	*/
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		String host =  "localhost";
		url = "jdbc:mysql://"+host+":3306/demo_ds_0?allowMultiQueries=true&useSSL=false&useServerPrepStmts=true&rewriteBatchedStatements=true";
		 conn = DriverManager.getConnection(url,"root","");
		TestMySQL g = new TestMySQL();
		g.assertUpdateBatch();
}

private void deleteBatch() throws SQLException {
	//String sql = "delete from t_order WHERE status=?";
	System.out.print("url:"+url);
	//conn.setAutoCommit(false);
	
	
	Statement st = conn.createStatement();
	// st.execute("delete from t_order WHERE status='1';delete from t_order WHERE status='2'");
	st.addBatch("delete from t_order WHERE status='1'");
	st.addBatch("delete from t_order WHERE status='2'");
	st.addBatch("delete from t_order WHERE status='3'");
	st.addBatch("delete from t_order WHERE status='4'");
	st.addBatch("delete from t_order WHERE status='5'");
	st.executeBatch();
		
	}

/**
 * 
 */
public void updateBatch()
{
	try {
		 Class.forName("com.mysql.jdbc.Driver");
		 String host =  "localhost";
		  url = "jdbc:mysql://"+host+":3306/demo_ds_0?useServerPrepStmts=true&cachePrepStmts=true";
		  
		 conn = DriverManager.getConnection(url,"root","");
		 //conn.setAutoCommit(false);
		 String sql = "SELECT d_next_o_id, d_tax FROM district" 
		 + " WHERE d_id = ? AND d_w_id = ? FOR UPDATE";

			PreparedStatement prest = conn.prepareStatement(sql);
			prest.setInt(1, 1);
			prest.setInt(2, 4);

			ResultSet re = prest.executeQuery();
			System.out.println("re-----"+re);

			if (prest != null)
				prest.close();
				
	}catch(Exception ex){
		ex.printStackTrace();
	}
	finally{releaseRes();
	}
	 System.out.println("ok");
}

public void assertUpdateBatch() throws SQLException {
	String sql = "UPDATE t_order SET status=? WHERE status=?";
	System.out.print("url:"+url);
	//conn.setAutoCommit(false);
	
	
//	Statement st = conn.createStatement();
//	st.addBatch("UPDATE t_order SET status='2' WHERE status='1'");
//	st.addBatch("UPDATE t_order SET status='2' WHERE status='2'");
//	st.addBatch("UPDATE t_order SET status='2' WHERE status='3'");
//	st.addBatch("UPDATE t_order SET status='2' WHERE status='4'");
//	st.addBatch("UPDATE t_order SET status='2' WHERE status='5'");
//	st.executeBatch();
//	
//	if(true)return;
   
	try {
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, "1");
        preparedStatement.setString(2, "mul");
        preparedStatement.addBatch();
        preparedStatement.setString(1, "1");
        preparedStatement.setString(2, "mul");
        preparedStatement.addBatch();
        preparedStatement.setString(1, "1");
        preparedStatement.setString(2, "mul");
        preparedStatement.addBatch(); 
        preparedStatement.setString(1, "1");
        preparedStatement.setString(2, "mul");
        preparedStatement.addBatch();
        preparedStatement.setString(1, "1");
        preparedStatement.setString(2, "mul");
        preparedStatement.addBatch();
        int[] result = preparedStatement.executeBatch();
        //conn.commit();
//        assertThat(result.length, is(3));
//        assertThat(result[0], is(4));
//        assertThat(result[1], is(0));
//        assertThat(result[2], is(4));
    }catch(Exception e){
    	e.printStackTrace();
    }
}

public void insertBatch()
{
	try {
		Class.forName("com.mysql.jdbc.Driver");
		 String host =  "localhost";
		  url = "jdbc:mysql://"+host+":3306/demo_ds_0?useSSL=false&useServerPrepStmts=true&rewriteBatchedStatements=true";
		  																				   
		 conn = DriverManager.getConnection(url,"root","");
		 conn.setAutoCommit(true);
		 String sql = "INSERT INTO t_order (user_id, status,order_id) VALUES (?, ?,?)";
		 Statement st = conn.createStatement();
		 st.execute("delete  from t_order");
			PreparedStatement prest = conn.prepareStatement(sql);
			for(int i =0;i<5;i++)
			{
				prest.setInt(1, i);
				prest.setString(2, "batch insert");
				prest.setInt(3, i);
				prest.addBatch();
			}
			

			int[] res = prest.executeBatch();
			for(int i =6;i<11;i++)
			{
				prest.setInt(1, i);
				prest.setString(2, "batch insert");
				prest.setInt(3, i);
				prest.addBatch();
			}
			res = prest.executeBatch();
			System.out.println("insert-----"+res);

			if (prest != null)
				prest.close();
				
	}catch(Exception ex){
		ex.printStackTrace();
	}
	finally{releaseRes();
	}
	System.out.println("ok");
}

public void insert()
{
	 String result = null;
     try {

         Class.forName("com.mysql.jdbc.Driver");
		 String host = "localhost";//10.45.43.201
		 url = "jdbc:mysql://"+host+":8066/zdaastpccdb?rewriteBatchedStatements=true";
		 System.out.println("url:"+  url);
		 conn = DriverManager.getConnection(url,"root","123");
		 conn.setAutoCommit(false);
          pstmt = conn
                 .prepareStatement("insert into tb (id) values(?)");
          for(int i = 0;i<2;i++)
          {
        	  pstmt.setInt(1,1);
        	  pstmt.addBatch();
          }
         
         int[] re = pstmt.executeBatch();
         pstmt.clearBatch();
         conn.commit();

     } catch (Exception ex) {
         ex.printStackTrace();
     } finally {
    	 try {
 			if (pstmt != null)
 				pstmt.close();
 			if (set != null)
 				set.close();
 			if (conn != null)		
 				conn.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
     }
 
}

public void query()
{
	 String result = null;
     try {

         Class.forName("com.mysql.jdbc.Driver");
		 String host = "localhost";//10.45.43.201
		 url = "jdbc:mysql://"+host+":8087/dbtest?rewriteBatchedStatements=true";
		 System.out.println("url:"+  url);
		 conn = DriverManager.getConnection(url,"root","123");
		 //conn.setAutoCommit(false);
          Statement  pstmt = conn
                 .createStatement();
          
         boolean  re = pstmt.execute("drop table  tb  ");
         
           re = pstmt.execute("create table  tb  (id int)");
         //conn.rollback();

     } catch (Exception ex) {
         ex.printStackTrace();
     } finally {
    	 try {
 			if (pstmt != null)
 				pstmt.close();
 			if (set != null)
 				set.close();
 			if (conn != null)		
 				conn.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
     }
    

 

}

public static void releaseRes()
{
		try {
			if (pstmt != null)
				pstmt.close();
			if (set != null)
				set.close();
			if (conn != null&& ! conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
}

void bak_sql()
{
	
	  
//	  else {
//		  String insertSql = 
//					"INSERT INTO PHY_TERM_PORT_WHL ( PHY_PORT_ID, NO, RES_SPEC_ID, PHY_EQP_ID, UNIT_ID, UP_OR_DOWN_ID, OPR_STATE_ID, MNT_STATE_ID, IS_BACKUP_ID, POS_X, POS_Y, SEQ_IN_UNIT, SEQ_IN_EQP, ROW_NO, COLUMN_NO, CREATE_OP, CREATE_DATE, MODIFY_OP, MODIFY_DATE, START_DATE, LIMIT_PERIOD, LONG_OR_LOCAL_ID, NOTES, WO_ID, PARTITION_ID, IS_FAULT, PORT_TYPE_ID, IS_LOCKED )  " +
//					"VALUES ( ?, '/16/10', 361, 1232000000, 1233000011, 1, 170001, 170351, NULL, NULL, NULL, 10, 190, 1, 10, NULL" +
//					//", date_format('2012-08-14', '%Y-%m-%d')" +
//					",TO_DATE('2012/08/14 00:44:12', 'YYYY/MM/DD HH24:MI:SS')" +
//					", NULL, NULL, NULL, NULL, NULL, NULL, NULL, 20, '0', NULL, '0' )" +
//					""; 
//		  
//		  
//		  insertSql = " insert into test_whl (test_id,version)values(1,'')";
//		  
//		  insertSql = "  delete from SRV_WO_EVENT_OWNER as A where A.id=? and A.state_id=? AND A.owner=?";
//		  insertSql = "insert into test_whl select test_id,version from test_whl ";
//		  insertSql = "update test_whl set name = 2 where id = 3 limit 0";
//		  
//		pstmt = conn
//				.prepareStatement(insertSql);
////		pstmt.setLong(1, 1);
////		pstmt.setLong(2, 1);
////		pstmt.setLong(3, 1);
//		
//		//	 pstmt.executeUpdate("/*!cobar: $table='CM_DEVICE',$partitionOperand=('sharding_id'=['200'])*/ call TESTPROC(1)");
//	 
//	boolean re =  pstmt.execute();
//	System.out.print(re);
//	
//		
//	  }
	
	
//	 sql = "SELECT NAME FROM cm_device where sharding_id in (200,766) limit 3";
//	 sql = " SELECT avg(标称容量),设备类型 FROM (SELECT A.ID AS 设备ID,A.CODE AS 设备编码,A.NAME AS 设备名称,D.NAME AS 设备类型,A.ROW_NUM AS 行数,A.COL_NUM AS 列数,A.CAPACITY AS 标称容量 FROM CM_DEVICE A  LEFT JOIN MM_ENTITY_SPEC D ON D.ID=A.SPEC_ID WHERE  1=1  and A.SHARDING_ID in(200,766)) TEMP1 GROUP BY 设备类型 ORDER BY 设备类型 DESC";
//	 sql=" select sum(id) from cm_device where SHARDING_ID = 200 and SPEC_ID=1 " +
//	 		"  or SHARDING_ID = 766 and spec_id=1 and " +
//	 		"region_ID=2 group by id";
//	 sql="  select ID,name from CM_DEVICE WHERE SHARDING_ID= 766  and SPEC_ID=1020200004  or SHARDING_ID = ( 200) and spec_id=1020200004 and region_id=44200211100000000012 ";
//	 sql="  select sum(id)  from (select id,name from CM_DEVICE WHERE SHARDING_ID  = 766  and SPEC_ID=1  or SHARDING_ID = 200 and spec_id=1 and region_id=1)"
//			 +"group  by   id";
//	 
//	 sql="  select a.id from cm_device a where sharding_id in (200)";
//	 sql="  select val from tb3 where id =1";
//	 sql = "select getRoadIDByTopoRes(1) roadid from dual";
//	 sql = "select t1.id from (select id,val from tb2 ) t1 where t1.id = 1";
//	 sql = "select 1 from dual";
//	 sql = "select count(*) test,id from test_whl t where t.id in(200,766) group by id order by test";
//	 sql ="select a.id from test_whl a";
//	 sql = "delete * from cm_link a where a.id = 1111 and a.sharding_id=1";
	 // sql="select avg(10/4) ,1 from dual;";
	 //sql = "/*!cobar: $table='CM_DEVICE',$partitionOperand=('sharding_id'=['200'])*/select * from cm_device where id = 1";
	 
	 //sql = "select time_stamp from cm_device limit 1";
	
	
	 //sql = "select logon_time from v$session";
	 //sql="SELECT id FROM test_whl LIMIT 0";
	 }
}


