package org.adempiere.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.compiere.model.GridField;
import org.compiere.model.PO;

import com.google.common.collect.ImmutableList;

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
	void copyRecord(PO po, String trxName);

	/**
	 * Gets the list with suggested table names for a PO
	 *
	 * @param persistence
	 *            object
	 * @param grid
	 *            tab
	 * @return a list of tables with info
	 */
	List<CopyRecordSupportTableInfo> getSuggestedChildren(PO po, List<CopyRecordSupportTableInfo> suggestedChildren);
	
	default List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po)
	{
		return getSuggestedChildren(po, ImmutableList.of());
	}

	/**
	 * Updates given <code>po</code> and sets the right values for columns that needs special handling.
	 *
	 * e.g. this method makes sure columns like Name or Value have an unique value (to not trigger the unique index).
	 *
	 * @param to
	 */
	void updateSpecialColumnsName(PO to);

	void setParentKeyColumn(String parentKeyColumn);

	void setParentPO(PO parentPO);

	void setSuggestedChildrenToCopy(List<CopyRecordSupportTableInfo> suggestedChildrenToCopy);

	void setFromPO_ID(int oldPO_id);

	void setBase(boolean base);

	/**
	 * Gets the value to be copied for a column which is calculated and whom value is not desirable to be copied.
	 *
	 * @param to
	 * @param from
	 * @param columnName
	 * @return copied value which needs to be set
	 */
	Object getValueToCopy(final PO to, final PO from, final String columnName);

	/**
	 * Gets the value to be copied for a column which is calculated and whom value is not desirable to be copied.
	 *
	 * @param gridField
	 * @return copied value which needs to be set
	 */
	Object getValueToCopy(final GridField gridField);

	void setAD_Window_ID(int aDWindowID);

	/**
	 * Allows other modules to install customer code to be executed each time a record was copied.
	 * <p>
	 * <b>Important:</b> usually it makes sense to register a listerner not here, but by invoking {@link CopyRecordFactory#registerCopyRecordSupport(String, Class)}.
	 * A listener that is registered there will be added to each CopyRecordSupport instance created by that factory.
	 *
	 * @param listener
	 */
	void addOnRecordCopiedListener(IOnRecordCopiedListener listener);

	interface IOnRecordCopiedListener
	{
		void onRecordCopied(final PO to, final PO from);
	}
}
