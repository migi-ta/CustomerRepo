package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBconfig;
import dto.LoginUser;

public class SqlDao {
		//DBconfig.propertiesのフルパス
		public final String file_path = "/Applications/Eclipse_2020-12.app/Contents/workspace/CustomerRepo/WebContent/DBconfig.properties";
		//DBconfigのオブジェクト生成
		DBconfig config = new DBconfig();

		//ログイン認証のアクセスロジック(DAO)
		public List<LoginUser> check(String user, String password) throws IOException {
			//DBconfig.propertiesの各値をlist形式で取得
			String[] DbInfo = config.getDBinfo(file_path);

			//DBconfig.propertiesのurlを設定
			String url = DbInfo[0];
			//DBconfig.propertiesのuserを設定
			String db_user_name = DbInfo[1];
			//DBconfig.propertiesのpasswordを設定
			String db_password = DbInfo[2];

			String sql = "select * from login_user_tb "
					+ "where name = ? and password = ?";

			//ログインユーザのオブジェクト生成(DTO)
			LoginUser login_user = new LoginUser();
			//ログインユーザの情報をリスト形式で取得する
			List<LoginUser> user_info = new ArrayList<LoginUser>();

			//データベースへの接続
			try(Connection conn = DriverManager.getConnection(url,db_user_name,db_password)){
				PreparedStatement stmt = conn.prepareStatement(sql);
				//変数sqlの一番目の?に引数のuserをセットする
				stmt.setString(1, user);
				//変数sqlの二番目の?に引数のpasswordをセットする
				stmt.setString(2, password);
				//sqlを実行し該当するデータ格納
				ResultSet rs = stmt.executeQuery();

				if(rs.next()) {
					login_user.setId(rs.getInt("id"));
					login_user.setName(rs.getString("name"));
					login_user.setPassword(rs.getString("password"));
					user_info.add(login_user);
				} else {
					login_user.setName("No user");
					login_user.setPassword("Not match password");
					user_info.add(login_user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return user_info;
		}

}
