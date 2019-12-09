package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.ArquivoAnexo;
import br.gov.mpog.gestaoriscos.modelo.Avaliacao;
import br.gov.mpog.gestaoriscos.modelo.Calculadora;
import br.gov.mpog.gestaoriscos.modelo.CalculoRisco;
import br.gov.mpog.gestaoriscos.modelo.Categoria;
import br.gov.mpog.gestaoriscos.modelo.Causa;
import br.gov.mpog.gestaoriscos.modelo.Consequencia;
import br.gov.mpog.gestaoriscos.modelo.Controle;
import br.gov.mpog.gestaoriscos.modelo.ControleEvento;
import br.gov.mpog.gestaoriscos.modelo.DecisaoProcesso;
import br.gov.mpog.gestaoriscos.modelo.Desenho;
import br.gov.mpog.gestaoriscos.modelo.Evento;
import br.gov.mpog.gestaoriscos.modelo.EventoCausa;
import br.gov.mpog.gestaoriscos.modelo.EventoConsequencia;
import br.gov.mpog.gestaoriscos.modelo.EventoRisco;
import br.gov.mpog.gestaoriscos.modelo.HistoricoEventoRisco;
import br.gov.mpog.gestaoriscos.modelo.Identificacao;
import br.gov.mpog.gestaoriscos.modelo.ImpactoCalculadora;
import br.gov.mpog.gestaoriscos.modelo.Macroprocesso;
import br.gov.mpog.gestaoriscos.modelo.MatrizSwot;
import br.gov.mpog.gestaoriscos.modelo.Natureza;
import br.gov.mpog.gestaoriscos.modelo.ObjetivoControle;
import br.gov.mpog.gestaoriscos.modelo.Operacao;
import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.Perfil;
import br.gov.mpog.gestaoriscos.modelo.PesoEventoRisco;
import br.gov.mpog.gestaoriscos.modelo.PlanoControle;
import br.gov.mpog.gestaoriscos.modelo.ProbabilidadeCalculadora;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.ProcessoAnexo;
import br.gov.mpog.gestaoriscos.modelo.Responsavel;
import br.gov.mpog.gestaoriscos.modelo.RespostaRisco;
import br.gov.mpog.gestaoriscos.modelo.StatusProcesso;
import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.modelo.TipoControle;
import br.gov.mpog.gestaoriscos.modelo.TipoMatriz;
import br.gov.mpog.gestaoriscos.modelo.Usuario;
import br.gov.mpog.gestaoriscos.modelo.dto.ArquivoAnexoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculoRiscoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CausaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ConsequenciaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleEventoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.DecisaoProcessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.DesenhoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoCausaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoConsequenciaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoRiscoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.IdentificacaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MatrizSwotDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.NaturezaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ObjetivoControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.OperacaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaosDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PerfilDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoProcessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PlanoControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoEtapaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoListagemDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ResponsavelDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.RespostaRiscoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.StatusProcessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoMatrizDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import br.gov.mpog.gestaoriscos.repositorio.ArquivoAnexoRepository;
import br.gov.mpog.gestaoriscos.repositorio.AvaliacaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.CalculadoraRepository;
import br.gov.mpog.gestaoriscos.repositorio.CalculoRiscoRepository;
import br.gov.mpog.gestaoriscos.repositorio.CategoriaRepository;
import br.gov.mpog.gestaoriscos.repositorio.CausaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.CausaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ConsequenciaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.ConsequenciaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ControleCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.ControleEventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.ControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.DecisaoProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.DesenhoRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoCausaRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoConsequenciaRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.EventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoRiscoRepository;
import br.gov.mpog.gestaoriscos.repositorio.HistoricoEventoRiscoRepository;
import br.gov.mpog.gestaoriscos.repositorio.IdentificacaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.ImpactoCalculadoraRepository;
import br.gov.mpog.gestaoriscos.repositorio.MacroprocessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.NaturezaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ObjetivoControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.OperacaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.PerfilRepository;
import br.gov.mpog.gestaoriscos.repositorio.PermissaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.PlanoControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.RespostaRiscoRepository;
import br.gov.mpog.gestaoriscos.repositorio.StatusProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.StatusTaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.TipoControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.TipoMatrizRepository;
import br.gov.mpog.gestaoriscos.repositorio.TipoTaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.UsuarioRepository;
import br.gov.mpog.gestaoriscos.servico.MailService;
import br.gov.mpog.gestaoriscos.servico.MonitoramentoService;
import br.gov.mpog.gestaoriscos.servico.ProcessoService;
import br.gov.mpog.gestaoriscos.servico.mapper.ArquivoAnexoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.CalculadoraMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.CategoriaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.CausaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ConsequenciaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ControleEventoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ControleMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.DecisaoProcessoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.DesenhoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoCausaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoConsequenciaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoRiscoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.MacroprocessoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.NaturezaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ObjetivoControleMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.OperacaoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.OrgaoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.PerfilMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.PlanoControleMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ProcessoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.RespostaRiscoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.StatusProcessoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TaxonomiaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TipoControleMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TipoMatrizMapper;
import br.gov.mpog.gestaoriscos.util.ReportFooterEventHandler;
import br.gov.mpog.gestaoriscos.util.ReportUtil;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import br.gov.mpog.gestaoriscos.util.UsuarioUtil;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing Processo.
 */
@Service
@Transactional
public class ProcessoServiceImpl implements ProcessoService {

    private final Logger log = LoggerFactory.getLogger(ProcessoServiceImpl.class);

    private static String GESTOR_DO_PROCESSO = "Gestor do Processo";

    private static String ANALISTA_DE_RISCO = "Analista de Risco";

    private static String UNIDADE = "Unidade";

    private static String NUCLEO = "Núcleo";

    private static String NAO_AVALIADO = "Não avaliado";

    private static String NAO_INFORMADO = "Não Informado";

    private final ProcessoRepository processoRepository;

    private final StatusProcessoRepository statusProcessoRepository;

    private final UsuarioRepository usuarioRepository;

    private final TipoMatrizRepository tipoMatrizRepository;

    private final PerfilRepository perfilRepository;

    private final PermissaoRepository permissaoRepository;

    private final EventoRiscoRepository eventoRiscoRepository;

    private final IdentificacaoRepository identificacaoRepository;

    private final OrgaoRepository orgaoRepository;

    private final EventoRepository eventoRepository;

    private final EventoCustomRepositorio eventoCustomRepositorio;

    private final CausaRepository causaRepository;

    private final CausaCustomRepositorio causaCustomRepositorio;

    private final ConsequenciaRepository consequenciaRepository;

    private final ConsequenciaCustomRepositorio consequenciaCustomRepositorio;

    private final CategoriaRepository categoriaRepository;

    private final NaturezaRepository naturezaRepository;

    private final EventoCausaRepository eventoCausaRepository;

    private final EventoConsequenciaRepository eventoConsequenciaRepository;

    private final OrgaoCustomRepositorio orgaoCustomRepositorio;

    private final ProcessoCustomRepositorio processoCustomRepositorio;

    private final ArquivoAnexoRepository arquivoAnexoRepository;

    private final CalculadoraRepository calculadoraRepository;

    private final ControleCustomRepositorio controleCustomRepositorio;

    private final ControleRepository controleRepository;

    private final DesenhoRepository desenhoRepository;

    private final OperacaoRepository operacaoRepository;

    private final AvaliacaoRepository avaliacaoRepository;

    private final ControleEventoRepository controleEventoRepository;

    private final CalculoRiscoRepository calculoRiscoRepository;

    private final TipoTaxonomiaRepository tipoTaxonomiaRepository;

    private final StatusTaxonomiaRepository statusTaxonomiaRepository;

    private final TaxonomiaRepository taxonomiaRepository;

    private final RespostaRiscoRepository respostaRiscoRepository;

    private final TipoControleRepository tipoControleRepository;

    private final ObjetivoControleRepository objetivoControleRepository;

    private final PlanoControleRepository planoControleRepository;

    private final HistoricoEventoRiscoRepository historicoEventoRiscoRepository;

    private final ProcessoMapper processoMapper;

    private final TipoMatrizMapper tipoMatrizMapper;

    private final EventoRiscoMapper eventoRiscoMapper;

    private final EventoMapper eventoMapper;

    private final CausaMapper causaMapper;

    private final ConsequenciaMapper consequenciaMapper;

    private final CategoriaMapper categoriaMapper;

    private final NaturezaMapper naturezaMapper;

    private final PerfilMapper perfilMapper;

    private final EventoCausaMapper eventoCausaMapper;

    private final EventoConsequenciaMapper eventoConsequenciaMapper;

    private final OrgaoMapper orgaoMapper;

    private final StatusProcessoMapper statusProcessoMapper;

    private final ArquivoAnexoMapper arquivoAnexoMapper;

    private final ControleMapper controleMapper;

    private final DesenhoMapper desenhoMapper;

    private final OperacaoMapper operacaoMapper;

    private final ControleEventoMapper controleEventoMapper;

    private final CalculadoraMapper calculadoraMapper;

    private final TaxonomiaMapper taxonomiaMapper;

    private final RespostaRiscoMapper respostaRiscoMapper;

    private final TipoControleMapper tipoControleMapper;

    private final ObjetivoControleMapper objetivoControleMapper;

    private final PlanoControleMapper planoControleMapper;

    private final MacroprocessoRepository macroprocessoRepository;

    private final ImpactoCalculadoraRepository impactoCalculadoraRepository;

    private final MacroprocessoMapper macroprocessoMapper;

    private final DecisaoProcessoRepository decisaoProcessoRepository;

    private final DecisaoProcessoMapper decisaoProcessoMapper;

    private final MailService mailService;

    private final MonitoramentoService monitoramentoService;

    @Value("${mail.remetente}")
    private String remetente;

    @Value("${mail.destinatario}")
    private String destinatario;

    @Autowired
    public ProcessoServiceImpl(ProcessoRepository processoRepository, StatusProcessoRepository statusProcessoRepository, UsuarioRepository usuarioRepository, TipoMatrizRepository tipoMatrizRepository, PerfilRepository perfilRepository, PermissaoRepository permissaoRepository, EventoRiscoRepository eventoRiscoRepository, IdentificacaoRepository identificacaoRepository, OrgaoRepository orgaoRepository, EventoRepository eventoRepository, EventoCustomRepositorio eventoCustomRepositorio, CausaRepository causaRepository, CausaCustomRepositorio causaCustomRepositorio, ConsequenciaRepository consequenciaRepository, ConsequenciaCustomRepositorio consequenciaCustomRepositorio, CategoriaRepository categoriaRepository, NaturezaRepository naturezaRepository, EventoCausaRepository eventoCausaRepository, EventoConsequenciaRepository eventoConsequenciaRepository, OrgaoCustomRepositorio orgaoCustomRepositorio, ProcessoCustomRepositorio processoCustomRepositorio, ArquivoAnexoRepository arquivoAnexoRepository, CalculadoraRepository calculadoraRepository, ControleCustomRepositorio controleCustomRepositorio, ControleRepository controleRepository, DesenhoRepository desenhoRepository, OperacaoRepository operacaoRepository, AvaliacaoRepository avaliacaoRepository, ControleEventoRepository controleEventoRepository, CalculoRiscoRepository calculoRiscoRepository, TipoTaxonomiaRepository tipoTaxonomiaRepository, StatusTaxonomiaRepository statusTaxonomiaRepository, TaxonomiaRepository taxonomiaRepository, RespostaRiscoRepository respostaRiscoRepository, TipoControleRepository tipoControleRepository, ObjetivoControleRepository objetivoControleRepository, PlanoControleRepository planoControleRepository, HistoricoEventoRiscoRepository historicoEventoRiscoRepository, ProcessoMapper processoMapper, TipoMatrizMapper tipoMatrizMapper, EventoRiscoMapper eventoRiscoMapper, EventoMapper eventoMapper, CausaMapper causaMapper, ConsequenciaMapper consequenciaMapper, CategoriaMapper categoriaMapper, NaturezaMapper naturezaMapper, PerfilMapper perfilMapper, EventoCausaMapper eventoCausaMapper, EventoConsequenciaMapper eventoConsequenciaMapper, OrgaoMapper orgaoMapper, StatusProcessoMapper statusProcessoMapper, ArquivoAnexoMapper arquivoAnexoMapper, ControleMapper controleMapper, DesenhoMapper desenhoMapper, OperacaoMapper operacaoMapper, ControleEventoMapper controleEventoMapper, CalculadoraMapper calculadoraMapper, TaxonomiaMapper taxonomiaMapper, RespostaRiscoMapper respostaRiscoMapper, TipoControleMapper tipoControleMapper, ObjetivoControleMapper objetivoControleMapper, PlanoControleMapper planoControleMapper, MacroprocessoRepository macroprocessoRepository, MacroprocessoMapper macroprocessoMapper, ImpactoCalculadoraRepository impactoCalculadoraRepository, DecisaoProcessoRepository decisaoProcessoRepository, DecisaoProcessoMapper decisaoProcessoMapper, MailService mailService, MonitoramentoService monitoramentoService) {
        this.processoRepository = processoRepository;
        this.statusProcessoRepository = statusProcessoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoMatrizRepository = tipoMatrizRepository;
        this.perfilRepository = perfilRepository;
        this.permissaoRepository = permissaoRepository;
        this.eventoRiscoRepository = eventoRiscoRepository;
        this.identificacaoRepository = identificacaoRepository;
        this.orgaoRepository = orgaoRepository;
        this.eventoRepository = eventoRepository;
        this.eventoCustomRepositorio = eventoCustomRepositorio;
        this.causaRepository = causaRepository;
        this.causaCustomRepositorio = causaCustomRepositorio;
        this.consequenciaRepository = consequenciaRepository;
        this.consequenciaCustomRepositorio = consequenciaCustomRepositorio;
        this.categoriaRepository = categoriaRepository;
        this.naturezaRepository = naturezaRepository;
        this.eventoCausaRepository = eventoCausaRepository;
        this.eventoConsequenciaRepository = eventoConsequenciaRepository;
        this.orgaoCustomRepositorio = orgaoCustomRepositorio;
        this.processoCustomRepositorio = processoCustomRepositorio;
        this.arquivoAnexoRepository = arquivoAnexoRepository;
        this.calculadoraRepository = calculadoraRepository;
        this.controleCustomRepositorio = controleCustomRepositorio;
        this.controleRepository = controleRepository;
        this.desenhoRepository = desenhoRepository;
        this.operacaoRepository = operacaoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.controleEventoRepository = controleEventoRepository;
        this.calculoRiscoRepository = calculoRiscoRepository;
        this.tipoTaxonomiaRepository = tipoTaxonomiaRepository;
        this.statusTaxonomiaRepository = statusTaxonomiaRepository;
        this.taxonomiaRepository = taxonomiaRepository;
        this.respostaRiscoRepository = respostaRiscoRepository;
        this.tipoControleRepository = tipoControleRepository;
        this.objetivoControleRepository = objetivoControleRepository;
        this.planoControleRepository = planoControleRepository;
        this.historicoEventoRiscoRepository = historicoEventoRiscoRepository;
        this.processoMapper = processoMapper;
        this.tipoMatrizMapper = tipoMatrizMapper;
        this.eventoRiscoMapper = eventoRiscoMapper;
        this.eventoMapper = eventoMapper;
        this.causaMapper = causaMapper;
        this.consequenciaMapper = consequenciaMapper;
        this.categoriaMapper = categoriaMapper;
        this.naturezaMapper = naturezaMapper;
        this.perfilMapper = perfilMapper;
        this.eventoCausaMapper = eventoCausaMapper;
        this.eventoConsequenciaMapper = eventoConsequenciaMapper;
        this.orgaoMapper = orgaoMapper;
        this.statusProcessoMapper = statusProcessoMapper;
        this.arquivoAnexoMapper = arquivoAnexoMapper;
        this.controleMapper = controleMapper;
        this.desenhoMapper = desenhoMapper;
        this.operacaoMapper = operacaoMapper;
        this.controleEventoMapper = controleEventoMapper;
        this.calculadoraMapper = calculadoraMapper;
        this.taxonomiaMapper = taxonomiaMapper;
        this.respostaRiscoMapper = respostaRiscoMapper;
        this.tipoControleMapper = tipoControleMapper;
        this.objetivoControleMapper = objetivoControleMapper;
        this.planoControleMapper = planoControleMapper;
        this.macroprocessoRepository = macroprocessoRepository;
        this.macroprocessoMapper = macroprocessoMapper;
        this.impactoCalculadoraRepository = impactoCalculadoraRepository;
        this.decisaoProcessoRepository = decisaoProcessoRepository;
        this.decisaoProcessoMapper = decisaoProcessoMapper;
        this.mailService = mailService;
        this.monitoramentoService = monitoramentoService;
    }

    /**
     * Save a processo.
     *
     * @param processoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProcessoDTO save(ProcessoDTO processoDTO) {
        log.debug("Request to save Processo : {}", processoDTO);
        Processo processo = processoMapper.processoDTOToProcesso(processoDTO);
        String search = "";

        if (processo.getMacroprocesso() != null && processo.getMacroprocesso().getDescricao() != null) {
            processo.setMacroprocesso(saveMacroprocesso(processo.getMacroprocesso().getDescricao()));
            search += StringUtil.removerAcento(processo.getMacroprocesso().getDescricao()) + " ";
        }
        if (processo.getProcesso() != null) {
            search += StringUtil.removerAcento(processo.getProcesso());
        }
        processo.setSearch(search);

        if (processo.getId() == null) {
            StatusProcesso statusNaoFinalizado = statusProcessoRepository.findByNomeIgnoreCase("não finalizado");
            processo.setStatus(statusNaoFinalizado);
            processo.setDtCadastro(Calendar.getInstance());
        }

        processo.getAnalise().setProcesso(processo);
        for (MatrizSwot matrizSwot : processo.getAnalise().getMatrizes()) {
            matrizSwot.setAnalise(processo.getAnalise());
        }

        for (Responsavel responsavel : processo.getResponsaveis()) {
            responsavel.setProcesso(processo);
            responsavel.setUsuario(new Usuario(responsavel.getUsuario().getId()));
        }

        if (processo.getIdentificacao() == null || processo.getIdentificacao().getId() == null) {
            processo.setIdentificacao(new Identificacao(processo));
        } else {
            processo.getIdentificacao().setProcesso(processo);
        }

        if (processo.getAvaliacao() == null || processo.getAvaliacao().getId() == null) {
            processo.setAvaliacao(new Avaliacao(processo));
        } else {
            processo.getAvaliacao().setProcesso(processo);
        }

        if (processo.getCalculadora() == null || processo.getCalculadora().getId() == null) {
            Calculadora calculadora = calculadoraRepository.findTop1ByCalculadoraBaseTrueOrderByIdAsc();

            processo.setCalculadora(new Calculadora(calculadora));
        } else {
            processo.getCalculadora().setCalculadoraBase(false);

            for (ImpactoCalculadora impactoCalculadora : processo.getCalculadora().getImpactos()) {
                impactoCalculadora.setCalculadora(processo.getCalculadora());
            }
            for (ProbabilidadeCalculadora probabilidadeCalculadora : processo.getCalculadora().getProbabilidades()) {
                probabilidadeCalculadora.setCalculadora(processo.getCalculadora());
            }
        }

        if (processo.getAnexos() != null) {
            for (ProcessoAnexo anexo : processo.getAnexos()) {
                anexo.setProcesso(processo);
            }
        }

        processo = processoRepository.save(processo);
        return processoMapper.processoToProcessoDTO(processo);
    }

    /**
     * Get all the processos.
     *
     * @param pageable  the pagination information
     * @param orgao     - Orgão da analise
     * @param descricao - Descrição do Macroprocesso ou processo
     * @param statusId  - Identificador do status do processo
     * @param inicio    - Data de Inicio do processo
     * @param fim       - Data de fim do processo
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProcessoListagemDTO> findAll(Pageable pageable, String cpf, String orgao, String descricao, Long statusId, Long inicio, Long fim) {
        log.debug("Request to get all Processos");

        Perfil analista = null;
        Perfil gestor = null;
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, NUCLEO);
        Perfil subcomite = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Subcomitê");
        Perfil comite = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Comitê");

        if (unidade == null && nucleo == null && subcomite == null && comite == null) {
            gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, GESTOR_DO_PROCESSO);
            analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, ANALISTA_DE_RISCO);
        }

        String search = StringUtil.removerAcento(descricao);
        if (search != null) {
            search = search.replaceAll("/", " ");
        }

        return processoCustomRepositorio.findByFilters(pageable, cpf, orgao, search, statusId, inicio, fim, gestor != null, analista != null);
    }

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProcessoDTO findOne(Long id) {
        log.debug("Request to get Processo : {}", id);
        Processo processo = processoRepository.findOne(id);
        return processoMapper.processoToProcessoDTO(processo);
    }

    /**
     * Delete the  processo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Processo : {}", id);
        processoRepository.delete(id);
    }

    @Override
    public List<UsuarioDTO> findAllAnalistas() {
        Orgao orgaoUsuarioLogado = orgaoRepository.findByUsuarioCPF(UsuarioUtil.getCpfUsuarioLogado());
        Perfil perfil = perfilRepository.findByNomeIgnoreCase(ANALISTA_DE_RISCO);
        List<Usuario> usuarios = usuarioRepository.findByPerfilAndOrgaoId(perfil.getId(), orgaoUsuarioLogado.getId());
        List<UsuarioDTO> result = new ArrayList<>(0);
        usuarios.forEach(usuario -> result.add(new UsuarioDTO(usuario)));
        return result;
    }

    @Override
    public List<UsuarioDTO> findAllGestores() {
        Orgao orgaoUsuarioLogado = orgaoRepository.findByUsuarioCPF(UsuarioUtil.getCpfUsuarioLogado());
        Perfil perfil = perfilRepository.findByNomeIgnoreCase(GESTOR_DO_PROCESSO);
        List<Usuario> usuarios = usuarioRepository.findByPerfilAndOrgaoId(perfil.getId(), orgaoUsuarioLogado.getId());
        List<UsuarioDTO> result = new ArrayList<>(0);
        usuarios.forEach(usuario -> result.add(new UsuarioDTO(usuario)));
        return result;
    }

    @Override
    public List<TipoMatrizDTO> findAllTiposMatriz() {
        List<TipoMatriz> result = tipoMatrizRepository.findAll();
        return tipoMatrizMapper.tipoMatrizsToTipoMatrizDTOs(result);
    }

    @Override
    public OrgaosDTO findOrgaosByCPF(String cpf) {
        Orgao secretaria = orgaoRepository.findByUsuarioCPF(cpf);
        Orgao orgao = secretaria.getOrgaoPai();

        if (orgao == null) {
            orgao = secretaria;
        }

        OrgaosDTO dto = new OrgaosDTO();
        dto.setOrgao(orgaoMapper.orgaoToOrgaoDTO(orgao));
        dto.setSecretaria(orgaoMapper.orgaoToOrgaoDTO(secretaria));
        return dto;
    }

    @Override
    public PermissaoProcessoDTO findPermissaoByCPF(String cpf) {
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, ANALISTA_DE_RISCO);
        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, GESTOR_DO_PROCESSO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, NUCLEO);
        Perfil subcomite = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Subcomitê");
        Perfil comite = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Comitê");

        PermissaoProcessoDTO permissaoProcessoDTO = new PermissaoProcessoDTO();
        permissaoProcessoDTO.setCriar(gestor != null || analista != null || unidade != null);
        permissaoProcessoDTO.setValidar(gestor != null);
        permissaoProcessoDTO.setExcluir(unidade != null);
        permissaoProcessoDTO.setEnviarParaValidacao(analista != null);
        permissaoProcessoDTO.setCriarEventoRisco(gestor != null || analista != null);

        if (nucleo != null || subcomite != null || comite != null) {
            permissaoProcessoDTO.setConsultarUnidade(true);
        }

        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        permissaoProcessoDTO.setOrgao(orgaoMapper.orgaoToOrgaoDTO(orgao));

        return permissaoProcessoDTO;
    }

    /**
     * Get all the eventos de risco.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventoRiscoDTO> findAllEventosRisco(Pageable pageable, Long processoId) {
        log.debug("Request to get all eventos de risco");

        Page<EventoRisco> result = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId, pageable);
        return result.map(eventoRiscoMapper::eventoRiscoToEventoRiscoDTO);
    }

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EventoRiscoDTO findEventoRisco(Long id) {
        log.debug("Request to get Processo : {}", id);
        EventoRisco eventoRisco = eventoRiscoRepository.findOne(id);
        return eventoRiscoMapper.eventoRiscoToEventoRiscoDTO(eventoRisco);
    }

    @Override
    public EventoRiscoDTO saveEventoRisco(EventoRiscoDTO eventoRiscoDTO, String cpf) {
        EventoRisco eventoRisco = eventoRiscoMapper.eventoRiscoDTOToEventoRisco(eventoRiscoDTO);
        eventoRisco.setIdentificacao(identificacaoRepository.findByProcessoId(eventoRiscoDTO.getIdentificacao().getProcesso().getId()));

        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        HistoricoEventoRisco historicoEventoRisco;

        if (eventoRisco.getEvento().getId() == null) {
            eventoRisco.setEvento(this.saveEvento(eventoRisco.getEvento().getDescricao(), cpf));

            historicoEventoRisco = this.saveHistoricoEvento(eventoRisco);
        } else {
            historicoEventoRisco = this.atualizaEventoRisco(eventoRisco);
        }

        for (EventoCausa eventoCausa : eventoRisco.getCausas()) {
            eventoCausa = verificaEventoCausa(eventoCausa, cpf);
            eventoCausa.setEventoRisco(eventoRisco);
        }

        for (EventoConsequencia eventoConsequencia : eventoRisco.getConsequencias()) {
            eventoConsequencia = verificaEventoConsequencia(eventoConsequencia, cpf);
            eventoConsequencia.setEventoRisco(eventoRisco);
        }

        if (eventoRisco.getControleEventos() != null && !eventoRisco.getControleEventos().isEmpty()) {
            for (ControleEvento controleEvento : eventoRisco.getControleEventos()) {
                controleEvento.setEventoRisco(eventoRisco);

                Controle controle = controleEvento.getControle();
                controle.setStatus(true);
                controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));

                if (controleEvento.getControle().getId() == null) {
                    controle.setOrgao(orgao);
                }

                controle = controleRepository.save(controle);
                controleEvento.setControle(controle);
            }
        }

        if (eventoRisco.getControles() != null) {
            for (PlanoControle planoControle : eventoRisco.getControles()) {
                planoControle.setEventoRisco(eventoRisco);
                if (planoControle.getControle().getId() == null) {
                    Controle controle = planoControle.getControle();
                    controle.setOrgao(orgao);
                    controle.setStatus(true);
                    controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
                    controle = controleRepository.save(controle);

                    planoControle.setControle(controle);
                }
            }
        }

        eventoRisco = eventoRiscoRepository.save(eventoRisco);

        if (historicoEventoRisco != null) {
            historicoEventoRiscoRepository.save(historicoEventoRisco);
        }

        alterarStatusProcessoNaoFinalizado(eventoRiscoDTO.getIdentificacao().getProcesso().getId());

        verificarCalculoRisco(eventoRisco);
        return eventoRiscoMapper.eventoRiscoToEventoRiscoDTO(eventoRisco);
    }

    private EventoCausa verificaEventoCausa(EventoCausa eventoCausa, String cpf) {
        if (eventoCausa.getCausa().getId() == null) {
            Causa causa = saveCausa(eventoCausa.getCausa().getDescricao(), cpf);
            eventoCausa.setCausa(causa);
        } else {
            causaRepository.save(eventoCausa.getCausa());
        }

        return eventoCausa;
    }

    private EventoConsequencia verificaEventoConsequencia(EventoConsequencia eventoConsequencia, String cpf) {
        if (eventoConsequencia.getConsequencia().getId() == null) {
            Consequencia consequencia = saveConsequencia(eventoConsequencia.getConsequencia().getDescricao(), cpf);
            eventoConsequencia.setConsequencia(consequencia);
        } else {
            consequenciaRepository.save(eventoConsequencia.getConsequencia());
        }

        return eventoConsequencia;
    }

    @Override
    public void deleteEventoRisco(Long id) {
        log.debug("Request to delete Evento Risco : {}", id);
        alterarStatusProcessoNaoFinalizado(processoRepository.findByEventoRiscoId(id).getId());
        eventoRiscoRepository.delete(id);
    }

    @Override
    public List<EventoDTO> findEventoByDescricaoAndCPF(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        List<Evento> result = eventoCustomRepositorio.findBySearchAndOrgaoId(descricao, orgao.getId());
        return eventoMapper.eventosToEventoDTOs(result);
    }

    @Override
    public List<CausaDTO> findCausaByDescricaoAndCPF(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        List<Causa> result = causaCustomRepositorio.findBySearchAndOrgaoId(descricao, orgao.getId());
        return causaMapper.causasToCausaDTOs(result);
    }

    @Override
    public List<ConsequenciaDTO> findConsequenciaByDescricaoAndCPF(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        List<Consequencia> result = consequenciaCustomRepositorio.findBySearchAndOrgaoId(descricao, orgao.getId());
        return consequenciaMapper.consequenciasToConsequenciaDTOs(result);
    }

    @Override
    public List<CategoriaDTO> findAllCategoria() {
        List<Categoria> result = categoriaRepository.findByStatusTrueOrderByDescricaoAsc();
        return categoriaMapper.categoriasToCategoriaDTOs(result);
    }

    @Override
    public List<NaturezaDTO> findAllNatureza() {
        List<Natureza> result = naturezaRepository.findByStatusTrueOrderByDescricaoAsc();
        return naturezaMapper.naturezasToNaturezaDTOs(result);
    }

    @Override
    public PerfilDTO verificarEvento(EventoRiscoDTO eventoRiscoDTO) {
        EventoRisco eventoRisco = eventoRiscoMapper.eventoRiscoDTOToEventoRisco(eventoRiscoDTO);
        EventoRisco eventoRiscoPersisted = eventoRiscoRepository.findOne(eventoRiscoDTO.getId());
        Evento evento = eventoRepository.findOne(eventoRiscoDTO.getEvento().getId());

        if (!eventoRiscoDTO.getEvento().getId().equals(eventoRiscoPersisted.getEvento().getId())) {
            Evento novoEvento = new Evento();
            novoEvento.setId(eventoRiscoDTO.getEvento().getId());
            eventoRiscoPersisted.setEvento(novoEvento);

            eventoRiscoRepository.save(eventoRiscoPersisted);
            return null;
        }
        if (eventoRiscoDTO.getEvento().getDescricao().equals(eventoRiscoPersisted.getEvento().getDescricao())) {
            return null;
        }

        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoRiscoDTO.getCpf(), GESTOR_DO_PROCESSO);
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoRiscoDTO.getCpf(), ANALISTA_DE_RISCO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoRiscoDTO.getCpf(), UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoRiscoDTO.getCpf(), NUCLEO);

        if (nucleo != null) {
            if (evento.getOrgao() == null) {
                return perfilMapper.perfilToPerfilDTO(nucleo);
            }
        } else if (unidade != null) {
            if (evento.getOrgao() == null) {
                saveEventoInEventoRisco(eventoRisco, eventoRiscoDTO.getCpf());
                return null;
            } else {
                if (eventoHasProcesso(eventoRisco, evento)) {
                    return null;
                } else {
                    return perfilMapper.perfilToPerfilDTO(unidade);
                }
            }
        } else if (gestor != null || analista != null) {
            if (evento.getOrgao() == null) {
                saveEventoInEventoRisco(eventoRisco, eventoRiscoDTO.getCpf());
                return null;
            } else {
                if (eventoHasProcesso(eventoRisco, evento)) {
                    return null;
                } else {
                    saveEventoInEventoRisco(eventoRisco, eventoRiscoDTO.getCpf());
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public EventoDTO atualizarEvento(EventoDTO eventoDTO) {
        Evento evento = eventoRepository.findOne(eventoDTO.getId());
        evento.setDescricao(eventoDTO.getDescricao());
        evento.setSearch(StringUtil.removerAcento(evento.getDescricao()));
        evento = eventoRepository.save(evento);
        return eventoMapper.eventoToEventoDTO(evento);
    }

    @Override
    public EventoRiscoDTO substituirEvento(EventoRiscoDTO eventoRiscoDTO) {
        EventoRisco eventoRisco = eventoRiscoRepository.findOne(eventoRiscoDTO.getId());

        Evento evento = saveEvento(eventoRiscoDTO.getEvento().getDescricao(), eventoRiscoDTO.getCpf());

        eventoRisco.setEvento(evento);
        eventoRisco = eventoRiscoRepository.save(eventoRisco);

        return eventoRiscoMapper.eventoRiscoToEventoRiscoDTO(eventoRisco);
    }

    @Override
    public PerfilDTO verificarCausa(EventoCausaDTO eventoCausaDTO) {
        EventoCausa eventoCausa = eventoCausaMapper.eventoCausaDTOToEventoCausa(eventoCausaDTO);
        EventoCausa eventoCausaPersisted = eventoCausaRepository.findOne(eventoCausaDTO.getId());
        Causa causa = causaRepository.findOne(eventoCausaDTO.getCausa().getId());

        if (!eventoCausaDTO.getCausa().getId().equals(eventoCausaPersisted.getCausa().getId())) {
            Causa novaCausa = new Causa();
            novaCausa.setId(eventoCausaDTO.getCausa().getId());
            eventoCausaPersisted.setCausa(novaCausa);

            eventoCausaRepository.save(eventoCausaPersisted);
            return null;
        }
        if (eventoCausaDTO.getCausa().getDescricao().equals(eventoCausaPersisted.getCausa().getDescricao())) {
            return null;
        }

        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoCausaDTO.getCpf(), GESTOR_DO_PROCESSO);
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoCausaDTO.getCpf(), ANALISTA_DE_RISCO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoCausaDTO.getCpf(), UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoCausaDTO.getCpf(), NUCLEO);

        if (nucleo != null) {
            return perfilMapper.perfilToPerfilDTO(nucleo);
        } else if (unidade != null) {
            if (causa.getOrgao() == null) {
                saveCausaInEventoCausa(eventoCausa, eventoCausaDTO.getCpf());
                return null;
            } else {
                if (causaHasProcesso(eventoCausa, causa)) {
                    return null;
                } else {
                    return perfilMapper.perfilToPerfilDTO(unidade);
                }
            }
        } else if (gestor != null || analista != null) {
            if (causa.getOrgao() == null) {
                saveCausaInEventoCausa(eventoCausa, eventoCausaDTO.getCpf());
                return null;
            } else {
                if (causaHasProcesso(eventoCausa, causa)) {
                    return null;
                } else {
                    saveCausaInEventoCausa(eventoCausa, eventoCausaDTO.getCpf());
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public CausaDTO atualizarCausa(CausaDTO causaDTO) {
        Causa causa = causaRepository.findOne(causaDTO.getId());
        causa.setDescricao(causaDTO.getDescricao());
        causa.setSearch(StringUtil.removerAcento(causa.getDescricao()));
        causa.setStatus(true);
        causa = causaRepository.save(causa);
        return causaMapper.causaToCausaDTO(causa);
    }

    @Override
    public EventoCausaDTO substituirCausa(EventoCausaDTO eventoCausaDTO) {
        EventoCausa eventoCausa = eventoCausaRepository.findOne(eventoCausaDTO.getId());

        Causa causa = saveCausa(eventoCausaDTO.getCausa().getDescricao(), eventoCausaDTO.getCpf());

        eventoCausa.setCausa(causa);
        eventoCausa = eventoCausaRepository.save(eventoCausa);

        return eventoCausaMapper.eventoCausaToEventoCausaDTO(eventoCausa);
    }

    @Override
    public PerfilDTO verificarConsequencia(EventoConsequenciaDTO eventoConsequenciaDTO) {
        EventoConsequencia eventoConsequencia = eventoConsequenciaMapper.eventoConsequenciaDTOToEventoConsequencia(eventoConsequenciaDTO);
        EventoConsequencia eventoConsequenciaPersisted = eventoConsequenciaRepository.findOne(eventoConsequenciaDTO.getId());
        Consequencia consequencia = consequenciaRepository.findOne(eventoConsequenciaDTO.getConsequencia().getId());

        if (!eventoConsequenciaDTO.getConsequencia().getId().equals(eventoConsequenciaPersisted.getConsequencia().getId())) {
            Consequencia novaConsequencia = new Consequencia();
            novaConsequencia.setId(eventoConsequenciaDTO.getConsequencia().getId());
            eventoConsequenciaPersisted.setConsequencia(novaConsequencia);

            eventoConsequenciaRepository.save(eventoConsequenciaPersisted);
            return null;
        }
        if (eventoConsequenciaDTO.getConsequencia().getDescricao().equals(eventoConsequenciaPersisted.getConsequencia().getDescricao())) {
            return null;
        }

        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoConsequenciaDTO.getCpf(), GESTOR_DO_PROCESSO);
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoConsequenciaDTO.getCpf(), ANALISTA_DE_RISCO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoConsequenciaDTO.getCpf(), UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoConsequenciaDTO.getCpf(), NUCLEO);

        if (nucleo != null) {
            return perfilMapper.perfilToPerfilDTO(nucleo);
        } else if (unidade != null) {
            if (consequencia.getOrgao() == null) {
                saveConsequenciaInEventoConsequencia(eventoConsequencia, eventoConsequenciaDTO.getCpf());
                return null;
            } else {
                if (consequenciaHasProcesso(eventoConsequencia, consequencia)) {
                    return null;
                } else {
                    return perfilMapper.perfilToPerfilDTO(unidade);
                }
            }
        } else if (gestor != null || analista != null) {
            if (consequencia.getOrgao() == null) {
                saveConsequenciaInEventoConsequencia(eventoConsequencia, eventoConsequenciaDTO.getCpf());
                return null;
            } else {
                if (consequenciaHasProcesso(eventoConsequencia, consequencia)) {
                    return null;
                } else {
                    saveConsequenciaInEventoConsequencia(eventoConsequencia, eventoConsequenciaDTO.getCpf());
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public ConsequenciaDTO atualizarConsequencia(ConsequenciaDTO consequenciaDTO) {
        Consequencia consequencia = consequenciaRepository.findOne(consequenciaDTO.getId());
        consequencia.setDescricao(consequenciaDTO.getDescricao());
        consequencia.setSearch(StringUtil.removerAcento(consequencia.getDescricao()));
        consequencia.setStatus(true);
        consequencia = consequenciaRepository.save(consequencia);
        return consequenciaMapper.consequenciaToConsequenciaDTO(consequencia);
    }

    @Override
    public EventoConsequenciaDTO substituirConsequencia(EventoConsequenciaDTO eventoConsequenciaDTO) {
        EventoConsequencia eventoConsequencia = eventoConsequenciaRepository.findOne(eventoConsequenciaDTO.getId());

        Consequencia consequencia = saveConsequencia(eventoConsequenciaDTO.getConsequencia().getDescricao(), eventoConsequenciaDTO.getCpf());

        eventoConsequencia.setConsequencia(consequencia);
        eventoConsequencia = eventoConsequenciaRepository.save(eventoConsequencia);

        return eventoConsequenciaMapper.eventoConsequenciaToEventoConsequenciaDTO(eventoConsequencia);
    }

    @Override
    public List<String> searchOrgaoByNome(String nome) {
        return orgaoCustomRepositorio.searchByNomeAndOrgaoMP(nome);
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        String cpf = UsuarioUtil.getCpfUsuarioLogado();
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, NUCLEO);

        if (nucleo != null) {
            return processoCustomRepositorio.searchByDescricao(descricao, null);
        } else {
            Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
            return processoCustomRepositorio.searchByDescricao(descricao, orgao.getId());
        }

    }

    @Override
    public List<StatusProcessoDTO> getStatus() {
        List<StatusProcesso> result = statusProcessoRepository.findAll();
        return statusProcessoMapper.statusProcessosToStatusProcessoDTOs(result);
    }

    @Override
    public List<ArquivoAnexoDTO> uploadFile(MultipartFile[] arquivos, String[] nomes) throws IOException {
        List<ArquivoAnexo> anexos = new ArrayList<>(0);
        int index = 0;

        for (MultipartFile arquivo : arquivos) {
            ArquivoAnexo arquivoAnexo = new ArquivoAnexo();
            arquivoAnexo.setArquivo(arquivo.getBytes());
            arquivoAnexo.setTamanho(arquivoAnexo.getArquivo().length);
            arquivoAnexo.setNomeArquivo(nomes[index]);
            arquivoAnexo.setNomeDocumento(nomes[index]);
            arquivoAnexo.setDataUpload(new Date());

            arquivoAnexo = arquivoAnexoRepository.save(arquivoAnexo);

            anexos.add(arquivoAnexo);
            index++;
        }

        return arquivoAnexoMapper.arquivoAnexosToArquivoAnexoDTOs(anexos);
    }

    @Override
    public ArquivoAnexo getAnexoById(Long id) {
        return arquivoAnexoRepository.findOne(id);
    }

    @Override
    public List<ControleDTO> findControleByDescricaoAndCPF(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        List<Controle> result = controleCustomRepositorio.findBySearchAndOrgaoId(descricao, orgao.getId());
        return controleMapper.controlesToControleDTOs(result);
    }

    @Override
    public List<DesenhoDTO> findAllDesenhos() {
        List<Desenho> result = desenhoRepository.findByStatusTrueOrderByDescricaoAsc();
        return desenhoMapper.desenhosToDesenhoDTOs(result);
    }

    @Override
    public List<OperacaoDTO> findAllOperacoes() {
        List<Operacao> result = operacaoRepository.findByStatusTrueOrderByDescricaoAsc();
        return operacaoMapper.operacaosToOperacaoDTOs(result);
    }

    @Override
    public PerfilDTO verificarControle(ControleEventoDTO controleEventoDTO) {
        ControleEvento controleEvento = controleEventoMapper.controleEventoDTOToControleEvento(controleEventoDTO);
        ControleEvento controleEventoPersisted = controleEventoRepository.findOne(controleEventoDTO.getId());
        Controle controle = controleRepository.findOne(controleEventoDTO.getControle().getId());

        atualizarControleEvento(controleEventoPersisted, controleEventoDTO);

        if (!controleEventoDTO.getControle().getId().equals(controleEventoPersisted.getControle().getId())) {
            Controle novaControle = new Controle();
            novaControle.setId(controleEventoDTO.getControle().getId());
            controleEventoPersisted.setControle(novaControle);

            controleEventoRepository.save(controleEventoPersisted);
            return null;
        }
        if (controleEventoDTO.getControle().getDescricao().equals(controleEventoPersisted.getControle().getDescricao())) {
            return null;
        }

        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(controleEventoDTO.getCpf(), GESTOR_DO_PROCESSO);
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(controleEventoDTO.getCpf(), ANALISTA_DE_RISCO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(controleEventoDTO.getCpf(), UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(controleEventoDTO.getCpf(), NUCLEO);

        if (nucleo != null) {
            if (controle.getOrgao() == null) {
                return perfilMapper.perfilToPerfilDTO(nucleo);
            }
        } else if (unidade != null) {
            if (controle.getOrgao() == null) {
                saveControleInControleEvento(controleEvento, controleEventoDTO.getCpf());
                return null;
            } else {
                if (controleHasProcesso(controleEvento, controle)) {
                    return null;
                } else {
                    return perfilMapper.perfilToPerfilDTO(unidade);
                }
            }
        } else if (gestor != null || analista != null) {
            if (controle.getOrgao() == null) {
                saveControleInControleEvento(controleEvento, controleEventoDTO.getCpf());
                return null;
            } else {
                if (controleHasProcesso(controleEvento, controle)) {
                    return null;
                } else {
                    saveControleInControleEvento(controleEvento, controleEventoDTO.getCpf());
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public ControleDTO atualizarControle(ControleDTO controleDTO) {
        Controle controle = controleRepository.findOne(controleDTO.getId());
        controle.setDescricao(controleDTO.getDescricao());
        controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
        controle.setStatus(true);
        controle = controleRepository.save(controle);
        return controleMapper.controleToControleDTO(controle);
    }

    @Override
    public ControleEventoDTO substituirControle(ControleEventoDTO controleEventoDTO) {
        ControleEvento controleEvento = controleEventoRepository.findOne(controleEventoDTO.getId());

        Controle controle = saveControle(controleEventoDTO.getControle().getDescricao(), controleEventoDTO.getCpf());

        controleEvento.setControle(controle);
        controleEvento = controleEventoRepository.save(controleEvento);

        return controleEventoMapper.controleEventoToControleEventoDTO(controleEvento);
    }

    @Override
    public CalculadoraDTO getCalculadoraByProcessoId(Long processoId) {
        Calculadora result = calculadoraRepository.findByProcessoId(processoId);
        return calculadoraMapper.calculadoraToCalculadoraDTO(result);
    }

    @Override
    public List<EventoRiscoDTO> findAllEventosRisco(Long processoId) {
        List<EventoRisco> result = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId);
        return eventoRiscoMapper.eventoRiscosToEventoRiscoDTOs(result);
    }

    @Override
    public Void salvarCalculo(IdentificacaoDTO identificacaoDTO) {
        List<EventoRisco> eventos = eventoRiscoMapper.eventoRiscoDTOsToEventoRiscos(identificacaoDTO.getEventos());

        for (EventoRisco evento : eventos) {
            verificarCalculoRisco(evento);
        }

        if (!eventos.isEmpty()) {
            this.alterarStatusProcessoNaoFinalizado(processoRepository.findByEventoRiscoId(eventos.get(0).getId()).getId());
        }

        return null;
    }

    @Override
    public Boolean getIgnorarRiscoInerenteByProcesso(Long processoId) {
        Avaliacao avaliacao = avaliacaoRepository.findByProcessoId(processoId);
        return avaliacao.getIgnorarRiscoInerente();
    }

    @Override
    public Boolean alterarIgnorarRiscoInerente(Long processoId) {
        Avaliacao avaliacao = avaliacaoRepository.findByProcessoId(processoId);
        avaliacao.setIgnorarRiscoInerente(!avaliacao.getIgnorarRiscoInerente());
        avaliacao = avaliacaoRepository.save(avaliacao);
        if (avaliacao.getIgnorarRiscoInerente()) {
            List<EventoRisco> eventos = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId);
            for (EventoRisco evento : eventos) {
                evento.setCalculoRiscoInerente(null);
            }
            eventoRiscoRepository.save(eventos);
        }
        return avaliacao.getIgnorarRiscoInerente();
    }

    @Override
    public TaxonomiaDTO salvarTaxonomiaEvento(EventoDTO eventoDTO) {
        Taxonomia taxonomia = new Taxonomia();

        if (eventoDTO.getId() == null) {
            taxonomia.setEvento(saveEvento(eventoDTO.getDescricao(), eventoDTO.getCpf()));
        } else {
            Evento eventoPersistido = eventoRepository.findOne(eventoDTO.getId());

            if (!eventoPersistido.getDescricao().equalsIgnoreCase(eventoDTO.getDescricao())) {
                taxonomia.setEvento(saveEvento(eventoDTO.getDescricao(), eventoDTO.getCpf()));
            } else {
                taxonomia.setEvento(eventoPersistido);
            }
        }

        taxonomia.setTipo(tipoTaxonomiaRepository.findByNomeIgnoreCase("Evento"));
        taxonomia.setStatus(statusTaxonomiaRepository.findByNomeIgnoreCase(NAO_AVALIADO));

        taxonomia.setDescricao(taxonomia.getEvento().getDescricao());
        taxonomia.setSearch(taxonomia.getEvento().getSearch());
        taxonomia.setDtCadastro(Calendar.getInstance());
        taxonomia.setOrgao(orgaoRepository.findByUsuarioCPF(eventoDTO.getCpf()));

        taxonomia = taxonomiaRepository.save(taxonomia);

        return taxonomiaMapper.taxonomiaToTaxonomiaDTO(taxonomia);
    }

    @Override
    public TaxonomiaDTO salvarTaxonomiaCausa(CausaDTO causaDTO) {
        Taxonomia taxonomia = new Taxonomia();

        if (causaDTO.getId() == null) {
            taxonomia.setCausa(saveCausa(causaDTO.getDescricao(), causaDTO.getCpf()));
        } else {
            Causa eventoPersistido = causaRepository.findOne(causaDTO.getId());

            if (!eventoPersistido.getDescricao().equalsIgnoreCase(causaDTO.getDescricao())) {
                taxonomia.setCausa(saveCausa(causaDTO.getDescricao(), causaDTO.getCpf()));
            } else {
                taxonomia.setCausa(eventoPersistido);
            }
        }

        taxonomia.setTipo(tipoTaxonomiaRepository.findByNomeIgnoreCase("Causa"));
        taxonomia.setStatus(statusTaxonomiaRepository.findByNomeIgnoreCase(NAO_AVALIADO));

        taxonomia.setDescricao(taxonomia.getCausa().getDescricao());
        taxonomia.setSearch(taxonomia.getCausa().getSearch());
        taxonomia.setDtCadastro(Calendar.getInstance());
        taxonomia.setOrgao(orgaoRepository.findByUsuarioCPF(causaDTO.getCpf()));

        taxonomia = taxonomiaRepository.save(taxonomia);

        return taxonomiaMapper.taxonomiaToTaxonomiaDTO(taxonomia);
    }

    @Override
    public TaxonomiaDTO salvarTaxonomiaConsequencia(ConsequenciaDTO consequenciaDTO) {
        Taxonomia taxonomia = new Taxonomia();

        if (consequenciaDTO.getId() == null) {
            taxonomia.setConsequencia(saveConsequencia(consequenciaDTO.getDescricao(), consequenciaDTO.getCpf()));
        } else {
            Consequencia eventoPersistido = consequenciaRepository.findOne(consequenciaDTO.getId());

            if (!eventoPersistido.getDescricao().equalsIgnoreCase(consequenciaDTO.getDescricao())) {
                taxonomia.setConsequencia(saveConsequencia(consequenciaDTO.getDescricao(), consequenciaDTO.getCpf()));
            } else {
                taxonomia.setConsequencia(eventoPersistido);
            }
        }

        taxonomia.setTipo(tipoTaxonomiaRepository.findByNomeIgnoreCase("Consequência"));
        taxonomia.setStatus(statusTaxonomiaRepository.findByNomeIgnoreCase(NAO_AVALIADO));

        taxonomia.setDescricao(taxonomia.getConsequencia().getDescricao());
        taxonomia.setSearch(taxonomia.getConsequencia().getSearch());
        taxonomia.setDtCadastro(Calendar.getInstance());
        taxonomia.setOrgao(orgaoRepository.findByUsuarioCPF(consequenciaDTO.getCpf()));

        taxonomia = taxonomiaRepository.save(taxonomia);

        return taxonomiaMapper.taxonomiaToTaxonomiaDTO(taxonomia);
    }

    @Override
    public TaxonomiaDTO salvarTaxonomiaControle(ControleDTO controleDTO) {
        Taxonomia taxonomia = new Taxonomia();

        if (controleDTO.getId() == null) {
            taxonomia.setControle(saveControle(controleDTO.getDescricao(), controleDTO.getCpf()));
        } else {
            Controle eventoPersistido = controleRepository.findOne(controleDTO.getId());

            if (!eventoPersistido.getDescricao().equalsIgnoreCase(controleDTO.getDescricao())) {
                taxonomia.setControle(saveControle(controleDTO.getDescricao(), controleDTO.getCpf()));
            } else {
                taxonomia.setControle(eventoPersistido);
            }
        }

        taxonomia.setTipo(tipoTaxonomiaRepository.findByNomeIgnoreCase("Controle"));
        taxonomia.setStatus(statusTaxonomiaRepository.findByNomeIgnoreCase(NAO_AVALIADO));

        taxonomia.setDescricao(taxonomia.getControle().getDescricao());
        taxonomia.setSearch(taxonomia.getControle().getSearch());
        taxonomia.setDtCadastro(Calendar.getInstance());
        taxonomia.setOrgao(orgaoRepository.findByUsuarioCPF(controleDTO.getCpf()));

        taxonomia = taxonomiaRepository.save(taxonomia);

        return taxonomiaMapper.taxonomiaToTaxonomiaDTO(taxonomia);
    }

    @Override
    public List<RespostaRiscoDTO> getTiposResposta() {
        List<RespostaRisco> result = respostaRiscoRepository.findAll();
        return respostaRiscoMapper.respostaRiscosToRespostaRiscoDTOs(result);
    }

    @Override
    public List<TipoControleDTO> getTiposControle() {
        List<TipoControle> result = tipoControleRepository.findAll();
        return tipoControleMapper.tipoControlesToTipoControleDTOs(result);
    }

    @Override
    public List<ObjetivoControleDTO> getObjetivosControle() {
        List<ObjetivoControle> result = objetivoControleRepository.findAll();
        return objetivoControleMapper.objetivoControlesToObjetivoControleDTOs(result);
    }

    @Override
    public List<MacroprocessoDTO> findAllMacroprocesso() {
        List<Macroprocesso> restul;

        String cpf = UsuarioUtil.getCpfUsuarioLogado();
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, NUCLEO);

        if (nucleo != null) {
            restul = macroprocessoRepository.findByStatusTrueOrderByDescricaoAsc();
        } else {
            Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
            restul = macroprocessoRepository.findBySecretariaIdAndStatusTrueOrderByDescricaoAsc(orgao.getId());
        }

        return macroprocessoMapper.toDto(restul);
    }

    @Override
    public Boolean verificaRiscoInerente(Long processoId) {
        Boolean valido = getIgnorarRiscoInerenteByProcesso(processoId);
        if (valido) {
            return true;
        } else {
            List<EventoRisco> eventos = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId);
            Boolean possuiFrequencia = true;
            for (EventoRisco evento : eventos) {
                if (evento.getCalculoRiscoInerente() == null || evento.getCalculoRiscoInerente().getFrequencia() == null) {
                    possuiFrequencia = false;
                }
            }
            return possuiFrequencia;
        }
    }

    @Override
    public Boolean verificaControleEvento(Long processoId) {
        List<EventoRisco> eventos = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId);
        for (EventoRisco evento : eventos) {
            if (!evento.getControleEventos().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void alterarStatusImpactoCalculadora(CalculadoraDTO calculadoraDTO) {
        Calculadora calculadora = calculadoraRepository.findOne(calculadoraDTO.getId());
        for (int i = 0; i < calculadora.getImpactos().size(); i++) {
            atualizaImpacto(calculadora.getImpactos().get(i).getId(), calculadoraDTO.getImpactos().get(i).getDesabilitado());
        }
    }

    @Override
    public void alterarStatusImpactoCalculadoras(Long processoId, CalculadoraDTO calculadoraDTO) {
        Calculadora calculadora = calculadoraRepository.findByProcessoId(processoId);

        for (int i = 0; i < calculadora.getImpactos().size(); i++) {
            atualizaImpacto(calculadora.getImpactos().get(i).getId(), calculadoraDTO.getImpactos().get(i).getDesabilitado());
        }
    }

    @Override
    public void saveRespostaRisco(Long eventoRiscoId, EventoRiscoDTO eventoRiscoDTO) {
        RespostaRisco respostaRisco = respostaRiscoMapper.respostaRiscoDTOToRespostaRisco(eventoRiscoDTO.getRespostaRisco());
        eventoRiscoRepository.saveRespostaRisco(eventoRiscoId, respostaRisco, eventoRiscoDTO.getJustificativaRespostaRisco());

        this.alterarStatusProcessoNaoFinalizado(processoRepository.findByEventoRiscoId(eventoRiscoId).getId());
    }

    @Override
    public EventoRiscoDTO findEventoWithControles(Long eventoRiscoId) {
        EventoRisco eventoRisco = eventoRiscoRepository.findOne(eventoRiscoId);
        List<PlanoControle> controles = planoControleRepository.findByEventoRiscoId(eventoRiscoId);
        EventoRiscoDTO result = eventoRiscoMapper.eventoRiscoToEventoRiscoDTO(eventoRisco);
        result.setControles(planoControleMapper.planoControlesToPlanoControleDTOs(controles));
        return result;
    }

    @Override
    public PlanoControleDTO savePlanoControle(PlanoControleDTO planoControleDTO) {
        PlanoControle planoControle = planoControleMapper.planoControleDTOToPlanoControle(planoControleDTO);

        Controle controle = saveControle(planoControle.getControle().getDescricao(), planoControleDTO.getCpf());
        planoControle.setControle(controle);

        planoControle = planoControleRepository.save(planoControle);

        this.alterarStatusProcessoNaoFinalizado(processoRepository.findByEventoRiscoId(planoControleDTO.getEventoRisco().getId()).getId());

        return planoControleMapper.planoControleToPlanoControleDTO(planoControle);
    }

    @Override
    public void deletePlanoControle(Long planoControleId) {
        this.alterarStatusProcessoNaoFinalizado(processoRepository.findByPlanoControleId(planoControleId).getId());
        planoControleRepository.delete(planoControleId);
    }

    @Override
    public ProcessoEtapaDTO getProcessoEtapa(Long processoId) {
        ProcessoEtapaDTO processoEtapaDTO = new ProcessoEtapaDTO();
        processoEtapaDTO.setAvaliacao(eventoRiscoRepository.countByIdentificacaoProcessoId(processoId) > 0);
        processoEtapaDTO.setResposta(controleEventoRepository.countByEventoRiscoIdentificacaoProcessoId(processoId) > 0 && calculoRiscoRepository.countNiveisByProcessoId(processoId) > 0);
        processoEtapaDTO.setPlano(respostaRiscoRepository.countByProcessoId(processoId) > 0);
        processoEtapaDTO.setValidacao(planoControleRepository.countByEventoRiscoIdentificacaoProcessoId(processoId) > 0);
        return processoEtapaDTO;
    }

    @Override
    public List<DecisaoProcessoDTO> findAllDecisoes() {
        List<DecisaoProcesso> result = decisaoProcessoRepository.findByOrderByNomeAsc();
        return decisaoProcessoMapper.decisaoProcessosToDecisaoProcessoDTOs(result);
    }

    @Override
    public void validarProcesso(ProcessoDTO processoDTO) {
        Processo processo = processoRepository.findOne(processoDTO.getId());
        processo.setJustificativa(processoDTO.getJustificativa());
        processo.setDecisao(decisaoProcessoMapper.decisaoProcessoDTOToDecisaoProcesso(processoDTO.getDecisao()));

        if (processo.getDecisao().getId().equals(decisaoProcessoRepository.findByNomeIgnoreCase("validar").getId())) {
            processo.setStatus(statusProcessoRepository.findByNomeIgnoreCase("finalizado"));
            processo.setDtValidacao(Calendar.getInstance());
        } else {
            processo.setStatus(statusProcessoRepository.findByNomeIgnoreCase("não finalizado"));
        }

        processoRepository.save(processo);

        mailService.enviarEmailValidacaoProcesso(processo.getId());
    }

    @Override
    public byte[] gerarRelatorioProcesso(Long id, HttpServletResponse response) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ProcessoDTO processo = processoMapper.processoToProcessoDTO(processoRepository.findOne(id));

        if (processo != null) {
            List<EventoRiscoDTO> eventosRisco = eventoRiscoMapper.eventoRiscosToEventoRiscoDTOs(eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(id));

            String nomeRelatorio = "Agatha - ".concat(processo.getAnalise().getSecretaria().getNome()).concat("_").concat(dateFormat.format(new Date())).concat(".pdf");

            byte[] relatorioBytes = criarRelatorioPdf(processo, eventosRisco);

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=\"" + nomeRelatorio + "\"");
            response.setContentLength(relatorioBytes.length);

            return relatorioBytes;
        }

        return null;
    }

    @Override
    public void deleteControleEvento(Long id) {
        controleEventoRepository.delete(id);
    }

    private byte[] criarRelatorioPdf(ProcessoDTO processo, List<EventoRiscoDTO> eventosRisco) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(arrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        ReportFooterEventHandler footerHandler = new ReportFooterEventHandler();

        Document document = ReportUtil.getInstance().criarDocumento(pdf, footerHandler, PageSize.A4);

        ReportUtil.getInstance().criarReportHeader(document, processo.getAnalise().getOrgao().getNome(), processo.getAnalise().getSecretaria().getNome(), true);

        criarRelatorioConteudo(document, processo, eventosRisco);

        footerHandler.writeTotalPages(pdf);

        document.close();

        return arrayOutputStream.toByteArray();
    }

    private void criarRelatorioConteudo(Document document, ProcessoDTO processo, List<EventoRiscoDTO> eventosRisco) throws IOException {
        document.add(criarRelatorioTableInformacoesProcesso(processo));
        document.add(criarRelatorioTableAnaliseSwot(processo));

        if (eventosRisco != null && !eventosRisco.isEmpty()) {
            document.add(new AreaBreak());
            ReportUtil.getInstance().criarReportHeader(document, processo.getAnalise().getOrgao().getNome(), processo.getAnalise().getSecretaria().getNome(), true);
            criarRelatorioTableEventosDeRisco(document, eventosRisco);
        }
    }

    private Table criarRelatorioTableInformacoesProcesso(ProcessoDTO processo) throws IOException {
        Table tableInformacoesProcesso = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 100)})
                .setWidthPercent(100);

        tableInformacoesProcesso.setMarginTop(7f);

        tableInformacoesProcesso.addCell(criarRelatorioTableCellTitle("Informações sobre o Macroprocesso/processo", 1, 1));

        tableInformacoesProcesso.addCell(this.criaHeader(processo));

        return tableInformacoesProcesso;
    }

    private Cell criaHeader(ProcessoDTO processo) throws IOException {
        Cell cellInformacoesProcesso = new Cell();

        cellInformacoesProcesso.setBorder(new SolidBorder(Color.BLACK, 0.5f));

        cellInformacoesProcesso.add(criarRelatorioTableParagraphSubTitle("Macroprocesso"));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphTexto(processo.getMacroprocesso() == null ? NAO_INFORMADO : processo.getMacroprocesso().getDescricao()));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphSubTitle("Processo"));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphTexto(processo.getProcesso() == null ? NAO_INFORMADO : processo.getProcesso()));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphSubTitle("Objetivo do Macroprocesso/Processo"));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphTexto(processo.getObjetivo() == null ? NAO_INFORMADO : processo.getObjetivo()));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphSubTitle("Leis e Regulamentos"));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphTexto(processo.getLei() == null ? NAO_INFORMADO : processo.getLei()));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphSubTitle("Secretaria"));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphTexto(processo.getAnalise().getSecretaria() == null ? NAO_INFORMADO : processo.getAnalise().getSecretaria().getNome()));

        cellInformacoesProcesso = this.criaHeaderCell(cellInformacoesProcesso, processo);

        return cellInformacoesProcesso;
    }

    private Cell criaHeaderCell(Cell cellInformacoesProcesso, ProcessoDTO processo) throws IOException {
        cellInformacoesProcesso.add(criarRelatorioTableParagraphSubTitle("Sistemas"));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphTexto(processo.getSistema() == null ? NAO_INFORMADO : processo.getSistema()));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphSubTitle("Gestor Responsável"));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphTexto(processo.getGestor() == null ? NAO_INFORMADO : processo.getGestor().getNome()));
        cellInformacoesProcesso.add(criarRelatorioTableParagraphSubTitle("Responsável pela análise"));
        if (processo.getResponsaveis() != null && !processo.getResponsaveis().isEmpty()) {
            for (ResponsavelDTO responsavelDTO : processo.getResponsaveis()) {
                cellInformacoesProcesso.add(criarRelatorioTableParagraphTexto(responsavelDTO.getUsuario().getNome()));
            }
        } else {
            cellInformacoesProcesso.add(criarRelatorioTableParagraphTexto(NAO_INFORMADO));
        }

        return cellInformacoesProcesso;
    }

    private Table criarRelatorioTableAnaliseSwot(ProcessoDTO processo) throws IOException {
        Table tableAnaliseSwot = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)})
                .setWidthPercent(100);

        tableAnaliseSwot.addCell(criarRelatorioTableCellTitle("Análise SWOT", 1, 2));

        tableAnaliseSwot.addCell(criarRelatorioTableCellFromMatrizSwotList("Forças", 1L, processo.getAnalise().getMatrizes()));
        tableAnaliseSwot.addCell(criarRelatorioTableCellFromMatrizSwotList("Oportunidades", 2L, processo.getAnalise().getMatrizes()));
        tableAnaliseSwot.addCell(criarRelatorioTableCellFromMatrizSwotList("Fraquezas", 3L, processo.getAnalise().getMatrizes()));
        tableAnaliseSwot.addCell(criarRelatorioTableCellFromMatrizSwotList("Ameaças", 4L, processo.getAnalise().getMatrizes()));

        return tableAnaliseSwot;
    }

    private Cell criarRelatorioTableCellFromMatrizSwotList(String subtitulo, Long tipoMatriz, List<MatrizSwotDTO> matrizList) throws IOException {
        Cell cell = new Cell();

        cell.setBorder(new SolidBorder(Color.BLACK, 0.5f));
        cell.add(criarRelatorioTableParagraphSubTitle(subtitulo));

        List<MatrizSwotDTO> matrizListFiltered = matrizList.stream().filter(matrizSwotDTO -> matrizSwotDTO.getTipoMatriz().getId().equals(tipoMatriz)).collect(Collectors.toList());

        if (matrizListFiltered != null && !matrizListFiltered.isEmpty()) {
            for (int i = 0; i < matrizListFiltered.size(); i++) {
                int index = i + 1;
                cell.add(criarRelatorioTableParagraphItem(index, matrizListFiltered.get(i).getDescricao()));
            }
        } else {
            cell.add(criarRelatorioTableParagraphTexto(NAO_INFORMADO));
        }

        return cell;
    }

    private void criarRelatorioTableEventosDeRisco(Document document, List<EventoRiscoDTO> eventosRisco) throws IOException {
        if (eventosRisco != null && !eventosRisco.isEmpty()) {
            Table tableEventosRisco = new Table(new UnitValue[]{
                    new UnitValue(UnitValue.PERCENT, 100)})
                    .setWidthPercent(100);

            tableEventosRisco.setMarginTop(7f);
            tableEventosRisco.addHeaderCell(criarRelatorioTableCellTitle("Eventos de risco", 1, 1));

            document.add(tableEventosRisco);

            for (int i = 0; i < eventosRisco.size(); i++) {

                document.add(this.criaTabelaEvento(eventosRisco.get(i)));

                if (i != (eventosRisco.size() - 1)) {
                    document.add(new AreaBreak());
                }
            }
        }
    }

    private Table criaTabelaEvento(EventoRiscoDTO eventoRisco) throws IOException {
        Table tableEvento = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16)})
                .setWidthPercent(100);

        tableEvento.addCell(criarRelatorioTableCellTitleLight(eventoRisco.getEvento().getDescricao(), 1, 6));

        tableEvento.addCell(this.criaCelulaCategoria(eventoRisco.getCategoria()));
        tableEvento.addCell(this.criaCelulaNivel(eventoRisco.getCalculoRiscoResidual()));
        tableEvento.addCell(this.criaCelulaResposta(eventoRisco.getRespostaRisco()));

        tableEvento.addCell(criarRelatorioTableCellFromCausas(eventoRisco.getCausas()));
        tableEvento.addCell(criarRelatorioTableCellFromConsequencias(eventoRisco.getConsequencias()));
        tableEvento.addCell(criarRelatorioTableCellFromControles(eventoRisco.getControleEventos()));
        tableEvento.addCell(criarRelatorioTableCellFromPlanosAcao(eventoRisco.getControles()));

        return tableEvento;
    }

    private Cell criaCelulaCategoria(CategoriaDTO categoria) throws IOException {
        Cell cellCategoria = new Cell(1, 2);

        cellCategoria.add(criarRelatorioTableParagraphSubTitle("Categoria de risco"));
        cellCategoria.add(criarRelatorioTableParagraphTexto(categoria == null ? NAO_INFORMADO : categoria.getDescricao()));

        return cellCategoria;
    }

    private Cell criaCelulaNivel(CalculoRiscoDTO calculoRisco) throws IOException {
        Cell cellNivel = new Cell(1, 2);

        cellNivel.add(criarRelatorioTableParagraphSubTitle("Nível de risco"));
        cellNivel.add(criarRelatorioTableParagraphTexto(calculoRisco == null || calculoRisco.getNivel() == null ? NAO_INFORMADO : getNivelRiscoDescricao(calculoRisco.getNivel().intValueExact())));

        return cellNivel;
    }

    private Cell criaCelulaResposta(RespostaRiscoDTO respostaRisco) throws IOException {
        Cell cellResposta = new Cell(1, 2);

        cellResposta.add(criarRelatorioTableParagraphSubTitle("Resposta ao risco"));
        cellResposta.add(criarRelatorioTableParagraphTexto(respostaRisco == null ? NAO_INFORMADO : respostaRisco.getNome()));

        return cellResposta;
    }

    private Cell criarRelatorioTableCellFromCausas(List<EventoCausaDTO> causas) throws IOException {
        Cell cell = new Cell(1, 3);

        cell.setBorder(new SolidBorder(Color.BLACK, 0.5f));
        cell.add(criarRelatorioTableParagraphSubTitle("Causas"));

        if (causas != null && !causas.isEmpty()) {
            for (int i = 0; i < causas.size(); i++) {
                int index = i + 1;

                cell.add(criarRelatorioTableParagraphItem(index, causas.get(i).getCausa().getDescricao()));
            }
        } else {
            cell.add(criarRelatorioTableParagraphTexto(NAO_INFORMADO));
        }

        return cell;
    }

    private Cell criarRelatorioTableCellFromConsequencias(List<EventoConsequenciaDTO> consequencias) throws IOException {
        Cell cell = new Cell(1, 3);

        cell.setBorder(new SolidBorder(Color.BLACK, 0.5f));
        cell.add(criarRelatorioTableParagraphSubTitle("Consequências"));

        if (consequencias != null && !consequencias.isEmpty()) {
            for (int i = 0; i < consequencias.size(); i++) {
                int index = i + 1;

                cell.add(criarRelatorioTableParagraphItem(index, consequencias.get(i).getConsequencia().getDescricao()));
            }
        } else {
            cell.add(criarRelatorioTableParagraphTexto(NAO_INFORMADO));
        }

        return cell;
    }

    private Cell criarRelatorioTableCellFromControles(List<ControleEventoDTO> controles) throws IOException {
        Cell cell = new Cell(1, 6);

        cell.setBorder(new SolidBorder(Color.BLACK, 0.5f));
        cell.add(criarRelatorioTableParagraphSubTitle("Controles Atuais"));

        if (controles != null && !controles.isEmpty()) {
            for (int i = 0; i < controles.size(); i++) {
                int index = i + 1;

                cell.add(criarRelatorioTableParagraphItem(index, controles.get(i).getControle().getDescricao()));
            }
        } else {
            cell.add(criarRelatorioTableParagraphTexto(NAO_INFORMADO));
        }

        return cell;
    }

    private Cell criarRelatorioTableCellFromPlanosAcao(List<PlanoControleDTO> planosControle) throws IOException {
        Cell cell = new Cell(1, 6);

        Table tablePlanosAcao = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 5),
                new UnitValue(UnitValue.PERCENT, 25),
                new UnitValue(UnitValue.PERCENT, 20),
                new UnitValue(UnitValue.PERCENT, 25),
                new UnitValue(UnitValue.PERCENT, 10),
                new UnitValue(UnitValue.PERCENT, 10),
                new UnitValue(UnitValue.PERCENT, 5)})
                .setWidthPercent(100);

        this.criaHeaderPlanoAcao(tablePlanosAcao);

        if (planosControle != null && !planosControle.isEmpty()) {
            for (int i = 0; i < planosControle.size(); i++) {

                tablePlanosAcao = this.adicionaPlanoAcao(tablePlanosAcao, planosControle.get(i), i + 1);

            }
        } else {
            tablePlanosAcao.addCell(new Cell(1, 7).setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(criarRelatorioTableParagraphTexto(NAO_INFORMADO)));
        }

        cell.setPadding(0);
        cell.setBorder(Border.NO_BORDER);
        cell.add(tablePlanosAcao);

        return cell;
    }

    private Table criaHeaderPlanoAcao(Table tablePlanosAcao) throws IOException {
        tablePlanosAcao.addCell(new Cell(1, 7).setBorder(new SolidBorder(Color.BLACK, 0.5f)).setBackgroundColor(ReportUtil.getInstance().colorGray).add(criarRelatorioTableParagraphSubTitle("Plano de Ação")));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph("#").setTextAlignment(TextAlignment.CENTER).setFontSize(8).setFont(ReportUtil.getInstance().getFontHelveticaBold())));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph("Descrição").setTextAlignment(TextAlignment.LEFT).setFontSize(8).setFont(ReportUtil.getInstance().getFontHelveticaBold())));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph("Tipo Controle").setTextAlignment(TextAlignment.LEFT).setFontSize(8).setFont(ReportUtil.getInstance().getFontHelveticaBold())));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph("Área Responsável").setTextAlignment(TextAlignment.LEFT).setFontSize(8).setFont(ReportUtil.getInstance().getFontHelveticaBold())));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph("Início").setTextAlignment(TextAlignment.CENTER).setFontSize(8).setFont(ReportUtil.getInstance().getFontHelveticaBold())));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph("Fim").setTextAlignment(TextAlignment.CENTER).setFontSize(8).setFont(ReportUtil.getInstance().getFontHelveticaBold())));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph("Status").setTextAlignment(TextAlignment.CENTER).setFontSize(8).setFont(ReportUtil.getInstance().getFontHelveticaBold())));

        return tablePlanosAcao;
    }

    private Table adicionaPlanoAcao(Table tablePlanosAcao, PlanoControleDTO planoControle, int index) {
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph(String.valueOf(index)).setTextAlignment(TextAlignment.CENTER).setFontSize(8)));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph(String.valueOf(planoControle.getControle().getDescricao())).setTextAlignment(TextAlignment.LEFT).setFontSize(8)));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph(String.valueOf(planoControle.getTipoControle().getNome())).setTextAlignment(TextAlignment.LEFT).setFontSize(8)));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph(String.valueOf(planoControle.getAreaResponsavel())).setTextAlignment(TextAlignment.LEFT).setFontSize(8)));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph(this.getDataDescricao(planoControle.getDtInicio())).setTextAlignment(TextAlignment.CENTER).setFontSize(8)));
        tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph(this.getDataDescricao(planoControle.getDtConclusao())).setTextAlignment(TextAlignment.CENTER).setFontSize(8)));

        String status = monitoramentoService.getPlanoControleStatus(planoControleMapper.planoControleDTOToPlanoControle(planoControle));

        if (status.equals("Não informada")) {
            tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph(status).setTextAlignment(TextAlignment.CENTER).setFontSize(8)).setFontColor(Color.RED));
        } else {
            tablePlanosAcao.addCell(new Cell().setBorder(new SolidBorder(Color.BLACK, 0.5f)).add(new Paragraph(status).setTextAlignment(TextAlignment.CENTER).setFontSize(8)));
        }

        return tablePlanosAcao;
    }

    private Cell criarRelatorioTableCellTitle(String texto, int rowspan, int colspan) throws IOException {
        Cell cell = new Cell(rowspan, colspan);

        cell.setBorder(new SolidBorder(Color.BLACK, 0.5f));
        cell.setBackgroundColor(ReportUtil.getInstance().colorGreenLight1);
        cell.add(criarRelatorioTableParagraphTitle(texto));

        return cell;
    }

    private Cell criarRelatorioTableCellTitleLight(String texto, int rowspan, int colspan) throws IOException {
        Cell cell = new Cell(rowspan, colspan);

        cell.setBorder(new SolidBorder(Color.BLACK, 0.5f));
        cell.setBackgroundColor(ReportUtil.getInstance().colorGreenLight2);
        cell.add(criarRelatorioTableParagraphTitle(texto));

        return cell;
    }

    private Paragraph criarRelatorioTableParagraphTitle(String texto) throws IOException {
        Paragraph paragraph = new Paragraph(texto);

        paragraph.setFont(ReportUtil.getInstance().getFontHelveticaBold());
        paragraph.setFontSize(9);
        paragraph.setPadding(4f);

        return paragraph;
    }

    private Paragraph criarRelatorioTableParagraphSubTitle(String texto) throws IOException {
        Paragraph paragraph = new Paragraph(texto);

        paragraph.setFont(ReportUtil.getInstance().getFontHelveticaBold());
        paragraph.setUnderline();
        paragraph.setFontSize(8);
        paragraph.setPadding(4f);
        paragraph.setPaddingBottom(0f);

        return paragraph;
    }

    private Paragraph criarRelatorioTableParagraphTexto(String texto) {
        Paragraph paragraph = new Paragraph(texto);

        paragraph.setFontSize(8);
        paragraph.setPadding(0f);
        paragraph.setPaddingLeft(4f);

        return paragraph;
    }

    private Paragraph criarRelatorioTableParagraphItem(int index, String texto) {
        Paragraph paragraph = new Paragraph(String.valueOf(index));

        paragraph.add(". ");
        paragraph.add(texto);

        paragraph.setFontSize(8);
        paragraph.setPaddingTop(4f);
        paragraph.setPaddingLeft(8f);

        return paragraph;
    }

    private void atualizaImpacto(Long impactoId, Boolean desabilitado) {
        ImpactoCalculadora impactoCalculadora = impactoCalculadoraRepository.findOne(impactoId);
        impactoCalculadora.setDesabilitado(desabilitado);
        impactoCalculadoraRepository.save(impactoCalculadora);
    }

    private Evento saveEvento(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        Evento evento = new Evento();
        evento.setOrgao(orgao);
        evento.setStatus(true);
        evento.setDescricao(descricao);
        evento.setSearch(StringUtil.removerAcento(evento.getDescricao()));
        evento = eventoRepository.save(evento);

        return evento;
    }

    private Causa saveCausa(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        Causa causa = new Causa();
        causa.setOrgao(orgao);
        causa.setStatus(true);
        causa.setDescricao(descricao);
        causa.setSearch(StringUtil.removerAcento(causa.getDescricao()));
        causa = causaRepository.save(causa);

        return causa;
    }

    private Consequencia saveConsequencia(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        Consequencia consequencia = new Consequencia();
        consequencia.setOrgao(orgao);
        consequencia.setStatus(true);
        consequencia.setDescricao(descricao);
        consequencia.setSearch(StringUtil.removerAcento(consequencia.getDescricao()));
        consequencia = consequenciaRepository.save(consequencia);

        return consequencia;
    }

    private Controle saveControle(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        Controle controle = new Controle();
        controle.setOrgao(orgao);
        controle.setStatus(true);
        controle.setDescricao(descricao);
        controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
        controle = controleRepository.save(controle);

        return controle;
    }

    private Macroprocesso saveMacroprocesso(String descricao) {
        Macroprocesso macroprocesso = macroprocessoRepository.findBySearchIgnoreCase(StringUtil.removerAcento(descricao));

        if (macroprocesso == null) {
            macroprocesso = new Macroprocesso();
            macroprocesso.setStatus(true);
            macroprocesso.setDescricao(descricao);
            macroprocesso.setSearch(StringUtil.removerAcento(macroprocesso.getDescricao()));
            macroprocesso = macroprocessoRepository.save(macroprocesso);
        }

        return macroprocesso;
    }

    private void verificarCalculoRisco(EventoRisco eventoRisco) {

        CalculoRisco calculoRiscoInerente = eventoRisco.getCalculoRiscoInerente();
        CalculoRisco calculoRiscoResidual = eventoRisco.getCalculoRiscoResidual();

        eventoRisco.setCalculoRiscoInerente(null);
        eventoRisco.setCalculoRiscoResidual(null);

        EventoRisco evento = eventoRiscoRepository.findOne(eventoRisco.getId());

        if (calculoRiscoInerente != null) {
            for (PesoEventoRisco pesoEventoRisco : calculoRiscoInerente.getPesos()) {
                pesoEventoRisco.setCalculoRisco(calculoRiscoInerente);
            }

            calculoRiscoInerente = calculoRiscoRepository.save(calculoRiscoInerente);

            evento.setCalculoRiscoInerente(calculoRiscoInerente);
        }

        if (calculoRiscoResidual != null) {
            for (PesoEventoRisco pesoEventoRisco : calculoRiscoResidual.getPesos()) {
                pesoEventoRisco.setCalculoRisco(calculoRiscoResidual);
            }

            calculoRiscoResidual = calculoRiscoRepository.save(calculoRiscoResidual);

            evento.setCalculoRiscoResidual(calculoRiscoResidual);
        }

        eventoRiscoRepository.save(evento);
    }

    private void saveEventoInEventoRisco(EventoRisco eventoRisco, String cpf) {
        Evento novoEvento = saveEvento(eventoRisco.getEvento().getDescricao(), cpf);

        eventoRisco = eventoRiscoRepository.findOne(eventoRisco.getId());
        eventoRisco.setEvento(novoEvento);
        eventoRiscoRepository.save(eventoRisco);
    }

    private Boolean eventoHasProcesso(EventoRisco eventoRisco, Evento evento) {
        List<Processo> processos = processoRepository.findByEventoId(eventoRisco.getEvento().getId());

        if (processos.size() <= 1) {
            evento.setDescricao(eventoRisco.getEvento().getDescricao());
            evento.setSearch(StringUtil.removerAcento(evento.getDescricao()));
            eventoRepository.save(evento);
            return true;
        } else {
            return false;
        }
    }

    private void saveCausaInEventoCausa(EventoCausa eventoCausa, String cpf) {
        Causa novaCausa = saveCausa(eventoCausa.getCausa().getDescricao(), cpf);

        eventoCausa = eventoCausaRepository.findOne(eventoCausa.getId());
        eventoCausa.setCausa(novaCausa);
        eventoCausaRepository.save(eventoCausa);
    }

    private Boolean causaHasProcesso(EventoCausa eventoCausa, Causa causa) {
        List<Processo> processos = processoRepository.findByCausaId(eventoCausa.getCausa().getId());

        if (processos.size() <= 1) {
            causa.setDescricao(eventoCausa.getCausa().getDescricao());
            causa.setSearch(StringUtil.removerAcento(causa.getDescricao()));
            causaRepository.save(causa);
            return true;
        } else {
            return false;
        }
    }

    private void saveConsequenciaInEventoConsequencia(EventoConsequencia eventoConsequencia, String cpf) {
        Consequencia novaConsequencia = saveConsequencia(eventoConsequencia.getConsequencia().getDescricao(), cpf);

        eventoConsequencia = eventoConsequenciaRepository.findOne(eventoConsequencia.getId());
        eventoConsequencia.setConsequencia(novaConsequencia);
        eventoConsequenciaRepository.save(eventoConsequencia);
    }

    private Boolean consequenciaHasProcesso(EventoConsequencia eventoConsequencia, Consequencia consequencia) {
        List<Processo> processos = processoRepository.findByConsequenciaId(eventoConsequencia.getConsequencia().getId());

        if (processos.size() <= 1) {
            consequencia.setDescricao(eventoConsequencia.getConsequencia().getDescricao());
            consequencia.setSearch(StringUtil.removerAcento(consequencia.getDescricao()));
            consequenciaRepository.save(consequencia);
            return true;
        } else {
            return false;
        }
    }

    private void saveControleInControleEvento(ControleEvento controleEvento, String cpf) {
        Controle novoControle = saveControle(controleEvento.getControle().getDescricao(), cpf);

        controleEvento = controleEventoRepository.findOne(controleEvento.getId());
        controleEvento.setControle(novoControle);
        controleEventoRepository.save(controleEvento);
    }

    private Boolean controleHasProcesso(ControleEvento controleEvento, Controle controle) {
        List<Processo> processos = processoRepository.findByControleId(controleEvento.getControle().getId());

        if (processos.size() <= 1) {
            controle.setDescricao(controleEvento.getControle().getDescricao());
            controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
            controleRepository.save(controle);
            return true;
        } else {
            return false;
        }
    }

    private void atualizarControleEvento(ControleEvento controleEvento, ControleEventoDTO controleEventoDTO) {
        ControleEvento novoControleEvento = controleEventoMapper.controleEventoDTOToControleEvento(controleEventoDTO);
        controleEvento.setDesenho(novoControleEvento.getDesenho());
        controleEvento.setOperacao(novoControleEvento.getOperacao());
        controleEventoRepository.save(controleEvento);
    }

    private String getNivelRiscoDescricao(Integer nivel) {
        if (nivel <= 3) {
            return "Risco Pequeno";
        } else if (nivel >= 4 && nivel <= 6) {
            return "Risco Moderado";
        } else if (nivel >= 7 && nivel <= 15) {
            return "Risco Alto";
        } else {
            return "Risco Crítico";
        }
    }

    private String getDataDescricao(Calendar calendar) {
        if (calendar != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return String.valueOf(dateFormat.format(calendar.getTime()));
        } else {
            return "-";
        }
    }

    private HistoricoEventoRisco atualizaEventoRisco(EventoRisco eventoRisco) {
        EventoRisco oldEventoRico = eventoRiscoRepository.findOne(eventoRisco.getId());

        if (!oldEventoRico.getEvento().getDescricao().equalsIgnoreCase(eventoRisco.getEvento().getDescricao())) {
            eventoRepository.save(eventoRisco.getEvento());
            return this.saveHistoricoEvento(eventoRisco);
        }

        return null;
    }

    private HistoricoEventoRisco saveHistoricoEvento(EventoRisco eventoRisco) {
        HistoricoEventoRisco historico = new HistoricoEventoRisco();

        historico.setDtCadastro(Calendar.getInstance());
        historico.setEvento(eventoRisco.getEvento().getDescricao());
        historico.setEventoRisco(eventoRisco);
        historico.setUsuario(usuarioRepository.findByCpf(UsuarioUtil.getCpfUsuarioLogado()));

        return historico;
    }

    private void alterarStatusProcessoNaoFinalizado(Long processoId) {
        Processo processo = processoRepository.findOne(processoId);
        processo.setStatus(statusProcessoRepository.findByNomeIgnoreCase("não finalizado"));
        processo.setDecisao(null);
        processo.setJustificativa("");
        processoRepository.save(processo);
    }
}