/* eslint-disable */
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

import { ColumnAndValue } from '../../support/commands/navigation';

describe('1', function() {
  describe('2', function() {
    describe('3', function() {
      describe('4', function() {

        it('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee', function() {
          cy.visitWindow('140');
          // const columnAndValue = [
          //   new ColumnAndValue('Value', 'P002753'),
          //   new ColumnAndValue('Name', 'Abzug für Beitrag Basic-Linie'),
          // ];
          // const columnAndValue = [
          //   { column: 'Value', value: 'P002753' },
          //   { column: 'Name', value: 'Abzug für Beitrag Basic-Linie' },
          // ];
          // cy.selectRowByColumnAndValue(columnAndValue)
          //   .click();

          cy.selectRowByColumnAndValue(new ColumnAndValue('Name', 'Abzug für Beitrag Basic-Linie'));

          // cy.selectRowByColumnAndValue( { column: 'Value', value: 'P002753' })
          // .click();
          // .dblclick();
        });

      });
    });
  });
});

