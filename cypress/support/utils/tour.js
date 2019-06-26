export class Tour {
  constructor(name) {
    this.name = name;
  }

  setName(name) {
    cy.log(`Tour - set name = ${name}`);
    this.name = name;
    return this;
  }

  apply() {
    cy.log(`Tour - apply - START (name=${this.name})`);
    applyTour(this);
    cy.log(`Tour - apply - END (name=${this.name})`);
    return this;
  }
}

function applyTour(tour) {
  cy.visitWindow('540331');
  cy.clickHeaderNav(Cypress.messages.window.new.caption);
  cy.writeIntoStringField('Name', tour.name);
}
