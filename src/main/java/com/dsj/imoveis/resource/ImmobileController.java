package com.dsj.imoveis.resource;

import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.service.ImmobileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "immobile")
public class ImmobileController {

    private final ImmobileService service;

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

}
