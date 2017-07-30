package desafio.com.br.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import desafio.com.br.modelo.Senha;
import desafio.com.br.util.ClientHttp;
import desafio.com.br.util.ConvercaoJSON;

/**
 * Servlet implementation class Etapa3Servlet
 */
@WebServlet("/Etapa3Servlet")
public class Etapa3Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
		
	String 			result 		= null;
	JSONObject 		resultLista = null;
	JSONObject 		resulJson 	= null;
	ConvercaoJSON 	paraClasse 	= null;
	List<Senha> 	listaNova 	= null;
	List<Senha> 	listaNova1 	= null;
	PrintWriter 	out 		= null;
	JSONObject 		etapa1Json		= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Etapa3Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		out 				= response.getWriter();
		listaNova 			= new ArrayList<Senha>();
		listaNova1 			= new ArrayList<Senha>();
		paraClasse 			= new ConvercaoJSON();
		resultLista 		= new JSONObject();
		result 				= new ClientHttp().requestGetSEAT("http://seat.ind.br/processo-seletivo/desafio-2017-03.php?nome=thiago pereira de azara");
		int cont 			= 0;
		long sub			= 0;
		long soma			= 0;
		long media			= 0;
		
		try {
			
			resulJson 		= new JSONObject(result);
			listaNova 		= paraClasse.converterParaClasse(resulJson.toString());
			
			//Etapa 2
			for(int i = 0; i < listaNova.size() ; i++){
				
				//add quantida de pessoas na frente
				listaNova.get(i).setNaFrente(i);
				
				listaNova1.add(listaNova.get(i));
				
			}
			
			//Etapa 3
			//realizar o calculo de tempo de espera de cada um
			for(int i = 0; i < listaNova.size(); i++){
				
				if(listaNova.get(i).getChamada()!= 0 && listaNova.get(i).getEmissao()!=0) {
					cont++;
					sub = listaNova.get(i).getChamada() - listaNova.get(i).getEmissao();
					soma = soma + sub;
				} else {
					//esse else é para completar senhas que ainda não foi chamados
					//nesses casos, o valor será de um atendimento anterior para ser preenchido
					//e gerar um valor para media.
					cont++;
					soma = soma + sub;
				}
				
			}
			//Etapa3 também
			//obter a media
			media = (long)soma/cont;
			
			for(int i = 0; i < listaNova.size() ; i++){
				
				//add quantida de pessoas na frente
				if(i==0){
					listaNova.get(i).setEspera(0);
				} else {
				
					listaNova.get(i).setEspera(media);
				
					//lista nova com 
					listaNova1.add(listaNova.get(i));
				
					//incrementa uma media de cada atendimento.
					media = media + media;
				}
				
			}
			//Etapa 1
			Collections.sort(listaNova);
						
			etapa1Json 		= new JSONObject();
			etapa1Json.put("nome", "thiago pereira de azara");
			etapa1Json.put("chave", "150142851");
			etapa1Json.put("resultado", new Gson().toJson(listaNova));
			
			System.out.println(etapa1Json);
			
			String retorno = new ClientHttp().requestPostSEAT("http://seat.ind.br/processo-seletivo/desafio-post-2017-03.php", etapa1Json);
		//	String retorno = "sucesso";
			out.println("{'mesagem':'"+retorno+"'}");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
