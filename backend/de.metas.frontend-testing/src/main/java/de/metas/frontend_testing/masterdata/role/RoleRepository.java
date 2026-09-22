package de.metas.frontend_testing.masterdata.role;

import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.security.TableAccessLevel;
import lombok.NonNull;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Role_Included;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/**
 * Persistence for the {@code AD_Role} / {@code AD_Role_Included} records that the masterdata API creates for a
 * single test run.
 * <p>
 * Separate from {@link CreateRoleCommand} because persistence primitives
 * ({@code InterfaceWrapperHelper.save}) belong in a {@code *Repository}/{@code *DAO} rather than in a command —
 * see {@code docs/coding-rules/service-injection.md} §4. An ArchUnit rule enforces this.
 * <p>
 * Deliberately local to the frontend-testing module rather than added to the core {@code IRoleDAO}: creating a
 * role is a test-masterdata concern with no production caller, so it must not widen the security module's API.
 * <p>
 * Repository Tables: AD_Role, AD_Role_Included
 * Repository Cluster: RoleRepository
 */
public class RoleRepository
{
	/**
	 * Creates a client+org level role that is bound to a single org.
	 *
	 * @param name the final {@code AD_Role.Name}; the caller is responsible for its uniqueness.
	 */
	@NonNull
	public RoleId createRole(@NonNull final String name)
	{
		final I_AD_Role record = newInstance(I_AD_Role.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(name);
		record.setUserLevel(TableAccessLevel.ClientPlusOrganization.getUserLevelString());
		// One org only: the login offers one entry per (role, tenant, org), and it auto-completes only for a
		// single entry. IsAccessAllOrgs=Y would make that depend on how many orgs the stack happens to have.
		record.setIsAccessAllOrgs(false);
		save(record);

		return RoleId.ofRepoId(record.getAD_Role_ID());
	}

	/** Includes {@code includedRoleId} into {@code roleId}, i.e. creates one {@code AD_Role_Included} row. */
	public void createRoleInclusion(
			@NonNull final RoleId roleId,
			@NonNull final RoleId includedRoleId,
			final int seqNo)
	{
		final I_AD_Role_Included record = newInstance(I_AD_Role_Included.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setAD_Role_ID(roleId.getRepoId());
		record.setIncluded_Role_ID(includedRoleId.getRepoId());
		record.setSeqNo(seqNo);
		save(record);
	}
}
