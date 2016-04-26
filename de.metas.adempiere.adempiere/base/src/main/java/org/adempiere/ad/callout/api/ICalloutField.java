package org.adempiere.ad.callout.api;

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
 * Callout aware field
 * 
 * @author tsa
 * 
 */
public interface ICalloutField
{

	boolean isTriggerCalloutAllowed();

	Properties getCtx();

	String getTableName();

	int getAD_Table_ID();

	int getAD_Column_ID();

	Object getValue();

	Object getOldValue();

	String getColumnName();

	<T> T getModel(Class<T> modelClass);

	int getWindowNo();

	int getTabNo();

	/**
	 * @return true if we are currently creating this record by copying (with or without details) from another record
	 */
	boolean isRecordCopyingMode();

	/**
	 * @return true if we are currently creating this record by copying (with details) from another record
	 */
	boolean isRecordCopyingModeIncludingDetails();

	ICalloutExecutor getCurrentCalloutExecutor();
	
	void fireDataStatusEEvent(final String AD_Message, final String info, final boolean isError);
}
