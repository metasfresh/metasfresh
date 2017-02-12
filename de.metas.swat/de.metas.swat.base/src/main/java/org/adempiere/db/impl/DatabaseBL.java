package org.adempiere.db.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.db.IDatabaseBL;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

public final class DatabaseBL implements IDatabaseBL {

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
	@Override
	public final <T extends PO> List<T> retrieveList(final String sql, final Object[] params, final Class<T> clazz, final String trxName)
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

			final List<T> result = new ArrayList<T>();
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

	@Override
	public <T extends PO> Map<Integer, T> retrieveMap(final String sql, final Object[] params, final Class<T> clazz, final String trxName)
	{
		final Map<Integer, T> result = new HashMap<Integer, T>();

		for (T currentPO : retrieveList(sql, params, clazz, trxName))
		{
			result.put(currentPO.get_ID(), currentPO);
		}
		return result;
	}
}
