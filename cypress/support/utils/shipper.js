export class Shipper {
  setName(name) {
    cy.log(`ShipperBuilder - set name = ${name}`);
    this.name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`ShipperBuilder - set description = ${description}`);
    this.description = description;
    return this;
  }
  setBPartner(bPartner) {
    cy.log(`ShipperBuilder - set bPartner = ${bPartner}`);
    this.bPartner = bPartner;
    return this;
  }

  apply() {
    cy.log(`Shipper - apply - START (name=${this.name})`);
    applyShipper(this);
    cy.log(`Shipper - apply - END (name=${this.name})`);
    return this;
  }
}
function applyShipper(shipper) {
  describe(`Create new Shipper ${shipper.name}`, function() {
    cy.visitWindow(142, 'NEW');
    cy.writeIntoStringField('Name', shipper.name);
    cy.writeIntoLookupListField('C_BPartner_ID', shipper.bPartner, shipper.bPartner);
    cy.writeIntoStringField('Description', shipper.description);
  });
}
