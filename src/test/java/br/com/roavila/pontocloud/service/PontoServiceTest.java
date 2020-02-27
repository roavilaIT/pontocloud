package br.com.roavila.pontocloud.service;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.roavila.pontocloud.model.Batida;
import br.com.roavila.pontocloud.model.Ponto;
import br.com.roavila.pontocloud.model.RelatorioPonto;
import br.com.roavila.pontocloud.model.Colaborador;
import br.com.roavila.pontocloud.repository.PontoRepository;
import br.com.roavila.pontocloud.service.PontoService;
import br.com.roavila.pontocloud.service.ColaboradorService;
import br.com.roavila.pontocloud.service.impl.PontoServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PontoServiceTest {

    @TestConfiguration
    static class PontoTestContextConfiguration {

        @Bean
        public PontoService pontoService() {
            return new PontoServiceImpl();
        }
    }

    @MockBean
    private PontoRepository pontoRepository;

    @MockBean
    private ColaboradorService colaboradorService;

    @Autowired
    private PontoService pontoService;


    @Test
    public void registrarPontoTest() {

        Colaborador colaborador = criarColaborador();

        when(colaboradorService.getBy(anyLong())).thenReturn(colaborador);

        Ponto entrada = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now())
                .comBatida(Batida.ENTRADA)
                .build();

        Ponto saida = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now().plusHours(2L))
                .comBatida(Batida.SAIDA)
                .build();

        Ponto entrada2 = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now().plusHours(3L))
                .comBatida(Batida.ENTRADA)
                .build();

        Ponto saida2 = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now().plusHours(4L))
                .comBatida(Batida.SAIDA)
                .build();

        List<Ponto> pontos = Lists.newArrayList(entrada, saida, entrada2, saida2);



        when(pontoRepository.save(entrada)).thenReturn(entrada);
        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(Lists.newArrayList());
        pontoService.registrarPonto(entrada);

        when(pontoRepository.save(saida)).thenReturn(saida);
        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(Lists.newArrayList(entrada));
        pontoService.registrarPonto(saida);

        when(pontoRepository.save(entrada2)).thenReturn(entrada2);
        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(Lists.newArrayList(entrada, saida));
        pontoService.registrarPonto(entrada2);

        when(pontoRepository.save(saida2)).thenReturn(saida2);
        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(Lists.newArrayList(entrada, saida, entrada2));
        pontoService.registrarPonto(saida2);

        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(pontos);
        RelatorioPonto relatorioPonto = pontoService.gerarRelatorio(colaborador.getId());

        assertThat(relatorioPonto.getPontos().size()).isEqualTo(4);
        assertThat(relatorioPonto.totalHoras()).isEqualTo("3:00:00");

    }

    @Test
    public void registrarPontoEntradaTest() {

        Colaborador colaborador = criarColaborador();

        when(colaboradorService.getBy(anyLong())).thenReturn(colaborador);

        Ponto entrada = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now())
                .comBatida(Batida.ENTRADA)
                .build();


        List<Ponto> pontos = Lists.newArrayList(entrada);

        when(pontoRepository.save(entrada)).thenReturn(entrada);
        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(pontos);

        pontoService.registrarPonto(entrada);
        RelatorioPonto relatorioPonto = pontoService.gerarRelatorio(colaborador.getId());

        assertThat(relatorioPonto.getPontos().size()).isEqualTo(1);

    }

    @Test
    public void registrarPontoEntradaSemSaidaTest() {

        Colaborador colaborador = criarColaborador();

        when(colaboradorService.getBy(anyLong())).thenReturn(colaborador);

        Ponto entrada = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now())
                .comBatida(Batida.ENTRADA)
                .build();

        Ponto saida = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now().plusSeconds(1))
                .comBatida(Batida.SAIDA)
                .build();

        Ponto entrada2 = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now().plusSeconds(2))
                .comBatida(Batida.ENTRADA)
                .build();

        List<Ponto> pontos = Lists.newArrayList(entrada, saida, entrada2);



        when(pontoRepository.save(entrada)).thenReturn(entrada);
        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(Lists.newArrayList());
        pontoService.registrarPonto(entrada);

        when(pontoRepository.save(saida)).thenReturn(saida);
        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(Lists.newArrayList(entrada));
        pontoService.registrarPonto(saida);


        when(pontoRepository.save(entrada2)).thenReturn(entrada2);
        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(Lists.newArrayList(entrada,saida));
        pontoService.registrarPonto(entrada2);

        when(pontoRepository.findByIdColaborador(colaborador.getId())).thenReturn(pontos);
        RelatorioPonto relatorioPonto = pontoService.gerarRelatorio(colaborador.getId());

        assertThat(relatorioPonto.getPontos().size()).isEqualTo(3);

    }

    private Colaborador criarColaborador() {
        return new Colaborador
                .Builder(1L)
                .comNome("urko")
                .comCpf("123456789")
                .comEmail("teste@itau.com.br")
                .comDataCadastro(LocalDate.now())
                .build();
    }

}
