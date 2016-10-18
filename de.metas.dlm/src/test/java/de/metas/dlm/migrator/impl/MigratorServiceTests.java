package de.metas.dlm.migrator.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Payment;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.dlm.Partition;
import de.metas.dlm.model.IDLMAware;

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
	@BeforeClass
	public static final void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
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
		final ImmutableList<IDLMAware> records = ImmutableList.of(paymentDlmAware);
		final Partition partition = new Partition(null, records);

		new MigratorService().testMigratePartition(partition);
	}
}
