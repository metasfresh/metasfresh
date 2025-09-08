/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.warehouse;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;

public class M_Warehouse_StepDefData extends StepDefData<I_M_Warehouse>
		implements StepDefDataGetIdAware<WarehouseId, I_M_Warehouse>
{
	public M_Warehouse_StepDefData()
	{
		super(I_M_Warehouse.class);
	}

	@Override
	public WarehouseId extractIdFromRecord(final I_M_Warehouse record)
	{
		return WarehouseId.ofRepoId(record.getM_Warehouse_ID());
	}
}
