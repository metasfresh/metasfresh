package org.adempiere.ad.persistence;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.Check;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Database-coupled test for {@link TableModelLoader#getPOs(java.util.Properties, String, Set, String)}.
 * <p>
 * Reproduces the bug where loading more than the PostgreSQL/JDBC 2-byte bind-parameter limit
 * (32767) of record IDs in a single {@code IN (?,?,...)} query fails with
 * {@code java.io.IOException: Tried to send an out-of-range integer as a 2-byte value: N}
 * (surfacing as {@code DBException: An I/O error occurred while sending to the backend}).
 * The fix chunks the load into batches of {@link TableModelLoader#MAX_IDS_PER_QUERY}.
 * <p>
 * This test is {@link Disabled} because this module has no automated real-DB harness and the
 * in-memory {@code POJOWrapper} does NOT exercise the JDBC path where the bug lives. Run it
 * manually against a real PostgreSQL instance (set the {@code PropertyFile} system property, as
 * the sibling {@code GuaranteedPOBufferedIterator_DBTest} does).
 * <p>
 * Ground-truth red/green for this fix was captured at the raw-JDBC level against a live
 * PostgreSQL 15 instance: a monolithic {@code IN} with 40000 bound params reproduced the exact
 * error above (and dropped the connection), while chunking by 1000 loaded all 40000 rows.
 */
@Disabled // requires a real database connection (see class javadoc)
public class TableModelLoader_DBTest
{
	private void setupAdempiere()
	{
		if (Check.isEmpty(System.getProperty("PropertyFile"), true))
		{
			final String propertyFile = new File(".").getAbsolutePath()
					+ File.separator + ".." + File.separator + ".."
					+ File.separator + "de.metas.endcustomer."
					+ File.separator + "Adempiere.properties_" + System.getProperty("user.name");
			System.out.println("Set default PropertyFile=" + propertyFile);
			System.setProperty("PropertyFile", propertyFile);
		}
		Env.getSingleAdempiereInstance(null).startup(RunMode.SWING_CLIENT);
	}

	/**
	 * Creates more than {@link TableModelLoader#MAX_IDS_PER_QUERY} records (and more than the
	 * 32767 JDBC bind-param limit) and asserts {@code loadByIds} returns them all without error.
	 * Fails on the pre-fix code with the 2-byte overflow; passes once getPOs chunks the load.
	 */
	@Test
	public void loadByIds_moreThanBindParameterLimit()
	{
		setupAdempiere();

		final int count = 33000; // > 32767 JDBC bind-param limit and > MAX_IDS_PER_QUERY
		final Set<Integer> ids = new HashSet<>(count);
		for (int i = 0; i < count; i++)
		{
			final I_Test record = InterfaceWrapperHelper.create(Env.getCtx(), I_Test.class, ITrx.TRXNAME_None);
			record.setName("Test_" + UUID.randomUUID());
			InterfaceWrapperHelper.save(record);
			ids.add(record.getTest_ID());
		}

		final List<I_Test> loaded = InterfaceWrapperHelper.loadByIds(ids, I_Test.class);

		Assertions.assertEquals(ids.size(), loaded.size(), "all created records must be loaded");
	}
}
