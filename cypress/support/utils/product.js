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

  setValue(value) {
    cy.log(`Product - set value = ${value}`);
    this.value = value;
    return this;
  }

  setDescription(description) {
    cy.log(`Product - set description = ${description}`);
    this.description = description;
    return this;
  }

  setStocked(isPurchased) {
    cy.log(`Product - set isPurchased = ${isPurchased}`);
    this.isPurchased = isPurchased;
    return this;
  }

  setSold(isSold) {
    cy.log(`Product - set isSold = ${isSold}`);
    this.isSold = isSold;
    return this;
  }

  setDiverse(isDiverse) {
    cy.log(`Product - set isDiverse = ${isDiverse}`);
    this.isDiverse = isDiverse;
    return this;
  }

  setProductType(c_uom_id) {
    cy.log(`Product - set UOM = ${c_uom_id}`);
    this.c_uom_id = c_uom_id;
    return this;
  }

  setProductCategory(m_product_category_id) {
    cy.log(`Product - set Product Category = ${m_product_category_id}`);
    this.m_product_category_id = m_product_category_id;
    return this;
  }

  apply() {
    cy.log(`Product - apply - START (name=${this.name})`);
    applyProduct(this);
    cy.log(`Product - apply - END (name=${this.name})`);
    return this;
  }
}

function applyProduct(product) {
  describe(`Create new Product ${product.name}`, function() {
    // debugger;
    cy.visitWindow('140', 'NEW');
    cy.writeIntoStringField('Name', product.name);

    // Value is readonly
    //cy.clearField('Value');
    //cy.writeIntoStringField('Value', product.value);

    cy.selectInListField('M_Product_Category_ID', product.m_product_category_id);

    cy.writeIntoStringField('Description', product.description);

    if (product.isStocked && !cy.isChecked('IsStocked')) {
      cy.clickOnCheckBox('IsStocked');
    }
    if (product.isPurchased && !cy.isChecked('IsPurchased')) {
      cy.clickOnCheckBox('IsPurchased');
    }
    if (product.isSold && !cy.isChecked('IsSold')) {
      cy.clickOnCheckBox('IsSold');
    }
    if (product.isDiverse && cy.isChecked('IsDiverse')) {
      cy.clickOnCheckBox('IsDiverse');
    }
  });
}

export class ProductCategory {
  constructor(name) {
    cy.log(`Product Category - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`Product Category - set name = ${name}`);
    this.name = name;
    return this;
  }

  setValue(value) {
    cy.log(`Product Category - set value = ${value}`);
    this.value = value;
    return this;
  }

  apply() {
    cy.log(`Product Category - apply - START (name=${this.name})`);
    applyProductCategory(this);
    cy.log(`Product Category - apply - END (name=${this.name})`);
    return this;
  }
}

function applyProductCategory(productCategory) {
  describe(`Create new Product ${productCategory.name}`, function() {
    cy.visitWindow('144', 'NEW');
    cy.writeIntoStringField('Name', productCategory.name);

    // Value is updateable
    cy.clearField('Value');
    cy.writeIntoStringField('Value', productCategory.value);
  });
}
