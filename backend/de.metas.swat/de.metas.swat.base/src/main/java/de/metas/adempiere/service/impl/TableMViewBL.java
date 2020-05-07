/**
 * 
 */
package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.POResultSet;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import de.metas.adempiere.engine.MViewMetadata;
import de.metas.adempiere.model.I_AD_Table_MView;
import de.metas.adempiere.service.ITableMViewBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * @author tsa
 * 
 */
public class TableMViewBL implements ITableMViewBL
{
	private final Logger log = LogManager.getLogger(getClass());

	private static class MViewPartition
	{
		public static final String TABLENAME_Source = new String();

		private final String sourceTableName;
		private final String sourceWhere;

		public MViewPartition(String sourceTableName, String sourceWhere)
		{
			this.sourceTableName = sourceTableName;
			this.sourceWhere = sourceWhere;
		}

		@SuppressWarnings("unused")
		public String getSourceTableName()
		{
			return sourceTableName;
		}

		@SuppressWarnings("unused")
		public String getSourceWhere()
		{
			return sourceWhere;
		}

		public String getWhereClause(String sourceTableName, String targetTableName)
		{
			if (sourceTableName == null)
				throw new IllegalArgumentException("sourceTableName is null");
			if (targetTableName == null)
				throw new IllegalArgumentException("targetTableName is null");

			if (sourceTableName == TABLENAME_Source)
				sourceTableName = this.sourceTableName;

			String where = sourceWhere
					.replace("@source@", sourceTableName)
					.replace("@target@", targetTableName);
			return "EXISTS(SELECT 1 FROM " + sourceTableName + " WHERE " + where + ")";
		}

		@Override
		public String toString()
		{
			return "MViewSeqment [sourceTableName=" + sourceTableName + ", sourceWhere=" + sourceWhere + "]";
		}
	};

	public TableMViewBL()
	{

	}

	@Cached(cacheName = I_AD_Table_MView.Table_Name)
	@Override
	public I_AD_Table_MView fetchForTable(
			@CacheCtx Properties ctx,
			int AD_Table_ID,
			@CacheTrx String trxName)
	{
		final String whereClause = I_AD_Table_MView.COLUMNNAME_AD_Table_ID + "=?";
		return new Query(ctx, I_AD_Table_MView.Table_Name, whereClause, trxName)
				.setParameters(AD_Table_ID)
				.firstOnly(I_AD_Table_MView.class);
	}

	@Override
	public I_AD_Table_MView fetchForTableName(
			Properties ctx,
			String tableName,
			String trxName)
	{
		final int AD_Table_ID = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		return fetchForTable(ctx, AD_Table_ID, trxName);
	}

	@Override
	public List<I_AD_Table_MView> fetchAll(Properties ctx)
	{
		return new Query(ctx, I_AD_Table_MView.Table_Name, null, null)
				.list(I_AD_Table_MView.class);
	}

	@Override
	public List<I_AD_Table_MView> fetchForSourceTableName(Properties ctx, String sourceTableName)
	{
		List<I_AD_Table_MView> list = new ArrayList<I_AD_Table_MView>(fetchAll(ctx));
		Iterator<I_AD_Table_MView> it = list.iterator();
		while (it.hasNext())
		{
			I_AD_Table_MView mview = it.next();
			MViewMetadata mdata = getMetadata(mview);
			if (mdata == null)
			{
				it.remove();
				continue;
			}
			if (!mdata.getSourceTables().contains(sourceTableName))
			{
				it.remove();
				continue;
			}
		}
		return list;
	}

	private final Map<Integer, MViewMetadata> metadata = new HashMap<Integer, MViewMetadata>();

	@Override
	public MViewMetadata getMetadata(I_AD_Table_MView mview)
	{
		return metadata.get(mview.getAD_Table_ID());
	}

	@Override
	public void setMetadata(MViewMetadata mdata)
	{
		I_AD_Table_MView mview = fetchForTableName(Env.getCtx(), mdata.getTargetTableName(), null);
		metadata.put(mview.getAD_Table_ID(), mdata);
		if (mview.isValid())
		{
			mview.setIsValid(false);
			InterfaceWrapperHelper.save(mview);
		}
		mview.setIsValid(true);
		InterfaceWrapperHelper.save(mview);
	}

	private void updateComplete(I_AD_Table_MView mview)
	{
		updatePartial(mview, (PO)null, InterfaceWrapperHelper.getTrxName(mview));
	}

	private void updatePartial(final I_AD_Table_MView mview, final PO sourcePO, final String trxName)
	{
		updatePartial(mview, Arrays.asList(sourcePO), trxName);
	}

	private void updatePartial(final I_AD_Table_MView mview, final List<PO> sourcePOs, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(mview);

		if (LogManager.isLevelFine())
			log.debug("Starting: " + getSummary(mview) + ", sources=" + sourcePOs + ", trxName=" + trxName);

		final MViewMetadata mdata = getMetadata(mview);
		if (mdata == null)
		{
			log.warn("No metadata found for " + getSummary(mview) + " [STOP]");
			mview.setIsValid(false);
			InterfaceWrapperHelper.save(mview);
			return;
		}

		//
		// Check if we can do a partial update
		// final String sourceTableName;
		// final String sourceWhere;
		List<MViewPartition> partitions = createMViewSegments(mdata, sourcePOs);
		if (partitions.isEmpty())
		{
			log.debug("UpdateMode=complete");
		}
		else
		{
			log.debug("UpdateMode=partial - " + partitions);
		}

		//
		// Build JOIN WhereClause between mview table and source view/sql
		final String targetTableName = mdata.getTargetTableName();
		final StringBuffer sqlMviewWhereJoin = new StringBuffer();
		final StringBuffer sqlMviewWhere = new StringBuffer();
		for (String keyColumn : mdata.getTargetKeyColumns())
		{
			if (sqlMviewWhereJoin.length() > 0)
			{
				sqlMviewWhereJoin.append(" AND ");
				sqlMviewWhere.append(" AND ");
			}
			sqlMviewWhereJoin.append(targetTableName).append(".").append(keyColumn)
					.append("=")
					.append("target.").append(keyColumn);
			sqlMviewWhere.append(targetTableName).append(".").append(keyColumn)
					.append("=?");
		}

		int stat_insertsNo = 0;
		int stat_updatesNo = 0;
		int stat_deletesNo = 0;

		//
		// Process INSERTs and UPDATEs
		{
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			final StringBuffer sql = new StringBuffer("SELECT target.*"
					+ ", (SELECT COUNT(*) FROM " + targetTableName + " WHERE " + sqlMviewWhereJoin + ") AS zz_mview_cnt"
					+ " FROM (" + mdata.getSql() + ") target"
					+ " WHERE 1=1");
			// Partial update:
			if (!partitions.isEmpty())
			{
				String where = buildWhereClause(partitions, MViewPartition.TABLENAME_Source, "target");
				if (!Check.isEmpty(where, true))
					sql.append(" AND (").append(where).append(")");
			}
			if (LogManager.isLevelFine())
				log.debug("Insert/Update SQL: " + sql);

			try
			{
				pstmt = DB.prepareStatement(sql.toString(), trxName);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					final PO po = TableModelLoader.instance.getPO(ctx, targetTableName, rs, trxName);
					int zz_mview_cnt = rs.getInt("zz_mview_cnt");
					if (LogManager.isLevelFine())
						log.debug("online PO=" + po + ", zz_mview_cnt=" + zz_mview_cnt);

					// new
					if (zz_mview_cnt == 0)
					{
						PO targetPO = TableModelLoader.instance.newPO(ctx, targetTableName, trxName);
						copyValues(po, targetPO);
						targetPO.saveEx();

						if (LogManager.isLevelFine())
							log.debug("saved new PO: " + targetPO);
						stat_insertsNo++;
					}
					// update
					else if (zz_mview_cnt == 1)
					{
						final Query query = new Query(ctx, targetTableName, sqlMviewWhere.toString(), trxName)
								.setParameters(getKeys(mdata, po));
						final PO targetPO = query.firstOnly();
						if (targetPO == null)
						{
							throw new AdempiereException("Target po not found for update on " + getSummary(mview) + ", " + query);
						}
						if (LogManager.isLevelFine())
							log.debug("updating PO: " + targetPO);
						copyValues(po, targetPO);
						targetPO.saveEx();

						if (LogManager.isLevelFine())
							log.debug("updated PO: " + targetPO);
						stat_updatesNo++;
					}
					else
					// zz_mview_cnt > 1
					{
						throw new AdempiereException("Error updating " + po + ". More then one records found in target table");
					}
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e, sql.toString());
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}

		//
		// Process DELETEs
		{
			POResultSet<PO> rs = null;
			final StringBuffer whereClause = new StringBuffer("NOT EXISTS ("
					+ " SELECT 1 FROM (" + mdata.getSql() + ") target"
					+ " WHERE " + sqlMviewWhereJoin
					+ ")");
			// Partial update
			if (!partitions.isEmpty())
			{
				String where = buildWhereClause(partitions, MViewPartition.TABLENAME_Source, targetTableName);
				if (!Check.isEmpty(where, true))
					whereClause.append(" AND (").append(where).append(")");

			}
			if (LogManager.isLevelFine())
				log.debug("Delete WHERE clause: " + whereClause);

			try
			{
				rs = new Query(ctx, targetTableName, whereClause.toString(), trxName)
						.scroll();
				while (rs.hasNext())
				{
					final PO po = rs.next();
					if (LogManager.isLevelFine())
						log.debug("deleting PO: " + po);
					po.deleteEx(true);

					stat_deletesNo++;
				}
			}
			finally
			{
				DB.close(rs);
				rs = null;
			}

		}

		//
		// Update MView info
		final ITableMViewBL mviewBL = Services.get(ITableMViewBL.class);
		final I_AD_Table_MView tableMView = mviewBL.fetchForTableName(ctx, targetTableName, trxName);
		tableMView.setIsStaled(false);
		tableMView.setStaledSinceDate(null);
		tableMView.setLastRefreshDate(SystemTime.asTimestamp());
		InterfaceWrapperHelper.save(tableMView);
		if (log.isInfoEnabled())
			log.info("" + getSummary(mview) + ": Updated INSERT/UPDATE/DELETE=" + stat_insertsNo + "/" + stat_updatesNo + "/" + stat_deletesNo);
	}

	private List<Object> getKeys(MViewMetadata mdata, PO po)
	{
		List<Object> keys = new ArrayList<Object>();
		for (String columnName : mdata.getTargetKeyColumns())
		{
			keys.add(po.get_Value(columnName));
		}
		return keys;
	}

	private void copyValues(PO sourcePO, PO targetPO)
	{
		for (int i = 0; i < sourcePO.get_ColumnCount(); i++)
		{
			String columnName = sourcePO.get_ColumnName(i);
			Object value = sourcePO.get_Value(i);
			targetPO.set_ValueNoCheck(columnName, value);
		}
	}

	public String getSummary(I_AD_Table_MView mview)
	{
		if (mview == null)
			return null;
		return "AD_Table_MView["
				+ " tableName=" + Services.get(IADTableDAO.class).retrieveTableName(mview.getAD_Table_ID())
				+ ", valid=" + mview.isValid()
				+ ", staled=" + mview.isStaled()
				+ ", staledSince=" + mview.getStaledSinceDate()
				+ ", lastRefresh=" + mview.getLastRefreshDate()
				+ "]";
	}

	private List<MViewPartition> createMViewSegments(MViewMetadata mdata, List<PO> sourcePOs)
	{
		List<MViewPartition> list = new ArrayList<MViewPartition>();
		if (sourcePOs == null || sourcePOs.isEmpty())
			return list;

		for (PO sourcePO : sourcePOs)
		{
			MViewPartition seg = createMViewSegment(mdata, sourcePO);
			if (seg != null)
				list.add(seg);
		}
		return list;
	}

	private MViewPartition createMViewSegment(MViewMetadata mdata, PO sourcePO)
	{
		final String sourceTableName;
		final String sourceWhere;
		if (sourcePO != null)
		{
			sourceTableName = sourcePO.get_TableName();
			String where = mdata.getRelationWhereClause(sourceTableName, mdata.getTargetTableName());
			if (Check.isEmpty(where, true))
			{
				sourceWhere = null;
			}
			else
			{
				sourceWhere = "(" + where + ") AND (" + sourcePO.get_WhereClause(true) + ")";
			}

			return new MViewPartition(sourceTableName, sourceWhere);
		}
		else
		{
			return null;
		}
	}

	private String buildWhereClause(List<MViewPartition> list, String sourceTableName, String targetTableName)
	{
		if (list == null || list.isEmpty())
			return null;

		StringBuffer sql = new StringBuffer();
		for (MViewPartition seg : list)
		{
			if (sql.length() > 0)
				sql.append(" OR ");
			sql.append(seg.getWhereClause(sourceTableName, targetTableName));
		}
		return sql.toString();
	}

	@Override
	public void setStaled(I_AD_Table_MView mview)
	{
		if (!mview.isStaled())
		{
			mview.setIsStaled(true);
			mview.setStaledSinceDate(SystemTime.asTimestamp());
			InterfaceWrapperHelper.save(mview);
		}
		else
		{
			if (LogManager.isLevelFine())
				log.debug("Already staled since " + mview.getStaledSinceDate());
		}
	}

	@Override
	public boolean isAllowRefresh(I_AD_Table_MView mview, PO sourcePO, RefreshMode refreshMode)
	{
		if (refreshMode == RefreshMode.Complete)
		{
			return true;
		}
		else if (refreshMode == RefreshMode.Partial)
		{
			MViewMetadata mdata = getMetadata(mview);
			if (mdata == null)
			{
				log.debug("No metadata found for " + mview + " => return false");
				return false;
			}
			String relationWhereClause = mdata.getRelationWhereClause(sourcePO.get_TableName(), mdata.getTargetTableName());
			if (Check.isEmpty(relationWhereClause, true))
			{
				return false;
			}
			else
			{
				return true;
			}

		}

		log.warn("Unknown refreshMode=" + refreshMode + " => returning false");
		return false;
	}

	@Override
	public void refreshEx(I_AD_Table_MView mview, PO sourcePO, RefreshMode refreshMode, String trxName)
	{
		if (refreshMode == RefreshMode.Complete)
		{
			updateComplete(mview);
		}
		else if (refreshMode == RefreshMode.Partial)
		{
			updatePartial(mview, sourcePO, trxName);
		}
		else
		{
			throw new AdempiereException("RefreshMode " + refreshMode + " not supported");
		}
	}

	/**
	 * Try to update the materialized view. This method is not throwing an exception in case it fails but it logs it and
	 * return false. This is useful because we don't want to condition the update of triggering PO to updating the
	 * MView. That can be solved later
	 * 
	 * @param mview
	 * @param po
	 * @param trxName
	 * @return true if updated, false if failed
	 */
	@Override
	public boolean refresh(final I_AD_Table_MView mview, final PO sourcePO, final RefreshMode refreshMode, final String trxName)
	{
		final boolean[] ok = new boolean[] { false };
		Services.get(ITrxManager.class).run(trxName, new TrxRunnable2()
		{
			@Override
			public void run(String trxName)
			{
				refreshEx(mview, sourcePO, refreshMode, trxName);
				ok[0] = true;
			}

			@Override
			public boolean doCatch(Throwable e) throws Exception
			{
				// log the error, return true to rollback the transaction but don't throw it forward
				log.error(e.getLocalizedMessage() + ", mview=" + mview + ", sourcePO=" + sourcePO + ", trxName=" + trxName, e);
				ok[0] = false;
				return true;
			}

			@Override
			public void doFinally()
			{
			}
		});

		return ok[0];
	}

	@Override
	public boolean isSourceChanged(MViewMetadata mdata, PO sourcePO, int changeType)
	{
		final String sourceTableName = sourcePO.get_TableName();
		final Set<String> sourceColumns = mdata.getSourceColumns(sourceTableName);
		if (sourceColumns == null || sourceColumns.isEmpty())
			return false;

		if (changeType == ModelValidator.TYPE_AFTER_NEW || changeType == ModelValidator.TYPE_AFTER_DELETE)
		{
			return true;
		}

		for (String sourceColumn : sourceColumns)
		{
			if (sourcePO.is_ValueChanged(sourceColumn))
			{
				return true;
			}
		}
		return false;
	}
}
