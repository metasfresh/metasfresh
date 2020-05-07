package org.adempiere.ad.trx.api.impl;

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


import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

public class TrxTest
{
	private MockedTrxManager trxManager;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		trxManager = new MockedTrxManager();
		Services.registerService(ITrxManager.class, trxManager);
	}

	@Test
	public void test_rollback_NeverThrowsException()
	{
		final MockedTrx trx = (MockedTrx)trxManager.createTrx("TestTrx", false);

		trx.setOnRollbackException(new RuntimeException("test - fail on commit"));

		trx.start();
		trx.rollback();
	}

}
