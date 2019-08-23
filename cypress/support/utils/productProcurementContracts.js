export class ProductProcurementContracts {
  constructor(name) {
    cy.log(`ProductProcurementContracts - set name = ${name}`);
    this.name = name;
    this.attributeValues = [];
  }

  setName(name) {
    cy.log(`Product - set name = ${name}`);
    this.name = name;
    return this;
  }
  apply() {
    cy.log(`ProductProcurementContracts - apply - START (name=${this.name})`);
    applyProduct(this);
    cy.log(`ProductProcurementContracts - apply - END (name=${this.name})`);
    return this;
  }
}
function applyProduct(product) {
  describe(`Create new ProductProcurementContracts ${product.name}`, function() {
    cy.visitWindow('540288', 'NEW');
    cy.writeIntoLookupListField('M_Product_ID', product.name, product.name, false, false, null, true);
  });
}
