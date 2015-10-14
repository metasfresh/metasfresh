package org.adempiere.webui.dashboard;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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


/**
 * Dashboard panel Activity item descriptor
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03798_Dashboard:_Anzahl_Rechnungen_%282013011810000038%29
 */
public interface IDPActivityItem
{
	boolean isAvailable();

	String getLabel();

	/**
	 * Has this item a dynamic label.
	 * 
	 * A dynamic label is a label which is changing it's value on each get (it is encapsulating the result).
	 * 
	 * @return true if is a dynamic label
	 */
	boolean isDynamicLabel();

	String getDescription();

	String getImage();

	/**
	 * Gets AD_Window_ID to be open when user clicks on activity item. If this method returns -1 then the window will be detected based on {@link #getTableName()}
	 * 
	 * @return AD_Window_ID or -1
	 */
	int getAD_Window_ID();

	String getTableName();

	/**
	 * Gets WHERE clause to be used when calculating the values and when opening the window.
	 * 
	 * NOTE: when implementing this method please fully qualify all column names because this is required for queries that needs to be used in reporting (e.g. user clicks on button, window opens and
	 * then he wants to report)
	 * 
	 * @return
	 */
	String getWhereClause();
	
	/**
	 * 
	 * @return true if this activity item requires that all that shall be filtered by AD_Org_ID=login org
	 */
	boolean isFilterLoginOrgOnly();
	
	String getCounterTableName();
	
	String getCounterWhereClause();
	
}
