package de.metas.inventory.interceptor;

import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_InventoryLine;
import org.springframework.stereotype.Component;

import de.metas.inventory.IInventoryBL;
import de.metas.util.Services;

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
@Interceptor(I_M_Inventory.class)
@Component
public class M_InventoryLine
{
	@Init
	public void init()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_M_InventoryLine.COLUMNNAME_InventoryType)
	@CalloutMethod(columnNames = I_M_InventoryLine.COLUMNNAME_InventoryType)
	public void onChargeAccountChangeForInternalInventory(final I_M_InventoryLine inventoryLine)
	{
		final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);

		final String inventoryType = inventoryLine.getInventoryType();

		if (!X_M_InventoryLine.INVENTORYTYPE_ChargeAccount.equals(inventoryType))
		{
			// nothing to do
			return;
		}

		if (!inventoryBL.isInternalUseInventory(inventoryLine))
		{
			// nothing to do, this is covered by the
			return;
		}

		if (inventoryLine.getC_Charge_ID() > 0)
		{
			// nothing to do, it's already set
			return;
		}

		final int chargeId = inventoryBL.getDefaultInternalChargeId();

		inventoryLine.setC_Charge_ID(chargeId);

	}

}
