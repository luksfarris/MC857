package com.br.unicamp.mc857integralizacaodac.utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Disciplina;
import com.br.unicamp.mc857integralizacaodac.model.Historico;

public class Parser {

	public static Historico parseHistorico(String xml) {
		Historico hist = new Historico();
		try {

			File fXmlFile = new File("historico.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			
			hist.setDisciplinas(new ArrayList<Disciplina>());

			NodeList nList = doc.getElementsByTagName("disciplina");
			NodeList nHist = doc.getElementsByTagName("historico");
			hist.setCurso(Integer.valueOf(((Element)nHist.item(0)).getAttribute("curso")));
			hist.setNome((((Element)nHist.item(0)).getAttribute("nome")));
			hist.setRa(Integer.valueOf(((Element)nHist.item(0)).getAttribute("ra")));

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					Disciplina disc = new Disciplina();

					String cred = eElement.getAttribute("cred");
					String sigla = eElement.getAttribute("sigla");
					
					disc.setCredito(Integer.valueOf(cred));
					disc.setSigla(sigla);
					
					hist.getDisciplinas().add(disc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hist;
	}

	public static Catalogo parseCatalogo(String xml) {
		Catalogo cat = new Catalogo();
		try {

			File fXmlFile = new File("catalogo.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			
			cat.setDisciplinas(new ArrayList<Disciplina>());

			NodeList nList = doc.getElementsByTagName("disciplina");
			NodeList nCat = doc.getElementsByTagName("curso");
			cat.setCodigo(Integer.valueOf(((Element)nCat.item(0)).getAttribute("cod")));
			cat.setNome((((Element)nCat.item(0)).getAttribute("nome")));

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					Disciplina disc = new Disciplina();

					String sigla = eElement.getAttribute("sigla");
					
					disc.setSigla(sigla);
					
					cat.getDisciplinas().add(disc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cat;
	}

}
