export class TransportationOrder {
  constructor() {
    this.tour = undefined;
  }

  setTour(tour) {
    cy.log(`TransportationOrder - set tour = ${tour}`);
    this.tour = tour;
    return this;
  }

  apply() {
    cy.log(`TransportationOrder - apply - START ${this.tour}`);
    applyTransportationOrder(this);
    cy.log(`TransportationOrder - apply - END ${this.tour}`);
    return this;
  }
}

function applyTransportationOrder(tOrder) {
  cy.visitWindow('540020');

  cy.clickHeaderNav(Cypress.messages.window.new.caption);
  cy.selectInListField('Shipper_BPartner_ID', tOrder.shipperBPartnerID);
  cy.selectInListField('Shipper_Location_ID', tOrder.shipperLocationID);
  cy.selectInListField('M_Tour_ID', tOrder.tour);
  cy.writeIntoStringField('DocumentNo', tOrder.documentNo);
}
