package org.adempiere.ad.callout.api;

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
 * Callout record. This is the underlying record for which a field callout or tab callout is invoked.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ICalloutRecord
{
	String getTableName();

	int getAD_Tab_ID();

	/**
	 * @return underlying model
	 */
	<T> T getModel(Class<T> modelClass);
	
	/**
	 * @return underlying model as it was before starting to change it
	 * @see #getModel(Class)
	 */
	<T> T getModelBeforeChanges(Class<T> modelClass);

	Object getValue(String columnName);

	/**
	 * Set New Value & call Callout
	 *
	 * @param columnName database column name
	 * @param value value
	 * @return error message or ""
	 */
	String setValue(String columnName, final Object value);

	boolean dataSave(boolean manualCmd);

	void dataRefresh();

	void dataRefreshAll();

	void dataRefreshRecursively();

}
