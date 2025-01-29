package de.metas.business_rule.descriptor.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.business_rule.descriptor.model.BusinessRuleAndTriggers.BusinessRuleAndTriggersBuilder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode
@ToString(of = "list")
public class BusinessRulesCollection
{
	public final static BusinessRulesCollection EMPTY = new BusinessRulesCollection(ImmutableList.of());

	@NonNull private final ImmutableList<BusinessRule> list;
	@NonNull private final ImmutableMap<BusinessRuleId, BusinessRule> byId;
	@NonNull private final ImmutableListMultimap<AdTableId, BusinessRuleAndTriggers> byTriggerTableId;

	BusinessRulesCollection(List<BusinessRule> list)
	{
		this.list = ImmutableList.copyOf(list);
		this.byId = Maps.uniqueIndex(list, BusinessRule::getId);
		this.byTriggerTableId = indexByTriggerTableId(list);
	}

	private static ImmutableListMultimap<AdTableId, BusinessRuleAndTriggers> indexByTriggerTableId(final List<BusinessRule> list)
	{
		final HashMap<AdTableId, HashMap<BusinessRuleId, BusinessRuleAndTriggersBuilder>> buildersByTriggerTableId = new HashMap<>();
		for (final BusinessRule rule : list)
		{
			for (final BusinessRuleTrigger trigger : rule.getTriggers())
			{
				buildersByTriggerTableId.computeIfAbsent(trigger.getTriggerTableId(), triggerTableId -> new HashMap<>())
						.computeIfAbsent(rule.getId(), ruleId -> BusinessRuleAndTriggers.builderFrom(rule))
						.trigger(trigger);
			}
		}

		final ImmutableListMultimap.Builder<AdTableId, BusinessRuleAndTriggers> result = ImmutableListMultimap.builder();
		buildersByTriggerTableId.forEach((triggerTableId, ruleBuilders) -> ruleBuilders.values()
				.stream().map(BusinessRuleAndTriggersBuilder::build)
				.forEach(ruleAndTriggers -> result.put(triggerTableId, ruleAndTriggers))
		);

		return result.build();
	}

	public static BusinessRulesCollection ofList(List<BusinessRule> list)
	{
		return !list.isEmpty() ? new BusinessRulesCollection(list) : EMPTY;
	}

	public ImmutableSet<AdTableId> getTriggerTableIds() {return byTriggerTableId.keySet();}

	public ImmutableList<BusinessRuleAndTriggers> getByTriggerTableId(@NonNull final AdTableId triggerTableId) {return this.byTriggerTableId.get(triggerTableId);}

	public BusinessRule getById(@NonNull final BusinessRuleId id)
	{
		final BusinessRule rule = getByIdOrNull(id);
		if (rule == null)
		{
			throw new AdempiereException("No rule found for id=" + id);
		}
		return rule;
	}

	@Nullable
	public BusinessRule getByIdOrNull(@Nullable final BusinessRuleId id)
	{
		return id == null ? null : byId.get(id);
	}
}
