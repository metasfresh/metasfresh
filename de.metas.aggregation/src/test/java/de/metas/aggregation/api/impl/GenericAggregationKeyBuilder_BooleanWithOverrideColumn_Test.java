package de.metas.aggregation.api.impl;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.aggregation.api.Aggregation;
import de.metas.aggregation.api.AggregationId;
import de.metas.aggregation.api.AggregationItem;
import de.metas.aggregation.api.AggregationItem.Type;
import de.metas.aggregation.api.AggregationItemId;
import de.metas.aggregation.api.AggregationKey;

/**
 * Tests that {@link GenericAggregationKeyBuilder} is correclty handling boolean columns and overrides.
 * 
 * The keys shall always use normalized boolean values even if the value if fetched from an override column.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08612_IsTaxIncluded_Aggregation_No_%28108906119293%29
 */
public class GenericAggregationKeyBuilder_BooleanWithOverrideColumn_Test
{
	private static final String AGGREGATION_KEY_PART_True = "true";
	private static final String AGGREGATION_KEY_PART_False = "false";

	private static final Boolean VALUE_False = false;
	private static final Boolean VALUE_True = true;
	private static final Boolean VALUE_Null = null;
	private static final String VALUE_OVERRIDE_False = "N";
	private static final String VALUE_OVERRIDE_True = "Y";
	private static final String VALUE_OVERRIDE_Null = null;

	private IContextAware contextProvider;
	private Aggregation aggregation;
	private GenericAggregationKeyBuilder<I_ModelWithBooleanOverride> aggregationKeyBuilder;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		contextProvider = new PlainContextAware(Env.getCtx());

		//
		// Create an aggregation to test
		int aggregationItemId = 1;
		this.aggregation = Aggregation.builder()
				.id(AggregationId.ofRepoId(1))
				.tableName(I_ModelWithBooleanOverride.Table_Name)
				.item(AggregationItem.builder()
						.id(AggregationItemId.ofRepoId(aggregationItemId++))
						.type(Type.ModelColumn)
						.columnName(I_ModelWithBooleanOverride.COLUMNNAME_IsTaxIncluded)
						.displayType(DisplayType.YesNo)
						.attribute(null)
						.includeLogic(ConstantLogicExpression.TRUE)
						.build())
				.build();

		//
		// Create aggregation key builder
		this.aggregationKeyBuilder = new GenericAggregationKeyBuilder<>(I_ModelWithBooleanOverride.class, aggregation);
	}

	private AggregationKey createExpectedAggregationKey(final String columnName, final Object value)
	{
		final String keyString = AggregationId.toRepoId(aggregation.getId())
				+ "#"
				+ columnName + "=" + value;

		return new AggregationKey(keyString, aggregation.getId());
	}

	@Test
	public void test_NothingSet()
	{
		testBoolean(
				VALUE_Null, // value,
				VALUE_OVERRIDE_Null, // valueOverride,
				AGGREGATION_KEY_PART_False // expectedKeyPart
		);
	}

	@Test
	public void test_ValueFalse_NoOverride()
	{
		testBoolean(
				VALUE_False, // value
				VALUE_OVERRIDE_Null, // valueOverride,
				AGGREGATION_KEY_PART_False // expectedKeyPart
		);
	}

	@Test
	public void test_ValueFalse_OverrideToNo()
	{
		testBoolean(
				VALUE_False, // value
				VALUE_OVERRIDE_False, // valueOverride,
				AGGREGATION_KEY_PART_False // expectedKeyPart
		);
	}

	@Test
	public void test_ValueFalse_OverrideToYes()
	{
		testBoolean(
				VALUE_False, // value
				VALUE_OVERRIDE_True, // valueOverride,
				AGGREGATION_KEY_PART_True // expectedKeyPart
		);
	}

	@Test
	public void test_ValueTrue_NoOverride()
	{
		testBoolean(
				VALUE_True, // value
				VALUE_OVERRIDE_Null, // valueOverride,
				AGGREGATION_KEY_PART_True // expectedKeyPart
		);
	}

	@Test
	public void test_ValueTrue_OverrideToNo()
	{
		testBoolean(
				VALUE_True, // value
				VALUE_OVERRIDE_False, // valueOverride,
				AGGREGATION_KEY_PART_False // expectedKeyPart
		);
	}

	@Test
	public void test_ValueTrue_OverrideToYes()
	{
		testBoolean(
				VALUE_True, // value
				VALUE_OVERRIDE_True, // valueOverride,
				AGGREGATION_KEY_PART_True // expectedKeyPart
		);
	}

	private final void testBoolean(final Boolean value, final String valueOverride, final String expectedKeyPart)
	{
		final AggregationKey aggregationKeyExpected = createExpectedAggregationKey(I_ModelWithBooleanOverride.COLUMNNAME_IsTaxIncluded, expectedKeyPart);

		//
		// Create the model instance
		final I_ModelWithBooleanOverride model = InterfaceWrapperHelper.newInstance(I_ModelWithBooleanOverride.class, contextProvider);
		if (value != null)
		{
			model.setIsTaxIncluded(value);
		}
		model.setIsTaxIncluded_Override(valueOverride);

		final AggregationKey aggregationKeyActual = aggregationKeyBuilder.buildAggregationKey(model);
		Assert.assertEquals(aggregationKeyExpected, aggregationKeyActual);
	}

	public interface I_ModelWithBooleanOverride
	{
		String Table_Name = "ModelWithBooleanOverride";

		//@formatter:off
	    String COLUMNNAME_IsTaxIncluded_Override = "IsTaxIncluded_Override";
		java.lang.String getIsTaxIncluded_Override();
		void setIsTaxIncluded_Override (java.lang.String IsTaxIncluded_Override);
		//@formatter:on

		//@formatter:off
		String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";
		void setIsTaxIncluded (boolean IsTaxIncluded);
		boolean isTaxIncluded();
		//@formatter:on
	}

}
