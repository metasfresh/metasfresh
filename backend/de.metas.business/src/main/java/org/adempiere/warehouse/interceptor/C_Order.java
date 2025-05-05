/*
 * #%L
 * de.metas.business
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

package org.adempiere.warehouse.interceptor;

import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.document.location.DocumentLocation;
import de.metas.order.IOrderBL;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Callout(I_C_Order.class)
@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	public C_Order()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_M_Warehouse_ID)
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Order.COLUMNNAME_M_Warehouse_ID)
	public void updateBillPartnerAndDropshipFlagFromWarehouse(@NonNull final I_C_Order order)
	{
		if (order.getM_Warehouse_ID() <= 0 || order.isSOTrx())
		{
			return; // nothing to do
		}

		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());

		final Optional<DocumentLocation> externalBPartnerBillToAddress = getBillingLocationDocumentForExternalBPartner(warehouseId, orgId);

		if (externalBPartnerBillToAddress.isPresent())
		{
			OrderDocumentLocationAdapterFactory.billLocationAdapter(order)
					.setFrom(externalBPartnerBillToAddress.get());

			order.setIsDropShip(false);
		}
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_M_Warehouse_ID)
	public void resetBillingPartnerIfWarehouseChanged(@NonNull final I_C_Order order)
	{
		if (order.getM_Warehouse_ID() <= 0 || order.isSOTrx())
		{
			return; // nothing to do
		}

		final I_M_Warehouse currentWarehouse = warehouseBL.getById(WarehouseId.ofRepoId(order.getM_Warehouse_ID()));

		if (currentWarehouse.isBPartnerInvoicesWithVendors())
		{
			//dev-note: no need to reset billing partner (org.adempiere.warehouse.interceptor.C_Order.updateBillPartnerAndDropshipFlagFromWarehouse will do that)
			return;
		}

		final I_C_Order oldOrder = InterfaceWrapperHelper.createOld(order, I_C_Order.class);

		if (oldOrder == null || oldOrder.getM_Warehouse_ID() <= 0)
		{
			return;
		}

		final I_M_Warehouse oldWarehouse = warehouseBL.getById(WarehouseId.ofRepoId(oldOrder.getM_Warehouse_ID()));

		if (!oldWarehouse.isBPartnerInvoicesWithVendors())
		{
			return;
		}

		//reset to default billing location
		orderBL.setBillLocation(order);
	}

	@NonNull
	private Optional<DocumentLocation> getBillingLocationDocumentForExternalBPartner(@NonNull final WarehouseId warehouseId, @NonNull final OrgId orgId)
	{
		final I_M_Warehouse warehousesRecord = warehouseBL.getById(warehouseId);

		if (!warehousesRecord.isBPartnerInvoicesWithVendors())
		{
			return Optional.empty();
		}

		final I_C_BPartner orgBPartner = bPartnerOrgBL.retrieveLinkedBPartner(orgId.getRepoId());

		if (warehousesRecord.getC_BPartner_ID() != orgBPartner.getC_BPartner_ID())
		{
			return Optional.of(warehouseBL.getBPartnerBillingLocationDocument(WarehouseId.ofRepoId(warehousesRecord.getM_Warehouse_ID())));
		}
		return Optional.empty();
	}
}
