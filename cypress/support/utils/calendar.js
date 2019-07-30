export class Calendar {
  constructor(baseName) {
    cy.log(`Calendar - set baseName = ${baseName};`);
    this.baseName = baseName;
    this.timestamp = new Date().getTime();

    this.years = [];
  }

  setName(baseName) {
    cy.log(`Calendar - set baseName = ${baseName}`);
    this.baseName = baseName;
    return this;
  }

  setTimestamp(timestamp) {
    cy.log(`Calendar - set timestamp = ${timestamp}`);
    this.timestamp = timestamp;
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
  constructor(baseName) {
    cy.log(`Year - set baseName = ${baseName}`);
    this.baseName = baseName;
  }

  setName(baseName) {
    cy.log(`Year - set baseName = ${baseName}`);
    this.baseName = baseName;
    return this;
  }
}

function applyCalendar(calendar) {
  describe(`Create new calendar ${calendar.baseName}`, function() {
    cy.visitWindow('117', 'NEW');
    cy.writeIntoStringField('Name', `${calendar.baseName} ${calendar.timestamp}`);

    // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
    if (calendar.years.length > 0) {
      cy.selectTab('C_Year');
      calendar.years.forEach(function(year) {
        applyYear(year, calendar);
      });

      cy.get('table tbody tr').should('have.length', calendar.years.length);
    }

    //cy.log(JSON.stringify(calendar)); // uncomment if you want to get a fixture
  });
}

function applyYear(year, calendar) {
  const yearName = `${year.baseName} ${calendar.timestamp}`;

  cy.pressAddNewButton();
  cy.writeIntoStringField('FiscalYear', yearName, true /*modal*/);
  cy.pressDoneButton();

  cy.get('.table-flex-wrapper')
    .find('tbody tr')
    .first()
    .find('td')
    .first()
    .should('exist')
    .click({ force: true });

  cy.executeHeaderActionWithDialog('C_Year_Create_Periods');
  cy.pressStartButton();
}
