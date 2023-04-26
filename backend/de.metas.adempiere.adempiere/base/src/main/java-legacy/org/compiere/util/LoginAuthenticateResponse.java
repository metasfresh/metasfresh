package org.compiere.util;

import com.google.common.collect.ImmutableList;
import de.metas.security.Role;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
public class LoginAuthenticateResponse
{
	@NonNull UserId userId;

	@Singular
	@NonNull ImmutableList<Role> availableRoles;

	public Optional<Role> getSingleRole()
	{
		return availableRoles.size() == 1 ? Optional.of(availableRoles.get(0)) : Optional.empty();
	}
}
