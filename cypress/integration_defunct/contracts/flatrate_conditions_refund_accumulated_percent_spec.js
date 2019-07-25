import {
  createAndCompleteTransition,
  createAndCompleteRefundPercentConditions,
} from '../../support/utils/contract_static';
import { BPartner, BPartnerLocation } from '../../support/utils/bpartner';
import { DiscountSchema, DiscountBreak } from '../../support/utils/discountschema';
import { runProcessCreateContract } from '../../support/functions/contractFunctions';

describe('Create accumulated percent-based (AP) refund conditions', function() {
  it('Create accumulated percent-based refund conditions', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering

    const transitionName = `Transitions (AP) ${timestamp}`;
    createAndCompleteTransition(transitionName, null, null);
    cy.screenshot();

    const conditionsName = `Conditions  (AP) ${timestamp}`;
    createAndCompleteRefundPercentConditions(conditionsName, transitionName, 'A' /*Accumulated / Gesamtrückvergütung*/);
    cy.screenshot();

    const discountSchemaName = `Discount schema (AP) ${timestamp}`;
    new DiscountSchema(discountSchemaName)
      .addDiscountBreak(new DiscountBreak().setBreakValue(0).setBreakDiscount(0))
      .apply();
    cy.screenshot();

    const bPartnerName = `Vendor (AP) ${timestamp}`;
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
    cy.screenshot();

    runProcessCreateContract(conditionsName);
  });
});
