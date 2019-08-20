import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Bank } from '../../support/utils/bank';
import { Builder } from '../../support/utils/builder';
import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';
import { DocumentActionKey, DocumentStatusKey } from '../../support/utils/constants';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';

describe('Create Sales order', function() {
  const date = humanReadableNow();
  const customer = `CustomerTest ${date}`;
  const productName = `ProductTest ${date}`;
  const productCategoryName = `ProductCategoryName ${date}`;
  const discountSchemaName = `DiscountSchemaTest ${date}`;
  const priceSystemName = `PriceSystem ${date}`;
  const priceListName = `PriceList ${date}`;
  const priceListVersionName = `PriceListVersion ${date}`;
  const productType = 'Item';

  before(function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);
    Builder.createBasicProductEntities(
      productCategoryName,
      productCategoryName,
      priceListName,
      productName,
      productName,
      productType
    );

    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });

    cy.fixture('finance/bank.json').then(productJson => {
      Object.assign(new Bank(), productJson).apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customer }).setCustomerDiscountSchema(discountSchemaName).apply();
    });

    cy.readAllNotifications();
  });
  it('Create a sales order', function() {
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      new SalesOrder()
        .setBPartner(customer)
        .setPriceSystem(priceSystemName)
        .addLine(new SalesOrderLine().setProduct(productName).setQuantity(1))
        .setDocumentAction(getLanguageSpecific(miscDictionary, DocumentActionKey.Complete))
        .setDocumentStatus(getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed))
        .apply();
    });
    /** Go to Shipment disposition*/
    cy.openReferencedDocuments('M_ShipmentSchedule');
    cy.selectNthRow(0).dblclick();
    /**Change shipment date */

    cy.selectOffsetDateViaPicker('DeliveryDate_Override', 1);
    cy.selectOffsetDateViaPicker('PreparationDate_Override', 1);

    var nextDay = new Date();
    nextDay.setDate(nextDay.getDate() + 1);
    let nextDayAsString = nextDay.toLocaleDateString('en-US', { year: 'numeric', month: '2-digit', day: '2-digit' });

    cy.get(
      '.form-field-DeliveryDate_Effective, .form-field-DeliveryDate_Override, .form-field-PreparationDate_Effective, .form-field-PreparationDate_Override'
    )
      .find('input')
      .should(input => {
        expect(input.val()).to.have.string(nextDayAsString);
      });
  });
});
