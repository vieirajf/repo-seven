<%--
    Document   : index
    Created on : 26/03/2010, 16:35:48
    Author     : Caio
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="br.ufc.pet.evento.Inscricao"%>
<%@page import="br.ufc.pet.services.InscricaoService"%>
<%@page import="br.ufc.pet.evento.Participante"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html>
<%@include file="../ErroAutenticacaoUser.jsp" %>

    <%          //recupera participante da sessão
                Participante p = (Participante) session.getAttribute("user");
                ArrayList<Inscricao> array = p.getInscricoes();
                String estado;//será utilizada para dizer se uma inscrição está ou não está consolidada
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
            <%@include file="/error.jsp"%>
            <div id="content">
                <h1 class="titulo">Visualizar Minhas Inscrições</h1>
                <p><a href="../ServletCentral?comando=CmdListarEventosAbertos" title="Nova Inscrição">Nova Inscrição</a></p>
                <%if (array.isEmpty()) {%>
                <center><label>Não há inscrições cadastradas</label></center>
                <%} else {%>
                <table>
                    <tr>
                        <th>Tipo</th>
                        <th>Nome do Evento</th>
                        <th>Status do Pagamento</th>
                        <th>Gerar boleto</th>
                        <th>Editar</th>
                        <th>Excluir</th>
                    </tr>
                    <% for (Inscricao i : array) {%>
                    <tr>
                        <td><%=i.getModalidade().getTipo()%></td>
                        <td><%=i.getEvento().getNome()%></td>
                        <%
                             if (i.isConfirmada()) {
                                 estado = "Efetuado";
                             } else {
                                 estado = "Não Efetuado";
                             }
                        %>
                        <td><%=estado%></td>
                        <% if (!i.isConfirmada()) {%>
                        <td><a href="../ServletCentral?comando=CmdGerarBoletoPagamento&id=<%=i.getId()%>"title="">Gerar Boleto</a></td>
                        <% } else {%>
                        <td> - </td>
                        <% }%>
                        <% if (!i.isConfirmada()) {%><%--link chama o comando de visualização/edição--%>
                        <td><a href="../ServletCentral?comando=CmdVisualizarInscricao&iId=<%=i.getId()%>" title="Visualizar/Editar">Visualizar/Editar</a></td>
                        <% } else {%>
                        <td> - </td>
                        <% }%>
                        <td><a href="../ServletCentral?comando=CmdExcluirInscricao&iId=<%=i.getId()%>" onclick="return confirm('ATENÇÃO: Se você excluir uma inscrição que já foi paga ela não estará mais no sistema e você não poderá recuperar a quantia gasta. Você também poderá perder as vagas e os certificados relacionados com esta inscrição. Tem certeza que dejesa excluir esta inscrição?');" title="Excluir">Excluir</a></td>
                    </tr>
                    <%}%> 
                </table>
                <%}%>
                <div id="list">  
                </div>
            </div>         
            <div id="footer"></div>
        </div>
    </body>
</html>
