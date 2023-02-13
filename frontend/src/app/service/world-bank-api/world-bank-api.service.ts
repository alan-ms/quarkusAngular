import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICountry } from 'src/app/model/country.model';
import { IIndicator } from 'src/app/model/indicator.model';

@Injectable({ providedIn: 'root' })
export class WorldBankApiService {

  public URL_DEFAULT = 'http://localhost:8080/api/countries';

  constructor(private http: HttpClient) { }

  getContries(): Observable<HttpResponse<ICountry[]>> {
    return this.http.get<ICountry[]>(this.URL_DEFAULT, {observe: 'response'}) ;
  }

  getIndicators(countryId: string): Observable<HttpResponse<IIndicator[]>> {
    return this.http.get<IIndicator[]>(`${this.URL_DEFAULT}/${countryId}/indicators`, {observe: 'response'});
  }
}
