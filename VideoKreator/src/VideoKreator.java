import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VideoKreator {
	
	static WebDriver firefox = null;
	private static String inicial;
	private static String linkFixo;
	private static String tituloPricipal;
	private static Elements sessoes;
	private static HashMap<Integer, String> lsImagensTempo;
	
	//Solicitar pesquisa
	//Pesquisar
	//Tratar resultado
	//Coletar imagens
	//Redenrizar
	//Organizar paragrafos por imagem
	//Juntar imagens no video
	//Colocar legendas
	//Colocar audio
	public static void main(String[] args) {
		solicitarPesquisa();
		
		
	}

	private static void solicitarPesquisa() {
		try {
		   Scanner ler = new Scanner(System.in); 
		   System.out.println("Informe sua pesquisa:");
		   String pesquisa = ler.next();
			 ler = new Scanner(System.in); 
		   Map<String, String> lsPesquisa = pesquisar(pesquisa);
		   Map<Integer, String> mapTitulos = listarResultadoPesquisa(lsPesquisa);
		   System.out.println("Para qual pesquisa deseja fazer o video:");
		   System.out.println("0 - Todos os Títulos");
		   for(Integer indice : mapTitulos.keySet())
			   System.out.println(indice +" - "+mapTitulos.get(indice));
		   int indiceEscolhido = ler.nextInt();
			if(indiceEscolhido==0) {
				
			}else {
				pesquisarIndiceEscolhido(lsPesquisa.get((mapTitulos.get(indiceEscolhido))));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void pesquisarIndiceEscolhido(String link) {
		linkFixo = link;
		try {
			Response response = Jsoup.connect(link).method(Method.GET).execute();
			List<Map<String, List<String>>> lista = extract(response.parse());
			criarVideo(lista);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void criarVideo(List<Map<String, List<String>>> lista) {
		//Imprime
		for(Map<String, List<String>> map : lista) {
			for(String str : map.keySet()) {
			//	String link = linkFixo +"/" +str;
				String texto = map.get(str).get(0); // Verificar se tem mais além do zero
				putLegendaImagem(str, texto);
			}
		}
		
		
	}

	private static void putLegendaImagem(String link, String texto) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new URL(link));
			Graphics g = image.getGraphics();
			putLegendaComBorda(g, texto, image);
			

			System.out.println(texto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block

	}

	private static Color getCorNegativa(BufferedImage image) {
		int neg = (0xFFFFFF - image.getRGB( getMargemInicioLegenda(image.getWidth()), getAlturaLegenda(image.getHeight(), 1))) | 0xFF000000;
		
		Color mycolor = new Color(neg);
		return mycolor;
	}

	private static void putLegendaComBorda(Graphics g, String texto, BufferedImage image) {
		try {
		List<String> ls = NLP.sentence(texto); 
		int desconto = texto.length()*10;
		Integer tamanhoFonte = 20;
		int linha = 0;
		for(String str : ls) {
			legendaComBorda(g, str, image, desconto, tamanhoFonte, linha);
			g.dispose();
			String nome = getNomeImagem(texto);
			ImageIO.write(image, "png", new File(nome+".png"));
			linha = linha+1;
		}
		} catch (Exception e) {
			System.out.println(e);		}
		
	}

	private static String getNomeImagem(String texto) {
		if(lsImagensTempo != null || lsImagensTempo.isEmpty()) {
			lsImagensTempo.put(1, texto);
		}else {
			
		}
			
		
		return lsImagensTempo.get(lsImagensTempo.size());
	}

	private static void legendaComBorda(Graphics g, String texto, BufferedImage image, int desconto, Integer tamanhoFonte, int linha) {
				g.setFont(new Font("Helvetica", Font.BOLD, tamanhoFonte));
				g.setColor(getCorNegativa(image));
				g.drawString(texto, getMargemInicioLegenda(image.getWidth()-1, desconto), getAlturaLegenda(image.getHeight(), linha));
				g.drawString(texto, getMargemInicioLegenda(image.getWidth()+1, desconto), getAlturaLegenda(image.getHeight(), linha));
				g.drawString(texto, getMargemInicioLegenda(image.getWidth(), desconto), getAlturaLegenda(image.getHeight(), linha)-1);
				g.drawString(texto, getMargemInicioLegenda(image.getWidth(), desconto), getAlturaLegenda(image.getHeight(), linha)+1);
				g.setFont(new Font("Helvetica", Font.BOLD, tamanhoFonte));
				g.setColor(Color.yellow);
				g.drawString(texto, getMargemInicioLegenda(image.getWidth(), desconto), getAlturaLegenda(image.getHeight(), linha));
				
	}
	private static int getMargemInicioLegenda(int largura) {
		return getMargemInicioLegenda(largura, 0);
	}


	private static int getMargemInicioLegenda(int largura, int desconto) {
		largura = largura-desconto;
		return largura*50/100;
	}

	private static int getAlturaLegenda(int altura, int linha) {
 		int multiplicador = 5 * linha;
		return altura * (80 + multiplicador) /100;
	}

	private static String getLinkReal(String link) {
		try {
			Response response = Jsoup.connect(link).method(Method.GET).execute();
			Document doc = response.parse();
			System.out.println(doc);
			String str = doc.select("div[class=img-container]").get(0).text();
			System.out.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String salvarIagem(String link) {
		try {
		URL url = new URL(link);
		String caminho = link.split("Imagem:")[1];
		InputStream in = new BufferedInputStream(url.openStream());
		OutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Hall\\Documents\\"+caminho));

		for ( int i; (i = in.read()) != -1; ) {
		    out.write(i);
		}
		in.close();
		out.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return link;
	}

	private static List<Map<String, List<String>>> extract(Document pagina) {
		try {
			Element body = pagina.select("#bodycontents").get(0);
			
			List<Map<String, List<String>>> ls = new ArrayList<Map<String,List<String>>>(); 
			
			Elements anchor = body.select("a[class=anchor]").parents();
			
			Map<String, List<String>> imagensTexto = new LinkedHashMap<String, List<String>>();
			for (Element el : anchor) {
				imagensTexto = new LinkedHashMap<String, List<String>>();
				Elements ancoras = el.select("a[class=stepanchor]");
				for (Element el2 : ancoras) {
					List<String> lsImagem = new ArrayList<String>();

					String texto = el2.nextElementSiblings().text();

					String enderecoImagem = el2.parent().select("img").attr("src");
					if (imagensTexto.get(enderecoImagem) == null) {
						lsImagem.add(texto);
						imagensTexto.put(enderecoImagem, lsImagem);
					} else {
						imagensTexto.get(enderecoImagem).add(texto);
					}
					
				}
				if(imagensTexto!= null && !imagensTexto.isEmpty())
					ls.add(imagensTexto);
			}
			
			//Imprime
//			for(Map<String, List<String>> map : ls) {
//				for(String str : map.keySet()) {
//					System.out.println(linkFixo +"/" +str);
//					System.out.println(map.get(str));
//				}
//			}
			
			 tituloPricipal = body.select("h1[class=firstHeading]").get(0).text();
			 sessoes = body.select("p[class=sp_method_toc]").select("a");

			Element cabecalho = body.select("div[id=intro]").get(0);
			cabecalho.removeClass("sp_method_toc");
			cabecalho.removeClass("firstHeading");
			cabecalho.select("div[id=expert_coauthor]").remove();
			cabecalho.select("div[id=sp_icon_hover]").remove();
			
			cabecalho.select("div[id=sp_icon_body]").remove();
			String textoInicial = cabecalho.text();
			if(textoInicial!= null)
				inicial = textoInicial;
			
			return ls;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static Map<Integer, String> listarResultadoPesquisa(Map<String, String> mapPesquisa) {
		int count = 1;
		Map<Integer, String> mapTitulos = new HashMap<Integer, String>();
		for(String titulo : mapPesquisa.keySet()) {
			mapTitulos.put(count, titulo);
			count++;
		}
		return mapTitulos;
	}

	private static Map<String, String> pesquisar(String pesquisa) {
		Response response;
		try {
		 response = Jsoup.connect("https://pt.wikihow.com/wikiHowTo")
					.method(Method.GET)
					.data("search",pesquisa)
					.execute();
		 return getTitulosLinks(response.parse());
		}catch (Exception e) {
				e.printStackTrace();
		}
		return null;
	}

	private static Map<String, String> getTitulosLinks(Document doc ) {
		Map<String, String> mapResultado = new HashMap<String, String>();
		Elements resultados  = doc.select("a[class=result_link]");
		resultados.remove(0);
		for(Element resultado : resultados) {
			String titulo = resultado.select("div[class=result_title]").text();
			String link = resultado.select("a").attr("href");
			if(titulo!= null && link != null)
				mapResultado.put(titulo, link);
		}
		return mapResultado;
	}
	
	
}
