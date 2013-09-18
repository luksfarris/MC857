package br.com.mc857.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

public class BuscarCatalogoController extends ActionSupport{
	
	private final String URL_BASE = "http://localhost:80//";
	
	private String cod;
	
	  private InputStream inputStream;
	  public InputStream getInputStream() {
	    return inputStream;
	   }
	
	@Action(value = "buscaCatalogo", results = {
			@Result( type="stream", name=SUCCESS)}
	)
	public String buscaCatalogo(){
		String url = URL_BASE + "?serv=c&cod="+cod;
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

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

}
