package org.adempiere.ad.session.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.session.ChangeLogRecord;
import org.adempiere.ad.session.ISessionDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.model.I_AD_Table;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class SessionDAO implements ISessionDAO
{
	private static final Logger logger = LogManager.getLogger(SessionDAO.class);

	private static final String SQL_INSERT_CHANGELOG = "INSERT INTO " + I_AD_ChangeLog.Table_Name + "("
	//
			+ I_AD_ChangeLog.COLUMNNAME_AD_Table_ID
			+ "," + I_AD_ChangeLog.COLUMNNAME_AD_Column_ID
			+ "," + I_AD_ChangeLog.COLUMNNAME_Record_ID
			//
			+ "," + I_AD_ChangeLog.COLUMNNAME_AD_Session_ID
			+ "," + I_AD_ChangeLog.COLUMNNAME_AD_PInstance_ID
			+ "," + I_AD_ChangeLog.COLUMNNAME_EventChangeLog
			+ "," + I_AD_ChangeLog.COLUMNNAME_OldValue
			+ "," + I_AD_ChangeLog.COLUMNNAME_NewValue
			+ "," + I_AD_ChangeLog.COLUMNNAME_TrxName
			//
			+ "," + I_AD_ChangeLog.COLUMNNAME_AD_Client_ID
			+ "," + I_AD_ChangeLog.COLUMNNAME_AD_Org_ID
			+ "," + I_AD_ChangeLog.COLUMNNAME_IsActive
			+ "," + I_AD_ChangeLog.COLUMNNAME_Created
			+ "," + I_AD_ChangeLog.COLUMNNAME_CreatedBy
			+ "," + I_AD_ChangeLog.COLUMNNAME_Updated
			+ "," + I_AD_ChangeLog.COLUMNNAME_UpdatedBy
			+ ") VALUES ("
			+ "?" // AD_Table_ID
			+ ", ?" // AD_Column_ID
			+ ", ?" // Record_ID
	//
			+ ", ?" // AD_Session_ID
			+ ", ?" // AD_PInstance_ID FRESH-314
			+ ", ?" // EventChangeLog
			+ ", ?" // OldValue
			+ ", ?" // NewValue
			+ ", ?" // TrxName
	//
			+ ", ?" // AD_Client_ID
			+ ", ?" // AD_Org_ID
			+ ", 'Y'" // IsActive
			+ ", now()" // Created
			+ ", ?" // CreatedBy
			+ ", now()" // Updated
			+ ", ?" // UpdatedBy
			+ ")";

	@Override
	public void saveChangeLogs(final Collection<ChangeLogRecord> records)
	{
		if (records == null || records.isEmpty())
		{
			return;
		}

		PreparedStatement pstmt = null;
		try
		{
			int countCreated = 0;
			for (final ChangeLogRecord record : records)
			{
				if (record == null)
				{
					continue;
				}

				// Null handling
				final Object oldValue = record.getOldValue();
				final Object newValue = record.getNewValue();
				if (oldValue == null && newValue == null)
				{
					continue;
				}
				// Equal Value
				if (oldValue != null && newValue != null && oldValue.equals(newValue))
				{
					continue;
				}

				//
				final int AD_Table_ID = record.getAD_Table_ID();
				if (!isLogged(AD_Table_ID))
				{
					continue;
				}

				if (pstmt == null)
				{
					pstmt = DB.prepareStatement(SQL_INSERT_CHANGELOG, ITrx.TRXNAME_ThreadInherited);
				}

				DB.setParameters(pstmt, new Object[] {
						AD_Table_ID //
						, record.getAD_Column_ID() //
						, record.getRecord_ID() //
						//
						, record.getAD_Session_ID() <= 0 ? null : record.getAD_Session_ID() // FRESH-314
						, record.getAD_PInstance_ID() // FRESH-314
						, record.getEventType() // EventChangeLog (type)
						, oldValue == null ? CHANGELOG_NullValue : oldValue.toString() //
						, newValue == null ? CHANGELOG_NullValue : newValue.toString() //
						, record.getTrxName() //
						//
						, record.getAD_Client_ID() //
						, record.getAD_Org_ID() //
						, record.getAD_User_ID() // CreatedBy
						, record.getAD_User_ID() // UpdatedBy
				});
				pstmt.addBatch();
				countCreated++;
			}

			if (pstmt != null)
			{
				pstmt.executeBatch();
				logger.trace("Created {} records", countCreated);
			}
		}
		catch (final Exception e)
		{
			logger.error("Failed creating change log", e);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	/**
	 * Do we track changes for this table
	 *
	 * @param AD_Table_ID table
	 * @return true if changes are tracked
	 */
	public boolean isLogged(final int AD_Table_ID)
	{
		final List<Integer> changeLogAllowedTableIds = retrieveChangeLogAllowedTableIds();
		return Collections.binarySearch(changeLogAllowedTableIds, AD_Table_ID) >= 0;
	}	// trackChanges

	/**
	 * Fill Log with tables to be logged
	 */
	@Cached(cacheName = I_AD_Table.Table_Name + "#IsChangeLog")
	public List<Integer> retrieveChangeLogAllowedTableIds()
	{
		final ImmutableList.Builder<Integer> list = ImmutableList.builder();
		final String sql = "SELECT t.AD_Table_ID FROM AD_Table t "
				+ "WHERE t.IsChangeLog='Y'"					// also inactive
				+ " OR EXISTS (SELECT * FROM AD_Column c "
				+ "WHERE t.AD_Table_ID=c.AD_Table_ID AND c.ColumnName='EntityType') "
				+ "ORDER BY t.AD_Table_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(rs.getInt(1));
			}
		}
		catch (final Exception e)
		{
			logger.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return list.build();
	}
}
