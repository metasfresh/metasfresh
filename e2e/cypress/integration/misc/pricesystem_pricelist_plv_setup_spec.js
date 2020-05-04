import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create Pricing Masterdata', function() {
  let priceSystemName;
  let priceListName;
  let currency;
  let priceListValueName;

  it('Read the fixture', function() {
    cy.fixture('misc/pricesystem_pricelist_plv_setup_spec.json').then(f => {
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      currency = f['currency'];
      priceListValueName = appendHumanReadableNow(f['priceListValueName']);
    });
  });

  it('Create a new Pricesystem and Pricelist with PLV', function() {
    //Pricesystem
    cy.visitWindow('540320', 'NEW');
    cy.writeIntoStringField('Name', priceSystemName);
    cy.clearField('Value');
    cy.writeIntoStringField('Value', priceSystemName);

    //Pricelist
    cy.visitWindow('540321', 'NEW');
    cy.writeIntoStringField('Name', priceListName);
    cy.writeIntoLookupListField('M_PricingSystem_ID', priceSystemName, priceSystemName);
    cy.selectInListField('C_Currency_ID', currency);

    //PLV
    cy.pressAddNewButton();
    cy.pressDoneButton();
  });
});
