import {
  createAndCompleteTransition,
  createAndCompleteRefundAmountConditions,
} from '../../support/utils/contract_static';
import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema, DiscountBreak } from '../../support/utils/discountschema';
import { runProcessCreateContract } from '../../support/functions/contractFunctions';

describe('Create accumulated amount-based (AA) refund conditions', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const transitionName = `Transition (AA) ${timestamp}`;
  const conditionsName = `Conditions (AA) ${timestamp}`;

  it('Create transition', function() {
    createAndCompleteTransition(transitionName, null, null);
    cy.screenshot();
  });

  it('Create conditions', function() {
    createAndCompleteRefundAmountConditions(conditionsName, transitionName, 'A' /*Accumulated / Gesamtrückvergütung*/);
    cy.screenshot();
  });

  it('Create accumulated amount-based refund conditions', function() {
    const discountSchemaName = `DiscountSchema (AA) ${timestamp}`;
    new DiscountSchema(discountSchemaName)
      .addDiscountBreak(new DiscountBreak().setBreakValue(0).setBreakDiscount(0))
      .apply();
    cy.screenshot();

    const vendorName = `Vendor (AA) ${timestamp}`;
    cy.fixture('purchase/simple_vendor.json').then(vendorJson => {
      Object.assign(new BPartner(), vendorJson)
        .setName(vendorName)
        .clearContacts()
        .apply();
    });

    runProcessCreateContract(conditionsName);
  });
});
