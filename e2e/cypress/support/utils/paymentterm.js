export class PaymentTerm {
  setName(name) {
    cy.log(`PaymentTerm - set name = ${name}`);
    this.name = name;
    return this;
  }

  setNetDays(netDays) {
    cy.log(`PaymentTerm - set net days = ${netDays}`);
    this.netDays = netDays;
    return this;
  }

  setGraceDays(graceDays) {
    cy.log(`PaymentTerm - set grace days = ${graceDays}`);
    this.graceDays = graceDays;
    return this;
  }

  fillField(field, content) {
    cy.clearField(field);
    cy.writeIntoStringField(field, content);
  }

  apply() {
    cy.visitWindow('141', 'NEW');
    this.fillField('Name', this.name);
    this.fillField('Value', this.name);
    this.fillField('NetDays', this.netDays);
    this.fillField('GraceDays', this.graceDays);
    cy.getCheckboxValue('IsValid').then(isValidValue => {
      expect(isValidValue).to.equal(true);
    });
    return this;
  }
}
