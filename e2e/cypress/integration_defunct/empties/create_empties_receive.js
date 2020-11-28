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

import { appendHumanReadableNow } from '../../support/utils/utils';
import { Builder } from '../../support/utils/builder';
import { PackingMaterial } from '../../support/utils/packing_material';

describe('Create Empties Receive', function() {
  let businessPartnerName;
  let productQuantity;
  let documentType;

  let priceSystemName;
  let priceListName;
  let priceListVersionName;

  let productCategory;
  let productName;

  it('Read the fixture', function() {
    cy.fixture('empties/create_empties_receive.json').then(f => {
      businessPartnerName = f['businessPartnerName'];
      productQuantity = f['productQuantity'];
      documentType = f['documentType'];

      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

      productCategory = appendHumanReadableNow(f['productCategory']);
      productName = appendHumanReadableNow(f['productName']);
    });
  });

  describe('Create Packing Material', function() {
    it('Create Price and Product', function() {
      Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);

      Builder.createBasicProductEntities(productCategory, productCategory, priceListName, productName, productName);
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
      cy.completeDocument();
    });
  });
});
