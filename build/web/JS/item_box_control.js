/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function show_item_box() {
    $("#background_cover").fadeIn();
    $("#add_box").fadeIn();
}

function hide_item_box() {
    $("#background_cover").fadeOut();
    $("#add_box").fadeOut();
}

function clear_item_box() {
    document.getElementById("input_titulo").value = "";
    document.getElementById("input_genero").value = "";
    document.getElementById("input_numero").value = "";
    document.getElementById("input_net").checked = true;
    document.getElementById("input_emcasa").checked = true;
    document.getElementById("input_watched").checked = false;
}

function fill_item_box(filme) {
    document.getElementById("input_titulo").value = filme.titulo;
    document.getElementById("input_genero").value = filme.genero;
    document.getElementById("input_numero").value = filme.numero;
    document.getElementById("input_net").checked = filme.net;
    document.getElementById("input_emcasa").checked = filme.emcasa;
    document.getElementById("input_watched").checked = filme.watched;
}

function disable_fields_item_box() {
    document.getElementById("input_titulo").disabled = true;
    document.getElementById("input_genero").disabled = true;
    document.getElementById("input_numero").disabled = true;
    document.getElementById("input_net").disabled = true;
    document.getElementById("input_emcasa").disabled = true;
    document.getElementById("input_watched").disabled = true;
}

function enable_fields_item_box() {
    document.getElementById("input_titulo").disabled = false;
    document.getElementById("input_genero").disabled = false;
    document.getElementById("input_numero").disabled = false;
    document.getElementById("input_net").disabled = false;
    document.getElementById("input_emcasa").disabled = false;
    document.getElementById("input_watched").disabled = false;
}

function show_add_options() {
    document.getElementById("add_action_options").style.display = "block";
    document.getElementById("edit_action_options").style.display = "none";
}

function show_details_options() {
    document.getElementById("edit_action_options").style.width = "100%";
    document.getElementById("add_action_options").style.display = "none";
    document.getElementById("edit_action_options").style.display = "block";
    document.getElementById("edit_save_button").style.display = "none";
    document.getElementById("edit_action_button").style.display = "inline-block";
    document.getElementById("delete_action_button").style.display = "inline-block";
    document.getElementById("edit_cancel_button").style.display = "none";
    document.getElementById("close_item_box_button").style.display = "inline-block";
}

function show_edit_options() {
    document.getElementById("edit_action_options").style.width = "310px";
    document.getElementById("edit_save_button").style.display = "inline-block";
    document.getElementById("edit_action_button").style.display = "none";
    document.getElementById("delete_action_button").style.display = "none";
    document.getElementById("edit_cancel_button").style.display = "inline-block";
    document.getElementById("close_item_box_button").style.display = "none";
}