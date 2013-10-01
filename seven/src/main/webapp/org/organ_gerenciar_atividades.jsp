<%--
    Document   : index
    Created on : 26/03/2010, 16:35:48
    Author     : fernando
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="br.ufc.pet.evento.Atividade,br.ufc.pet.evento.Organizador,br.ufc.pet.evento.Organizacao,br.ufc.pet.evento.ResponsavelAtividade" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
    <%@include file="../ErroAutenticacaoUser.jsp" %>
    <head>      
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="../css/estilo.css" rel="stylesheet" type="text/css" />
        <title>Centro de Controle :: Organizador</title>
    </head>
    <body>
        <%
                    br.ufc.pet.evento.Evento e = (br.ufc.pet.evento.Evento) session.getAttribute("evento");
                    Organizador organizador = (Organizador) session.getAttribute("user");
                    ArrayList<Atividade> ats = e.getAtividades();
        %>
        <div id="container">
            <div id="top">
                <%-- Incluindo o Menu --%>
                <%@include file="organ_menu.jsp"%>
            </div>
            <div id="content">
                <h1 class="titulo">Gerenciar as Atividades do evento <%=e.getNome()%></h1>
                <%@include file="/error.jsp" %>
                <% if (organizador.recuperarOrganizacaoByEvendoId(e.getId()).getManterAtividade()) {%>
                <h2 class="titulo">Gerenciar Período de Inscrição e do Evento</h2>
                <p><a href="organ_periodos_inscricao_e_evento.jsp" title="Gerenciar Periodo de Inscricao e do Evento">Gerenciar os periodos da inscrição e evento</a></p>
                <h2 class="titulo"> Gerenciamento dos Horários</h2>
                <p><a href="../ServletCentral?comando=CmdListarHorarios" title="Gerencia atributos do Horario">Gerenciar os Horarios que estarão disponíveis às atividades</a></p>
                <h2 class="titulo"> Gerenciamento dos Tipos de Atividade</h2>
                <p><a href="../ServletCentral?comando=CmdListarTipoAtividade" title="Gerencia Tipo de Atividade">Gerenciar os Tipos de Atividades que o evento possui</a></p>
                <h2 class="titulo">Gerenciamento das Modalidades de Inscrição no evento</h2>
                <p><a href="../ServletCentral?comando=CmdListarTipoModalidade" title="Insere uma nova modalidade de Inscrição ao evento">Gerenciar as Modalidades de inscrição deste evento</a></p>
                <h2 class="titulo"> Gerenciamento de Atividades</h2>
                
                <p><a href="../ServletCentral?comando=CmdMontarPaginaAdicaoAtividade" title="Adicionar uma nova Atividade">Adicionar Atividade</a></p>
                <%}%>
                <%if (ats == null || ats.size() == 0) {%>
                <center><label>Sem atividades no momento</label></center>
                <%} else {%>
                <table>
                    <tr>
                        <th>Nome</th>
                        <th>Tipo</th>
                        <th>Capacidade</th>
                        <th>Local</th>
                        <th>Responsável</th>
                        <th>Alterar | Excluir</th>
                        <th>Visualizar</th>
                    </tr>
                    <%for (Atividade a : ats) {%>
                    <tr>
                        <td><%=a.getNome()%></td>
                        <td><%=a.getTipo().getNome() %></td>
                        <td><%=a.getVagas()%></td>
                        <td><%=a.getLocal()%></td>
                        <% java.lang.StringBuffer sb = new java.lang.StringBuffer("");
                            for (ResponsavelAtividade ra : a.getResponsaveis()) {
                                sb.append(ra.getUsuario().getNome());
                                sb.append("<br>");
                            }%>
                        <td><%=sb.toString()%></td>
                        <% if (organizador.recuperarOrganizacaoByEvendoId(e.getId()).getManterAtividade()) {%>
                        <td><a href="../ServletCentral?comando=CmdEditarAtividade&ativ_id=<%=a.getId()%>" title="Alterar Atividade">Alterar</a> | <a href="../ServletCentral?comando=CmdExcluirAtividade&ativ_id=<%=a.getId()%>" title="Excluir Atividade" onclick="return confirm('Tem certeza que deseja excluir essa atividade?')">Excluir</a></td>
                        <% } else {%>
                        <td><label title="Alterar Atividade">Alterar</label> | <label title="Excluir">Excluir</label></td>
                        <%}%>
                        <td><a href="../ServletCentral?comando=CmdVisualizarAtividade&ativ_id=<%=a.getId()%>" title="Visualizar Atividade">Visualizar</a></td>
                    </tr>
                    <%}%>
                </table>
                <% }%>
            </div>
            <div id="footer"></div>
        </div>
    </body>
</html>
