package com.br.unicamp.mc857integralizacaodac.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.google.gson.Gson;

public class WebServiceInterface {

	private String URL_BASE = "http://ec2-54-200-200-56.us-west-2.compute.amazonaws.com:8080/MC857Servidor/";

	public Boolean validarIntegralizacao(Atribuicao atribuicao, String ra) {
		StringBuffer response = null;
		
		try {
			String url = URL_BASE + "valida?atribuicaoString=" + new Gson().toJson(atribuicao) + "&ra=" + ra;
			url = url.replace(" ", "%20");
			URL obj = new URL(url);
			
			if (android.os.Build.VERSION.SDK_INT < 14) {
				// ipv6 old android workaround
				obj = new URL("GET", "ec2-54-200-200-56.us-west-2.compute.amazonaws.com", 8080, "MC857Servidor/"+
						"valida?atribuicaoString=" + new Gson().toJson(atribuicao) + "&ra=" + ra);
			}
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", "");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response== null) {
			return null;
		}
		return response.toString().equalsIgnoreCase("true");
	}

	public Catalogo requisitarCatalogo(String codigo) {
		Catalogo cat = new Catalogo();
		StringBuffer response = null;
		
		try {
			String url = URL_BASE + "buscaCatalogo?cod=" + codigo;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", "");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		cat = Parser.parseCatalogo(response.toString());
		return cat;
	}

	public Historico requisitarHistorico(String ra) {
		Historico hist = new Historico();
		StringBuffer response = null;
		
		try {
			String url = URL_BASE + "buscaHistorico?ra=" + ra;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", "");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		hist = Parser.parseHistorico(response.toString());
		return hist;
	}


}
