package de.metas.aggregation.api.impl;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.compiere.util.DisplayType;
import org.junit.Assert;
import org.junit.Test;

import de.metas.aggregation.api.Aggregation;
import de.metas.aggregation.api.AggregationItem;
import de.metas.aggregation.api.AggregationItem.Type;
import de.metas.aggregation.api.AggregationItemId;

/**
 * Tests {@link Aggregation}
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
		int aggregationItemId = 1;
		final Aggregation aggregation = Aggregation.builder()
				.id(null)
				.tableName("MyTable")
				.item(AggregationItem.builder()
						.id(AggregationItemId.ofRepoId(aggregationItemId++))
						.type(Type.ModelColumn)
						.columnName("Column1")
						.displayType(DisplayType.String)
						.includeLogic(ConstantLogicExpression.TRUE)
						.build())
				.item(AggregationItem.builder()
						.id(AggregationItemId.ofRepoId(aggregationItemId++))
						.type(Type.ModelColumn)
						.columnName("Column2")
						.displayType(DisplayType.String)
						.includeLogic(ConstantLogicExpression.TRUE)
						.build())
				.build();

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
