package de.metas.security;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.SeqNo;

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

	/**
	 * Creates a client+org-level {@code AD_Role} bound to a single org.
	 *
	 * @param name the final {@code AD_Role.Name}; the caller is responsible for its uniqueness.
	 */
	RoleId createRole(String name);

	/** Includes {@code includedRoleId} into {@code roleId}, i.e. creates one {@code AD_Role_Included} row. */
	void createRoleInclusion(RoleId roleId, RoleId includedRoleId, SeqNo seqNo);
}
