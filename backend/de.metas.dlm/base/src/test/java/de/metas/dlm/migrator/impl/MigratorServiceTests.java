package de.metas.dlm.migrator.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.dlm.Partition;
import de.metas.dlm.model.IDLMAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class MigratorServiceTests
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Simple test, just to exercise the service once.
	 */
	@Test
	public void test()
	{
		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		InterfaceWrapperHelper.save(payment);

		final IDLMAware paymentDlmAware = InterfaceWrapperHelper.create(payment, IDLMAware.class);

		final ImmutableMap<String, Collection<ITableRecordReference>> records = ImmutableMap.of(
				I_C_Payment.Table_Name,
				ImmutableList.of(TableRecordReference.ofOrNull(paymentDlmAware)));

		final Partition partition = new Partition().withDLM_Partition_ID(1).withRecords(records);

		new MigratorService().testMigratePartition(partition);
	}
}
