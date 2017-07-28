package desafio.com.br.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import desafio.com.br.util.ClientHttp;

/**
 * Servlet implementation class AtendimentoServlet
 */
@WebServlet("/AtendimentoServlet")
public class AtendimentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int controle 					   = 0;
	private int proximo 					   = 0;
	PrintWriter out 						   = null;
	
	//resultado da fila obtido
	JSONObject 		resultLista = new JSONObject();
	
	//variaveis
	JSONObject 		resulJson 	= null;
	JSONArray		listaJson 	= new JSONArray();
	String 			result 		= null;
      

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AtendimentoServlet() {
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
		
		//obter 1 como incremento e decremento
		int atendimento = Integer.parseInt(request.getParameter("atendimento").toString()) ;
		
		//tratar a resposta a ser enviado
		out = response.getWriter();
			
		try {
			//aqui só vou entrar uma vez para fazer requisição, entro de novo quando a fila estiver vazia.
			if(controle==0){				
					//requisição via GET
					result 			= new ClientHttp().requestGetSEAT("http://seat.ind.br/processo-seletivo/desafio-2017-03.php?nome=thiago pereira de azara");
														
					//resultado obtido em JSON
					resulJson 		= new JSONObject(result);
					
					//obter lista do array com as senhas
					listaJson = resulJson.getJSONArray("input");
					
					//controle da lista de senhas para ser chamado.
					controle 		= listaJson.length()-1;
					proximo 		= 0;
					
					//para teste
					System.out.println("Tamanho: "+listaJson.length());					
					System.out.println("Posição qualquer: "+listaJson.optJSONObject(0));
									
			}else {
				//aqui entro todas as vezes enquanto estiver usuários na fila.
				System.out.print("Controle da variaveis.");
				controle 	= controle 	- atendimento;
				proximo 	= proximo 	+ atendimento;
				
				System.out.println("Proximo: "+proximo);						
			}
			
			//verifica se teve requisição para próximo atendimento.
			if(atendimento==1) {
;
				System.out.println("Tamanho: "+listaJson.length());
				
				//validar se existe senha na lista
				if(listaJson.optJSONObject(proximo)!=null) {
					//criar instancia nova aqui sempre que o metodo for chamado, garantir nova entrega.
					JSONObject usuario = new JSONObject(listaJson.optJSONObject(proximo).toString());
					
					//add a senha no JSON
					resultLista.put("tamanho", controle);
					resultLista.put("usuario", usuario);
					
					//add para o ajax pegar
					out.println(resultLista);
					
				} else {
					//tratar o erro
					out.println("{'mesagem':'objeto null'}");
				}				
							
			}
		} catch (IOException e) {
			// Erro para tratar requisição via GET
			e.printStackTrace();
		} catch (JSONException e) {
			// Erro para tratar conversão com JSON
			e.printStackTrace();
		}
	}//método post
}
