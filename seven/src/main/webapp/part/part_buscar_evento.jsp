<%--
    Document   : index
    Created on : 26/03/2010, 16:35:48
    Author     : fernando
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="br.ufc.pet.evento.Evento"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
    <%@include file="../ErroAutenticacaoUser.jsp" %>
    <%
                ArrayList<Evento> ae = (ArrayList<Evento>) session.getAttribute("eventosAbertos");
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="../css/estilo.css" rel="stylesheet" type="text/css" />
        <title>Centro de Controle :: Administrador</title>
    </head>
    <body>
        <div id="container">
            <div id="top">
                <%-- Incluindo Menu --%>
                <%@include file="part_menu.jsp" %>
            </div>
            <div id="content">
                <%@include file="/error.jsp"%>
                <h1 class="titulo">Selecionar Evento para Inscrição</h1>
                <table>
                    <% if (ae == null || ae.isEmpty()) {%>
                    <center><label>Não há eventos disponíveis</label></center>
                    <%} else {%>
                    <h2 class="titulo" style="margin-bottom: 10px;">Eventos abertos:</h2>
                    <tr>
                        <th>Sigla</th>
                        <th>Nome do Evento</th>
                        <th>Programação</th>
                    </tr>
                    <% for (Evento e : ae) {%>
                    <tr>
                        <td><%= e.getSigla()%></td>
                        <td><a href="../ServletCentral?comando=CmdSelecionarEvento&id=<%=e.getId()%>" title=""><%= e.getNome()%></a></td>
                        <td> <a href="../ServletCentral?comando=CmdVisualizarProgramacao&id=<%=e.getId()%>" title="Programacao" > Visualizar</a> </td>
                    </tr>
                    <%}
                                }%>
                </table>
                <p><a href="" title="" onclick="history.back(); return false;" class="voltar">Voltar</a></p>
            </div>
            <div id="footer"></div>
        </div>
    </body>
</html>
