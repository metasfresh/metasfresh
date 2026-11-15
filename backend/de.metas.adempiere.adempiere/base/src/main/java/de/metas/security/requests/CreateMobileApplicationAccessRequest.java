package de.metas.security.requests;

import com.google.common.collect.ImmutableSet;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@Builder
public class CreateMobileApplicationAccessRequest
{
	@NonNull RoleId roleId;
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@NonNull MobileApplicationRepoId applicationId;

	/**
	 * {@code Mobile_Application_Access.IsAllowAllActions}. {@code null} (default): a new row allows all actions and an existing
	 * row is left as it is. Not {@code null}: the row gets exactly this value, also when it already exists (e.g. created by
	 * {@code role_access_update()} when the role was created).
	 */
	@Nullable Boolean isAllowAllActions;

	/** Actions to grant through {@code Mobile_Application_Action_Access} (relevant when the access does not allow all actions); rows that exist are kept. */
	@NonNull @Builder.Default ImmutableSet<MobileApplicationActionId> allowedActionIds = ImmutableSet.of();
}
