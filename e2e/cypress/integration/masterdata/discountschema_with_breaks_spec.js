import { Product, ProductCategory } from '../../support/utils/product';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('New discount schema Test', function() {
  let discountSchemaName;
  let productCategoryName;
  let discountTypeBreaks;
  let discountSchemaValidFrom = '01/01/2019';
  let productName;
  let serviceProductType;

  let breakValue1;
  let breakValue2;
  let breakDiscount1;
  let breakDiscount2;

  it('Read the fixture', function() {
    cy.fixture('masterdata/discountschema_with_breaks_spec.json').then(f => {
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      productName = appendHumanReadableNow(f['productName']);
      serviceProductType = f['serviceProductType'];
      discountTypeBreaks = f['discountTypeBreaks'];
      discountSchemaValidFrom = f['discountSchemaValidFrom'];

      breakValue1 = f['breakValue1'];
      breakValue2 = f['breakValue2'];
      breakDiscount1 = f['breakDiscount1'];
      breakDiscount2 = f['breakDiscount2'];
    });
  });

  it('Create a product to test with', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductType(serviceProductType)
        .apply();
    });
  });

  it('Create a discount schema record', function() {
    cy.visitWindow('233', 'NEW');
    cy.writeIntoStringField('Name', discountSchemaName, false);
    cy.selectInListField('DiscountType', discountTypeBreaks);
    cy.writeIntoStringField('ValidFrom', discountSchemaValidFrom, false, null, true);
    cy.writeIntoStringField('Description', `Description for ${discountSchemaName}`, false);

    addBreakRecord(productName, breakValue1, breakDiscount1);
    addBreakRecord(productName, breakValue2, breakDiscount2);
  });
});

function addBreakRecord(productValue, breakValue, breakDiscount) {
  cy.pressAddNewButton();

  cy.writeIntoLookupListField('M_Product_ID', productValue, productValue, false, true /*modal*/);
  cy.clearField('BreakValue', true /*modal*/).writeIntoStringField('BreakValue', breakValue, true /*modal*/);
  cy.clearField('BreakDiscount', true /*modal*/).writeIntoStringField('BreakDiscount', breakDiscount, true /*modal*/);

  cy.resetListValue('PriceBase', true /*modal*/);

  cy.pressDoneButton();
}
