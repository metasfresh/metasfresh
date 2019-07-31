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

import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';
import { DocumentActionKey, DocumentStatusKey } from '../../support/utils/constants';
import { Builder } from '../../support/utils/builder';
import { PackingMaterial } from '../../support/utils/packing_material';

describe('Create Empties Receive', function() {
  // empties receive
  const businessPartnerName = 'Test Lieferant 1';
  const productQuantity = 222;
  const documentType = 'Leergutrücknahme';

  // priceList
  const date = humanReadableNow();
  const priceSystemName = `PriceSystem ${date}`;
  const priceListName = `PriceList ${date}`;
  const priceListVersionName = `PriceListVersion ${date}`;

  // product
  const productCategory = `ProductCategory ${date}`;
  const productName = `Product ${date}`;
  const productType = 'Item';

  describe('Create Packing Material', function() {
    it('Create Price and Product', function() {
      Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);

      Builder.createBasicProductEntities(
        productCategory,
        productCategory,
        priceListName,
        productName,
        productName,
        productType
      );
    });

    it('Add Product to Packing Material', function() {
      new PackingMaterial()
        .setName(productName)
        .setProduct(productName)
        .apply();
    });
  });

  describe('Create Empties Receive', function() {
    it('Open Material Receipt Candidates', function() {
      cy.visitWindow('540196');
    });

    it('Execute action "Empties Receive"', function() {
      cy.executeHeaderAction('WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsFromCustomer');
      cy.selectInListField('C_DocType_ID', documentType);
      cy.writeIntoLookupListField('C_BPartner_ID', businessPartnerName, businessPartnerName);
      cy.selectTab('M_InOutLine');
      cy.pressBatchEntryButton();
      cy.writeIntoLookupListField('M_HU_PackingMaterial_ID', productName, productName);
      cy.writeIntoStringField('Qty', productQuantity, false, null, true);
      cy.closeBatchEntry();

      cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
        cy.processDocument(
          getLanguageSpecific(miscDictionary, DocumentActionKey.Complete),
          getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed)
        );
      });
    });
  });
});
