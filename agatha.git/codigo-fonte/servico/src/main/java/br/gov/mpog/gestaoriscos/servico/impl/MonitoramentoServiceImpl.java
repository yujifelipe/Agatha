package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Acompanhamento;
import br.gov.mpog.gestaoriscos.modelo.CalculoRisco;
import br.gov.mpog.gestaoriscos.modelo.Categoria;
import br.gov.mpog.gestaoriscos.modelo.EntidadeBaseDescricaoStatusSearch;
import br.gov.mpog.gestaoriscos.modelo.EventoRisco;
import br.gov.mpog.gestaoriscos.modelo.Macroprocesso;
import br.gov.mpog.gestaoriscos.modelo.Monitoramento;
import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.Perfil;
import br.gov.mpog.gestaoriscos.modelo.PlanoControle;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.base.EntidadeBaseOrgao;
import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MonitoramentoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MonitoramentoDetalhadoDTO;
import br.gov.mpog.gestaoriscos.repositorio.AcompanhamentoRepository;
import br.gov.mpog.gestaoriscos.repositorio.CategoriaRepository;
import br.gov.mpog.gestaoriscos.repositorio.MacroprocessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.MonitoramentoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.MonitoramentoRepository;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.PermissaoRepository;
import br.gov.mpog.gestaoriscos.servico.MonitoramentoService;
import br.gov.mpog.gestaoriscos.servico.mapper.CategoriaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.MacroprocessoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.MonitoramentoMapper;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import br.gov.mpog.gestaoriscos.util.DateUtil;
import br.gov.mpog.gestaoriscos.util.JFreeChartBarDrawingSupplier;
import br.gov.mpog.gestaoriscos.util.ReportFooterEventHandler;
import br.gov.mpog.gestaoriscos.util.ReportUtil;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import br.gov.mpog.gestaoriscos.util.UsuarioUtil;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.UnitType;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MonitoramentoServiceImpl implements MonitoramentoService {

    private static String PERFIL_NUCLEO = "Núcleo";
    private DeviceRgb borderColor = new DeviceRgb(AnnotationNumberUtil.L119, AnnotationNumberUtil.L113, AnnotationNumberUtil.L113);

    private final Logger log = LoggerFactory.getLogger(CausaServiceImpl.class);
    private final MonitoramentoRepository monitoramentoRepository;
    private final MonitoramentoMapper monitoramentoMapper;

    private final OrgaoRepository orgaoRepository;

    private final MacroprocessoRepository macroprocessoRepository;
    private final MacroprocessoMapper macroprocessoMapper;

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    private final PermissaoRepository permissaoRepository;

    private final MonitoramentoCustomRepositorio monitoramentoCustomRepositorio;

    private final AcompanhamentoRepository acompanhamentoRepository;

    @Autowired
    public MonitoramentoServiceImpl(MonitoramentoRepository monitoramentoRepository, MonitoramentoMapper monitoramentoMapper, OrgaoRepository orgaoRepository, MacroprocessoRepository macroprocessoRepository, MacroprocessoMapper macroprocessoMapper, CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper, PermissaoRepository permissaoRepository, MonitoramentoCustomRepositorio monitoramentoCustomRepositorio, AcompanhamentoRepository acompanhamentoRepository) {
        this.monitoramentoRepository = monitoramentoRepository;
        this.monitoramentoMapper = monitoramentoMapper;
        this.orgaoRepository = orgaoRepository;
        this.macroprocessoRepository = macroprocessoRepository;
        this.macroprocessoMapper = macroprocessoMapper;
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.permissaoRepository = permissaoRepository;
        this.monitoramentoCustomRepositorio = monitoramentoCustomRepositorio;
        this.acompanhamentoRepository = acompanhamentoRepository;
    }

    @Override
    public MonitoramentoDTO save(MonitoramentoDTO monitoramentoDTO) {
        log.debug("Request to save a Monitoramento : {}", monitoramentoDTO);
        Monitoramento monitoramento = monitoramentoMapper.toEntity(monitoramentoDTO);

        String cpf = UsuarioUtil.getCpfUsuarioLogado();
        monitoramento.setOrgao(orgaoRepository.findByUsuarioCPF(cpf));

        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, PERFIL_NUCLEO);
        monitoramento.setPerfilNucleo(nucleo != null);

        if (monitoramento.getId() != null) {
            monitoramento.setDtCadastro(monitoramentoRepository.getDataCadastroById(monitoramento.getId()));
        }

        monitoramento = monitoramentoRepository.save(monitoramento);
        return monitoramentoMapper.toDto(monitoramento);
    }

    @Override
    public Page<MonitoramentoDTO> findAll(Pageable pageable) {
        log.debug("Request to a page of Monitoramento : {}", pageable);

        String cpf = UsuarioUtil.getCpfUsuarioLogado();
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, PERFIL_NUCLEO);

        Page<Monitoramento> result = monitoramentoRepository.findByOrgaoIdAndPerfilNucleoOrderByDtCadastroDesc(orgao.getId(), nucleo != null, pageable);
        return result.map(monitoramentoMapper::toDto);
    }

    @Override
    public MonitoramentoDTO findOne(Long id) {
        log.debug("Request to get Monitoramento : {}", id);
        Monitoramento monitoramento = monitoramentoRepository.findOne(id);
        return monitoramentoMapper.toDto(monitoramento);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Monitoramento : {}", id);
        monitoramentoRepository.delete(id);
    }

    @Override
    public List<MacroprocessoDTO> getAllMacroProcessos() {
        log.debug("Request to get all macroprocessos : {}");
        List<Macroprocesso> restul;

        String cpf = UsuarioUtil.getCpfUsuarioLogado();
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, PERFIL_NUCLEO);

        if (nucleo != null) {
            restul = macroprocessoRepository.findByStatusTrueOrderByDescricaoAsc();
        } else {
            Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
            restul = macroprocessoRepository.findBySecretariaIdAndStatusTrueOrderByDescricaoAsc(orgao.getId());
        }

        return macroprocessoMapper.toDto(restul);
    }

    @Override
    public List<CategoriaDTO> getAllCategorias() {
        log.debug("Request to get all categorias : {}");
        List<Categoria> result = categoriaRepository.findByStatusTrueOrderByDescricaoAsc();
        return categoriaMapper.categoriasToCategoriaDTOs(result);
    }

    @Override
    public MonitoramentoDetalhadoDTO gerarGrafico(MonitoramentoDTO monitoramentoDTO) {
        Monitoramento monitoramento = monitoramentoMapper.toEntity(monitoramentoDTO);
        MonitoramentoDetalhadoDTO result = new MonitoramentoDetalhadoDTO();

        result.setMacroprocesso(monitoramentoCustomRepositorio.countMacroprocessoByFiltro(monitoramento));
        result.setProcesso(monitoramentoCustomRepositorio.countProcessoByFiltro(monitoramento));
        result.setEventoRisco(monitoramentoCustomRepositorio.countEventoRiscoByFiltro(monitoramento));
        result.setCausa(monitoramentoCustomRepositorio.countCausaByFiltro(monitoramento));
        result.setConsequencia(monitoramentoCustomRepositorio.countConsequenciaByFiltro(monitoramento));

        return result;
    }

    @Override
    public List<GraficoMonitoramentoDTO> gerarGraficoRiscoResidual(MonitoramentoDTO monitoramentoDTO) {
        return gerarGraficoRiscoResidual(monitoramentoMapper.toEntity(monitoramentoDTO));
    }

    private List<GraficoMonitoramentoDTO> gerarGraficoRiscoResidual(Monitoramento monitoramento) {
        List<GraficoMonitoramentoDTO> grafico = new ArrayList<>();

        grafico.add(monitoramentoCustomRepositorio.countNivelRiscoBaixoByFilter(monitoramento));
        grafico.add(monitoramentoCustomRepositorio.countNivelRiscoModeradoByFilter(monitoramento));
        grafico.add(monitoramentoCustomRepositorio.countNivelRiscoAltoByFilter(monitoramento));
        grafico.add(monitoramentoCustomRepositorio.countNivelRiscoCriticoByFilter(monitoramento));

        return grafico;
    }

    @Override
    public List<GraficoMonitoramentoDTO> gerarGraficoCategoriaRisco(MonitoramentoDTO monitoramentoDTO) {
        Monitoramento monitoramento = monitoramentoMapper.toEntity(monitoramentoDTO);
        return monitoramentoCustomRepositorio.getCategoriaRiscoCountByFiltro(monitoramento);
    }

    @Override
    public byte[] gerarRelatorioMonitoramento(MonitoramentoDTO monitoramentoDTO) throws IOException {
        Monitoramento monitoramento = monitoramentoMapper.toEntity(monitoramentoDTO);
        return criarRelatorioPdf(monitoramento);
    }

    private byte[] criarRelatorioPdf(Monitoramento monitoramento) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(arrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        ReportFooterEventHandler footerHandler = new ReportFooterEventHandler();

        Document document = ReportUtil.getInstance().criarDocumento(pdf, footerHandler, PageSize.A4.rotate());

        ReportUtil.getInstance().criarReportHeader(document, "Ministério do Planejamento, Desenvolvimento e Gestão", this.getSecretariaUsuarioLogado(), false);
        criarRelatorioConteudo(document, monitoramento);

        footerHandler.writeTotalPages(pdf);

        document.close();

        return arrayOutputStream.toByteArray();
    }

    private String getSecretariaUsuarioLogado() {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(UsuarioUtil.getCpfUsuarioLogado());
        return orgao.getNome();
    }

    private void criarRelatorioConteudo(Document document, Monitoramento monitoramento) throws IOException {
        criarRelatorioConteudoSubheader(document);
        criarRelatorioConteudoGraficos(document, monitoramento);
        criarRelatorioConteudoProcessos(document, monitoramento);
    }

    private void criarRelatorioConteudoSubheader(Document document) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Table tableSubheader = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L50),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L50)})
                .setWidthPercent(AnnotationNumberUtil.L100);

        tableSubheader.setMarginTop(AnnotationNumberUtil.F7);

        tableSubheader.addCell(new Cell().setPadding(AnnotationNumberUtil.F4).setBorderRight(Border.NO_BORDER).add(new Paragraph("Relatório – Portfólio de Riscos").setFontSize(AnnotationNumberUtil.F14)));
        tableSubheader.addCell(new Cell().setPadding(AnnotationNumberUtil.F4).setBorderLeft(Border.NO_BORDER).add(new Paragraph().add(new Text("Gerado em: ")).add(new Text(dateFormat.format(new Date()))).setTextAlignment(TextAlignment.RIGHT).setFontSize(AnnotationNumberUtil.F14)));

        document.add(tableSubheader);
    }

    private void criarRelatorioConteudoGraficos(Document document, Monitoramento monitoramento) throws IOException {
        Table tableGraficos = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L50),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L50)})
                .setWidthPercent(AnnotationNumberUtil.L100);

        tableGraficos.setMarginTop(AnnotationNumberUtil.F7);

        criarRelatorioCellFiltros(document, monitoramento);
        criarRelatorioCellGraficoNivel(document, monitoramento);
        criarRelatorioCellGraficoCategoria(document, monitoramento);

        document.add(tableGraficos);
    }

    private void criarRelatorioCellFiltros(Document document, Monitoramento monitoramento) {
        Table table = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L50)
        }).setWidthPercent(AnnotationNumberUtil.L100);

        table.setMarginTop(AnnotationNumberUtil.F7);

        Cell cell = new Cell(0, 2);

        cell.setPadding(AnnotationNumberUtil.F4);

        if (monitoramento.getId() != null) {
            cell.add(new Paragraph("Filtro Utilizado: " + monitoramento.getNome()).setFontSize(AnnotationNumberUtil.F12));
        }

        cell.add(new Paragraph("1. Secretaria: " + getSecretarias(monitoramento)).setFontSize(AnnotationNumberUtil.F10));
        cell.add(new Paragraph("2. Macroprocesso: " + getMacroprocessos(monitoramento)).setFontSize(AnnotationNumberUtil.F10));
        cell.add(new Paragraph("3. Categoria de Risco: " + getCategorias(monitoramento)).setFontSize(AnnotationNumberUtil.F10));
        cell.add(new Paragraph("4. Risco de Integridade: " + getRiscoIntegridade(monitoramento)).setFontSize(AnnotationNumberUtil.F10));
        cell.add(new Paragraph("5. Nível de Risco: " + getNiveisRisco(monitoramento)).setFontSize(AnnotationNumberUtil.F10));
        cell.add(new Paragraph("6. Risco Residual: " + getRiscosResiduais(monitoramento)).setFontSize(AnnotationNumberUtil.F10));
        cell.add(new Paragraph("7. Risco Inerente: " + getRiscosInerentes(monitoramento)).setFontSize(AnnotationNumberUtil.F10));

        table.addCell(cell);
        document.add(table);
    }

    private String getSecretarias(Monitoramento monitoramento) {
        if (monitoramento.getSecretarias().isEmpty()) {
            return "Todas";
        } else {
            return monitoramento.getSecretarias().stream().map(EntidadeBaseOrgao::getSigla).collect(Collectors.joining(", "));
        }
    }

    private String getMacroprocessos(Monitoramento monitoramento) {
        if (monitoramento.getMacroprocessos().isEmpty()) {
            return "Todos";
        } else {
            return monitoramento.getMacroprocessos().stream().map(EntidadeBaseDescricaoStatusSearch::getDescricao).collect(Collectors.joining(", "));
        }
    }

    private String getCategorias(Monitoramento monitoramento) {
        if (monitoramento.getMacroprocessos().isEmpty()) {
            return "Todas";
        } else {
            return monitoramento.getCategorias().stream().map(EntidadeBaseDescricaoStatusSearch::getDescricao).collect(Collectors.joining(", "));
        }
    }

    private String getRiscoIntegridade(Monitoramento monitoramento) {
        if (monitoramento.getIntegridades().split(",").length == 1) {
            return "Todos";
        } else {
            if (Boolean.getBoolean(monitoramento.getIntegridades().split(",")[0])) {
                return "Sim";
            } else {
                return "Não";
            }
        }
    }

    private String getNiveisRisco(Monitoramento monitoramento) {
        if (monitoramento.getNiveisRisco().isEmpty()) {
            return "Todos";
        } else {
            return monitoramento.getNiveisRisco();
        }
    }

    private String getRiscosResiduais(Monitoramento monitoramento) {
        if (monitoramento.getRiscosResiduais().isEmpty()) {
            return "Todos";
        } else {
            return monitoramento.getRiscosResiduais().stream().map(risco -> risco.getFator() + " - " + risco.getNiveis()).collect(Collectors.joining(", "));
        }
    }

    private String getRiscosInerentes(Monitoramento monitoramento) {
        if (monitoramento.getRiscosResiduais().isEmpty()) {
            return "Todos";
        } else {
            return monitoramento.getRiscosInerentes().stream().map(risco -> risco.getFator() + " - " + risco.getNiveis()).collect(Collectors.joining(", "));
        }
    }

    private void criarRelatorioCellGraficoCategoria(Document document, Monitoramento monitoramento) throws IOException {
        Table table = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L75),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L25)
        }).setWidthPercent(AnnotationNumberUtil.L100);

        table.setMarginTop(AnnotationNumberUtil.F7);

        Cell cell = new Cell();

        cell.setPadding(AnnotationNumberUtil.F4);
        cell.setBorder(Border.NO_BORDER);
        cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        List<GraficoMonitoramentoDTO> result = monitoramentoCustomRepositorio.getCategoriaRiscoCountByFiltro(monitoramento);

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<String> legendas = new ArrayList<>(0);

        int i = 1;
        for (GraficoMonitoramentoDTO graficoMonitoramentoDTO : result) {
            dataset.addValue(graficoMonitoramentoDTO.getQuantidade(), String.valueOf(i), String.valueOf(i));
            legendas.add(i + " - " + graficoMonitoramentoDTO.getNome() + " (" + graficoMonitoramentoDTO.getQuantidade() + ") ");

            i++;
        }

        JFreeChart chart = ChartFactory.createStackedBarChart("Categoria do Risco", null, null, dataset, PlotOrientation.VERTICAL, false, true, false);
        chart.setAntiAlias(true);

        CategoryPlot plot = setupGraficoCategoriaPlot(chart);

        setupGraficoCategoriaRender(plot);

        cell.add(getImageBarChart(chart));

        table.addCell(cell);

        criarRelatorioCellGraficoCategoriaLengedas(table, legendas);

        document.add(table);
    }

    private void criarRelatorioCellGraficoCategoriaLengedas(Table table, List<String> legendas) {
        Cell cell = new Cell();

        cell.setPadding(AnnotationNumberUtil.F4);
        cell.setBorder(Border.NO_BORDER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);

        for (String legenda : legendas) {
            cell.add(new Paragraph(legenda).setFontSize(AnnotationNumberUtil.F10));
        }

        table.addCell(cell);
    }

    private CategoryPlot setupGraficoCategoriaPlot(JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();

        plot.setOutlineVisible(false);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDrawingSupplier(new JFreeChartBarDrawingSupplier(JFreeChartBarDrawingSupplier.getUniqueColorList()));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        plot.getDomainAxis().setTickLabelFont(new Font("Arial", Font.BOLD, AnnotationNumberUtil.L25));
        plot.getRangeAxis().setTickLabelFont(new Font("Arial", Font.BOLD, AnnotationNumberUtil.L25));

        return plot;
    }

    private void setupGraficoCategoriaRender(CategoryPlot plot) {
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setDefaultItemLabelFont(new Font("Arial", Font.BOLD, AnnotationNumberUtil.L25));

        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        renderer.setGradientPaintTransformer(null);
        renderer.setBarPainter(new StandardBarPainter());

        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);

        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultPositiveItemLabelPosition(position);
    }

    private void criarRelatorioCellGraficoNivel(Document document, Monitoramento monitoramento) throws IOException {
        Table table = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L75),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L25)
        }).setWidthPercent(AnnotationNumberUtil.L100);

        table.setMarginTop(AnnotationNumberUtil.F7);

        List<GraficoMonitoramentoDTO> result = gerarGraficoRiscoResidual(monitoramento);

        Cell cell = new Cell();

        cell.setPadding(AnnotationNumberUtil.F4);
        cell.setBorder(Border.NO_BORDER);

        final DefaultPieDataset dataset = new DefaultPieDataset();
        List<String> legendas = new ArrayList<>(0);

        for (GraficoMonitoramentoDTO graficoMonitoramentoDTO : result) {
            dataset.setValue(graficoMonitoramentoDTO.getNome(), graficoMonitoramentoDTO.getQuantidade());
            legendas.add(graficoMonitoramentoDTO.getNome() + " (" + graficoMonitoramentoDTO.getQuantidade() + ")");
        }

        JFreeChart chart = ChartFactory.createRingChart("Nível de Risco Residual", dataset, false, false, false);
        chart.setAntiAlias(true);

        setupGraficoNivelPlot(chart);

        cell.add(getImageRingChart(chart));
        table.addCell(cell);
        criarRelatorioCellGraficoNivelLengedas(table, legendas);

        document.add(table);
    }

    private void criarRelatorioCellGraficoNivelLengedas(Table table, List<String> legendas) {
        Table tableLegendas = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L10),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L90)
        }).setWidthPercent(AnnotationNumberUtil.L100);

        tableLegendas.setBorder(Border.NO_BORDER);

        int countColor = 0;

        for (String legenda : legendas) {
            tableLegendas.addCell(new Cell().setBorder(Border.NO_BORDER).setMargin(AnnotationNumberUtil.F2).add(new Paragraph("##").setFontColor(JFreeChartBarDrawingSupplier.getCustomiTextColorList().get(countColor)).setBackgroundColor(JFreeChartBarDrawingSupplier.getCustomiTextColorList().get(countColor))));
            tableLegendas.addCell(new Cell().setBorder(Border.NO_BORDER).setPadding(AnnotationNumberUtil.F4).setVerticalAlignment(VerticalAlignment.MIDDLE).add(new Paragraph(legenda).setFontSize(AnnotationNumberUtil.F10)));
            countColor++;
        }

        table.addCell(new Cell().setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE).add(tableLegendas));
    }

    private void setupGraficoNivelPlot(JFreeChart chart) {
        RingPlot plot = (RingPlot) chart.getPlot();

        plot.setSectionDepth(AnnotationNumberUtil.L04);
        plot.setOutlineVisible(false);
        plot.setSectionOutlinesVisible(false);
        plot.setLabelGenerator(null);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);
        plot.setShadowPaint(null);
        plot.setSimpleLabels(true);
        plot.setLabelOutlinePaint(Color.WHITE);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
        plot.setLabelFont(new Font("Arial", Font.BOLD, AnnotationNumberUtil.L25));
        plot.setSimpleLabelOffset(new RectangleInsets(
                UnitType.RELATIVE, AnnotationNumberUtil.L009, AnnotationNumberUtil.L009, AnnotationNumberUtil.L009, AnnotationNumberUtil.L009));
        plot.setLabelBackgroundPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setDrawingSupplier(new JFreeChartBarDrawingSupplier(JFreeChartBarDrawingSupplier.getCustomColorList()));
    }

    private Image getImageRingChart(JFreeChart chart) throws IOException {
        BufferedImage original = chart.createBufferedImage(AnnotationNumberUtil.L900, AnnotationNumberUtil.L700);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            ImageIO.write(original, "png", os);
            os.flush();

            Image imageChart = new Image(ImageDataFactory.create(os.toByteArray()));
            imageChart.setAutoScale(true);
            imageChart.setHorizontalAlignment(HorizontalAlignment.RIGHT);

            return imageChart;
        }
    }

    private Image getImageBarChart(JFreeChart chart) throws IOException {
        BufferedImage original = chart.createBufferedImage(AnnotationNumberUtil.L900, AnnotationNumberUtil.L700);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            ImageIO.write(original, "png", os);
            os.flush();

            Image imageChart = new Image(ImageDataFactory.create(os.toByteArray()));
            imageChart.setWidth(AnnotationNumberUtil.F400);
            imageChart.setHeight(AnnotationNumberUtil.F300);
            imageChart.setHorizontalAlignment(HorizontalAlignment.RIGHT);

            return imageChart;
        }
    }

    private void criarRelatorioConteudoProcessos(Document document, Monitoramento monitoramento) throws IOException {
        List<Processo> processos = monitoramentoCustomRepositorio.getProcessosByFiltro(monitoramento);

        List<Orgao> secretarias = processos.stream().map(processo -> processo.getAnalise().getSecretaria()).distinct().sorted(Comparator.comparing(Orgao::getNome)).collect(Collectors.toList());

        for (Orgao secretaria : secretarias) {
            criarRelatorioConteudoSecretaria(document, secretaria, processos.stream().filter(processo -> processo.getAnalise().getSecretaria().getId().equals(secretaria.getId())).collect(Collectors.toList()));
        }

    }

    private void criarRelatorioConteudoSecretaria(Document document, Orgao secretaria, List<Processo> processos) throws IOException {
        Table table = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L4),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L10),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L10),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L20),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L20),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L20),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L8),
                new UnitValue(UnitValue.PERCENT, AnnotationNumberUtil.L8)
        }).setWidthPercent(AnnotationNumberUtil.L100);

        table.addCell(new Cell(0, 8).setPadding(AnnotationNumberUtil.F4).setMarginTop(AnnotationNumberUtil.F10).setBorder(Border.NO_BORDER).add(new Paragraph().setFontSize(AnnotationNumberUtil.F14).setFont(ReportUtil.getInstance().getFontHelveticaBold()).setFontColor(borderColor).add(new Text("Unidade: ")).add(new Text(secretaria.getNome()))));

        criarRelatorioConteudoProcesso(table, processos);

        document.add(table);
    }

    private void criarRelatorioConteudoProcesso(Table table, List<Processo> processos) {
        int count = 1;

        for (Processo processo : processos) {

            table.addCell(new Cell(0, 0).setPadding(AnnotationNumberUtil.F4).setBorder(new SolidBorder(new DeviceRgb(AnnotationNumberUtil.L119, AnnotationNumberUtil.L113, AnnotationNumberUtil.L113), 1)).setBackgroundColor(new DeviceRgb(AnnotationNumberUtil.L255, AnnotationNumberUtil.L255, AnnotationNumberUtil.L255))
                    .add(new Paragraph(String.valueOf(count)).setTextAlignment(TextAlignment.CENTER).setFontSize(AnnotationNumberUtil.F12)));

            table.addCell(new Cell(0, 7).setPadding(AnnotationNumberUtil.F4).setBorder(new SolidBorder(new DeviceRgb(AnnotationNumberUtil.L119, AnnotationNumberUtil.L113, AnnotationNumberUtil.L113), 1)).setBackgroundColor(new DeviceRgb(AnnotationNumberUtil.L255, AnnotationNumberUtil.L255, AnnotationNumberUtil.L255))
                    .add(new Paragraph().setFontSize(AnnotationNumberUtil.F12).add(new Text("Macroprocesso / Processo: "))
                            .add(new Text(getMacroprocessoProcesso(processo)))));

            criarRelatorioConteudoProcessoSubHeader(table);

            table.addCell(criarRelatorioConteudoProcessoHeaderCell("#"));
            table.addCell(criarRelatorioConteudoProcessoHeaderCell("Nível Residual"));
            table.addCell(criarRelatorioConteudoProcessoHeaderCell("Categoria"));
            table.addCell(criarRelatorioConteudoProcessoHeaderCell("Descrição"));
            table.addCell(criarRelatorioConteudoProcessoHeaderCell("Descrição"));
            table.addCell(criarRelatorioConteudoProcessoHeaderCell("Área Responsável"));
            table.addCell(criarRelatorioConteudoProcessoHeaderCell("Conclusão"));
            table.addCell(criarRelatorioConteudoProcessoHeaderCell("Status"));

            criarRelatorioConteudoProcessoEvento(table, processo, count);

            count++;
        }
    }

    private void criarRelatorioConteudoProcessoSubHeader(Table table) {
        table.addCell(criarRelatorioConteudoProcessoHeaderCell("#"));
        table.addCell(criarRelatorioConteudoProcessoHeaderCell("Evento de Risco", 3));
        table.addCell(criarRelatorioConteudoProcessoHeaderCell("Ação / Controle", 4));
    }

    private String getMacroprocessoProcesso(Processo processo) {
        if (processo.getMacroprocesso() != null && processo.getProcesso() != null && !processo.getProcesso().equals("")) {
            return processo.getMacroprocesso().getDescricao() + " / " + processo.getProcesso();
        } else if (processo.getMacroprocesso() != null) {
            return processo.getMacroprocesso().getDescricao();
        } else {
            return processo.getProcesso();
        }
    }

    private Cell criarRelatorioConteudoProcessoHeaderCell(String header) {
        return criarRelatorioConteudoProcessoHeaderCell(header, 0);
    }

    private Cell criarRelatorioConteudoProcessoHeaderCell(String header, int colspan) {
        return new Cell(0, colspan).setPadding(AnnotationNumberUtil.F4).setBorder(new SolidBorder(new DeviceRgb(AnnotationNumberUtil.L119, AnnotationNumberUtil.L113, AnnotationNumberUtil.L113), 1)).setBackgroundColor(new DeviceRgb(240, 240, 240))
                .add(new Paragraph(header).setFontSize(AnnotationNumberUtil.F10).setTextAlignment(TextAlignment.CENTER)).setVerticalAlignment(VerticalAlignment.MIDDLE);
    }

    private void criarRelatorioConteudoProcessoEvento(Table table, Processo processo, int processoCount) {
        int count = 1;
        for (EventoRisco evento : processo.getIdentificacao().getEventos()) {

            int qtdPlanos = evento.getPlanos().size();

            table.addCell(criarRelatorioConteudoProcessoEventoCell(processoCount + "." + count, TextAlignment.CENTER, qtdPlanos));

            table.addCell(criarRelatorioConteudoProcessoEventoCell(getStringNivelRiscoResidual(evento.getCalculoRiscoResidual()), TextAlignment.CENTER, qtdPlanos));

            table.addCell(criarRelatorioConteudoProcessoEventoCell(getStringCategoria(evento), TextAlignment.CENTER, qtdPlanos));

            table.addCell(criarRelatorioConteudoProcessoEventoCell(evento.getEvento().getDescricao(), TextAlignment.LEFT, qtdPlanos));

            criarRelatorioConteudoProcessoPlano(table, evento);

            count++;
        }
    }

    private Cell criarRelatorioConteudoProcessoEventoCell(String texto, TextAlignment alignment, int rowspan) {
        return new Cell(rowspan, 0).setPadding(AnnotationNumberUtil.F4).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(new SolidBorder(new DeviceRgb(AnnotationNumberUtil.L119, AnnotationNumberUtil.L113, AnnotationNumberUtil.L113), 1))
                .add(new Paragraph(texto).setFontSize(AnnotationNumberUtil.F10).setTextAlignment(alignment));
    }

    private String getStringNivelRiscoResidual(CalculoRisco risco) {
        if (risco == null || risco.getNivel() == null) {
            return "";
        }

        int nivel = risco.getNivel().intValue();

        if (nivel <= 3) {
            return "Pequeno";
        } else if (nivel <= 6) {
            return "Moderado";
        } else if (nivel <= 15) {
            return "Alto";
        } else {
            return "Crítico";
        }
    }

    private String getStringCategoria(EventoRisco eventoRisco) {
        String categoria = eventoRisco.getCategoria().getDescricao();

        if (eventoRisco.getRiscoIntegridade()) {
            categoria += ", Integridade";
        }

        return categoria;
    }

    private void criarRelatorioConteudoProcessoPlano(Table table, EventoRisco evento) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (evento.getPlanos().isEmpty()) {
            table.addCell(criarRelatorioConteudoProcessoEventoCell("", TextAlignment.LEFT, 0));
            table.addCell(criarRelatorioConteudoProcessoEventoCell("", TextAlignment.LEFT, 0));
            table.addCell(criarRelatorioConteudoProcessoEventoCell("", TextAlignment.LEFT, 0));
            table.addCell(criarRelatorioConteudoProcessoEventoCell("", TextAlignment.LEFT, 0));
        }

        for (PlanoControle plano : evento.getPlanos()) {
            table.addCell(criarRelatorioConteudoProcessoEventoCell(plano.getControle().getDescricao(), TextAlignment.LEFT, 0));
            table.addCell(criarRelatorioConteudoProcessoEventoCell(StringUtil.verificaString(plano.getAreaResponsavel()), TextAlignment.LEFT, 0));
            table.addCell(criarRelatorioConteudoProcessoEventoCell(plano.getDtConclusao() != null ? dateFormat.format(plano.getDtConclusao().getTime()) : "", TextAlignment.CENTER, 0));

            String status = getPlanoControleStatus(plano);

            if (status.equals("Não informada")) {
                table.addCell(criarRelatorioConteudoProcessoEventoCell(status, TextAlignment.CENTER, 0).setFontColor(com.itextpdf.kernel.color.Color.RED));
            } else {
                table.addCell(criarRelatorioConteudoProcessoEventoCell(status, TextAlignment.CENTER, 0));
            }
        }
    }

    @Override
    public String getPlanoControleStatus(PlanoControle planoControle) {
        List<Acompanhamento> result = acompanhamentoRepository.findByPlanoControleIdOrderByDtCadastroDesc(planoControle.getId());

        if (result.isEmpty()) {
            return "Não informada";
        } else {
            Acompanhamento acompanhamento = result.get(0);

            if (acompanhamento.getStatus().equals("A iniciar") || acompanhamento.getStatus().equals("Iniciada")) {
                Calendar hoje = DateUtil.getCalendarMinimizedHours(Calendar.getInstance().getTime());
                Calendar dtConclusao = DateUtil.getCalendarMinimizedHours(planoControle.getDtConclusao().getTime());

                if (hoje.after(dtConclusao)) {
                    return "Vencida";
                } else {
                    return acompanhamento.getStatus();
                }

            } else {
                return acompanhamento.getStatus();
            }

        }

    }

}
