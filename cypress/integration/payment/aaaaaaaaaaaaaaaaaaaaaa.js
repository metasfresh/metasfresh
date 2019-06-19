/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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


describe('Create a manual Payment for a Sales Invoice', function () {
  it('Creates a Sales Order and Invoice', function () {


    // cy.visitWindow('167', '1000166'); // this happens on tbp localhost
    cy.visitWindow('167', '1000014'); // run this on https://dev508.metasfresh.com/window/167/1000014
    cy.wait(1000);

    cy.isChecked('IsPaid', false).then(checkBoxValue => {
      cy.log(`IsPaid = ${checkBoxValue}`); // this should be true
      assert.isTrue(checkBoxValue);
    });
  });
});
