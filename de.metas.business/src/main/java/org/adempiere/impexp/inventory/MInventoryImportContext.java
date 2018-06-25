/**
 *
 */
package org.adempiere.impexp.inventory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.I_I_Inventory;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class MInventoryImportContext
{
	private I_I_Inventory previousImportRecord = null;
	private List<I_I_Inventory> previousImportRecordsForSameInventory = new ArrayList<>();

	public I_I_Inventory getPreviousImportRecord()
	{
		return previousImportRecord;
	}

	public void setPreviousImportRecord(final I_I_Inventory previousImportRecord)
	{
		this.previousImportRecord = previousImportRecord;
	}

	public int getPreviousM_Inventory_ID()
	{
		return previousImportRecord == null ? -1 : previousImportRecord.getM_Inventory_ID();
	}

	public String getPreviousWarehouseValue()
	{
		return previousImportRecord == null ? null : previousImportRecord.getWarehouseValue();
	}

	public Timestamp getPreviousMovementDate()
	{
		return previousImportRecord == null ? null : previousImportRecord.getMovementDate();
	}

	public List<I_I_Inventory> getPreviousImportRecordsForSameInventory()
	{
		return previousImportRecordsForSameInventory;
	}

	public void clearPreviousRecordsForSameInventory()
	{
		previousImportRecordsForSameInventory = new ArrayList<>();
	}

	public void collectImportRecordForSameInventory(final I_I_Inventory importRecord)
	{
		previousImportRecordsForSameInventory.add(importRecord);
	}
}
