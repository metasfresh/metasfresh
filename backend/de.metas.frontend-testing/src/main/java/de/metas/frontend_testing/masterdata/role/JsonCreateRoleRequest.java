package de.metas.frontend_testing.masterdata.role;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserRequest;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Creates a purpose-built role with its {@code AD_Table_Access} rows and, optionally, a user bound to
 * nothing but that role, so a spec can log in and exercise the role's permissions without editing any
 * pre-existing role.
 */
@Value
@Builder
@Jacksonized
public class JsonCreateRoleRequest
{
	/**
	 * Role name, defaulting to the section's identifier. Either way a per-run timestamp is appended, because
	 * {@code AD_Role.Name} is unique over the active rows and the database is shared across runs.
	 * The suffix is 19 characters and {@code AD_Role.Name} is {@code varchar(60)}, so at most 41 characters here.
	 */
	@Nullable String name;

	/**
	 * The roles this role includes, either by the identifier of another entry of the {@code roles} section or
	 * by {@code AD_Role_ID}. Defaults to the {@code WebUI} role, without which the role has no WebUI menu at all.
	 */
	@Nullable List<Identifier> includedRoles;

	@Nullable List<JsonRoleTableAccessRequest> tableAccess;

	/**
	 * The user to create for this role. It is bound to this role and to no other, so
	 * {@code LoginRestController} completes its login without a role-selection step.
	 */
	@Nullable JsonLoginUserRequest user;
}
