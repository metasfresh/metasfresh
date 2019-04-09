describe('Create Attribute Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/13', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
  });

  it('Create a new Attribute Set and Attributes', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const attributeName = `ListAttributeName ${timestamp}`;
    const attributeValue = `ListAttributeValue ${timestamp}`;
    const attributeSetName = `TestAttributeSet ${timestamp}`;

    //create Attribute1
    cy.visitWindow('260', 'NEW');
    cy.writeIntoStringField('Name', attributeName);
    cy.writeIntoStringField('Description', 'TestAttribute1');
    cy.clearField('Value');
    cy.writeIntoStringField('Value', attributeValue);

    cy.selectInListField('AttributeValueType', 'List');
    cy.clickOnCheckBox('IsInstanceAttribute', 'Y');
    cy.clickOnCheckBox('IsPricingRelevant', 'Y');
    cy.clickOnCheckBox('IsStorageRelevant', 'Y');
    cy.clickOnCheckBox('IsAttrDocumentRelevant', 'Y');

    //create AttributeValue1
    cy.get('#tab_M_AttributeValue').click();
    cy.pressAddNewButton();
    cy.writeIntoStringField('Name', 'TestAttributeName1', true);
    cy.writeIntoStringField('Value', 'TestAttributeValue1', true);
    cy.pressDoneButton();

    //create AttributeSet1
    cy.visitWindow('256', 'NEW');
    cy.writeIntoStringField('Name', attributeSetName);
    cy.selectInListField('MandatoryType', 'Not Mandatory');

    //set AttributeValue
    cy.get('#tab_M_AttributeUse').click();
    cy.pressAddNewButton();
    cy.selectInListField('M_Attribute_ID', `${attributeValue}_${attributeName}`);
    cy.pressDoneButton();
  });
});
