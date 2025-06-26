package de.metas.impexp.processing;

import de.metas.impexp.ActualImportRecordsResult;
import de.metas.impexp.ValidateImportRecordsResult;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.OptionalInt;

/**
 * The result of an {@link IImportProcess} execution.
 *
 * @author tsa
 */
@Value
public class ImportProcessResult
{
	public static ImportProcessResultCollector newCollector(@NonNull final String targetTableName)
	{
		return new ImportProcessResultCollector(targetTableName);
	}

	@NonNull Instant importStartTime;
	@NonNull Instant importEndTime;
	@NonNull ValidateImportRecordsResult importRecordsValidation;
	@NonNull ActualImportRecordsResult actualImport;

	private ImportProcessResult(@NonNull final ImportProcessResultCollector collector)
	{
		this.importStartTime = collector.importStartTime;
		this.importEndTime = Instant.now();

		this.importRecordsValidation = ValidateImportRecordsResult.builder()
				.importTableName(collector.importTableName)
				.countImportRecordsDeleted(collector.countImportRecordsDeleted.toIntOr(0))
				.countImportRecordsWithValidationErrors(collector.countImportRecordsWithValidationErrors.toOptionalInt())
				.build();

		this.actualImport = ActualImportRecordsResult.builder()
				.targetTableName(collector.targetTableName)
				.importTableName(collector.importTableName)
				.countImportRecordsConsidered(collector.countImportRecordsConsidered.toOptionalInt())
				.countInsertsIntoTargetTable(collector.countInsertsIntoTargetTable.toOptionalInt())
				.countUpdatesIntoTargetTable(collector.countUpdatesIntoTargetTable.toOptionalInt())
				.errors(collector.actualImportErrors)
				.build();
	}

	public String getSummary()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("Took ").append(getDuration());

		final Duration durationPerRecord = getDurationPerRecord();
		if (durationPerRecord != null)
		{
			sb.append(" (").append(durationPerRecord).append("/record)");
		}

		final String validationSummary = StringUtils.trimBlankToNull(importRecordsValidation.getSummary());
		if (validationSummary != null)
		{
			if (sb.length() > 0) {sb.append("; ");}
			sb.append(validationSummary);
		}

		final String actualImportSummary = StringUtils.trimBlankToNull(actualImport.getSummary());
		if (actualImportSummary != null)
		{
			if (sb.length() > 0) {sb.append("; ");}
			sb.append(actualImportSummary);
		}

		return sb.toString();
	}

	public Duration getDuration()
	{
		return Duration.between(getImportStartTime(), getImportEndTime());
	}

	@Nullable
	private Duration getDurationPerRecord()
	{
		final int recordsCount = actualImport.getCountImportRecordsConsidered().orElse(0);
		if (recordsCount <= 0)
		{
			return null;
		}
		else
		{
			return getDuration().dividedBy(recordsCount);
		}
	}

	//
	//
	//
	//
	//

	@ToString
	public static class ImportProcessResultCollector
	{
		@NonNull private final Instant importStartTime;

		//
		// Records cleanup before validation+import
		@NonNull private final Counter countImportRecordsDeleted = new Counter();

		//
		// Mass validation
		@NonNull private final Counter countImportRecordsWithValidationErrors = new Counter();

		//
		// Actual data import
		@NonNull private String importTableName;
		@NonNull private final String targetTableName;
		@NonNull private final Counter countImportRecordsConsidered = new Counter();
		/**
		 * target table name, where the records were imported (e.g. C_BPartner)
		 */
		@NonNull private final Counter countInsertsIntoTargetTable = new Counter();
		@NonNull private final Counter countUpdatesIntoTargetTable = new Counter();
		@NonNull private final ArrayList<ActualImportRecordsResult.Error> actualImportErrors = new ArrayList<>();

		private ImportProcessResultCollector(@NonNull final String targetTableName)
		{
			this.targetTableName = targetTableName;
			this.importStartTime = Instant.now();
		}

		public ImportProcessResult toResult()
		{
			return new ImportProcessResult(this);
		}

		public ImportProcessResultCollector importTableName(@NonNull final String importTableName)
		{
			this.importTableName = importTableName;
			return this;
		}

		public void setCountImportRecordsDeleted(final int countImportRecordsDeleted)
		{
			Check.assumeGreaterOrEqualToZero(countImportRecordsDeleted, "countImportRecordsDeleted");
			this.countImportRecordsDeleted.set(countImportRecordsDeleted);
		}

		public void setCountImportRecordsWithValidationErrors(final int count)
		{
			Check.assumeGreaterOrEqualToZero(count, "count");
			this.countImportRecordsWithValidationErrors.set(count);
		}

		public void addCountImportRecordsConsidered(final int count)
		{
			countImportRecordsConsidered.add(count);
		}

		public boolean isCountImportRecordsConsidered() {return countImportRecordsConsidered.isGreaterThanZero();}

		public void addInsertsIntoTargetTable(final int count)
		{
			countInsertsIntoTargetTable.add(count);
		}

		public void addUpdatesIntoTargetTable(final int count)
		{
			countUpdatesIntoTargetTable.add(count);
		}

		public void actualImportError(@NonNull final ActualImportRecordsResult.Error error)
		{
			actualImportErrors.add(error);
		}
	}

	@EqualsAndHashCode
	private static final class Counter
	{
		private boolean unknownValue;
		private int value = 0;

		@Override
		public String toString()
		{
			return unknownValue ? "N/A" : String.valueOf(value);
		}

		public void set(final int value)
		{
			if (value < 0)
			{
				throw new AdempiereException("value shall NOT be negative: " + value);
			}
			this.value = value;
			this.unknownValue = false;
		}

		public void add(final int valueToAdd)
		{
			Check.assumeGreaterOrEqualToZero(valueToAdd, "valueToAdd");
			set(this.value + valueToAdd);
		}

		public OptionalInt toOptionalInt() {return unknownValue ? OptionalInt.empty() : OptionalInt.of(value);}

		public int toIntOr(final int defaultValue) {return unknownValue ? defaultValue : value;}

		public boolean isGreaterThanZero() {return !unknownValue && value > 0;}
	}
}
