import {
  createAndCompleteTransition,
  createAndCompleteRefundAmountConditions,
} from '../../support/utils/contract_static';
import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema, DiscountBreak } from '../../support/utils/discountschema';
import { runProcessCreateContract } from '../../support/functions/contractFunctions';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create accumulated amount-based (AA) refund conditions', function() {
  const date = humanReadableNow();
  const transitionName = `Transition (AA) ${date}`;
  const conditionsName = `Conditions (AA) ${date}`;

  it('Create transition', function() {
    createAndCompleteTransition(transitionName, null, null);
    cy.screenshot();
  });

  it('Create conditions', function() {
    createAndCompleteRefundAmountConditions(conditionsName, transitionName, 'A' /*Accumulated / Gesamtrückvergütung*/);
    cy.screenshot();
  });

  it('Create accumulated amount-based refund conditions', function() {
    const discountSchemaName = `DiscountSchema (AA) ${date}`;
    new DiscountSchema(discountSchemaName)
      .addDiscountBreak(new DiscountBreak().setBreakValue(0).setBreakDiscount(0))
      .apply();
    cy.screenshot();

    const vendorName = `Vendor (AA) ${date}`;
    cy.fixture('purchase/simple_vendor.json').then(vendorJson => {
      Object.assign(new BPartner(), vendorJson)
        .setName(vendorName)
        .clearContacts()
        .apply();
    });

    runProcessCreateContract(conditionsName);
  });
});
