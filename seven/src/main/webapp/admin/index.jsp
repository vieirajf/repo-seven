<%-- 
    Document   : index
    Created on : 26/03/2010, 16:35:48
    Author     : fernando
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.ufc.pet.evento.Administrador"%>
<%@include file="../ErroAutenticacaoUser.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%
            Administrador admin = (Administrador) session.getAttribute("user");
            String nomeSaudacao = admin.getUsuario().getNome().split(" ")[0];
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="../css/estilo.css" rel="stylesheet" type="text/css" />
        <title>Centro de Controle :: Administrador</title>
    </head>
    <body>
        <div id="container">
            <div id="top">
                <%-- Incluindo o Menu --%>
                <%@include file="admin_menu.jsp" %>
            </div>
            <div id="content">
                <div id="content_left">
                    <h1 class="titulo">Olá, <%= nomeSaudacao%></h1>
                    <p>Esta é a página inicial do Administrador. Quando desejar retornar a esta página, clique na opção 'Home' no menu acima.</p>
                </div>
                <div id="content_right">
                </div>
            </div>
            <div id="footer"></div>
        </div>
    </body>
</html>
