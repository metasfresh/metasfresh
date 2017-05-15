package org.adempiere.ad.security;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Role_Included;

import de.metas.adempiere.model.I_AD_Role;

/**
 * {@link I_AD_Role} related DAO.
 * 
 * @author tsa
 *
 */
public interface IRoleDAO extends ISingletonService
{
	I_AD_Role retrieveRole(Properties ctx);

	I_AD_Role retrieveRole(Properties ctx, int AD_Role_ID);

	List<I_AD_Role> retrieveRolesForUser(Properties ctx, int adUserId);

	/**
	 * Retrieves substitute roles for a given user.
	 * 
	 * @param ctx
	 * @param adUserId AD_User_ID
	 * @param date date on which substitute roles shall be effective for given user
	 * @return substitute roles
	 */
	List<I_AD_Role> retrieveSubstituteRoles(Properties ctx, int adUserId, Date date);

	List<I_AD_Role_Included> retrieveRoleIncludes(Properties ctx, int adRoleId);

	IRolesTreeNode retrieveRolesTree(int adRoleId, int substitute_ForUserId, Date substituteDate);

	/**
	 * @param ctx
	 * @return all roles (from all clients) which were configured to be automatically maintained
	 */
	List<I_AD_Role> retrieveAllRolesWithAutoMaintenance(Properties ctx);

	/**
	 * @return all roles on which current user has access
	 */
	List<I_AD_Role> retrieveAllRolesWithUserAccess(Properties ctx);

	/**
	 * @param ctx
	 * @param adClientId AD_Client_ID
	 * @return all roles of given AD_Client_ID
	 */
	List<I_AD_Role> retrieveRolesForClient(Properties ctx, int adClientId);

	/**
	 * Convenient method to retrieve the role's name.
	 * 
	 * @param ctx
	 * @param adRoleId
	 * @return role's name
	 */
	String retrieveRoleName(Properties ctx, int adRoleId);

	List<Integer> retrieveUserIdsForRoleId(int adRoleId);

	int retrieveFirstRoleIdForUserId(int adUserId);

	void createUserRoleAssignmentIfMissing(int adUserId, int adRoleId);
}
