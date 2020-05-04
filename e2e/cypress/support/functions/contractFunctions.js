export function runProcessCreateContract(conditionsName) {
  cy.executeHeaderActionWithDialog('C_Flatrate_Term_Create_For_BPartners')
    .selectInListField('C_Flatrate_Conditions_ID', conditionsName, true)
    .selectDateViaPicker('StartDate')
    .pressStartButton();
  cy.screenshot();
}
