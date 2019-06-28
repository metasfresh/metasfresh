export class BillOfMaterial {
  constructor(product) {
    cy.log(`BillOfMaterial - set product = ${product}`);
    this.product = product;
    this.name = `${product}-BOM`;
    this.productComponent = undefined;
  }

  setName(name) {
    cy.log(`BillOfMaterial - set name = ${name}`);
    this.name = name;
    return this;
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
    cy.visitWindow('53006', 'NEW', billOfMaterial.name /*documentIdAliasName*/);

    cy.writeIntoTextField('Name', billOfMaterial.name);

    cy.writeIntoLookupListField('M_Product_ID', billOfMaterial.product, billOfMaterial.product);
    cy.writeIntoStringField('DocumentNo', 'X');

    cy.selectTab('PP_Product_BOMLine');
    cy.pressAddNewButton();

    cy.writeIntoLookupListField(
      'M_Product_ID',
      billOfMaterial.productComponent,
      billOfMaterial.productComponent,
      false,
      true /*modal*/
    );

    cy.writeIntoStringField('QtyBOM', '44', true /*modal */);
    cy.writeIntoStringField('Scrap', '10', true /*modal */);

    cy.pressDoneButton();
  });
}
