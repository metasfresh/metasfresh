export class BillOfMaterial {
  constructor(product) {
    cy.log(`BillOfMaterial - set product = ${product}`);
    this.product = product;
    this.productComponent = undefined;
  }

  setProduct(product) {
    cy.log(`BillOfMaterial - set product = ${product}`);
    this.product = product;
    return this;
  }
  setProductComponent(productComponent) {
    cy.log(`BillOfMaterial - set productComponent = ${productComponent}`);
    this.productComponent = productComponent;
    return this;
  }
  apply() {
    cy.log(`BillOfMaterial - apply - START (name=${this.product})`);
    applyBillOfMaterial(this);
    cy.log(`BillOfMaterial - apply - END (name=${this.product})`);
    return this;
  }
}
function applyBillOfMaterial(billOfMaterial) {
  describe(`Create new BillOfMaterial ${billOfMaterial.product}`, function() {
    cy.visitWindow('53006', 'NEW');
    cy.get('#lookup_M_Product_ID input')
      .type(billOfMaterial.product)
      .type('\n');
    cy.contains('.input-dropdown-list-option', billOfMaterial.product).click();
    cy.writeIntoStringField('DocumentNo', 'X');

    cy.selectTab('PP_Product_BOMLine');
    cy.pressAddNewButton();
    cy.get('#lookup_M_Product_ID input')
      .eq(0)
      .type(billOfMaterial.productComponent);
    cy.contains('.input-dropdown-list-option', billOfMaterial.productComponent).click();
    cy.get('.form-field-IsQtyPercentage input').click({ force: true });
    cy.writeIntoStringField('QtyBatch', '44');
    cy.writeIntoStringField('Scrap', '10');
    cy.pressDoneButton();
  });
}
