describe('Sales order window widgets test', function () {
  before(function () {
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

  context('Add/remove product', function () {
    it('Add product using batch entry', function () {
      cy.intercept('POST', '/rest/api/window/143/1000489/187/quickInput').as('postAddress');
      cy.pressBatchEntryButton();

      cy.wait('@postAddress').then((xhr) => {
        const requestId = xhr.response.body.id;

        cy.intercept('PATCH', `/rest/api/window/143/1000489/187/quickInput/${requestId}`).as('patchAddress');
        cy.writeIntoLookupListField('M_Product_ID', 'co', 'Convenience Salat 250g_P002737');
        cy.wait('@patchAddress');
        cy.get('#lookup_M_HU_PI_Item_Product_ID input').should('have.value', 'IFCO 6410 x 10 Stk');
        cy.writeIntoStringField('Qty', '2').type('{enter}');

        cy.get('.table-flex-wrapper').find('tbody tr').should('have.length', 3);
      });
    });

    after(function () {
      cy.get('.table-flex-wrapper').find('tbody tr').eq(2).click({ force: true }).find('td').first().trigger('contextmenu', { force: true });

      cy.get('.context-menu-open').should('exist');
      cy.get('body').type('{alt}Y');

      cy.get('.panel-modal-primary').should('exist').find('.prompt-button-wrapper .btn-submit').click();

      cy.get('.panel-modal-primary').should('not.exist');

      cy.get('.table-flex-wrapper').find('tbody tr').should('have.length', 2);
    });
  });

  context('Edit product in list', function () {
    it('Change product attributes', function () {
      cy.get('.ProductAttributes').find('.productattributes-cell').last().dblclick();

      cy.get('.form-field-M_AttributeSetInstance_ID').should('exist').find('button').click({ force: true });

      cy.get('.attributes-dropdown').should('exist');

      cy.get('.form-field-IsRepackNumberRequired').find('.input-dropdown-container').click();

      cy.get('.input-dropdown-list').should('exist').find('.input-dropdown-list-option.ignore-react-onclickoutside').contains('Yes').click();

      // this should hide the attributes panel
      cy.get('.row-selected .ProductAttributes').click();

      //this command doesn't really work right now, but hopefuly will work soon :)
      cy.get('body').type('{esc}');
      cy.get('.input-dropdown-list').should('not.exist');

      cy.get('.form-field-M_AttributeSetInstance_ID').contains('Yes');
    });

    // cleanup
    after(function () {
      cy.get('.ProductAttributes').find('.productattributes-cell').last().click();

      cy.get('.form-field-M_AttributeSetInstance_ID').find('button').click({ force: true });

      cy.get('.form-field-IsRepackNumberRequired').find('.input-dropdown-container').click();

      cy.get('.input-dropdown-list').should('exist').find('.input-dropdown-list-option.ignore-react-onclickoutside').contains('No').click();

      cy.get('.row-selected .Integer').click();
    });
  });
});
