package de.metas.security;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * {@link I_AD_Role} related DAO.
 * 
 * @author tsa
 *
 */
public interface IRoleDAO extends ISingletonService
{
	Role getById(RoleId roleId);

	Set<RoleId> getUserRoleIds(UserId userId);

	List<Role> getUserRoles(UserId adUserId);

	Set<RoleId> getSubstituteRoleIds(UserId adUserId, LocalDate date);

	List<RoleInclude> retrieveRoleIncludes(RoleId adRoleId);

	IRolesTreeNode retrieveRolesTree(RoleId adRoleId, UserId substituteForUserId, LocalDate substituteDate);

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
	 */
	String getRoleName(RoleId adRoleId);

	Set<UserId> retrieveUserIdsForRoleId(RoleId adRoleId);

	RoleId retrieveFirstRoleIdForUserId(UserId adUserId);

	void createUserRoleAssignmentIfMissing(UserId adUserId, RoleId adRoleId);

	void deleteUserRolesByUserId(final UserId userId);
}
