package br.com.mc857.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnector {
	
	public static StringBuffer connect(String url) {
		StringBuffer histBuffer = null;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", "");

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			histBuffer = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				histBuffer.append(inputLine);
			}
			in.close();
		} catch(Exception e) {
			e.printStackTrace();

		}

		return histBuffer;
	}
	
}
