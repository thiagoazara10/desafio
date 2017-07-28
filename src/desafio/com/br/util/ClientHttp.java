package desafio.com.br.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ClientHttp {
		
	public String requestGetSEAT(String urlToRead) throws ClientProtocolException, IOException{

		StringBuilder result = new StringBuilder();
	    URL url = new URL(urlToRead);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = rd.readLine()) != null) {
	         result.append(line);
	    }
	    rd.close();
	return result.toString();
}
	
	
	public String requestPostSEAT(String url, String paramNome, String paramChave){
		
		if(url.length()==0 && paramNome.length() == 0 && paramChave.length() == 0) return "Parametro inválido.";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", "SEAT");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("nome", paramNome));
		urlParameters.add(new BasicNameValuePair("chave", paramChave));
		//urlParameters.add(new BasicNameValuePair("locale", ""));
		//urlParameters.add(new BasicNameValuePair("caller", ""));
		//urlParameters.add(new BasicNameValuePair("num", "12345"));

		try {
			
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse response = client.execute(post);
			System.out.println("Response Code : "
			                + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
			        new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		
			return result.toString();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "erro1";
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "erro2";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "erro3";
		}

	
	}
	
	
	public static void main(String args[]) {
				
		//variaveis
		JSONObject 		resulJson;
		JSONArray		listaJson;
		String 			result;
		
		
		try {
			//requisição via GET
			result = new ClientHttp().requestGetSEAT("http://seat.ind.br/processo-seletivo/desafio-2017-03.php?nome=thiago pereira de azara");
			
			//resultado obtido em JSON
			resulJson = new JSONObject(result);
			
			//instanciar
			listaJson = new JSONArray();
			
			//obter lista do array
			listaJson = resulJson.getJSONArray("input");

			
			//para teste
			System.out.println("Tamanho: "+listaJson.length());
			
			System.out.println("Posição qualquer: "+listaJson.optJSONObject(0));
			
		} catch (IOException e) {
			// Erro para tratar requisição via GET
			e.printStackTrace();
		} catch (JSONException e) {
			// Erro para tratar conversão com JSON
			e.printStackTrace();
		}
		
	}
	
	

}
