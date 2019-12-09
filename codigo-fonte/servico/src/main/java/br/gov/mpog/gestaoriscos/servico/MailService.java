package br.gov.mpog.gestaoriscos.servico;

public interface MailService {

    void enviarEmailNovoUsuario(String email);

    void enviarEmailSolicitarValidacao(Long processoId);

    void enviarEmailValidacaoProcesso(Long processoId);
}