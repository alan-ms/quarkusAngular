import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

import { WorldBankApiService } from 'src/app/service/world-bank-api/world-bank-api.service';
import { IIndicator } from 'src/app/model/indicator.model';

@Component({
  selector: 'app-indicator',
  templateUrl: './indicator.component.html',
  styleUrls: ['./indicator.component.scss']
})
export class IndicatorComponent implements OnInit  {
  isLoading = true;
  columnsToDisplay = ['date', 'indicator'];
  dataSourceContries = new MatTableDataSource<IIndicator>([]);

  countryId: string = '';
  indicators: IIndicator[] = [];

  @ViewChild(MatPaginator, {static: false})
  set paginator(value: MatPaginator) {
    if (this.dataSourceContries) {
      this.dataSourceContries.paginator = value;
    }
  }

  @ViewChild(MatSort, {static: false})
  set sort(value: MatSort) {
    if (this.dataSourceContries) {
      this.dataSourceContries.sort = value;
    }
  }

  constructor(
    private activatedRoute: ActivatedRoute,
    private worldBankApiService: WorldBankApiService,
    private snackMessage: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
      this.activatedRoute.queryParams.subscribe(params => {
        this.countryId = params[`countryId`];
      });
      this.worldBankApiService.getIndicators(this.countryId)
        .subscribe((res: HttpResponse<IIndicator[]>) => {
            this.indicators = res.body ?? [];
            this.dataSourceContries = new MatTableDataSource<IIndicator>(this.indicators);
            this.isLoading = false;
        }, (error: HttpErrorResponse) => {
          this.snackMessage.open('Não foi possível encontrar indicadores de pobreza, tente novamente.', 'Fechar');
          this.router.navigate(['home']);
        });
  }
}
