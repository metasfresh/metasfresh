export class DunningType {
  constructor(name, checkDefault) {
    this.name = name;
    this.checkDefault = checkDefault;
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

  apply() {
    cy.log(`DunningType - apply - START (name=${this.name})`);
    applyDunningType(this);
    cy.log(`DunningType - apply - END (name=${this.name})`);
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

    dunningTypeEntryLine(1, `${timestamp}`);
    dunningTypeEntryLine(2, `${timestamp}`);
  });
}

const dunningTypeEntryLine = (arg, timestamp) => {
  cy.pressAddNewButton();

  cy.writeIntoStringField('Name', 'Level ' + arg + timestamp, true);
  cy.writeIntoStringField('PrintName', 'Text ' + arg + timestamp, true);
  cy.writeIntoStringField('DaysBetweenDunning', '01', true);
  cy.writeIntoTextField('NoteHeader', 'Header ' + arg + timestamp, true);
  cy.writeIntoTextField('Note', 'Note ' + arg + timestamp, true);

  cy.pressDoneButton();
};
