import org.openqa.selenium.remote.http.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpRequest {
	
	
	public static void main(String[] argsv) {
		String chave = "atnção";
		Response response;
		try {
			response = Jsoup.connect("https://pt.wikihow.com/wikiHowTo")
					.method(Method.GET)
					.data("search",chave)
					.execute();
		
		Document doc = response.parse();
		
		System.out.println(doc);
		System.out.println();
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		


		}
		

}
