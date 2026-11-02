package de.metas.cucumber.stepdefs.adissue;

import de.metas.cucumber.CucumberLifeCycleSupport;
import de.metas.cucumber.stepdefs.rabbitMQ.RabbitMQ_StepDef;
import de.metas.util.Services;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_AD_Issue;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Global guard: after each scenario, finds {@code AD_Issue} rows created while it ran.
 * Currently WARN-only: findings are attached to the Allure report, the scenario is never failed.
 * <p>
 * Opt-outs: scenario tag {@code @expectsAdIssue:<pattern>} (case-insensitive regex, substring match)
 * and the in-repo registry, see {@link KnownAdIssues}.
 */
public class AdIssueGuardHooks
{
	static final String TAG_PREFIX = "@expectsAdIssue:";
	private static final Duration QUEUE_DRAIN_CAP = Duration.ofSeconds(15);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final RabbitMQ_StepDef rabbitMQStepDef;

	/** null = baseline not captured (infrastructure was not booted yet when the scenario started) */
	private Integer baselineMaxAdIssueId;

	public AdIssueGuardHooks(@NonNull final RabbitMQ_StepDef rabbitMQStepDef)
	{
		this.rabbitMQStepDef = rabbitMQStepDef;
	}

	@Before(order = 0)
	public void captureBaseline()
	{
		if (!CucumberLifeCycleSupport.isBeforeAllDone())
		{
			baselineMaxAdIssueId = null;
			return;
		}
		baselineMaxAdIssueId = retrieveMaxAdIssueId();
	}

	@After(order = 0)
	public void checkAdIssues(@NonNull final Scenario scenario)
	{
		if (baselineMaxAdIssueId == null)
		{
			return;
		}

		try
		{
			final String drainNote = rabbitMQStepDef.waitEmptyAllQueuesCapped(QUEUE_DRAIN_CAP);
			if (drainNote != null)
			{
				Allure.addAttachment("AD_Issue guard: queue drain incomplete", drainNote);
			}

			final List<AdIssueRow> newRows = queryBL
					.createQueryBuilderOutOfTrx(I_AD_Issue.class)
					.addCompareFilter(I_AD_Issue.COLUMNNAME_AD_Issue_ID, Operator.GREATER, baselineMaxAdIssueId)
					.orderBy(I_AD_Issue.COLUMNNAME_AD_Issue_ID)
					.create()
					.list(I_AD_Issue.class)
					.stream()
					.map(r -> new AdIssueRow(r.getAD_Issue_ID(), String.valueOf(r.getIssueSummary())))
					.collect(Collectors.toList());
			if (newRows.isEmpty())
			{
				return;
			}

			final AdIssueGuard.Result result = AdIssueGuard.classify(newRows, extractExpectedPatterns(scenario), KnownAdIssues.load());

			if (!result.getUnknown().isEmpty())
			{
				Allure.addAttachment("Unexpected AD_Issues (WARN)", render(result.getUnknown()));
			}
			if (!result.getKnown().isEmpty())
			{
				Allure.addAttachment("Known AD_Issues (tracked)", render(result.getKnown()));
			}
		}
		catch (final Exception e)
		{
			// WARN mode: the guard must never fail a scenario
			Allure.addAttachment("AD_Issue guard: check failed", String.valueOf(e));
		}
	}

	private int retrieveMaxAdIssueId()
	{
		final I_AD_Issue last = queryBL
				.createQueryBuilderOutOfTrx(I_AD_Issue.class)
				.orderByDescending(I_AD_Issue.COLUMNNAME_AD_Issue_ID)
				.create()
				.first(I_AD_Issue.class);
		return last != null ? last.getAD_Issue_ID() : 0;
	}

	static Set<String> extractExpectedPatterns(@NonNull final Scenario scenario)
	{
		final Set<String> patterns = new HashSet<>();
		for (final String tag : scenario.getSourceTagNames())
		{
			if (tag.startsWith(TAG_PREFIX) && tag.length() > TAG_PREFIX.length())
			{
				patterns.add(tag.substring(TAG_PREFIX.length()));
			}
		}
		return patterns;
	}

	private static String render(final List<AdIssueGuard.Verdict> verdicts)
	{
		return verdicts.stream()
				.map(v -> "AD_Issue_ID=" + v.getRow().getAdIssueId() + ": " + v.getRow().getSummary()
						+ (v.getIssueUrl() != null ? " -> " + v.getIssueUrl() : ""))
				.collect(Collectors.joining("\n"));
	}
}
