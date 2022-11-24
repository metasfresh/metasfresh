export class StockControlPurchase {
  constructor() {
    this.products = [];
  }

  setDescription(description) {
    cy.log(`StockControlPurchase - setDescription = ${description}`);
    this.description = description;
    return this;
  }

  addProduct(product) {
    cy.log(`StockControlPurchase - addProduct = ${JSON.stringify(product)}`);
    this.products.push(product);
    return this;
  }

  apply() {
    cy.log(`StockControlPurchase - apply - START (${this.description})`);
    StockControlPurchase.applyStockControlPurchase(this);
    cy.log(`StockControlPurchase - apply - END (${this.description})`);
    return this;
  }

  static applyStockControlPurchase(stockControlPurchase) {
    cy.visitWindow('540253');
    cy.clickHeaderNav('new');

    cy.writeIntoStringField('Description', stockControlPurchase.description);

    stockControlPurchase.products.forEach((product) => {
      StockControlPurchase.applyProduct(product);
    });
  }

  static applyProduct(product) {
    cy.pressAddNewButton();
    cy.writeIntoLookupListField('M_Product_ID', product.product, product.product, true);

    cy.writeIntoStringField('QtyCount', product.qty, true);
    cy.selectInListField('M_Warehouse_ID', product.warehouse, true);
    cy.pressDoneButton();
  }
}

export class StockControlPurchaseProduct {
  constructor(product) {
    this.product = product;
  }

  setProduct(product) {
    cy.log(`StockControlPurchaseProduct - setProduct = ${product}`);
    this.product = product;
    return this;
  }

  setQuantity(qty) {
    cy.log(`StockControlPurchaseProduct - setQuantity = ${qty}`);
    this.qty = qty;
    return this;
  }

  setWarehouse(warehouse) {
    cy.log(`StockControlPurchaseProduct - setWarehouse = ${warehouse}`);
    this.warehouse = warehouse;
    return this;
  }
}
