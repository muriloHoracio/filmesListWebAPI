/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global tr_selected */

function edit_movie(id, titulo, genero, numero, net, emcasa, watched) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
            if(this.readyState === 4 && this.status === 200){
                if(xhttp.responseText === "true"){
                    alert("Filme editado com sucesso!");
                    var fields = tr_selected.find("td");
                    var inputs = tr_selected.find("input");
                    fields[0].innerHTML = titulo;
                    fields[1].innerHTML = genero;
                    fields[2].innerHTML = numero;
                    inputs[1].checked = net;
                    inputs[2].checked = emcasa;
                    inputs[3].checked = watched;
                    hide_item_box();
                    clear_item_box();
                    search_movie(document.getElementById("search").value);
                } else {
                    alert("Erro ao editar filme!");
                }
            }
    };
    xhttp.open("POST","ListaFilmes",true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
    xhttp.send("reqType=edit&id="+id+"&titulo="+titulo+"&genero="+genero+"&numero="+numero+"&net="+net+"&emcasa="+emcasa+"&watched="+watched);
}