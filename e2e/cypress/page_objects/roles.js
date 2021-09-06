import Metasfresh from './page';

class Roles extends Metasfresh {
  constructor() {
    super();

    this.windowId = 111;
    this.tableRows = '.table-flex-wrapper-row';
    this.rowSelector = 'tbody tr';
    this.listHeader = '.document-list-header';
    this.selectedRows = '.row-selected';
    this.tableHeader = '.table thead tr';
  }

  visit() {
    cy.visit(`/window/${this.windowId}`);
  }

  visitNew() {
    cy.visit(`/window/${this.windowId}/NEW`);
  }

  verifyElements() {
    cy.get(this.tableRows).find(this.rowSelector).eq(0).should('exist');
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

  // This function shows how you can check if element containing text exists
  valueExists(val) {
    return this.getRows().then((rows) => {
      let exists = false;

      for (let i = 0; i < rows.length; i += 1) {
        if (rows[i] && rows[i].textContent.includes(val)) {
          exists = true;
          break;
        }
      }
      return exists;
    });
  }

  clickListHeader() {
    // TODO: Find scrollable element instead of forcing click
    cy.get(this.listHeader).click({ force: true });
  }
}

export const roles = new Roles();
