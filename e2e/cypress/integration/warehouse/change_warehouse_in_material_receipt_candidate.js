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

import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import { purchaseOrders } from '../../page_objects/purchase_orders';

// task: https://github.com/metasfresh/metasfresh-e2e/issues/153

describe('Change warehouse in material receipt candidate #153', function () {
  let warehouse1;
  let warehouse2;
  let businessPartnerName;
  let productQuantity;
  let productName; // the product must have a packing item, else the test will fail
  let huItem;

  it('Read the fixture', function () {
    cy.fixture('warehouse/change_warehouse_in_material_receipt_candidate.json').then((f) => {
      warehouse1 = f['warehouse1'];
      warehouse2 = f['warehouse2'];
      businessPartnerName = f['businessPartnerName'];
      productQuantity = f['productQuantity'];
      productName = f['productName'];
      huItem = f['huItem'];
    });
  });

  // test
  let purchaseOrderRecordId;

  it('Create Purchase Order', function () {
    new PurchaseOrder().setBPartner(businessPartnerName).addLine(new PurchaseOrderLine().setProduct(productName).setHU(huItem).setQuantity(productQuantity)).apply();

    cy.completeDocument();
  });

  it('Save values needed for the next steps', function () {
    cy.getCurrentWindowRecordId().then((recordId) => {
      purchaseOrderRecordId = recordId;
    });
  });

  it('Visit referenced Material Receipt Candidates', function () {
    cy.openReferencedDocuments('M_ReceiptSchedule');

    cy.selectNthRow(0).dblclick();
  });

  it('Check Warehouse', function () {
    cy.getStringFieldValue('M_Warehouse_ID').should('contain', warehouse1);
  });

  it('Change the warehouse with Warehouse Override', function () {
    cy.openAdvancedEdit();
    cy.selectInListField('M_Warehouse_Override_ID', warehouse2, true);
    cy.pressDoneButton();
  });

  it('Warehouse should not be changed', function () {
    cy.getStringFieldValue('M_Warehouse_ID').should('contain', warehouse1);
  });

  it('Go back to the filtered view of Material Receipt Candidates and create the Material Receipt', function () {
    cy.go('back');
    cy.selectNthRow(0).click();
    cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults');
    cy.selectNthRow(0, true);
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', true, false);
    cy.pressDoneButton();
  });

  it('Go to the referenced Material Receipt', function () {
    cy.visitWindow(purchaseOrders.windowId, purchaseOrderRecordId);

    cy.openReferencedDocuments('M_ReceiptSchedule');
    cy.get('.spinner').should('not.exist');
    cy.selectNthRow(0).dblclick();
  });

  // TODO: check when the BE Bug is fixed and bring back this test into the suite
  // it('Warehouse should be 2nd warehouse', function () {
  //   cy.getStringFieldValue('M_Warehouse_ID').should('contain', warehouse2);
  // });
});
