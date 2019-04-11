describe('Create a Product Category and Products with Product Price', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
  });

  it('Create a new Product Category and Products', function() {
    //create ProductCategory
    cy.visitWindow('144', 'NEW');
    cy.writeIntoStringField('Name', 'TestProductCategory1');
    cy.clearField('Value');
    cy.writeIntoStringField('Value', 'TestProductCategory1');

    //create AttributeSet to use
    new AttributeSet(vendorName)
      .setVendor(true)
      .setVendorPricingSystem('Testpreisliste Lieferanten')
      .setVendorDiscountSchema(discountSchemaName)

      .addContact(
        new BPartnerContact()
          .setFirstName('Default')
          .setLastName('Contact')
          .setDefaultContact(true)
      );

    //set AttributeSet
    cy.selectInListField('M_AttributeSet_ID', 'TestAttributeSet1');

    //create Product
    cy.visitWindow('140', 'NEW');
    cy.writeIntoStringField('Name', 'TestProduct1');
    cy.selectInListField('M_Product_Category_ID', 'TestProductCategory1_TestProductCategory1');

    //set Product Price
    cy.get('#tab_M_ProductPrice').click();
    cy.pressAddNewButton();
    cy.selectInListField('M_PriceList_Version_ID', 'TestPristList');
    cy.clearField('PriceStd');
    cy.writeIntoStringField('PriceStd', '2,00');
    cy.selectInListField('C_TaxCategory_ID', 'Reduzierter Satz Waren/ DL');
    cy.pressDoneButton();
  });
});
