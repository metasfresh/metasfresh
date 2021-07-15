import { humanReadableNow } from '../utils/utils';
import { checkIfWindowCanExecuteActions } from './commands_utils';
import { RewriteURL } from '../utils/constants';
/*
 * Basic command for clicking a button element having a certain text
 * @param text string to search for in the button
 */
Cypress.Commands.add('clickButtonWithText', text => {
  cy.get('button')
    .contains(text)
    .should('exist')
    .click();
});

/*
 * Basic command for clicking an element with certain selector
 * @param selector string used to query for the element
 */
Cypress.Commands.add('clickElementWithClass', (selector, force) => {
  const opts = {};

  if (force) {
    opts.force = true;
  }

  cy.get(selector)
    .should('exist')
    .click({ ...opts });
});

Cypress.Commands.add('selectTab', (tabName, force) => {
  const opts = {};

  if (force) {
    opts.force = true;
  }

  return cy.get(`#tab_${tabName}`).click(opts);
});

Cypress.Commands.add('selectSingleTabRow', () => {
  cy.get('.table-flex-wrapper')
    .find('tbody tr')
    .should('exist')
    .click({ force: true });
});

Cypress.Commands.add('openReferencedDocuments', (referenceId, retriesLeft = 8) => {
  // retry 8 times to open the referenced document
  // const date = humanReadableNow();
  const timeout = { timeout: 20000 };
  checkIfWindowCanExecuteActions();

  if (retriesLeft >= 1) {
    // const referencesAliasName = `references-${date}`;
    // cy.server();
    // -- removed this as calls to SSE (eventSource) are not recorded, they were introduced in me03/issues -> #4383
    // cy.route('GET', new RegExp(RewriteURL.REFERENCES)).as(referencesAliasName);

    cy.get('body').type('{alt}6'); // open referenced docs
    // cy.wait(`@${referencesAliasName}`, timeout);  -- this has been also removed due to the reason mentioned above
    cy.get('.order-list-panel .order-list-loader', timeout).should('not.exist');

    return cy.get('body').then(body => {
      if (body.find(`[data-cy="reference-${referenceId}"]`).length > 0) {
        return cy.get(`[data-cy="reference-${referenceId}"]`).click();
      } else {
        cy.wait(1000);
        cy.get('body').type('{alt}5'); // close referenced docs by switching to something else
        return cy.openReferencedDocuments(referenceId, retriesLeft - 1);
      }
    });
  }
  cy.get('body').type('{alt}6'); // open referenced docs
  return cy.get(`[data-cy="reference-${referenceId}"]`).click(); // one more time just because we need to throw the error
});

/**
 * Select the nth row in a list. Starts from 0.
 *
 * @param rowNumber - the row number
 */
Cypress.Commands.add('selectNthRow', (rowNumber, modal = false, force = false) => {
  let path = '.table-flex-wrapper';

  if (modal) {
    path = '.modal-content-wrapper ' + path;
  }

  return cy
    .get(path)
    .find(`tbody tr:nth-child(${rowNumber + 1})`)
    .should('exist')
    .click()
    .then(el => {
      if (!force) {
        cy.waitForSaveIndicator();
      }
      return cy.wrap(el);
    });
});

Cypress.Commands.add('expectNumberOfRows', (numberOfRows, modal = false) => {
  let path = 'table tbody tr';

  if (modal) {
    path = '.modal-content-wrapper ' + path;
  }

  return cy.get(path).should('have.length', numberOfRows);
});

/**
 * Expect the table rows to be greater than a given number
 *
 * @param numberOfRows - the number of rows
 */
Cypress.Commands.add('expectNumberOfRowsToBeGreaterThan', numberOfRows => {
  return cy.get('table tbody tr').should(el => {
    expect(el).to.have.length.greaterThan(numberOfRows);
  });
});

export class ColumnAndValue {
  constructor(column, value) {
    this.column = column;
    this.value = value;
  }
}

/**
 * selectRowByColumnAndValue - command
 */
Cypress.Commands.add('selectRowByColumnAndValue', (columnAndValue, modal = false, force = false, single = true) => {
  cy.log(`Select row by ${JSON.stringify(columnAndValue)}`);
  const timeout = { timeout: 10000 };

  if (!force) {
    cy.waitForSaveIndicator();
  }

  let path = '.table-flex-wrapper';
  if (modal) {
    path = '.modal-content-wrapper ' + path;
  }

  // this makes searching for a single column and value easier to use (for the developer),
  // by using a simple object instead of an array.
  if (!Array.isArray(columnAndValue)) {
    columnAndValue = [columnAndValue];
  }

  const $ = Cypress.$;

  return (
    cy
      .get(path, timeout)
      .should(table => {
        // step: make sure the values exist and the page is loaded
        const htmlTable = $(table).html();
        columnAndValue.forEach(c => {
          expect(table).to.contain(c.value);
          expect(htmlTable.includes(`data-cy="cell-${c.column}"`), `Column ${c.column} exists`).is.true;
        });
      })
      // step: find all the columns' indexes
      .then(() => {
        columnAndValue.forEach(item => {
          item.columnIndex = $(`[data-cy='cell-${item.column}']`).index();
        });
      })
      .then(() => {
        // step: iterate through all the table rows and return only the ones matching everything in the array
        return cy.get(`${path} tr`).then($tableRows => {
          let matchingRows = [];

          $tableRows.each((_, tr) => {
            let trMatchesAllColumns = true;

            columnAndValue.forEach(item => {
              const realValue = $(tr)
                .children()
                .eq(item.columnIndex)
                .text();
              if (!realValue.includes(item.value)) {
                trMatchesAllColumns = false;
                return false;
              }
            });

            if (trMatchesAllColumns) {
              // we execute click on the row only if it's not selected already - due to https://github.com/metasfresh/metasfresh/issues/10167
              if (!tr.classList.contains('row-selected')) {
                cy.wrap(tr).click();
              }
              matchingRows.push(tr);
            }
          });
          cy.wrap(matchingRows).should('not.be.empty');
          if (single && matchingRows.length > 1) {
            return cy.wrap(matchingRows[0]).click();
          }
          return cy.wrap(matchingRows);
        });
      })
  );
});

/**
 * Select a single item using the Barcode lookup
 *
 */
 Cypress.Commands.add('selectItemUsingBarcodeFilter', (columnAndValue, modal = false, force = false) => {
  cy.log(`Select HU using BarcodeFilter by ${JSON.stringify(columnAndValue)}`);
  cy.log(`Running selectItemUsingBarcodeFilter in modal: ${modal}`);

  const timeout = { timeout: 10000 };

  if (!force) {
    cy.waitForSaveIndicator();
  }

  cy.get(':nth-child(3) > .filter-wrapper > .btn', timeout).click();

  const filterAlias = 'filter_' + humanReadableNow();
  cy.server();
  cy.route('GET', new RegExp(RewriteURL.Filter)).as(filterAlias);

  const quickActionsAlias = 'quickActions_' + humanReadableNow();
  cy.server();
  cy.route('GET', new RegExp(RewriteURL.QuickActions)).as(quickActionsAlias);

  cy.get('label:contains("Barcode")') // todo @petrica: this label should use a data-cy attribute
    .siblings()
    .find('input[type=text]')
    .type(columnAndValue.value)
    .type('{enter}');

  if (!force) {
    cy.waitForSaveIndicator();
  }

  cy.wait(`@${filterAlias}`);
  cy.wait(`@${quickActionsAlias}`);
});

/**
 * Select all rows on the current page
 *
 */
Cypress.Commands.add('selectAllRowsOnCurrentPage', () => {
  cy.get('body').type('{alt}a');
});
/**
 * Select all rows from all pages
 * This function only works on a list window, and not on a single view window
 */
Cypress.Commands.add('countAllRows', () => {
  cy.get('.pagination-row .pagination-part .hidden-sm-down').then(totalString => {
    const totalRows = parseInt(
      totalString
        .text()
        .split(' ')
        .pop(),
      10
    );
    cy.log(`Total number of rows on all pages is: ${totalRows}`);
    return cy.wrap(totalRows);
  });
});
