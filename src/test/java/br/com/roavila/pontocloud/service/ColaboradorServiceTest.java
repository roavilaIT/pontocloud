package br.com.roavila.pontocloud.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.roavila.pontocloud.model.Colaborador;
import br.com.roavila.pontocloud.repository.ColaboradorRepository;
import br.com.roavila.pontocloud.service.ColaboradorService;
import br.com.roavila.pontocloud.service.impl.ColaboradorServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ColaboradorServiceTest {

    @TestConfiguration
    static class ColaboradorTestContextConfiguration {

        @Bean
        public ColaboradorService colaboradorService() {
            return new ColaboradorServiceImpl();
        }
    }

    @Autowired
    private ColaboradorService colaboradorService;

    @MockBean
    private ColaboradorRepository colaboradorRepository;


    @Test
    public void listColaboradorsTest() {
        List<Colaborador> colaboradorsPersistidos = Lists.newArrayList(new Colaborador.Builder(1L).comNome("urko").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build());
        when(colaboradorRepository.findAll()).thenReturn(colaboradorsPersistidos);

        List<Colaborador> colaboradorsListados = colaboradorService.listar();
        Assertions.assertThat(colaboradorsListados.get(0).getNome()).isEqualTo(colaboradorsPersistidos.get(0).getNome());
    }

    @Test
    public void getColaboradorTest() {
        Colaborador colaboradorPersistido = new Colaborador.Builder(1L).comNome("urko").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();
        when(colaboradorRepository.findById(anyLong())).thenReturn(Optional.of(colaboradorPersistido));

        Colaborador colaborador = colaboradorService.getBy(1L);
        Assertions.assertThat(colaborador.getNome()).isEqualTo(colaboradorPersistido.getNome());
    }

    @Test
    public void updateColaboradorTest() {
        Colaborador colaboradorPersistido = new Colaborador.Builder(1L).comNome("urko").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();
        Colaborador colaboradorASerAtualizado = new Colaborador.Builder(1L).comNome("chave").comCpf("11111").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();
        when(colaboradorRepository.findById(anyLong())).thenReturn(Optional.of(colaboradorPersistido));
        when(colaboradorRepository.save(colaboradorASerAtualizado)).thenReturn(colaboradorASerAtualizado);

        Colaborador colaborador = colaboradorService.atualizar(colaboradorASerAtualizado, 1L);
        Assertions.assertThat(colaborador.getNome()).isEqualTo(colaboradorASerAtualizado.getNome());
    }

    @Test
    public void criarColaboradorTest() {

        Colaborador colaboradorParaSalvar = new Colaborador.Builder(null).comNome("urko").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();
        Colaborador colaboradorPersistido = new Colaborador.Builder(1L).comNome("urko").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();


        when(colaboradorRepository.save(colaboradorParaSalvar)).thenReturn(colaboradorPersistido);

        Colaborador colaborador = colaboradorService.criar(colaboradorParaSalvar);
        Assertions.assertThat(colaborador.getNome()).isEqualTo(colaboradorPersistido.getNome());
        Assertions.assertThat(colaborador.getId()).isEqualTo(1L);
    }
}
