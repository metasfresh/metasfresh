package org.adempiere.ad.table.api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableNameTest
{
	@Test
	void test_toString()
	{
		//noinspection AssertThatObjectExpression
		assertThat(TableName.ofString("MyTable").toString()).isEqualTo("MyTable");
	}

}