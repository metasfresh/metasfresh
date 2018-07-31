describe('Business partner window widgets test', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
    cy.visit('/window/123/2156447');
    cy
      .get('.header-breadcrumb-sitename')
      .should('contain', 'Cypress Test Partner #1');
  });

  context('Tabs', function() {
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
          .contains('Pickup');
      });
    });
  });

  context('Location', function() {

    it('Edit existing address', function() {
      cy.get('.nav-item[title="Location"]').click()

      cy.get('.tab-content table tbody tr').last().find('td').first().should('exist')
      .trigger('contextmenu')
      cy.get('.context-menu').find('.context-menu-item')
        .contains('Advanced edit')
        .click();

      cy.get('.panel-modal').should('exist')
        .get('.form-field-C_Location_ID')
        .find('button')
        .click();

      cy.get('.panel-modal .attributes-dropdown').should('exist')
        .get('.form-field-C_Country_ID')
        .find('.input-icon-lg')
        .click();

      cy.get('.panel-modal .form-field-C_Country_ID')
        .find('input')
        .type('Germany')

        cy.get('.input-dropdown-list')
          .should('exist')
          .find('.input-dropdown-list-option')
          .contains('Germany')
          .click();

        cy.get('.panel-modal-header').click().find('button').click();
        cy.get('.input-dropdown-list').should('not.exist');
    });

    after(function() {
      cy
        .get('.nav-item[title="Location"]')
        .click()

      cy.get('.tab-content table tr').last().trigger('contextmenu')
      cy.get('.context-menu').find('.context-menu-item')
        .contains('Advanced edit')
        .click();

      cy.get('.panel-modal')
        .get('.form-field-C_Location_ID')
        .find('button')
        .click();

      cy.get('.panel-modal .attributes-dropdown')
        .get('.form-field-C_Country_ID')
        .find('.input-icon-lg')
        .click();

      cy.get('.panel-modal .form-field-C_Country_ID')
        .find('input')
        .type('Poland')

        cy.get('.input-dropdown-list')
          .find('.input-dropdown-list-option')
          .contains('Poland')
          .click();

        cy.get('.panel-modal-header').click().find('button').click();    
    });
  });
});
