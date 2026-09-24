package de.metas.frontend_testing.masterdata.role;

import de.metas.frontend_testing.masterdata.user.JsonLoginUserResponse;
import de.metas.security.RoleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonCreateRoleResponse
{
	@NonNull RoleId roleId;

	@NonNull String name;

	/** Set when the request asked for a user; these are the credentials to log in with. */
	@Nullable JsonLoginUserResponse user;
}
