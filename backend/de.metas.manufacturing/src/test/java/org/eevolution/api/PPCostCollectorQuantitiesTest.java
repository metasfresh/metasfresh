/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class PPCostCollectorQuantitiesTest
{
	private I_C_UOM uom;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = BusinessTestHelper.createUomEach();
	}

	public ImmutableList<PPCostCollectorQuantities> valuesWithOnlyOneQtySet()
	{
		return ImmutableList.of(
				PPCostCollectorQuantities.builder().movementQty(Quantity.of(1, uom)).build(),
				PPCostCollectorQuantities.builder().movementQty(Quantity.zero(uom)).scrappedQty(Quantity.of(1, uom)).build(),
				PPCostCollectorQuantities.builder().movementQty(Quantity.zero(uom)).rejectedQty(Quantity.of(1, uom)).build()
		);
	}

	@Test
	public void negate()
	{
		valuesWithOnlyOneQtySet().forEach(this::negate);
	}

	private void negate(final PPCostCollectorQuantities qtys)
	{
		assertThat(qtys).usingRecursiveComparison().isEqualTo(qtys.negate().negate());
	}

}