package org.compiere.util;

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

/**
 * Cache reset listener.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @see CacheMgt#addCacheResetListener(String, ICacheResetListener)
 */
@FunctionalInterface
public interface ICacheResetListener
{
	/**
	 * Method called when listened cache got reset.
	 * 
	 * @param tableName
	 * @param key actual key or <code>null</code> in case the whole cache got reset
	 */
	int reset(String tableName, Object key);
}
