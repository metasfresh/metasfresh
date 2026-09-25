package de.metas.frontend_testing.masterdata.role;

import de.metas.security.RoleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCreateRoleResponse
{
	@NonNull RoleId roleId;

	@NonNull String name;
}
