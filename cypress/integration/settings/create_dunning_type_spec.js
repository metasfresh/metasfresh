import { DunningType } from '../../support/utils/dunning_type';
import { BPartner } from '../../support/utils/bpartner';
import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';

describe('create dunning type', function() {
  const timestamp = new Date().getTime();
  const dunningTypeName = `dunning test ${timestamp}`;
  const bPartnerName = `Customer Dunning ${timestamp}`;

  before(function() {
    cy.fixture('settings/dunning_type.json').then(dunningType => {
      Object.assign(new DunningType(), dunningType)
        .setName(dunningTypeName)
        .apply();
    });

    cy.fixture('settings/dunning_bpartner.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(bPartnerName)
        .apply();
    });
  });

  it('operations on BP', function() {
    cy.visitWindow('123');

    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('Name', bPartnerName, false, undefined, true);
    applyFilters();
    cy.get('table tr')
      .eq(1)
      .dblclick();
    cy.selectTab('Customer');
    cy.get('table tbody tr')
      .should('have.length', 1)
      .eq(0)
      .click();
    cy.openAdvancedEdit();
    cy.selectInListField('C_PaymentTerm_ID', 'immediately', true, null, true);
    cy.selectInListField('C_Dunning_ID', dunningTypeName, true, null, true);
    cy.pressDoneButton();
  });
});
