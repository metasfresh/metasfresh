package org.adempiere.ad.service;

import de.metas.ad_reference.ReferenceId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.ad.validationRule.AdValRuleId;

@Value
@Builder
public
class MinimalColumnInfo
{
	String columnName;
	String tableName;
	ReferenceId AD_Reference_Value_ID;
	boolean parent;
	AdValRuleId AD_Val_Rule_ID;
}
