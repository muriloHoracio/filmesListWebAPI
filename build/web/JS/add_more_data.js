/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function add_more_data(search,type){
    data_offset = data_offset + 1;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
            if(this.readyState === 4 && this.status === 200){
                var list = JSON.parse(xhttp.responseText);
                for(var i = 0; i < list.length; i++){
                    $("#table").append(
                        "<tr>" +
                            "<input type=\"hidden\" value=\""+list[i].id+"\"/>" +
                            "<td class=\"col_title\">"+list[i].titulo+"</td>" +
                            "<td class=\"col_genre\">"+list[i].genero+"</td>" +
                            "<td class=\"col_number\">"+list[i].numero+"</td>" +
                            "<td class=\"col_net\"><input type=\"checkbox\" class=\"table_checkbox\""+((list[i].net)?("checked"):(""))+" disabled></td>" +
                            "<td class=\"col_emcasa\"><input type=\"checkbox\" class=\"table_checkbox\""+((list[i].emcasa)?("checked"):(""))+" disabled></td>" +
                        "</tr>");
                }
            }
    };
    xhttp.open("GET","ListaFilmes?reqType=add_more_data&search_query="+search+"&type="+type+"&data_offset="+data_offset,true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
    xhttp.send(null);
}