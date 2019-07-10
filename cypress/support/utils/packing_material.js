export class PackingMaterial {
  constructor(name) {
    cy.log(`PackingMaterialBuilder - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`PackingMaterialBuilder - set name = ${name}`);
    this.name = name;
    return this;
  }
  setProduct(product) {
    cy.log(`PackingMaterialBuilder - set product = ${product}`);
    this.product = product;
    return this;
  }

  apply() {
    cy.log(`PackingMaterial - apply - START (name=${this.name})`);
    applyPackingMaterial(this);
    cy.log(`PackingMaterial - apply - END (name=${this.name})`);
    return this;
  }
}
function applyPackingMaterial(packingMaterial) {
  describe('create new packing material', function() {
    cy.visitWindow('540192', 'NEW');

    cy.writeIntoStringField('Name', packingMaterial.name);
    cy.selectInListField('M_Product_ID', packingMaterial.product);
  });
}
