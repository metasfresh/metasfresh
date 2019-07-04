import { DunningType } from '../../support/utils/dunning_type';
import { BPartner } from '../../support/utils/bpartner';

describe('create dunning type', function() {
  const timestamp = new Date().getTime();
  const dunningTypeName = `dunning test ${timestamp}`;
  const bPartnerName = `Customer Dunning ${timestamp}`;
  let bpartnerID = null;

  before(function() {
    cy.fixture('settings/dunning_type.json').then(dunningType => {
      Object.assign(new DunningType(), dunningType)
        .setName(dunningTypeName)
        .apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: bPartnerName })
        .setCustomer(true)
        .setDunning(dunningTypeName)
        .clearLocations()
        .clearContacts()
        .setBank(undefined);

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
