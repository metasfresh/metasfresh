export class Calendar {
  constructor(name) {
    cy.log(`Calendar - set name = ${name};`);
    this.name = name;
    this.years = [];
  }

  setName(name) {
    cy.log(`Calendar - set name = ${name}`);
    this.name = name;
    return this;
  }

  addYear(year) {
    cy.log(`Calendar - add year = ${JSON.stringify(year)}`);
    this.years.push(year);
    return this;
  }

  apply() {
    cy.log(`Calendar - apply - START (name=${this.name})`);
    applyCalendar(this);
    cy.log(`Calendar - apply - END (name=${this.name})`);
    return this;
  }
}

export class Year {
  constructor(name) {
    cy.log(`Year - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`Year - set name = ${name}`);
    this.name = name;
    return this;
  }
}

function applyCalendar(calendar) {
  describe(`Create new calendar ${calendar.name}`, function() {
    cy.visitWindow('117', 'NEW');
    cy.writeIntoStringField('Name', calendar.name);

    // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
    if (calendar.years.length > 0) {
      cy.selectTab('C_Year');
      calendar.years.forEach(function(year) {
        applyYear(year);
      });

      cy.get('table tbody tr').should('have.length', calendar.years.length);
    }
  });
}

function applyYear(year) {
  cy.pressAddNewButton();
  cy.writeIntoStringField('FiscalYear', year.name, true /*modal*/);
  cy.pressDoneButton();
}
