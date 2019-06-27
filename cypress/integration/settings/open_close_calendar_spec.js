import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';

describe('Filter calendar periods', function() {
  before(function() {
    cy.visit('/window/540349');
  });

  it('press filter button', function() {
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    applyFilters();
  });

  it('change year', function() {
    cy.selectInListField('C_Year_ID', '2018', false /*modal*/, null, true /*skipRequest*/);
  });
  it('write name', function() {
    cy.writeIntoStringField('Name', 'Dez-18', false, null, true /*skipRequest*/);
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
    cy.selectInListField('PeriodAction', 'Close Period', true /*modal*/, '/rest/api/process/');
    cy.pressStartButton();
    cy.wait(500);
  });

  it('open/close action - open periods', function() {
    cy.executeHeaderAction('C_Period_Process');
    cy.wait(700);
    cy.pressStartButton();
  });
});
