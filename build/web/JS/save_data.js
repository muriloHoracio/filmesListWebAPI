/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function save_data(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
            if(this.readyState === 4 && this.status === 200){
                console.log(xhttp.responseText);
            }
    };
    xhttp.open("GET","ListaFilmes?reqType=save",true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
    xhttp.send();
}