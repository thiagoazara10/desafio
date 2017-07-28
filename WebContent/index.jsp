<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sistema de Atendimento</title>

<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>

</head>
<body>

		<form id="atendimento" method="post">
			
			<label id="senha1"> 		Número de atendimento:: 			</label>
			<label id="senha"> 												</label><br><br>
			<label id="emissao1"> 		Emissão: 							</label>
			<label id="emissao"> 											</label><br><br>
			<label id="prioridade1"> 	Prioridade de atendimento:			</label>
			<label id="prioridade"> 										</label><br><br>
			<label id="tamanho1"> 		Quantidade de pessoa(s) em espera:	</label>
			<label id="tamanho"> 											</label><br><br>
			<input type="submit" value="Chamar próximo usuário"/>
		</form>
	
		<script>
			$(document).ready(function(){		
				$('#atendimento').on('submit', function(e){
					var formData = {
	       				'atendimento' 			: '1'	       						
	    			};
					
					console.log(formData);
    				e.preventDefault(); //necessário, esqueci o que é
   					$.ajax({
        				url: 	'atendimentoServlet',
        				method: 'POST',	       				
        				data: 	formData,
        				success: function(data) {
        					console.log("sucesso na requisição !"+data);
            				stringValueJson = JSON.parse(data);//aqui consigo pegar cada campo
            				
            				document.getElementById("tamanho").innerHTML = stringValueJson.tamanho;
            				
            				document.getElementById("senha").innerHTML = stringValueJson.usuario.senha;
            				document.getElementById("emissao").innerHTML = stringValueJson.usuario.emissao;
            				document.getElementById("prioridade").innerHTML = stringValueJson.usuario.prioridade;
            				           				
                 		},
    					error: function(){
    			
    						console.log("erro ao enviar a servlet !");
    						document.getElementById("info").innerHTML += "Erro ao iniciar visualizador de log! \n";
 						}                   
    				});//ajax
				});//onsubmit
			});//verifica se está tudo OK antes de executar javascript
  	 </script>

</body>
</html>