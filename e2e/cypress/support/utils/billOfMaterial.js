export class BillOfMaterial {
  setName(name) {
    cy.log(`BillOfMaterial - set name = ${name}`);
    this.name = name;
    return this;
  }

  setProduct(product) {
    cy.log(`BillOfMaterial - set product = ${product}`);
    this.product = product;

    if (!name) {
      this.name = product + '_BOM';
    }
    return this;
  }

  apply() {
    cy.log(`BillOfMaterial - apply - START (name=${this.product})`);
    applyBillOfMaterial(this);
    cy.log(`BillOfMaterial - apply - END (name=${this.product})`);
  }
}

function applyBillOfMaterial(billOfMaterial) {
  cy.visitWindow('541317', 'NEW');

  cy.writeIntoTextField('Name', billOfMaterial.name);
  cy.writeIntoLookupListField('M_Product_ID', billOfMaterial.product, billOfMaterial.product);
}
