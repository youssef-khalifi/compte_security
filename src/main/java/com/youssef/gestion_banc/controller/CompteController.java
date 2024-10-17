package com.youssef.gestion_banc.controller;


import com.youssef.gestion_banc.entities.Compte;
import com.youssef.gestion_banc.service.CompteService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/Comptes")


@OpenAPIDefinition(
        info = @Info(
                title = "Gestion des Comptes bancaire",
                description = " Gerer les opération de banque",
                version = "1.0.0"
        ),

        servers = @Server(
                url = "http://localhost:8080/"
        )
)


public class CompteController {

    @Autowired
    CompteService compteService;


    @PostMapping

    @Operation(
            summary = "Ajouter Un compte",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "Application/json",
                            schema = @Schema(implementation = Compte.class)
                    )
                ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "ajouter par succéses",
                            content = @Content(
                                    mediaType = "Application/json",
                                    schema = @Schema(implementation = Compte.class))
                    ),

                    @ApiResponse(responseCode = "400",description = "erreur données"),
                    @ApiResponse(responseCode ="500", description = "erreur server")
            }
            )


    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Compte> add(@RequestBody Compte compte){
        Compte compte1 = compteService.CreateCompte(compte);
        return ResponseEntity.ok(compte1);
    }


    @GetMapping
    @Operation(
            summary="Recuprer Liste des Compe",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Succès",
                            content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Compte.class)
                    )
                    ),
                    @ApiResponse(responseCode = "400", description = "Paramètre d'entrée non valide")
            }  )
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<Compte>> GetALL(){
        List<Compte> compteList = compteService.GetAllCompte();
        return ResponseEntity.ok(compteList);
    }



    @Operation(
            summary = "recuperer un compte par son Id",
            parameters = @Parameter(
                    name = "id",
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "bien recuperer",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Compte.class))
                    ),
                    @ApiResponse(responseCode = "404",description = "compte pas trouvé ")
            }
    )
    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/{id}")
    public ResponseEntity <Compte> GetbyId( @PathVariable Long id){
        Compte compte = compteService.GetCompteById(id);
        return compte.equals(null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(compte);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity  Delete( @PathVariable Long id){
        compteService.DeleteCompte(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Compte> Update(@PathVariable Long id, @RequestBody Compte c){
        Compte compte = compteService.UpdateCompte(id,c);
        return ResponseEntity.ok(compte);
    }


    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/crediter/{id}/{m}")
    public ResponseEntity<Compte> crediter(@PathVariable Long id, @PathVariable float m){
        Compte compte = compteService.Crediter(id,m);
        return ResponseEntity.ok(compte);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/debiter/{id}/{m}")
    public ResponseEntity<Compte> debiter(@PathVariable Long id, @PathVariable float m){
        Compte compte = compteService.Debiter(id,m);
        return ResponseEntity.ok(compte);
    }


}
