package de.metas.frontend_testing.masterdata.user;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonLoginUserRequest
{
	String language;
	@Nullable Identifier workplace;

	/**
	 * The single role this user is assigned to - by the identifier of a {@code roles} section entry or by
	 * {@code AD_Role_ID}. Defaults to the {@code WebUI} role. The user is bound to this role and no other, so
	 * login completes without a role-selection step (exactly one (role, tenant, org) entry is offered).
	 */
	@Nullable Identifier role;

	@Nullable String login;
	@Nullable String firstname;
	@Nullable String lastname;
}
