package org.adempiere.ad.table.api.impl;

import de.metas.util.Services;
import org.adempiere.ad.table.api.IReferencedRecordDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReferencedRecordDAOTest
{
	private IReferencedRecordDAO referencedRecordDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		referencedRecordDAO = Services.get(IReferencedRecordDAO.class);
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
