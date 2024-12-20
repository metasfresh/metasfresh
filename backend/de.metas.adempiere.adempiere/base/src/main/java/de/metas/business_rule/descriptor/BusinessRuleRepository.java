package de.metas.business_rule.descriptor;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.business_rule.descriptor.model.BusinessRule;
import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRulePrecondition;
import de.metas.business_rule.descriptor.model.BusinessRuleTrigger;
import de.metas.business_rule.descriptor.model.BusinessRuleTriggerId;
import de.metas.business_rule.descriptor.model.BusinessRulesCollection;
import de.metas.business_rule.descriptor.model.TriggerTiming;
import de.metas.business_rule.descriptor.model.Validation;
import de.metas.business_rule.descriptor.model.ValidationType;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.ICacheResetListener;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_BusinessRule;
import org.compiere.model.I_AD_BusinessRule_Precondition;
import org.compiere.model.I_AD_BusinessRule_Trigger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class BusinessRuleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, BusinessRulesCollection> cache = CCache.<Integer, BusinessRulesCollection>builder()
			.tableName(I_AD_BusinessRule.Table_Name)
			.additionalTableNameToResetFor(I_AD_BusinessRule_Precondition.Table_Name)
			.additionalTableNameToResetFor(I_AD_BusinessRule_Trigger.Table_Name)
			.build();

	public void addCacheResetListener(@NonNull final BusinessRulesChangedListener listener)
	{
		final ICacheResetListener cacheResetListener = (request) -> {
			listener.onRulesChanged();
			return 1L;
		};

		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.addCacheResetListener(I_AD_BusinessRule.Table_Name, cacheResetListener);
		cacheMgt.addCacheResetListener(I_AD_BusinessRule_Precondition.Table_Name, cacheResetListener);
		cacheMgt.addCacheResetListener(I_AD_BusinessRule_Trigger.Table_Name, cacheResetListener);
	}

	public BusinessRulesCollection getAll()
	{
		return cache.getOrLoad(0, this::retrieveAll);
	}

	private BusinessRulesCollection retrieveAll()
	{
		final ImmutableListMultimap<BusinessRuleId, BusinessRulePrecondition> preconditionsMap = queryBL.createQueryBuilder(I_AD_BusinessRule_Precondition.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_BusinessRule_Precondition.COLUMNNAME_AD_BusinessRule_ID)
				.orderBy(I_AD_BusinessRule_Precondition.COLUMNNAME_AD_BusinessRule_Precondition_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> BusinessRuleId.ofRepoId(record.getAD_BusinessRule_ID()),
						BusinessRuleRepository::fromRecord
				));

		final ImmutableListMultimap<BusinessRuleId, BusinessRuleTrigger> triggersMap = queryBL.createQueryBuilder(I_AD_BusinessRule_Trigger.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_BusinessRule_Trigger.COLUMNNAME_AD_BusinessRule_ID)
				.orderBy(I_AD_BusinessRule_Trigger.COLUMNNAME_AD_BusinessRule_Trigger_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> BusinessRuleId.ofRepoId(record.getAD_BusinessRule_ID()),
						BusinessRuleRepository::fromRecord
				));

		return queryBL.createQueryBuilder(I_AD_BusinessRule.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_BusinessRule.COLUMNNAME_AD_BusinessRule_ID)
				.create()
				.stream()
				.map(record -> fromRecord(record, preconditionsMap, triggersMap))
				.collect(GuavaCollectors.collectUsingListAccumulator(BusinessRulesCollection::ofList));
	}

	private static BusinessRule fromRecord(
			@NonNull final I_AD_BusinessRule record,
			@NonNull final ImmutableListMultimap<BusinessRuleId, BusinessRulePrecondition> preconditionsMap,
			@NonNull final ImmutableListMultimap<BusinessRuleId, BusinessRuleTrigger> triggersMap)
	{
		final BusinessRuleId id = BusinessRuleId.ofRepoId(record.getAD_BusinessRule_ID());
		return BusinessRule.builder()
				.id(id)
				.name(record.getName())
				.adTableId(AdTableId.ofRepoId(record.getAD_Table_ID()))
				.preconditions(preconditionsMap.get(id))
				.validation(extractValidation(record))
				.triggers(triggersMap.get(id))
				.warningMessage(record.getWarningMessage())
				.build();
	}

	private static Validation extractValidation(final I_AD_BusinessRule record)
	{
		return Validation.adValRule(AdValRuleId.ofRepoId(record.getValidation_Rule_ID()));
	}

	private static BusinessRulePrecondition fromRecord(I_AD_BusinessRule_Precondition record)
	{
		return BusinessRulePrecondition.builder()
				.validation(extractValidation(record))
				.build();
	}

	private static Validation extractValidation(I_AD_BusinessRule_Precondition record)
	{
		final ValidationType type = ValidationType.ofCode(record.getPreconditionType());
		switch (type)
		{
			case SQL:
				//noinspection DataFlowIssue
				return Validation.sql(record.getPreconditionSQL());
			case ValidationRule:
				return Validation.adValRule(AdValRuleId.ofRepoId(record.getPrecondition_Rule_ID()));
			default:
				throw new AdempiereException("Unknown validation type: " + type);
		}
	}

	private static BusinessRuleTrigger fromRecord(I_AD_BusinessRule_Trigger record)
	{
		return BusinessRuleTrigger.builder()
				.id(BusinessRuleTriggerId.ofRepoId(record.getAD_BusinessRule_Trigger_ID()))
				.triggerTableId(AdTableId.ofRepoId(record.getSource_Table_ID()))
				.timings(extractTimings(record))
				.condition(extractCondition(record))
				.targetRecordMappingSQL(record.getTargetRecordMappingSQL())
				.build();
	}

	private static @NonNull ImmutableSet<TriggerTiming> extractTimings(final I_AD_BusinessRule_Trigger record)
	{
		ImmutableSet.Builder<TriggerTiming> timings = ImmutableSet.builder();
		if (record.isOnNew())
		{
			timings.add(TriggerTiming.NEW);
		}
		if (record.isOnUpdate())
		{
			timings.add(TriggerTiming.UPDATE);
		}
		if (record.isOnDelete())
		{
			timings.add(TriggerTiming.DELETE);
		}
		return timings.build();
	}

	@Nullable
	private static Validation extractCondition(final I_AD_BusinessRule_Trigger record)
	{
		return StringUtils.trimBlankToOptional(record.getConditionSQL())
				.map(Validation::sql)
				.orElse(null);
	}
}
