/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.workflow;

import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class WFResponsible
{
	@NonNull WFResponsibleId id;
	@NonNull WFResponsibleType type;
	@NonNull String name;
	@Nullable UserId userId;
	@Nullable RoleId roleId;
	@Nullable OrgId orgId;

	public boolean isInvoker() {return userId == null && roleId == null;}

	public boolean isHuman() {return type == WFResponsibleType.Human && userId != null;}

	public boolean isRole() {return type == WFResponsibleType.Role && roleId != null;}

	public boolean isOrganization() {return type == WFResponsibleType.Organization && orgId != null;}

	@NonNull
	public UserId getUserIdNotNull() {return Check.assumeNotNull(userId, "Expected an user based responsible: {}", this);}

	@NonNull
	public RoleId getRoleIdNotNull() {return Check.assumeNotNull(roleId, "Expected a role based responsible: {}", this);}
}
