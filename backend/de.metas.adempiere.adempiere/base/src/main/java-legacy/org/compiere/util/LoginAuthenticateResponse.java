package org.compiere.util;

import com.google.common.collect.ImmutableList;
import de.metas.security.Role;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class LoginAuthenticateResponse
{
	@NonNull UserId userId;
	boolean is2FARequired;
	@NonNull @Singular ImmutableList<Role> availableRoles;
}
