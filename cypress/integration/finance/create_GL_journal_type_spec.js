let description="test";
let category="Standard";
let postingType1="Statistical";
let currency="CHF";
let docType="Journal";
let postingType2="Budget";
let postingType3="Ist (Jahr Ende)";
let postingType4="Statistical";

it('Read fixture and prepare the names', function() {
  cy.fixture('finance/create_GL_journal_type_spec.json').then(f => {
    description = f['description'];
    category = f['category'];
    postingType1 = f['postingType1'];
    currency = f['currency'];
    docType = f['docType'];
    postingType2 = f['postingType2'];
    postingType3 = f['postingType3'];
    postingType4 = f['postingType4'];
  });
});

describe('Creating GL journal entry and change posting type', function() {
  before(function() {
    cy.visit('/window/540356');
  });

  it('create tour', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);

    cy.writeIntoStringField('Description', description);
    cy.selectInListField('GL_Category_ID', category);
    cy.selectInListField('PostingType', postingType1);
    cy.selectInListField('C_Currency_ID', currency);
    cy.selectInListField('C_DocType_ID', docType);
    cy.selectDateViaPicker('DateDoc');
    cy.selectInListField('PostingType', postingType2);
    cy.selectInListField('PostingType', postingType3);
    cy.selectInListField('PostingType', postingType4);
  });
});
