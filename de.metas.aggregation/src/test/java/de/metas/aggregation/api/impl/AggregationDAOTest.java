package de.metas.aggregation.api.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
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
