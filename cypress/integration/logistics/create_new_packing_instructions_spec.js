import { BPartner } from '../../support/utils/bpartner';
import { Product, ProductCategory } from '../../support/utils/product';
import { appendHumanReadableNow } from '../../support/utils/utils';

let customerName;
let packingMaterialInstruction;
let productName;
let packingMaterial;
let productCategoryName;

it('Read fixture and prepare the names', function() {
  cy.fixture('logistics/create_new_packing_instructions_spec.json').then(f => {
    customerName = appendHumanReadableNow(f['customerName']);
    packingMaterialInstruction = appendHumanReadableNow(f['packingMaterialInstruction']);
    productName = appendHumanReadableNow(f['productName']);
    packingMaterial = appendHumanReadableNow(f['packingMaterial']);
    productCategoryName = appendHumanReadableNow(f['productCategoryName']);
  });
});
describe('create new packing instructions ', function() {
  it('Set up product and partner', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductCategory(productCategoryName)
        .apply();
    });

    cy.visitWindow('540192', 'NEW');
    cy.writeIntoStringField('Name', packingMaterial);
    cy.selectInListField('M_Product_ID', productName);

    cy.fixture('settings/dunning_bpartner.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customerName }).setName(customerName).apply();
    });
  });

  it('creating packing instructions', function() {
    cy.visitWindow('540343', 'NEW');
    cy.writeIntoStringField('Name', packingMaterialInstruction);
  });

  it('creating packing instructions version', function() {
    cy.visitWindow('540344', 'NEW');
    cy.selectInListField('M_HU_PI_ID', packingMaterialInstruction);
    cy.writeIntoStringField('Name', packingMaterialInstruction);
    cy.selectInListField('HU_UnitType', 'Load/Logistique Unit');
  });

  it('add new in modal', function() {
    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Packmittel', true);
    cy.selectInListField('M_HU_PackingMaterial_ID', packingMaterial, true);
    cy.pressDoneButton();

    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Unter-Packvorschrift', true);
    cy.selectInListField('Included_HU_PI_ID', 'IFCO 6416', true);
    cy.writeIntoStringField('Qty', '10', true);
    cy.writeIntoLookupListField('C_BPartner_ID', customerName, customerName, true, true);
    cy.pressDoneButton();
  });
});
