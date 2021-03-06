/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.pet.comandos.organizador;

import br.ufc.pet.evento.Participante;
import br.ufc.pet.evento.ResponsavelAtividade;
import br.ufc.pet.evento.Usuario;
import br.ufc.pet.interfaces.Comando;
import br.ufc.pet.services.ParticipanteService;
import br.ufc.pet.services.UsuarioService;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Escritorio projetos
 */
public class CmdCadastrarUsuarioResponsavel implements Comando {

    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);

        //Recuperar dados do formulário.
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
//        String senha = request.getParameter("senha");
//        String confSenha = request.getParameter("r-senha");

        Usuario usuarioTemp = new Usuario();
        //salva os dados preenchidos no formulário
        if (nome != null) {
            usuarioTemp.setNome(nome);
        } else {
            usuarioTemp.setNome("");
        }
        if (email != null) {
            usuarioTemp.setEmail(email);
        } else {
            usuarioTemp.setEmail("");
        }
//        if (senha != null) {
//            usuarioTemp.setSenha(senha);
//        } else {
//            usuarioTemp.setSenha("");
//        }

        if (nome == null || nome.trim().equals("") || email == null || email.trim().equals("")) {
            session.setAttribute("erro", "Os campos marcados com * são obrigatórios.");
            session.setAttribute("usuarioTemp", usuarioTemp);
            return "/org/organ_add_novo_responsavel.jsp";
        }

//        if (!senha.trim().equals(confSenha)) {
//            session.setAttribute("erro", "A senha não confere com a sua confirmação.");
//            session.setAttribute("usuarioTemp", usuarioTemp);
//            return "/org/organ_add_novo_responsavel.jsp";
//        }
        //Montar participante
        Participante part = new Participante();
        part.setStatus(true);
        part.setDataCriacao(new Date());
        part.getUsuario().setEmail(email);
        part.getUsuario().setNome(nome);
        part.getUsuario().setSenha(nome.split(" ")[0] + "01");
//        part.getUsuario().setSenha(senha);


        //Validar a inserção
        UsuarioService us = new UsuarioService();
        if (us.getByEmail(part.getUsuario().getEmail()) != null) {
            session.setAttribute("erro", "E-Mail já cadastrado.");
            System.out.println("E-Mail já cadastrado.");
            return "/org/organ_add_novo_responsavel.jsp";
        }

        ParticipanteService ps = new ParticipanteService();
        //ResponsavelAtividadeService ras = new ResponsavelAtividadeService();
        ResponsavelAtividade ra = new ResponsavelAtividade();
        ra.setUsuario(part.getUsuario());
        ra.setStatus(true);
        ra.setDataCriacao(new Date());
        if (ps.insertParticipanteUsuario(part)) {
            ArrayList<ResponsavelAtividade> resps = (ArrayList<ResponsavelAtividade>) session.getAttribute("responsaveisEscolhidos");
            resps.add(ra);
            return "/org/organ_add_atividades.jsp";
        } else {
            session.setAttribute("erro", "Erro ao tentar cadastrar Responsavel por Atividade.");
            return "/org/organ_add_novo_responsavel.jsp";
        }
    }
}
