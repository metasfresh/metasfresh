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

import { humanReadableNow } from '../../support/utils/utils';
import { Builder } from '../../support/utils/builder';
import { PackingMaterial } from '../../support/utils/packing_material';

describe('Reactivate and change Empties Return', function() {
  const businessPartnerName = 'Test Lieferant 1';
  const initialProductQuantity = 222;
  const newProductQuantity = 654;
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

  describe('Create Packing Material', function() {
    it('Create Price and Product', function() {
      Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);

      Builder.createBasicProductEntities(
        productCategory1,
        productCategory1,
        priceListName,
        productName1,
        productName1,
        productType
      );

      Builder.createProductWithPriceUsingExistingCategory(
        priceListName,
        productName2,
        productName2,
        productType,
        productCategory1
      );

      Builder.createProductWithPriceUsingExistingCategory(
        priceListName,
        productName3,
        productName3,
        productType,
        productCategory1
      );
    });

    it('Add Product to Packing Material', function() {
      // eslint-disable-next-line
      new PackingMaterial().setName(productName1).setProduct(productName1).apply();
      // eslint-disable-next-line
      new PackingMaterial().setName(productName2).setProduct(productName2).apply();
      // eslint-disable-next-line
      new PackingMaterial().setName(productName3).setProduct(productName3).apply();
    });
  });

  describe('Create Positive Empties Return with 1 product', function() {
    createEmptiesReturn(documentType, businessPartnerName, [productName2], initialProductQuantity);
  });

  describe('Reactivate and edit Empties Return with 1 product', function() {
    it('Edit existing line', function() {
      cy.reactivateDocument();
      writeQtyInAdvancedEdit(newProductQuantity, 0);
    });

    it('Add new line', function() {
      const productNames = [productName3];
      addLines(productNames, initialProductQuantity);
      cy.completeDocument();
    });
  });

  describe('Create Positive Empties Return with 2 products', function() {
    createEmptiesReturn(documentType, businessPartnerName, [productName1, productName2], initialProductQuantity);
  });

  describe('Reactivate and edit Empties Return with 2 products', function() {
    it('Edit existing lines', function() {
      cy.reactivateDocument();
      writeQtyInAdvancedEdit(newProductQuantity, 0);
      writeQtyInAdvancedEdit(newProductQuantity, 1);
    });

    it('Add new line', function() {
      const productNames = [productName3];
      addLines(productNames, initialProductQuantity);
      cy.completeDocument();
    });
  });

  describe('Cannot Create Negative Empties Return', function() {
    it('Cannot Create Negative Empties Return', function() {
      // eslint-disable-next-line
      cy.log(`cannot test due to cypress bug: https://github.com/cypress-io/cypress/issues/2173#issuecomment-512776378 (cypress cannot write minus ('-') for negative numbers)`);
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
      cy.writeIntoStringField('Qty', productQuantity + index, false, null, true); //.type('{enter}');
      cy.closeBatchEntry();
    } else {
      cy.writeIntoStringField('Qty', -1 * productQuantity, false, null, true); //.type('{enter}'); // first write the positive qty (frontend bug workaround)
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
  cy.writeIntoStringField('MovementQty', productQuantity, true, null, true);
  cy.writeIntoStringField('QtyEntered', productQuantity, true, null, true);
  cy.pressDoneButton(100);
}
