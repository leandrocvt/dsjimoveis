package com.dsj.imoveis.resource;

import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.dto.ImmobileMinDTO;
import com.dsj.imoveis.lib.enums.ImmobileCategory;
import com.dsj.imoveis.lib.enums.OptionImmobile;
import com.dsj.imoveis.service.ImmobileService;
import com.dsj.imoveis.service.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "immobile")
public class ImmobileController {

    private final ImmobileService service;

    private final S3Service s3Service;

    private final PagedResourcesAssembler<ImmobileMinDTO> pagedResourcesAssembler;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ImmobileDTO> save(@RequestParam("json") String json,
                                            @RequestParam("files") List<MultipartFile> files){

        ObjectMapper objectMapper = new ObjectMapper();
        ImmobileDTO dto;
        try {
            dto = objectMapper.readValue(json, ImmobileDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar o JSON", e);
        }

        dto = service.save(dto, files);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ImmobileDTO> findById(@PathVariable Long id){
        ImmobileDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ImmobileMinDTO>>> findAll(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "category", defaultValue = "") ImmobileCategory category,
            @RequestParam(value = "subtype", defaultValue = "") String subtype,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "neighborhood", required = false) String neighborhood,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "bedrooms", required = false) Integer bedrooms,
            @RequestParam(value = "suites", required = false) Integer suites,
            @RequestParam(value = "garage", required = false) Integer garage,
            @RequestParam(value = "zipCode", required = false) String zipCode,
            @RequestParam(value = "option", defaultValue = "") OptionImmobile option,
            Pageable pageable
            ) {
        Page<ImmobileMinDTO> dto = service.findAll(title, category, subtype, city, state, neighborhood, minPrice, maxPrice, bedrooms, suites, garage, zipCode, option, pageable);
        PagedModel<EntityModel<ImmobileMinDTO>> pagedModel = pagedResourcesAssembler.toModel(dto);
        return ResponseEntity.ok(pagedModel);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ImmobileDTO> update(
            @PathVariable Long id,
            @RequestParam("json") String json,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        ImmobileDTO dto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            dto = objectMapper.readValue(json, ImmobileDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON do DTO", e);
        }

        ImmobileDTO updatedDto = service.update(id, dto, files);
        return ResponseEntity.ok(updatedDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
