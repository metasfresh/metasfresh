/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 www.metas.de                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_M_RMA;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.POInfo;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/**
 * Generic provider of zoom targets. Contains pieces of {@link org.compiere.apps.AZoomAcross}
 * methods <code>getZoomTargets</code> and <code>addTarget</code>
 *
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 *
 */
public class GenericZoomProvider implements IZoomProvider
{
	public static final GenericZoomProvider instance = new GenericZoomProvider();
	
	private static final Logger logger = LogManager.getLogger(GenericZoomProvider.class);

	private final CCache<String, List<GenericZoomInfoDescriptor>> keyColumnName2descriptors = CCache.newLRUCache(I_AD_Window.Table_Name + "#GenericZoomInfoDescriptors", 100, 0);

	private GenericZoomProvider()
	{
		super();
	}

	@Override
	public List<ZoomInfo> retrieveZoomInfos(final IZoomSource source, final int targetAD_Window_ID, final boolean checkRecordsCount)
	{
		final List<GenericZoomInfoDescriptor> zoomInfoDescriptors = getZoomInfoDescriptors(source.getKeyColumnName());
		if (zoomInfoDescriptors.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<ZoomInfo> result = ImmutableList.builder();
		for (final GenericZoomInfoDescriptor zoomInfoDescriptor : zoomInfoDescriptors)
		{
			final int AD_Window_ID = zoomInfoDescriptor.getTargetAD_Window_ID();
			if(targetAD_Window_ID > 0 && targetAD_Window_ID != AD_Window_ID)
			{
				continue;
			}
			
			final String name = zoomInfoDescriptor.getName();
			final MQuery query = buildMQuery(zoomInfoDescriptor, source);
			if(query == null)
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
		if(sourceKeyColumnName == null)
		{
			return ImmutableList.of();
		}
		return keyColumnName2descriptors.getOrLoad(sourceKeyColumnName, () -> retrieveZoomInfoDescriptors(sourceKeyColumnName));
	}

	private List<GenericZoomInfoDescriptor> retrieveZoomInfoDescriptors(final String sourceKeyColumnName)
	{
		Check.assumeNotEmpty(sourceKeyColumnName, "sourceKeyColumnName is not empty");

		final List<Object> sqlParams = new ArrayList<>();
		String sql = "SELECT DISTINCT ws.AD_Window_ID,ws.Name, wp.AD_Window_ID,wp.Name, t.TableName "
				+ "\nFROM AD_Table t ";
		final boolean baseLanguage = Env.isBaseLanguage(Env.getCtx(), "AD_Window");
		if (baseLanguage)
		{
			sql += "\n INNER JOIN AD_Window ws ON (t.AD_Window_ID=ws.AD_Window_ID)"
					+ "\n LEFT OUTER JOIN AD_Window wp ON (t.PO_Window_ID=wp.AD_Window_ID) ";
		}
		else
		{
			sql += "\n INNER JOIN AD_Window_Trl ws ON (t.AD_Window_ID=ws.AD_Window_ID AND ws.AD_Language=?)"
					+ "\n LEFT OUTER JOIN AD_Window_Trl wp ON (t.PO_Window_ID=wp.AD_Window_ID AND wp.AD_Language=?) ";

			final String adLanguage = Env.getAD_Language(Env.getCtx()); // FIXME: this will not work on server side/webui -> get rid of Language checking
			sqlParams.add(adLanguage);
			sqlParams.add(adLanguage);
		}
		//
		//@formatter:off
		sql += "WHERE t.TableName NOT LIKE 'I%'" // No Import
				//
				// Consider first window tab or any tab if our column has AllowZoomTo set
				+ " AND EXISTS ("
					+ "SELECT 1 FROM AD_Tab tt "
						+ "WHERE (tt.AD_Window_ID=ws.AD_Window_ID OR tt.AD_Window_ID=wp.AD_Window_ID)"
						+ " AND tt.AD_Table_ID=t.AD_Table_ID"
						+ " AND ("
							// First Tab
							+ " tt.SeqNo=10"
							// Or tab contains our column and AllowZoomTo=Y
							+ " OR EXISTS (SELECT 1 FROM AD_Column c where c.AD_Table_ID=t.AD_Table_ID AND ColumnName=? AND "+I_AD_Column.COLUMNNAME_AllowZoomTo+"='Y')" // #1
						+ ")"
				+ ")"
				//
				// Consider tables which have a reference to our column
				+ " AND (" // metas
					+ " t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName=? AND IsKey='N' AND IsParent='N') " // #2
					// metas: begin: support for "Zoomable Record_IDs" (03921)
					+ " OR ("
						+ " t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName='AD_Table_ID' AND IsKey='N' AND IsParent='N')"
						+ " AND t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName='Record_ID' AND IsKey='N' AND IsParent='N' AND "+I_AD_Column.COLUMNNAME_AllowZoomTo+"='Y')"
					+ ") "
				+ ") "
				// metas: end: support for "Zoomable Record_IDs" (03921)
				+ "ORDER BY 2";
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

			final ImmutableList.Builder<GenericZoomInfoDescriptor> result = ImmutableList.builder();
			while (rs.next())
			{
				final int AD_Window_ID = rs.getInt(1);
				final String name = rs.getString(2);
				final int PO_Window_ID = rs.getInt(3);
				final String poName = rs.getString(4);
				final String targetTableName = rs.getString(5);
				
				final POInfo targetTableInfo = POInfo.getPOInfo(targetTableName);
				if(targetTableInfo == null)
				{
					logger.warn("No POInfo found for {}. Skip it.", targetTableName);
					continue;
				}

				final GenericZoomProvider.GenericZoomInfoDescriptor.Builder zoomInfoDescriptorBuilder = GenericZoomInfoDescriptor.builder()
						.setTargetTableName(targetTableName)
						.setTargetHasIsSOTrxColumn(targetTableInfo.hasColumnName("IsSOTrx"));

				final String targetColumnName = sourceKeyColumnName;
				final boolean hasTargetColumnName = targetTableInfo.hasColumnName(targetColumnName);
				
				// Dynamic references AD_Table_ID/Record_ID (task #03921)
				if (!hasTargetColumnName
						&& targetTableInfo.hasColumnName("AD_Table_ID")
						&& targetTableInfo.hasColumnName("Record_ID"))
				{
					zoomInfoDescriptorBuilder.setDynamicTargetColumnName(true);
				}
				// No target column
				else if (!hasTargetColumnName)
				{
					logger.warn("Target column name {} not found in table {}", targetColumnName, targetTableName);
					continue;
				}
				// Regular target column
				else
				{
					zoomInfoDescriptorBuilder.setDynamicTargetColumnName(false);
					zoomInfoDescriptorBuilder.setTargetColumnName(targetColumnName);
					if(targetTableInfo.isVirtualColumn(targetColumnName))
					{
						zoomInfoDescriptorBuilder.setVirtualTargetColumnSql(targetTableInfo.getColumnSql(targetColumnName));
					}
				}

				if(PO_Window_ID <= 0)
				{
					result.add(zoomInfoDescriptorBuilder
							.setName(name)
							.setTargetAD_Window_ID(AD_Window_ID)
							.setIsSOTrx(null)  // applies for SO and PO
							.build());
				}
				else
				{
					result.add(zoomInfoDescriptorBuilder
							.setName(name)
							.setTargetAD_Window_ID(AD_Window_ID)
							.setIsSOTrx(Boolean.TRUE)
							.build());
					result.add(zoomInfoDescriptorBuilder
							.setName(poName)
							.setTargetAD_Window_ID(PO_Window_ID)
							.setIsSOTrx(Boolean.FALSE)
							.build());
				}
			}
			
			return result.build();
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
		query.setZoomColumnName(source.getKeyColumnName());
		query.setZoomValue(source.getRecord_ID());

		return query;
	}
	
	private void updateRecordCount(final MQuery query, final GenericZoomInfoDescriptor zoomInfoDescriptor, final String sourceTableName)
	{
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
		query.setRecordCount(count);
	}

	private static final class GenericZoomInfoDescriptor
	{
		public static final Builder builder()
		{
			return new Builder();
		}
		
		private final String name;
		private final int targetAD_Window_ID;
				
		private final String targetTableName;
		private final String targetColumnName;
		private final boolean dynamicTargetColumnName;
		private final String virtualTargetColumnSql;
		
		private final Boolean isSOTrx;
		private final boolean targetHasIsSOTrxColumn;

		private GenericZoomInfoDescriptor(final Builder builder)
		{
			super();
			this.name = builder.name;
			Check.assumeNotEmpty(name, "name is not empty");
			
			this.targetTableName = builder.targetTableName;
			Check.assumeNotEmpty(targetTableName, "targetTableName is not empty");
			
			this.targetColumnName = builder.targetColumnName;
			this.virtualTargetColumnSql = builder.virtualTargetColumnSql;
			this.dynamicTargetColumnName  = builder.dynamicTargetColumnName;
			if(!dynamicTargetColumnName && Check.isEmpty(targetColumnName, true))
			{
				throw new IllegalArgumentException("targetColumnName must be set when it's not dynamic");
			}
			
			this.targetAD_Window_ID = builder.targetAD_Window_ID;
			Check.assume(targetAD_Window_ID > 0, "AD_Window_ID > 0");
			
			this.isSOTrx = builder.isSOTrx; // null is also accepted
			this.targetHasIsSOTrxColumn = builder.targetHasIsSOTrxColumn;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("name", name)
					.add("targetTableName", targetTableName)
					.add("targetAD_Window_ID", targetAD_Window_ID)
					.add("IsSOTrx", isSOTrx)
					.toString();
		}

		public String getName()
		{
			return name;
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
		
		public static final class Builder
		{
			private String name;
			
			private String targetTableName;
			private String targetColumnName;
			private boolean dynamicTargetColumnName;
			private String virtualTargetColumnSql;
			private int targetAD_Window_ID;
			private Boolean isSOTrx;
			private Boolean targetHasIsSOTrxColumn;
			
			private Builder()
			{
				super();
			}
			
			public GenericZoomProvider.GenericZoomInfoDescriptor build()
			{
				return new GenericZoomInfoDescriptor(this);
			}

			public Builder setName(String name)
			{
				this.name = name;
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

			public Builder setTargetAD_Window_ID(int aD_Window_ID)
			{
				targetAD_Window_ID = aD_Window_ID;
				return this;
			}

			public Builder setIsSOTrx(Boolean isSOTrx)
			{
				this.isSOTrx = isSOTrx;
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
