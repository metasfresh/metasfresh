package de.metas.security.requests;

import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@Builder
public class CreateUserAuthTokenRequest
{
	@NonNull UserId userId;
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@NonNull RoleId roleId;

	@Nullable String description;
}
