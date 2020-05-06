/// <reference types="Cypress" />

import {
  createAndCompleteTransition,
  createAndCompleteRefundPercentConditions,
} from '../../support/utils/contract_static';
import { BPartner, BPartnerLocation } from '../../support/utils/bpartner';
import { DiscountSchema, DiscountBreak } from '../../support/utils/discountschema';
import { runProcessCreateContract } from '../../support/functions/contractFunctions';

describe('Create tiered percent-based (TP) refund conditions', function() {
  before(function() {
    // login before each test and open the flatrate conditions window
    cy.loginByForm();
  });

  it('Create tiered percent-based refund conditions and a vendor with a respective contract', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering

    const transitionName = `Transition (TP) ${timestamp}`;
    createAndCompleteTransition(transitionName, null, null);
    cy.screenshot();

    const conditionsName = `Conditions (TP) ${timestamp}`;
    createAndCompleteRefundPercentConditions(
      conditionsName,
      transitionName,
      'T' /*Tiered / Gestaffelte Rückvergütung*/
    );
    cy.screenshot();

    const discountSchemaName = `DiscountSchema (TP) ${timestamp}`;
    new DiscountSchema(discountSchemaName)
      .addDiscountBreak(new DiscountBreak().setBreakValue(0).setBreakDiscount(0))
      .apply();
    cy.screenshot();

    const bPartnerName = `Vendor (TP) ${timestamp}`;
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

    runProcessCreateContract(conditionsName);
  });
});
