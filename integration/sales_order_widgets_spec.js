describe('Sales order window widgets test', function() {
  before(function(){
    // login before each test
    cy.loginByForm('kuba', 'kuba1234');
  });

  context('Toggle widgets', function() {
    beforeEach(function() {
      cy.visit('/window/143/1000489');
      cy.get('.header-breadcrumb-sitename').should('contain', '0359');
    });

    it('Select lookup option', function() {
      cy
        .get('.input-dropdown-container .raw-lookup-wrapper')
        .first()
        .find('input')
        .clear()
        .type('k');

      cy.get('.input-dropdown-list').should('exist');
      cy
        .contains('.input-dropdown-list-option', '1000004_kaystest')
        .click();
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');
    });
  });
});
