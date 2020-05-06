let description;
let category;
let postingType1;
let currency;
let docType;
let postingType2;
let postingType3;
let postingType4;

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
  it('create journal', function() {
    cy.visitWindow('540356', 'NEW');

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
