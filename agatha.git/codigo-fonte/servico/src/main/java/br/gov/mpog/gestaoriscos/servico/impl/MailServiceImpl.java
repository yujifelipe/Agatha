package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.Responsavel;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.servico.MailService;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailServiceImpl implements MailService {

    private final Logger log = LoggerFactory.getLogger(PermissaoServiceImpl.class);

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.remetente}")
    private String remetente;

    @Value("${link.sistema}")
    private String linkSistema;

    @Override
    public void enviarEmailNovoUsuario(String email) {
        log.debug("Request to send email to usuario created : {}", email);

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            String msg = "Prezado(a),\n";
            msg += "Foi concluído o seu cadastro no Sistema de Gestão de Riscos - Ágatha\n";
            msg += "Para acessar o sistema acesse o link abaixo utilizando as suas credenciais do Brasil Cidadão para autenticação.\n\n";

            msg += linkSistema;

            msg += this.getAssinatura();

            helper.setTo(email);
            helper.setFrom(remetente);
            helper.setSubject("Ágatha - cadastro de novo usuário");
            helper.setText(msg);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void enviarEmailSolicitarValidacao(Long processoId) {
        log.debug("Request to send email to gestor from processo : {}", processoId);

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        Processo processo = processoRepository.findOne(processoId);

        try {
            String msg = "Prezado Gestor,\n";
            msg += "Foi concluído um processo de mapeamento de riscos no sistema Ágatha.\n\n";

            msg += "Dados do processo: ";
            msg += getMacroprocessoProcesso(processo);
            msg += "\n";

            msg += "Órgão: ";
            msg += processo.getAnalise().getOrgao().getNome();
            msg += "\n";

            msg += "Secretaria: ";
            msg += processo.getAnalise().getSecretaria().getNome();
            msg += "\n\n";

            msg += "Acesse o sistema para realizar a validação.";

            msg += this.getAssinatura();

            helper.setTo(processo.getGestor().getEmail());
            helper.setFrom(remetente);
            helper.setSubject("Ágatha - Solicitação de Validação do Processo");
            helper.setText(msg);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void enviarEmailValidacaoProcesso(Long processoId) {
        log.debug("Request to send email to Responsaveis from processo : {}", processoId);

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        Processo processo = processoRepository.findOne(processoId);
        boolean processoValidado = processo.getDecisao().getNome().equalsIgnoreCase("Validar");

        try {
            String subject = "Ágatha - ";
            subject += processoValidado ? "Validação" : "Recusa";
            subject += " do Processo de Mapeamento";

            String msg = "Prezado(a),\n";
            msg += "Foi concluída a validação do processo de mapeamento de riscos com a seguinte resposta do gestor do processo: ";
            msg += processoValidado ? "Validado" : "Recusado";
            msg += ".\n";
            msg += "Macroprocesso/Processo: ";
            msg += getMacroprocessoProcesso(processo);

            msg += this.getAssinatura();

            helper.setTo(getAnalistasEmail(processo));
            helper.setFrom(remetente);
            helper.setSubject(subject);
            helper.setText(msg);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getMacroprocessoProcesso(Processo processo) {
        String msg = "";
        if (processo.getMacroprocesso() != null && !processo.getMacroprocesso().getDescricao().equals("")) {
            msg += processo.getMacroprocesso().getDescricao();
        }

        if (processo.getProcesso() != null && !processo.getProcesso().equals("")) {
            if (!msg.isEmpty()) {
                msg += "/";
                msg += processo.getProcesso();
            }
        }

        return msg;
    }

    private String[] getAnalistasEmail(Processo processo) {
        List<String> emails = new ArrayList<>(0);

        for (Responsavel responsavel : processo.getResponsaveis()) {
            emails.add(responsavel.getUsuario().getEmail());
        }

        return emails.toArray(new String[0]);
    }

    private String getAssinatura(){
        String assinatura = "\n\nAtenciosamente,\n\n";
        assinatura += "Ágatha - Sistema de Gestão de Riscos\n";
        assinatura += "Favor não responder a esse email!\n\n";

        return assinatura;
    }
}
