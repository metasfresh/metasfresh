package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.expectations.request.JsonInventoryExpectation;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.InventoryId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.frontend-testing
 * %%
 * Copyright (C) 2026 metas GmbH
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

public class AssertInventoryExpectationsCommandTest
{
	private static final InventoryId INVENTORY_ID = InventoryId.ofRepoId(100);
	private static final InventoryId OTHER_INVENTORY_ID = InventoryId.ofRepoId(200);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static I_M_InventoryLine inventoryLine(final InventoryId inventoryId, final String qtyBook, final String qtyCount)
	{
		final I_M_InventoryLine line = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);
		line.setM_Inventory_ID(inventoryId.getRepoId());
		line.setQtyBook(new BigDecimal(qtyBook));
		line.setQtyCount(new BigDecimal(qtyCount));
		InterfaceWrapperHelper.saveRecord(line);
		return line;
	}

	private static void assertQtys(final List<I_M_InventoryLine> lines, final String qtyBook, final String qtyCount)
	{
		AssertInventoryExpectationsCommand.assertQtys(
				lines,
				INVENTORY_ID,
				JsonInventoryExpectation.builder()
						.qtyBook(qtyBook != null ? new BigDecimal(qtyBook) : null)
						.qtyCount(qtyCount != null ? new BigDecimal(qtyCount) : null)
						.build());
	}

	@Test
	public void qtyBookAndQtyCountOfTheWriteOffLine()
	{
		final List<I_M_InventoryLine> lines = ImmutableList.of(inventoryLine(INVENTORY_ID, "0.002", "0"));

		assertThatCode(() -> assertQtys(lines, "0.002", "0")).doesNotThrowAnyException();
	}

	@Test
	public void qtysAreComparedNumerically_soScaleDoesNotMatter()
	{
		// the DB hands back the column's scale, not the scale the expectation was written with
		final List<I_M_InventoryLine> lines = ImmutableList.of(inventoryLine(INVENTORY_ID, "0.00200", "0.00000"));

		assertThatCode(() -> assertQtys(lines, "0.002", "0")).doesNotThrowAnyException();
	}

	@Test
	public void qtysOfTheHUsLinesOfTheSAMEInventoryAreSummed()
	{
		final List<I_M_InventoryLine> lines = ImmutableList.of(
				inventoryLine(INVENTORY_ID, "0.002", "0"),
				inventoryLine(INVENTORY_ID, "0.003", "0.001"));

		assertThatCode(() -> assertQtys(lines, "0.005", "0.001")).doesNotThrowAnyException();
	}

	@Test
	public void linesOfAnotherInventoryAreIgnored()
	{
		final List<I_M_InventoryLine> lines = ImmutableList.of(
				inventoryLine(INVENTORY_ID, "0.002", "0"),
				inventoryLine(OTHER_INVENTORY_ID, "0.5", "0.5"));

		assertThatCode(() -> assertQtys(lines, "0.002", "0")).doesNotThrowAnyException();
	}

	@Test
	public void aMismatchingQtyBookFails()
	{
		final List<I_M_InventoryLine> lines = ImmutableList.of(inventoryLine(INVENTORY_ID, "0.5", "0.498"));

		assertThatThrownBy(() -> assertQtys(lines, "0.002", null))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("QtyBook");
	}

	@Test
	public void aMismatchingQtyCountFails()
	{
		final List<I_M_InventoryLine> lines = ImmutableList.of(inventoryLine(INVENTORY_ID, "0.002", "0.498"));

		assertThatThrownBy(() -> assertQtys(lines, null, "0"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("QtyCount");
	}
}
