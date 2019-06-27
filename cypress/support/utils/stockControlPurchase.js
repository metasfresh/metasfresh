export class StockControlPurchase {
  contructor(description) {
    this.description = description;
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
    applyStockControlPurchase(this);
    cy.log(`StockControlPurchase - apply - END (${this.description})`);
    return this;
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
}

function applyStockControlPurchase(stockControlPurchase) {
  cy.visitWindow('540253');
  cy.clickHeaderNav(Cypress.messages.window.new.caption);

  cy.writeIntoStringField('Description', stockControlPurchase.description);

  if (stockControlPurchase.products.length > 0) {
    stockControlPurchase.products.forEach(product => {
      applyProduct(product);
    });
  }
}

function applyProduct(product) {
  cy.pressAddNewButton();
  cy.writeIntoLookupListField('M_Product_ID', product.product, product.product, true);

  cy.writeIntoStringField('QtyCount', product.qty, true);
  cy.selectInListField('PP_Plant_ID', 'test', true);
  cy.pressDoneButton();
}
