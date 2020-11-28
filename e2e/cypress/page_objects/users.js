import Metasfresh from './page';

class Users extends Metasfresh {
  constructor() {
    super();

    this.windowId = 108;
    this.tableRows = '.table-flex-wrapper-row';
    this.rowSelector = 'tbody tr';
    this.listHeader = '.document-list-header';
    this.selectedRows = '.row-selected';
    this.tableHeader = '.table thead tr';
  }

  visit() {
    cy.visit(`/window/${this.windowId}`);
  }

  verifyElements() {
    cy.get(this.tableRows)
      .find(this.rowSelector)
      .eq(0)
      .should('exist');
  }

  getRows() {
    return cy.get(this.tableRows).find(this.rowSelector);
  }

  getSelectedRows() {
    return cy.get(this.selectedRows);
  }

  getRowWithValue(val) {
    return cy.get(this.tableRows).contains(val);
  }

  getHeaderFilter(name) {
    return cy.get(this.tableHeader).contains(name);
  }

  clickListHeader() {
    // TODO: Find scrollable element instead of forcing click
    // cy.get('body').scrollTo('top')
    // cy.get(this.documentList).scrollTo('top');
    // cy.get(this.listHeader).scrollIntoView();
    // cy.scrollTo('bottom')
    // cy.get('#root').scrollTo(0, 10);
    // cy.get('.document-list-wrapper').scrollTo(0, 0);
    // cy.get('.header-sticky-distance').scrollTo('top')
    cy.get(this.listHeader).click({ force: true });
  }
}

export const users = new Users();
