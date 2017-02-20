/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function delete_movie(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
            if(this.readyState === 4 && this.status === 200){
                if(xhttp.responseText === "true"){
                    alert("Filme excluído com sucesso!");
                    hide_item_box();
                    clear_item_box();
                    search_movie(document.getElementById("search").value);
                    films_count();
                } else {
                    alert("Erro ao editar filme!");
                }
            }
    };
    xhttp.open("POST","ListaFilmes",true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
    xhttp.send("reqType=delete&id="+id);
}