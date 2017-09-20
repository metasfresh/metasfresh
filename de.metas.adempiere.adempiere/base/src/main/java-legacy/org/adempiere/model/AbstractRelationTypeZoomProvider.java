package org.adempiere.model;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.PORelationException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import lombok.NonNull;

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

public abstract class AbstractRelationTypeZoomProvider implements IZoomProvider
{
	protected boolean directed;
	protected String zoomInfoId;
	protected String internalName;
	protected int adRelationTypeId;
	protected static final Logger logger = LogManager.getLogger(RelationTypeZoomProvider.class);

	/**
	 * Each zoom provider must have a target reference
	 */
	protected static ZoomProviderDestination target;

	public boolean isDirected()
	{
		return directed;
	}

	protected String getZoomInfoId()
	{
		return zoomInfoId;
	}

	public String getInternalName()
	{
		return internalName;
	}

	public int getAD_RelationType_ID()
	{
		return adRelationTypeId;
	}

	protected static void updateRecordsCountAndZoomValue(final MQuery query)
	{
		final String sqlCommon = " FROM " + query.getZoomTableName() + " WHERE " + query.getWhereClause(false);

		final String sqlCount = "SELECT COUNT(*) " + sqlCommon;
		final int count = DB.getSQLValueEx(ITrx.TRXNAME_None, sqlCount);
		query.setRecordCount(count);

		if (count > 0)
		{
			final String sqlFirstKey = "SELECT " + query.getZoomColumnName() + sqlCommon;

			final int firstKey = DB.getSQLValueEx(ITrx.TRXNAME_None, sqlFirstKey);
			query.setZoomValue(firstKey);
		}
	}

	public ZoomProviderDestination getTarget()
	{
		return target;
	}


	public abstract <T> List<T> retrieveDestinations( final PO sourcePO, final Class<T> clazz, final String trxName);

	protected static class ZoomProviderDestination
	{
		private final int AD_Reference_ID;
		private final ITableRefInfo tableRefInfo;
		private final ITranslatableString roleDisplayName;

		protected ZoomProviderDestination(final int AD_Reference_ID, @NonNull final ITableRefInfo tableRefInfo, @Nullable final ITranslatableString roleDisplayName)
		{
			super();
			Preconditions.checkArgument(AD_Reference_ID > 0, "AD_Reference_ID > 0");
			this.AD_Reference_ID = AD_Reference_ID;
			this.tableRefInfo = tableRefInfo;
			this.roleDisplayName = roleDisplayName;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("AD_Reference_ID", AD_Reference_ID)
					.add("roleDisplayName", roleDisplayName)
					.add("tableRefInfo", tableRefInfo)
					.toString();
		}

		public int getAD_Reference_ID()
		{
			return AD_Reference_ID;
		}

		public String getTableName()
		{
			return tableRefInfo.getTableName();
		}

		public String getKeyColumnName()
		{
			return tableRefInfo.getKeyColumn();
		}

		public ITableRefInfo getTableRefInfo()
		{
			return tableRefInfo;
		}

		public ITranslatableString getRoleDisplayName(final int fallbackAD_Window_ID)
		{
			if (roleDisplayName != null)
			{
				return roleDisplayName;
			}

			// Fallback to window name
			final ITranslatableString windowName = Services.get(IADWindowDAO.class).retrieveWindowName(fallbackAD_Window_ID);
			Check.errorIf(windowName == null, "Found no display string for, destination={}, AD_Window_ID={}", this, fallbackAD_Window_ID);
			return windowName;
		}

		/**
		 * @return the <code>AD_Window_ID</code>
		 * @throws PORelationException if no <code>AD_Window_ID</code> can be found.
		 */
		public int getAD_Window_ID(final boolean isSOTrx)
		{
			int windowId = tableRefInfo.getZoomAD_Window_ID_Override();
			if (windowId > 0)
			{
				return windowId;
			}

			if (isSOTrx)
			{
				windowId = tableRefInfo.getZoomSO_Window_ID();
			}
			else
			{
				windowId = tableRefInfo.getZoomPO_Window_ID();
			}
			if (windowId <= 0)
			{
				throw PORelationException.throwMissingWindowId(tableRefInfo.getName(), tableRefInfo.getTableName(), isSOTrx);
			}

			return windowId;
		}

	}

}
