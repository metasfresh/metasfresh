/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.view.invalidation;

import de.metas.ui.web.base.model.I_WEBUI_ViewInvalidateOnChange;
import de.metas.ui.web.window.datatypes.WindowId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the {@link WebuiViewInvalidateOnChangeRepository#retrieve()} mapping logic:
 * AD_Table_ID &rarr; TableName resolution, the active-only filter, and multimap construction.
 * A fresh repository + reset persistence per test (see {@link #setUp()}) keeps the table-scoped
 * cache from leaking rows between scenarios.
 */
class WebuiViewInvalidateOnChangeRepositoryTest
{
	private static final int WINDOW_A = 1000001;
	private static final int WINDOW_B = 1000002;

	private WebuiViewInvalidateOnChangeRepository repository;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		repository = new WebuiViewInvalidateOnChangeRepository();
	}

	private int createTable(final String tableName)
	{
		final I_AD_Table table = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		table.setName(tableName);
		table.setTableName(tableName);
		InterfaceWrapperHelper.saveRecord(table);
		return table.getAD_Table_ID();
	}

	private void createConfig(final int adTableId, final int adWindowId, final boolean active)
	{
		final I_WEBUI_ViewInvalidateOnChange record = InterfaceWrapperHelper.newInstance(I_WEBUI_ViewInvalidateOnChange.class);
		record.setAD_Table_ID(adTableId);
		record.setAD_Window_ID(adWindowId);
		record.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(record);
	}

	@Test
	void resolvesTableNameAndMapsToWindow()
	{
		final int receiptScheduleTableId = createTable("M_ReceiptSchedule");
		createConfig(receiptScheduleTableId, WINDOW_A, true);

		assertThat(repository.getWindowIdsToInvalidateForTable("M_ReceiptSchedule"))
				.containsExactly(WindowId.of(WINDOW_A));
		assertThat(repository.getAllTriggerTableNames())
				.containsExactly("M_ReceiptSchedule");
	}

	@Test
	void inactiveConfigRowsAreExcluded()
	{
		final int receiptScheduleTableId = createTable("M_ReceiptSchedule");
		final int orderTableId = createTable("C_Order");
		createConfig(receiptScheduleTableId, WINDOW_A, true);
		createConfig(orderTableId, WINDOW_B, false /*inactive*/);

		assertThat(repository.getWindowIdsToInvalidateForTable("C_Order")).isEmpty();
		assertThat(repository.getAllTriggerTableNames()).containsExactly("M_ReceiptSchedule");
	}

	@Test
	void multipleWindowsForSameTriggerTable()
	{
		final int receiptScheduleTableId = createTable("M_ReceiptSchedule");
		createConfig(receiptScheduleTableId, WINDOW_A, true);
		createConfig(receiptScheduleTableId, WINDOW_B, true);

		assertThat(repository.getWindowIdsToInvalidateForTable("M_ReceiptSchedule"))
				.containsExactlyInAnyOrder(WindowId.of(WINDOW_A), WindowId.of(WINDOW_B));
	}

	@Test
	void unconfiguredTableReturnsEmpty()
	{
		final int receiptScheduleTableId = createTable("M_ReceiptSchedule");
		createConfig(receiptScheduleTableId, WINDOW_A, true);

		assertThat(repository.getWindowIdsToInvalidateForTable("C_Invoice")).isEmpty();
	}
}
