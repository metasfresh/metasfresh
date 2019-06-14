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
    // cy.selectInListField('C_Year_ID', '2018', true);
    cy.get('.form-field-C_Year_ID > :nth-child(2) > .input-body-container > .input-dropdown-container').click();
    cy.get('div.input-dropdown-list')
      .get('div.input-dropdown-list-option.ignore-react-onclickoutside')
      .contains('2018')
      .click();
  });
  it('write name', function() {
    // cy.writeIntoStringField('Name', 'Dez-18', true);
    cy.get('.input-body-container > .input-block > .input-field')
      .click()
      .type('Dez-18');
  });

  it('Apply filter', function() {
    cy.clickButtonWithText("Apply");
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
    cy.get('.header-left-side > .btn-square').click();
    cy.get('#headerAction_C_Period_Process').click();
    cy.get(
      '.window-wrapper > :nth-child(1) > .form-group > .col-sm-9 > .input-body-container > .input-dropdown-container > .input-dropdown > .input-editable > .input-field'
    ).click();
    cy.get('[data-test-id="CClose Period"]').click();
    cy.get('.items-row-2 > :nth-child(2)').click();
  });

  it('open/close action - open periods', function() {
    cy.get('.header-left-side > .btn-square').click();
    cy.get('#headerAction_C_Period_Process').click();
    cy.get('.items-row-2 > :nth-child(2)').click();
  });
});
