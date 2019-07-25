export default class MetasfreshPage {
  constructor() {
    this.breadcrumbs = '.header-breadcrumb';
    this.sitename = '.header-breadcrumb-sitename';
  }

  getBreadcrumbs() {
    return cy.get(this.breadcrumb);
  }

  getSitename() {
    return cy.get(this.sitename);
  }

  selectAllVisibleRows() {
    cy.get('body').type('{alt}a');
  }
}
