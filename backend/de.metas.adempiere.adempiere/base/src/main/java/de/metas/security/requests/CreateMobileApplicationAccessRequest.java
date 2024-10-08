package de.metas.security.requests;

import de.metas.mobile.application.repository.MobileApplicationRepoId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

@Value
@Builder
public class CreateMobileApplicationAccessRequest
{
	@NonNull RoleId roleId;
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@NonNull MobileApplicationRepoId applicationId;
}
