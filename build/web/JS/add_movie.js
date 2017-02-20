/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function add_movie(titulo, genero, numero, net, emcasa) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
            if(this.readyState === 4 && this.status === 200){
                if(xhttp.responseText === "true"){
                    alert("Filme adicionado com sucesso!");
                    hide_item_box();
                    clear_item_box();
                    films_count();
                } else {
                    alert("Erro ao adicionar filme!");
                }
            }
    };
    xhttp.open("POST","ListaFilmes",true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
    xhttp.send("reqType=add&titulo="+titulo+"&genero="+genero+"&numero="+numero+"&net="+net+"&emcasa="+emcasa);
}
