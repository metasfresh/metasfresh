describe('Business partner window widgets test', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
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
          .get('.header-breadcrumb-sitename')
          .should('contain', 'Cypress Test Partner #1');

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
          .contains('Pickup');
      });
    });
  });
});
