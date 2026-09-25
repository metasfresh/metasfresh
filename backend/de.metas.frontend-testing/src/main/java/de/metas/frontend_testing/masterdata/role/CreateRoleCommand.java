package de.metas.frontend_testing.masterdata.role;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.requests.CreateTableAccessRequest;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashSet;
import java.util.List;

/**
 * Creates a role for a single test run, with its {@code AD_Table_Access} rows. This command does not create
 * a user; to log in as this role a {@code login} user references it via
 * {@link de.metas.frontend_testing.masterdata.user.JsonLoginUserRequest#getRole()}, which binds that user to
 * this role and no other, so login completes without a role-selection step.
 */
@Builder
public class CreateRoleCommand
{
	/** {@code AD_Role.Name} is {@code varchar(60)}; a longer name would surface as a raw DB length error. */
	private static final int NAME_MAX_LENGTH = 60;

	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	@NonNull private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

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
				.roleId(roleId)
				.name(name)
				.build();
	}

	private RoleId createRole(@NonNull final String name)
	{
		final RoleId roleId = roleDAO.createRole(name);

		userRolePermissionsDAO.createOrgAccess(roleId, MasterdataContext.ORG_ID);

		SeqNo seqNo = SeqNo.first();
		for (final RoleId includedRoleId : getIncludedRoleIds())
		{
			roleDAO.createRoleInclusion(roleId, includedRoleId, seqNo);

			seqNo = seqNo.next();
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

			// Every JsonRoleTableAccessRequest flag defaults to its column's non-restricting value
			// (see JsonRoleTableAccessRequest), so each flag is written directly and a spec that sets
			// nothing yields a row equal to the column defaults.
			userRolePermissionsDAO.createTableAccess(CreateTableAccessRequest.builder()
					.roleId(roleId)
					.adTableId(adTableId)
					.readOnly(tableAccessRequest.isReadOnly())
					.canReport(tableAccessRequest.isCanReport())
					.canExport(tableAccessRequest.isCanExport())
					.canCreateNewRecords(tableAccessRequest.isCanCreateNewRecords())
					.build());
		}
	}
}
