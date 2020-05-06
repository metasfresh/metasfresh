import { inventory } from '../../page_objects/inventory';
import { doctypes } from '../../page_objects/doctypes';

import { getLanguageSpecific } from '../../support/utils/utils';

describe('Verify that the default inventory doctype is used on new inventory docs', function() {
  it('Make aggregated-HUs inventory doc-type the default', function() {
    cy.log(`Make sure that disposal with C_DocType_ID=${inventory.docTypeDisposal} is *not* default`);
    doctypes.visit(inventory.docTypeDisposal);
    cy.setCheckBoxValue('IsDefault', false);

    cy.log(`Make sure that single-HU C_DocType_ID=${inventory.docTypeInventoryWithSingleHU} is *not* default`);
    doctypes.visit(inventory.docTypeInventoryWithSingleHU);
    cy.setCheckBoxValue('IsDefault', false);

    cy.log(`Make sure that aggregated-HU C_DocType_ID=${inventory.docTypeInventoryWithMultipleHUs} is default`);
    doctypes.visit(inventory.docTypeInventoryWithMultipleHUs);
    cy.setCheckBoxValue('IsDefault', true);

    cy.screenshot();
  });

  it('Verify that a new inventory doc is created with aggregated-HUs inventory doc-type', function() {
    cy.visitWindow(inventory.windowId, 'NEW', 'newInventoryRecord');

    cy.fixture('inventory/inventory.json').then(inventoryJson => {
      cy.getStringFieldValue('C_DocType_ID').then(docTypeName => {
        expect(docTypeName).to.eq(getLanguageSpecific(inventoryJson, 'aggregatedHUsInventoryDocTypeName'));
      });
    });
    cy.screenshot();
  });

  it('Make single-HU inventory doc-type the default', function() {
    cy.log(`Make sure that disposal with C_DocType_ID=${inventory.docTypeDisposal} is *not* default`);
    doctypes.visit(inventory.docTypeDisposal);
    cy.setCheckBoxValue('IsDefault', false);

    cy.log(`Make sure that single-HU with C_DocType_ID=${inventory.docTypeInventoryWithSingleHU} is default`);
    doctypes.visit(inventory.docTypeInventoryWithSingleHU);
    cy.setCheckBoxValue('IsDefault', true);

    cy.log(`Make sure that aggregated-HU C_DocType_ID=${inventory.docTypeInventoryWithMultipleHUs} is *not* default`);
    doctypes.visit(inventory.docTypeInventoryWithMultipleHUs);
    cy.setCheckBoxValue('IsDefault', false);

    cy.screenshot();
  });

  it('Verify that a new inventory doc is created with single-HU inventory doc-type', function() {
    cy.visitWindow(inventory.windowId, 'NEW', 'newInventoryRecord');

    cy.fixture('inventory/inventory.json').then(inventoryJson => {
      cy.getStringFieldValue('C_DocType_ID').then(docTypeName => {
        expect(docTypeName).to.eq(getLanguageSpecific(inventoryJson, 'singleHUInventoryDocTypeName'));
      });
    });
    cy.screenshot();
  });
});
