package de.metas.frontend_testing.masterdata.role;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Creates a purpose-built role with its {@code AD_Table_Access} rows, so a spec can exercise a role's
 * permissions without editing any pre-existing role. To log in as this role, a {@code login} user
 * references it via {@link de.metas.frontend_testing.masterdata.user.JsonLoginUserRequest#getRole()}.
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
}
