import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class APIService {

  constructor(private http: HttpClient) { }

  public async sendGET(url: string, config?: any): Promise<any> {
    return await this.http.get(url, config || {}).toPromise();
  }

  public async sendPOST(url: string, body: {}, config?: any): Promise<any> {
    return await this.http.post(url, body, config || {}).toPromise();
  }

  public async sendPUT(url: string, body: {}, config?: any): Promise<any> {
    return await this.http.put(url, body, config || {}).toPromise();
  }

  public async sendDELETE(url: string): Promise<any> {
    return await this.http.delete(url).toPromise();
  }
}
