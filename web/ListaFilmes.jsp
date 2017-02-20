<%-- 
    Document   : ListaFilmes
    Created on : Jan 30, 2017, 12:57:52 PM
    Author     : murilo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="CSS/lista.css"/>
        <link rel="stylesheet" type="text/css" href="CSS/adicionar.css"/>
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
        <title>Lista de Filmes</title>
        <link rel="shortcut icon" href="favicon.ico"/>
        <script src="JS/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="JS/search_movie.js"></script>
        <script type="text/javascript" src="JS/add_more_data.js"></script>
        <script type="text/javascript" src="JS/last_number.js"></script>
        <script type="text/javascript" src="JS/add_movie.js"></script>
        <script type="text/javascript" src="JS/item_box_control.js"></script>
        <script type="text/javascript" src="JS/edit_movie.js"></script>
        <script type="text/javascript" src="JS/delete_movie.js"></script>
        <scritp type="text/javascript" src="JS/save_data.js"></script>
        <script type="text/javascript">
            var filme = {id:0,titulo:"",genero:"",numero:0,net:false,emcasa:false,watched:false};
            var offset_times = 0;
            var scroll_aux = 0;
            var tr_selected = null;
        </script>
    </head>
    <body>
        <datalist id="generos">
            <option>Ação</option>
            <option>Animação</option>
            <option>Aventura</option>
            <option>Comédia</option>
            <option>Documentário</option>
            <option>Drama</option>
            <option>Faroeste</option>
            <option>Ficção Científica</option>
            <option>Guerra</option>
            <option>Policial</option>
            <option>Romance</option>
            <option>Série</option>
            <option>Suspense</option>
            <option>Terror</option>
            <option>Seriado</option>
        </datalist>
        <div class="nav_bar">
            <button class="nav_bar_item"><a href="ListaFilmes.jsp">FILMES</a></button>
            <button id="add_button" class="nav_bar_item">ADICIONAR</button>
            <button id="backup_button" class="nav_bar_item"><a href="ListaFilmes?reqType=save">SALVAR</a></button>
            <span id="filmes_count_label" style="float: right; margin-right: 45px;margin-top: 48px;color:lightgray;"></span>
        </div>
        <div class="background_cover" id="background_cover" style="display: none;">
        </div>
        <div class="add_box" id="add_box" style="display: none;">
            <div id="input_box">
                <span id="first_row">
                    <label>Título:</label><input id="input_titulo" type="text" style="text-transform: capitalize;"/>
                </span>
                <span id="second_row">
                    <label>Gênero:</label><input id="input_genero" type="text" list="generos" autocomplete="off"/>
                </span>
                <span id="third_row">
                    <label>Número:</label><input type="number" id="input_numero"/>
                </span>
                <span id="third_bottom_row">
                    <label id="label_net">Net:</label><input id="input_net" type="checkbox" checked="checked" style="width: 25px;"/>
                    <label id="label_emcasa" style="margin-left: 50px;">Em Casa:</label><input id="input_emcasa" type="checkbox" checked="checked" style="width: 25px;"/>
                    <label id="label_watched" style="margin-left: 50px;">Assistido:</label><input id="input_watched" type="checkbox" style="width: 25px;"/>
                </span>
                <span id="forth_row">
                    <div id="add_action_options">
                        <button id="save_button" class="action_button">Adicionar</button>
                        <button id="cancel_button" class="action_button">Cancelar</button>
                    </div>
                    <div id="edit_action_options">
                        <button id="edit_action_button" class="action_button">Editar</button>
                        <button id="edit_save_button" class="action_button">Salvar</button>
                        <button id="edit_cancel_button" class="action_button">Cancelar</button>
                        <button id="close_item_box_button" class="action_button">Voltar</button>
                        <button id="delete_action_button" class="action_button">Excluir</button>
                    </div>
                </span>
            </div>
        </div>
        <div class="search_box">
            <div style="display: inline-block;position: relative;float: left;"><select id="search_type" class="drop-down-search-menu"><option>Título</option><option>Número</option><option>Gênero</option></select></div>
            <div class="search_gear_wrapper-4">
                <input type="text" id="search" name="search_query" placeholder="Pesquisar...">
                <button class="search_icon" id="search_button"><i class="fa fa-search"></i></button>
            </div>    
        </div>
        
        <div class="table_wrapper">
            <table id="table">
                <tr>
                    <th class="col_title">Título</th>
                    <th class="col_genre">Gênero</th>
                    <th class="col_number">Número</th>
                    <th class="col_net">Net</th>
                    <th class="col_emcasa">Em Casa</th>
                    <th class="col_watched">Assistido</th>
                </tr>
                <tr></tr>
            </table>
        </div>
        <span id="table_bottom" style="display: block;"></span>
        <script type="text/javascript">
            document.getElementById("search_button").addEventListener("click", function (){
                search_movie(document.getElementById("search").value,document.getElementById("search_type").value);
            });
            document.getElementById("save_button").addEventListener("click", function (){
                    add_movie(document.getElementById("input_titulo").value,
                    document.getElementById("input_genero").value,
                    document.getElementById("input_numero").value,
                    document.getElementById("input_net").checked,
                    document.getElementById("input_emcasa").checked,
                    document.getElementById("input_watched").checked
                );
            });
            document.getElementById("cancel_button").addEventListener("click", function() {
                clear_item_box();
                hide_item_box();
            });
            document.getElementById("add_button").addEventListener("click", function (){
                show_add_options();
                clear_item_box();
                enable_fields_item_box();
                last_number();
                show_item_box();
            });
            document.getElementById("edit_cancel_button").addEventListener("click", function (){
                fill_item_box(filme);
                disable_fields_item_box();
                show_details_options();
            });
            document.getElementById("edit_action_button").addEventListener("click", function() {
                enable_fields_item_box();
                show_edit_options();
            });
            document.getElementById("edit_save_button").addEventListener("click", function () {
                edit_movie(filme.id,
                    document.getElementById("input_titulo").value,
                    document.getElementById("input_genero").value,
                    document.getElementById("input_numero").value,
                    document.getElementById("input_net").checked,
                    document.getElementById("input_emcasa").checked,
                    document.getElementById("input_watched").checked
                );
            });
            document.getElementById("close_item_box_button").addEventListener("click", function () {
                hide_item_box();
                clear_item_box();
            });
            document.getElementById("delete_action_button").addEventListener("click", function () {
                if(confirm("Tem certeza que deseja excluir o filme selecionado?")){        
                    delete_movie(filme.id);
                }
            });
            $(document).ready(function (){
                films_count();
                $("#table").delegate("tr","click",function(){
                    tr_selected = $(this);
                    var fields = $(this).find("td");
                    var inputs = $(this).find("input");
                    filme.id = inputs[0].value;
                    filme.titulo = fields[0].innerHTML;
                    filme.genero = fields[1].innerHTML;
                    filme.numero = fields[2].innerHTML;
                    filme.net = inputs[1].checked;
                    filme.emcasa = inputs[2].checked;
                    filme.watched = inputs[3].checked;
                    fill_item_box(filme);
                    disable_fields_item_box();
                    show_item_box();
                    show_details_options();
                });
            });
            $(window).scroll( function () {
                if(scroll_aux === 0) {
                    scroll_aux = 1;
                    setTimeout(function() {
                        if($("#table_bottom").offset().top - $(window).scrollTop() < 1000){
                            add_more_data(document.getElementById("search").value,document.getElementById("search_type").value);
                        }
                        scroll_aux = 0;
                    },400);
                }
            });
            $("#search").keyup(function (e){
                if(e.keyCode === 13)
                    $("#search_button").click();
            });
        </script>
    </body>
</html>
