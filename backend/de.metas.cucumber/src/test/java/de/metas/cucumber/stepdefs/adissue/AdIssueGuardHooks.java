package de.metas.cucumber.stepdefs.adissue;

import de.metas.cucumber.CucumberLifeCycleSupport;
import de.metas.event.remote.rabbitmq.RabbitMQDestinationResolver;
import de.metas.event.remote.rabbitmq.queues.async_batch.AsyncBatchQueueConfiguration;
import de.metas.event.remote.rabbitmq.queues.material_dispo.MaterialEventsQueueConfiguration;
import de.metas.util.Services;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Issue;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Global guard: after each scenario, finds {@code AD_Issue} rows created while it ran.
 * Currently WARN-only: findings are attached to the Allure report, the scenario is never failed.
 * <p>
 * Opt-outs: scenario tag {@code @expectsAdIssue:<pattern>} (case-insensitive regex, substring match)
 * and the in-repo registry, see {@link KnownAdIssues}. Note: Gherkin tags cannot contain whitespace,
 * so an {@code @expectsAdIssue} pattern cannot contain spaces.
 * <p>
 * This class is instantiated by cucumber-picocontainer before the infrastructure is booted (boot runs
 * from a {@code Given} step, not a hook), so it must NOT pull Spring beans at construction time. All
 * Spring access is done lazily inside {@link #checkAdIssues(Scenario)}, which only runs once a baseline
 * was captured (i.e. after boot).
 */
public class AdIssueGuardHooks
{
	static final String TAG_PREFIX = "@expectsAdIssue:";
	private static final Duration QUEUE_DRAIN_CAP = Duration.ofSeconds(15);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** null = baseline not captured (infrastructure was not booted yet when the scenario started) */
	private Integer baselineMaxAdIssueId;

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
			final String drainNote = drainQueuesCapped();
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

	/**
	 * Best-effort capped drain of the material + async queues, so async AD_Issue-writing work has a
	 * chance to settle before we read. Bounded by {@link #QUEUE_DRAIN_CAP} (never the 5-minute wait).
	 * Resolves its Spring beans lazily (this only runs after boot).
	 *
	 * @return {@code null} if the queues drained; otherwise a short note of what was still pending
	 */
	@Nullable
	private String drainQueuesCapped()
	{
		final long deadlineMillis = System.currentTimeMillis() + QUEUE_DRAIN_CAP.toMillis();
		final AmqpTemplate amqpTemplate = SpringContextHolder.instance.getBean(AmqpTemplate.class);
		final RabbitMQDestinationResolver resolver = SpringContextHolder.instance.getBean(RabbitMQDestinationResolver.class);
		final RabbitAdmin rabbitAdmin = new RabbitAdmin((RabbitTemplate)amqpTemplate);

		final StringBuilder notEmpty = new StringBuilder();
		for (final String topicName : Arrays.asList(
				MaterialEventsQueueConfiguration.EVENTBUS_TOPIC.getName(),
				AsyncBatchQueueConfiguration.EVENTBUS_TOPIC.getName()))
		{
			final String queueName = resolver.getAMQPQueueNameByTopicName(topicName);
			long messageCount;
			do
			{
				final QueueInformation info = rabbitAdmin.getQueueInfo(queueName);
				messageCount = info != null ? info.getMessageCount() : 0;
				if (messageCount > 0 && System.currentTimeMillis() < deadlineMillis)
				{
					try
					{
						Thread.sleep(1000);
					}
					catch (final InterruptedException e)
					{
						Thread.currentThread().interrupt();
						return appendPending(notEmpty, queueName + "=interrupted");
					}
				}
			}
			while (messageCount > 0 && System.currentTimeMillis() < deadlineMillis);

			if (messageCount > 0)
			{
				appendPending(notEmpty, queueName + "=" + messageCount);
			}
		}
		return notEmpty.length() > 0 ? notEmpty.toString() : null;
	}

	private static String appendPending(final StringBuilder sb, final String entry)
	{
		if (sb.length() > 0)
		{
			sb.append(", ");
		}
		sb.append(entry);
		return sb.toString();
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
