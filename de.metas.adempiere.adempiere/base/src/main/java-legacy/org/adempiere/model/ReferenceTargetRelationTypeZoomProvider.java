package org.adempiere.model;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.model.ZoomInfoFactory.POZoomSource;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.model.Query;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Implementation for relation types that only have target table reference.
 * The table reference of suck relation types must contain the columns "AD_Table_ID" and "Record_ID" which will link with the table_ID and key column of the source.
 * The source is considered the entry (window and table considered) that is currently in the context ( opened in the user interface)
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ReferenceTargetRelationTypeZoomProvider extends AbstractRelationTypeZoomProvider
{

	private ZoomProviderDestination target;

	private ReferenceTargetRelationTypeZoomProvider(final Builder builder)
	{
		super();

		directed = builder.isDirected();
		zoomInfoId = builder.getZoomInfoId();
		internalName = builder.getInternalName();
		adRelationTypeId = builder.getAD_RelationType_ID();
		target = new ZoomProviderDestination(builder.getTarget_Reference_ID(), builder.getTargetTableRefInfoOrNull(), builder.getTargetRoleDisplayName());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("zoomInfoId", zoomInfoId)
				.add("internalName", internalName)
				.add("directed", directed)
				.toString();
	}

	/**
	 * @return a query which will find all zoomSource references in given target
	 */
	private MQuery mkQuery(final IZoomSource zoomSource, final ZoomProviderDestination target)
	{
		final StringBuilder queryWhereClause = new StringBuilder();
		final ITableRefInfo refTable = target.getTableRefInfo();

		queryWhereClause
				.append(zoomSource.getAD_Table_ID())
				.append(" = ")
				.append(refTable.getTableName())
				.append(".")
				.append("AD_Table_ID")
				.append(" AND ")
				.append(zoomSource.getRecord_ID())
				.append(" = ")
				.append(refTable.getTableName())
				.append(".")
				.append("Record_ID");

		final MQuery query = new MQuery();
		query.addRestriction(queryWhereClause.toString());
		query.setZoomTableName(target.getTableName());
		query.setZoomColumnName(target.getKeyColumnName());

		return query;
	}

	@Override
	public List<ZoomInfo> retrieveZoomInfos(IZoomSource zoomSource, int targetAD_Window_ID, boolean checkRecordsCount)
	{
		final ZoomProviderDestination target = getTarget();

		final int adWindowId = target.getAD_Window_ID(zoomSource.isSOTrx());

		if (targetAD_Window_ID > 0 && targetAD_Window_ID != adWindowId)
		{
			return ImmutableList.of();
		}

		final MQuery query = mkQuery(zoomSource, target);
		if (checkRecordsCount)
		{
			updateRecordsCountAndZoomValue(query);
		}

		final ITranslatableString display = target.getRoleDisplayName(adWindowId);

		final String zoomInfoId = getZoomInfoId();
		return ImmutableList.of(ZoomInfo.of(zoomInfoId, adWindowId, query, display));
	}

	@Override
	protected ZoomProviderDestination getTarget()
	{
		return target;
	}

	@Override
	protected String getTargetTableName()
	{
		return target.getTableName();
	}

	@Override
	public <T> List<T> retrieveDestinations(Properties ctx, PO sourcePO, Class<T> clazz, String trxName)
	{
		final IZoomSource zoomSource = POZoomSource.of(sourcePO, -1);

		final MQuery query = mkQuery(zoomSource, getTarget());

		return new Query(ctx, query.getZoomTableName(), query.getWhereClause(false), trxName)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(query.getZoomColumnName())
				.list(clazz);
	}

	@ToString(exclude = "lookupDAO")
	public static final class Builder
	{
		private final transient ILookupDAO lookupDAO = Services.get(ILookupDAO.class);

		private Boolean directed;
		private String internalName;
		private int adRelationTypeId;
		private boolean isReferenceTarget;

		private int sourceReferenceId = -1;
		private ITranslatableString sourceRoleDisplayName;
		private ITableRefInfo sourceTableRefInfo = null; // lazy

		private int targetReferenceId = -1;
		private ITranslatableString targetRoleDisplayName;
		private ITableRefInfo targetTableRefInfo = null; // lazy

		protected Builder()
		{
			super();
		}

		public ReferenceTargetRelationTypeZoomProvider buildOrNull()
		{

			if (getTargetTableRefInfoOrNull() == null)
			{
				logger.info("Skip building {} because target tableRefInfo is null", this);
				return null;
			}

			return new ReferenceTargetRelationTypeZoomProvider(this);
		}

		public Builder setAD_RelationType_ID(final int adRelationTypeId)
		{
			this.adRelationTypeId = adRelationTypeId;
			return this;
		}

		private int getAD_RelationType_ID()
		{
			Check.assume(adRelationTypeId > 0, "adRelationTypeId > 0");
			return adRelationTypeId;
		}

		private String getZoomInfoId()
		{
			return "relationType-" + getAD_RelationType_ID();
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		private String getInternalName()
		{
			if (Check.isEmpty(internalName, true))
			{
				return getZoomInfoId();
			}
			return internalName;
		}

		public Builder setDirected(final boolean directed)
		{
			this.directed = directed;
			return this;
		}

		private boolean isDirected()
		{
			Check.assumeNotNull(directed, "Parameter directed is not null");
			return directed;
		}

		public Builder setTarget_Reference_AD(final int targetReferenceId)
		{
			this.targetReferenceId = targetReferenceId;
			targetTableRefInfo = null; // lazy
			return this;
		}

		private int getTarget_Reference_ID()
		{
			Check.assume(targetReferenceId > 0, "targetReferenceId > 0");
			return targetReferenceId;
		}

		private ITableRefInfo getTargetTableRefInfoOrNull()
		{
			if (targetTableRefInfo == null)
			{
				targetTableRefInfo = lookupDAO.retrieveTableRefInfo(getTarget_Reference_ID());
			}
			return targetTableRefInfo;
		}

		public Builder setTargetRoleDisplayName(final ITranslatableString targetRoleDisplayName)
		{
			this.targetRoleDisplayName = targetRoleDisplayName;
			return this;
		}

		public ITranslatableString getTargetRoleDisplayName()
		{
			return targetRoleDisplayName;
		}

		public Builder setIsReferenceTarget(boolean isReferenceTarget)
		{
			this.isReferenceTarget = isReferenceTarget;
			return this;
		}

		private boolean isReferenceTarget()
		{
			return isReferenceTarget;
		}
	}

}
