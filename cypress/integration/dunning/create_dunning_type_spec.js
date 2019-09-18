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

import { DunningType } from '../../support/utils/dunning_type';
import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('create dunning type', function() {
  let dunningTypeName;
  let bPartnerName;
  let bpartnerID;

  it('Read the fixture', function() {
    cy.fixture('dunning/create_dunning_type_spec.json').then(f => {
      dunningTypeName = appendHumanReadableNow(f['dunningTypeName']);
      bPartnerName = appendHumanReadableNow(f['bPartnerName']);
    });
  });

  it('Create dunning type and bpartner', function() {
    cy.fixture('settings/dunning_type.json').then(dunningType => {
      Object.assign(new DunningType(), dunningType)
        .setName(dunningTypeName)
        .apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: bPartnerName })
        .setDunning(dunningTypeName)
        .clearLocations()
        .clearContacts();

      bpartner.apply().then(bpartner => {
        bpartnerID = bpartner.id;
      });
    });
  });

  it('operations on BP', function() {
    cy.visitWindow('123', bpartnerID);
    cy.selectTab('Customer');
    cy.get('.table tbody td').should('exist');

    cy.get('.table tbody').then(el => {
      expect(el[0].innerHTML).to.include(dunningTypeName);
    });
  });
});
