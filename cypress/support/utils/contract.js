export function createAndCompleteTransition(transitionName, extensionType, nextConditionsName) {
  describe(`Create and complete transition record ${transitionName}`, function() {
    const one = '{selectall}{backspace}1';

    cy.visit('/window/540120/NEW');
    cy.writeIntoStringField('Name', transitionName);
    cy.selectInListField('C_Calendar_Contract_ID', 'Buch');
    cy.writeIntoStringField('TermDuration', one); // note: there seems to be some bug somewhere, just '1' dos not work
    cy.selectInListField('TermDurationUnit', 'Jahr');
    cy.writeIntoStringField('TermOfNotice', one);
    cy.selectInListField('TermOfNoticeUnit', 'Monat');
    cy.writeIntoStringField('DeliveryInterval', one);
    cy.selectInListField('DeliveryIntervalUnit', 'Monat');

    if (extensionType) {
      cy.selectInListField('ExtensionType', 'Extend contract for all periods');
    }
    if (nextConditionsName) {
      cy.selectInListField('C_Flatrate_Conditions_Next_ID', nextConditionsName);
    }

    cy.pressAddNewButton();
    cy.writeIntoStringField('DeadLine', '0');
    cy.selectInListField('DeadLineUnit', 'Tag');
    cy.selectInListField('Action', 'Statuswechsel');
    cy.selectInListField('ContractStatus', 'Gekündigt');

    cy.wait(500); // we need to wait a bit (unfortunately idk for what in articular), to avoid the "do you really want to leave" error
    cy.pressDoneButton();

    cy.wait(500);
    cy.processDocument('Complete', 'Completed');
  });
}

export function createAndCompleteRefundPercentConditions(conditionsName, transitionName, refundMode) {
  describe(`Create and complete conditions record ${conditionsName}`, function() {
    const conditionsType = 'Rückvergütung';

    cy.visit('/window/540113/NEW');
    cy.writeIntoStringField('Name', conditionsName);
    cy.selectInListField('Type_Conditions', conditionsType);
    cy.selectInListField('C_Flatrate_Transition_ID', transitionName);

    cy.selectTab('C_Flatrate_RefundConfig');

    createPercentConfig(refundMode, '0' /*minQty*/, '10' /*percent*/);
    createPercentConfig(refundMode, '15' /*minQty*/, '20' /*percent*/);

    cy.wait(500);
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

    cy.visit('/window/540113/NEW');
    cy.writeIntoStringField('Name', conditionsName);
    cy.selectInListField('Type_Conditions', conditionsType);
    cy.selectInListField('C_Flatrate_Transition_ID', transitionName);

    cy.selectTab('C_Flatrate_RefundConfig');

    createAmountConfig(refundMode, '0' /*minQty*/, '0.5' /*amount*/);
    createAmountConfig(refundMode, '15' /*minQty*/, '0.75' /*amount*/);
    cy.wait(500);
    cy.processDocument('Complete', 'Completed');
  });
}

function createAmountConfig(refundMode, minQty, amount) {
  cy.pressAddNewButton();

  cy.writeIntoStringField('MinQty', `{selectall}{backspace}${minQty}`);
  cy.wait(500);
  cy.selectInListField('RefundMode', refundMode);
  cy.selectInListField('RefundBase', 'B' /*Betrag/amount*/);

  cy.writeIntoLookupListField('M_Product_ID', 'P002737', 'Convenience Salat 250g_P002737');
  cy.writeIntoLookupListField('C_Currency_ID', 'EUR', 'EUR');

  cy.selectInListField('C_InvoiceSchedule_ID', 'jährlich');
  cy.wait(500);

  cy.writeIntoStringField('RefundAmt', `{selectall}{backspace}${amount}{enter}`);

  cy.pressDoneButton(1000);
}
