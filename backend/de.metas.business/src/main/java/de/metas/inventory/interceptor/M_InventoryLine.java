package de.metas.inventory.interceptor;

import de.metas.inventory.IInventoryBL;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_InventoryLine;
import org.springframework.stereotype.Component;

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
@Interceptor(I_M_InventoryLine.class)
@Callout(I_M_InventoryLine.class)
@Component
public class M_InventoryLine
{
	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
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

		inventoryBL.setDefaultInternalChargeId(inventoryLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_M_InventoryLine.COLUMNNAME_M_Product_ID)
	@CalloutMethod(columnNames = I_M_InventoryLine.COLUMNNAME_InventoryType)
	public void onProductChange(final I_M_InventoryLine inventoryLine)
	{
		final IProductActivityProvider productActivityProvider = Services.get(IProductActivityProvider.class);

		if (InterfaceWrapperHelper.isCopy(inventoryLine))
		{
			// let the activity be copied from the source.
			return;
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(inventoryLine.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final ActivityId productActivityId = productActivityProvider.getActivityForAcct(
				ClientId.ofRepoId(inventoryLine.getAD_Client_ID()),
				OrgId.ofRepoId(inventoryLine.getAD_Org_ID()),
				productId);
		if (productActivityId == null)
		{
			return;
		}
		inventoryLine.setC_Activity_ID(productActivityId.getRepoId());
	}

}
