package de.metas.frontend_testing.masterdata.role;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

/**
 * One {@code Mobile_Application_Access} row of a {@link JsonCreateRoleRequest}, plus its
 * {@code Mobile_Application_Action_Access} rows.
 * <p>
 * Note: permissions of included roles are merged (union), so a role that must NOT allow all actions of the application
 * has to be created without the default {@code WebUI} included role ({@code includedRoles: []}).
 */
@Value
@Builder
@Jacksonized
public class JsonRoleMobileApplicationAccessRequest
{
	/** {@code Mobile_Application.Value}, e.g. {@code huManager} */
	@NonNull String application;

	/** {@code Mobile_Application_Access.IsAllowAllActions}, default {@code true} (like a grant via the application window) */
	@Builder.Default boolean isAllowAllActions = true;

	/**
	 * {@code Mobile_Application_Action.InternalName}s granted through {@code Mobile_Application_Action_Access}
	 * (relevant only when {@link #isAllowAllActions} is {@code false}), e.g. {@code createHU}
	 */
	@Nullable List<String> grantedActions;
}
