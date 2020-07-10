package org.adempiere.inout.service.impl;

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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.inout.IInOutBL;
import de.metas.inout.impl.InOutBL;
import de.metas.util.Services;

public class InOutBLTest
{
	private InOutBL inoutBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		POJOWrapper.setDefaultStrictValues(false); // we will want to return "null"
		
		inoutBL = new InOutBL();
		Services.registerService(IInOutBL.class, inoutBL);
	}

	@Test
	public void test_isReversal_NoReversal()
	{
		final I_M_InOut record = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		Assert.assertEquals("", false, inoutBL.isReversal(record));
	}

	@Test
	public void test_isReversal_HasReversal()
	{
		final I_M_InOut record = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		final I_M_InOut reversal = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		reversal.setReversal_ID(InterfaceWrapperHelper.getId(record));
		InterfaceWrapperHelper.save(reversal);

		record.setReversal_ID(InterfaceWrapperHelper.getId(reversal));
		InterfaceWrapperHelper.save(record);

		Assert.assertEquals(false, inoutBL.isReversal(record));
		Assert.assertEquals(true, inoutBL.isReversal(reversal));
	}

	@Test(expected=AdempiereException.class)
	public void test_isReversal_SelfReferencing()
	{
		final I_M_InOut record = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		record.setReversal_ID(InterfaceWrapperHelper.getId(record));
		InterfaceWrapperHelper.save(record);

		final boolean isReversalActual = inoutBL.isReversal(record);
		Assert.fail("This test shall throw exception because self referencing is not ok, but instead returned: "+isReversalActual);
	}

}
