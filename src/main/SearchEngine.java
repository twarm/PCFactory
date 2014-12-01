package main;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.lang.Boolean;

/**
 * Search Engine is used to deploy sqls in tables by giving an object. The class
 * name should be same as the table name and all declared public fields should
 * be exactly same and in the same order to the columns' names.
 * 
 * @author Jianyu Wang(@jw3236@drexel.edu)
 */
public class SearchEngine {
	private Connection conn = null;

	/**
	 * Get a list by giving an object. Empty object will return all entries in
	 * the table. Every non-empty attribute will be viewed as a search
	 * constraint.
	 * 
	 * @param obj
	 * @return
	 */
	public ArrayList getObject(Object obj) {
		ArrayList result = new ArrayList();
		try {
			String sql = getSearchSql(obj);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			Object o, var;
			Class type = obj.getClass();
			Field[] fields = type.getFields();
			while (rs.next()) {
				o = type.newInstance();
				for (int i = 0; i < fields.length; i++) {
					var = rs.getString(i + 1);
					if (var == null)
						continue;
					else {
						Class vartype = fields[i].getType();
						if (vartype == int.class)
							fields[i].setInt(o,
									Integer.parseInt(var.toString()));
						else if (vartype == double.class)
							fields[i].setDouble(o,
									Double.parseDouble(var.toString()));
						else if (vartype == Date.class) {
							fields[i].set(o, Date.valueOf(var.toString()));
						} else
							fields[i].set(o, var.toString());
					}
				}
				result.add(o);
			}
			statement.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateObject(Object obj) {
		String sql = getPrimaryKeySql(obj);
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			ArrayList list = new ArrayList();
			while (rs.next()) {
				Object o = rs.getString("cname");
				list.add(o);
			}
			rs.close();

			Class type = obj.getClass();
			Field[] fields = type.getFields();
			StringBuffer query = new StringBuffer();
			StringBuffer condition = new StringBuffer();
			query.append("UPDATE " + getSimpleName(type.getName()) + " SET ");
			condition.append(" WHERE 1=1 ");
			int b = 0;
			for (int i = 0; i < fields.length; i++) {
				if (exist(list, fields[i].getName())) {
					condition.append("AND " + fields[i].getName() + " = ");
					condition.append(getSqlValue(fields[i].get(obj)));
				} else {
					Object o = fields[i].get(obj);
					if (o != null) {
						Class tempt = fields[i].getType();
						if (tempt == double.class || tempt == int.class) {
							if (Double.parseDouble(o.toString()) != 0
									|| fields[i].getName().equals("isDel")) {
								if (b == 1) {
									query.append(", ");
								} else {
									b = 1;
								}
								query.append(fields[i].getName() + " = " + o);
							}
						} else {
							if (b == 1) {
								query.append(" AND ");
							} else {
								b = 1;
							}
							query.append(fields[i].getName() + " = '" + o + "'");
						}
					}
				}
			}
			sql = query.append(condition).toString();
			statement.execute(sql);
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean insertObject(Object obj) {
		String sql = getPrimaryKeySql(obj);
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			ArrayList list = new ArrayList();
			while (rs.next()) {
				list.add(rs.getObject("cname"));
			}
			rs.close();

			Class type = obj.getClass();
			Field[] fields = type.getFields();
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO " + getSimpleName(type.getName())
					+ " VALUES( ");
			for (int i = 0; i < fields.length; i++) {
				if (exist(list, fields[i].getName())) {
					continue;
				} else {
					query.append(getSqlValue(fields[i].get(obj)) + ",");
				}
			}
			query.deleteCharAt(query.length() - 1);
			query.append(")");

			sql = query.toString();
			statement.execute(sql);
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteObject(Object obj) {
		String sql = getPrimaryKeySql(obj);
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			ArrayList list = new ArrayList();
			while (rs.next()) {
				list.add(rs.getString("cname"));
			}
			rs.close();

			Class type = obj.getClass();
			Field[] fields = type.getFields();
			StringBuffer query = new StringBuffer();
			StringBuffer condition = new StringBuffer();
			query.append("UPDATE " + getSimpleName(type.getName()) + " SET ");
			condition.append(" WHERE 1=1 ");
			int b = 0;
			for (int i = 0; i < fields.length; i++) {
				if (exist(list, fields[i].getName())) {
					condition.append("AND " + fields[i].getName() + " = ");
					condition.append(getSqlValue(fields[i].get(obj)));
				} else if (fields[i].getName().equals("isdel")) {
					query.append(" isDel = 1 ");
					b = 1;
				}
			}
			sql = query.append(condition).toString();
			if (b == 1) {
				statement.execute(sql);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getPrimaryKeySql(Object obj) {
		StringBuffer builder = new StringBuffer();
		builder.append("SELECT cols.table_name, cols.column_name cname, cols.position position, cons.status, cons.owner");
		builder.append(" FROM all_constraints cons, all_cons_columns cols");
		builder.append(" WHERE cols.table_name = '"
				+ getSimpleName(obj.getClass().getName()).toUpperCase() + "'");
		builder.append(" AND cons.constraint_type = 'P'");
		builder.append(" AND cons.constraint_name = cols.constraint_name");
		builder.append(" AND cons.owner = cols.owner");
		builder.append(" ORDER BY cols.table_name, cols.position");
		return builder.toString();
	}

	public String getSearchSql(Object obj) {
		StringBuffer builder = new StringBuffer();
		StringBuffer condition = new StringBuffer();
		builder.append("SELECT  ");
		condition.append(" WHERE 1=1 ");
		Class type = obj.getClass();

		Field[] fields = type.getFields();
		for (int i = 0; i < fields.length; i++) {
			builder.append(fields[i].getName() + ",");
			Object o;
			try {
				o = fields[i].get(obj);
				if (o != null) {
					Class tempt = fields[i].getType();
					if (tempt == double.class || tempt == int.class) {
						if (Double.parseDouble(o.toString()) != 0
								|| fields[i].getName().equals("isDel"))
							condition.append(" AND " + fields[i].getName()
									+ " = " + o);

					} else {
						condition.append(" AND " + fields[i].getName() + " = '"
								+ o + "'");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		builder.deleteCharAt(builder.length() - 1);

		builder.append(" FROM " + getSimpleName(type.getName()));
		builder.append(condition);

		return builder.toString();
	}

	public String getSqlValue(Object obj) {
		Class type = obj.getClass();
		if (type == Integer.class || type == Double.class)
			return obj.toString();
		else
			return "'" + obj.toString() + "'";
	}

	public String getInsertSql(Object obj) {
		StringBuffer builder = new StringBuffer();
		Class type = obj.getClass();
		builder.append("INSERT INTO " + getSimpleName(type.getName())
				+ " VALUES (");
		Field[] fields = type.getFields();

		return builder.toString();
	}

	public String getSimpleName(String name) {
		String result = "";
		int flag = 0;
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) == '.' && i < name.length() - 1)
				flag = i + 1;
		}
		result = name.substring(flag);
		return result;
	}

	public boolean exist(ArrayList list, String s) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().equals(s)) {
				list.remove(i);
				return true;
			}
		}
		return false;
	}

	public Connection openDBConnection(String user, String pass, String SID,
			String host, int port) throws SQLException, ClassNotFoundException {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + SID;
		String username = user;
		String password = pass;
		Class.forName(driver);
		conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	public void closeDBConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void LoadConnection(Connection con) {
		conn = con;
	}

	public SearchEngine() {

	}

	public SearchEngine(Connection con) {
		conn = con;
	}
}
