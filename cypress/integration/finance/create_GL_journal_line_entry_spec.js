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
    postingType = f['postingType'];
    currency = f['currency'];
    docType = f['docType'];
    account = f['account'];
    amountSource = f['amountSource'];
    activity = f['activity'];
  });
});

describe('Creating GL journal entry and change posting type', function() {
  before(function() {
    cy.visit('/window/540356');
  });

  it('create journal', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);

    cy.writeIntoStringField('Description', description);
    cy.selectInListField('GL_Category_ID', category);
    cy.selectInListField('PostingType', postingType);
    cy.selectInListField('C_Currency_ID', currency);
    cy.selectInListField('C_DocType_ID', docType);
    cy.selectDateViaPicker('DateDoc');
  });

  it('add new entry line', function() {
    cy.pressAddNewButton();
    cy.writeIntoStringField('Description', description, true /*modal*/);
    cy.writeIntoLookupListField('Account_DR_ID', account, account, false /*typeList*/, true /*modal*/);
    cy.writeIntoStringField('AmtSourceDr', amountSource, true /*modal*/);
    cy.writeIntoLookupListField('Account_CR_ID', account, account, false /*typeList*/, true /*modal*/);
    cy.selectInListField('C_Activity_ID', activity, true /*modal*/);
    cy.pressDoneButton();

    cy.completeDocument();
  });
});
