/*
 * #%L
 * metasfresh-e2e
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import Metasfresh from './page';

class _BillingCandidates extends Metasfresh {
  constructor() {
    super();

    this.windowId = '540092';
    this.tableRows = '.table-flex-wrapper-row';
    this.rowSelector = 'tbody tr';
    this.listHeader = '.document-list-header';
    this.selectedRows = '.row-selected';
  }

  visit() {
    cy.visitWindow(this.windowId);
  }

  getRows() {
    return cy.get(this.tableRows).find(this.rowSelector);
  }
}

export const BillingCandidates = new _BillingCandidates();
