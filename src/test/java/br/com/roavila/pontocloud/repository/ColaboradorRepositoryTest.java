package br.com.roavila.pontocloud.repository;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.roavila.pontocloud.model.Colaborador;
import br.com.roavila.pontocloud.repository.ColaboradorRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ColaboradorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;


    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Test
    public void saveTest() {

        Colaborador colaboradorASerPersistido = new Colaborador.Builder(null).comNome("bill").comCpf("99999").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();

        Colaborador colaborador = colaboradorRepository.save(colaboradorASerPersistido);
        assertThat(colaborador.getNome()).isEqualTo("bill");
        assertThat(colaborador.getCpf()).isEqualTo("99999");
    }

    @Test
    public void findByIdTest() {

        Colaborador colaborador1 = new Colaborador.Builder(null).comNome("dude").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();

        Colaborador colaboradorPersistido = entityManager.persistAndFlush(colaborador1);
        Colaborador colaborador = colaboradorRepository.findById(colaboradorPersistido.getId()).get();

        assertThat(colaborador.getNome()).isEqualTo("dude");
        assertThat(colaborador.getCpf()).isEqualTo("123456");
    }

    @Test
    public void updateColaboradorTest() {

        Colaborador colaboradorASerPersistido = new Colaborador.Builder(null).comNome("bill").comCpf("99999").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();

        Colaborador colaborador = colaboradorRepository.save(colaboradorASerPersistido);

        Colaborador colaboradorAlterado = new Colaborador.Builder(colaborador.getId()).comNome("novo valor").comCpf("2222").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();

        Colaborador colaboradorComAlteracao = colaboradorRepository.save(colaboradorAlterado);

        assertThat(colaboradorComAlteracao.getId()).isEqualTo(colaborador.getId());
        assertThat(colaboradorComAlteracao.getNome()).isEqualTo("novo valor");
    }
}
