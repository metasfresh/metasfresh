export class SalesOrder {
  constructor(reference) {
    this.reference = reference;
    this.bPartner = undefined;
    this.bPartnerLocation = undefined;
  }

  setBPartner(bPartner) {
    cy.log(`SalesOrder - setBPartner = ${bPartner}`);
    this.bPartner = bPartner;
    return this;
  }

  setBPartnerLocation(location) {
    cy.log(`SalesOrder - setBPartnerLocation = ${location}`);
    this.bPartnerLocation = location;
    return this;
  }

  setPoReference(reference) {
    cy.log(`SalesOrder - setReference = ${reference}`);
    this.reference = reference;
    return this;
  }

  apply() {
    cy.log(`SalesOrder - apply - START (${this.reference})`);
    applySalesOrder(this);
    cy.log(`SalesOrder - apply - END (${this.reference})`);
    return this;
  }
}

function applySalesOrder(salesOrder) {
  describe(`Create new salesOrder`, function() {
    cy.visitWindow('143', 'NEW');
    cy.get('.header-breadcrumb-sitename').should('contain', '<');

    cy.writeIntoLookupListField('C_BPartner_ID', salesOrder.bPartner, salesOrder.bPartner);
    if (salesOrder.bPartnerLocation) {
      cy.writeIntoLookupListField(
        'C_BPartner_Location_ID',
        salesOrder.bPartnerLocation,
        salesOrder.bPartnerLocation,
        true /*typeList*/
      );
    }
    cy.get('.header-breadcrumb-sitename').should('not.contain', '<');

    cy.writeIntoStringField('POReference', salesOrder.reference);
  });
}
