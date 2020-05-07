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
import org.compiere.util.DisplayType;
import org.junit.Assert;
import org.junit.Test;

import de.metas.aggregation.api.IAggregation;
import de.metas.aggregation.api.IAggregationAttribute;
import de.metas.aggregation.api.IAggregationItem;
import de.metas.aggregation.api.IAggregationItem.Type;

/**
 * Tests {@link IAggregation}
 *
 * @author tsa
 *
 */
public class AggregationTest
{
	@Test
	public void test_hasColumnName()
	{
		//
		// Create an aggregation to test
		final int aggregationId = 1;
		int aggregationItemId = 1;
		final List<IAggregationItem> aggregationItems = Arrays.<IAggregationItem> asList(
				new AggregationItem(aggregationItemId++, Type.ModelColumn, "Column1", DisplayType.String, IAggregationAttribute.NULL, ConstantLogicExpression.TRUE)
				, new AggregationItem(aggregationItemId++, Type.ModelColumn, "Column2", DisplayType.String, IAggregationAttribute.NULL, ConstantLogicExpression.TRUE)
				);
		final Aggregation aggregation = new Aggregation("MyTable", aggregationItems, aggregationId);

		//
		// Test:
		testHasColumnName(true, aggregation, "Column1");
		testHasColumnName(true, aggregation, "Column2");
		testHasColumnName(false, aggregation, "Column3");
	}

	private void testHasColumnName(final boolean hasColumnNameExpected, final Aggregation aggregation, final String columnName)
	{
		final String message = "Invalid hasColumnName(" + columnName + ")"
				+ "\n Aggregation: " + aggregation;
		Assert.assertEquals(message, hasColumnNameExpected, aggregation.hasColumnName(columnName));
	}
}
