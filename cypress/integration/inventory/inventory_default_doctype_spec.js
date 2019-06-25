import { inventory } from '../../page_objects/inventory';
import { doctypes } from '../../page_objects/doctypes';

import { getLanguageSpecific } from '../../support/utils/utils';

describe('Verify that the default inventory doctype is used on new inventory docs', function() {
  it('Make aggregated-HUs inventory doc-type the default', function() {
    cy.log(`Make sure that C_DocType_ID=${inventory.docTypeInventoryWithSingleHU} is *not* default`);
    doctypes.visit(inventory.docTypeInventoryWithSingleHU);
    cy.getCheckboxValue('IsDefault').then(isDefaultValue => {
      cy.log(`isDefaultValue=${isDefaultValue}`);
      if (isDefaultValue) {
        cy.clickOnCheckBox('IsDefault');
      }
    });
    cy.log(`Make sure that C_DocType_ID=${inventory.docTypeInventoryWithMultipleHUs} is default`);
    doctypes.visit(inventory.docTypeInventoryWithMultipleHUs);
    cy.getCheckboxValue('IsDefault').then(isDefaultValue => {
      cy.log(`isDefaultValue=${isDefaultValue}`);
      if (!isDefaultValue) {
        cy.clickOnCheckBox('IsDefault');
      }
    });
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
    cy.log(`Make sure that C_DocType_ID=${inventory.docTypeInventoryWithSingleHU} is default`);
    doctypes.visit(inventory.docTypeInventoryWithSingleHU);
    cy.getCheckboxValue('IsDefault').then(isDefaultValue => {
      cy.log(`isDefaultValue=${isDefaultValue}`);
      if (!isDefaultValue) {
        cy.clickOnCheckBox('IsDefault');
      }
    });
    cy.log(`Make sure that C_DocType_ID=${inventory.docTypeInventoryWithMultipleHUs} is *not* default`);
    doctypes.visit(inventory.docTypeInventoryWithMultipleHUs);
    cy.getCheckboxValue('IsDefault').then(isDefaultValue => {
      cy.log(`isDefaultValue=${isDefaultValue}`);
      if (isDefaultValue) {
        cy.clickOnCheckBox('IsDefault');
      }
    });
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
