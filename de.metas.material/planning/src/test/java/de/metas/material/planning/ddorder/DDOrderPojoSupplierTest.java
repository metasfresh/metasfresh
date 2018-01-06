package de.metas.material.planning.ddorder;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.planning.exception.MrpException;
import mockit.Mocked;

public class DDOrderPojoSupplierTest
{
	@Mocked
	private ModelProductDescriptorExtractor modelProductDescriptorExtractor;

	@Test
	public void test_calculateQtyToMove_ZeroQty()
	{
		test_calculateQtyToMove(
				new BigDecimal("0"), // qtyToMoveExpected
				new BigDecimal("0"), // qtyToMoveRequested
				new BigDecimal("100") // transferPercent
		);
	}

	@Test
	public void test_calculateQtyToMove_HighPrecision_Transfer100()
	{
		test_calculateQtyToMove(
				new BigDecimal("12.3456789"), // qtyToMoveExpected
				new BigDecimal("12.3456789"), // qtyToMoveRequested
				new BigDecimal("100") // transferPercent
		);
	}

	@Test
	public void test_calculateQtyToMove_HighPrecision_Transfer50()
	{
		test_calculateQtyToMove(
				new BigDecimal("15.22222222"), // qtyToMoveExpected
				new BigDecimal("30.44444444"), // qtyToMoveRequested
				new BigDecimal("50") // transferPercent
		);
	}

	@Test
	public void test_calculateQtyToMove_HighPrecision_Transfer10()
	{
		test_calculateQtyToMove(
				new BigDecimal("03.0123713812937129"), // qtyToMoveExpected
				new BigDecimal("30.123713812937129"), // qtyToMoveRequested
				new BigDecimal("10") // transferPercent
		);
	}

	@Test
	public void test_calculateQtyToMove_AnyQty_Transfer0()
	{
		test_calculateQtyToMove(
				new BigDecimal("0"), // qtyToMoveExpected
				new BigDecimal("30.123713812937129"), // qtyToMoveRequested
				new BigDecimal("0") // transferPercent
		);
	}

	@Test(expected = MrpException.class)
	public void test_calculateQtyToMove_AnyQty_TransferNegative()
	{
		test_calculateQtyToMove(
				new BigDecimal("99999999999999999"), // qtyToMoveExpected - does not matter
				new BigDecimal("30.123713812937129"), // qtyToMoveRequested
				new BigDecimal("-10") // transferPercent
		);
	}

	private void test_calculateQtyToMove(
			final BigDecimal qtyToMoveExpected,
			final BigDecimal qtyToMoveRequested,
			final BigDecimal transferPercent)
	{
		final DDOrderPojoSupplier ddOrderPojoSupplier = new DDOrderPojoSupplier(modelProductDescriptorExtractor);

		final BigDecimal qtyToMoveActual = ddOrderPojoSupplier
				.calculateQtyToMove(qtyToMoveRequested, transferPercent);

		final String msg = "Invalid QtyToMove for "
				+ " QtyToMoveRequested=" + qtyToMoveRequested
				+ ", TransferPercent=" + transferPercent;
		Assert.assertThat(msg, qtyToMoveActual, Matchers.comparesEqualTo(qtyToMoveExpected));
	}
}
