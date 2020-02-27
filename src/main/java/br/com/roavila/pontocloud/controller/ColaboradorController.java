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

import br.com.roavila.pontocloud.json.ColaboradorJson;
import br.com.roavila.pontocloud.mapper.ColaboradorMapper;
import br.com.roavila.pontocloud.model.Colaborador;
import br.com.roavila.pontocloud.service.ColaboradorService;

import java.net.URI;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/v1")
@Tag(name = "colaboradores", description = "API de colaborador")
public class ColaboradorController {

    @Autowired
    private ColaboradorService service;

    @Operation(summary = "Atualiza informações do colaborador", description = "Atualiza informações do colaborador.", tags = { "colaborador" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ColaboradorJson.class)))})
    @PutMapping("/colaboradores/{id}")
    public ResponseEntity<ColaboradorJson> atualizar(@Parameter(description = "Dados do colaborador a ser atualizado", required = true) @RequestBody ColaboradorJson colaborador,
                                                 @Parameter(description = "Id do colaborador para atualizar", required = true) @PathVariable Long id) {

        ColaboradorJson colaboradorAtualizado = new ColaboradorMapper().convertModelToJson(service.atualizar(new ColaboradorMapper().convertJsonToModel(colaborador), id));

        return new ResponseEntity<>(colaboradorAtualizado, HttpStatus.OK);
    }

    @Operation(summary = "Inclusão do colaborador", description = "Inclusão dos colaboradores.", tags = { "colaborador" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ColaboradorJson.class)))})
    @PostMapping("/colaboradores")
    public ResponseEntity<ColaboradorJson> salvar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do colaborador",
                                                    content = @Content(schema = @Schema(implementation = ColaboradorJson.class)), required = true)
                                                  @RequestBody ColaboradorJson colaborador) {

        ColaboradorJson colaboradorJson = new ColaboradorMapper().convertModelToJson(service.criar(new ColaboradorMapper().convertJsonToModel(colaborador)));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(colaboradorJson.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    
    @Operation(summary = "Listagem de colaboradores", description = "Lista todos os colaboradores cadastrados.", tags = { "colaborador" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ColaboradorJson.class))))})
    @GetMapping("/colaboradores")
    public ResponseEntity<List<ColaboradorJson>> listagemColaborador() {

        List<ColaboradorJson> colaboradores = service.listar().stream().map(convert()).collect(Collectors.toList());

        return new ResponseEntity<>(colaboradores, HttpStatus.OK);
    }


    @Operation(summary = "Busca por um colaborador", description = "Exibe as informações do colaborador.", tags = { "colaborador" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ColaboradorJson.class)))})
    @GetMapping("/colaboradores/{id}")
    public ResponseEntity<ColaboradorJson> consulta(@Parameter(description = "Id do colaborador", required = true) @PathVariable Long id) {

        ColaboradorJson colaborador = new ColaboradorMapper().convertModelToJson(service.getBy(id));

        return new ResponseEntity<>(colaborador, HttpStatus.OK);

    }
    
    private Function<Colaborador, ColaboradorJson> convert() {
        return (item) -> new ColaboradorMapper().convertModelToJson(item);
    }

}
