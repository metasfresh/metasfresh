/*
 * #%L
 * metasfresh-material-planning
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

package de.metas.material.planning.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PPOrderQuantitiesTest
{
	private I_C_UOM uom;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUomEach();
	}

	@Test
	public void test_convertQuantities()
	{
		for (final PPOrderQuantities qtys : valuesWithSingleQtySet())
		{
			final PPOrderQuantities qtysActual = qtys.convertQuantities(Quantity::negate).convertQuantities(Quantity::negate);
			assertThat(qtysActual).usingRecursiveComparison().isEqualTo(qtys);
		}
	}

	private final List<PPOrderQuantities> valuesWithSingleQtySet()
	{
		final PPOrderQuantities.PPOrderQuantitiesBuilder builder = PPOrderQuantities.builder().qtyRequiredToProduce(Quantity.of(0, uom));
		return ImmutableList.of(
				builder.qtyRequiredToProduce(Quantity.of(1, uom)).build(),
				builder.qtyRequiredToProduceBeforeClose(Quantity.of(1, uom)).build(),
				builder.qtyReceived(Quantity.of(1, uom)).build(),
				builder.qtyScrapped(Quantity.of(1, uom)).build(),
				builder.qtyRejected(Quantity.of(1, uom)).build(),
				builder.qtyReserved(Quantity.of(1, uom)).build()
		);
	}

}