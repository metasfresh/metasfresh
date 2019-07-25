export class DunningType {
  constructor(name, isDefault) {
    this.name = name;
    this.isDefault = isDefault;
    this.entryLine = [];
  }

  setName(name) {
    cy.log(`DunningType - set name = ${name}`);
    this.name = name;
    return this;
  }

  setIsDefault(isDefault) {
    cy.log(`DunningType - check default = ${isDefault}`);
    this.isDefault = isDefault;
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

  setCurrency(currency) {
    cy.log(`DunningType - set currency = ${currency}`);
    this.currency = currency;
    return this;
  }
}

function applyDunningType(dunningType) {

  describe(`Create new Dunning Type ${dunningType.name}`, function () {
    cy.visitWindow('159', 'NEW');

    cy.writeIntoStringField('Name', `${dunningType.name}`);
    cy.setCheckBoxValue('IsDefault', dunningType.isDefault);

    cy.openAdvancedEdit();
    cy.selectInListField('C_Currency_ID', dunningType.currency, true);
    cy.pressDoneButton();

    if (dunningType.entryLine.length > 0) {
      dunningType.entryLine.forEach(function (dunningEntryLine) {
        applyDunningTypeEntryLine(dunningEntryLine);
      });
      cy.get('table tbody tr').should('have.length', dunningType.entryLine.length);
    }
  });
}

const applyDunningTypeEntryLine = (dunningEntry) => {
  cy.pressAddNewButton();

  cy.writeIntoStringField('Name', dunningEntry.name, true);
  cy.writeIntoStringField('PrintName', dunningEntry.printName, true);
  cy.writeIntoStringField('DaysBetweenDunning', dunningEntry.days, true);
  cy.writeIntoTextField('NoteHeader', dunningEntry.header, true);
  cy.writeIntoTextField('Note', dunningEntry.note, true);

  cy.pressDoneButton();
};
