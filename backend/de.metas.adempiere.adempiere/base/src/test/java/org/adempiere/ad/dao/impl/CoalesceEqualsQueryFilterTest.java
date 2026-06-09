package org.adempiere.ad.dao.impl;

import com.google.common.collect.ImmutableList;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CoalesceEqualsQueryFilterTest
{
	I_Test createRecord(final String description, final String help)
	{
		final I_Test record = InterfaceWrapperHelper.newInstance(I_Test.class);
		record.setDescription(description);
		record.setHelp(help);
		return record;
	}

	@Nested
	public class getSql
	{
		@Test
		void noModifier()
		{
			final CoalesceEqualsQueryFilter<Object> filter = new CoalesceEqualsQueryFilter<>(
					"Value1",
					ImmutableList.of(I_Test.COLUMNNAME_Help, I_Test.COLUMNNAME_Description),
					NullQueryFilterModifier.instance);

			assertThat(filter.getSql()).isEqualTo("COALESCE(Help,Description)=?");
			assertThat(filter.getSqlParams()).containsExactly("Value1");
		}

		@Test
		void noModifier_nullValue()
		{
			final CoalesceEqualsQueryFilter<Object> filter = new CoalesceEqualsQueryFilter<>(
					null,
					ImmutableList.of(I_Test.COLUMNNAME_Help, I_Test.COLUMNNAME_Description),
					NullQueryFilterModifier.instance);

			assertThat(filter.getSql()).isEqualTo("COALESCE(Help,Description) IS NULL");
			assertThat(filter.getSqlParams()).isEmpty();
		}

		@Test
		void dateTruncModifier()
		{
			final CoalesceEqualsQueryFilter<Object> filter = new CoalesceEqualsQueryFilter<>(
					LocalDate.parse("2025-05-06"),
					ImmutableList.of("DateColumn2", "DateColumn1"),
					DateTruncQueryFilterModifier.DAY);

			assertThat(filter.getSql()).isEqualTo("TRUNC(COALESCE(DateColumn2,DateColumn1), 'DD')=TRUNC(?::timestamp, 'DD')");
			assertThat(filter.getSqlParams()).containsExactly(LocalDate.parse("2025-05-06"));
		}

		@Test
		void dateTruncModifier_nullValue()
		{
			final CoalesceEqualsQueryFilter<Object> filter = new CoalesceEqualsQueryFilter<>(
					null,
					ImmutableList.of("DateColumn2", "DateColumn1"),
					DateTruncQueryFilterModifier.DAY);

			assertThat(filter.getSql()).isEqualTo("TRUNC(COALESCE(DateColumn2,DateColumn1), 'DD') IS NULL");
			assertThat(filter.getSqlParams()).isEmpty();
		}
	}

	@Nested
	class accept
	{
		@BeforeEach
		void beforeEach()
		{
			AdempiereTestHelper.get().init();
		}

		@Test
		void noModifier()
		{
			final CoalesceEqualsQueryFilter<I_Test> filter = new CoalesceEqualsQueryFilter<>(
					"Value1",
					ImmutableList.of(I_Test.COLUMNNAME_Help, I_Test.COLUMNNAME_Description),
					NullQueryFilterModifier.instance);

			assertThat(filter.accept(createRecord(null, null))).isFalse();
			assertThat(filter.accept(createRecord("OtherValue", null))).isFalse();
			assertThat(filter.accept(createRecord("Value1", null))).isTrue();
			assertThat(filter.accept(createRecord("Value1", "OtherValue"))).isFalse();
			assertThat(filter.accept(createRecord("Value1", "Value1"))).isTrue();
			assertThat(filter.accept(createRecord("OtherValue", "Value1"))).isTrue();
			assertThat(filter.accept(createRecord(null, "Value1"))).isTrue();
		}

		@Test
		void noModifier_nullValue()
		{
			final CoalesceEqualsQueryFilter<I_Test> filter = new CoalesceEqualsQueryFilter<>(
					null,
					ImmutableList.of(I_Test.COLUMNNAME_Help, I_Test.COLUMNNAME_Description),
					NullQueryFilterModifier.instance);

			assertThat(filter.accept(createRecord(null, null))).isTrue();
			assertThat(filter.accept(createRecord("Value1", null))).isFalse();
			assertThat(filter.accept(createRecord("Value1", "OtherValue"))).isFalse();
			assertThat(filter.accept(createRecord(null, "Value1"))).isFalse();
		}

	}

}