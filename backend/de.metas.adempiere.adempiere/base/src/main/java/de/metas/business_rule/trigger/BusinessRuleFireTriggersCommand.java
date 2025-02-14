package de.metas.business_rule.trigger;

import com.google.common.collect.ImmutableList;
import de.metas.business_rule.descriptor.model.BusinessRule;
import de.metas.business_rule.descriptor.model.BusinessRuleAndTriggers;
import de.metas.business_rule.descriptor.model.BusinessRuleTrigger;
import de.metas.business_rule.descriptor.model.BusinessRulesCollection;
import de.metas.business_rule.descriptor.model.TriggerTiming;
import de.metas.business_rule.descriptor.model.Validation;
import de.metas.business_rule.event.BusinessRuleEventCreateRequest;
import de.metas.business_rule.event.BusinessRuleEventRepository;
import de.metas.business_rule.log.BusinessRuleLogLevel;
import de.metas.business_rule.log.BusinessRuleLogger;
import de.metas.business_rule.log.BusinessRuleStopwatch;
import de.metas.business_rule.util.BusinessRuleRecordMatcher;
import de.metas.i18n.BooleanWithReason;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class BusinessRuleFireTriggersCommand
{
	@NonNull private static final String LOGGER_MODULE = "trigger";

	@NonNull private final BusinessRuleEventRepository eventRepository;
	@NonNull private final BusinessRuleLogger logger;
	@NonNull private final BusinessRuleRecordMatcher recordMatcher;

	@NonNull final BusinessRulesCollection rules;
	@NonNull private final Object sourceModel;
	@NonNull private final TriggerTiming timing;

	@NonNull private final TableRecordReference sourceModelRef;
	@NonNull private final ClientAndOrgId clientAndOrgId;
	@NonNull private final UserId triggeringUserId;

	@Builder
	private BusinessRuleFireTriggersCommand(
			@NonNull final BusinessRuleEventRepository eventRepository,
			@NonNull final BusinessRuleLogger logger,
			@NonNull final BusinessRuleRecordMatcher recordMatcher,
			//
			@NonNull final BusinessRulesCollection rules,
			@NonNull final Object sourceModel,
			@NonNull final TriggerTiming timing)
	{
		this.eventRepository = eventRepository;
		this.logger = logger;
		this.recordMatcher = recordMatcher;

		this.rules = rules;
		this.sourceModel = sourceModel;
		this.timing = timing;

		this.sourceModelRef = TableRecordReference.of(sourceModel);
		this.clientAndOrgId = InterfaceWrapperHelper.getClientAndOrgId(sourceModel);
		this.triggeringUserId = InterfaceWrapperHelper.getUpdatedBy(sourceModel);
	}

	public void execute()
	{
		try (final IAutoCloseable ignored = setupLoggerContext())
		{
			final BusinessRuleStopwatch stopwatch = logger.newStopwatch();

			final ImmutableList<BusinessRuleAndTriggers> rulesAndTriggers = getRuleAndTriggers();
			try (final IAutoCloseable ignored1 = updateLoggerContextFrom(rulesAndTriggers))
			{
				for (final BusinessRuleAndTriggers ruleAndTriggers : rulesAndTriggers)
				{
					final BusinessRule rule = ruleAndTriggers.getBusinessRule();
					try (final IAutoCloseable ignored2 = updateLoggerContextFrom(rule))
					{
						for (final BusinessRuleTrigger trigger : ruleAndTriggers.getTriggers())
						{
							try (final IAutoCloseable ignored3 = updateLoggerContextFrom(trigger))
							{
								final BooleanWithReason matching = checkTriggerMatching(rule, trigger);
								if (matching.isFalse())
								{
									logger.debug("Skip enqueueing event because trigger is not matching");
									continue;
								}

								enqueueToRecompute(rule, trigger);
							}
						}
					}
				}

				logger.debug(stopwatch, "Done processing all triggers for source record");
			}

		}
	}

	private IAutoCloseable setupLoggerContext()
	{
		return logger.temporaryChangeContext(context -> context.moduleName(LOGGER_MODULE)
				.sourceRecordRef(sourceModelRef));
	}

	private IAutoCloseable updateLoggerContextFrom(final ImmutableList<BusinessRuleAndTriggers> rulesAndTriggers)
	{
		final BusinessRuleLogLevel maxLogLevel = extractMaxLogLevel(rulesAndTriggers).orElse(null);
		return maxLogLevel != null
				? logger.temporaryChangeContext(contextBuilder -> contextBuilder.logLevel(maxLogLevel))
				: IAutoCloseable.NOP;
	}

	private static Optional<BusinessRuleLogLevel> extractMaxLogLevel(final ImmutableList<BusinessRuleAndTriggers> rulesAndTriggers)
	{
		return rulesAndTriggers.stream()
				.map(ruleAndTrigger -> ruleAndTrigger.getBusinessRule().getLogLevel())
				.filter(Objects::nonNull)
				.max(Comparator.comparing(BusinessRuleLogLevel::ordinal));
	}

	private IAutoCloseable updateLoggerContextFrom(final BusinessRule rule)
	{
		return logger.temporaryChangeContext(contextBuilder -> contextBuilder.businessRule(rule));
	}

	private IAutoCloseable updateLoggerContextFrom(final BusinessRuleTrigger trigger)
	{
		return logger.temporaryChangeContext(contextBuilder -> contextBuilder.triggerId(trigger.getId()));
	}

	private ImmutableList<BusinessRuleAndTriggers> getRuleAndTriggers()
	{
		return rules.getByTriggerTableId(sourceModelRef.getAdTableId());
	}

	private BooleanWithReason checkTriggerMatching(@NonNull final BusinessRule rule, @NonNull final BusinessRuleTrigger trigger)
	{
		final BusinessRuleStopwatch stopwatch = logger.newStopwatch();
		BooleanWithReason matching = BooleanWithReason.FALSE;
		try
		{
			if (!trigger.isChangeTypeMatching(timing))
			{
				return BooleanWithReason.falseBecause("timing not matching");
			}

			matching = checkConditionMatching(trigger.getCondition());
		}
		catch (final Exception ex)
		{
			logger.debug("Failed evaluating trigger condition {}/{} for {}/{}", rule, trigger, sourceModel, timing, ex);
			matching = BooleanWithReason.falseBecause(ex);
		}
		finally
		{
			logger.debug(stopwatch, "Checked if trigger is matching source: {}", matching);
		}

		return matching;
	}

	private BooleanWithReason checkConditionMatching(@Nullable final Validation condition)
	{
		return condition == null || recordMatcher.isRecordMatching(sourceModelRef, condition)
				? BooleanWithReason.TRUE
				: BooleanWithReason.FALSE;
	}

	private void enqueueToRecompute(@NonNull final BusinessRule rule, @NonNull final BusinessRuleTrigger trigger)
	{
		eventRepository.create(BusinessRuleEventCreateRequest.builder()
				.clientAndOrgId(clientAndOrgId)
				.triggeringUserId(triggeringUserId)
				.recordRef(sourceModelRef)
				.businessRuleId(rule.getId())
				.triggerId(trigger.getId())
				.build());

		logger.debug("Enqueued event for re-computation");
	}
}
