/// <reference types="Cypress" />

import {
  createAndCompleteTransition,
  createAndCompleteRefundAmountConditions,
} from '../../support/utils/contract_static';
import { BPartner, BPartnerLocation } from '../../support/utils/bpartner';
import { DiscountSchema, DiscountBreak } from '../../support/utils/discountschema';
import { runProcessCreateContract } from '../../support/functions/contractFunctions';
//import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';

describe('Create tiered amount-based (TA) refund conditions', function() {
  before(function() {
    // login before each test and open the flatrate conditions window
    cy.loginByForm();
  });

  it('Create tiered amount-based refund conditions and a vendor with a respective contract', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering

    const transitionName = `Transition (TA) ${timestamp}`;
    createAndCompleteTransition(transitionName, null, null);
    cy.screenshot();

    const conditionsName = `Conditions (TA) ${timestamp}`;
    createAndCompleteRefundAmountConditions(conditionsName, transitionName, 'T' /*Tiered / Gestaffelte Rückvergütung*/);
    cy.screenshot();

    const discountSchemaName = `DiscountSchema (TA) ${timestamp}`;
    new DiscountSchema(discountSchemaName)
      .addDiscountBreak(new DiscountBreak().setBreakValue(0).setBreakDiscount(0))
      .apply();
    cy.screenshot();

    const bPartnerName = `Vendor (TA) ${timestamp}`;
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

    // TODO: verify that we have a contract (maybe via selectReference)

    // new PurchaseOrder
    //     .builder()
    //         .bPartner(bPartnerName)
    //         .line(new PurchaseOrderLine
    //             .builder()
    //             .product('Convenience')
    //             .tuQuantity('2')
    //             .build())
    //         .docAction('Complete')
    //         .docStatus('Completed')
    //     .build()
    //     .apply();
  });
});
