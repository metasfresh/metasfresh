export class TaxCategory {
  setName(name) {
    cy.log(`TaxCategory - set name = ${name}`);
    this.name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`TaxCategory - set description = ${description}`);
    this.description = description;
    return this;
  }

  setVATType(vatType) {
    cy.log(`TaxCategory - set vatType = ${vatType}`);
    this.vatType = vatType;
    return this;
  }

  setID(id) {
    cy.log(`TaxCategory - set ID = ${id}`);
    this.id = id;
    return this;
  }

  apply() {
    cy.log(`TaxCategory - apply - START (name=${this.name})`);
    applyTaxCategory(this);
    cy.log(`TaxCategory - apply - END (name=${this.name})`);
    return this;
  }
}

function applyTaxCategory(taxCat) {
  cy.visitWindow('138', 'NEW');
  cy.writeIntoStringField('Name', taxCat.name);
  cy.writeIntoStringField('Description', taxCat.description);
  cy.selectInListField('VATType', taxCat.vatType);
}
