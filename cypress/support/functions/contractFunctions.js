export function runProcessCreateContract(conditionsName) {
  cy.executeHeaderActionWithDialog('C_Flatrate_Term_Create_For_BPartners')
    .selectInListField('C_Flatrate_Conditions_ID', conditionsName, true /*modal*/, '/rest/api/process/' /*rewriteUrl*/)
    .selectDateViaPicker('StartDate')
    .pressStartButton();
  cy.screenshot();
}
