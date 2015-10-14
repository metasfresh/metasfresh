package org.adempiere.model.virtualColumn;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.Collection;
import java.util.Map;

/**
 * Implementors of this class compute data to be displayed in a grid table.
 * 
 * @author tobi42
 * 
 */
public interface IVirtualColumn {

	/**
	 * This method is used to "register" record ids for which virtual column
	 * values should be computed and might later be retrieved by
	 * {@link #getValue(String, int)}.
	 * 
	 * @param recordIds
	 *            the new record Ids to be added. For example if the C_Order
	 *            table has virtual columns, this would be C_Order_IDs.
	 * @param trxName name of the trx this method is invoked from.
	 * @param specific parameters for the computation
	 * 
	 */
	void addRecords(Collection<Integer> recordIds, String trxName,
			Map<String, Object> params);

	/**
	 * either returns a cached (and not yet invalidated) value or computes the
	 * value and stores it in the cache
	 * 
	 * @param recordId
	 * @return
	 */
	Object getValue(String colName, int recordId);

	String[] getColumnNames();

	String getTableName();

	void invalidate(int recordId, String trxName);

	void invalidateAll(String trxName);

	/**
	 * @param virtualColumnListener
	 *            add a listener that will be notified of important events
	 */
	void addVirtualColumnListener(IVirtualColumnListener virtualColumnListener);

	void removeVirtualColumnListener(
			IVirtualColumnListener virtualColumnListener);
}
