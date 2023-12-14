package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessClassInfo;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Process;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AD_Process_StepDef
{
	private static final Logger logger = LogManager.getLogger(AD_Process_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("validate all AD_Processes, except:")
	public void validateAD_Processes(@NonNull final DataTable dataTable)
	{
		final ImmutableSet<AdProcessId> adProcessIdsToSkip = getAdProcessIdsToSkip(dataTable);

		final AtomicInteger countTotal = new AtomicInteger();
		final ErrorsCollector errorsCollector = new ErrorsCollector();
		final SoftAssertions softly = new SoftAssertions();

		final ImmutableMap<AdProcessId, I_AD_Process> adProcesses = queryBL.createQueryBuilder(I_AD_Process.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_Process.COLUMNNAME_EntityType)
				.orderBy(I_AD_Process.COLUMNNAME_AD_Process_ID)
				.toMap(AD_Process_StepDef::extractAdProcessId);

		//
		// Check each AD_Process entry to be valid
		adProcesses.forEach((adProcessId, adProcess) -> {
			if (adProcessIdsToSkip.contains(adProcessId))
			{
				logger.info("{} is Skipped", getSummary(adProcess));
				return;
			}

			countTotal.incrementAndGet();

			softly.assertThatCode(() -> validateAD_Process(adProcess, errorsCollector))
					.as(() -> getSummary(adProcess))
					.doesNotThrowAnyException();
		});

		//
		// Hygiene of the skip list:
		adProcessIdsToSkip.forEach(adProcessId -> {
			final I_AD_Process adProcess = adProcesses.get(adProcessId);
			softly.assertThat(adProcess)
					.as(() -> "Expect skipped " + adProcessId + " to have an AD_Process entry but it's missing. Feel free to remove it from the skip list.")
					.isNotNull();

			softly.assertThatCode(() -> validateAD_Process(adProcess, null))
					.as(() -> "Expect skipped " + adProcessId + " to fail but it's a success. NICE! Feel free to remove it from the skip list.")
					.isInstanceOf(Throwable.class); // any exception
		});

		//
		// Help developer to skip more records if needed
		logger.info("{} AD_Process(es) were checked", countTotal);
		if (!errorsCollector.isEmpty())
		{
			logger.info("Found {} errors. To Ignore them, in your feature file use: \n{}", errorsCollector.count(), errorsCollector.toCucumberTable());
		}

		//
		// IMPORTANT: actually execute all asserts
		softly.assertAll();
	}

	private void validateAD_Process(@NonNull final I_AD_Process adProcess, @Nullable final ErrorsCollector errorsCollector)
	{
		final String className = StringUtils.trimBlankToNull(adProcess.getClassname());
		if (className == null)
		{
			return;
		}

		final Class<?> processClass;
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = ProcessClassInfo.class.getClassLoader();
			}
			processClass = classLoader.loadClass(className);
		}
		catch (final ClassNotFoundException ex)
		{
			if (errorsCollector != null)
			{
				errorsCollector.addError(adProcess, "Class not found");
			}

			throw new AdempiereException("Class not found: " + className);
		}

		// Make sure is correctly introspected:
		new ProcessClassInfo(processClass);

		logger.info("{} is OK", getSummary(adProcess));
	}

	private static String getSummary(@NonNull final I_AD_Process adProcess)
	{
		return adProcess.getValue() + "/" + adProcess.getAD_Process_ID() + "/" + adProcess.getClassname() + "/" + adProcess.getEntityType();
	}

	private static ImmutableSet<AdProcessId> getAdProcessIdsToSkip(@NonNull final DataTable dataTable)
	{
		return DataTableRow.toRows(dataTable)
				.stream()
				.map(row -> AdProcessId.ofRepoId(row.getAsInt("AD_Process_ID")))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static AdProcessId extractAdProcessId(final I_AD_Process adProcess)
	{
		return AdProcessId.ofRepoId(adProcess.getAD_Process_ID());
	}

	//
	//
	//

	private static class ErrorsCollector
	{
		private final LinkedHashMap<AdProcessId, ErrorEntry> errors = new LinkedHashMap<>();

		public void addError(final I_AD_Process adProcess, final String error)
		{
			final ErrorEntry entry = new ErrorEntry(adProcess, error);
			errors.put(entry.getAdProcessId(), entry);
		}

		public int count() {return errors.size();}

		public boolean isEmpty() {return errors.isEmpty();}

		public String toCucumberTable()
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(" | AD_Process_ID | Value | EntityType | Classname | Reason |");
			errors.forEach((adProcessId, entry) -> sb
					.append("\n")
					.append(" | ").append(entry.getAdProcessId().getRepoId())
					.append(" | ").append(entry.adProcess().getValue())
					.append(" | ").append(entry.adProcess().getEntityType())
					.append(" | ").append(entry.adProcess().getClassname())
					.append(" | ").append(entry.error())
					.append(" | ")
			);
			return sb.toString();
		}
	}

	private record ErrorEntry(@NonNull I_AD_Process adProcess, @Nullable String error)
	{
		public AdProcessId getAdProcessId() {return AdProcessId.ofRepoId(adProcess.getAD_Process_ID());}
	}
}
