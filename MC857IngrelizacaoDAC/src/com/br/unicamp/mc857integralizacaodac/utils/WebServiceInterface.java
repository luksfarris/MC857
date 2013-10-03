package com.br.unicamp.mc857integralizacaodac.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Historico;

public class WebServiceInterface {

	private String URL_BASE = "http://143.106.196.235:8080/MC857Servidor/";

	private Boolean validarIntegralizacao(List<Atribuicao> atribuicoes, String ra) {
		// TODO: implementar
		return true;
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
