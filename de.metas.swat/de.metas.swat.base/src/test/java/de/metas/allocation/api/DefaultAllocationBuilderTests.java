package de.metas.allocation.api;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Properties;

import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

public class DefaultAllocationBuilderTests
{

	@Before
	public void before()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.DEBUG);
	}

	/**
	 * The builders constructor ist called with a context provider which is used to get the allocation's trxName. Es need to make sure that this provider actually has a trxName, to avoid our
	 * allocation (which is a document after all!) to be processed within a trx. Otherwise we can't do save rollbacks
	 */
	@Test(expected = TrxException.class)
	public void testConstructorChecksTrx()
	{
		final IContextAware ctxAware = PlainContextAware.newWithTrxName(new Properties(), null);
		new DefaultAllocationBuilder(ctxAware);
	}

}
