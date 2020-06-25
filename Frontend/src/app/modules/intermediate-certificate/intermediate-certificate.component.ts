import { Component, OnInit } from '@angular/core';
import {IssuerModel} from "../../shared/model/issuer.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CertificateApiService} from "../../core/certificate-api.service";
import {Router} from "@angular/router";
import {X500NameModel} from "../../shared/model/x500-name.model";
import {EndUserCertificateModel} from "../../shared/model/end-user-certificate.model";
import {IntermediateCertificateModel} from "../../shared/model/intermediate-certificate.model";


@Component({
  selector: 'app-intermediate-certificate',
  templateUrl: './intermediate-certificate.component.html',
  styleUrls: ['./intermediate-certificate.component.scss']
})
export class IntermediateCertificateComponent implements OnInit {
  issuers: IssuerModel[] = [];

  ngForm = new FormGroup({
    _cn: new FormControl('', [Validators.required]),
    _surname: new FormControl('', [Validators.required]),
    _givenname: new FormControl('', [Validators.required]),
    _o: new FormControl('', [Validators.required]),
    _ou: new FormControl('', [Validators.required]),
    _c: new FormControl('', [Validators.required]),
    _e: new FormControl('', [Validators.required]),
    _uid: new FormControl('', [Validators.required]),
    issuerAlias: new FormControl(null, [Validators.required]),
    subjectAlias: new FormControl('', [Validators.required])}
  );

  constructor(private certificateService: CertificateApiService, private router: Router) { }

  ngOnInit() {
    this.getCertificates();
  }

  onSubmit() {
    const createICObserver = {
      next: x =>{
        console.log("Intermediate Certificate created successfully!");
        this.router.navigate(['/dashboard/ic/preview']);
      },
      error: (err: any) => {
        console.log(err);
      }
    };
    const x500NameModel: X500NameModel = new X500NameModel(
      this.ngForm.controls['_cn'].value, this.ngForm.controls['_surname'].value, this.ngForm.controls['_givenname'].value,
      this.ngForm.controls['_o'].value, this.ngForm.controls['_ou'].value, this.ngForm.controls['_c'].value,
      this.ngForm.controls['_e'].value, this.ngForm.controls['_uid'].value
    );
    const icModel: IntermediateCertificateModel = new IntermediateCertificateModel(
      x500NameModel, this.ngForm.controls['issuerAlias'].value, this.ngForm.controls['subjectAlias'].value
    );

    this.certificateService.createIntermediateCertificate(icModel).subscribe(createICObserver);
  }

  private getCertificates() {
    this.certificateService.getAllCertificates().subscribe({
      next: (result: IssuerModel[]) => {
        this.issuers = result;
      },
      error: (message: string) => {
        console.log(message);
      }
    })
  }

}
