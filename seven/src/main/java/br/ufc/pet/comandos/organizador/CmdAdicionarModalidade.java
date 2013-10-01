/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.pet.comandos.organizador;

import br.ufc.pet.evento.Evento;
import br.ufc.pet.evento.ModalidadeInscricao;
import br.ufc.pet.evento.PrecoAtividade;
import br.ufc.pet.evento.TipoAtividade;
import br.ufc.pet.interfaces.Comando;
import br.ufc.pet.services.ModalidadeInscricaoService;
import br.ufc.pet.util.UtilSeven;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Franklin
 */
public class CmdAdicionarModalidade implements Comando {

    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String nomeModalidade = request.getParameter("nomeModalidade").trim();
        Evento evento = (Evento) session.getAttribute("evento");
        ModalidadeInscricao modalidade = new ModalidadeInscricao();
        ModalidadeInscricaoService ms = new ModalidadeInscricaoService();

        ArrayList<TipoAtividade> tipos = UtilSeven.getTiposDeAtividadeByEventoId(evento.getId());
        String id = request.getParameter("id_atualizar");
        if (id != null && id.isEmpty() == false) {
            modalidade = ms.getModalidadeInscricaoById(Long.parseLong(id));
            modalidade.setTipo(nomeModalidade);
            modalidade.setEventoId(evento.getId());
            ArrayList<PrecoAtividade> precos = new ArrayList<PrecoAtividade>();

            for (TipoAtividade t : tipos) {
                PrecoAtividade preco = new PrecoAtividade();
                preco.setModalidadeId(modalidade.getId());
                preco.setTipoAtividadeId(t.getId());
                preco.setValor(Double.parseDouble(request.getParameter("preco_" + t.getId().toString())));
                precos.add(preco);
            }
            modalidade.setPrecoAtividades(precos);
            ms.atualizar(modalidade);
            session.setAttribute("sucesso", "Modalidade atualizada com sucesso!");

        } else {
            boolean naoInseriri=false;            
            ArrayList<ModalidadeInscricao> mods= ms.getModalidadesInscricaoByEventoId(evento.getId());
            for(ModalidadeInscricao m: mods){
            if(m.getTipo().equalsIgnoreCase(nomeModalidade)){
            naoInseriri=true;
            }
            }
            if (naoInseriri) {
                //session.setAttribute("modalidade_existe", "ok");
                //return "/ServletCentral?comando=CmdListarTipoModalidade";
                session.setAttribute("erro", "Já existe uma modalidade cadastrada com esse nome!");
                return "/org/organ_add_modalidade.jsp";


            }
            modalidade.setTipo(nomeModalidade);
            modalidade.setEventoId(evento.getId());
//            ms.adicionar(modalidade);
            ArrayList<PrecoAtividade> precos = new ArrayList<PrecoAtividade>();
            for (TipoAtividade t : tipos) {
//                modalidade = ms.getModalidadeInscricaoByTipo(nomeModalidade);
//                pa.setModalidadeId(modalidade.getId());
//                pa.setTipoAtividadeId(t.getId());
//                pa.setValor(Double.parseDouble(request.getParameter("preco_" + t.getId().toString())));
//                pas.adicionar(pa);
                PrecoAtividade precoAtiv = new PrecoAtividade();
                // modalidade = ms.getModalidadeInscricaoByTipo(nomeModalidade);
                //precoAtiv.setModalidadeId(modalidade.getId());
                precoAtiv.setTipoAtividadeId(t.getId());
                precoAtiv.setValor(Double.parseDouble(request.getParameter("preco_" + t.getId().toString())));
                precos.add(precoAtiv);
            }
            modalidade.setPrecoAtividades(precos);
            ms.adicionar(modalidade);
            session.setAttribute("sucesso", "Modalidade adicionada com sucesso!");
        }
        return "/ServletCentral?comando=CmdListarTipoModalidade";
    }
}
