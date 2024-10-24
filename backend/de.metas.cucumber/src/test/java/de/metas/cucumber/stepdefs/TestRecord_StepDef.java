package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.context.SharedTestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_Test;
import org.compiere.util.TimeUtil;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

public class TestRecord_StepDef
{
	private static final ImmutableSet<String> TEST_TIMEZONE_IDS = ImmutableSet.of(
			"Pacific/Midway", // -11:00
			"US/Alaska", // -09:00,
			"America/Jamaica", // -05:00
			"Atlantic/Azores", // -01:00
			"UTC",
			"Europe/Berlin",  // +01:00
			"Europe/Bucharest", // +02:00
			"Asia/Kolkata", // +05:30
			"Asia/Tokyo", // +09:00
			"Pacific/Kiritimati" // +14:00
	);

	@And("using Test record, validate save and load of following fields:")
	public void saveAndLoad(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final LocalDate expectedDate = row.getAsOptionalLocalDate("T_Date").orElse(null);
			final Instant expectedDateTime = row.getAsOptionalString("T_DateTime").map(Instant::parse).orElse(null);
			final LocalTime expectedTime = row.getAsOptionalString("T_Time").map(LocalTime::parse).orElse(null);
			forEachTestTimeZone(() -> testDates(expectedDate, expectedDateTime, expectedTime));
		});
	}

	private void testDates(
			@Nullable final LocalDate expectedDate,
			@Nullable final Instant expectedDateTime,
			@Nullable final LocalTime expectedTime)
	{
		//
		// Save record
		final int recordId;
		{
			final I_Test record = InterfaceWrapperHelper.newInstance(I_Test.class);
			record.setName("Test " + Instant.now());

			if (expectedDate != null)
			{
				record.setT_Date(Timestamp.valueOf(expectedDate.atStartOfDay()));
			}
			if (expectedDateTime != null)
			{
				record.setT_DateTime(Timestamp.from(expectedDateTime));
			}
			if (expectedTime != null)
			{
				record.setT_Time(TimeUtil.asTimestamp(expectedTime));
			}

			InterfaceWrapperHelper.save(record);
			SharedTestContext.put("record.afterSave", record);
			recordId = record.getTest_ID();
		}

		//
		// Load record and test
		{
			final I_Test record = InterfaceWrapperHelper.load(recordId, I_Test.class);
			SharedTestContext.put("record.afterReload", record);

			if (expectedDate != null)
			{
				final Timestamp actualTS = record.getT_Date();
				final LocalDate actual = actualTS != null ? actualTS.toLocalDateTime().toLocalDate() : null;
				assertThat(actual).as("T_Date").isEqualTo(expectedDate);
			}
			if (expectedDateTime != null)
			{
				final Timestamp actualTS = record.getT_DateTime();
				final Instant actual = actualTS != null ? actualTS.toInstant() : null;
				assertThat(actual).as("T_DateTime").isEqualTo(expectedDateTime);
			}
			if (expectedTime != null)
			{
				final Timestamp actualTS = record.getT_Time();
				final LocalTime actual = actualTS != null ? actualTS.toLocalDateTime().toLocalTime() : null;
				assertThat(actual).as("T_Time").isEqualTo(expectedTime);
			}
		}
	}

	private void forEachTestTimeZone(final Runnable runnable)
	{
		for (final String timezoneId : TEST_TIMEZONE_IDS)
		{
			try (final IAutoCloseable ignored = temporaryChangeJVMTimezone(timezoneId))
			{
				runnable.run();
			}
		}
	}

	private IAutoCloseable temporaryChangeJVMTimezone(String timezoneId)
	{
		final TimeZone previousTimeZone = TimeZone.getDefault();
		TimeZone.setDefault(TimeZone.getTimeZone(timezoneId));
		assertThat(TimeZone.getDefault()).isEqualTo(TimeZone.getTimeZone(timezoneId));
		SharedTestContext.put("timezoneId", timezoneId);
		return () -> TimeZone.setDefault(previousTimeZone);
	}
}
