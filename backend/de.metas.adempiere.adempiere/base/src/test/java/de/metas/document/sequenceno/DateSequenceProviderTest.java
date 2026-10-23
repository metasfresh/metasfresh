package de.metas.document.sequenceno;

import de.metas.document.DocumentSequenceInfo;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateSequenceProviderTest
{
	private static final String SYSCONFIG_DATE_FORMAT = "de.metas.document.seqNo.DateSequenceProvider.dateFormat";

	private DateSequenceProvider provider;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		provider = new DateSequenceProvider();
	}

	private static Evaluatee movementDate(final LocalDate date)
	{
		return Evaluatees.mapBuilder().put("MovementDate", TimeUtil.asTimestamp(date)).build();
	}

	private static DocumentSequenceInfo sequence(@Nullable final String dateFormat, @Nullable final String decimalPattern)
	{
		return DocumentSequenceInfo.builder()
				.adSequenceId(556360)
				.name("seq")
				.dateFormat(dateFormat)
				.decimalPattern(decimalPattern)
				.dateColumn("MovementDate")
				.build();
	}

	private static void setGlobalFormat(final String format)
	{
		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_DATE_FORMAT, format, ClientId.SYSTEM, OrgId.ANY);
	}

	@Test
	void sequenceDateFormat_wins_overGlobal()
	{
		setGlobalFormat("yyMMdd");

		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 10, 23)), sequence("yy-DDD", null), "17"))
				.isEqualTo("26-296");
	}

	@Test
	void sequenceDateFormatEmpty_globalApplies_withCounter()
	{
		setGlobalFormat("yyMMdd");

		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 10, 23)), sequence(null, "0000"), "17"))
				.isEqualTo("2610230017");
		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 10, 23)), sequence(" ", null), null))
				.isEqualTo("261023");
	}

	@Test
	void globalFormat_dateOnlySequence()
	{
		setGlobalFormat("yy-DDD");

		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 10, 23)), sequence(null, null), "17"))
				.isEqualTo("26-296");
	}

	/**
	 * yy is the calendar year; YY would be the week-based year, which already belongs to the next year in the last days of December.
	 */
	@Test
	void sequenceDateFormat_calendarYearAtYearEnd()
	{
		setGlobalFormat("yyMMdd");

		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 12, 31)), sequence("yy-DDD", null), null))
				.isEqualTo("26-365");
	}

	@Test
	void dateOnlySequence_isIndependentOfIncrement()
	{
		assertThat(provider.isSequenceNoIndependentOfIncrement(sequence("yy-DDD", null))).isTrue();
		assertThat(provider.isSequenceNoIndependentOfIncrement(sequence("yy-DDD", "0000"))).isFalse();
	}
}
