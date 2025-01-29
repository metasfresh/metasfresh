package de.metas.business_rule.descriptor.model;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class BusinessRuleAndTriggers
{
	@NonNull BusinessRule businessRule;
	@NonNull @Singular ImmutableSet<BusinessRuleTrigger> triggers;

	public static BusinessRuleAndTriggersBuilder builderFrom(@NonNull final BusinessRule businessRule) {return builder().businessRule(businessRule);}
}
