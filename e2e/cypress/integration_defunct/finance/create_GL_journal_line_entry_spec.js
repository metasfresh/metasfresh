import { getLanguageSpecific } from '../../support/utils/utils';

let description;
let category;
let postingType;
let currency;
let docType;
let account;
let amountSource;
let activity;

it('Read fixture and prepare the names', function() {
  cy.fixture('finance/create_GL_journal_line_entry_spec.json').then(f => {
    description = f['description'];
    category = f['category'];
    postingType = getLanguageSpecific(f,'postingType');
    currency = f['currency'];
    docType = f['docType'];
    account = f['account'];
    amountSource = f['amountSource'];
    activity = f['activity'];
  });
});

describe('Creating GL journal entry and change posting type', function() {
  it('create journal', function() {
    cy.visitWindow('540356', 'NEW');
    cy.writeIntoStringField('Description', description);
    cy.selectInListField('GL_Category_ID', category);
    cy.selectInListField('PostingType', postingType);
    cy.selectInListField('C_Currency_ID', currency);
    cy.selectInListField('C_DocType_ID', docType);
    cy.selectDateViaPicker('DateDoc');
  });

  it('add new entry line', function() {
    cy.pressAddNewButton();
    cy.writeIntoStringField('Description', description, true);
    cy.writeIntoLookupListField('Account_DR_ID', account, account, false, true);
    cy.writeIntoStringField('AmtSourceDr', amountSource, true);
    cy.writeIntoLookupListField('Account_CR_ID', account, account, false, true);
    cy.selectInListField('C_Activity_ID', activity, true);
    cy.pressDoneButton();

    cy.completeDocument();
  });
});
