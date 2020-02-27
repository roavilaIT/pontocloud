package br.com.roavila.pontocloud.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.roavila.pontocloud.model.Batida;
import br.com.roavila.pontocloud.model.Ponto;
import br.com.roavila.pontocloud.model.Colaborador;
import br.com.roavila.pontocloud.repository.PontoRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PontoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PontoRepository pontoRepository;

    @Test
    public void registrarPontoTest() {

        Colaborador colaborador = criarColaborador();

        Ponto entrada = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now().minusHours(1L))
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
                .comHora(LocalTime.now().plusHours(4L))
                .comBatida(Batida.ENTRADA)
                .build();

        Ponto saida2 = new Ponto
                .Builder(null)
                .comColaborador(colaborador)
                .comHora(LocalTime.now().plusHours(8L))
                .comBatida(Batida.SAIDA)
                .build();

        pontoRepository.save(entrada);
        pontoRepository.save(saida);
        pontoRepository.save(entrada2);
        pontoRepository.save(saida2);

        List<Ponto> pontos = pontoRepository.findByColaborador(colaborador);
        Assertions.assertThat(pontos.size()).isEqualTo(4);


    }

    private Colaborador criarColaborador() {
        Colaborador colaborador = new Colaborador
                .Builder(null)
                .comNome("urko")
                .comCpf("123456789")
                .comEmail("teste@itau.com.br")
                .comDataCadastro(LocalDate.now())
                .build();
        return entityManager.persistAndFlush(colaborador);
    }
}
