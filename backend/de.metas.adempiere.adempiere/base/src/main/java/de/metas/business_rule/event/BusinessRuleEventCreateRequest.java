package de.metas.business_rule.event;

import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRuleTriggerId;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

@Value
@Builder
public class BusinessRuleEventCreateRequest
{
	@NonNull ClientAndOrgId clientAndOrgId;
	@NonNull UserId triggeringUserId;
	@NonNull TableRecordReference recordRef;
	@NonNull BusinessRuleId businessRuleId;
	@NonNull BusinessRuleTriggerId triggerId;
}
