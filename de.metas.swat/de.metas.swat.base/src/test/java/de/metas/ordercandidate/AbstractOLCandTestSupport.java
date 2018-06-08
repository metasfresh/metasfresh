package de.metas.ordercandidate;

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


import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.BeforeClass;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.modelvalidator.OrderCandidate;
import de.metas.testsupport.AbstractTestSupport;

/**
 * Test support for {@link I_C_OLCand}s
 *
 * @author al
 */
public abstract class AbstractOLCandTestSupport extends AbstractTestSupport
{
	public AbstractOLCandTestSupport()
	{
		super();
	}

	@BeforeClass
	public static final void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@Before
	public final void initStuff()
	{
		AdempiereTestHelper.get().init();

		initModelValidators();

		initDB();
	}

	protected void initModelValidators()
	{
		// Initialize MAIN OrderCandidate model validator
		final OrderCandidate orderCandidateMV = new OrderCandidate();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(orderCandidateMV);
	}

	protected abstract void initDB();

	protected <T extends I_C_OLCand> T olCand(final IContextAware context, final Class<T> interfaceClass, final boolean save)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		final T olCand = db.newInstance(context.getCtx(), interfaceClass);
		if (save)
		{
			InterfaceWrapperHelper.save(olCand);
		}
		return olCand;
	}
}
