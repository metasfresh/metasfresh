const toggleFrequentFilters = () => {
  cy.clickElementWithClass('.filters-frequent .toggle-filters', true);
  cy.get('.filter-widget').should('exist');
};

const toggleNotFrequentFilters = () => {
  cy.clickElementWithClass('.filters-not-frequent .toggle-filters', true);
  cy.get('.filter-menu').should('exist');
};

const selectFrequentFilterWidget = isDate => {
  if (isDate) {
    cy.clickElementWithClass(`.meta-icon-calendar.input-icon-right`, true);
  }
  return cy.get('.filters-frequent .filter-widget');
};

const selectNotFrequentFilterWidget = filterId => {
  cy.clickElementWithClass(`.filter-menu .filter-option-${filterId}`, true);
  return cy.get('.filters-not-frequent .filter-widget');
};

const applyFilters = () => {
  cy.clickElementWithClass('.filter-btn-wrapper .applyBtn', true);
  cy.wait(1000); // give time to apply the filters
  cy.waitForSaveIndicator();
};

const confirmCalendarDay = () => {
  cy.get('.rdtPicker')
    .find('.rdtToday')
    .click();
};

const clearNotFrequentFilters = () => {
  cy.get('.filters-not-frequent')
    .click()
    .find('.filter-clear')
    .click();
  cy.waitForSaveIndicator(true);
}

export {
  toggleFrequentFilters,
  toggleNotFrequentFilters,
  selectFrequentFilterWidget,
  selectNotFrequentFilterWidget,
  applyFilters,
  confirmCalendarDay,
  clearNotFrequentFilters,
};
