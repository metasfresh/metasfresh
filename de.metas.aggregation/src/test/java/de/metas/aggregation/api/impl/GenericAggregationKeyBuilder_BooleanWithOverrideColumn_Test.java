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


import java.util.Arrays;
import java.util.List;

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

import de.metas.aggregation.api.IAggregationAttribute;
import de.metas.aggregation.api.IAggregationItem;
import de.metas.aggregation.api.IAggregationItem.Type;
import de.metas.aggregation.api.IAggregationKey;

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
	private int aggregationId;
	private Aggregation aggregation;
	private GenericAggregationKeyBuilder<I_ModelWithBooleanOverride> aggregationKeyBuilder;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		contextProvider = new PlainContextAware(Env.getCtx());

		//
		// Create an aggregation to test
		this.aggregationId = 1;
		int aggregationItemId = 1;
		final List<IAggregationItem> aggregationItems = Arrays.<IAggregationItem> asList(
				new AggregationItem(aggregationItemId++,
						Type.ModelColumn,
						I_ModelWithBooleanOverride.COLUMNNAME_IsTaxIncluded,
						DisplayType.YesNo,
						IAggregationAttribute.NULL,
						ConstantLogicExpression.TRUE)
				);
		this.aggregation = new Aggregation(
				I_ModelWithBooleanOverride.Table_Name,
				aggregationItems,
				aggregationId);

		//
		// Create aggregation key builder
		this.aggregationKeyBuilder = new GenericAggregationKeyBuilder<>(I_ModelWithBooleanOverride.class, aggregation);
	}

	private AggregationKey createExpectedAggregationKey(final String columnName, final Object value)
	{
		final String keyString = aggregationId
				+ "#"
				+ columnName + "=" + value;

		return new AggregationKey(keyString, aggregationId);
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

		final IAggregationKey aggregationKeyActual = aggregationKeyBuilder.buildAggregationKey(model);
		Assert.assertEquals(aggregationKeyExpected, aggregationKeyActual);
	}

	public static interface I_ModelWithBooleanOverride
	{
		public static final String Table_Name = "ModelWithBooleanOverride";

		//@formatter:off
	    public static final String COLUMNNAME_IsTaxIncluded_Override = "IsTaxIncluded_Override";
		public java.lang.String getIsTaxIncluded_Override();
		public void setIsTaxIncluded_Override (java.lang.String IsTaxIncluded_Override);
		//@formatter:on

		//@formatter:off
		public static final String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";
		public void setIsTaxIncluded (boolean IsTaxIncluded);
		public boolean isTaxIncluded();
		//@formatter:on
	}

}
