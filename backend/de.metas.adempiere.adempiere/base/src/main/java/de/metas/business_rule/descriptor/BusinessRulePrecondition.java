package de.metas.business_rule.descriptor;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class BusinessRulePrecondition
{
	@NonNull Validation validation;
}
