import {StockControlPurchase, StockControlPurchaseProduct} from '../../support/utils/stockControlPurchase';
import {Product, ProductCategory, ProductPrice} from '../../support/utils/product';
import {Builder} from "../../support/utils/builder";

describe('Create new Stock Control Purchase', function() {
  const timestamp = new Date().getTime();
  const productName1 = `StockControlPurchase1 ${timestamp}`;
  const productName2 = `StockControlPurchase2 ${timestamp}`;
  const productName3 = `StockControlPurchase3 ${timestamp}`;
  const productCategoryName = `ProductCategory ${timestamp}`;
  const priceListName = `PriceList ${timestamp}`;
  const priceSystemName = `PriceSystem ${timestamp}`;
  const priceListVersionName = `PriceListVersion ${timestamp}`;
  const productType = `Item`;

  it('Prepare 3 products', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName);

    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryName)
        .apply();
    });

    let productPrice;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName1)
        .setValue(productName1)
        .setProductType(productType)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName2)
        .setValue(productName2)
        .setProductType(productType)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });

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
