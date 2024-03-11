package de.metas.security.permissions;

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

import com.google.common.collect.ImmutableSet;
import de.metas.organization.OrgId;
import de.metas.security.permissions.PermissionsBuilder.CollisionPolicy;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.MTree_Base;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Immutable
public class OrgPermissions extends AbstractPermissions<OrgPermission>
{
	public static Builder builder(@Nullable final AdTreeId orgTreeId)
	{
		return new Builder(orgTreeId);
	}

	private final AdTreeId orgTreeId;
	private final ImmutableSet<ClientId> adClientIds;
	private final ImmutableSet<OrgId> adOrgIds;

	private OrgPermissions(final Builder builder)
	{
		super(builder);
		this.orgTreeId = builder.getOrgTreeId();
		this.adClientIds = builder.adClientIds.build();
		this.adOrgIds = builder.adOrgIds.build();
	}

	public Builder asNewBuilder()
	{
		final Builder builder = builder(orgTreeId);
		builder.addPermissions(this, CollisionPolicy.Override);
		return builder;
	}

	/**
	 * @return all AD_Client_IDs on which we have at least read permissions
	 */
	public Set<ClientId> getAD_Client_IDs()
	{
		return adClientIds;
	}

	// TODO: cache it using memorized function
	public String getClientWhere(final String tableName, final String tableAlias, final Access access)
	{
		return buildClientWhere(tableName, tableAlias, access, getAD_Client_IDs());
	}

	public static String buildClientWhere(
			final String tableName,
			final String tableAlias,
			@NonNull final Access access,
			@NonNull final Set<ClientId> adClientIds)
	{
		final HashSet<ClientId> adClientIdsEffective = new HashSet<>(adClientIds);
		if (access.isReadOnly())
		{
			adClientIdsEffective.add(ClientId.SYSTEM);
		}

		final String tablePrefix = tableAlias != null ? tableAlias + "." : "";

		if (adClientIdsEffective.isEmpty())
		{
			return "/* no Org Access records */ " + tablePrefix + "AD_Client_ID=-1";
		}

		final StringBuilder whereClause = new StringBuilder();
		whereClause.append(DB.buildSqlList(tablePrefix + "AD_Client_ID", adClientIdsEffective, /* paramsOut */null));

		if (access.isReadWrite() && "AD_Org".equals(tableName))
		{
			whereClause.append(" OR (")
					.append(tablePrefix).append("AD_Client_ID=").append(ClientId.SYSTEM.getRepoId())
					.append(" AND ").append(tablePrefix).append("AD_Org_ID=").append(OrgId.ANY.getRepoId())
					.append(")");

			whereClause.insert(0, "(").append(")");
		}

		return whereClause.toString();
	}

	/**
	 * @return true if there is access to given AD_Client_ID
	 */
	public boolean isClientAccess(final ClientId clientId, final Access access)
	{
		// Positive List
		for (final OrgPermission perm : getPermissionsList())
		{
			if (ClientId.equals(perm.getClientId(), clientId))
			{
				if (access.isReadOnly())
				{
					return true;
				}
				if (!perm.isReadOnly())
				{
					return true;
				}
			}
		}
		return false;
	}

	public String getAD_Org_IDs_AsString()
	{
		return adOrgIds.stream()
				.map(OrgId::getRepoId)
				.map(String::valueOf)
				.collect(Collectors.joining(","));
	}

	// FRESH-560: Retrieve the org IDs as set
	public Set<OrgId> getAD_Org_IDs_AsSet()
	{
		return adOrgIds;
	}

	/**
	 * @return list of AD_Org_IDs on which we have access
	 */
	public Set<OrgId> getOrgAccess(final Access access)
	{
		final Set<OrgId> adOrgIds = new HashSet<>();
		if (Access.READ.equals(access))
		{
			adOrgIds.add(OrgId.ANY);
		}

		// Positive List
		for (final OrgPermission perm : getPermissionsList())
		{
			if (perm.hasAccess(access))
			{
				adOrgIds.add(perm.getOrgId());
			}
		}

		return adOrgIds;
	}

	/**
	 * @return resources which have given access and are matched by given matcher (read-write set)
	 */
	public Set<OrgResource> getResourcesWithAccessThatMatch(final Access access, final Predicate<OrgResource> matcher)
	{
		Set<OrgResource> result = new LinkedHashSet<>();
		for (final OrgPermission perm : getPermissionsList())
		{
			if (!perm.hasAccess(access))
			{
				continue;
			}

			final OrgResource resource = perm.getResource();
			if (!matcher.test(resource))
			{
				continue;
			}

			result.add(resource);
		}

		return result;
	}

	@SuppressWarnings("UnusedReturnValue")
	public static class Builder extends PermissionsBuilder<OrgPermission, OrgPermissions>
	{
		private final AdTreeId orgTreeId;
		private final ImmutableSet.Builder<ClientId> adClientIds = ImmutableSet.builder();
		private final ImmutableSet.Builder<OrgId> adOrgIds = ImmutableSet.builder();

		private Builder(@Nullable final AdTreeId orgTreeId)
		{
			this.orgTreeId = orgTreeId;
		}

		@Override
		protected void buildPrepare()
		{
			for (final OrgPermission perm : getPermissionsInternalMap().values())
			{
				adClientIds.add(perm.getClientId());
				adOrgIds.add(perm.getOrgId());
			}
		}

		@Override
		protected OrgPermissions createPermissionsInstance()
		{
			return new OrgPermissions(this);
		}

		private AdTreeId getOrgTreeId()
		{
			return orgTreeId;
		}

		/**
		 * Load Org Access Add Tree to List
		 */
		public Builder addPermissionRecursively(final OrgPermission oa)
		{
			if (hasPermission(oa))
			{
				return this;
			}
			addPermission(oa, CollisionPolicy.Override);

			// Do we look for trees?
			final AdTreeId adTreeOrgId = getOrgTreeId();
			if (adTreeOrgId == null)
			{
				return this;
			}

			final OrgResource orgResource = oa.getResource();
			if (!orgResource.isGroupingOrg())
			{
				return this;
			}

			// Summary Org - Get Dependents
			final MTree_Base tree = MTree_Base.getById(adTreeOrgId);
			final String sql = "SELECT "
					+ "  AD_Client_ID"
					+ ", AD_Org_ID"
					+ ", " + I_AD_Org.COLUMNNAME_IsSummary
					+ " FROM AD_Org "
					+ " WHERE IsActive='Y' AND AD_Org_ID IN (SELECT Node_ID FROM " + tree.getNodeTableName()
					+ " WHERE AD_Tree_ID=? AND Parent_ID=? AND IsActive='Y')";
			final Object[] sqlParams = new Object[] { tree.getAD_Tree_ID(), orgResource.getOrgId() };
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				DB.setParameters(pstmt, sqlParams);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					final ClientId clientId = ClientId.ofRepoId(rs.getInt(1));
					final OrgId orgId = OrgId.ofRepoId(rs.getInt(2));
					final boolean isGroupingOrg = StringUtils.toBoolean(rs.getString(3));
					final OrgResource resource = OrgResource.of(clientId, orgId, isGroupingOrg);
					final OrgPermission childOrgPermission = oa.copyWithResource(resource);
					addPermissionRecursively(childOrgPermission);
				}

				return this;
			}
			catch (final SQLException e)
			{
				throw new DBException(e, sql, sqlParams);
			}
			finally
			{
				DB.close(rs, pstmt);
			}
		}

	}
}
