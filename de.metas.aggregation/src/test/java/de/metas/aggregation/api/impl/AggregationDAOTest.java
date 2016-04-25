package de.metas.aggregation.api.impl;

/*
 * #%L
 * de.metas.aggregation
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


import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.aggregation.api.IAggregationDAO;
import de.metas.aggregation.model.C_Aggregation_Builder;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.X_C_Aggregation;

public class AggregationDAOTest
{
	private static final Boolean IsSOTrx_NULL = null;

	@Rule
	public TestWatcher testWatchman = new AdempiereTestWatcher();

	private IContextAware context;
	private AggregationDAO aggregationDAO;
	private final Class<I_Test> aggTestModelClass = I_Test.class;
	private String aggregationUsageLevel = X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header;

	@Before
	public void init()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();
		adempiereTestHelper.setupContext_AD_Client_IfNotSet();

		this.context = new PlainContextAware(Env.getCtx());
		this.aggregationDAO = (AggregationDAO)Services.get(IAggregationDAO.class);
	}

	@Test
	public void test_retrieveDefaultAggregation_WithDefault()
	{
		final I_C_Aggregation aggDefault = newAggregation()
				.setName("Default")
				.setIsDefault(true)
				.build();

		test_retrieveDefaultAggregation(aggDefault, true);
		test_retrieveDefaultAggregation(aggDefault, false);
		test_retrieveDefaultAggregation(aggDefault, IsSOTrx_NULL);
	}

	@Test
	public void test_retrieveDefaultAggregation_WithDefault_WithDefaultSO()
	{
		final I_C_Aggregation aggDefault = newAggregation()
				.setName("Default")
				.setIsDefault(true)
				.build();
		final I_C_Aggregation aggDefaultSO = newAggregation()
				.setName("DefaultSO")
				.setIsDefaultSO(true)
				.build();

		test_retrieveDefaultAggregation(aggDefaultSO, true);
		test_retrieveDefaultAggregation(aggDefault, false);
		test_retrieveDefaultAggregation(aggDefault, IsSOTrx_NULL);
	}

	@Test
	public void test_retrieveDefaultAggregation_WithDefault_WithDefaultPO()
	{
		final I_C_Aggregation aggDefault = newAggregation()
				.setName("Default")
				.setIsDefault(true)
				.build();
		final I_C_Aggregation aggDefaultPO = newAggregation()
				.setName("DefaultPO")
				.setIsDefaultPO(true)
				.build();

		test_retrieveDefaultAggregation(aggDefault, true);
		test_retrieveDefaultAggregation(aggDefaultPO, false);
		test_retrieveDefaultAggregation(aggDefault, IsSOTrx_NULL);
	}

	@Test
	public void test_retrieveDefaultAggregation_WithDefault_WithDefaultPO_WithDefaultSO()
	{
		final I_C_Aggregation aggDefault = newAggregation()
				.setName("Default")
				.setIsDefault(true)
				.build();
		final I_C_Aggregation aggDefaultPO = newAggregation()
				.setName("DefaultPO")
				.setIsDefaultPO(true)
				.build();
		final I_C_Aggregation aggDefaultSO = newAggregation()
				.setName("DefaultSO")
				.setIsDefaultSO(true)
				.build();

		test_retrieveDefaultAggregation(aggDefaultSO, true);
		test_retrieveDefaultAggregation(aggDefaultPO, false);
		test_retrieveDefaultAggregation(aggDefault, IsSOTrx_NULL);
	}

	@Test
	public void test_retrieveDefaultAggregation_NoDefault_WithDefaultPO_WithDefaultSO()
	{
		final I_C_Aggregation aggDefaultPO = newAggregation()
				.setName("DefaultPO")
				.setIsDefaultPO(true)
				.build();
		final I_C_Aggregation aggDefaultSO = newAggregation()
				.setName("DefaultSO")
				.setIsDefaultSO(true)
				.build();

		test_retrieveDefaultAggregation(aggDefaultSO, true);
		test_retrieveDefaultAggregation(aggDefaultPO, false);
		test_retrieveDefaultAggregation(null, IsSOTrx_NULL);
	}

	private void test_retrieveDefaultAggregation(final I_C_Aggregation expectedAggregation, final Boolean isSOTrx)
	{
		final int adTableId = InterfaceWrapperHelper.getTableId(aggTestModelClass);
		final I_C_Aggregation actualAgg = aggregationDAO.retrieveDefaultAggregationQuery(context.getCtx(), adTableId, isSOTrx, aggregationUsageLevel)
				.create()
				.first(I_C_Aggregation.class);

		Assert.assertThat("Invalid aggregation",
				actualAgg,
				Matchers.equalTo(expectedAggregation));
	}

	private final C_Aggregation_Builder newAggregation()
	{
		return new C_Aggregation_Builder()
				.setContext(context)
				.setAD_Table_ID(InterfaceWrapperHelper.getTableId(aggTestModelClass))
				.setAggregationUsageLevel(aggregationUsageLevel);
	}
}
