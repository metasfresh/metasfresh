/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.kpi.data;

import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.ui.web.session.UserSession;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Properties;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class KPIDataContext
{
	public static final CtxName CTXNAME_AD_User_ID = CtxNames.parse("AD_User_ID");
	public static final CtxName CTXNAME_AD_Role_ID = CtxNames.parse("AD_Role_ID");
	public static final CtxName CTXNAME_AD_Client_ID = CtxNames.parse("AD_Client_ID");
	public static final CtxName CTXNAME_AD_Org_ID = CtxNames.parse("AD_Org_ID");

	@Nullable Instant from;
	@Nullable Instant to;

	@Nullable UserId userId;
	@Nullable RoleId roleId;
	@Nullable ClientId clientId;
	@Nullable OrgId orgId;

	public static KPIDataContext ofUserSession(@NonNull final UserSession userSession)
	{
		return builderFromUserSession(userSession).build();
	}

	public static KPIDataContextBuilder builderFromUserSession(@NonNull final UserSession userSession)
	{
		return builder()
				.userId(userSession.getLoggedUserId())
				.roleId(userSession.getLoggedRoleId())
				.clientId(userSession.getClientId())
				.orgId(userSession.getOrgId());
	}

	public static KPIDataContext ofEnvProperties(@NonNull final Properties ctx)
	{
		return builder()
				.userId(Env.getLoggedUserIdIfExists(ctx).orElse(null))
				.roleId(Env.getLoggedRoleIdIfExists(ctx).orElse(null))
				.clientId(Env.getClientId(ctx))
				.orgId(Env.getOrgId(ctx))
				.build();
	}

	public KPIDataContext retainOnlyRequiredParameters(@NonNull final Set<CtxName> requiredParameters)
	{
		UserId userId_new = null;
		RoleId roleId_new = null;
		ClientId clientId_new = null;
		OrgId orgId_new = null;

		for (final CtxName requiredParam : requiredParameters)
		{
			final String requiredParamName = requiredParam.getName();
			if (CTXNAME_AD_User_ID.getName().equals(requiredParamName)
					|| Env.CTXNAME_AD_User_ID.equals(requiredParamName))
			{
				userId_new = this.userId;
			}
			else if (CTXNAME_AD_Role_ID.getName().equals(requiredParamName)
					|| Env.CTXNAME_AD_Role_ID.equals(requiredParamName))
			{
				roleId_new = this.roleId;
			}
			else if (CTXNAME_AD_Client_ID.getName().equals(requiredParamName)
					|| Env.CTXNAME_AD_Client_ID.equals(requiredParamName))
			{
				clientId_new = this.clientId;
			}
			else if (CTXNAME_AD_Org_ID.getName().equals(requiredParamName)
					|| Env.CTXNAME_AD_Org_ID.equals(requiredParamName))
			{
				orgId_new = this.orgId;
			}
		}

		return toBuilder()
				.userId(userId_new)
				.roleId(roleId_new)
				.clientId(clientId_new)
				.orgId(orgId_new)
				.build();
	}
}
