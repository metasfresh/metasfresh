/*
 * #%L
 * metasfresh-e2e
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/// <reference types="Cypress" />

import { appendHumanReadableNow } from '../../support/utils/utils';
import { Builder } from '../../support/utils/builder';
import { PackingMaterial } from '../../support/utils/packing_material';
import { EmptiesReturn } from '../../page_objects/empties_returns';
import { DocumentStatusKey } from '../../support/utils/constants';

describe('Reverse Empties Return', function() {
  let businessPartnerName;
  let productQuantity;
  let documentType;

  // priceList
  let priceSystemName;
  let priceListName;
  let priceListVersionName;

  // product
  let productCategory;
  let productName1;
  let productName2;
  let productName3;
  let productList;

  // test
  let reversalDocumentCounterpartNumber;
  let originalDocumentNumber;

  it('Read the fixture', function() {
    cy.fixture('empties/reverse_empties_return.json').then(f => {
      businessPartnerName = f['businessPartnerName'];
      productQuantity = f['productQuantity'];
      documentType = f['documentType'];

      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

      productCategory = appendHumanReadableNow(f['productCategory']);
      productName1 = appendHumanReadableNow(f['productName1']);
      productName2 = appendHumanReadableNow(f['productName2']);
      productName3 = appendHumanReadableNow(f['productName3']);
      productList = [productName1, productName2, productName3];
    });
  });

  describe('Create Packing Materials', function() {
    it('Create Price and Products', function() {
      Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);

      Builder.createBasicProductEntities(productCategory, productCategory, priceListName, productName1, productName1);

      Builder.createProductWithPriceUsingExistingCategory(priceListName, productName2, productName2, null, productCategory);

      Builder.createProductWithPriceUsingExistingCategory(priceListName, productName3, productName3, null, productCategory);
    });

    it('Add Products to Packing Materials', function() {
      // eslint-disable-next-line
      new PackingMaterial().setName(productName1).setProduct(productName1).apply();
      // eslint-disable-next-line
      new PackingMaterial().setName(productName2).setProduct(productName2).apply();
      // eslint-disable-next-line
      new PackingMaterial().setName(productName3).setProduct(productName3).apply();
    });
  });

  describe('Create Positive Empties Return with 3 products', function() {
    createEmptiesReturn();

    it('Save values needed for the next step', function() {
      cy.getStringFieldValue('DocumentNo').then(documentNumber => {
        originalDocumentNumber = documentNumber;
      });
    });
  });

  describe('Reverse the Empties Return and check', function() {
    it('Reverse the Empties Return', function() {
      cy.reverseDocument();
    });

    it('Get reversed document counterpart', function() {
      cy.openAdvancedEdit();
      cy.getStringFieldValue('Reversal_ID', true).then(function(str) {
        reversalDocumentCounterpartNumber = str.split('_')[0];
      });
    });

    it('Open reversed document counterpart', function() {
      cy.visitWindow(EmptiesReturn.windowId);
      // eslint-disable-next-line
      EmptiesReturn
        .getRows()
        .contains(reversalDocumentCounterpartNumber)
        .dblclick();
    });

    it('Check document data', function() {
      cy.getStringFieldValue('C_BPartner_ID').should('contain', businessPartnerName);
      cy.getStringFieldValue('C_DocType_ID').should('equals', documentType);
      cy.expectDocumentStatus(DocumentStatusKey.Reversed);
      cy.getTextFieldValue('Description').should('contain', originalDocumentNumber);

      cy.openAdvancedEdit();
      cy.getStringFieldValue('Reversal_ID', true).should('contains', originalDocumentNumber);
      cy.pressDoneButton();
    });

    it('Check document line data', function() {
      productList.forEach((productName, index) => {
        cy.selectTab('M_InOutLine');
        cy.selectNthRow(index);
        cy.openAdvancedEdit();
        cy.getStringFieldValue('M_Product_ID', true).should('contain', productName);
        const lineValue = -1 * (productQuantity + index);
        cy.getStringFieldValue('MovementQty', true).should('equals', lineValue.toString(10));
        cy.getStringFieldValue('QtyEntered', true).should('equals', lineValue.toString(10));
        cy.pressDoneButton();
      });
    });
  });

  function createEmptiesReturn() {
    it('Open Material Receipt Candidates and execute action "Empties Return"', function() {
      cy.visitWindow('540196');
      cy.executeHeaderAction('WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsToVendor');
    });
    it('Create Empties Return', function() {
      cy.selectInListField('C_DocType_ID', documentType);
      cy.writeIntoLookupListField('C_BPartner_ID', businessPartnerName, businessPartnerName);
      addLines();
      cy.completeDocument();
    });
  }

  function addLines() {
    productList.forEach((productName, index) => {
      cy.selectTab('M_InOutLine');
      cy.pressBatchEntryButton();
      cy.writeIntoLookupListField('M_HU_PackingMaterial_ID', productName, productName);

      const qty = productQuantity + index;
      if (productQuantity > 0) {
        cy.writeIntoStringField('Qty', qty); //.type('{enter}');
        cy.closeBatchEntry();
      } else {
        cy.writeIntoStringField('Qty', -1 * qty); //.type('{enter}'); // first write the positive qty (frontend bug workaround)
        writeQtyInAdvancedEdit(index);
      }
    });
  }

  function writeQtyInAdvancedEdit(rowNumber) {
    // select nth line
    cy.selectTab('M_InOutLine');
    cy.selectNthRow(rowNumber);

    // do ya thing
    cy.openAdvancedEdit();
    cy.writeIntoStringField('MovementQty', productQuantity);
    cy.writeIntoStringField('QtyEntered', productQuantity);
    cy.pressDoneButton(100);
  }
});
