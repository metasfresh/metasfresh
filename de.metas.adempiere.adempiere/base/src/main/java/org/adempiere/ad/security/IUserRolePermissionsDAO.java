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
import java.util.function.Consumer;

import org.adempiere.ad.security.impl.RolePermissionsNotFoundException;
import org.adempiere.ad.security.permissions.ElementPermissions;
import org.adempiere.ad.security.permissions.OrgPermissions;
import org.adempiere.ad.security.permissions.TableColumnPermissions;
import org.adempiere.ad.security.permissions.TablePermissions;
import org.adempiere.ad.security.permissions.TableRecordPermissions;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_AD_Record_Access;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Role_OrgAccess;

import com.google.common.base.Optional;

/**
 * {@link IUserRolePermissions} retrieval DAO.
 *
 * @author tsa
 *
 */
public interface IUserRolePermissionsDAO extends ISingletonService
{
	/** @return role dependent tables; it does not include AD_Role table; it might include tables which does not have AD_Role_ID column */
	Set<String> getRoleDependentTableNames();

	/**
	 * Gets current cache version.
	 * 
	 * The cache version is incremented on each cache reset call.
	 */
	long getCacheVersion();

	/**
	 * Resets all role and permissions related caches after current transaction is commited.
	 * If there is no current transaction, the caches will be reset right away.
	 * 
	 * If a cache reset was already scheduled for current transaction this method won't schedule another one.
	 */
	void resetCacheAfterTrxCommit();

	/**
	 * Resets all role and permissions related caches.
	 * It won't broadcast the event.
	 */
	void resetLocalCache();

	/**
	 * Retrieves user/role permissions.
	 *
	 * @param adRoleId
	 * @param adUserId
	 * @param adClientId
	 * @param date date when permissions shall be effective
	 * @return user/role permissions
	 * @throws RolePermissionsNotFoundException if permissions could not be loaded
	 */
	IUserRolePermissions retrieveUserRolePermissions(int adRoleId, int adUserId, int adClientId, Date date);

	IUserRolePermissions retrieveUserRolePermissions(UserRolePermissionsKey key);

	/**
	 * Retrieves {@link IUserRolePermissions} assigned to given user and which have (readonly) access to given organization.
	 *
	 * @param ctx
	 * @param adUserId user
	 * @param adOrgId organization
	 * @return permissions with organization access.
	 */
	List<IUserRolePermissions> retrieveUserRolesPermissionsForUserWithOrgAccess(Properties ctx, int adUserId, int adOrgId);

	/**
	 * Retrieves first {@link IUserRolePermissions} assigned to given user and which have (readonly) access to given organization.
	 *
	 * @param ctx
	 * @param adUserId user
	 * @param adOrgId organization
	 * @return permissions with organization access.
	 * @see #retrieveUserRolesPermissionsForUserWithOrgAccess(Properties, int, int)
	 */
	Optional<IUserRolePermissions> retrieveFirstUserRolesPermissionsForUserWithOrgAccess(Properties ctx, int adUserId, int adOrgId);

	boolean matchUserRolesPermissionsForUser(Properties ctx, int adUserId, Predicate<IUserRolePermissions> matcher);

	OrgPermissions retrieveOrgPermissions(final I_AD_Role role, final int adUserId);

	OrgPermissions retrieveRoleOrgPermissions(final int adRoleId, final int adTreeOrgId);

	OrgPermissions retrieveUserOrgPermissions(final int adUserId, final int adTreeOrgId);

	ElementPermissions retrieveWorkflowPermissions(final int adRoleId, final int adClientId);

	ElementPermissions retrieveFormPermissions(final int adRoleId, final int adClientId);

	ElementPermissions retrieveTaskPermissions(final int adRoleId, final int adClientId);

	ElementPermissions retrieveProcessPermissions(final int adRoleId, final int adClientId);

	ElementPermissions retrieveWindowPermissions(final int adRoleId, final int adClientId);

	TablePermissions retrieveTablePermissions(final int adRoleId);

	TableColumnPermissions retrieveTableColumnPermissions(final int adRoleId);

	TableRecordPermissions retrieveRecordPermissions(final int adRoleId);

	/**
	 * Re-create Access Records for all automatic roles
	 */
	void updateAccessRecordsForAllRoles();

	/**
	 * Re-create Access Records if the role is not automatic (i.e. {@link I_AD_Role#isManual()} is false)
	 *
	 * @return info
	 */
	String updateAccessRecords(I_AD_Role role);

	/**
	 * Delete Access Records of the role.
	 *
	 * WARNING: to be called after the role was (successfully) deleted.
	 */
	void deleteAccessRecords(I_AD_Role role);

	/**
	 * If this method was called, also consider accounting related permissions. Supposed to be called from the accounting module.
	 */
	void setAccountingModuleActive();

	/**
	 * 
	 * @return always return {@code false}, unless {@link #setAccountingModuleActive()} was previously called.
	 */
	boolean isAccountingModuleActive();

	void createOrgAccess(int adRoleId, int adOrgId);

	List<I_AD_Role_OrgAccess> retrieveRoleOrgAccessRecordsForOrg(int adOrgId);

	void createWindowAccess(I_AD_Role role, int adWindowId, boolean readWrite);

	void deleteWindowAccess(I_AD_Role role, int adWindowId);

	void createProcessAccess(I_AD_Role role, int adProcessId, boolean readWrite);

	void deleteProcessAccess(I_AD_Role role, int adProcessId);

	void createFormAccess(I_AD_Role role, int adFormId, boolean readWrite);

	void deleteFormAccess(I_AD_Role role, int adFormId);

	void createTaskAccess(I_AD_Role role, int adTaskId, boolean readWrite);

	void deleteTaskAccess(I_AD_Role role, int adTaskId);

	void createWorkflowAccess(I_AD_Role role, int adWorkflowId, boolean readWrite);

	void deleteWorkflowAccess(I_AD_Role role, int adWorkflowId);

	void createDocumentActionAccess(I_AD_Role role, int docTypeId, int docActionRefListId);

	void deleteDocumentActionAccess(I_AD_Role role, int docTypeId, int docActionRefListId);

	I_AD_Record_Access changeRecordAccess(I_AD_Role role, int adTableId, int recordId, Consumer<I_AD_Record_Access> updater);

	List<I_AD_Record_Access> retrieveRecordAccesses(int adTableId, int recordId, int adClientId);

	void createPrivateAccess(int adUserId, int adTableId, int recordId);

	void deletePrivateAccess(int adUserId, int adTableId, int recordId);

	Set<Integer> retrievePrivateAccessRecordIds(int adUserId, int adTableId);

	/**
	 * @return true if given user has a role where he/she is an administrator, according to {@link IUserRolePermissions#isSystemAdministrator()} 
	 */
	boolean isAdministrator(Properties ctx, int adUserId);
}
