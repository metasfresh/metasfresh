package org.adempiere.ad.table;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ReferencedRecordDAOTest
{
	private ReferencedRecordDAO referencedRecordDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		referencedRecordDAO = ReferencedRecordDAO.newInstanceForUnitTesting();
	}

	@Test
	void existingRecord()
	{
		final I_C_UOM record = createUOM(true);

		assertThat(referencedRecordDAO.exists(TableRecordReference.of(record))).isTrue();
	}

	@Test
	void missingRecord()
	{
		assertThat(referencedRecordDAO.exists(TableRecordReference.of(I_C_UOM.Table_Name, 999999))).isFalse();
	}

	/** Guards the deliberate absence of an active-records filter in {@link ReferencedRecordDAO#exists(TableRecordReference)}. */
	@Test
	void inactiveRecordStillExists()
	{
		final I_C_UOM record = createUOM(false);

		assertThat(referencedRecordDAO.exists(TableRecordReference.of(record))).isTrue();
	}

	private static I_C_UOM createUOM(final boolean active)
	{
		final I_C_UOM record = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		record.setName("test");
		record.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(record);
		return record;
	}
}
