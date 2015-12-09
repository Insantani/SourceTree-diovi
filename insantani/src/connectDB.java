import java.sql.*;

public class connectDB {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public connectDB(){
		try { 
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/insantani", "root", "");
			st = con.createStatement();
		} catch(Exception ex){
			System.out.println("Error: " + ex);
		}
	}
	
	public void getProduct() {
		try {
			String query = "select * from product";
			rs = st.executeQuery(query);
			System.out.println("Product Records");
			while(rs.next()){
				String product_name = rs.getString("product_name");
				String farmer_name = rs.getString("farmer_name");
				String prod_price = rs.getString("prod_price");
				String stock_num = rs.getString("stock_num");
				String prod_desc = rs.getString("prod_desc");
				String nutri_facts = rs.getString("nutri_facts");
				System.out.println("Product Name:" + product_name + "\n"+
								   "Farmer Name: " + farmer_name + "\n" +
								   "Product Price:" + prod_price + "\n" + 
								   "Current Stock:" + stock_num + "\n" + 
								   "Description:" + prod_desc + "\n" + 
								   "Nutrition Facts:" + nutri_facts + "\n");
			}
		} catch (Exception ex){
			System.out.println(ex);
		}
	}

}
