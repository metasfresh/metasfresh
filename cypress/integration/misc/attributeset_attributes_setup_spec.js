import { Attribute, AttributeSet } from '../../support/utils/attribute';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create Attribute Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/13', function() {
  const date = humanReadableNow();
  const attributeName = `ListAttributeName ${date}`;
  const attributeSetName = `AttributeSet ${date}`;

  it('Create a new Attribute', function() {
    cy.fixture('misc/simple_attribute.json').then(attributeJson => {
      Object.assign(new Attribute(), attributeJson)
        .setName(attributeName)
        .setValue(attributeName)
        .apply();
    });
  });

  it('Create AttributeSet', function() {
    // eslint-disable-next-line
    new AttributeSet(attributeSetName)
      .addAttribute(`${attributeName}`)
      .apply();
  });
});
