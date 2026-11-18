package org.adempiere.ad.dao.impl;

import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link AddDurationQueryFilterModifier} through a {@link CompareQueryFilter}, in SQL and in memory.
 */
class AddDurationQueryFilterModifierTest
{
	public interface ITestModel
	{
		String Table_Name = "TestModel";

		String COLUMNNAME_Date = "Date";

		Timestamp getDate();

		void setDate(Timestamp date);

		String COLUMNNAME_OtherDate = "OtherDate";

		Timestamp getOtherDate();

		void setOtherDate(Timestamp otherDate);
	}

	private static final Instant DATE = Instant.parse("2026-11-17T10:00:00Z");

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void sql_columnPlusConstantInterval_comparedWithColumn()
	{
		final CompareQueryFilter<ITestModel> filter = new CompareQueryFilter<>(
				ITestModel.COLUMNNAME_Date,
				Operator.GREATER,
				ModelColumnNameValue.forColumnName(ITestModel.COLUMNNAME_OtherDate),
				new AddDurationQueryFilterModifier(Duration.ofHours(24)));

		assertThat(filter.getSql()).isEqualTo("(Date + INTERVAL '86400 seconds') > OtherDate");
		assertThat(filter.getSqlParams(null)).isEmpty();
	}

	@Test
	void sql_constantOperandIsPassedAsParameterUnchanged()
	{
		final Timestamp value = Timestamp.from(DATE);
		final CompareQueryFilter<ITestModel> filter = new CompareQueryFilter<>(
				ITestModel.COLUMNNAME_Date,
				Operator.LESS_OR_EQUAL,
				value,
				new AddDurationQueryFilterModifier(Duration.ofMinutes(90)));

		assertThat(filter.getSql()).isEqualTo("(Date + INTERVAL '5400 seconds') <= ?");
		assertThat(filter.getSqlParams(null)).containsExactly(value);
	}

	@Test
	void accept_addsTheDurationToTheColumnOnly()
	{
		final Duration duration = Duration.ofHours(24);

		// Date + 24 h compared with OtherDate, around the boundary
		assertGreater(false, DATE, DATE.plus(duration).plusSeconds(1), duration);
		assertGreater(false, DATE, DATE.plus(duration), duration);
		assertGreater(true, DATE, DATE.plus(duration).minusSeconds(1), duration);
		assertGreater(true, DATE, DATE, duration);
	}

	@Test
	void convertValue_columnNull_staysNull()
	{
		final AddDurationQueryFilterModifier modifier = new AddDurationQueryFilterModifier(Duration.ofHours(1));

		assertThat(modifier.convertValue(ITestModel.COLUMNNAME_Date, null, null)).isNull();
	}

	@Test
	void convertValue_constantUnchanged()
	{
		final Timestamp value = Timestamp.from(DATE);
		final AddDurationQueryFilterModifier modifier = new AddDurationQueryFilterModifier(Duration.ofHours(3));

		assertThat(modifier.convertValue(AddDurationQueryFilterModifier.COLUMNNAME_Constant, value, null)).isSameAs(value);
		assertThat(modifier.convertValue(ITestModel.COLUMNNAME_Date, value, null)).isEqualTo(Timestamp.from(DATE.plusSeconds(3 * 3600)));
	}

	@Test
	void getValueSql_column_returnsColumnName()
	{
		final List<Object> params = new ArrayList<>();
		final String sql = new AddDurationQueryFilterModifier(Duration.ofHours(1))
				.getValueSql(ModelColumnNameValue.forColumnName(ITestModel.COLUMNNAME_OtherDate), params);

		assertThat(sql).isEqualTo(ITestModel.COLUMNNAME_OtherDate);
		assertThat(params).isEmpty();
	}

	private void assertGreater(final boolean expected, final Instant date, final Instant otherDate, final Duration duration)
	{
		final ITestModel model = createModel(Timestamp.from(date), Timestamp.from(otherDate));
		assertThat(greaterThanOtherDate(duration).accept(model))
				.as("Date=%s + %s > OtherDate=%s", date, duration, otherDate)
				.isEqualTo(expected);
	}

	private static CompareQueryFilter<ITestModel> greaterThanOtherDate(final Duration duration)
	{
		return new CompareQueryFilter<>(
				ITestModel.COLUMNNAME_Date,
				Operator.GREATER,
				ModelColumnNameValue.forColumnName(ITestModel.COLUMNNAME_OtherDate),
				new AddDurationQueryFilterModifier(duration));
	}

	private static ITestModel createModel(final Timestamp date, final Timestamp otherDate)
	{
		final ITestModel model = InterfaceWrapperHelper.create(Env.getCtx(), ITestModel.class, ITrx.TRXNAME_None);
		model.setDate(date);
		model.setOtherDate(otherDate);
		InterfaceWrapperHelper.save(model);
		return model;
	}
}
