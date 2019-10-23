package org.compiere.util;

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


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExecuteResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1121381937893681417L;
	private int m_updateCount = 0;
	private ArrayList<ResultSet> m_resultSets = new ArrayList<ResultSet>();
	private int resultSetPointer = 0;
	private boolean firstResult = false;
	
	public int getUpdateCount() {
		if (resultSetPointer >= m_resultSets.size()) {
			int updateCount = m_updateCount;
			m_updateCount = -1;
			return updateCount;
		}
		return -1;
	}
	
	public void setUpdateCount(int updateCount) {
		m_updateCount = updateCount;
	}
	
	public void addResultSet(ResultSet rs) {
		m_resultSets.add(rs);
	}
	
	public boolean getMoreResults() {
		if (resultSetPointer >= m_resultSets.size())
			return false;
		
		//implicitly close the current resultset
		try {
			m_resultSets.get(resultSetPointer).close();
		} catch (SQLException e) {}
		resultSetPointer ++;
		return (resultSetPointer < m_resultSets.size());
	}
	
	public ResultSet getResultSet() {
		if (resultSetPointer >= m_resultSets.size())
			return null;
		
		return m_resultSets.get(resultSetPointer);
	}

	public boolean isFirstResult() {
		return firstResult;
	}

	public void setFirstResult(boolean firstResult) {
		this.firstResult = firstResult;
	}
}
