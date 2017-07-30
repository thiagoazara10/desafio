package desafio.com.br.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import desafio.com.br.modelo.Senha;

public class ConvercaoJSON {
	
	public List<Senha> converterParaClasse(String listaJson1){
		
		List<Senha> listaDeSenhas = new ArrayList<Senha>();
		
		JSONArray		listaJson 	= new JSONArray();
		
		try {
			
			JSONObject 		resultLista = new JSONObject(listaJson1);
			
			listaJson = resultLista.getJSONArray("input");
			
			//Gson gson = new Gson();
			for(int i = 0; i < listaJson.length(); i++){
				listaDeSenhas.add(new Gson().fromJson(listaJson.optJSONObject(i).toString(), Senha.class));
			}
			
			return listaDeSenhas;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
}
