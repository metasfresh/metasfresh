const dunningEntryLine = arg => {
  cy.pressAddNewButton();

  cy.writeIntoStringField('Name', 'Level ' + arg, true);
  cy.writeIntoStringField('PrintName', 'Text ' + arg, true);
  cy.writeIntoStringField('DaysBetweenDunning', '01', true);
  cy.writeIntoStringField('NoteHeader', 'Header ' + arg, true);
  cy.writeIntoStringField('Note', 'Note ' + arg, true);

  cy.pressDoneButton();
};

describe('create dunning type', function() {
  // before(function() {
  //   cy.visit('/window/159');
  // });

  // if creating more than 1 default dunning type will give an error

  //   it('config dunning', function() {
  //     cy.clickHeaderNav(Cypress.messages.window.new.caption);

  //     cy.writeIntoStringField('Name', 'test Dunning');
  //     cy.clickOnCheckBox('IsDefault');
  //   });

  //   it('first dunning entry', function() {
  //     dunningEntryLine(1);
  //   });

  //   it('second dunning entry', function() {
  //     dunningEntryLine(2);
  //   });

  it('operations on BP', function() {
    cy.visit('/window/123');

    cy.get(':nth-child(1) > .text-left.td-sm').dblclick();
    cy.selectTab('Customer');
    cy.get('.tr-even > :nth-child(3)')
      .dblclick()
      .selectInListField('C_PaymentTerm_ID', 'immediately', false, null, true);
    cy.selectTab('Customer');
    cy.get('.tab-pane > :nth-child(1) > :nth-child(1) > :nth-child(1) > .panel').scrollTo('right');
    cy.get('.tr-even > :nth-child(9)')
      .dblclick()
      .selectInListField('C_Dunning_ID', 'test Dunning', false, null, true);
  });
});
