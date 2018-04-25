describe('Business partner window widgets test', function() {
  before(function() {
    cy.visit('/login');

    cy.get('input[name=username]').type('kuba');
    cy.get('input[name=password]').type('kuba1234{enter}');

    // cy.get('button').click();

    cy.url().should('not.include', '/login');
    cy.get('.header-item').should('contain', 'Dashboard');
  });

  context('Tabs', function() {
    beforeEach(function() {
      cy.visit('/window/123/2156447');
      cy
        .get('.header-breadcrumb-sitename')
        .should('contain', 'Cypress Test Partner #1');
    });

    context('Vendor', function() {
      it('Check if list widget works properly', function() {
        cy
          .get('.nav-item[title="Vendor"]')
          .click()
          .get('.tab-content tbody td:nth-of-type(4)')
          .dblclick()
          .find('.input-dropdown-container')
          .should('have.class', 'focused')
          .click();

        cy
          .get('.input-dropdown-list')
          .should('exist')
          .find('.input-dropdown-list-option')
          .first()
          .click();

        cy
          .get('.tab-content tbody td:nth-of-type(4)')
          .should('not.have.class', 'pulse-on')
          .find('.input-editable')
          .should('not.have.class', 'opened');

        cy.get('.tab-content tbody td:nth-of-type(4)').click();

        cy
          .get('.input-dropdown-list')
          .should('exist')
          .find('.input-dropdown-list-option')
          .contains('none');
      });
    });
  });
});
