package de.metas.business_rule.event;

import de.metas.business_rule.descriptor.BusinessRule;
import de.metas.business_rule.descriptor.BusinessRuleId;
import de.metas.business_rule.descriptor.BusinessRuleRepository;
import de.metas.business_rule.descriptor.BusinessRuleTrigger;
import de.metas.business_rule.descriptor.BusinessRulesCollection;
import de.metas.business_rule.descriptor.Validation;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.record.warning.RecordWarningCreateRequest;
import de.metas.record.warning.RecordWarningQuery;
import de.metas.record.warning.RecordWarningRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.ad.validationRule.impl.EvaluateeValidationContext;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee2;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;

import javax.annotation.Nullable;

@Builder
public class BusinessRuleEventProcessorCommand
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IValidationRuleFactory validationRuleFactory = Services.get(IValidationRuleFactory.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final BusinessRuleRepository ruleRepository;
	@NonNull private final BusinessRuleEventRepository eventRepository;
	@NonNull private final RecordWarningRepository recordWarningRepository;

	@NonNull private final QueryLimit limit;
	@Nullable private BusinessRulesCollection rules;

	public void execute()
	{
		eventRepository.updateAllNotProcessed(this::processEvent, limit);
	}

	private BusinessRulesCollection getRules()
	{
		if (rules == null)
		{
			rules = ruleRepository.getAll();
		}
		return rules;
	}

	private BusinessRule getRuleById(final BusinessRuleId businessRuleId)
	{
		return getRules().getById(businessRuleId);
	}

	private BusinessRuleEvent processEvent(final BusinessRuleEvent event)
	{
		if (event.isProcessed())
		{
			return event;
		}

		try
		{
			trxManager.runInThreadInheritedTrx(() -> processEvent0(event));
			return event.markAsProcessedOK();
		}
		catch (Exception ex)
		{
			final AdIssueId errorId = errorManager.createIssue(ex);
			return event.markAsProcessingError(errorId);
		}
	}

	private void processEvent0(final BusinessRuleEvent event)
	{
		final TableRecordReference targetRecordRef = retrieveTargetRecordRef(event);

		final BusinessRule rule = getRuleById(event.getBusinessRuleId());
		final boolean isValid = isRecordValid(targetRecordRef, rule.getValidation());

		if (isValid)
		{
			recordWarningRepository.deleteByRecordRef(RecordWarningQuery.builder()
					.recordRef(targetRecordRef)
					.businessRuleId(rule.getId())
					.build());
		}
		else
		{
			recordWarningRepository.createOrUpdate(RecordWarningCreateRequest.builder()
					.recordRef(targetRecordRef)
					.businessRuleId(rule.getId())
					.message(rule.getWarningMessage())
					.build());
		}
	}

	private TableRecordReference retrieveTargetRecordRef(@NonNull final BusinessRuleEvent event)
	{
		final BusinessRule rule = getRuleById(event.getBusinessRuleId());
		final BusinessRuleTrigger trigger = rule.getTriggerById(event.getTriggerId());

		final AdTableId sourceTableId = event.getSourceRecordRef().getAdTableId();
		final String sourceTableName = TableIdsCache.instance.getTableName(sourceTableId);
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(sourceTableName);
		final int sourceRecordId = event.getSourceRecordRef().getRecord_ID();
		final String sql = "SELECT " + trigger.getTargetRecordMappingSQL() + " FROM " + sourceTableName + " WHERE " + keyColumnName + "=?";
		final int targetRecordId = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, sourceRecordId);
		return TableRecordReference.of(rule.getAdTableId(), targetRecordId);
	}

	private boolean isRecordValid(
			@NonNull final TableRecordReference targetRecordRef,
			@NonNull final Validation validation)
	{
		switch (validation.getType())
		{
			case SQL:
			{
				throw new UnsupportedOperationException("not yet implemented");
			}
			case ValidationRule:
			{
				final IValidationRule validationRule = validationRuleFactory.create(targetRecordRef.getTableName(), validation.getAdValRuleId(), null, null);
				return isRecordValid_ValidationRule(targetRecordRef, validationRule);
			}
			default:
			{
				throw new AdempiereException("Unknown type: " + validation.getType());
			}
		}
	}

	private boolean isRecordValid_ValidationRule(
			@NonNull final TableRecordReference targetRecordRef,
			@NonNull final IValidationRule validationRule)
	{

		final String tableName = targetRecordRef.getTableName();
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final int targetRecordId = targetRecordRef.getRecord_ID();
		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(tableName)
				.addEqualsFilter(keyColumnName, targetRecordId);

		final Evaluatee2 evaluatee = Evaluatees.empty();
		final String sqlWhereClause = validationRule.getPrefilterWhereClause().evaluate(evaluatee, IExpressionEvaluator.OnVariableNotFound.Fail);
		if (!Check.isBlank(sqlWhereClause))
		{
			queryBuilder.addFilter(TypedSqlQueryFilter.of(sqlWhereClause));
		}

		if (!queryBuilder.create().anyMatch())
		{
			return false;
		}

		return validationRule.getPostQueryFilter().accept(new EvaluateeValidationContext(evaluatee), KeyNamePair.of(targetRecordId, String.valueOf(targetRecordId)));
	}

}
