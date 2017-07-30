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
 * Servlet implementation class Etapa4Servlet
 */
@WebServlet("/Etapa4Servlet")
public class Etapa4Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String 			result 		= null;
	JSONObject 		resultLista = null;
	JSONObject 		resulJson 	= null;
	ConvercaoJSON 	paraClasse 	= null;
	List<Senha> 	listaNova 	= null;
	PrintWriter 	out 		= null;
	JSONObject 		etapa1Json		= null;
	List<Integer> listaClasse	= null;
	
	long tempoGasto		= 0;
	long dividendo		= 0;
	long classe			= 0;
	int cont			= 0;
	int cont1			= 0;
	int cont2			= 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Etapa4Servlet() {
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
		paraClasse 			= new ConvercaoJSON();
		resultLista 		= new JSONObject();
		result 				= new ClientHttp().requestGetSEAT("http://seat.ind.br/processo-seletivo/desafio-2017-03.php?nome=thiago pereira de azara");
		

		
		listaClasse = new ArrayList<>();
		
		try {
			
			resulJson 		= new JSONObject(result);
			listaNova 		= paraClasse.converterParaClasse(resulJson.toString());
			
			for(int i =0; i< listaNova.size(); i++){
				
				tempoGasto = listaNova.get(i).getChamada() - listaNova.get(i).getEmissao();
				dividendo = tempoGasto;
				
				boolean completo = false;
				
				while(!completo) {
					
					classe = (long)dividendo/5;
					
					if(classe <= 18000){
						listaClasse.add(cont);
						cont = 0;
						completo = true;
						
					} else {
						dividendo = classe;
						cont++;
					}					
					
				}
				
			}
			
			for(int j = 0; j < listaClasse.size(); j++){
				
				System.out.println(listaClasse.get(j));
				
				for(int k = 0; k < listaClasse.size(); k++){
					if(listaClasse.get(j)== listaClasse.get(k)) {
						cont1++;
					}
				}
				
				
				System.out.println((listaClasse.get(j)*5)+"min: "+cont1);
							
							
				cont1 =0;				
			}
			
			Collections.sort(listaNova);
						
			etapa1Json 		= new JSONObject();
			etapa1Json.put("nome", "thiago pereira de azara");
			etapa1Json.put("chave", "150142851");
			etapa1Json.put("resultado", new Gson().toJson(listaNova));
			
			System.out.println(etapa1Json);
			
			String retorno = new ClientHttp().requestPostSEAT("http://seat.ind.br/processo-seletivo/desafio-post-2017-03.php", etapa1Json);
			
			out.println("{'mesagem':'"+retorno+"'}");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
