import { StockControlPurchase, StockControlPurchaseProduct } from '../../support/utils/stockControlPurchase';
import { Product } from '../../support/utils/product';

describe('create new stock control purchase', function() {
  const timestamp = new Date().getTime();
  const description = `test ${timestamp}`;
  const productName1 = `StockControlPurchase1 ${timestamp}`;
  const productName2 = `StockControlPurchase2 ${timestamp}`;
  const productName3 = `StockControlPurchase3 ${timestamp}`;

  before(function() {
    cy.fixture('warehouse/product_stock_control_purchase.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName1)
        .apply();
    });

    cy.fixture('warehouse/product_stock_control_purchase.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName2)
        .apply();
    });

    cy.fixture('warehouse/product_stock_control_purchase.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName3)
        .apply();
    });
  });

  it('create process', function() {
    cy.fixture('warehouse/stock_control_purchase.json').then(stockControlPurchase => {
      Object.assign(new StockControlPurchase(), stockControlPurchase)
        .setDescription(description)
        .addProduct(new StockControlPurchaseProduct().setProduct(productName1).setQuantity('100'))
        .addProduct(new StockControlPurchaseProduct().setProduct(productName2).setQuantity('200'))
        .addProduct(new StockControlPurchaseProduct().setProduct(productName3).setQuantity('500'))
        .apply();
    });
  });

  it('header actions', function() {
    cy.executeHeaderAction('Fresh_QtyOnHand_UpdateSeqNo');
  });

  it('processed activate', function() {
    cy.clickOnCheckBox('Processed');
  });
});
