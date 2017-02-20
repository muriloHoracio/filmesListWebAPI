/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function search_movie(search,type){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
            if(this.readyState === 4 && this.status === 200){
                var list = JSON.parse(xhttp.responseText);
                document.getElementById("table").innerHTML = 
                    "<tr>"+
                        "<th class=\"col_title\">Título</th>"+
                        "<th class=\"col_genre\">Gênero</th>"+
                        "<th class=\"col_number\">Número</th>"+
                        "<th class=\"col_net\">Net</th>"+
                        "<th class=\"col_emcasa\">Em Casa</th>"+
                        "<th class=\"col_watched\">Assistido</th>"+
                    "</tr>";
                var table = "";
                for(var i = 0; i < list.length; i++ ){
                    $("#table").append(
                        "<tr>" +
                            "<input type=\"hidden\" value=\""+list[i].id+"\"/>" +
                            "<td class=\"col_title\">"+list[i].titulo+"</td>" +
                            "<td class=\"col_genre\">"+list[i].genero+"</td>" +
                            "<td class=\"col_number\">"+list[i].numero+"</td>" +
                            "<td class=\"col_net\"><input type=\"checkbox\" class=\"table_checkbox\""+((list[i].net)?("checked"):(""))+" disabled></td>" +
                            "<td class=\"col_emcasa\"><input type=\"checkbox\" class=\"table_checkbox\""+((list[i].emcasa)?("checked"):(""))+" disabled></td>" +
                            "<td class=\"col_watched\"><input type=\"checkbox\" class=\"table_checkbox\""+((list[i].watched)?("checked"):(""))+" disabled></td> "+
                        "</tr>");
                }
                document.getElementById("table").innerHTML = document.getElementById("table").innerHTML + table;
                data_offset = 0;
            }
    };
    xhttp.open("GET","ListaFilmes?reqType=search&search_query="+search+"&type="+type,true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
    xhttp.send();
}

function films_count(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
            if(this.readyState === 4 && this.status === 200){
                console.log(xhttp.responseText);
                document.getElementById("filmes_count_label").innerHTML = "<span>"+xhttp.responseText+"</span><span style=\"margin-left:20px;\">Filmes</span>";
            }
    };
    xhttp.open("GET","ListaFilmes?reqType=films_count",true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
    xhttp.send();
}