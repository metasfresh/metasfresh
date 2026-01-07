package de.metas.record.warning;

import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.Severity;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

@Value
@Builder
public class RecordWarningCreateRequest
{
	@NonNull TableRecordReference rootRecordRef;
	@NonNull TableRecordReference recordRef;
	@NonNull BusinessRuleId businessRuleId;
	@NonNull String message;
	@NonNull UserId userId;
	@NonNull Severity severity;
}
