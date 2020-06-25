import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";


export interface ILogTemplate {
  name: string;
  number: string;
  time: string;
  message: string;
  sendMessage: string;
}

@Injectable({
  providedIn: 'root'
})
export class SiemCenterService {

 constructor(private http: HttpClient ) {}

  createTemplate(log: ILogTemplate) {
    return this.http.post<ILogTemplate>('http://localhost:8081/siem-center/template',
      {
        name: log.name,
        number: log.number,
        time: log.time,
        message: log.message,
        sendMessage: log.sendMessage
      });
  }

  getAlarms(){
   return this.http.get('http://localhost:8081/siem-center/alarms');
  }

}
