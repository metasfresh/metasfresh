export class DunningType {
  constructor(name, checkDefault) {
    this.name = name;
    this.checkDefault = checkDefault;
    this.entryLine = [];
  }

  setName(name) {
    cy.log(`DunningType - set name = ${name}`);
    this.name = name;
    return this;
  }

  setCheckDefault(checkDefault) {
    cy.log(`DunningType - check default = ${checkDefault}`);
    this.checkDefault = checkDefault;
    return this;
  }

  addEntryLine(entry) {
    cy.log(`BPartner - add contact = ${JSON.stringify(entry)}`);
    this.entryLine.push(entry);
    return this;
  }

  apply() {
    cy.log(`DunningType - apply - START (name=${this.name})`);
    applyDunningType(this);
    cy.log(`DunningType - apply - END (name=${this.name})`);
    return this;
  }
}

export class DunningTypeEntryLine {
  contructor(name) {
    this.name = name;
    this.printName = undefined;
    this.days = undefined;
    this.header = undefined;
    this.note = undefined;
  }

  setName(name) {
    cy.log(`DunningType - set name = ${name}`);
    this.name = name;
    return this;
  }

  setPrintName(printName) {
    cy.log(`DunningType - set printName = ${printName}`);
    this.printName = printName;
    return this;
  }

  setDays(days) {
    cy.log(`DunningType - set days = ${days}`);
    this.days = days;
    return this;
  }

  setHeader(header) {
    cy.log(`DunningType - set header = ${header}`);
    this.header = header;
    return this;
  }

  setNote(note) {
    cy.log(`DunningType - set note = ${note}`);
    this.note = note;
    return this;
  }
}

function applyDunningType(dunningType) {
  const timestamp = new Date().getTime();

  describe(`Create new Dunning Type ${dunningType.name}`, function() {
    cy.visit('/window/159');
    cy.waitForHeader('Finance', 'Settings');
    cy.clickHeaderNav(Cypress.messages.window.new.caption);

    cy.writeIntoStringField('Name', `${dunningType.name}`);
    if (dunningType.checkDefault) {
      cy.clickOnCheckBox(`${dunningType.checkDefault}`);
    }

    if (dunningType.entryLine.length > 0) {
      dunningType.entryLine.forEach(function(dunningEntryLine) {
        applyDunningTypeEntryLine(dunningEntryLine, timestamp);
      });
      cy.get('table tbody tr').should('have.length', dunningType.entryLine.length);
    }
  });
}

const applyDunningTypeEntryLine = (dunningEntry, timestamp) => {
  cy.pressAddNewButton();

  cy.writeIntoStringField('Name', dunningEntry.name + timestamp, true);
  cy.writeIntoStringField('PrintName', dunningEntry.printName + timestamp, true);
  cy.writeIntoStringField('DaysBetweenDunning', dunningEntry.days, true);
  cy.writeIntoTextField('NoteHeader', dunningEntry.header + timestamp, true);
  cy.writeIntoTextField('Note', dunningEntry.note + timestamp, true);

  cy.pressDoneButton();
};
