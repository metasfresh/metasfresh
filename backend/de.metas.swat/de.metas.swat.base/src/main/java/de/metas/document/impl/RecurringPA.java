package de.metas.document.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Recurring;
import org.compiere.model.MRecurring;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.document.IRecurringPA;

public final class RecurringPA implements IRecurringPA {

	private static final String SQL_TODAY = "SELECT r.* " //
			+ "\n FROM C_Recurring r "//
			+ "\n WHERE "//
			+ "\n    r.IsActive='Y' " //
			+ "\n    AND r.AD_Client_ID=? " //
			+ "\n    AND r.DateNextRun::date <= now()::date " //
			+ "\n    AND not exists ( " //
			+ "\n         select * from C_Recurring_Run rr "//
			+ "\n         where "//
			+ "\n            rr.C_Recurring_ID=r.C_Recurring_ID " //
			+ "\n            AND rr.DateDoc=r.DateNextRun::date "//
			+ "\n    )";

	@Override
	public Collection<I_C_Recurring> retrieveForToday(final int adClientId,
			final String trxName) {

		final List<MRecurring> recurrings = retrieveList(SQL_TODAY,
				new Object[] { adClientId }, MRecurring.class, trxName);

		return new ArrayList<>(recurrings);
	}
	
	/**
	 * Executes the given sql (prepared) statement and returns the result as a
	 * list of {@link PO}s.
	 * 
	 * @param sql
	 *            the sql statement to execute
	 * @param params
	 *            prepared statement parameters (its length must correspond to
	 *            the number of '?'s in the sql)
	 * @param clazz
	 *            the class of the returned list elements needs to have a
	 *            constructor with three parameters:
	 *            <li>{@link Properties},
	 *            <li>{@link ResultSet},
	 *            <li>{@link String}
	 * @param trxName
	 */
	private <T extends PO> List<T> retrieveList(final String sql, final Object[] params, final Class<T> clazz, final String trxName)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			
			final Properties ctx = Env.getCtx();
			final String tableName = InterfaceWrapperHelper.getTableName(clazz);

			final List<T> result = new ArrayList<>();
			while (rs.next()) 
			{
				final T newPO = TableModelLoader.instance.retrieveModel(ctx, tableName, clazz, rs, trxName);
				result.add(newPO);
			}
			return result;
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, params);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

}
