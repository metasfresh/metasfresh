package org.adempiere.ad.validationRule.impl;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.AdValRuleId;

import javax.annotation.Nullable;

@Value
@Builder
public class ValidationRuleDescriptor
{
	@NonNull AdValRuleId id;
	@NonNull String name;
	boolean active;
	@NonNull ValidationRuleType type;
	@NonNull ImmutableSet<String> dependsOnTableNames;

	//
	// SQL:
	@Nullable IStringExpression sqlWhereClause;

	//
	// JAVA:
	@Nullable String javaClassname;

	//
	// COMPOSITE:
	@Nullable ImmutableSet<AdValRuleId> includedValRuleIds;
}
