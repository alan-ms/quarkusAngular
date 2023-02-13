import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, startWith} from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';

import { WorldBankApiService } from 'src/app/service/world-bank-api/world-bank-api.service';
import { ICountry } from 'src/app/model/country.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  constructor(
    private fb: FormBuilder, private router: Router,
    private worldBankApiService: WorldBankApiService,
    private snackMessage: MatSnackBar
  ) {}
  isError = false;

  countries: ICountry[] = [];
  filteredCountries: Observable<ICountry[]>;

  searchForm = this.fb.group({
    countryId: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(3)]],
    countryName: ['']
  });

  ngOnInit() {
    this.worldBankApiService.getContries()
      .subscribe((res: HttpResponse<ICountry[]>) => {
        this.countries = res.body ?? [];
      }, (error: HttpErrorResponse) => {
        this.isError = true;
        this.snackMessage.open('Não foi possível buscar os países, tente novamente fazendo recarregamento da página', 'Fechar');
      });
    this.filteredCountries = this.searchForm.get(['countryId']).valueChanges
                                .pipe(startWith(''), map((value) => this.filterCountries(value)));
  }

  filterCountries(countryId: string) {
    const countryFilter = countryId.toUpperCase();
    return this.countries.filter(country =>
      country.id.toUpperCase().includes(countryFilter) || country.name.toUpperCase().includes(countryFilter));
  }

  searchIndicator(): void {
    this.router.navigate(['/indicator', { countryId: this.searchForm.get('countryId').value }]);
  }
}
