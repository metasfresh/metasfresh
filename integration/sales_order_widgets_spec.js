describe('Sales order window widgets test', function() {
  before(function() {
    cy.visit('/login');

    cy.get('input[name=username]').type('kuba');
    cy.get('input[name=password]').type('kuba1234{enter}');

    cy.url().should('not.include', '/login');
    cy.get('.header-item').should('contain', 'Dashboard');
  });

  context('Toggle widgets', function() {
    beforeEach(function() {
      cy.visit('/window/143/1000489');
      cy.get('.header-breadcrumb-sitename').should('contain', '0319');
    });

    it('Select lookup option', function() {
      cy
        .get('.input-dropdown-container .raw-lookup-wrapper')
        .first()
        .find('input')
        .clear()
        .type('k');

      cy.get('.input-dropdown-list.lookuplist').should('exist');
      cy
        .get('.input-dropdown-item-title')
        .contains('kaystest')
        .click();
      cy.get('.input-dropdown-list.lookuplist').should('not.exist');
    });
  });
});
