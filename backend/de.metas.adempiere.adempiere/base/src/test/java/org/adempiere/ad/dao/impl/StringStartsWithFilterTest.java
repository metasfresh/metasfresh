package org.adempiere.ad.dao.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringStartsWithFilterTest
{
	@Nested
	class accept
	{
		private I_Test record;

		@BeforeEach
		void beforeEach()
		{
			AdempiereTestHelper.get().init();
			record = InterfaceWrapperHelper.newInstance(I_Test.class);
		}

		@Test
		void name_123456_startsWith_123()
		{
			record.setName("123456");
			final StringStartsWithFilter<I_Test> filter = new StringStartsWithFilter<>(I_Test.COLUMNNAME_Name, "123");
			assertThat(filter.accept(record)).isTrue();
		}

		@Test
		void name_123456_doesNotStartWith_1235()
		{
			record.setName("123456");
			final StringStartsWithFilter<I_Test> filter = new StringStartsWithFilter<>(I_Test.COLUMNNAME_Name, "1235");
			assertThat(filter.accept(record)).isFalse();
		}
	}

	@Nested
	class getSql
	{
		@Test
		void standardCase()
		{
			final StringStartsWithFilter<I_Test> filter = new StringStartsWithFilter<>(I_Test.COLUMNNAME_Name, "123");
			assertThat(filter.getSql()).isEqualTo("Name LIKE ? ESCAPE '\\'");
			assertThat(filter.getSqlParams()).containsExactly("123%");
		}

		@Test
		void prefixContainsPercentSign()
		{
			final StringStartsWithFilter<I_Test> filter = new StringStartsWithFilter<>(I_Test.COLUMNNAME_Name, "123%");
			assertThat(filter.getSql()).isEqualTo("Name LIKE ? ESCAPE '\\'");
			assertThat(filter.getSqlParams()).containsExactly("123\\%%");
		}
	}
}


