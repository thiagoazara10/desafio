package desafio.com.br.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import desafio.com.br.modelo.Senha;


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
	
	
	
	public String requestPostSEAT1(String url, JSONObject paramJson){
		
		System.out.println("Cliete HTTP -> "+paramJson);
		
		HttpResponse  response 		= null;
		String       postUrl       	= url;// put in your url
		Gson         gson          	= new Gson();
		HttpClient   httpClient   	= HttpClientBuilder.create().build();
		HttpPost     post          	= new HttpPost(postUrl);
		StringEntity postingString 	= null;
		StringBuffer result 		= null;

		try {
			postingString = new StringEntity(paramJson.toString());
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			response = httpClient.execute(post);
			
			BufferedReader rd = new BufferedReader(
			        new InputStreamReader(response.getEntity().getContent()));
			
			
			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}
	public String requestPostSEAT(String url, JSONObject paramJson){
		
		
		
		try {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		System.out.println("Cliete HTTP -> "+paramJson.getLong("chave"));
		//String de retorno
		String resultCode = "";

		// add header
		post.setHeader("User-Agent", "SEAF");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		//está sendo fixo
		
		urlParameters.add(new BasicNameValuePair("nome", paramJson.getString("nome")));		
		urlParameters.add(new BasicNameValuePair("chave", paramJson.getString("chave")));
		urlParameters.add(new BasicNameValuePair("resultado", paramJson.getString("resultado")));
	
		//urlParameters.add(new BasicNameValuePair("caller", ""));
		//urlParameters.add(new BasicNameValuePair("num", "12345"));

		StringBuffer result = null;		
		
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse response = client.execute(post);
			
			//recebe o resultado
			resultCode = response.getStatusLine().getStatusCode()+"";
			
			//verificar a saida do retorno HTTP
			System.out.println("Retorno HTTP : " + resultCode);

			BufferedReader rd = new BufferedReader(
			        new InputStreamReader(response.getEntity().getContent()));

			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			//testar saida
			//System.out.println("Aqui é o result do Márcio: "+result);
			
			//verificar se a resposta do HTTP para alterar o retorno e ser tratado pelo JSON
			if (!resultCode.equalsIgnoreCase("200")) {
				result.setLength(0);
				result.append("{\"result\":10000}");
			}
	
			return result.toString();	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
///////////////////////////////////////	
 

///////////////////////////////////////
	
	
	
	
	
	public static void main(String args[]) {
				
		//variaveis
		JSONObject 		resulJson;
		JSONArray		listaJson;
		String 			result;
		ConvercaoJSON 	convercao = new ConvercaoJSON();
		
		try {
			//requisição via GET
			result = new ClientHttp().requestGetSEAT("http://seat.ind.br/processo-seletivo/desafio-2017-03.php?nome=thiago pereira de azara");
			System.out.println(result);
			//resultado obtido em JSON
			resulJson = new JSONObject(result);
			
			//instanciar
			listaJson = new JSONArray();
			
			//obter lista do array
			listaJson = resulJson.getJSONArray("input");

			System.out.println("Outro tamanho: "+convercao.converterParaClasse(resulJson.toString()).size());
			
			ArrayList<Senha> n = new ArrayList<Senha>();
			n = (ArrayList<Senha>) convercao.converterParaClasse(resulJson.toString());
			Collections.sort(n);
			
			for(int i = 0; i < n.size(); i++){
				System.out.println(n.get(i).getEmissao());
			}
			
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
