import { StockControlPurchase, StockControlPurchaseProduct } from '../../support/utils/stockControlPurchase';
import { Product, ProductCategory, ProductPrice } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create new Stock Control Purchase', function() {
  const date = humanReadableNow();
  const productName1 = `StockControlPurchase1 ${date}`;
  const productName2 = `StockControlPurchase2 ${date}`;
  const productName3 = `StockControlPurchase3 ${date}`;
  const productCategoryName = `ProductCategory ${date}`;
  const priceListName = `PriceList ${date}`;
  const priceSystemName = `PriceSystem ${date}`;
  const priceListVersionName = `PriceListVersion ${date}`;
  const productType = `Item`;

  // test
  let productPrice;

  it('Prepare Price, ProductCategory and Price', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName);

    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryName)
        .apply();
    });

    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });
  });

  it('Prepare product 1', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName1)
        .setValue(productName1)
        .setProductType(productType)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
  });

  it('Prepare product 2', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName2)
        .setValue(productName2)
        .setProductType(productType)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
  });

  it('Prepare product 3', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName3)
        .setValue(productName3)
        .setProductType(productType)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
  });

  it('Create Stock Control Purchase', function() {
    new StockControlPurchase()
      .setDescription('test')
      .addProduct(new StockControlPurchaseProduct().setProduct(productName1).setQuantity('100'))
      .addProduct(new StockControlPurchaseProduct().setProduct(productName2).setQuantity('200'))
      .addProduct(new StockControlPurchaseProduct().setProduct(productName3).setQuantity('500'))
      .apply();
  });

  it('Execute action Fresh_QtyOnHand_UpdateSeqNo', function() {
    cy.executeHeaderAction('Fresh_QtyOnHand_UpdateSeqNo');
    cy.getNotificationModal('Process completed successfully');
  });

  it('Check Processed button', function() {
    cy.setCheckBoxValue('Processed', true);
  });
});
