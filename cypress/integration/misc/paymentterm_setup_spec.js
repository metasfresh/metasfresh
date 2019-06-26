describe('Create test: payment term, https://github.com/metasfresh/metasfresh-e2e/issues/45', function() {
  it('Create a new Payment Term', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const paymenttermName = `ListPaymentTermName ${timestamp}`;
    const paymenttermValue = `ListPaymentTermValue ${timestamp}`;

    //create PaymentTerm 5 Tage Netto
    cy.visitWindow('141', 'NEW');
    cy.writeIntoStringField('Name', paymenttermName);
    cy.clearField('Value');
    cy.writeIntoStringField('Value', paymenttermValue);

    cy.clearField('NetDays');
    cy.writeIntoStringField('NetDays', '5');
    cy.clearField('GraceDays');
    cy.writeIntoStringField('GraceDays', '3');

    cy.getCheckboxValue('IsValid').then(isValidValue => {
      expect(isValidValue).to.equal(true);
    });
  });
});
