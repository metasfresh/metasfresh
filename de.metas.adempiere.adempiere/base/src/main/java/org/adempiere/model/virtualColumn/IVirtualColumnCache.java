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

public interface IVirtualColumnCache {

	void addRecordIds(final Collection<Integer> recordIds, final String trxName);
	
	/**
	 * Invalidates a cached object. Invalid objects may be replaced using
	 * {@link #cacheValue(int, Object)}.
	 * 
	 * @param recordId
	 * @throws IllegalArgumentException
	 *             if there is no object cached for <code>recordId</code>.
	 */
	void invalidate(int recordId, String trxName);

	void invalidateAll(String trxName);

	/**
	 * 
	 * @param recordId
	 * @return
	 * @throws IllegalArgumentException
	 *             if there is no object cached for <code>recordId</code>.
	 */
	Object getCachedValue(String colName, int recordId);

	/**
	 * 
	 * @param recordId
	 * @param value
	 *            may be <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>recordId</code> is less than one.
	 */
	void cacheValue(final String colName, final int recordId,
			final Object value, String trxName);

	boolean isCached(final int recordId);

	boolean isCachedAndValid(final String colName, final int recordId);

	Collection<Integer> getRecordIds();

}