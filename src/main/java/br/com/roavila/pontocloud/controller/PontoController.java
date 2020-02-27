package br.com.roavila.pontocloud.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roavila.pontocloud.json.PontoJson;
import br.com.roavila.pontocloud.json.RelatorioPontoJson;
import br.com.roavila.pontocloud.mapper.PontoMapper;
import br.com.roavila.pontocloud.model.Ponto;
import br.com.roavila.pontocloud.model.RelatorioPonto;
import br.com.roavila.pontocloud.service.PontoService;

import java.net.URI;

@RestController
@RequestMapping("/v1")
@Tag(name = "pontos", description = "API do Ponto")
public class PontoController {

    @Autowired
    private PontoService pontoService;
    
    
    @Operation(summary = "Listagem dos pontos", description = "Lista dos pontos efetuados.", tags = { "pontos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PontoJson.class))))})
    @GetMapping("/pontos/{idColaborador}")
    public ResponseEntity<RelatorioPontoJson> gerarRelatorio(@Parameter(description = "Id do colaborador a ser listado os pontos", required = true) @PathVariable Long idColaborador) {

        RelatorioPonto relatorioPonto = pontoService.gerarRelatorio(idColaborador);
        RelatorioPontoJson relatorioPontoJson = new RelatorioPontoJson(relatorioPonto);

        return new ResponseEntity<>(relatorioPontoJson, HttpStatus.OK);
    }

    @Operation(summary = "Bater ponto", description = "Batida do ponto do colaborador.", tags = { "pontos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PontoJson.class))),
            @ApiResponse(responseCode = "400", description = "Usuário 0 não encontrado")})
    @PostMapping("/pontos")
    public ResponseEntity<PontoJson> baterPonto(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do ponto",
                                                    content = @Content(schema = @Schema(implementation = PontoJson.class, requiredProperties = {"colaborador"})), required = true)
                                                    @RequestBody PontoJson ponto) {
        Ponto pontoParaRegistrar = new PontoMapper().convertJsonToModel(ponto);
        Ponto pontoRegistrado = pontoService.registrarPonto(pontoParaRegistrar);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pontoRegistrado.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }


}
