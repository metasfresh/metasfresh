import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { DiscountSchema } from '../../support/utils/discountschema';

describe('Create a new payment without invoice selection', function() {
  let paymentDocumentType;
  let bPartnerName;
  let discountSchemaName;
  let accountNo1060;
  let accountNo1106;
  let currency;

  it('Read the fixture', function() {
    cy.fixture('payment/create_payment_without_invoice_selection.json').then(f => {
      paymentDocumentType = f['paymentDocumentType'];
      bPartnerName = appendHumanReadableNow(f['bPartnerName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      accountNo1060 = f['accountNo1060'];
      accountNo1106 = f['accountNo1106'];
      currency = f['currency'];
    });
  });

  it('Create discount schema and customer', function() {
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: bPartnerName }).setCustomerDiscountSchema(discountSchemaName).apply();
    });
  });

  it('Create new payment', function() {
    cy.visitWindow('195', 'NEW');
    cy.writeIntoLookupListField('C_BPartner_ID', bPartnerName, bPartnerName);
    cy.getStringFieldValue('C_BP_BankAccount_ID').should('not.be.empty');
    cy.writeIntoStringField('PayAmt', '250', false, null, true);
    cy.getStringFieldValue('C_Currency_ID').should('equal', currency);
    cy.selectInListField('C_DocType_ID', paymentDocumentType);
    cy.getStringFieldValue('DocumentNo').should('not.be.empty');

    cy.completeDocument();
  });

  it('Check account transactions', function() {
    cy.openReferencedDocuments('AD_RelationType_ID-540201');
    cy.expectNumberOfRows(2);
    cy.selectNthRow(0).dblclick();
    cy.getStringFieldValue('Account_ID', false).should('contain', accountNo1060);
    cy.go('back');
    cy.selectNthRow(1).dblclick();
    cy.getStringFieldValue('Account_ID', false).should('contain', accountNo1106);
  });
});
