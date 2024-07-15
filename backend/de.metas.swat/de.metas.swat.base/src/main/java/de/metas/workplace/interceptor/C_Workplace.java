/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.workplace.interceptor;

import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Workplace;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Workplace.class)
@Callout(I_C_Workplace.class)
@Component
public class C_Workplace
{
	private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);

	public C_Workplace()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Workplace.COLUMNNAME_M_Warehouse_ID)
	public void resetPickingSlotId(@NonNull final I_C_Workplace workplace)
	{
		workplace.setM_PickingSlot_ID(-1);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Workplace.COLUMNNAME_M_PickingSlot_ID)
	public void validatePickingSlot(@NonNull final I_C_Workplace workplace)
	{
		final PickingSlotId pickingSlotId = PickingSlotId.ofRepoIdOrNull(workplace.getM_PickingSlot_ID());
		if (pickingSlotId == null)
		{
			return;
		}

		final I_M_PickingSlot pickingSlot = pickingSlotBL.getById(pickingSlotId);

		if (pickingSlot.getM_Warehouse_ID() != workplace.getM_Warehouse_ID())
		{
			throw new AdempiereException("Different Warehouses on Picking Slot: " + pickingSlot + " and on Workplace: " 
												 + workplace.getC_Workplace_ID());
		}
	}
}
