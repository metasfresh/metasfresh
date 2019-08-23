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

  setLength(length) {
    cy.log(`PackingMaterialBuilder - set length = ${length}`);
    this.length = length;
    return this;
  }
  setWidth(width) {
    cy.log(`PackingMaterialBuilder - set width = ${width}`);
    this.width = width;
    return this;
  }
  setHeight(height) {
    cy.log(`PackingMaterialBuilder - set height = ${height}`);
    this.height = height;
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

    if (packingMaterial.length) {
      cy.writeIntoStringField('Length', packingMaterial.length);
    }
    if (packingMaterial.width) {
      cy.writeIntoStringField('Width', packingMaterial.width);
    }
    if (packingMaterial.height) {
      cy.writeIntoStringField('Height', packingMaterial.height);
    }
  });
}
