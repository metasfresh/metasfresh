/**
 * 
 */
package org.compiere.apps.search;

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


import java.util.Properties;

/**
 * @author tsa
 * 
 */
public interface IInfoSimple
{
	public Properties getCtx();

	public int getWindowNo();

	public Object getContextVariable(String columnName);

	public int getContextVariableAsInt(String columnName);

	public void setCtxAttribute(String column, Object value);

	public void executeQuery();

	public void setJoinClauseAnd(boolean isAND);

	public Integer getSelectedRowKey();

	/**
	 * Gets grid table value.
	 * 
	 * @param row
	 * @param columnName
	 * @return value
	 */
	public <T> T getValue(int row, String columnName);

	/**
	 * Gets row record ID or -1.
	 * 
	 * @param rowIndexModel
	 * @return row key or -1
	 */
	public int getRecordId(final int rowIndexModel);

	/**
	 * Sets grid table value.
	 * 
	 * @param infoColumn
	 * @param rowIndexModel
	 * @param value
	 */
	public void setValue(Info_Column infoColumn, int rowIndexModel, Object value);

	/**
	 * Sets grid table value
	 * 
	 * @param columnName
	 * @param rowIndexModel
	 * @param value
	 */
	public void setValueByColumnName(String columnName, int rowIndexModel, Object value);
	
	/**
	 * Overrides the "isLoading" functionality.
	 * 
	 * @param ignoreLoading
	 */
	void setIgnoreLoading(boolean ignoreLoading);

	<T extends IInfoColumnController> T getInfoColumnControllerOrNull(String columnName, Class<T> controllerClass);
	
	/**
	 * 
	 * @return true if this window is currently disposing or it was already disposed
	 */
	public boolean isDisposed();
}
