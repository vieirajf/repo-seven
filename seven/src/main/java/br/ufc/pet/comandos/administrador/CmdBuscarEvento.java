/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.pet.comandos.administrador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufc.pet.evento.Evento;
import br.ufc.pet.interfaces.Comando;
import br.ufc.pet.services.EventoService;


/**
 *
 * @author mardson
 */
public class CmdBuscarEvento implements Comando{

    public String executa(HttpServletRequest request, HttpServletResponse response) {
         HttpSession session = request.getSession(true);

        String codigo = request.getParameter("id");
        Long cod = Long.parseLong(codigo);
        EventoService s = new EventoService();
        Evento e = s.getEventoById(cod);
        session.setAttribute("evento", e);

        return "/admin/edit_events.jsp";
    }

}
