package org.adempiere.ad.validationRule.impl;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.exceptions.AdempiereException;

@ToString
final class ValidationRuleDescriptorsMap
{
	private final ImmutableMap<AdValRuleId, ValidationRuleDescriptor> byId;

	ValidationRuleDescriptorsMap(@NonNull final ImmutableMap<AdValRuleId, ValidationRuleDescriptor> byId)
	{
		this.byId = byId;
	}

	public ValidationRuleDescriptor getById(@NonNull final AdValRuleId adValRuleId)
	{
		final ValidationRuleDescriptor valRuleDescriptor = byId.get(adValRuleId);
		if (valRuleDescriptor == null)
		{
			throw new AdempiereException("No AD_Val_Rule found for " + adValRuleId);
		}
		return valRuleDescriptor;
	}
}
