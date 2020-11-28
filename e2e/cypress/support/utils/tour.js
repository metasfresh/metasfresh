export class Tour {
  constructor(name) {
    cy.log(`TourBuilder - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`TourBuilder - set name = ${name}`);
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
  describe(`Create new Tour ${tour.name}`, function() {
    cy.visitWindow(540331, 'NEW');
    cy.writeIntoStringField('Name', tour.name);
  });
}
