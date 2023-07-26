package de.metas.security;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Role_OrgAccess;

import com.google.common.base.Optional;

import de.metas.organization.OrgId;
import de.metas.security.impl.RolePermissionsNotFoundException;
import de.metas.security.requests.CreateDocActionAccessRequest;
import de.metas.security.requests.CreateFormAccessRequest;
import de.metas.security.requests.CreateProcessAccessRequest;
import de.metas.security.requests.CreateRecordPrivateAccessRequest;
import de.metas.security.requests.CreateTaskAccessRequest;
import de.metas.security.requests.CreateWindowAccessRequest;
import de.metas.security.requests.CreateWorkflowAccessRequest;
import de.metas.security.requests.RemoveDocActionAccessRequest;
import de.metas.security.requests.RemoveFormAccessRequest;
import de.metas.security.requests.RemoveProcessAccessRequest;
import de.metas.security.requests.RemoveRecordPrivateAccessRequest;
import de.metas.security.requests.RemoveTaskAccessRequest;
import de.metas.security.requests.RemoveWindowAccessRequest;
import de.metas.security.requests.RemoveWorkflowAccessRequest;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;

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
	IUserRolePermissions getUserRolePermissions(RoleId adRoleId, UserId adUserId, ClientId adClientId, LocalDate date);

	IUserRolePermissions getUserRolePermissions(UserRolePermissionsKey key);

	/**
	 * Retrieves {@link IUserRolePermissions} assigned to given user and which have (readonly) access to given organization.
	 * 
	 * @return permissions with organization access.
	 */
	List<IUserRolePermissions> retrieveUserRolesPermissionsForUserWithOrgAccess(ClientId clientId, OrgId orgId, UserId adUserId, LocalDate localDate);

	/**
	 * Retrieves first {@link IUserRolePermissions} assigned to given user and which have (readonly) access to given organization.
	 * 
	 * @return permissions with organization access.
	 * @see #retrieveUserRolesPermissionsForUserWithOrgAccess(ClientId, OrgId, UserId, LocalDate)
	 */
	Optional<IUserRolePermissions> retrieveFirstUserRolesPermissionsForUserWithOrgAccess(ClientId clientId, OrgId orgId, UserId adUserId, LocalDate localDate);

	boolean matchUserRolesPermissionsForUser(ClientId clientId, UserId adUserId, LocalDate date, Predicate<IUserRolePermissions> matcher);

	/**
	 * Re-create Access Records for all automatic roles
	 */
	void updateAccessRecordsForAllRoles();

	/**
	 * Re-create Access Records if the role is not automatic (i.e. {@link I_AD_Role#isManual()} is false)
	 *
	 * @return info
	 */
	String updateAccessRecords(Role role);

	void updateAccessRecords(RoleId roleId, UserId createdByUserId);

	/**
	 * Delete Access Records of the role.
	 *
	 * WARNING: to be called after the role was (successfully) deleted.
	 */
	void deleteAccessRecords(RoleId roleId);

	/**
	 * If this method was called, also consider accounting related permissions. Supposed to be called from the accounting module.
	 */
	void setAccountingModuleActive();

	/**
	 * 
	 * @return always return {@code false}, unless {@link #setAccountingModuleActive()} was previously called.
	 */
	boolean isAccountingModuleActive();

	void createOrgAccess(RoleId adRoleId, OrgId adOrgId);

	List<I_AD_Role_OrgAccess> retrieveRoleOrgAccessRecordsForOrg(OrgId adOrgId);

	void createWindowAccess(CreateWindowAccessRequest request);

	void deleteWindowAccess(RemoveWindowAccessRequest request);

	void createProcessAccess(CreateProcessAccessRequest request);

	void deleteProcessAccess(RemoveProcessAccessRequest request);

	void createFormAccess(CreateFormAccessRequest request);

	void deleteFormAccess(RemoveFormAccessRequest request);

	void createTaskAccess(CreateTaskAccessRequest request);

	void deleteTaskAccess(RemoveTaskAccessRequest request);

	void createWorkflowAccess(CreateWorkflowAccessRequest request);

	void deleteWorkflowAccess(RemoveWorkflowAccessRequest request);

	void createDocumentActionAccess(CreateDocActionAccessRequest request);

	void deleteDocumentActionAccess(RemoveDocActionAccessRequest request);

	void createPrivateAccess(CreateRecordPrivateAccessRequest request);

	void deletePrivateAccess(RemoveRecordPrivateAccessRequest request);

	/**
	 * @return true if given user has a role where he/she is an administrator, according to {@link IUserRolePermissions#isSystemAdministrator()}
	 */
	boolean isAdministrator(ClientId clientId, UserId adUserId, LocalDate date);
}
