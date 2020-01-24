import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.http.client.entity.InputStreamFactory;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
 

public class NLP {
	
	public static void main(String[] argvs ) {
		token("\r\n" + 
				"Entenda a diferença entre \"dash\" e \"splash\". Como a \"pitada\" nas receitas de comida, o \"dash\" equivale a uma quantidade bem pequena de ingrediente, usada apenas para dar um toque de sabor.[3] O \"splash\" equivale a uma quantidade um pouco maior do que o \"dash\", por isso você deve decidir por si mesmo o quanto usar.\r\n" + 
				"O \"splash\" é usado para ingredientes como granadina e curaçau blue.\r\n" + 
				"O \"dash\" para ingredientes mais \"fortes\" como molho inglês ou molho de pimenta.");
		
	}
	
	public static List<String> sentence(String texto) {
		List<String> listaSetencas = new ArrayList<String>();
		try {
			InputStream is = new FileInputStream("C:\\Programacao\\nlp\\trainBase\\pt-sent.bin");
			SentenceModel model = new SentenceModel(is);
			SentenceDetectorME sdetector = new SentenceDetectorME(model);
			String sentences[] = sdetector.sentDetect(texto);
			for (String str : sentences) {
				if(str!= null)
					listaSetencas.add(str);
			}
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaSetencas;
	}
	
	public static List<String> token(String texto) {
		List<String> listToken = new ArrayList<String>();
		InputStream modelIn = null;
		try {
			modelIn = new FileInputStream("C:\\Programacao\\nlp\\trainBase\\pt-token.bin");
			TokenizerModel model = new TokenizerModel(modelIn);
			TokenizerME tokenizer = new TokenizerME(model);
			String tokens[] = tokenizer.tokenize(texto);
			double tokenProbs[] = tokenizer.getTokenProbabilities();

			for (int i = 0; i < tokens.length; i++) {
				listToken.add(tokens[i]);
				//System.out.println(tokens[i] + "\t: " + tokenProbs[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
				}
			}
		}
		return listToken;
	}
	
	


}
