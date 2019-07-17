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

describe('Create Empties Receive', function() {
  const businessPartnerName = 'Test Lieferant 1';
  const productName = 'EUR-Tauschpalette Holz_P001503';
  const originalQuantity = 222;
  const documentType = 'Leergutrücknahme';

  it('Open Material Receipt Candidates', function() {
    cy.visitWindow('540196');
  });

  it('Execute action "Empties Receive"', function() {
    cy.executeHeaderAction('headerAction_WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsFromCustomer');
    cy.selectInListField('form-group row  form-field-C_DocType_ID', documentType);
    cy.writeIntoStringField('lookup_C_BPartner_ID', businessPartnerName);
    cy.selectTab('M_InOutLine');
    cy.pressBatchEntryButton();
    cy.writeIntoLookupListField('M_HU_PackingMaterial_ID', productName, productName);
    cy.writeIntoStringField('Qty', originalQuantity, false, null, true).type('{enter}');
  });
});
