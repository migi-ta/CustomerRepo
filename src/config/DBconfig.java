package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DBconfig {

	public String[] getDBinfo(String file_path) throws FileNotFoundException {

		Properties  info = new Properties();
		FileInputStream file_stream = null;

		try {
			file_stream = new FileInputStream(file_path);
			info.load(file_stream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//DBconfig.propertiesのキーから値を取得する
		String url = info.getProperty("url");
		String user = info.getProperty("user");
		String password = info.getProperty("password");

		//DBconfig.propertiesの値を配列に格納しておく
		String[] db_info = {url,user,password};

		//getDBinfoメソッドが呼び出された際に
		//『接続情報、ユーザ名、パスワード』の情報を返す
		return db_info;
	}
}

