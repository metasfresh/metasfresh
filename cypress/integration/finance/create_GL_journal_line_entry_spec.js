import { getLanguageSpecific } from '../../support/utils/utils';

describe('Creating GL journal entry and change posting type', function() {
  before(function() {
    cy.visit('/window/540356');
  });

  it('create journal', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);

    cy.writeIntoStringField('Description', 'Spende');
    cy.selectInListField('GL_Category_ID', 'Standard');
    cy.selectInListField('PostingType', 'Actual');
    cy.selectInListField('C_Currency_ID', 'CHF');
    cy.selectInListField('C_DocType_ID', 'Journal');
    cy.selectDateViaPicker('DateDoc');
  });

  it('add new entry line', function() {
    cy.pressAddNewButton();
    cy.writeIntoStringField('Description', 'Spende', true /*modal*/);
    cy.writeIntoLookupListField('Account_DR_ID', '10', '1020_UBS Kontokorrent', false /*typeList*/, true /*modal*/);
    cy.writeIntoStringField('AmtSourceDr', '100.00', true /*modal*/);
    cy.writeIntoLookupListField('Account_CR_ID', '10', '1020_UBS Kontokorrent', false /*typeList*/, true /*modal*/);
    cy.selectInListField('C_Activity_ID', '250_Verwaltung', true /*modal*/);
    cy.pressDoneButton();
  });

  it('complete', function() {
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.processDocument(
        getLanguageSpecific(miscDictionary, 'docActionComplete'),
        getLanguageSpecific(miscDictionary, 'docStatusCompleted')
      );
    });
  });
});
