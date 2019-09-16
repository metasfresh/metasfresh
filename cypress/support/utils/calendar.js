export class Calendar {
  constructor() {
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
}

function applyCalendar(calendar) {
  cy.visitWindow('117', 'NEW');
  cy.writeIntoStringField('Name', calendar.name);

  // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
  calendar.years.forEach(function(year) {
    applyYear(year);
  });
  cy.expectNumberOfRows(calendar.years.length);
}

function applyYear(year) {
  const yearName = year.name;

  cy.selectTab('C_Year');
  cy.pressAddNewButton();
  cy.writeIntoStringField('FiscalYear', yearName, true /*modal*/);
  cy.pressDoneButton();

  cy.selectRowByColumnAndValue({ column: 'FiscalYear', value: yearName });
  cy.executeHeaderActionWithDialog('C_Year_Create_Periods');
  cy.pressStartButton();
}
