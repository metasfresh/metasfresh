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
	/** Role name; defaults to the section identifier. A per-run uniqueness suffix is appended either way. */
	@Nullable String name;

	/**
	 * The roles this role includes, either by the identifier of another entry of the {@code roles} section or
	 * by {@code AD_Role_ID}. Defaults to the {@code WebUI} role, without which the role has no WebUI menu at all.
	 */
	@Nullable List<Identifier> includedRoles;

	@Nullable List<JsonRoleTableAccessRequest> tableAccess;

	/**
	 * Fixture convenience: co-create a login user together with this role, bound to this role and to no other,
	 * so a spec can log straight in without a role-selection step. Not a role-owns-user assignment - the one
	 * request just mints both the role and its user in a single call.
	 */
	@Nullable JsonLoginUserRequest user;
}
