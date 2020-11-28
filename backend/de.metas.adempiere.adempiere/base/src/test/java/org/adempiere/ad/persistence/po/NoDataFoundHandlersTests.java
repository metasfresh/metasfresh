package org.adempiere.ad.persistence.po;

import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
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

public class NoDataFoundHandlersTests
{
	private static final TestHandler testHandler = new TestHandler();

	@Before
	public void init()
	{
		NoDataFoundHandlers.get().addHandler(testHandler);
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1076
	 */
	@Test
	public void avoidStackOverFlowError()
	{
		NoDataFoundHandlers.get().invokeHandlers("teableName", new Object[] { 1 }, PlainContextAware.newWithThreadInheritedTrx());
		// nothing really to assert. If we get to this point, we are fine
	}

	/**
	 * This little dummy handler does all it can to provoke a {@link StackOverflowError}.
	 */
	private static class TestHandler implements INoDataFoundHandler
	{
		@Override
		public boolean invoke(String tableName, Object[] ids, IContextAware ctx)
		{
			return NoDataFoundHandlers.get().invokeHandlers(tableName, ids, ctx);
		}
	}
}
