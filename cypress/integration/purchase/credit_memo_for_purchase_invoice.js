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

import {getLanguageSpecific} from '../../support/utils/utils';
import {DocumentActionKey, DocumentStatusKey} from '../../support/utils/constants';
import {PurchaseInvoice, PurchaseInvoiceLine} from "../../support/utils/purchase_invoice";

describe('Create a Credit memo for Purchase Invoice', function() {
  const creditMemoVendor = 'Credit Memo (Vendor)';
  const businessPartnerName = 'Test Lieferant 1';
  const productName = 'Convenience Salat 250g';
  const quantity = 200;

  it(`Ensure ${creditMemoVendor} is Number Controlled`, function() {
    cy.visitWindow('135', '1000006');
    cy.setCheckBoxValue('IsDocNoControlled', true);
  });

  it('Prepare Purchase Invoice', function() {
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      new PurchaseInvoice(businessPartnerName, creditMemoVendor)
        .addLine(
          new PurchaseInvoiceLine().setProduct(productName).setQuantity(quantity)
        )
        .setDocumentAction(getLanguageSpecific(miscDictionary, DocumentActionKey.Complete))
        .setDocumentStatus(getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed))
        .apply();
    });
  });

  it('Purchase Invoice is Completed', function() {
    cy.expectDocumentStatus(DocumentStatusKey.Completed);
  });

  it('Purchase Invoice is not paid', function() {
    cy.getCheckboxValue('IsPaid').then(checkBoxValue => {
      cy.log(`IsPaid = ${checkBoxValue}`);
      assert.equal(checkBoxValue, false);
    });
  });

});
