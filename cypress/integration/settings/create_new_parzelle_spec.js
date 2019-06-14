describe('Create new Parzelle/Allotment', function() {
  it('open new parzelle', function() {
    cy.visit('/window/540210');
    cy.clickHeaderNav('new');
  });

  it('create it', function() {
    cy.writeIntoStringField('Value', 'Industriestrasse');
    cy.writeIntoStringField('Name', 'Industriestrasse');
    cy.get(':nth-child(4) > .header-btn > .header-item-container > .header-item').click();
  });
});
