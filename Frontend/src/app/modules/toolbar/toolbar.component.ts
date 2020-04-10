import {Component, OnInit} from '@angular/core';
import {AuthenticationApiService} from "../../core/authentication-api.service";
import {Router} from '@angular/router';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  constructor(private authService: AuthenticationApiService,
              private router: Router) {
  }

  getRole: string = this.authService.getRole();

  ngOnInit() {
  }

  logOut() {
    const logoutObserver = {
      next: x => {
        localStorage.removeItem('token');
        console.log('You logged out successfully!');
        this.router.navigate(['/login'])
      },
      error: (err: Response) => {
        console.log(JSON.parse(JSON.stringify(err)).error);
      }
    };
    this.authService.logout().subscribe(logoutObserver);
  }

}
