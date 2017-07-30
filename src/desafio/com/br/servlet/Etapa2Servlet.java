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
 * Servlet implementation class Etapa2Servlet
 */
@WebServlet("/Etapa2Servlet")
public class Etapa2Servlet extends HttpServlet {
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
    public Etapa2Servlet() {
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
		listaNova1			= new ArrayList<Senha>();
		paraClasse 			= new ConvercaoJSON();
		resultLista 		= new JSONObject();
		result 				= new ClientHttp().requestGetSEAT("http://seat.ind.br/processo-seletivo/desafio-2017-03.php?nome=thiago pereira de azara");
		
		try {
			
			resulJson 		= new JSONObject(result);
			listaNova 		= paraClasse.converterParaClasse(resulJson.toString());
			
			Collections.sort(listaNova);
			
			for(int i = 0; i < listaNova.size() ; i++){
				
				//add quantida de pessoas na frente
				listaNova.get(i).setNaFrente(i);
				
				listaNova1.add(listaNova.get(i));
				
			}
						
			etapa1Json 		= new JSONObject();
			etapa1Json.put("nome", "thiago pereira de azara");
			etapa1Json.put("chave", "150142851");
			etapa1Json.put("resultado", new Gson().toJson(listaNova1));
			
			System.out.println(etapa1Json);
			
			String retorno = new ClientHttp().requestPostSEAT("http://seat.ind.br/processo-seletivo/desafio-post-2017-03.php", etapa1Json);
			//String retorno = "ok";
			out.println("{'mesagem':'"+retorno+"'}");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
