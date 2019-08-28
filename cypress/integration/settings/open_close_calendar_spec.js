import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';

let year;
let period;
let closePeriod;

it('Read fixture and prepare the names', function() {
  cy.fixture('settings/open_close_calendar_spec.json').then(f => {
    year = f['year'];
    period = f['period'];
    closePeriod = f['closePeriod'];
  });
});
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
    cy.selectInListField('C_Year_ID', year, false, null, true);
  });
  it('write name', function() {
    cy.writeIntoStringField('Name', period, false, null, true);
  });

  it('Apply filter', function() {
    cy.clickButtonWithText('Apply');
  });

  it('enter in item', function() {
    cy.wait(1000);

    cy.performDocumentViewAction('540349', function() {
      cy.get('.Text > :nth-child(1) > .cell-text-wrapper')
        .contains(period)
        .dblclick('540349');
    });
  });
});

describe('Actions on selected item', function() {
  it('open/close action - close periods', function() {
    cy.executeHeaderActionWithDialog('C_Period_Process');
    cy.selectInListField('PeriodAction', closePeriod, true);
    cy.pressStartButton();
  });

  it('open/close action - open periods', function() {
    cy.executeHeaderActionWithDialog('C_Period_Process');
    cy.pressStartButton();
  });
});
