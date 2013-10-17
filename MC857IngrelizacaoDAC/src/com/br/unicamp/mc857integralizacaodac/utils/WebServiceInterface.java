package com.br.unicamp.mc857integralizacaodac.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.text.TextUtils;
import android.util.Log;

import com.br.unicamp.mc857integralizacaodac.model.Atribuicao;
import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.google.gson.Gson;

public class WebServiceInterface {

	public Boolean validarIntegralizacao(Atribuicao atribuicao, String ra) {
		StringBuffer response = null;
		
		try {
			String url = Constants.URL_BASE + "valida?atribuicaoString=" + new Gson().toJson(atribuicao) + "&ra=" + ra;
			url = TextUtils.htmlEncode(url);
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
		return response.toString().equalsIgnoreCase("true");
	}

	public Catalogo requisitarCatalogo(String codigo) {
		Catalogo cat = null;
		StringBuffer response = null;
		
		try {
			String url = Constants.URL_BASE + "buscaCatalogo?cod=" + codigo;
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
			
			if (response != null) {
				cat = Parser.parseCatalogo(response.toString());
			}
			
		} catch (FileNotFoundException e) {
			// TODO: mostar o erro de conexao pro usuario
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		} catch (MalformedURLException e) {
			// TODO: mostar o erro de conexao pro usuario
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		} catch (IOException e) {
			// TODO: mostar o erro de conexao pro usuario
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		} catch (NullPointerException e) {
			// TODO: mostar o erro de conexao pro usuario
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		}
		
		return cat;
	}

	public Historico requisitarHistorico(String ra) {
		Historico hist = new Historico();
		StringBuffer response = null;
		
		try {
			String url = Constants.URL_BASE + "buscaHistorico?ra=" + ra;
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
			
			if (response != null) {
				hist = Parser.parseHistorico(response.toString());				
			}
			
		} catch (FileNotFoundException e) {
			// TODO: mostar o erro de conexao pro usuario
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		} catch (MalformedURLException e) {
			// TODO: mostar o erro de conexao pro usuario
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		} catch (IOException e) {
			// TODO: mostar o erro de conexao pro usuario
			Log.e("WebServiceInterface", "Erro de conexão.", e);
		}
		
		return hist;
	}


}
