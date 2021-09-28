import { StockControlPurchase, StockControlPurchaseProduct } from '../../support/utils/stockControlPurchase';
import { Product, ProductCategory, ProductPrice } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create new Stock Control Purchase', function () {
  let productName1;
  let productName2;
  let productName3;
  let productCategoryName;
  let priceListName;
  let priceSystemName;
  let priceListVersionName;
  let productWarehouse;

  // test
  let productPrice;

  it('Read the fixture', function () {
    cy.fixture('warehouse/create_new_stock_control_purchase_spec.json').then((f) => {
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

      productName1 = appendHumanReadableNow(f['productName1']);
      productName2 = appendHumanReadableNow(f['productName2']);
      productName3 = appendHumanReadableNow(f['productName3']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      productWarehouse = f['warehouse'];
    });
  });

  it('Prepare Price, ProductCategory and Price', function () {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName);

    cy.fixture('product/simple_productCategory.json').then((productCategoryJson) => {
      Object.assign(new ProductCategory(), productCategoryJson).setName(productCategoryName).apply();
    });

    cy.fixture('product/product_price.json').then((productPriceJson) => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });
  });

  it('Prepare product 1', function () {
    cy.fixture('product/simple_product.json').then((productJson) => {
      Object.assign(new Product(), productJson).setName(productName1).setProductCategory(productCategoryName).addProductPrice(productPrice).apply();
    });
  });

  it('Prepare product 2', function () {
    cy.fixture('product/simple_product.json').then((productJson) => {
      Object.assign(new Product(), productJson).setName(productName2).setProductCategory(productCategoryName).addProductPrice(productPrice).apply();
    });
  });

  it('Prepare product 3', function () {
    cy.fixture('product/simple_product.json').then((productJson) => {
      Object.assign(new Product(), productJson).setName(productName3).setProductCategory(productCategoryName).addProductPrice(productPrice).apply();
    });
  });

  it('Create Stock Control Purchase', function () {
    new StockControlPurchase()
      .setDescription('test')
      .addProduct(new StockControlPurchaseProduct().setProduct(productName1).setQuantity('100').setWarehouse(productWarehouse))
      .addProduct(new StockControlPurchaseProduct().setProduct(productName2).setQuantity('200').setWarehouse(productWarehouse))
      .addProduct(new StockControlPurchaseProduct().setProduct(productName3).setQuantity('500').setWarehouse(productWarehouse))
      .apply();
  });

  it('Execute action Fresh_QtyOnHand_UpdateSeqNo', function () {
    cy.executeHeaderAction('Fresh_QtyOnHand_UpdateSeqNo');
    cy.getNotificationModal('Process completed successfully');
  });

  it('Check Processed button', function () {
    cy.setCheckBoxValue('Processed', true);
  });
});
