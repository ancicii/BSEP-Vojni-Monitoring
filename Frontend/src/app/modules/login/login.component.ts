import { Input, Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationApiService} from "../../core/authentication-api.service";
import {LoginDto} from "../../shared/model/login-dto.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {

  constructor(private authService:AuthenticationApiService,
              private router: Router) {
  }

  ngOnInit() {
  }

  ngForm = new FormGroup({
    email: new FormControl('', [Validators.required,
      Validators.pattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")]),
    password: new FormControl('', [Validators.required])
  });

  onSubmit() {
    const loginObserver = {
      next: x =>{
        localStorage.setItem('token', x);
        console.log("Welcome!");
        this.router.navigate(['/dashboard/events/preview']);
      },
      error: (err: any) => {
        console.log(err);
        console.log(JSON.parse(JSON.stringify(err))["error"]);
      }
    };
    const loginDTO: LoginDto = new LoginDto(
      this.ngForm.controls['email'].value, this.ngForm.controls['password'].value
    );

    this.authService.login(loginDTO).subscribe(loginObserver);
  }

}
