export class DiscountSchema {
  constructor(name) {
    cy.log(`DiscountSchemaBuilder - set name = ${name}`);
    this.name = name;
    this.validFrom = '01/01/2019';
    this.discountBreaks = [];
  }

  setValidFrom(validFrom) {
    cy.log(`DiscountSchemaBuilder - set validFrom = ${validFrom}`);
    this.validFrom = validFrom;
    return this;
  }

  setName(name) {
    cy.log(`DiscountSchemaBuilder - set name = ${name}`);
    this.name = name;
    return this;
  }

  addDiscountBreak(discountBreak) {
    cy.log(`DiscountSchemaBuilder - add discountBreak = ${discountBreak}`);
    this.discountBreaks.push(discountBreak);
    return this;
  }

  apply() {
    cy.log(`DiscountSchema - apply - START (name=${this.name})`);
    applyDiscountSchema(this);
    cy.log(`DiscountSchema - apply - END (name=${this.name})`);
    return this;
  }
}

export class DiscountBreak {
  constructor() {
    cy.log(`DiscountBreak - constructor`);
    this.breakValue = '0';
    this.breakDiscount = '0';
  }

  setBreakValue(breakValue) {
    cy.log(`DiscountBreakBuilder - set breakValue = ${breakValue}`);
    this.breakValue = breakValue;
    return this;
  }

  setBreakDiscount(breakDiscount) {
    cy.log(`DiscountSchemaBuilder - set breakDiscount = ${breakDiscount}`);
    this.breakDiscount = breakDiscount;
    return this;
  }
}

function applyDiscountSchema(discountSchema) {
  describe(`Create new discount schema ${discountSchema.name}`, function() {
    cy.visitWindow(233, 'NEW');
    cy.writeIntoStringField('Name', discountSchema.name);

    cy.writeIntoStringField(
      'ValidFrom',
      discountSchema.validFrom,
      false /*modal*/,
      null /*rewriteUrl*/,
      true /*noRequest*/
    );
    cy.selectInListField('DiscountType', 'Breaks');

    // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
    if (discountSchema.discountBreaks.length > 0) {
      discountSchema.discountBreaks.forEach(function(discountBreak) {
        applyDiscountBreak(discountBreak);
      });
      cy.expectNumberOfRows(discountSchema.discountBreaks.length);
    }
  });
}

function applyDiscountBreak(discountBreak) {
  cy.selectTab('M_DiscountSchemaBreak');
  cy.pressAddNewButton();

  // we want neither a fixed nor a pricelist based price
  cy.resetListValue('PriceBase', true);

  cy.getStringFieldValue('BreakValue', true /*modal*/).then(breakValueFieldValue => {
    if (discountBreak.breakValue && breakValueFieldValue != discountBreak.breakValue) {
      cy.writeIntoStringField('BreakValue', discountBreak.breakValue, true /*modal*/);
    }
  });
  cy.getStringFieldValue('BreakDiscount', true /*modal*/).then(breakDiscountFieldValue => {
    if (discountBreak.breakDiscount && breakDiscountFieldValue != discountBreak.breakDiscount) {
      cy.writeIntoStringField('BreakDiscount', discountBreak.breakDiscount);
    }
  });

  cy.pressDoneButton();
}
