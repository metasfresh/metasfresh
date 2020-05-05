/*
 * #%L
 * metasfresh-e2e
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

const checkIfWindowCanExecuteActions = () => {
  /**
   * Only specific windows can have actions. They match one of the following urls:
   *
   * https://dev586.metasfresh.com/window/123?viewId=123-o&page=1 - list view
   *    - in this case also '.table-flex-wrapper' should exist
   * https://dev586.metasfresh.com/window/123/2156425 - single view
   *    - in this case also '.panel' should exist
   *
   * This match is needed because cypress is so fast that it may press the action button before any viewId is available, and the system will error out.
   */
  cy.url().should('matches', new RegExp(`window/[0-9]+(/[0-9]+|.*viewId=)`));

  cy.url().then(url => {
    const listViewRegexp = new RegExp(`window/[0-9]+.*viewId=`);
    // const singleViewRegexp = new RegExp(`window/[0-9]+/[0-9]+`);

    if (url.match(listViewRegexp)) {
      cy.get('.table-flex-wrapper').should('exist');
    } else {
      cy.get('.panel .row').should('exist');
    }
  });
};

export { checkIfWindowCanExecuteActions };
