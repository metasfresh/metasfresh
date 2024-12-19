package de.metas.business_rule;

import com.google.common.collect.ImmutableList;
import de.metas.business_rule.descriptor.BusinessRuleAndTrigger;
import de.metas.business_rule.descriptor.BusinessRuleTrigger;
import de.metas.business_rule.descriptor.BusinessRulesCollection;
import de.metas.business_rule.descriptor.TriggerTiming;
import de.metas.business_rule.descriptor.Validation;
import de.metas.business_rule.event.BusinessRuleEventCreateRequest;
import de.metas.business_rule.event.BusinessRuleEventRepository;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class BusinessRuleFireTriggersCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(BusinessRuleFireTriggersCommand.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final BusinessRuleEventRepository eventRepository;

	@NonNull final BusinessRulesCollection rules;
	@NonNull private final Object model;
	@NonNull private final TriggerTiming timing;

	@NonNull private final TableRecordReference modelRef;
	@NonNull private final ClientAndOrgId clientAndOrgId;

	@Builder
	private BusinessRuleFireTriggersCommand(
			@NonNull final BusinessRuleEventRepository eventRepository,
			@NonNull final BusinessRulesCollection rules,
			@NonNull final Object model,
			@NonNull final TriggerTiming timing)
	{
		this.eventRepository = eventRepository;
		this.rules = rules;
		this.model = model;
		this.timing = timing;

		this.modelRef = TableRecordReference.of(model);
		this.clientAndOrgId = InterfaceWrapperHelper.getClientAndOrgId(model);
	}

	public void execute()
	{
		for (final BusinessRuleAndTrigger ruleAndTrigger : getRuleAndTriggers())
		{
			final BooleanWithReason matching = isMatching(ruleAndTrigger);
			if (matching.isFalse())
			{
				logger.debug("Trigger {} not matching for {}: {}", ruleAndTrigger, model, matching.getReason());
				continue;
			}

			enqueueToRecompute(ruleAndTrigger);
		}
	}

	private ImmutableList<BusinessRuleAndTrigger> getRuleAndTriggers()
	{
		return rules.getByTriggerTableId(modelRef.getAdTableId());
	}

	private BooleanWithReason isMatching(@NonNull final BusinessRuleAndTrigger ruleAndTrigger)
	{
		try
		{
			final BusinessRuleTrigger trigger = ruleAndTrigger.getTrigger();

			if (!trigger.isChangeTypeMatching(timing))
			{
				return BooleanWithReason.falseBecause("timing not matching");
			}

			return isConditionMatching(model, trigger.getCondition());
		}
		catch (final Exception ex)
		{
			logger.debug("Failed evaluating trigger condition {} for {}/{}", ruleAndTrigger, model, timing, ex);
			return BooleanWithReason.falseBecause(ex.getLocalizedMessage());
		}
	}

	private BooleanWithReason isConditionMatching(
			@NonNull final Object model,
			@Nullable final Validation condition)
	{
		if (condition == null)
		{
			return BooleanWithReason.TRUE;
		}

		//noinspection SwitchStatementWithTooFewBranches
		switch (condition.getType())
		{
			case SQL:
			{
				final IStringExpression sqlWhereClauseExpr = condition.getSql();
				if (sqlWhereClauseExpr == null || sqlWhereClauseExpr.isNullExpression())
				{
					return BooleanWithReason.TRUE;
				}

				final Evaluatee ctx = InterfaceWrapperHelper.getEvaluatee(model);
				final String sqlWhereClause = sqlWhereClauseExpr.evaluate(ctx, IExpressionEvaluator.OnVariableNotFound.ReturnNoResult);
				final String tableName = InterfaceWrapperHelper.getModelTableName(model);
				final String keyColumnName = InterfaceWrapperHelper.getModelKeyColumnName(model);
				final int recordId = InterfaceWrapperHelper.getId(model);

				try
				{
					final boolean marching = queryBL.createQueryBuilder(tableName)
							.addEqualsFilter(keyColumnName, recordId)
							.addFilter(TypedSqlQueryFilter.of(sqlWhereClause))
							.create()
							.anyMatch();
					return marching
							? BooleanWithReason.TRUE
							: BooleanWithReason.falseBecause("SQL condition not met");
				}
				catch (final Exception ex)
				{
					return BooleanWithReason.falseBecause(ex.getLocalizedMessage());
				}
			}
			// case ValidationRule: {
			// 	break;
			// }
			default:
			{
				throw new AdempiereException("Unknown type: " + condition.getType());
			}
		}
	}

	private void enqueueToRecompute(@NonNull final BusinessRuleAndTrigger ruleAndTrigger)
	{
		eventRepository.create(BusinessRuleEventCreateRequest.builder()
				.clientAndOrgId(clientAndOrgId)
				.recordRef(modelRef)
				.businessRuleId(ruleAndTrigger.getBusinessRuleId())
				.triggerId(ruleAndTrigger.getTriggerId())
				.build());
	}
}
