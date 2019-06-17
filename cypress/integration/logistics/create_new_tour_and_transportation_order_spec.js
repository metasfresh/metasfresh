describe('create new tour and transportation order', function() {
  before(function() {
    cy.visit('/window/540331');
  });

  const testTour = 'TestTour';

  it('create tour', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);
    cy.writeIntoStringField('Name', testTour);
  });

  it('create transportation order', function() {
    cy.visit('/window/540020');

    cy.clickHeaderNav(Cypress.messages.window.new.caption);
    cy.selectInListField('Shipper_BPartner_ID', 'metasfresh AG');
    cy.selectInListField('Shipper_Location_ID', 'Am Nossbacher Weg 2');
    cy.selectInListField('M_Tour_ID', testTour);
    cy.writeIntoStringField('DocumentNo', 'X');
  });
});
