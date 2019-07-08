export function createAndCompleteTransition(transitionName, extensionType, nextConditionsName) {
  describe(`Create and complete transition record ${transitionName}`, function() {
    cy.visitWindow('540120', 'NEW');

    cy.selectInListField('C_Calendar_Contract_ID', 'Buch');
    cy.writeIntoStringField('Name', transitionName);

    cy.clearField('TermDuration');
    cy.writeIntoStringField('TermDuration', '1'); // note: there seems to be some bug somewhere, just '1' dos not work

    cy.selectInListField('TermDurationUnit', 'Jahr');

    cy.clearField('TermOfNotice');
    cy.writeIntoStringField('TermOfNotice', '1');
    cy.selectInListField('TermOfNoticeUnit', 'Monat');

    cy.clearField('DeliveryInterval');
    cy.writeIntoStringField('DeliveryInterval', '1');

    cy.selectInListField('DeliveryIntervalUnit', 'Monat');

    if (extensionType) {
      cy.selectInListField('ExtensionType', 'Extend contract for all periods');
    }
    if (nextConditionsName) {
      cy.selectInListField('C_Flatrate_Conditions_Next_ID', nextConditionsName);
    }

    cy.pressAddNewButton();
    cy.getStringFieldValue('DeadLine', true /*modal*/).then(deadLine => {
      if (deadLine != 0) {
        cy.clearField('DeadLine');
        cy.writeIntoStringField('DeadLine', '0', true /*modal*/);
      }
    });
    cy.selectInListField('DeadLineUnit', 'Tag', true /*modal*/);
    cy.selectInListField('Action', 'Statuswechsel', true /*modal*/);
    cy.selectInListField('ContractStatus', 'Gekündigt', true /*modal*/);

    //cy.wait(500); // we need to wait a bit (unfortunately idk for what in articular), to avoid the "do you really want to leave" error
    cy.pressDoneButton();

    //cy.wait(500);
    cy.processDocument('Complete', 'Completed');
  });
}

export function createAndCompleteRefundPercentConditions(conditionsName, transitionName, refundMode) {
  describe(`Create and complete conditions record ${conditionsName}`, function() {
    const conditionsType = 'Rückvergütung';

    cy.visitWindow('540113', 'NEW');
    cy.writeIntoStringField('Name', conditionsName);
    cy.selectInListField('Type_Conditions', conditionsType);
    cy.selectInListField('C_Flatrate_Transition_ID', transitionName);

    cy.selectTab('C_Flatrate_RefundConfig');

    createPercentConfig(refundMode, '0' /*minQty*/, '10' /*percent*/);
    createPercentConfig(refundMode, '15' /*minQty*/, '20' /*percent*/);

    //cy.wait(500);
    cy.processDocument('Complete', 'Completed');
  });
}

function createPercentConfig(refundMode, minQty, percent) {
  cy.pressAddNewButton();
  cy.selectInListField('RefundMode', refundMode);
  cy.selectInListField('RefundBase', 'P' /*percent*/);

  cy.writeIntoLookupListField('M_Product_ID', 'P002737', 'Convenience Salat 250g_P002737');
  cy.writeIntoStringField('MinQty', `{selectall}{backspace}${minQty}`);
  cy.writeIntoStringField('RefundPercent', `{selectall}{backspace}${percent}`);
  cy.selectInListField('C_InvoiceSchedule_ID', 'jährlich');

  cy.pressDoneButton(1000);
}

export function createAndCompleteRefundAmountConditions(conditionsName, transitionName, refundMode) {
  describe(`Create and complete conditions record ${conditionsName}`, function() {
    const conditionsType = 'Rückvergütung';

    cy.visitWindow('540113', 'NEW');
    cy.writeIntoStringField('Name', conditionsName);
    cy.selectInListField('Type_Conditions', conditionsType);
    cy.selectInListField('C_Flatrate_Transition_ID', transitionName);

    cy.selectTab('C_Flatrate_RefundConfig');

    createAmountConfig(refundMode, '0' /*minQty*/, '0.50' /*amount*/);
    createAmountConfig(refundMode, '15' /*minQty*/, '0.75' /*amount*/);
    cy.wait(500);
    cy.processDocument('Complete', 'Completed');
  });
}

function createAmountConfig(refundMode, minQty, amount) {
  cy.pressAddNewButton();

  cy.getStringFieldValue('MinQty', true /*modal*/).then(minQtyFieldValue => {
    if (minQty && minQty != minQtyFieldValue) {
      cy.clearField('MinQty', true /*modal*/);
      cy.writeIntoStringField('MinQty', minQty, true /*modal*/);
    }
  });
  cy.selectInListField('RefundMode', refundMode, true /*modal*/);
  cy.selectInListField('RefundBase', 'B' /*Betrag/amount*/, true /*modal*/);

  cy.writeIntoLookupListField(
    'M_Product_ID',
    'P002737',
    'Convenience Salat 250g_P002737',
    false /*typeList*/,
    true /*modal*/
  );

  cy.writeIntoLookupListField('C_Currency_ID', 'EUR', 'EUR', false /*typeList*/, true /*modal*/);

  cy.getStringFieldValue('RefundAmt', true /*modal*/).then(refundAmtFieldValue => {
    if (amount && amount != refundAmtFieldValue) {
      cy.clearField('RefundAmt', true /*modal*/);
      cy.writeIntoStringField('RefundAmt', amount, true /*modal*/);
    }
  });

  cy.selectInListField('C_InvoiceSchedule_ID', 'jährlich', true /*modal*/);

  cy.pressDoneButton(1000);
}
