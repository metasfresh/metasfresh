describe('Sales order window widgets test', function() {
  before(function() {
    cy.loginByForm();
    cy.visit('/window/143/1000489');
    cy.get('.header-breadcrumb-sitename').should('contain', '0359');
  });

  // This is tested in sales_order_spec.js
  // context('Toggle widgets', function() {
    // beforeEach(function() {
    //   cy.visit('/window/143/1000489');
    //   cy.get('.header-breadcrumb-sitename').should('contain', '0359');
    // });

    // it('Select lookup option', function() {
    //   cy
    //     .get('.input-dropdown-container .raw-lookup-wrapper')
    //     .first()
    //     .find('input')
    //     .clear()
    //     .type('test');

    //   cy.get('.input-dropdown-list').should('exist');
    //   cy.contains('.input-dropdown-list-option', '1000004_kaystest').click();
    //   cy
    //     .get('.input-dropdown-list .input-dropdown-list-header')
    //     .should('not.exist');
    // });
  // });

  context('Edit product in list', function() {
    it('Change product attributes', function() {
      cy.get('.ProductAttributes').find('.productattributes-cell').last().dblclick();

      cy.get('.form-field-M_AttributeSetInstance_ID').should('exist')
        .find('button').click();

      cy.get('.attributes-dropdown').should('exist');

      cy.get('.form-field-IsRepackNumberRequired')
        .find('.input-dropdown-container')
        .click();

      cy.get('.input-dropdown-list').should('exist')
        .find('.input-dropdown-list-option')
        .contains('Yes')
        .click()

      // this should hide the attributes panel
      cy.get('.row-selected .ProductAttributes').click();

      //this command doesn't really work right now, but hopefuly will work soon :)
      cy.get('body').type('{esc}');
      cy.get('.input-dropdown-list').should('not.exist');

      cy.get('.form-field-M_AttributeSetInstance_ID').contains('Yes');
    });

    // cleanup
    after(function() {
      cy.get('.ProductAttributes').find('.productattributes-cell').last().click();

      cy.get('.form-field-M_AttributeSetInstance_ID')
        .find('button').click();

      cy.get('.form-field-IsRepackNumberRequired')
        .find('.input-dropdown-container')
        .click();

      cy.get('.input-dropdown-list').should('exist')
        .find('.input-dropdown-list-option')
        .contains('No')
        .click()

      cy.get('.row-selected .Integer').click();
    });
  });
});
