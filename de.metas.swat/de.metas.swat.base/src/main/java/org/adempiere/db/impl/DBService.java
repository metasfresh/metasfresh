package org.adempiere.db.impl;

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

import org.adempiere.db.IDBService;
import org.compiere.util.DB;

public class DBService implements IDBService
{
	/**
	 * Invokes {@link org.compiere.util.DB#prepareStatement(String, String)}.
	 */
	@Override
	public PreparedStatement mkPstmt(final String sql, final String trxName)
	{

		return DB.prepareStatement(sql, trxName);
	}

	@Override
	public void close(PreparedStatement pstmt)
	{
		DB.close(pstmt);
	}

	/**
	 * Invokes {@link DB#getSQLValueEx(String, String, Object...)}.
	 * 
	 * @param trxName
	 * @param sql
	 * @param params
	 * @return
	 */
	@Override
	public int getSQLValueEx(final String trxName, final String sql, final Object... params)
	{
		return DB.getSQLValueEx(trxName, sql, params);
	}

}
