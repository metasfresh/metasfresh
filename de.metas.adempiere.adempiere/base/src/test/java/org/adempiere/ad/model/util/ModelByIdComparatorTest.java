package org.adempiere.ad.model.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelByIdComparatorTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testStandard_AscendingOrder()
	{
		final I_Test record1 = createRecord();
		final I_Test record2 = createRecord();
		final I_Test record3 = createRecord();
		final I_Test record4 = createRecord();
		final I_Test record5 = createRecord();

		final List<I_Test> listToSort = Arrays.asList(record1, record5, record2, record4, record3);
		Collections.sort(listToSort, ModelByIdComparator.getInstance());

		Assert.assertEquals(
				Arrays.asList(record1, record2, record3, record4, record5),
				listToSort);
	}

	@Test
	public void testStandard_ReverseOrder()
	{
		final I_Test record1 = createRecord();
		final I_Test record2 = createRecord();
		final I_Test record3 = createRecord();
		final I_Test record4 = createRecord();
		final I_Test record5 = createRecord();

		final List<I_Test> listToSort = Arrays.asList(record1, record5, record2, record4, record3);
		Collections.sort(listToSort, ModelByIdComparator.getInstance().reversed());

		Assert.assertEquals(
				Arrays.asList(record5, record4, record3, record2, record1),
				listToSort);
	}
	
	private final I_Test createRecord()
	{
		final I_Test record = InterfaceWrapperHelper.create(Env.getCtx(), I_Test.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);
		return record;
	}
}
