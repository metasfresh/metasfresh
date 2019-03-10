package de.metas.security;

import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;

/**
 * {@link I_AD_Role} related DAO.
 * 
 * @author tsa
 *
 */
public interface IRoleDAO extends ISingletonService
{
	Role getById(RoleId roleId);

	Role getLoginRole(Properties ctx);

	Set<RoleId> getUserRoleIds(UserId userId);

	List<Role> getUserRoles(UserId adUserId);

	Set<RoleId> getSubstituteRoleIds(UserId adUserId, Date date);

	List<RoleInclude> retrieveRoleIncludes(RoleId adRoleId);

	IRolesTreeNode retrieveRolesTree(RoleId adRoleId, UserId substitute_ForUserId, Date substituteDate);

	/**
	 * @return all roles (from all clients) which were configured to be automatically maintained
	 */
	Collection<Role> retrieveAllRolesWithAutoMaintenance();

	/**
	 * @return all roles on which current user has access
	 */
	Collection<Role> retrieveAllRolesWithUserAccess();

	/**
	 * Convenient method to retrieve the role's name.
	 * 
	 * @param ctx
	 * @param adRoleId
	 * @return role's name
	 */
	String getRoleName(RoleId adRoleId);

	Set<UserId> retrieveUserIdsForRoleId(RoleId adRoleId);

	RoleId retrieveFirstRoleIdForUserId(UserId adUserId);

	void createUserRoleAssignmentIfMissing(UserId adUserId, RoleId adRoleId);
}
