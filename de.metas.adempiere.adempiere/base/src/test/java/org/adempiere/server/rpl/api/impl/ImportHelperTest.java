package org.adempiere.server.rpl.api.impl;

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


import static org.adempiere.server.rpl.api.impl.ReplicationHelper.setReplicationCtx;

import java.util.Properties;

import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

public class ImportHelperTest
{
	@Test
	public void test_setReplicationCtx() throws Exception
	{
		final Properties initialCtx = Env.newTemporaryCtx();
		Env.setContext(initialCtx, Env.CTXNAME_AD_Client_ID, 12345);

		final Properties ctx = Env.deriveCtx(initialCtx);
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 12345);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 1, false); // overwrite = false
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 1);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 1, true); // overwrite = true, but same value
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 1);

		// Test remove
		// We expect that AD_Client_ID to be set to ZERO
		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, null, true); // overwrite = true
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 0);
	}

	@Test(expected = ReplicationException.class)
	public void test_setReplicationCtx_overwrite_fails() throws Exception
	{
		final Properties initialCtx = new Properties();
		Env.setContext(initialCtx, Env.CTXNAME_AD_Client_ID, 12345);

		final Properties ctx = new Properties(initialCtx);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 1, false); // overwrite = false
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 1);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 2, false); // overwrite = false, not same value, shall throw exception
	}
}
