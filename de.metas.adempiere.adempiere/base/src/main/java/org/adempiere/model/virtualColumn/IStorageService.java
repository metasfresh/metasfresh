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

import org.adempiere.util.ISingletonService;
import org.compiere.model.PO;

public interface IStorageService extends ISingletonService {

	Map<Integer, ?> lookupIfValid(String tableName, String colName,
			Collection<Integer> recordIds, String trxSame);

	/**
	 * 
	 * @param pos
	 * @param colName
	 * @param trxSame
	 * @throws IllegalArgumentException
	 *             if 'pos' doesn't contain {@link PO}s
	 */
	void invalidate(Collection<?> pos, String colName, String trxSame);

	void invalidate(String tableName, String colName,
			Collection<Integer> recordIds, String trxSame);

	/**
	 * 
	 * @param pos
	 * @param colName
	 * @param trxSame
	 * @return
	 * @throws IllegalArgumentException
	 *             if 'pos' doesn't contain {@link PO}s
	 */
	boolean allValid(Collection<?> pos, String colName,
			String trxSame);

	boolean allValid(String tableName, String colName,
			Collection<Integer> recordIds, String trxSame);

	/**
	 * 
	 * @param po2value
	 * @param colName
	 * @param trxName
	 * @throws IllegalArgumentException
	 *             if 'po2value' doesn't contain {@link PO}s as keys
	 */
	void store(Map<?, ?> po2value, String colName, String trxName);

	/**
	 * 
	 * @param tableName
	 * @param colName
	 * @param recordId2Value
	 * @param trxName
	 */
	void store(String tableName, String colName,
			Map<Integer, ?> recordId2Value, String trxName);

	/**
	 * For each value in <code>record2value</code>: Store the value for the
	 * given table, column and record <b>if</b> there isn't already a valid
	 * stored value. Used to make sure that a virtual column's default value
	 * doesn't overwrite existing valid values.
	 * 
	 * @param tableName
	 * @param colName
	 * @param recordId2Value
	 */
	void storeIf(String tableName, String colName,
			Map<Integer, ?> recordId2Value, String trxName);

}
