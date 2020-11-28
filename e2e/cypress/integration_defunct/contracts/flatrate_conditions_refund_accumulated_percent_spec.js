import {
  createAndCompleteRefundPercentConditions,
  createAndCompleteTransition,
} from '../../support/utils/contract_static';
import { BPartner } from '../../support/utils/bpartner';
import { DiscountBreak, DiscountSchema } from '../../support/utils/discountschema';
import { runProcessCreateContract } from '../../support/functions/contractFunctions';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create accumulated percent-based (AP) refund conditions', function() {
  it('Create accumulated percent-based refund conditions', function() {
    const date = humanReadableNow();

    const transitionName = `Transitions (AP) ${date}`;
    createAndCompleteTransition(transitionName, null, null);
    cy.screenshot();

    const conditionsName = `Conditions  (AP) ${date}`;
    createAndCompleteRefundPercentConditions(conditionsName, transitionName, 'A' /*Accumulated / Gesamtrückvergütung*/);
    cy.screenshot();

    const discountSchemaName = `Discount schema (AP) ${date}`;
    new DiscountSchema(discountSchemaName)
      .addDiscountBreak(new DiscountBreak().setBreakValue(0).setBreakDiscount(0))
      .apply();
    cy.screenshot();

    const bPartnerName = `Vendor (AP) ${date}`;
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
