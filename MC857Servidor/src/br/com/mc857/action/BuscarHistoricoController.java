package br.com.mc857.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

public class BuscarHistoricoController  extends ActionSupport{

	private final String URL_BASE = "http://localhost:81//";

	private String ra;

	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}

	@Action(value = "buscaHistorico", results = {
			@Result( type="stream", name=SUCCESS)}
			)
	public String buscaHistorico() {
		String url = "?serv=h&ra="+ra;
		StringBuffer response;

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", "");

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch(Exception e) {
			e.printStackTrace();

			return ERROR;
		}

		inputStream = new StringBufferInputStream(
				response.toString());
		return SUCCESS;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

}
