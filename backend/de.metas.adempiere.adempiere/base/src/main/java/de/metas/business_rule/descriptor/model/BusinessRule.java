package de.metas.business_rule.descriptor.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.business_rule.log.BusinessRuleLogLevel;
import de.metas.i18n.AdMessageId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@ToString(of = { "id", "name" })
public class BusinessRule
{
	@NonNull BusinessRuleId id;
	@NonNull String name;

	@NonNull AdTableId adTableId;

	@NonNull ImmutableList<BusinessRulePrecondition> preconditions;
	@NonNull Validation validation;
	@NonNull ImmutableList<BusinessRuleTrigger> triggers;
	@NonNull @Getter(AccessLevel.NONE) ImmutableMap<BusinessRuleTriggerId, BusinessRuleTrigger> triggersById;

	@NonNull AdMessageId warningMessageId;

	@Nullable BusinessRuleLogLevel logLevel;

	@Builder
	private BusinessRule(
			@NonNull final BusinessRuleId id,
			@NonNull final String name,
			@NonNull final AdTableId adTableId,
			@NonNull final ImmutableList<BusinessRulePrecondition> preconditions,
			@NonNull final Validation validation,
			@NonNull final ImmutableList<BusinessRuleTrigger> triggers,
			@NonNull final AdMessageId warningMessageId,
			@Nullable BusinessRuleLogLevel logLevel)
	{
		this.id = id;
		this.name = name;
		this.adTableId = adTableId;
		this.preconditions = preconditions;
		this.validation = validation;
		this.triggers = triggers;
		this.triggersById = Maps.uniqueIndex(triggers, BusinessRuleTrigger::getId);
		this.warningMessageId = warningMessageId;
		this.logLevel = logLevel;
	}

	@NonNull
	public BusinessRuleTrigger getTriggerById(final BusinessRuleTriggerId triggerId)
	{
		final BusinessRuleTrigger trigger = triggersById.get(triggerId);
		if (trigger == null)
		{
			throw new AdempiereException("No trigger found for " + triggerId + " in " + this);
		}
		return trigger;
	}
}
