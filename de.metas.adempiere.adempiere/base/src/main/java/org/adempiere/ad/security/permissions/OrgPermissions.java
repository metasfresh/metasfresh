package org.adempiere.ad.security.permissions;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.security.permissions.PermissionsBuilder.CollisionPolicy;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.MTree_Base;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.collections.NullPredicate;

@Immutable
public class OrgPermissions extends AbstractPermissions<OrgPermission>
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final ImmutableSet<Integer> adClientIds;
	private final ImmutableSet<Integer> adOrgIds;
	private final int orgTreeId;

	private OrgPermissions(final Builder builder)
	{
		super(builder);
		this.adClientIds = builder.adClientIds.build();
		this.adOrgIds = builder.adOrgIds.build();
		this.orgTreeId = builder.getOrg_Tree_ID();
	}

	public Builder asNewBuilder()
	{
		final Builder builder = builder();
		builder.setOrg_Tree_ID(orgTreeId);
		builder.addPermissions(this, CollisionPolicy.Override);
		return builder;
	}

	public final int getOrg_Tree_ID()
	{
		return orgTreeId;
	}

	/**
	 * @return all AD_Client_IDs on which we have at least read permissions
	 */
	public Set<Integer> getAD_Client_IDs()
	{
		return adClientIds;
	}

	// TODO: cache it using memorized function
	public String getClientWhere(final String tableName, final String tableAlias, final boolean rw)
	{
		return buildClientWhere(tableName, tableAlias, rw, getAD_Client_IDs());
	}

	public static String buildClientWhere(
			final String tableName,
			final String tableAlias,
			final boolean rw,
			final Set<Integer> adClientIds)
	{
		final HashSet<Integer> adClientIdsEffective = new HashSet<>(adClientIds);
		if (!rw)
		{
			adClientIdsEffective.add(OrgPermission.AD_Client_ID_System);
		}

		final String tablePrefix = tableAlias != null ? tableAlias + "." : "";

		if (adClientIdsEffective.isEmpty())
		{
			return "/* no Org Access records */ " + tablePrefix + "AD_Client_ID=-1";
		}

		final StringBuilder whereClause = new StringBuilder();
		whereClause.append(DB.buildSqlList(tablePrefix + "AD_Client_ID", adClientIdsEffective, /* paramsOut */null));

		if (rw && "AD_Org".equals(tableName))
		{
			whereClause.append(" OR (")
					.append(tablePrefix).append("AD_Client_ID=").append(OrgPermission.AD_Client_ID_System)
					.append(" AND ").append(tablePrefix).append("AD_Org_ID=").append(OrgPermission.AD_Org_ID_System)
					.append(")");

			whereClause.insert(0, "(").append(")");
		}

		return whereClause.toString();
	}

	/**
	 * @param AD_Client_ID
	 * @param rw true if read-write access is required, false if read-only access is required
	 * @return true if there is access to given AD_Client_ID
	 */
	public boolean isClientAccess(final int AD_Client_ID, final boolean rw)
	{
		// Positive List
		for (final OrgPermission perm : getPermissionsList())
		{
			if (perm.getAD_Client_ID() == AD_Client_ID)
			{
				if (!rw)
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
		return Joiner.on(',')
				.skipNulls()
				.join(adOrgIds);
	}

	// FRESH-560: Retrieve the org IDs as set
	public Set<Integer> getAD_Org_IDs_AsSet()
	{
		return adOrgIds;
	}

	/**
	 * @param rw true if read-write access is required, false if read-only access is required
	 * @return list of AD_Org_IDs on which we have access
	 */
	public Set<Integer> getOrgAccess(final boolean rw)
	{
		final Set<Integer> adOrgIds = new HashSet<>();
		if (!rw)
		{
			adOrgIds.add(Env.CTXVALUE_AD_Org_ID_System);
		}

		final Access access = rw ? Access.WRITE : Access.READ;

		// Positive List
		for (final OrgPermission perm : getPermissionsList())
		{
			if (perm.hasAccess(access))
			{
				adOrgIds.add(perm.getAD_Org_ID());
			}
		}

		return adOrgIds;
	}

	/**
	 * @param access
	 * @return resources which have given access (read-write set)
	 */
	public Set<OrgResource> getResourcesWithAccess(final Access access)
	{
		Predicate<OrgResource> matcher = NullPredicate.of();
		return getResourcesWithAccessThatMatch(access, matcher);
	}

	/**
	 * @param access
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

	public static class Builder extends PermissionsBuilder<OrgPermission, OrgPermissions>
	{
		private final ImmutableSet.Builder<Integer> adClientIds = ImmutableSet.builder();
		private final ImmutableSet.Builder<Integer> adOrgIds = ImmutableSet.builder();
		private Integer _orgTreeId;

		Builder()
		{
			super();
		}

		@Override
		protected void buildPrepare()
		{
			for (final OrgPermission perm : getPermissionsInternalMap().values())
			{
				adClientIds.add(perm.getAD_Client_ID());
				adOrgIds.add(perm.getAD_Org_ID());
			}
		}

		@Override
		protected OrgPermissions createPermissionsInstance()
		{
			return new OrgPermissions(this);
		}

		public Builder setOrg_Tree_ID(final int orgTreeId)
		{
			this._orgTreeId = orgTreeId;
			return this;
		}

		private final int getOrg_Tree_ID()
		{
			Check.assumeNotNull(_orgTreeId, "Org's AD_Tree_ID shall be configured");
			return _orgTreeId;
		}

		/**
		 * Load Org Access Add Tree to List
		 *
		 * @param list list
		 * @param oa org access
		 * @return
		 * @see org.compiere.util.Login
		 */
		public Builder addPermissionRecursivelly(final OrgPermission oa)
		{
			if (hasPermission(oa))
			{
				return this;
			}
			addPermission(oa, CollisionPolicy.Override);

			// Do we look for trees?
			final int adTreeOrgId = getOrg_Tree_ID();
			if (adTreeOrgId <= 0)
			{
				return this;
			}

			final OrgResource orgResource = oa.getResource();
			if (!orgResource.isSummaryOrganization())
			{
				return this;
			}

			// Summary Org - Get Dependents
			final MTree_Base tree = MTree_Base.get(Env.getCtx(), adTreeOrgId, ITrx.TRXNAME_None);
			final String sql = "SELECT AD_Client_ID, AD_Org_ID FROM AD_Org "
					+ "WHERE IsActive='Y' AND AD_Org_ID IN (SELECT Node_ID FROM " + tree.getNodeTableName()
					+ " WHERE AD_Tree_ID=? AND Parent_ID=? AND IsActive='Y')";
			final Object[] sqlParams = new Object[] { tree.getAD_Tree_ID(), orgResource.getAD_Org_ID() };
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				DB.setParameters(pstmt, sqlParams);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					final int AD_Client_ID = rs.getInt(1);
					final int AD_Org_ID = rs.getInt(2);
					final OrgResource resource = OrgResource.of(AD_Client_ID, AD_Org_ID);
					final OrgPermission childOrgPermission = oa.copyWithResource(resource);
					addPermissionRecursivelly(childOrgPermission);
				}
			}
			catch (final SQLException e)
			{
				throw new DBException(e, sql, sqlParams);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

			return this;
		}	// loadOrgAccessAdd

	}
}
