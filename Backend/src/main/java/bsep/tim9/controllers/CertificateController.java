package bsep.tim9.controllers;

import bsep.tim9.DTOs.EndUserCertificateDTO;
import bsep.tim9.DTOs.IntermediateCertificateDTO;
import bsep.tim9.exceptions.AliasAlreadyExistsException;
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

//    @GetMapping(value = "/enduser")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<Object> getEndUserCertificates() {
//        return new ResponseEntity<>(certificateService.getEndUserCertificates(), HttpStatus.OK);
//    }


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

    @GetMapping(value="/aliases")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getIssuers() {
        return new ResponseEntity<>(certificateService.getIssuers(), HttpStatus.OK);
    }

}
