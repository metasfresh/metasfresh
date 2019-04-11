export class Product {
  constructor(name) {
    cy.log(`Product - set name = ${name}`);
    this.name = name;
    this.attributeValues = [];
  }

  setName(name) {
    cy.log(`Product - set name = ${name}`);
    this.name = name;
    return this;
  }

  setValue(name) {
    cy.log(`Product - set value = ${name}`);
    this.name = name;
    return this;
  }
}

export class ProductCategory {
  constructor(name) {
    cy.log(`Attribute - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`Attribute - set name = ${name}`);
    this.name = name;
    return this;
  }
}
