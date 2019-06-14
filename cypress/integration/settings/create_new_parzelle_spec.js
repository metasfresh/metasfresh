describe('Create new Parzelle/Allotment', function() {
  it('open new parzelle', function() {
    cy.visit('/window/540210');
    cy.get('.btn-square').click();
    cy.get('#subheaderNav_new').click();
  });

  it('create it', function() {
    cy.get('.elements-line > .form-group').children(
      'div[title="Search key for the record in the format required - must be unique"]'
    );
    cy.get(':nth-child(1) > .form-group > .col-sm-9 > .input-body-container > .input-block > .input-field')
      .click()
      .type('Industriestrasse');
    cy.get('.elements-line > .form-group').children('div[title="Name"]');
    cy.get(':nth-child(2) > .form-group > .col-sm-9 > .input-body-container > .input-block > .input-field')
      .click()
      .type('Industriestrasse')
      .trigger('esc');
  });
});
