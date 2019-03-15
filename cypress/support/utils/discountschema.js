export class DiscountSchema {
  constructor(name) {
    cy.log(`DiscountSchemaBuilder - set name = ${name}`);
    this.name = name;
    this.validFrom = '01/01/2019';
    this.discountBreaks = [];
  }

  apply() {
    cy.log(`DiscountSchema - apply - START (name=${this.name})`);
    applyDiscountSchema(this);
    cy.log(`DiscountSchema - apply - END (name=${this.name})`);
    return this;
  }

  setValidFrom(validFrom) {
    cy.log(`DiscountSchemaBuilder - set validFrom = ${validFrom}`);
    this.validFrom = validFrom;
    return this;
  }

  addDiscountBreak(discountBreak) {
    cy.log(`DiscountSchemaBuilder - add discountBreak = ${discountBreak}`);
    this.discountBreaks.push(discountBreak);
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
  describe(`Create new dicscount schema ${discountSchema.name}`, function () {
    cy.visit('/window/233/NEW');
    cy.selectInListField('DiscountType', 'Breaks');
    cy.writeIntoStringField('Name', discountSchema.name);
    cy.writeIntoStringField('ValidFrom', `${discountSchema.validFrom}{enter}`);

    // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
    if (discountSchema.discountBreaks.length > 0) {
      discountSchema.discountBreaks.forEach(function (discountBreak) {
        applyDiscountBreak(discountBreak);
      });
      cy.get('table tbody tr').should('have.length', discountSchema.discountBreaks.length);
    }
  });
}

function applyDiscountBreak(discountBreak) {
  cy.selectTab('M_DiscountSchemaBreak');
  cy.pressAddNewButton();

  cy.writeIntoStringField('BreakValue', discountBreak.breakValue);
  cy.writeIntoStringField('BreakDiscount', discountBreak.breakDiscount);
  cy.selectInListField('PriceBase', 'F');
  cy.pressDoneButton();
}
