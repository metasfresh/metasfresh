describe('Create Attribute Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/me03/issues/1362', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
  });

  it('Create a new Attribute Set and Attributes', function() {

    //create Attribute1
    cy.visitWindow('260', 'NEW');
    cy.writeIntoStringField('Name', 'TestAttribute1');
    cy.writeIntoStringField('Description', 'TestAttribute1');
    cy.clearField('Value');
    cy.writeIntoStringField('Value', 'TestAttribute1');
    
    cy.selectInListField('AttributeValueType', 'List')
    cy.clickOnCheckBox('IsInstanceAttribute', 'Y')
    cy.clickOnCheckBox('IsPricingRelevant', 'Y')  
    cy.clickOnCheckBox('IsStorageRelevant', 'Y')  
    cy.clickOnCheckBox('IsAttrDocumentRelevant', 'Y')  

    //create AttributeValue1
    cy.get('#tab_M_AttributeValue').click(); 
    cy.pressAddNewButton();
    cy.writeIntoStringField('Name', 'TestAttributeName1',true);
    cy.writeIntoStringField('Value', 'TestAttributeValue1',true);
    cy.pressDoneButton();

    //create AttributeSet1
    cy.visitWindow('256', 'NEW');
    cy.writeIntoStringField('Name', 'TestAttributeSet1');
    cy.selectInListField('MandatoryType', 'Not Mandatory')
   
    //set AttributeValue
    cy.get('#tab_M_AttributeUse').click(); 
    cy.pressAddNewButton();
    cy.selectInListField('M_Attribute_ID', 'TestAttribute1_TestAttribute1');
    cy.pressDoneButton();
  });
});

