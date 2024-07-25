package com.dsj.imoveis.resource;

import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.dto.ImmobileMinDTO;
import com.dsj.imoveis.lib.enums.ImmobileCategory;
import com.dsj.imoveis.service.ImmobileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "immobile")
public class ImmobileController {

    private final ImmobileService service;

    private final PagedResourcesAssembler<ImmobileMinDTO> pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<ImmobileDTO> save(@RequestBody ImmobileDTO dto){
        dto = service.save(dto);
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
            @RequestParam(value = "zipCode", required = false) String zipCode,
            Pageable pageable
            ) {
        Page<ImmobileMinDTO> dto = service.findAll(title, category, subtype, city, state, neighborhood, minPrice, maxPrice, bedrooms, zipCode, pageable);
        PagedModel<EntityModel<ImmobileMinDTO>> pagedModel = pagedResourcesAssembler.toModel(dto);
        return ResponseEntity.ok(pagedModel);
    }


}
