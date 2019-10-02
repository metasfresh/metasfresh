package de.metas.impexp.processing;

import java.time.Duration;
import java.time.Instant;
import java.util.OptionalInt;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/**
 * The result of an {@link IImportProcess} execution.
 * 
 * @author tsa
 *
 */
@Value
public final class ImportProcessResult
{
	public static ImportProcessResultCollector newCollector(@NonNull final String targetTableName)
	{
		return new ImportProcessResultCollector(targetTableName);
	}

	/** target table name, where the records were imported (e.g. C_BPartner) */
	private final String targetTableName;
	private final OptionalInt countInsertsIntoTargetTable;
	private final OptionalInt countUpdatesIntoTargetTable;

	/** import table name, FROM where the records are imported (e.g. I_BPartner) */
	private final String importTableName;
	private final OptionalInt countImportRecordsConsidered;
	private final OptionalInt countImportRecordsDeleted;
	private final OptionalInt countImportRecordsWithErrors;

	private final Instant importStartTime;
	private final Instant importEndTime;

	private ImportProcessResult(@NonNull final ImportProcessResultCollector collector)
	{
		this.targetTableName = collector.targetTableName;
		this.countInsertsIntoTargetTable = collector.countInsertsIntoTargetTable.toOptionalInt();
		this.countUpdatesIntoTargetTable = collector.countUpdatesIntoTargetTable.toOptionalInt();

		this.importTableName = collector.importTableName;
		this.countImportRecordsConsidered = collector.countImportRecordsConsidered.toOptionalInt();
		this.countImportRecordsDeleted = collector.countImportRecordsDeleted.toOptionalInt();
		this.countImportRecordsWithErrors = collector.countImportRecordsWithErrors.toOptionalInt();

		this.importStartTime = collector.importStartTime;
		this.importEndTime = Instant.now();
	}

	public String getCountInsertsIntoTargetTableString()
	{
		return counterToString(getCountInsertsIntoTargetTable());
	}

	public String getCountUpdatesIntoTargetTableString()
	{
		return counterToString(getCountUpdatesIntoTargetTable());
	}

	private static String counterToString(final OptionalInt counter)
	{
		return counter.isPresent() ? String.valueOf(counter.getAsInt()) : "N/A";
	}

	public Duration getDuration()
	{
		return Duration.between(getImportStartTime(), getImportEndTime());
	}

	//
	//
	//
	//
	//

	@ToString
	public static class ImportProcessResultCollector
	{
		/** target table name, where the records were imported (e.g. C_BPartner) */
		private final String targetTableName;

		private final Counter countInsertsIntoTargetTable = new Counter();
		private final Counter countUpdatesIntoTargetTable = new Counter();

		private String importTableName;
		private final Counter countImportRecordsConsidered = new Counter();
		private final Counter countImportRecordsDeleted = new Counter();
		private final Counter countImportRecordsWithErrors = new Counter();

		private final Instant importStartTime;

		private ImportProcessResultCollector(@NonNull final String targetTableName)
		{
			this.targetTableName = targetTableName;
			this.importStartTime = Instant.now();
		}

		public ImportProcessResultCollector importTableName(@NonNull final String importTableName)
		{
			this.importTableName = importTableName;
			return this;
		}

		public ImportProcessResult toResult()
		{
			return new ImportProcessResult(this);
		}

		public void addInsertsIntoTargetTable(final int count)
		{
			countInsertsIntoTargetTable.add(count);
		}

		public void addUpdatesIntoTargetTable(final int count)
		{
			countUpdatesIntoTargetTable.add(count);
		}

		public void addCountImportRecordsConsidered(final int count)
		{
			countImportRecordsConsidered.add(count);
		}

		public void setCountImportRecordsDeleted(final int countImportRecordsDeleted)
		{
			Check.assumeGreaterOrEqualToZero(countImportRecordsDeleted, "countImportRecordsDeleted");
			this.countImportRecordsDeleted.set(countImportRecordsDeleted);
		}

		public void setCountImportRecordsWithErrors(final int countImportRecordsWithErrors)
		{
			Check.assumeGreaterOrEqualToZero(countImportRecordsWithErrors, "countImportRecordsWithErrors");
			this.countImportRecordsWithErrors.set(countImportRecordsWithErrors);
		}
	}

	@EqualsAndHashCode
	@ToString
	private static final class Counter
	{
		private boolean unknownValue;
		private int value = 0;

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

		public OptionalInt toOptionalInt()
		{
			return unknownValue ? OptionalInt.empty() : OptionalInt.of(value);
		}
	}
}
