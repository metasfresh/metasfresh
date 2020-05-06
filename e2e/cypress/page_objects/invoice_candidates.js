import Metasfresh from './page';

class InvoiceCandidates extends Metasfresh {
  constructor() {
    super();

    this.windowId = 540092;
    this.tableRows = '.table-flex-wrapper-row';
    this.rowSelector = 'tbody tr';
    this.listHeader = '.document-list-header';
    this.selectedRows = '.row-selected';
  }

  visit() {
    cy.visit(`/window/${this.windowId}`);
  }

  getRows() {
    return cy.get(this.tableRows).find(this.rowSelector);
  }
}

export const invoiceCandidates = new InvoiceCandidates();
