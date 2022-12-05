package org.adempiere.ad.table.api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnNameFQTest
{
	@Test
	void test_toString()
	{
		//noinspection AssertThatObjectExpression
		assertThat(ColumnNameFQ.ofTableAndColumnName("MyTable", "MyColumn").toString())
				.isEqualTo("MyTable.MyColumn");
	}

}