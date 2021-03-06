/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.pet.comandos.administrador;

import br.ufc.pet.evento.Administrador;
import br.ufc.pet.evento.Evento;
import br.ufc.pet.interfaces.Comando;
import br.ufc.pet.services.EventoService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.ufc.pet.util.UtilSeven;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author welligton
 */
public class CmdAdicionarEvento implements Comando {

    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Administrador admin = (Administrador) session.getAttribute("user");

        String nomeEvento = request.getParameter("nome_evento");
        session.setAttribute("nomeEvento", nomeEvento);
        String siglaEvento = request.getParameter("sigla_evento");
        session.setAttribute("siglaEvento", siglaEvento);
        String descricao = request.getParameter("descricao");
        session.setAttribute("descricao", descricao);
        String tema = request.getParameter("tema_evento");
        session.setAttribute("tema", tema);
        String inicioEvento = request.getParameter("inicio_evento");
        session.setAttribute("inicioEvento", inicioEvento);
        String fimEvento = request.getParameter("fim_evento");
        session.setAttribute("fimEvento", fimEvento);
        String inicioInscricao = request.getParameter("inicio_periodo_inscricao");
        session.setAttribute("inicioInscricao", inicioInscricao);
        String fimInscricao = request.getParameter("fim_periodo_inscricao");
        session.setAttribute("fimInscricao", fimInscricao);
        String limiteDeAtividadesPorParticipante = request.getParameter("limite_de_atividades_por_participante");
        session.setAttribute("limiteDeAtividadesPorParticipante", limiteDeAtividadesPorParticipante);

        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date data = UtilSeven.treatToDate(dateFormat.format(date));

        int limiteDeAtividades;
        
        Evento E = null;
        if (!request.getParameter("operacao_evento").equalsIgnoreCase("0")) {
            E = new EventoService().getEventoById(Long.parseLong(request.getParameter("operacao_evento")));
        }
        if (nomeEvento.trim().equals("") || nomeEvento == null || siglaEvento.trim().equals("")
                || siglaEvento == null || descricao.trim().equals("") || descricao == null || tema.trim().equals("")
                || inicioInscricao.trim().equals("") || inicioInscricao == null || fimInscricao.trim().equals("")
                || fimInscricao == null || limiteDeAtividadesPorParticipante.trim().equals("") || limiteDeAtividadesPorParticipante == null) {
            session.setAttribute("erro", "Preencha todos os campos");
            session.setAttribute("evento", E);
            return "/admin/add_events.jsp";
        } else if (UtilSeven.validaData(inicioInscricao) != true || UtilSeven.validaData(fimInscricao) != true
                || !UtilSeven.validaData(inicioEvento) || !UtilSeven.validaData(fimEvento)) {
            session.setAttribute("erro", "Data InvÃ¡lida, digite no formato dd/mm/aaaa");
            return "/admin/add_events.jsp";
        } else {
            
            try{
                limiteDeAtividades = Integer.parseInt(limiteDeAtividadesPorParticipante);
            }
            catch(NumberFormatException e){
                System.out.print(limiteDeAtividadesPorParticipante);
                session.setAttribute("erro", "Limite de atividades invalido. Por favor digite apenas nÃºmeros.");
                return "/admin/add_events.jsp";
            }
            
            if (UtilSeven.treatToDate(inicioEvento).before(data)) {
                session.setAttribute("erro", "Data de inicio do evento anterior a data de hoje.");
                return "/admin/add_events.jsp";
            }
            if (UtilSeven.treatToDate(inicioEvento).after(UtilSeven.treatToDate(fimEvento))) {
                session.setAttribute("erro", "Data de inicio do evento posterior ao termino do evento.");
                return "/admin/add_events.jsp";
            }
            if (UtilSeven.treatToDate(inicioInscricao).before(data)) {
                session.setAttribute("erro", "Data de inicio das incriÃ§Ãµes anterior a data de hoje.");
                return "/admin/add_events.jsp";
            }
            if (UtilSeven.treatToDate(inicioInscricao).after(UtilSeven.treatToDate(fimEvento))) {
                session.setAttribute("erro", "Data de inicio das inscriÃ§Ãµes posterior ao termino do evento.");
                return "/admin/add_events.jsp";
            }
            if (UtilSeven.treatToDate(inicioInscricao).after(UtilSeven.treatToDate(inicioEvento))) {
                session.setAttribute("erro", "Data de inicio das inscriÃ§Ãµes posterior ao inicio do evento.");
                return "/admin/add_events.jsp";
            }
            if (UtilSeven.treatToDate(inicioInscricao).after(UtilSeven.treatToDate(fimInscricao))) {
                session.setAttribute("erro", "Data de inicio das inscriÃ§Ãµes posterior ao termino das inscriÃ§Ãµes.");
                return "/admin/add_events.jsp";
            }
            if (UtilSeven.treatToDate(fimInscricao).after(UtilSeven.treatToDate(inicioEvento))) {
                session.setAttribute("erro", "Data de fim das inscriÃ§Ãµes posterior ao inicio do evento.");
                return "/admin/add_events.jsp";
            }
            if (request.getParameter("operacao_evento").equalsIgnoreCase("0")){
                EventoService es = new EventoService();
                if (es.getEventoBySigla(siglaEvento) != null) {
                    session.setAttribute("erro", "Evento jÃ¡ cadastrado");
                    return "/admin/add_events.jsp";
                }
                E = new Evento();
                E.setNome(nomeEvento);
                E.setSigla(siglaEvento);
                E.setTema(tema);
                E.setInicioPeriodoEvento(UtilSeven.treatToDate(inicioEvento));
                E.setFimPeriodoEvento(UtilSeven.treatToDate(fimEvento));
                E.setInicioPeriodoInscricao(UtilSeven.treatToDate(inicioInscricao));
                E.setFimPeriodoInscricao(UtilSeven.treatToDate(fimInscricao));
                E.setAdministrador(admin);
                E.setDescricao(descricao);
                E.setLimiteAtividadePorParticipante(limiteDeAtividades);
                if (es.adicionar(E)) {
                    admin.addEvento(E);
                    session.setAttribute("sucesso", "Evento adicionado com sucesso");
                    System.out.println("Adicionei na sessÃ£o");
                    return "/admin/manege_events.jsp";

                } else {
                    session.setAttribute("erro", "Erro ao adicionar evento");
                }
             }else {
                admin.removerEventoById(E.getId());
                //System.out.println("passou");
                E.setNome(nomeEvento);
                E.setSigla(siglaEvento);
                E.setTema(tema);
                E.setInicioPeriodoEvento(UtilSeven.treatToDate(inicioEvento));
                E.setFimPeriodoEvento(UtilSeven.treatToDate(fimEvento));
                E.setInicioPeriodoInscricao(UtilSeven.treatToDate(inicioInscricao));
                E.setFimPeriodoInscricao(UtilSeven.treatToDate(fimInscricao));
                E.setLimiteAtividadePorParticipante(limiteDeAtividades);
                E.setAdministrador(admin);
                E.setDescricao(descricao);
                EventoService es = new EventoService();
                es.atualizar(E);
                admin.addEvento(E);
                session.setAttribute("sucesso", "Evento alterado com sucesso");

            }
        }
        return "/admin/manege_events.jsp";
    }
}
