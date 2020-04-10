package bsep.tim9.controllers;

import bsep.tim9.DTOs.EndUserCertificateDTO;
import bsep.tim9.DTOs.IntermediateCertificateDTO;
import bsep.tim9.exceptions.AliasAlreadyExistsException;
import bsep.tim9.model.CertificateType;
import bsep.tim9.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificate")
@CrossOrigin
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping(value = "/enduser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createEndUserCertificate(@RequestBody EndUserCertificateDTO endUserCertificateDTO) {
        try {
            return new ResponseEntity<>(certificateService.createEndUserCertificate(endUserCertificateDTO), HttpStatus.OK);
        } catch (AliasAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/intermediate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createIntermediateCertificate(@RequestBody IntermediateCertificateDTO intermediateCertificateDTO) {
        try {
            return new ResponseEntity<>(certificateService.createIntermediateCertificate(intermediateCertificateDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllCertificates() {
        return new ResponseEntity<>(certificateService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value="/all/{type}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllCertificatesByType(@PathVariable("type") String type) {
        return new ResponseEntity<>(certificateService.getAllByType(CertificateType.valueOf(type.toUpperCase())), HttpStatus.OK);
    }

    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> revokeCertificate(@PathVariable("id") String alias){
        certificateService.revoke(alias);
        return new ResponseEntity<>(certificateService.getOne(alias), HttpStatus.OK);
    }

}
