export class Taxcategory {
  constructor(name) {
    this.name = name;
  }

  setName(name) {
    cy.log(`Taxcategory - set name = ${name}`);
    this.name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`Taxcategory - set description = ${description}`);
    this.description = description;
    return this;
  }

  setVATType(vatType) {
    cy.log(`Taxcategory - set vatType = ${vatType}`);
    this.vatType = vatType;
    return this;
  }

  setActive(isActive) {
    cy.log(`Taxcategory - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }

  setID(id) {
    cy.log(`Taxcategory - set ID = ${id}`);
    this.id = id;
    return this;
  }

  apply() {
    cy.log(`Taxrate - apply - START (name=${this.name})`);
    applyTaxcategory(this);
    cy.log(`Taxrate - apply - END (name=${this.name})`);
    return this;
  }

  activate() {
    cy.log(`Taxrate - activate - START (name=${this.name})`);
    applyActive(this);
    cy.log(`Taxrate - activate - END (name=${this.name})`);
    return this;
  }
}

function applyTaxcategory(taxcat) {
  //const timestamp = new Date().getTime();

  describe(`Create new Taxcategory ${taxcat.name}`, function() {
    cy.visitWindow('138', 'NEW', 'taxCatObj');
    //cy.log(`Taxcategory - Name = ${taxcat.Name} ${timestamp}`);
    cy.log(`Taxcategory - Name = ${taxcat.name}`);
    //cy.writeIntoStringField('Name', `${taxcat.Name} ${timestamp}`);
    cy.writeIntoStringField('Name', `${taxcat.name}`);
    cy.log(`Taxcategory - Description = ${taxcat.description}`);
    cy.writeIntoStringField('Description', `${taxcat.description}`, false, null, true);
    cy.log(`Taxcat - VATType = ${taxcat.vatType}`);
    cy.selectInListField('VATType', taxcat.vatType);
    return this;
  });
}

function applyActive(taxcat) {
  describe(`Create new Taxrate ${taxcat.name}`, function() {
    if (this.ID) {
      cy.visitWindow('138', `${this.ID}`);
    }
    cy.isChecked('IsActive').then(isActive => {
      if (!isActive) {
        cy.clickOnIsActive();
      }
    });
    cy.log(`Taxcategory - activate ${taxcat.name} END`);
    return this;
  });
}
