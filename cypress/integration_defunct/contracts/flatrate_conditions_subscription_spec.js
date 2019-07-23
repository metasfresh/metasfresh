/// <reference types="Cypress" />

import { createAndCompleteTransition } from '../../support/utils/contract_static';
import { BPartner, BPartnerLocation } from '../../support/utils/bpartner';
import { DiscountSchema, DiscountBreak } from '../../support/utils/discountschema';

describe('Create subscription flatrate conditions for three 1-year-periods', function() {
  before(function() {
    // login before each test and open the flatrate conditions window
    cy.loginByForm();
  });

  it('Create subscription flatrate conditions for three 1-year-periods', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering

    // we start with the conditions for the 3rd/last contract period
    const year3TransitionName = `Transition (S) year 3-dontExtend ${timestamp}`;
    const year3ConditionsName = `Conditions (S) year 3 ${timestamp}`;
    createAndCompleteTransition(year3TransitionName, null, null);
    createAndCompleteConditions(year3ConditionsName, year3TransitionName);
    cy.screenshot();

    const year2To3TransitionName = `Transition (S) year 2-3 ${timestamp}`;
    const year2ConditionsName = `Conditions year 2 ${timestamp}`;
    createAndCompleteTransition(year2To3TransitionName, 'Extend contract for all periods', year3ConditionsName);
    createAndCompleteConditions(year2ConditionsName, year2To3TransitionName);
    cy.screenshot();

    const year1To2TransitionName = `Transition (S) year 1-2 ${timestamp}`;
    const year1ConditionsName = `Conditions (S) year 1 ${timestamp}`;
    createAndCompleteTransition(year1To2TransitionName, 'Extend contract for all periods', year2ConditionsName);
    createAndCompleteConditions(year1ConditionsName, year1To2TransitionName);
    cy.screenshot();

    const discountSchemaName = `DiscountSchema (S) ${timestamp}`;
    new DiscountSchema(discountSchemaName)
      .addDiscountBreak(new DiscountBreak().setBreakValue(0).setBreakDiscount(0))
      .apply();
    cy.screenshot();

    const bPartnerName = `Customer (S) ${timestamp}`;
    new BPartner.builder(bPartnerName)
      .setVendor(true)
      .setVendorDiscountSchema(discountSchemaName)
      .addLocation(
        new BPartnerLocation.builder('Address1')
          .setCity('Cologne')
          .setCountry('Deutschland')
          .build()
      )
      .build()
      .apply();

    cy.executeHeaderAction('C_Flatrate_Term_Create_For_BPartners')
      .selectInListField('C_Flatrate_Conditions_ID', year1ConditionsName, year1ConditionsName)
      .writeIntoStringField('StartDate', '01/01/2019{enter}')
      .pressStartButton();
    cy.screenshot();
  });
});

function createAndCompleteConditions(conditionsName, transitionName) {
  cy.visit('/window/540113/NEW');
  cy.writeIntoStringField('Name', conditionsName);
  cy.selectInListField('Type_Conditions', 'Abonnement');
  cy.selectInListField('OnFlatrateTermExtend', 'Co');
  cy.selectInListField('C_Flatrate_Transition_ID', transitionName);

  cy.processDocument('Complete', 'Completed');
}
