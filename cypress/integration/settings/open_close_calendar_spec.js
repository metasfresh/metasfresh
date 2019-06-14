describe('Filter calendar periods', function() {
  it('open page', function() {
    cy.visit('/window/540349');
  });

  it('press filter button', function() {
    cy.get('div')
      .should('have.class', 'filter-wrapper')
      .get('button')
      .contains('Filter')
      .click({ force: true })
      .get('div.filters-overlay')
      .get('ul.filter-menu')
      .get('li.filter-option-default')
      .click({ force: true });
  });

  it('change year', function() {
    cy.selectInListField('C_Year_ID', '2018', false, null, true);
  });
  it('write name', function() {
    cy.writeIntoStringField('Name', 'Dez-18', false, null, true);
  });

  it('Apply filter', function() {
    cy.clickButtonWithText('Apply');
  });

  it('enter in item', function() {
    cy.wait(1000);
    cy.get('.Text > :nth-child(1) > .cell-text-wrapper')
      .contains('Dez-18')
      .dblclick();
  });
});

describe('Actions on selected item', function() {
  it('open/close action - close periods', function() {
    cy.executeHeaderAction('C_Period_Process');
    cy.selectInListField('PeriodAction', 'Close Period', false, null, true);
    cy.pressStartButton();
    cy.wait(500);
  });

  it('open/close action - open periods', function() {
    cy.executeHeaderAction('C_Period_Process');
    cy.wait(700);
    cy.pressStartButton();
  });
});
