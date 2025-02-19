package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.context.ContextAwareDescription;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_Test;
import org.compiere.util.DB;
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
		String dbTimezone = getDBTimezone();
		System.out.println("Database timezone: " + dbTimezone);

		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach((row) -> {
			SharedTestContext.put("dbTimezone", dbTimezone);

			final LocalDate expectedDate = row.getAsOptionalLocalDate("T_Date").orElse(null);
			final Instant expectedDateTime = row.getAsOptionalString("T_DateTime").map(Instant::parse).orElse(null);
			final LocalTime expectedTime = row.getAsOptionalString("T_Time").map(LocalTime::parse).orElse(null);
			forEachTestTimeZone(() -> testDates(softly, expectedDate, expectedDateTime, expectedTime));
		});

		softly.assertAll();
	}

	private void testDates(
			@NonNull final SoftAssertions softly,
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
				final Timestamp expectedDateTS = Timestamp.valueOf(expectedDate.atStartOfDay());
				SharedTestContext.put("expectedDateTS", expectedDateTS);
				SharedTestContext.put("expectedDate", expectedDate);

				record.setT_Date(expectedDateTS);
			}
			if (expectedDateTime != null)
			{
				final Timestamp expectedDateTimeTS = Timestamp.from(expectedDateTime);
				SharedTestContext.put("expectedDateTimeTS", expectedDateTimeTS);
				SharedTestContext.put("expectedDateTime", expectedDateTimeTS);

				record.setT_DateTime(expectedDateTimeTS);
			}
			if (expectedTime != null)
			{
				final Timestamp expectedTimeTS = TimeUtil.asTimestamp(expectedTime);
				SharedTestContext.put("expectedTimeTS", expectedTimeTS);
				SharedTestContext.put("expectedTime", expectedTime);

				record.setT_Time(expectedTimeTS);
			}

			record.setHelp(SharedTestContext.getAsString());
			InterfaceWrapperHelper.save(record);
			SharedTestContext.put("record.afterSave", record);
			recordId = record.getTest_ID();
		}

		//
		// Load record and test
		{
			final I_Test record = InterfaceWrapperHelper.load(recordId, I_Test.class);
			SharedTestContext.put("record.afterReload", record);

			final LocalDate actualDate;
			if (expectedDate != null)
			{
				final Timestamp actualDateTS = record.getT_Date();
				actualDate = actualDateTS != null ? actualDateTS.toLocalDateTime().toLocalDate() : null;
				SharedTestContext.put("actualDate", actualDate);
				SharedTestContext.put("actualDateTS", actualDateTS);
			}
			else
			{
				actualDate = null;
			}

			final Instant actualDateTime;
			if (expectedDateTime != null)
			{
				final Timestamp actualDateTimeTS = record.getT_DateTime();
				actualDateTime = actualDateTimeTS != null ? actualDateTimeTS.toInstant() : null;
				SharedTestContext.put("actualDateTime", actualDateTime);
				SharedTestContext.put("actualDateTimeTS", actualDateTimeTS);
			}
			else
			{
				actualDateTime = null;
			}

			final LocalTime actualTime;
			if (expectedTime != null)
			{
				final Timestamp actualTimeTS = record.getT_Time();
				actualTime = actualTimeTS != null ? actualTimeTS.toLocalDateTime().toLocalTime() : null;
				SharedTestContext.put("actualTime", actualTime);
				SharedTestContext.put("actualTimeTS", actualTimeTS);
			}
			else
			{
				actualTime = null;
			}

			record.setHelp(SharedTestContext.getAsString());
			InterfaceWrapperHelper.save(record);

			softly.assertThat(actualDate).as(ContextAwareDescription.ofString("T_Date")).isEqualTo(expectedDate);
			softly.assertThat(actualDateTime).as(ContextAwareDescription.ofString("T_DateTime")).isEqualTo(expectedDateTime);
			softly.assertThat(actualTime).as(ContextAwareDescription.ofString("T_Time")).isEqualTo(expectedTime);
		}
	}
	
	private void forEachTestTimeZone(final Runnable runnable)
	{
		for (final String timezoneId : TEST_TIMEZONE_IDS)
		{
			try (final IAutoCloseable ignored = temporaryChangeJVMTimezone(timezoneId))
			{
				SharedTestContext.run(() -> {
					SharedTestContext.put("jvm.timezoneId", TimeZone.getDefault());
					runnable.run();
				});
			}
			catch (Error | RuntimeException ex)
			{
				throw ex;
			}
			catch (Throwable ex)
			{
				throw AdempiereException.wrapIfNeeded(ex);
			}
		}
	}

	private IAutoCloseable temporaryChangeJVMTimezone(String timezoneId)
	{
		final TimeZone previousTimeZone = TimeZone.getDefault();
		TimeZone.setDefault(TimeZone.getTimeZone(timezoneId));
		assertThat(TimeZone.getDefault()).isEqualTo(TimeZone.getTimeZone(timezoneId));
		
		return () -> TimeZone.setDefault(previousTimeZone);
	}

	private String getDBTimezone()
	{
		return DB.getSQLValueStringEx(ITrx.TRXNAME_None, "SELECT current_setting('timezone')");
	}
}
