/*
@kuba idk where all these should be placed so i just threw all of them in here.

Pls either tell me the _organisation_ standards for cypress/js :^).
 */


Cypress.Commands.add('getCurrentRecordId', () => {
  describe('Select the current record ID from the url', function () {
    let currentRecordId = 0;
    cy.url().then(ulrr => {
      currentRecordId = ulrr.split('/').pop();
    });
    return currentRecordId;
  });
});
