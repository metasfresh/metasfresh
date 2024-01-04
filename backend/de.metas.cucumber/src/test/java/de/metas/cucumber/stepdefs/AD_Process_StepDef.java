package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessClassInfo;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAwares;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Process;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;

public class AD_Process_StepDef
{
	private static final Logger logger = LogManager.getLogger(AD_Process_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final MatchingRules includeRules = new MatchingRules();
	private final MatchingRules excludeRules = new MatchingRules();

	@Given("all AD_Processes")
	public void includeAllProcesses()
	{
		includeRules.add(MatchingRule.ALL);
	}

	@But("exclude AD_Processes")
	public void excludeProcesses(final DataTable dataTable)
	{
		DataTableRow.toRows(dataTable)
				.forEach(row -> {
					final MatchingRule rule = MatchingRule.ofDataTableRow(row);
					if (rule.isMatchAll())
					{
						throw new AdempiereException("Exclude all rule is not allowed: " + row);
					}

					excludeRules.add(rule);
				});
	}

	@Then("assert selected AD_Processes are valid")
	public void assertProcessesAreValid()
	{
		includeRules.resetStats();
		excludeRules.resetStats();

		final ErrorsCollector errorsCollector = new ErrorsCollector();
		final SoftAssertions softly = new SoftAssertions();

		final ImmutableMap<AdProcessId, I_AD_Process> adProcesses = queryBL.createQueryBuilder(I_AD_Process.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_Process.COLUMNNAME_EntityType)
				.orderBy(I_AD_Process.COLUMNNAME_AD_Process_ID)
				.stream()
				.filter(includeRules::checkMatching)
				.collect(ImmutableMap.toImmutableMap(AD_Process_StepDef::extractAdProcessId, adProcess -> adProcess));

		softly.assertThat(adProcesses)
				.as("At least one AD_Process shall be selected for validation")
				.isNotEmpty();

		//
		// Check each AD_Process entry to be valid
		adProcesses.forEach((adProcessId, adProcess) -> {
			if (excludeRules.checkMatching(adProcess))
			{
				logger.info("{} is Skipped", getSummary(adProcess));
				return;
			}

			softly.assertThatCode(() -> validateAD_Process(adProcess, errorsCollector))
					.as(() -> getSummary(adProcess))
					.doesNotThrowAnyException();
		});

		//
		// Hygiene of the skip list:
		excludeRules.getRules().forEach(excludeRule -> {
			softly.assertThat(excludeRule.isHit())
					.as(() -> "Expect exclude rule " + excludeRule + " to match at least one AD_Process but it didn't. Feel free to remove it from the exclude list.")
					.isTrue();

			final AdProcessId adProcessId = excludeRule.getAdProcessId();
			if (adProcessId != null)
			{
				excludeRule.getHits().forEach(adProcess -> {
					softly.assertThatCode(() -> validateAD_Process(adProcess, null))
							.as(() -> "Expect skipped " + getSummary(adProcess) + " to fail but it's a success. NICE! Feel free to remove it from the skip list.")
							.isInstanceOf(Throwable.class); // any exception
				});
			}
		});

		//
		// Help developer to skip more records if needed
		logger.info("{} AD_Process(es) were checked", adProcesses.size());
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

	private static AdProcessId extractAdProcessId(final I_AD_Process adProcess)
	{
		return AdProcessId.ofRepoId(adProcess.getAD_Process_ID());
	}

	//
	//
	//

	private static class MatchingRules
	{
		private final LinkedHashSet<MatchingRule> rules = new LinkedHashSet<>();

		public void add(final MatchingRule rule)
		{
			rules.add(rule);
		}

		public ImmutableSet<MatchingRule> getRules() {return ImmutableSet.copyOf(rules);}

		public void resetStats()
		{
			rules.forEach(MatchingRule::resetStats);
		}

		public boolean checkMatching(final I_AD_Process adProcess)
		{
			if (rules.isEmpty())
			{
				return false;
			}

			for (final MatchingRule rule : rules)
			{
				if (rule.checkMatching(adProcess))
				{
					return true;
				}
			}

			return false;
		}

	}

	@Builder
	@EqualsAndHashCode
	@ToString
	private static class MatchingRule
	{
		public static MatchingRule ALL = builder().build();

		@Nullable @Getter private final AdProcessId adProcessId;
		@Nullable private final String entityType;

		private final HashMap<AdProcessId, I_AD_Process> hits = new HashMap<>();

		public static MatchingRule ofDataTableRow(final DataTableRow row)
		{
			final String adProcessIdPattern = row.getAsOptionalString("AD_Process_ID").map(StringUtils::trimBlankToNull).orElse(null);
			final AdProcessId adProcessId;
			if (adProcessIdPattern == null || adProcessIdPattern.equals("*"))
			{
				adProcessId = null;
			}
			else
			{
				adProcessId = RepoIdAwares.ofObject(adProcessIdPattern, AdProcessId.class);
			}

			final String entityTypePattern = row.getAsOptionalString("EntityType").map(StringUtils::trimBlankToNull).orElse(null);
			final String entityType;
			if (entityTypePattern == null || entityTypePattern.equals("*"))
			{
				entityType = null;
			}
			else
			{
				entityType = entityTypePattern;
			}

			return builder().adProcessId(adProcessId).entityType(entityType).build();
		}

		public void resetStats() {hits.clear();}

		public boolean isHit() {return !hits.isEmpty();}

		public Collection<I_AD_Process> getHits() {return hits.values();}

		public boolean checkMatching(final I_AD_Process adProcess)
		{
			final boolean isMatching = (adProcessId == null || adProcess.getAD_Process_ID() == adProcessId.getRepoId())
					&& (entityType == null || Objects.equals(adProcess.getEntityType(), entityType));
			if (isMatching)
			{
				hits.put(extractAdProcessId(adProcess), adProcess);
			}
			return isMatching;
		}

		public boolean isMatchAll() {return this.equals(ALL);}
	}

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
