package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.X_PP_MRP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.impl.MRPContext;

public class MRPQueryBuilderTest
{
	private MRPTestHelper helper;
	private MRPTestDataSimple masterData;

	@Before
	public void init()
	{
		helper = new MRPTestHelper();
		masterData = new MRPTestDataSimple(helper);
	}

	@Test
	public void test01()
	{
		final int tomatoLowLevel = 5;
		masterData.pTomato.setLowLevel(tomatoLowLevel);
		InterfaceWrapperHelper.save(masterData.pTomato);
		Assert.assertEquals("Invalid product LowLevel", tomatoLowLevel, masterData.pTomato.getLowLevel());

		final IContextAware contextProvider = helper.contextProvider;

		final IMutableMRPContext mrpContext = new MRPContext();
		mrpContext.setCtx(contextProvider.getCtx());
		mrpContext.setTrxName(contextProvider.getTrxName());
		mrpContext.setAD_Org(masterData.adOrg01);
		mrpContext.setPlant(masterData.plant01);
		mrpContext.setM_Warehouse(masterData.warehouse_plant01);
		mrpContext.setM_Product(null);
		mrpContext.setPlanningHorizon(TimeUtil.getDay(2014, 12, 31));

		final MRPQueryBuilder mrpQueryBuilder = new MRPQueryBuilder();
		final ICompositeQueryFilter<I_PP_MRP> queryFilter = mrpQueryBuilder
				// Planning Dimension
				.setMRPContext(mrpContext)
				// .setM_Product_ID(mrpContext.getM_Product_ID()) // shall be grabbed from context
				// Product's LLC
				.setLowLevelCode(tomatoLowLevel)
				// Only demands (Firm, Not Firm)
				.setTypeMRP(X_PP_MRP.TYPEMRP_Demand)
				// Only those MRP records which are in our planning horizon
				.setDatePromisedMax(mrpContext.getPlanningHorizon())
				//
				.createQueryBuilder()
				.getFilters();

		final I_PP_MRP mrp = InterfaceWrapperHelper.newInstance(I_PP_MRP.class, contextProvider);
		mrp.setAD_Org(mrpContext.getAD_Org());
		mrp.setS_Resource(mrpContext.getPlant());
		mrp.setM_Warehouse(mrpContext.getM_Warehouse());
		mrp.setM_Product(masterData.pTomato);
		mrp.setTypeMRP(X_PP_MRP.TYPEMRP_Demand);
		mrp.setDatePromised(TimeUtil.getDay(2014, 06, 01));

		assertMatched(queryFilter, mrp);
	}

	@Test
	public void test_clear()
	{
		final MRPQueryBuilder mrpQueryBuilder = new MRPQueryBuilder();
		mrpQueryBuilder.setContextProvider(new PlainContextAware(Env.getCtx()));

		// Clear all filters
		mrpQueryBuilder.clear();

		// Create query filters
		final ICompositeQueryFilter<I_PP_MRP> filters = mrpQueryBuilder.createQueryBuilder().getFilters();

		// Make sure there are NO filters
		Assert.assertEquals("Invalid filters: " + filters, "(((true)))", filters.toString());
	}

	private void assertMatched(final IQueryFilter<I_PP_MRP> filter, final I_PP_MRP mrp)
	{
		final boolean matched = filter.accept(mrp);
		if (!matched)
		{
			final String debugInfo = Services.get(IQueryBL.class).debugAccept(filter, mrp);
			System.out.println(debugInfo);
		}
		Assert.assertTrue("Record shall be matched.\nMRP=" + mrp + "\nfilter=" + filter, matched);
	}
}
