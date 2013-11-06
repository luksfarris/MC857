package com.br.unicamp.mc857integralizacaodac.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.google.gson.Gson;

public class WebServiceInterface {

	private String URL_BASE = "http://ec2-54-200-200-56.us-west-2.compute.amazonaws.com:8080/MC857Servidor/";

	public Boolean validarIntegralizacao(Atribuicao atribuicao, String ra) {
		StringBuffer response = null;
		
		try {
			String path = "valida?atribuicaoString=" + new Gson().toJson(atribuicao) + "&ra=" + ra;
			path = path.replace(" ", "%20");
			String url = URL_BASE + path;
			URL obj = new URL(url);
			
			if (android.os.Build.VERSION.SDK_INT < 14) {
				// ipv6 old android workaround
				obj = new URL("http", "ec2-54-200-200-56.us-west-2.compute.amazonaws.com", 8080, "/MC857Servidor/"+path);
				url = obj.toString();
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
			
		} catch (FileNotFoundException e) {
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		} catch (MalformedURLException e) {
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		} catch (IOException e) {
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		} catch (NullPointerException e) {
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		}

		if(response == null) {
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
		
		if (response != null) {
			cat = Parser.parseCatalogo(response.toString());
		}
		
		
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
		
		if (response != null){
			hist = Parser.parseHistorico(response.toString());
		}
		return hist;
	}


}
