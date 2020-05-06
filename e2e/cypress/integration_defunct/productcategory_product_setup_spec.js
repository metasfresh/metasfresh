describe('Create a Product Category and Products', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
  });

  it('Create a new Product Category and Products', function() {
    //create ProductCategory
    cy.visit('/window/144/NEW');
    cy.writeIntoStringField('Name', 'TestProductCategory1');
    cy.writeIntoStringField('Value', 'T').type('{selectall}{backspace}');
    cy.writeIntoStringField('Value', '01');

    //set AttributeSet
    cy.get('.form-field-M_AttributeSet_ID')
      .find('input')
      .type('T');
    cy.get('.input-dropdown-list').should('exist');
    cy.contains('.input-dropdown-list-option', 'TestAttributeSet1').click();


    //create Product
    cy.visit('/window/140/NEW');
    cy.writeIntoStringField('Name', 'TestProduct1');

    cy.get('.form-field-M_Product_Category_ID')
      .find('input')
      .type('T');
    cy.get('.input-dropdown-list').should('exist');
    cy.contains('.input-dropdown-list-option', '01_TestProductCategory1').click();


    //set Product Price
    cy.get('tab_M_ProductPrice').click(); 
    cy.pressAddNewButton();
    cy.get('.form-field-M_PriceList_Version_ID')
      .find('input')
      .type('T')
    cy.get('.input-dropdown-list').should('exist');
    cy.contains('.input-dropdown-list-option', 'TestPLV').click();
    cy.writeIntoStringField('PriceStd', '2').type('{selectall}{backspace}');
    cy.writeIntoStringField('PriceStd', '2,00');
    cy.get('.form-field-field-C_TaxCategory_ID')
      .find('input')
      .type('T')
    cy.get('.input-dropdown-list').should('exist');
    cy.contains('.input-dropdown-list-option', 'Reduzierter Satz Waren/ DL').click();
    cy.get('.items-row-2 > .btn').click();



    cy.get('.form-field-...')
      .find('.input-checkbox-tick')
      .click();
    });
});