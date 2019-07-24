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
import { PurchaseOrder, PurchaseOrderLine } from "../../support/utils/purchase_order";
import { purchaseOrders } from "../../page_objects/purchase_orders";

describe('Change warehouse in material receipt candidate #153', function() {
  const warehouse1 = 'Hauptlager';
  const warehouse2 = 'Lager für Streckengeschäft';

  //
  const businessPartnerName = 'Test Lieferant 1';
  const productQuantity = 222;
  const productName = 'Convenience Salat 250g';
  // const documentType = 'Leergutausgabe';
  //
  // // priceList
  // const date = humanReadableNow();
  // const priceSystemName = `PriceSystem ${date}`;
  // const priceListName = `PriceList ${date}`;
  // const priceListVersionName = `PriceListVersion ${date}`;
  //
  // // product
  // const productCategory1 = `ProductCategory ${date}`;
  // const productName1 = `Product1 ${date}`;
  // const productName2 = `Product2 ${date}`;
  // const productName3 = `Product3 ${date}`;
  // const productType = 'Item';
  // const productList = [productName1, productName2, productName3];
  //
  // // test
  let purchaseOrderRecordId;

  it('Create Purchase Order', function() {
    new PurchaseOrder()
      .setBPartner(businessPartnerName)
      .addLine(new PurchaseOrderLine().setProduct(productName).setQuantity(productQuantity))
      .apply();

    cy.completeDocument();
  });

  it('Save values needed for the next steps', function() {
    cy.getCurrentWindowRecordId().then(recordId => {
      purchaseOrderRecordId = recordId;
    });
  });


    it('Visit referenced Material Receipt Candidates', function() {
    cy.waitUntilProcessIsFinished();
    cy.openReferencedDocuments('M_ReceiptSchedule');

      cy.selectNthRow(0).dblclick();
    // const tableRows = '.table-flex-wrapper-row';
    // const rowSelector = 'tbody tr';
    // cy.get(tableRows)
    //   .find(rowSelector)
    //   .eq(0)
    //   .dblclick();
  });

  it('Check Warehouse', function() {
    cy.getStringFieldValue('M_Warehouse_ID').should('contain', warehouse1);
  });

  it('Change the warehouse with Warehouse Override', function() {
    cy.openAdvancedEdit()
    cy.writeIntoStringField('M_Warehouse_Override_ID', warehouse2, true);
    cy.pressDoneButton();
  });

  it('Warehouse should not be changed', function() {
    cy.getStringFieldValue('M_Warehouse_ID').should('contain', warehouse1);
  });

  it('Go back to the filtered view of Material Receipt Candidates and create the Material Receipt', function() {
    cy.go('back');
    cy.selectNthRow(0);
    // cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults', true);
    // cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', true);
  });

  // it('Go to the referenced Material Receipt', function() {
  //   cy.visitWindow(purchaseOrders.windowId, purchaseOrderRecordId);
  //
  //   cy.waitUntilProcessIsFinished();
  //   cy.openReferencedDocuments('M_ReceiptSchedule');
  //
  //   cy.selectNthRow(0).dblclick();
  // });


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
