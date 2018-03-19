/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2009 www.metas.de *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.adempiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_M_RMA;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.POInfo;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Generic provider of zoom targets. Contains pieces of {@link org.compiere.apps.AZoomAcross}
 * methods <code>getZoomTargets</code> and <code>addTarget</code>
 *
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 *
 */
/* package */ class GenericZoomProvider implements IZoomProvider
{
	public static final GenericZoomProvider instance = new GenericZoomProvider();

	private static final Logger logger = LogManager.getLogger(GenericZoomProvider.class);

	private final CCache<String, List<GenericZoomInfoDescriptor>> keyColumnName2descriptors = //
			CCache.newLRUCache(I_AD_Window.Table_Name + "#GenericZoomInfoDescriptors", 100, 0);

	private GenericZoomProvider()
	{
	}

	@Override
	public List<ZoomInfo> retrieveZoomInfos(
			@NonNull final IZoomSource source,
			final int targetAD_Window_ID,
			final boolean checkRecordsCount)
	{
		final List<GenericZoomInfoDescriptor> zoomInfoDescriptors = getZoomInfoDescriptors(source.getKeyColumnNameOrNull());
		if (zoomInfoDescriptors.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<ZoomInfo> result = ImmutableList.builder();
		for (final GenericZoomInfoDescriptor zoomInfoDescriptor : zoomInfoDescriptors)
		{
			final int AD_Window_ID = zoomInfoDescriptor.getTargetAD_Window_ID();
			if (targetAD_Window_ID > 0 && targetAD_Window_ID != AD_Window_ID)
			{
				continue;
			}

			final ITranslatableString name = zoomInfoDescriptor.getName();
			final MQuery query = buildMQuery(zoomInfoDescriptor, source);
			if (query == null)
			{
				continue;
			}

			if (checkRecordsCount)
			{
				updateRecordCount(query, zoomInfoDescriptor, source.getTableName());
			}

			final String zoomInfoId = "generic-" + AD_Window_ID;
			result.add(ZoomInfoFactory.ZoomInfo.of(zoomInfoId, AD_Window_ID, query, name));
		}

		return result.build();
	}

	private List<GenericZoomInfoDescriptor> getZoomInfoDescriptors(final String sourceKeyColumnName)
	{
		if (sourceKeyColumnName == null)
		{
			return ImmutableList.of();
		}
		return keyColumnName2descriptors.getOrLoad(sourceKeyColumnName, () -> retrieveZoomInfoDescriptors(sourceKeyColumnName));
	}

	private List<GenericZoomInfoDescriptor> retrieveZoomInfoDescriptors(final String sourceKeyColumnName)
	{
		Check.assumeNotEmpty(sourceKeyColumnName, "sourceKeyColumnName is not empty");

		final List<Object> sqlParams = new ArrayList<>();
		String sql = "SELECT DISTINCT "
				+ "\n   w_so_trl.AD_Language"
				+ "\n , w_so.AD_Window_ID"
				+ "\n , w_so.Name as Name_BaseLang"
				+ "\n , w_so_trl.Name as Name"
				//
				+ "\n , w_po.AD_Window_ID as PO_Window_ID"
				+ "\n , w_po.Name as PO_Name_BaseLang"
				+ "\n , w_po_trl.Name as PO_Name"
				//
				+ "\n , t.TableName "
				+ "\n FROM AD_Table t "
				+ "\n INNER JOIN AD_Window w_so ON (t.AD_Window_ID=w_so.AD_Window_ID) AND w_so.IsActive='Y'" // gh #1489 : only consider active windows
				+ "\n LEFT OUTER JOIN AD_Window_Trl w_so_trl ON (w_so_trl.AD_Window_ID=w_so.AD_Window_ID)"
				+ "\n LEFT OUTER JOIN AD_Window w_po ON (t.PO_Window_ID=w_po.AD_Window_ID) AND w_po.IsActive='Y'" // gh #1489 : only consider active windows
				+ "\n LEFT OUTER JOIN AD_Window_Trl w_po_trl ON (w_po_trl.AD_Window_ID=w_po.AD_Window_ID AND w_po_trl.AD_Language=w_so_trl.AD_Language)";

		//@formatter:off
		sql += "WHERE t.TableName NOT LIKE 'I%'" // No Import

				+ " AND t.IsActive='Y'" // gh #1489 : only consider active tables

				// Consider first window tab or any tab if our column has AllowZoomTo set
				+ " AND EXISTS ("
					+ "SELECT 1 FROM AD_Tab tt "
						+ "WHERE (tt.AD_Window_ID=t.AD_Window_ID OR tt.AD_Window_ID=t.PO_Window_ID)"
						+ " AND tt.AD_Table_ID=t.AD_Table_ID"
						+ " AND ("
							// First Tab
							+ " tt.SeqNo=10"
							// Or tab contains our column and AllowZoomTo=Y
							+ " OR EXISTS (SELECT 1 FROM AD_Column c where c.AD_Table_ID=t.AD_Table_ID AND ColumnName=? AND "+I_AD_Column.COLUMNNAME_AllowZoomTo+"='Y')" // #1
						+ ")"
				+ ")"

				// Consider tables which have an AD_Table_ID/Record_ID reference to our column
				+ " AND ("
					+ " t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName=? AND IsKey='N') " // #2
					// metas: begin: support for "Zoomable Record_IDs" (03921)
					+ " OR ("
						+ " t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName='AD_Table_ID' AND IsKey='N')"
						+ " AND t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName='Record_ID' AND IsKey='N' AND "+I_AD_Column.COLUMNNAME_AllowZoomTo+"='Y')"
					+ ") "
				+ ") "

				//
				+ "ORDER BY 2"; // FIXME ORDER BY!
		//@formatter:on
		sqlParams.add(sourceKeyColumnName);
		sqlParams.add(sourceKeyColumnName);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final Map<ArrayKey, GenericZoomInfoDescriptor.Builder> builders = new LinkedHashMap<>();
			while (rs.next())
			{
				//
				// Get/create the zoom info descriptor builders (one for each target table and window IDs triplet)
				final String targetTableName = rs.getString("TableName");
				final int SO_Window_ID = rs.getInt("AD_Window_ID");
				final int PO_Window_ID = rs.getInt("PO_Window_ID");
				final String soNameBaseLang = rs.getString("Name_BaseLang");
				final String poNameBaseLang = rs.getString("PO_Name_BaseLang");
				final ArrayKey key = ArrayKey.of(targetTableName, SO_Window_ID, PO_Window_ID);
				final GenericZoomInfoDescriptor.Builder zoomInfoDescriptorBuilder = builders.computeIfAbsent(key, k -> {

					final POInfo targetTableInfo = POInfo.getPOInfo(targetTableName);
					if (targetTableInfo == null)
					{
						logger.warn("No POInfo found for {}. Skip it.", targetTableName);
						return null;
					}

					final GenericZoomInfoDescriptor.Builder builder = GenericZoomInfoDescriptor.builder()
							.setTargetTableName(targetTableName)
							.setTargetHasIsSOTrxColumn(targetTableInfo.hasColumnName(Env.CTXNAME_IsSOTrx))
							.setTargetNames(soNameBaseLang, poNameBaseLang)
							.setTargetWindowIds(SO_Window_ID, PO_Window_ID);

					final String targetColumnName = sourceKeyColumnName;
					final boolean hasTargetColumnName = targetTableInfo.hasColumnName(targetColumnName);

					// Dynamic references AD_Table_ID/Record_ID (task #03921)
					if (!hasTargetColumnName
							&& targetTableInfo.hasColumnName(ITableRecordReference.COLUMNNAME_AD_Table_ID)
							&& targetTableInfo.hasColumnName(ITableRecordReference.COLUMNNAME_Record_ID))
					{
						builder.setDynamicTargetColumnName(true);
					}
					// No target column
					else if (!hasTargetColumnName)
					{
						logger.warn("Target column name {} not found in table {}", targetColumnName, targetTableName);
						return null;
					}
					// Regular target column
					else
					{
						builder.setDynamicTargetColumnName(false);
						builder.setTargetColumnName(targetColumnName);
						if (targetTableInfo.isVirtualColumn(targetColumnName))
						{
							builder.setVirtualTargetColumnSql(targetTableInfo.getColumnSql(targetColumnName));
						}
					}

					return builder;
				});

				if (zoomInfoDescriptorBuilder == null)
				{
					// builder could not be created
					// we expect error to be already reported, so here we just skip it
					continue;
				}

				//
				// Add translation
				final String adLanguage = rs.getString("AD_Language");
				final String soNameTrl = rs.getString("Name");
				final String poNameTrl = rs.getString("PO_Name");
				zoomInfoDescriptorBuilder.putTargetNamesTrl(adLanguage, soNameTrl, poNameTrl);
			}

			//
			// Run each builder, get the list of descriptors and join them together in one list
			return builders.values().stream() // each builder
					.filter(builder -> builder != null) // skip null builders
					.flatMap(builder -> builder.buildAll().stream())
					.collect(ImmutableList.toImmutableList());
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static MQuery buildMQuery(final GenericZoomInfoDescriptor zoomInfoDescriptor, final IZoomSource source)
	{
		final String targetTableName = zoomInfoDescriptor.getTargetTableName();
		final String targetColumnName = zoomInfoDescriptor.getTargetColumnName();

		//
		// Zoom by dynamic references AD_Table_ID/Record_ID
		// task "Zoomable Record_IDs" (03921)
		if (zoomInfoDescriptor.isDynamicTargetColumnName())
		{
			final MQuery query = new MQuery(targetTableName);
			query.addRestriction("AD_Table_ID", Operator.EQUAL, source.getAD_Table_ID());
			query.addRestriction("Record_ID", Operator.EQUAL, source.getRecord_ID());
			query.setZoomTableName(targetTableName);
			// query.setZoomColumnName(po.get_KeyColumns()[0]);
			query.setZoomValue(source.getRecord_ID());

			return query;
		}

		//
		//
		final MQuery query = new MQuery(targetTableName);
		if (zoomInfoDescriptor.isVirtualTargetColumnName())
		{
			// TODO: find a way to specify restriction's ColumnName and ColumnSql
			final String columnSql = zoomInfoDescriptor.getVirtualTargetColumnSql();
			query.addRestriction("(" + columnSql + ") = " + source.getRecord_ID());
		}
		else
		{
			query.addRestriction(targetColumnName, Operator.EQUAL, source.getRecord_ID());
		}
		query.setZoomTableName(targetTableName);
		query.setZoomColumnName(source.getKeyColumnNameOrNull());
		query.setZoomValue(source.getRecord_ID());

		return query;
	}

	private void updateRecordCount(final MQuery query, final GenericZoomInfoDescriptor zoomInfoDescriptor, final String sourceTableName)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final String sqlCount = "SELECT COUNT(*) FROM " + query.getTableName() + " WHERE " + query.getWhereClause(false);

		Boolean isSO = zoomInfoDescriptor.getIsSOTrx();
		String sqlCountAdd = "";
		if (isSO != null && zoomInfoDescriptor.isTargetHasIsSOTrxColumn())
		{
			//
			// For RMA, Material Receipt window should be loaded for
			// IsSOTrx=true and Shipment for IsSOTrx=false
			// TODO: fetch the additional SQL from window's first tab where clause
			final int AD_Window_ID = zoomInfoDescriptor.getTargetAD_Window_ID();
			if (I_M_RMA.Table_Name.equals(sourceTableName) && (AD_Window_ID == 169 || AD_Window_ID == 184))
			{
				isSO = !isSO;
			}

			// TODO: handle the case when IsSOTrx is a virtual column

			sqlCountAdd = " AND IsSOTrx=" + DB.TO_BOOLEAN(isSO);
		}

		int count = DB.getSQLValue(ITrx.TRXNAME_None, sqlCount + sqlCountAdd);
		if (count < 0 && isSO != null)     // error try again w/o SO
		{
			count = DB.getSQLValue(ITrx.TRXNAME_None, sqlCount);
		}

		final Duration countDuration = Duration.ofNanos(stopwatch.stop().elapsed(TimeUnit.NANOSECONDS));
		query.setRecordCount(count, countDuration);

		Loggables.get().addLog("GenericZoomInfoDescriptor {} took {}", zoomInfoDescriptor, countDuration);
	}

	private static final class GenericZoomInfoDescriptor
	{
		private static final Builder builder()
		{
			return new Builder();
		}

		private final ImmutableTranslatableString nameTrl;
		private final int targetAD_Window_ID;

		private final String targetTableName;
		private final String targetColumnName;
		private final boolean dynamicTargetColumnName;
		private final String virtualTargetColumnSql;

		private final Boolean isSOTrx;
		private final boolean targetHasIsSOTrxColumn;

		private GenericZoomInfoDescriptor(final Builder builder, final ImmutableTranslatableString nameTrl, final int targetAD_Window_ID, final Boolean isSOTrx)
		{
			super();
			this.nameTrl = nameTrl;

			this.targetTableName = builder.targetTableName;
			Check.assumeNotEmpty(targetTableName, "targetTableName is not empty");

			this.targetColumnName = builder.targetColumnName;
			this.virtualTargetColumnSql = builder.virtualTargetColumnSql;
			this.dynamicTargetColumnName = builder.dynamicTargetColumnName;
			if (!dynamicTargetColumnName && Check.isEmpty(targetColumnName, true))
			{
				throw new IllegalArgumentException("targetColumnName must be set when it's not dynamic");
			}

			this.targetAD_Window_ID = targetAD_Window_ID;
			Check.assume(targetAD_Window_ID > 0, "AD_Window_ID > 0");

			this.isSOTrx = isSOTrx; // null is also accepted
			this.targetHasIsSOTrxColumn = builder.targetHasIsSOTrxColumn;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("name", nameTrl)
					.add("targetTableName", targetTableName)
					.add("targetAD_Window_ID", targetAD_Window_ID)
					.add("IsSOTrx", isSOTrx)
					.toString();
		}

		public ITranslatableString getName()
		{
			return nameTrl;
		}

		public int getTargetAD_Window_ID()
		{
			return targetAD_Window_ID;
		}

		public Boolean getIsSOTrx()
		{
			return isSOTrx;
		}

		public boolean isTargetHasIsSOTrxColumn()
		{
			return targetHasIsSOTrxColumn;
		}

		public String getTargetTableName()
		{
			return targetTableName;
		}

		public String getTargetColumnName()
		{
			return targetColumnName;
		}

		public boolean isDynamicTargetColumnName()
		{
			return dynamicTargetColumnName;
		}

		public boolean isVirtualTargetColumnName()
		{
			return virtualTargetColumnSql != null;
		}

		public String getVirtualTargetColumnSql()
		{
			return virtualTargetColumnSql;
		}

		private static final class Builder
		{
			private ImmutableTranslatableString.Builder soNameTrl = ImmutableTranslatableString.builder();
			private ImmutableTranslatableString.Builder poNameTrl = ImmutableTranslatableString.builder();

			private String targetTableName;
			private String targetColumnName;
			private boolean dynamicTargetColumnName;
			private String virtualTargetColumnSql;
			private int targetSO_Window_ID;
			private int targetPO_Window_ID;
			private Boolean targetHasIsSOTrxColumn;

			private Builder()
			{
				super();
			}

			public List<GenericZoomInfoDescriptor> buildAll()
			{
				if (targetPO_Window_ID <= 0)
				{
					final ImmutableTranslatableString soNameTrl = this.soNameTrl.build();
					final Boolean isSOTrx = null; // applies for SO and PO
					return ImmutableList.of(new GenericZoomInfoDescriptor(this, soNameTrl, targetSO_Window_ID, isSOTrx));
				}
				else
				{
					final ImmutableTranslatableString soNameTrl = this.soNameTrl.build();
					final ImmutableTranslatableString poNameTrl = this.poNameTrl.build();

					return ImmutableList.of( //
							new GenericZoomInfoDescriptor(this, soNameTrl, targetSO_Window_ID, Boolean.TRUE) //
							, new GenericZoomInfoDescriptor(this, poNameTrl, targetPO_Window_ID, Boolean.FALSE) //
					);
				}
			}

			public Builder setTargetNames(final String soName, final String poName)
			{
				if (soName != null)
				{
					soNameTrl.setDefaultValue(soName);
				}
				if (poName != null)
				{
					poNameTrl.setDefaultValue(poName);
				}
				return this;
			}

			public Builder putTargetNamesTrl(final String adLanguage, final String soName, final String poName)
			{
				if (soName != null)
				{
					soNameTrl.put(adLanguage, soName);
				}
				if (poName != null)
				{
					poNameTrl.put(adLanguage, poName);
				}
				return this;
			}

			public Builder setTargetTableName(String targetTableName)
			{
				this.targetTableName = targetTableName;
				return this;
			}

			public Builder setTargetColumnName(String targetColumnName)
			{
				this.targetColumnName = targetColumnName;
				return this;
			}

			public Builder setDynamicTargetColumnName(boolean dynamicTargetColumnName)
			{
				this.dynamicTargetColumnName = dynamicTargetColumnName;
				this.targetColumnName = null;
				return this;
			}

			public Builder setVirtualTargetColumnSql(final String virtualTargetColumnSql)
			{
				this.virtualTargetColumnSql = virtualTargetColumnSql;
				return this;
			}

			public Builder setTargetWindowIds(int soWindowId, int poWindowId)
			{
				targetSO_Window_ID = soWindowId;
				targetPO_Window_ID = poWindowId;
				return this;
			}

			public Builder setTargetHasIsSOTrxColumn(boolean targetHasIsSOTrxColumn)
			{
				this.targetHasIsSOTrxColumn = targetHasIsSOTrxColumn;
				return this;
			}
		}

	}
}
