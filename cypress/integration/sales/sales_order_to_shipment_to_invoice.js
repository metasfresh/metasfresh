import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Builder } from '../../support/utils/builder';
import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';
import { DocumentActionKey, DocumentStatusKey } from '../../support/utils/constants';

describe('Create Sales order', function() {
  const date = humanReadableNow();
  const customer = `CustomerTest ${date}`;
  const productName = `ProductTest ${date}`;
  const productValue = `sales_order_test ${date}`;
  const productCategoryName = `ProductCategoryName ${date}`;
  const productCategoryValue = `ProductCategoryValue ${date}`;
  const discountSchemaName = `DiscountSchemaTest ${date}`;
  const priceSystemName = `PriceSystem ${date}`;
  const priceListName = `PriceList ${date}`;
  const priceListVersionName = `PriceListVersion ${date}`;
  const productType = 'Item';

  before(function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);
    Builder.createBasicProductEntities(
      productCategoryName,
      productCategoryValue,
      priceListName,
      productName,
      productValue,
      productType
    );
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: customer })
        .setCustomerDiscountSchema(discountSchemaName)
        .setBank(undefined);

      bpartner.apply();
    });

    cy.readAllNotifications();
  });
  it('Create a sales order', function() {
    cy.visitWindow('143', 'NEW');
    cy.get('#lookup_C_BPartner_ID input')
      .type(customer)
      .type('\n');
    cy.contains('.input-dropdown-list-option', customer).click();

    cy.selectInListField('M_PricingSystem_ID', priceSystemName);

    const addNewText = Cypress.messages.window.batchEntry.caption;
    cy.get('.tabs-wrapper .form-flex-align .btn')
      .contains(addNewText)
      .should('exist')
      .click();
    cy.get('.quick-input-container .form-group').should('exist');
    cy.writeIntoLookupListField('M_Product_ID', productName, productName, false, false, null, true);

    cy.get('.form-field-Qty')
      .click()
      .find('.input-body-container.focused')
      .should('exist')
      .find('i')
      .eq(0)
      .click();

    cy.get('.form-field-Qty')
      .find('input')
      .should('have.value', '0.1')
      .type('1{enter}');
    cy.waitUntilProcessIsFinished();
    /**Complete sales order */
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.processDocument(
        getLanguageSpecific(miscDictionary, DocumentActionKey.Complete),
        getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed)
      );
    });
    cy.waitUntilProcessIsFinished();
    cy.get('.btn-header.side-panel-toggle').click({ force: true });
    cy.get('.order-list-nav .order-list-btn')
      .eq('1')
      .find('i')
      .click({ force: true });
    /** Go to Shipment disposition*/
    cy.get('.reference_M_ShipmentSchedule').click();
    cy.get('tbody tr')
      .eq('0')
      .click({ force: true });
    /**Generate shipments */
    cy.executeQuickAction('M_ShipmentSchedule_EnqueueSelection');
    cy.pressStartButton();
    /**Wait for the shipment schedule process to complete */
    cy.waitUntilProcessIsFinished();
    /**Open notifications */
    cy.get('.header-item-badge.icon-lg i', { timeout: 10000 }).click();
    cy.get('.inbox-item-unread .inbox-item-title')
      .filter(':contains("' + customer + '")')
      .first()
      .click();
    cy.waitUntilProcessIsFinished();
    cy.get('.btn-header.side-panel-toggle').click({ force: true });
    cy.get('.order-list-nav .order-list-btn')
      .eq('1')
      .find('i')
      .click({ force: true });
    /**Billing - Invoice disposition */
    cy.get('.reference_C_Invoice_Candidate', { timeout: 10000 }).click();
    cy.get('tbody tr')
      .eq('0')
      .click();
    /**Generate invoices on billing candidates */
    cy.executeQuickAction('C_Invoice_Candidate_EnqueueSelectionForInvoicing');
    cy.pressStartButton();
    // /**Open notifications */
    cy.get('.header-item-badge.icon-lg i', { timeout: 10000 }).click();
    cy.get('.inbox-item-unread .inbox-item-title')
      .filter(':contains("' + customer + '")')
      .first()
      .click();
  });
});
