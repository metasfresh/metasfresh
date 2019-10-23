package org.adempiere.db.util;

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
import java.util.Iterator;

import org.compiere.util.DB;

import de.metas.util.collections.BlindIterator;
import de.metas.util.collections.BlindIteratorWrapper;

/**
 * Abstract class useful for implementing iterators over {@link PreparedStatement}s.
 * 
 * This class behaves like an {@link BlindIterator}. If you want full {@link Iterator} functionality then you can wrap this by using {@link BlindIteratorWrapper}.
 * 
 * @author tsa
 * 
 * @param <E>
 */
public abstract class AbstractPreparedStatementBlindIterator<E> extends AbstractResultSetBlindIterator<E>
{
	private PreparedStatement pstmt = null;

	public AbstractPreparedStatementBlindIterator()
	{
		super();
	}

	/**
	 * Create and returns the {@link PreparedStatement}.
	 * 
	 * This method will be called internally, one time, right before trying to iterate first element.
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected abstract PreparedStatement createPreparedStatement() throws SQLException;

	@Override
	protected final ResultSet createResultSet() throws SQLException
	{
		pstmt = createPreparedStatement();
		return pstmt.executeQuery();
	}

	/**
	 * Closes the underlying {@link ResultSet} and {@link PreparedStatement}.
	 */
	@Override
	public void close()
	{
		super.close();
		DB.close(pstmt);
		pstmt = null;
	}
}
