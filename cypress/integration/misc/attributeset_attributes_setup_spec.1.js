import { Attribute } from '../../support/utils/attribute';

describe('Create Attribute Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/13', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
  });

  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const attributeName = `ListAttributeName ${timestamp}`;
  const attributeValue = `ListAttributeValue ${timestamp}`;
  //const attributeSetName = `TestAttributeSet ${timestamp}`;

  it('Create a new Attribute Set and Attributes', function() {
    cy.fixture('misc/simple_attribute.json').then(attributeJson => {
      Object.assign(new Attribute(), attributeJson)
        .setName(attributeName)
        .setValue(attributeValue)
        .apply();
    });
  });
});
