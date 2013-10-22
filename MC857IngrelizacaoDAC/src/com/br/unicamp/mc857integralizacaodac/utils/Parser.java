package com.br.unicamp.mc857integralizacaodac.utils;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.br.unicamp.mc857integralizacaodac.model.Catalogo;
import com.br.unicamp.mc857integralizacaodac.model.Disciplina;
import com.br.unicamp.mc857integralizacaodac.model.GrupoEletiva;
import com.br.unicamp.mc857integralizacaodac.model.Historico;
import com.br.unicamp.mc857integralizacaodac.model.Modalidade;

public class Parser {

	public static Historico parseHistorico(String xml) {
		Historico hist = new Historico();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));

			doc.getDocumentElement().normalize();

			hist.setDisciplinas(new ArrayList<Disciplina>());

			NodeList nList = doc.getElementsByTagName("disciplina");
			NodeList nHist = doc.getElementsByTagName("historico");
			hist.setCurso(Integer.valueOf(((Element)nHist.item(0)).getAttribute("curso")));
			hist.setNome((((Element)nHist.item(0)).getAttribute("nome")));
			hist.setModalidade((((Element)nHist.item(0)).getAttribute("modalidade")));
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
			return null;
		}

		return hist;
	}

	public static Catalogo parseCatalogo(String xml) {
		Catalogo cat = new Catalogo();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));

			doc.getDocumentElement().normalize();

			NodeList nCat = doc.getElementsByTagName("curso");

			cat.setCodigo(Integer.valueOf(((Element)nCat.item(0)).getAttribute("cod")));
			cat.setNome((((Element)nCat.item(0)).getAttribute("nome")));

			NodeList c = doc.getElementsByTagName("curso").item(0).getChildNodes();
			Node cx = doc.getElementsByTagName("curso").item(0).getFirstChild();

			for (int i=0;i<c.getLength();i++) {
				if (cx.getNodeType()==1 && cx.getNodeName().equals("disciplinas")) {
					if(cat.getDisciplinas() == null) cat.setDisciplinas(new ArrayList<Disciplina>());

					NodeList d = cx.getChildNodes();
					Node dx = cx.getFirstChild();

					for (int j=0;j<d.getLength();j++) {
						if(dx.getNodeType() == 1) {
							Disciplina disc = new Disciplina();

							String sigla = ((Element)dx).getAttribute("sigla");

							disc.setSigla(sigla);

							cat.getDisciplinas().add(disc);
						}

						dx = dx.getNextSibling();
					}

				} else if(cx.getNodeType()==1 && cx.getNodeName().equals("gruposEletivas")) {
					if(cat.getGrupos() == null) cat.setGrupos(new ArrayList<GrupoEletiva>());
					NodeList ge = cx.getChildNodes();
					Node gex = cx.getFirstChild();

					for (int j=0;j<ge.getLength();j++) {
						if(gex.getNodeType() == 1 && gex.getNodeName().equals("grupoEletivas")) {
							GrupoEletiva grupo = new GrupoEletiva();

							grupo.setCredito(Integer.valueOf(((Element)gex).getAttribute("cred")));

							NodeList e = gex.getChildNodes();
							Node ex = gex.getFirstChild();

							for (int k=0;k<e.getLength();k++) {
								if(ex.getNodeType() == 1 && ex.getNodeName().equals("disciplinas")) {
									if(grupo.getDisciplinas() == null) grupo.setDisciplinas(new ArrayList<Disciplina>());

									NodeList d = ex.getChildNodes();
									Node dx = ex.getFirstChild();

									for (int l=0;l<d.getLength();l++) {
										if(dx.getNodeType() == 1) {
											Disciplina disc = new Disciplina();

											String sigla = ((Element)dx).getAttribute("sigla");

											disc.setSigla(sigla);

											grupo.getDisciplinas().add(disc);
										}
										dx = dx.getNextSibling();
									}
								}
								ex = ex.getNextSibling();
							}
							cat.getGrupos().add(grupo);
						}
						gex = gex.getNextSibling();
					}
				} else if(cx.getNodeType()==1 && cx.getNodeName().equals("modalidades")) {
					if(cat.getModalidades() == null) cat.setModalidades(new ArrayList<Modalidade>());
					NodeList m = cx.getChildNodes();
					Node mx = cx.getFirstChild();

					for (int j=0;j<m.getLength();j++) {
						if(mx.getNodeType() == 1 && mx.getNodeName().equals("modalidade")) {
							Modalidade modalidade = new Modalidade();

							modalidade.setNome(((Element)mx).getAttribute("nome"));

							NodeList mo = mx.getChildNodes();
							Node mox = mx.getFirstChild();

							for (int k=0;k<mo.getLength();k++) {
								if(mox.getNodeType() == 1 && mox.getNodeName().equals("disciplinas")) {
									if(modalidade.getDisciplinas() == null) modalidade.setDisciplinas(new ArrayList<Disciplina>());

									NodeList d = mox.getChildNodes();
									Node dx = mox.getFirstChild();

									for (int l=0;l<d.getLength();l++) {
										if(dx.getNodeType() == 1) {
											Disciplina disc = new Disciplina();

											String sigla = ((Element)dx).getAttribute("sigla");

											disc.setSigla(sigla);

											modalidade.getDisciplinas().add(disc);
										}

										dx = dx.getNextSibling();
									}
								} else if(mox.getNodeType()==1 && mox.getNodeName().equals("gruposEletivas")) {
									if(modalidade.getGrupos() == null) modalidade.setGrupos(new ArrayList<GrupoEletiva>());
									NodeList ge = mox.getChildNodes();
									Node gex = mox.getFirstChild();

									for (int l=0;l<ge.getLength();l++) {
										if(gex.getNodeType() == 1 && gex.getNodeName().equals("grupoEletivas")) {
											GrupoEletiva grupo = new GrupoEletiva();

											grupo.setCredito(Integer.valueOf(((Element)gex).getAttribute("cred")));

											NodeList e = gex.getChildNodes();
											Node ex = gex.getFirstChild();

											for (int n=0;n<e.getLength();n++) {
												if(ex.getNodeType() == 1 && ex.getNodeName().equals("disciplinas")) {
													grupo.setDisciplinas(new ArrayList<Disciplina>());

													NodeList d = ex.getChildNodes();
													Node dx = ex.getFirstChild();

													for (int o=0;o<d.getLength();o++) {
														if(dx.getNodeType() == 1) {
															Disciplina disc = new Disciplina();

															String sigla = ((Element)dx).getAttribute("sigla");

															disc.setSigla(sigla);

															grupo.getDisciplinas().add(disc);
														}

														dx = dx.getNextSibling();
													}
												}

												ex = ex.getNextSibling();
											}

											modalidade.getGrupos().add(grupo);
										}

										gex = gex.getNextSibling();
									}
								}

								mox = mox.getNextSibling();
							}

							cat.getModalidades().add(modalidade);
						}

						mx = mx.getNextSibling();
					}
				} 

				cx=cx.getNextSibling();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cat;
	}

}
