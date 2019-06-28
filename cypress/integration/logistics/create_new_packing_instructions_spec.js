import { BPartner } from '../../support/utils/bpartner';
import { Product } from '../../support/utils/product';

describe('create new packing instructions ', function() {
  const timestamp = new Date().getTime();
  const customerName = `TestBPartnerPackingMaterial ${timestamp}`;
  const packingMaterialInstruction = `TestPackingMaterialInstruction ${timestamp}`;
  const productName = `TestProductPackingMaterial ${timestamp}`;
  const packingMaterial = `TestPackingMaterial ${timestamp}`;

  before(function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductCategory('24_Gebinde')
        .apply();
    });

    cy.visitWindow('540192');
    cy.clickHeaderNav(Cypress.messages.window.new.caption);
    cy.writeIntoStringField('Name', packingMaterial);
    cy.selectInListField('M_Product_ID', productName);

    cy.fixture('settings/dunning_bpartner.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(customerName)
        .apply();
    });
  });

  it('creating packing instructions', function() {
    cy.visitWindow('540343');
    cy.clickHeaderNav(Cypress.messages.window.new.caption);
    cy.writeIntoStringField('Name', packingMaterialInstruction);
  });

  it('creating packing instructions version', function() {
    cy.visitWindow('540344');
    cy.clickHeaderNav(Cypress.messages.window.new.caption);
    cy.selectInListField('M_HU_PI_ID', packingMaterialInstruction);
    cy.writeIntoStringField('Name', packingMaterialInstruction);
    cy.selectInListField('HU_UnitType', 'Load/Logistique Unit');
  });

  it('add new in modal', function() {
    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Packmittel');
    cy.selectInListField('M_HU_PackingMaterial_ID', packingMaterial);
    cy.pressDoneButton();

    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Unter-Packvorschrift');
    cy.selectInListField('Included_HU_PI_ID', 'IFCO 6416');
    cy.writeIntoStringField('Qty', '10');
    cy.writeIntoLookupListField('C_BPartner_ID', customerName, customerName);
    cy.pressDoneButton();
  });
});
