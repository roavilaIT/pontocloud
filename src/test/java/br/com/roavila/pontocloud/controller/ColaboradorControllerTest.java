package br.com.roavila.pontocloud.controller;


import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.roavila.pontocloud.json.ColaboradorJson;
import br.com.roavila.pontocloud.mapper.ColaboradorMapper;
import br.com.roavila.pontocloud.model.Colaborador;
import br.com.roavila.pontocloud.service.ColaboradorService;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ColaboradorControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ColaboradorService colaboradorService;


    @Test
    public void deveRetornarListagemColaboradors() {
        List<Colaborador> colaboradorsPersistidos = Lists.newArrayList(new Colaborador.Builder(1L).comNome("urko").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build());

        when(colaboradorService.listar()).thenReturn(colaboradorsPersistidos);
        ResponseEntity<List<ColaboradorJson>> response = this.restTemplate.exchange(colaboradorsEndpoint(), HttpMethod.GET, null, new ParameterizedTypeReference<List<ColaboradorJson>>() {});

        List<ColaboradorJson> colaboradores = response.getBody();
        assertThat(colaboradores.get(0).getId() == 1L);
        assertThat(colaboradores.get(0).getNome().equals("urko"));
        assertThat(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void deveRetornarColaborador() {
        Colaborador colaborador = new Colaborador.Builder(1L).comNome("urko").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();

        when(colaboradorService.getBy(anyLong())).thenReturn(colaborador);
        ResponseEntity<ColaboradorJson> response = this.restTemplate.exchange(colaboradorsEndpoint()+"/1", HttpMethod.GET, null, new ParameterizedTypeReference<ColaboradorJson>() {});

        ColaboradorJson colaboradorRetornado = response.getBody();
        assertThat(colaboradorRetornado.getId() == 1L);
        assertThat(colaboradorRetornado.getNome().equals("urko"));
        assertThat(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void deveIncluirColaborador() {

        Colaborador colaborador = new Colaborador.Builder(null).comNome("urko").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();
        Colaborador colaboradorPersistido = new Colaborador.Builder(1L).comNome("urko").comCpf("123456").comEmail("teste@itau.com.br").comDataCadastro(LocalDate.now()).build();
        ColaboradorJson colaboradorJson = new ColaboradorMapper().convertModelToJson(colaborador);

        when(colaboradorService.criar(any())).thenReturn(colaboradorPersistido);
        HttpEntity<ColaboradorJson> body = new HttpEntity<>(colaboradorJson);
        ResponseEntity<ColaboradorJson> response = this.restTemplate.exchange(colaboradorsEndpoint(), HttpMethod.POST, body, new ParameterizedTypeReference<ColaboradorJson>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }
    
    

    private String host() {
        return "http://localhost:" + port + "/pontocloud/v1/";
    }

    private String colaboradorsEndpoint() {
        return host() + "colaboradores";
    }
}
