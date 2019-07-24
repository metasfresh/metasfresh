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

import { humanReadableNow } from '../../support/utils/utils';
import { Builder } from '../../support/utils/builder';
import { PackingMaterial } from '../../support/utils/packing_material';
import { EmptiesReturn } from '../../page_objects/empties_returns';
import { DocumentStatusKey } from '../../support/utils/constants';

describe('Reverse Empties Return', function() {
  const businessPartnerName = 'Test Lieferant 1';
  const productQuantity = 222;
  const documentType = 'Leergutausgabe';

  // priceList
  const date = humanReadableNow();
  const priceSystemName = `PriceSystem ${date}`;
  const priceListName = `PriceList ${date}`;
  const priceListVersionName = `PriceListVersion ${date}`;

  // product
  const productCategory1 = `ProductCategory ${date}`;
  const productName1 = `Product1 ${date}`;
  const productName2 = `Product2 ${date}`;
  const productName3 = `Product3 ${date}`;
  const productType = 'Item';
  const productList = [productName1, productName2, productName3];

  // test
  let reversalDocumentCounterpartNumber;
  let originalDocumentNumber;

  describe('Create Packing Materials', function() {
    it('Create Price and Products', function() {
      Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);

      Builder.createBasicProductEntities(
        productCategory1,
        productCategory1,
        priceListName,
        productName1,
        productName1,
        productType
      );

      Builder.createBasicProductEntities(
        productCategory1,
        productCategory1,
        priceListName,
        productName2,
        productName2,
        productType
      );

      Builder.createBasicProductEntities(
        productCategory1,
        productCategory1,
        priceListName,
        productName3,
        productName3,
        productType
      );
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
    createEmptiesReturn(documentType, businessPartnerName, productList, productQuantity);

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
});

function createEmptiesReturn(documentType, businessPartnerName, productNames, productQuantity) {
  it('Open Material Receipt Candidates and execute action "Empties Return"', function() {
    cy.visitWindow('540196');
    cy.executeHeaderAction('WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsToVendor');
  });

  it('Create Empties Return', function() {
    cy.selectInListField('C_DocType_ID', documentType);
    cy.writeIntoLookupListField('C_BPartner_ID', businessPartnerName, businessPartnerName);
    addLines(productNames, productQuantity);
    cy.completeDocument();
  });
}

function addLines(productNames, productQuantity) {
  productNames.forEach((productName, index) => {
    cy.selectTab('M_InOutLine');
    cy.pressBatchEntryButton();
    cy.writeIntoLookupListField('M_HU_PackingMaterial_ID', productName, productName);

    if (productQuantity > 0) {
      cy.writeIntoStringField('Qty', productQuantity + index); //.type('{enter}');
      cy.closeBatchEntry();
    } else {
      cy.writeIntoStringField('Qty', -1 * productQuantity); //.type('{enter}'); // first write the positive qty (frontend bug workaround)
      writeQtyInAdvancedEdit(productQuantity + index, productName, index);
    }
  });
}

function writeQtyInAdvancedEdit(productQuantity, rowNumber) {
  // select nth line
  cy.selectTab('M_InOutLine');
  cy.selectNthRow(rowNumber);

  // do ya thing
  cy.openAdvancedEdit();
  cy.writeIntoStringField('MovementQty', productQuantity);
  cy.writeIntoStringField('QtyEntered', productQuantity);
  cy.pressDoneButton(100);
}
