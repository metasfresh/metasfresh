describe('New sales order test', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
    cy.visit('/window/143');
  });

  context('Create a new sales order', function() {
    before(function() {
      cy.get('.header-breadcrumb')
        .find('.header-item')
        .contains('Sales')
        .click();

      cy.get('.header-breadcrumb')
        .find('.menu-overlay')
        .should('exist')
        .find('.menu-overlay-link')
        .contains('New Sales Order')
        .click();

      cy.get('.header-breadcrumb-sitename').should('contain', '<');
    });

    it('Fill Business Partner', function() {
      cy.get('.input-dropdown-container .raw-lookup-wrapper')
        .first()
        .find('input')
        .clear()
        .type('G0001');

      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', 'G0001345a0_Test Kunde 1')
        .click();
      cy.get('.input-dropdown-list .input-dropdown-list-header')
        .should('not.exist');

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<');
    });

    it('Fill order reference to differentiate cypress tests', function() {
      cy.get('.form-field-POReference')
        .find('input')
        .type('Cyress Test{enter}');

      cy.get('.indicator-pending').should('exist');
      cy.wait(100);
      cy.get('.indicator-pending').should('not.exist');
    });

    it('Add new product', function() {
      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains('Add new')
        .should('exist')
        .click();

      cy.get('.panel-modal').should('exist');

      cy.get('.form-field-M_Product_ID')
        .find('input')
        .type('C');

      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', 'P002737_Convenience Salat 250g')
        .click();
      cy.get('.input-dropdown-list .input-dropdown-list-header')
        .should('not.exist');

      cy.get('.form-field-QtyEntered', { timeout: 12000 })
        .find('input')
        .should('not.have.value', '0');

      cy.get('.panel-modal-header')
        .find('.btn')
        .click();
    });

    it('Change document status', function() {
      cy.get('.form-field-DocAction')
        .find('.meta-dropdown-toggle')
        .click();

      cy.get('.form-field-DocAction')
        .find('.dropdown-status-toggler')
        .should('have.class', 'dropdown-status-open');

      cy.get('.form-field-DocAction .dropdown-status-list')
        .find('.dropdown-status-item')
        .contains('Complete')
        .click();

      cy.get('.indicator-pending', { timeout: 10000 }).should('not.exist');

      cy.get('.meta-dropdown-toggle .tag-success').contains('Completed');
    });
  });
});
