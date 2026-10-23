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
	private static final int SEQUENCE_ID = 556360;
	private static final int OTHER_SEQUENCE_ID = 556359;

	private ISysConfigBL sysConfigBL;
	private DateSequenceProvider provider;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		sysConfigBL = Services.get(ISysConfigBL.class);
		provider = new DateSequenceProvider();
	}

	private static Evaluatee movementDate(final LocalDate date)
	{
		return Evaluatees.mapBuilder().put("MovementDate", TimeUtil.asTimestamp(date)).build();
	}

	private static DocumentSequenceInfo sequence(final int adSequenceId, @Nullable final String decimalPattern)
	{
		return DocumentSequenceInfo.builder()
				.adSequenceId(adSequenceId)
				.name("seq")
				.decimalPattern(decimalPattern)
				.dateColumn("MovementDate")
				.build();
	}

	private void setGlobalFormat(final String format)
	{
		sysConfigBL.setValue(SYSCONFIG_DATE_FORMAT, format, ClientId.SYSTEM, OrgId.ANY);
	}

	private void setSequenceFormat(final int adSequenceId, final String format)
	{
		sysConfigBL.setValue(SYSCONFIG_DATE_FORMAT + "." + adSequenceId, format, ClientId.SYSTEM, OrgId.ANY);
	}

	@Test
	void globalFormat_dateOnlySequence()
	{
		setGlobalFormat("yy-DDD");

		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 10, 23)), sequence(SEQUENCE_ID, null), "17"))
				.isEqualTo("26-296");
	}

	/**
	 * A per-sequence format wins over the global one, for that sequence only: e.g. a date-only production lot
	 * next to a material-receipt lot that keeps the global format plus its counter.
	 */
	@Test
	void perSequenceFormat_overridesGlobal_onlyForThatSequence()
	{
		setGlobalFormat("yyMMdd");
		setSequenceFormat(SEQUENCE_ID, "yy-DDD");

		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 10, 23)), sequence(SEQUENCE_ID, null), "17"))
				.isEqualTo("26-296");
		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 10, 23)), sequence(OTHER_SEQUENCE_ID, "0000"), "17"))
				.isEqualTo("2610230017");
	}

	/**
	 * yy is the calendar year; YY would be the week-based year, which already belongs to the next year in the last days of December.
	 */
	@Test
	void perSequenceFormat_calendarYearAtYearEnd()
	{
		setGlobalFormat("yyMMdd");
		setSequenceFormat(SEQUENCE_ID, "yy-DDD");

		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 12, 31)), sequence(SEQUENCE_ID, null), null))
				.isEqualTo("26-365");
	}

	@Test
	void perSequenceFormatBlank_fallsBackToGlobal()
	{
		setGlobalFormat("yy-DDD");
		setSequenceFormat(SEQUENCE_ID, " ");

		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 10, 23)), sequence(SEQUENCE_ID, null), null))
				.isEqualTo("26-296");
	}

	@Test
	void noSequenceId_usesGlobal()
	{
		setGlobalFormat("yy-DDD");

		assertThat(provider.provideSequenceNo(movementDate(LocalDate.of(2026, 10, 23)), sequence(0, null), null))
				.isEqualTo("26-296");
	}

	@Test
	void dateOnlySequence_isIndependentOfIncrement()
	{
		assertThat(provider.isSequenceNoIndependentOfIncrement(sequence(SEQUENCE_ID, null))).isTrue();
		assertThat(provider.isSequenceNoIndependentOfIncrement(sequence(SEQUENCE_ID, "0000"))).isFalse();
	}
}
