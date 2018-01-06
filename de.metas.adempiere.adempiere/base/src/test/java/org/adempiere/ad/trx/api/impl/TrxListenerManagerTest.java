package org.adempiere.ad.trx.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.impl.TrxConstraintsBL;
import org.compiere.util.Trx;
import org.junit.Before;
import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class TrxListenerManagerTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testOnAfterCommit() throws SQLException
	{
		Services.get(ISysConfigBL.class).setValue(TrxConstraintsBL.SYSCONFIG_TRX_CONSTRAINTS_DISABLED, true, 0);

		final TrxManager trxManager = new TrxManager();
		final Trx trx = new Trx(trxManager, "trxName", true);
		final ITrxListenerManager trxListenerManager = trx.getTrxListenerManager();
		assertThat(trxListenerManager).isInstanceOf(TrxListenerManager.class); // make sure that we test the right class

		final TrxRrunnable runnable = new TrxRrunnable();

		trxListenerManager
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerWeakly(false) // register "hard", because that's how it was before
				.invokeMethodJustOnce(false)
				.registerHandlingMethod(innerTrx -> runnable.run());

		trx.commit(false);
		assertThat(runnable.timesInvoked).isEqualTo(1);
		trx.commit(false);
		assertThat(runnable.timesInvoked).isEqualTo(2);
	}

	@Test
	public void testOnAfterNextCommit() throws SQLException
	{
		Services.get(ISysConfigBL.class).setValue(TrxConstraintsBL.SYSCONFIG_TRX_CONSTRAINTS_DISABLED, true, 0);

		final TrxManager trxManager = new TrxManager();
		final Trx trx = new Trx(trxManager, "trxName", true);
		final ITrxListenerManager trxListenerManager = trx.getTrxListenerManager();
		assertThat(trxListenerManager).isInstanceOf(TrxListenerManager.class); // make sure that we test the right class

		final TrxRrunnable runnable = new TrxRrunnable();

		trxListenerManager
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerWeakly(false) // register "hard", because that's how it was before
				.invokeMethodJustOnce(true)
				.registerHandlingMethod(innerTrx -> runnable.run());

		trx.commit(false);
		assertThat(runnable.timesInvoked).isEqualTo(1);
		trx.commit(false);
		assertThat(runnable.timesInvoked).isEqualTo(1); // no additional invocation of the listener
	}

	final static class TrxRrunnable implements Runnable
	{
		private int timesInvoked = 0;

		@Override
		public void run()
		{
			timesInvoked++;
		}

	}

}
