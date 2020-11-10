/**
 * 
 */
package de.metas.replenishment.impexp;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_I_Replenish;
import org.compiere.model.I_M_Replenish;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
/* package */ class ReplenishImportHelper
{
	final public boolean isValidRecordForImport(@NonNull final I_I_Replenish importRecord)
	{
		return (importRecord.getM_Product_ID() > 0)
				&& (importRecord.getM_Warehouse_ID() > 0)
				&& (importRecord.getLevel_Max() != null)
				&& (importRecord.getLevel_Min() != null)
				&& (!Check.isEmpty(importRecord.getReplenishType(), true));
	}

	final public I_M_Replenish createNewReplenish(@NonNull final I_I_Replenish importRecord)
	{
		final I_M_Replenish replenish = InterfaceWrapperHelper.newInstance(I_M_Replenish.class, importRecord);
		setReplenishmenttValueFields(importRecord, replenish);
		return replenish;
	}

	final public I_M_Replenish uppdateReplenish(@NonNull final I_I_Replenish importRecord)
	{
		final I_M_Replenish replenish = importRecord.getM_Replenish();
		setReplenishmenttValueFields(importRecord, replenish);
		return replenish;
	}

	private void setReplenishmenttValueFields(@NonNull final I_I_Replenish importRecord, @NonNull final I_M_Replenish replenish)
	{
		// mandatory fields
		replenish.setAD_Org_ID(importRecord.getAD_Org_ID());
		replenish.setM_Product_ID(importRecord.getM_Product_ID());
		replenish.setM_Warehouse_ID(importRecord.getM_Warehouse_ID());
		replenish.setLevel_Max(importRecord.getLevel_Max());
		replenish.setLevel_Min(importRecord.getLevel_Min());
		replenish.setReplenishType(importRecord.getReplenishType());
		replenish.setTimeToMarket(importRecord.getTimeToMarket());

		// optional fields
		if (importRecord.getM_Locator_ID() > 0)
		{
			replenish.setM_Locator_ID(importRecord.getM_Locator_ID());
		}
		if (importRecord.getC_Period_ID() > 0)
		{
			replenish.setC_Period_ID(importRecord.getC_Period_ID());
			replenish.setC_Calendar_ID(getCalendar(importRecord.getC_Period()));
		}
	}

	private int getCalendar(final I_C_Period period)
	{
		return period.getC_Year().getC_Calendar_ID();
	}

}
