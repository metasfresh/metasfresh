import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';

describe('Filter calendar periods', function() {
  before(function() {
    cy.visitWindow('540349');
  });

  it('Filter for Dez-18', function() {
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

    cy.performDocumentViewAction('540349', function() {
      cy.get('.Text > :nth-child(1) > .cell-text-wrapper')
        .contains('Dez-18')
        .dblclick('540349');
    });
  });
});

describe('Actions on selected item', function() {
  it('open/close action - close periods', function() {
    cy.executeHeaderActionWithDialog('C_Period_Process');
    cy.selectInListField('PeriodAction', 'Close Period', true /*modal*/, '/rest/api/process/');
    cy.pressStartButton();
  });

  it('open/close action - open periods', function() {
    cy.executeHeaderActionWithDialog('C_Period_Process');
    cy.pressStartButton();
  });
});
