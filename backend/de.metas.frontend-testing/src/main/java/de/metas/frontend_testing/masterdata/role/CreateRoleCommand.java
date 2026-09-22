package de.metas.frontend_testing.masterdata.role;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserRequest;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserResponse;
import de.metas.frontend_testing.masterdata.user.LoginUserCommand;
import de.metas.organization.OrgId;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.TableAccessLevel;
import de.metas.security.UserAuthToken;
import de.metas.security.requests.CreateTableAccessRequest;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.security.UserAuthTokenService;
import de.metas.workplace.WorkplaceService;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Role_Included;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

/**
 * Creates a role for a single test run, with its {@code AD_Table_Access} rows and - when asked for - a
 * user that is bound to this role and to no other one.
 * <p>
 * The single-role binding is what lets a spec log in without a role-selection step: {@code LoginRestController}
 * completes the login itself when exactly one (role, tenant, org) entry is available.
 */
@Builder
public class CreateRoleCommand
{
	/** Suffix under which the role's user is registered in the {@link MasterdataContext}. */
	public static final String USER_IDENTIFIER_SUFFIX = "_roleUser";

	/** {@code AD_Role.Name} is {@code varchar(60)}; a longer name would surface as a raw DB length error. */
	private static final int NAME_MAX_LENGTH = 60;

	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	@NonNull private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	@NonNull private final UserAuthTokenService userAuthTokenService;
	@NonNull private final WorkplaceService workplaceService;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateRoleRequest request;
	@NonNull private final Identifier identifier;

	public JsonCreateRoleResponse execute()
	{
		// The name goes through toUniqueString() even when the request supplies one: AD_Role.Name carries a
		// unique index over the active rows, so a verbatim label collides across runs reusing it.
		final String customName = request.getName();
		final String name = Check.isNotBlank(customName)
				? Identifier.ofString(customName).toUniqueString()
				: identifier.toUniqueString();
		// toUniqueString() appends _yyyyMMddTHHmmssSSS (19 chars), so the checked value is the final name.
		if (name.length() > NAME_MAX_LENGTH)
		{
			throw new AdempiereException("name must not exceed " + NAME_MAX_LENGTH + " characters including the"
					+ " uniqueness suffix (got: `" + name + "`, " + name.length() + " characters)");
		}

		final RoleId roleId = createRole(name);
		context.putIdentifier(identifier, roleId);

		createTableAccessRecords(roleId);

		return JsonCreateRoleResponse.builder()
				.roleId(roleId.getRepoId())
				.name(name)
				.user(createUser(roleId))
				.build();
	}

	private RoleId createRole(@NonNull final String name)
	{
		final I_AD_Role record = InterfaceWrapperHelper.newInstance(I_AD_Role.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(name);
		record.setUserLevel(TableAccessLevel.ClientPlusOrganization.getUserLevelString());
		// One org only: the login offers one entry per (role, tenant, org), and it auto-completes only for a
		// single entry. IsAccessAllOrgs=Y would make that depend on how many orgs the stack happens to have.
		record.setIsAccessAllOrgs(false);
		InterfaceWrapperHelper.save(record);

		final RoleId roleId = RoleId.ofRepoId(record.getAD_Role_ID());

		userRolePermissionsDAO.createOrgAccess(roleId, MasterdataContext.ORG_ID);

		int seqNo = 10;
		for (final RoleId includedRoleId : getIncludedRoleIds())
		{
			final I_AD_Role_Included includedRecord = InterfaceWrapperHelper.newInstance(I_AD_Role_Included.class);
			includedRecord.setAD_Org_ID(OrgId.ANY.getRepoId());
			includedRecord.setAD_Role_ID(roleId.getRepoId());
			includedRecord.setIncluded_Role_ID(includedRoleId.getRepoId());
			includedRecord.setSeqNo(seqNo);
			InterfaceWrapperHelper.save(includedRecord);

			seqNo += 10;
		}

		return roleId;
	}

	private ImmutableList<RoleId> getIncludedRoleIds()
	{
		final List<Identifier> includedRoles = request.getIncludedRoles();
		if (includedRoles == null)
		{
			// Without the WebUI role the new role has no WebUI menu at all, so it's the sensible default.
			return ImmutableList.of(RoleId.WEBUI);
		}

		return includedRoles.stream()
				.map(this::getIncludedRoleId)
				.collect(ImmutableList.toImmutableList());
	}

	private RoleId getIncludedRoleId(@NonNull final Identifier includedRole)
	{
		return context.getOptionalId(includedRole, RoleId.class)
				.orElseGet(() -> {
					try
					{
						return includedRole.toId(RoleId.class);
					}
					catch (final Exception ex)
					{
						throw new AdempiereException("includedRoles: no role created under the identifier `"
								+ includedRole.getAsString() + "`, and it is not an AD_Role_ID either", ex);
					}
				});
	}

	private void createTableAccessRecords(@NonNull final RoleId roleId)
	{
		final List<JsonRoleTableAccessRequest> tableAccessRequests = request.getTableAccess();
		if (tableAccessRequests == null || tableAccessRequests.isEmpty())
		{
			return;
		}

		// A role has at most one AD_Table_Access row per table, so a table listed twice would fail with a raw
		// unique violation; say which table it is instead.
		final HashSet<AdTableId> seenTableIds = new HashSet<>();

		for (final JsonRoleTableAccessRequest tableAccessRequest : tableAccessRequests)
		{
			final AdTableId adTableId = adTableDAO.retrieveAdTableId(tableAccessRequest.getTableName());
			if (!seenTableIds.add(adTableId))
			{
				throw new AdempiereException("tableAccess: `" + tableAccessRequest.getTableName()
						+ "` is listed more than once; a role has one row per table, so state every flag in that one row");
			}

			userRolePermissionsDAO.createTableAccess(CreateTableAccessRequest.builder()
					.roleId(roleId)
					.orgId(OrgId.ANY)
					.adTableId(adTableId)
					.exclude(tableAccessRequest.getExclude())
					.readOnly(tableAccessRequest.getReadOnly())
					.canReport(tableAccessRequest.getCanReport())
					.canExport(tableAccessRequest.getCanExport())
					.canCreateNewRecords(tableAccessRequest.getCanCreateNewRecords())
					.build());
		}
	}

	@Nullable
	private JsonLoginUserResponse createUser(@NonNull final RoleId roleId)
	{
		final JsonLoginUserRequest userRequest = request.getUser();
		if (userRequest == null)
		{
			return null;
		}

		final Identifier userIdentifier = Identifier.ofString(identifier.getAsString() + USER_IDENTIFIER_SUFFIX);
		final JsonLoginUserResponse user = LoginUserCommand.builder()
				.userAuthTokenService(userAuthTokenService)
				.workplaceService(workplaceService)
				.context(context)
				.request(userRequest)
				.identifier(userIdentifier)
				.build()
				.execute();

		// LoginUserCommand binds the user to the WebUI role; rebind it to this role ONLY, so that the login
		// has a single entry to offer and completes without a role-selection step. Its WebUI auth token goes
		// too: a token carries the role it was issued for and the authentication reads that role off the
		// token, so a token left behind would keep authenticating as the unrestricted WebUI role.
		final UserId userId = context.getId(userIdentifier, UserId.class);
		userAuthTokenService.deleteTokensByUserId(userId);
		roleDAO.deleteUserRolesByUserId(userId);
		roleDAO.createUserRoleAssignmentIfMissing(userId, roleId);

		final UserAuthToken token = userAuthTokenService.getOrCreateNewToken(CreateUserAuthTokenRequest.builder()
				.userId(userId)
				.roleId(roleId)
				.clientId(ClientId.METASFRESH)
				.orgId(MasterdataContext.ORG_ID)
				.description("generated by frontend testing role masterdata")
				.build());

		return JsonLoginUserResponse.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.token(token.getAuthToken())
				.build();
	}
}
