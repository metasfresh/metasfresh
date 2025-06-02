package de.metas.record.warning;

import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.Severity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
@Builder
public class RecordWarningQuery
{
	@NonNull TableRecordReference recordRef;
	@Nullable BusinessRuleId businessRuleId;
	@Nullable Severity severity;
}
