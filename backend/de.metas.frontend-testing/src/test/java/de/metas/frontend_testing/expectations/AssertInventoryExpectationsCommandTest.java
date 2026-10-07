package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.engine.DocStatus;
import de.metas.frontend_testing.expectations.request.JsonInventoryExpectation;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.InventoryId;
import de.metas.organization.OrgId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

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

	//
	//
	// Which inventory document the qtyBook/qtyCount check reads
	//
	//

	private static final String WRITE_OFF_DESCRIPTION = "Bei Materialzuteilung zu PP_1 geleert";

	private static Inventory inventory(final InventoryId id, @Nullable final String description)
	{
		return Inventory.builder()
				.id(id)
				.orgId(OrgId.ANY)
				.docBaseAndSubType(DocBaseAndSubType.of("MMI", null))
				.movementDate(ZonedDateTime.parse("2026-03-30T00:00:00+02:00"))
				.description(description)
				.docStatus(DocStatus.Completed)
				.documentNo(String.valueOf(id.getRepoId()))
				.lines(ImmutableList.of())
				.build();
	}

	/**
	 * The two documents TC-CW2 sees on the HU, in the order {@code getInventoryLinesByHUId} returns them
	 * (ascending line id): the described write-off FIRST, the harness's seed inventory LAST — so "the
	 * described one" and "the latest one" are different records and a regression to the latter is visible.
	 */
	private static List<I_M_InventoryLine> writeOffThenSeedLines()
	{
		return ImmutableList.of(
				inventoryLine(INVENTORY_ID, "0.002", "0"),           // the described write-off
				inventoryLine(OTHER_INVENTORY_ID, "0.5", "0.5"));    // the seed inventory, latest
	}

	private static void assertQtysOfSelected(
			final List<I_M_InventoryLine> lines,
			@Nullable final String description,
			@Nullable final String qtyBook,
			@Nullable final String qtyCount)
	{
		final Map<InventoryId, Inventory> inventoriesById = ImmutableMap.of(
				INVENTORY_ID, inventory(INVENTORY_ID, WRITE_OFF_DESCRIPTION),
				OTHER_INVENTORY_ID, inventory(OTHER_INVENTORY_ID, null));

		final Inventory selectedInventory = AssertInventoryExpectationsCommand.selectInventory(
				lines,
				description,
				line -> inventoriesById.get(InventoryId.ofRepoId(line.getM_Inventory_ID())));

		AssertInventoryExpectationsCommand.assertQtysOfSelectedInventory(
				selectedInventory,
				lines,
				description,
				JsonInventoryExpectation.builder()
						.description(description)
						.qtyBook(qtyBook != null ? new BigDecimal(qtyBook) : null)
						.qtyCount(qtyCount != null ? new BigDecimal(qtyCount) : null)
						.build());
	}

	@Test
	public void qtysAreReadFromTheDESCRIBEDInventory_notTheLatestOne()
	{
		final List<I_M_InventoryLine> lines = writeOffThenSeedLines();

		// 0.002/0 is the write-off's; 0.5/0.5 is the seed's. Picking the latest would assert the seed
		// and TC-CW2 would pass against a broken write-off.
		assertThatCode(() -> assertQtysOfSelected(lines, WRITE_OFF_DESCRIPTION, "0.002", "0")).doesNotThrowAnyException();

		assertThatThrownBy(() -> assertQtysOfSelected(lines, WRITE_OFF_DESCRIPTION, "0.5", "0.5"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("QtyBook");
	}

	@Test
	public void qtysFallBackToTheLatestInventoryWhenNoDescriptionIsGiven()
	{
		final List<I_M_InventoryLine> lines = writeOffThenSeedLines();

		assertThatCode(() -> assertQtysOfSelected(lines, null, "0.5", "0.5")).doesNotThrowAnyException();
	}

	@Test
	public void aDescriptionMatchingNoInventoryFails()
	{
		final List<I_M_InventoryLine> lines = writeOffThenSeedLines();

		assertThatThrownBy(() -> assertQtysOfSelected(lines, "no such description", "0.002", "0"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("no such description");
	}
}
