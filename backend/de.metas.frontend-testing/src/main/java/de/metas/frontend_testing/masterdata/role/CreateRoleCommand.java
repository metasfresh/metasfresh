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
import de.metas.security.requests.CreateRoleRequest;
import de.metas.security.requests.CreateTableAccessRequest;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.security.UserAuthTokenService;
import de.metas.workplace.WorkplaceService;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Creates a role for a single test run, with its {@code AD_Table_Access} rows and - when asked for - a
 * user that is bound to this role and to no other one.
 * <p>
 * The single-role binding is what lets a spec log in without a role-selection step: {@code LoginRestController}
 * completes the login itself when exactly one role is available.
 */
@Builder
public class CreateRoleCommand
{
	/** Suffix under which the role's user is registered in the {@link MasterdataContext}. */
	public static final String USER_IDENTIFIER_SUFFIX = "_roleUser";

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
		final String customName = request.getName();
		final String name = Check.isNotBlank(customName) ? customName : identifier.toUniqueString();

		final RoleId roleId = roleDAO.createRole(CreateRoleRequest.builder()
				.name(name)
				.orgId(OrgId.ANY)
				.userLevel(TableAccessLevel.ClientPlusOrganization)
				.accessAllOrgs(true)
				.includedRoleIds(getIncludedRoleIds())
				.build());
		context.putIdentifier(identifier, roleId);

		createTableAccessRecords(roleId);

		return JsonCreateRoleResponse.builder()
				.roleId(roleId.getRepoId())
				.name(name)
				.user(createUser(roleId))
				.build();
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
				.map(includedRole -> context.getOptionalId(includedRole, RoleId.class)
						.orElseGet(() -> includedRole.toId(RoleId.class)))
				.collect(ImmutableList.toImmutableList());
	}

	private void createTableAccessRecords(@NonNull final RoleId roleId)
	{
		final List<JsonRoleTableAccessRequest> tableAccessRequests = request.getTableAccess();
		if (tableAccessRequests == null || tableAccessRequests.isEmpty())
		{
			return;
		}

		for (final JsonRoleTableAccessRequest tableAccessRequest : tableAccessRequests)
		{
			userRolePermissionsDAO.createTableAccess(CreateTableAccessRequest.builder()
					.roleId(roleId)
					.orgId(OrgId.ANY)
					.adTableId(adTableDAO.retrieveAdTableId(tableAccessRequest.getTableName()))
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
		// has a single role to offer and completes without a role-selection step.
		final UserId userId = context.getId(userIdentifier, UserId.class);
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
