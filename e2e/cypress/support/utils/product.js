'use strict';

import { getLanguageSpecific } from './utils';

export class Product {
  constructor() {
    this.attributeValues = [];
    this.productPrices = [];
    this.packingInstructions = [];
  }

  setName(name) {
    cy.log(`Product - set name = ${name}`);
    this.name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`Product - set description = ${description}`);
    this.description = description;
    return this;
  }

  setStocked(isStocked) {
    cy.log(`Product - set isStocked = ${isStocked}`);
    this.isStocked = isStocked;
    return this;
  }

  setPurchased(isPurchased) {
    cy.log(`Product - set isPurchased = ${isPurchased}`);
    this.isPurchased = isPurchased;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setSold(isSold) {
    cy.log(`Product - set isSold = ${isSold}`);
    this.isSold = isSold;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setDiverse(isDiverse) {
    cy.log(`Product - set isDiverse = ${isDiverse}`);
    this.isDiverse = isDiverse;
    return this;
  }

  setProductCategory(productCategory) {
    cy.log(`Product - set productCategory = ${productCategory}`);
    this.productCategory = productCategory;
    return this;
  }

  setProductType(productType) {
    cy.log(`Product - set productType = ${productType}`);
    this.productType = productType;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setUOM(c_uom) {
    cy.log(`Product - set c_uom = ${c_uom}`);
    this.c_uom = c_uom;
    return this;
  }

  addProductPrice(productPrice) {
    cy.log(`Product - productPrice = ${JSON.stringify(productPrice)}`);
    this.productPrices.push(productPrice);
    return this;
  }

  addCUTUAllocation(packingInstruction) {
    cy.log(`Product - add packingInstruction = ${packingInstruction}`);
    this.packingInstructions.push(packingInstruction);
    return this;
  }

  setBusinessPartner(businessPartner) {
    cy.log(`Product - set businessPartner = ${businessPartner}`);
    this.businessPartner = businessPartner;
    return this;
  }

  apply() {
    cy.log(`Product - apply - START (name=${this.name})`);
    Product.applyProduct(this);
    cy.log(`Product - apply - END (name=${this.name})`);
    return this;
  }

  static applyProduct(product) {
    cy.log(`Create new Product ${product.name}`);
    cy.visitWindow('140', 'NEW');

    cy.writeIntoStringField('Name', product.name);

    cy.setCheckBoxValue('IsStocked', product.isStocked);
    cy.setCheckBoxValue('IsPurchased', product.isPurchased);
    cy.setCheckBoxValue('IsSold', product.isSold);
    cy.setCheckBoxValue('IsDiverse', product.isDiverse);

    cy.getStringFieldValue('ProductType').then(productTypeValue => {
      const productType = getLanguageSpecific(product, 'productType');

      if (productType !== productTypeValue) {
        cy.resetListValue('ProductType');
        cy.selectInListField('ProductType', productType);
      }
    });

    cy.selectInListField('M_Product_Category_ID', product.productCategory);

    cy.writeIntoStringField('Description', product.description);

    cy.getStringFieldValue('C_UOM_ID').then(uomValue => {
      const c_uom = getLanguageSpecific(product, 'c_uom');

      if (c_uom && c_uom !== uomValue) {
        cy.selectInListField('C_UOM_ID', c_uom);
      }
    });

    product.packingInstructions.forEach(pi => {
      cy.selectTab('M_HU_PI_Item_Product');
      cy.pressAddNewButton();
      cy.selectInListField('M_HU_PI_Item_ID', pi, true);
      cy.writeIntoStringField('Qty', 10, true, null, true);
      cy.selectDateViaPicker('ValidFrom');
      cy.pressDoneButton();
    });
    cy.expectNumberOfRows(product.packingInstructions.length);

    if (product.businessPartner != null) {
      cy.selectTab('C_BPartner_Product');
      cy.pressAddNewButton();
      cy.writeIntoLookupListField('C_BPartner_ID', product.businessPartner, product.businessPartner, true);
      cy.clickOnCheckBox('IsCurrentVendor');
      cy.pressDoneButton();
    }

    if (product.productPrices.length > 0) {
      product.productPrices.forEach(function(pp) {
        Product.applyProductPrice(pp);
      });
      cy.get('table tbody tr').should('have.length', product.productPrices.length);
    }
  }

  /**
   * See complaint at ProductPrice class.
   */
  static applyProductPrice(productPrice) {
    cy.selectTab('M_ProductPrice');
    cy.pressAddNewButton();

    cy.selectInListField('M_PriceList_Version_ID', productPrice.priceList, true);
    cy.writeIntoStringField('PriceList', productPrice.listPriceAmount, true, null, true);
    cy.writeIntoStringField('PriceStd', productPrice.standardPriceAmount, true /*modal*/, null /*rewriteUrl*/, true /*noRequest*/);
    cy.writeIntoStringField('PriceLimit', productPrice.limitPriceAmount, true, null, true);

    // don't set TaxCategory if there's a default one already selected
    cy.get('.form-field-C_TaxCategory_ID input')
      .invoke('val')
      .then((val) => {
        if (!val) {
          const taxCategory = getLanguageSpecific(productPrice, 'taxCategory');
          cy.selectInListField('C_TaxCategory_ID', taxCategory, true);
        }
      });

    cy.pressDoneButton();
  }
}

export class ProductCategory {
  setName(name) {
    cy.log(`Product Category - set name = ${name}`);
    this.name = name;
    return this;
  }

  setAttributeSet(attributeSet) {
    cy.log(`Product Category - set attributeSet = ${attributeSet}`);
    this.attributeSet = attributeSet;
    return this;
  }

  apply() {
    cy.log(`Product Category - apply - START (name=${this.name})`);
    ProductCategory.applyProductCategory(this);
    cy.log(`Product Category - apply - END (name=${this.name})`);
  }

  static applyProductCategory(productCategory) {
    cy.log(`Create new Product ${productCategory.name}`);
    cy.visitWindow('144', 'NEW');
    cy.writeIntoStringField('Name', productCategory.name);

    // cy.writeIntoStringField('Value', productCategory.name + '_value');
    if (productCategory.attributeSet) {
      cy.selectInListField('M_AttributeSet_ID', productCategory.attributeSet);
    }
  }
}

/**
 * i dont like this, but it seems when i search for a price list version, the search does NOT use PriceListVersion.Name,
 * but instead a combination of PriceList.Name and PriceListVersion.FromDate.
 * Why? IDK!
 *
 * FIXPLS
 */
export class ProductPrice {
  setPriceList(priceList) {
    cy.log(`ProductPrice priceList - set = ${priceList}`);
    this.priceList = priceList;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setListPriceAmount(listPriceAmount) {
    cy.log(`ProductPrice listPriceAmount - set = ${listPriceAmount}`);
    this.listPriceAmount = listPriceAmount;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setStandardPriceAmount(standardPriceAmount) {
    cy.log(`ProductPrice standardPriceAmount - set = ${standardPriceAmount}`);
    this.standardPriceAmount = standardPriceAmount;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setLimitPriceAmount(limitPriceAmount) {
    cy.log(`ProductPrice limitPriceAmount - set = ${limitPriceAmount}`);
    this.limitPriceAmount = limitPriceAmount;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setTaxCategory(taxCategory) {
    cy.log(`ProductPrice taxCategory - set = ${taxCategory}`);
    this.taxCategory = taxCategory;
    return this;
  }
}
