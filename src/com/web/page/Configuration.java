package com.web.page;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置类 初始化配置
 * 
 * @author Administrator
 * 
 */
public class Configuration {
	private static int pageSize;// 每页记录数
	private static int frontPage;// 显示前页
	private static int afterPage;// 显示后页

	static {
		InputStream in = Configuration.class.getClassLoader().getResourceAsStream(
				"default.properties");
		Properties props = new Properties();
		try {
			// 1，读取配置文件
			props.load(in);

			// 2，初始化配置
			pageSize = Integer.parseInt(props.getProperty("pageSize"));
			frontPage = Integer.parseInt(props.getProperty("frontPage"));
			afterPage = Integer.parseInt(props.getProperty("afterPage"));

			System.out.println("------- 配置文件加载完毕 ------");
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	private Configuration() {
	}

	public static int getPageSize() {
		return pageSize;
	}

	public static int getFrontPage() {
		return frontPage;
	}

	public static int getAfterPage() {
		return afterPage;
	}

}
