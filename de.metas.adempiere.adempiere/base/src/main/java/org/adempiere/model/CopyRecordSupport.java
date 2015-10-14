package org.adempiere.model;

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


import java.util.List;

import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.PO;

/**
 * @author Cristina Ghita, METAS.RO
 * 
 */
public interface CopyRecordSupport
{
	/**
	 * Copy the persistence object with all his children
	 * 
	 * @param po
	 * @param parentKeyColumn
	 * @param parent_id
	 * @param grid
	 *            tab
	 */
	public void copyRecord(PO po, String trxName);

	/**
	 * Gets the list with suggested table names for a PO
	 * 
	 * @param persistence
	 *            object
	 * @param grid
	 *            tab
	 * @return a list of tables with info
	 */
	public List<TableInfoVO> getSuggestedChildren(PO po, GridTab gt);

	public void setParentKeyColumn(String parentKeyColumn);

	public String getParentKeyColumn();

	public void setParentID(int parent_id);

	public int getParentID();

	public PO getParentPO();

	public void setParentPO(PO parentPO);

	public void setGridTab(GridTab gt);

	public GridTab getGridTab();

	public void setFromPO_ID(int oldPO_id);

	public int getFromPO_ID();

	/**
	 * Updates given <code>po</code> and sets the right values for columns that needs special handling.
	 * 
	 * e.g. this method makes sure columns like Name or Value have an unique value (to not trigger the unique index).
	 * 
	 * @param to
	 */
	public void setSpecialColumnsName(PO to);

	public boolean isBase();

	public void setBase(boolean base);

	/**
	 * Gets the value to be copied for a column which is calculated and whom value is not desirable to be copied.
	 * 
	 * @param to
	 * @param from
	 * @param columnName
	 * @return copied value which needs to be set
	 */
	public Object getValueToCopy(final PO to, final PO from, final String columnName);

	/**
	 * Gets the value to be copied for a column which is calculated and whom value is not desirable to be copied.
	 * 
	 * @param gridField
	 * @return copied value which needs to be set
	 */
	public Object getValueToCopy(final GridField gridField);

	public int getAD_Window_ID();

	public void setAD_Window_ID(int aDWindowID);
}
