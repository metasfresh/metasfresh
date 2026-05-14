/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.model.validator;

import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.createFrom.po_from_so.DropshipPOFromSOService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Model interceptor for {@link I_C_Order} that handles dropship-warehouse purchase order creation.
 * <p>
 * Responsibilities:
 * <ul>
 * <li>BEFORE_COMPLETE: validates that every sales-order line on a dropship-warehouse SO has a vendor.
 *     Explicit {@code C_BPartner_Vendor_ID} on the line wins. If absent, falls back to the canonical
 *     vendor lookup via {@link VendorProductInfoService#getDefaultVendorProductInfo} and
 *     <em>populates</em> the line's {@code C_BPartner_Vendor_ID} when the lookup succeeds.
 *     Throws an {@link AdempiereException} listing offending line numbers if any line still has no vendor
 *     after the auto-fill attempt.</li>
 * <li>AFTER_COMPLETE: triggers dropship PO creation for the sales order via
 *     {@link DropshipPOFromSOService}.</li>
 * </ul>
 * Both methods are no-ops for non-dropship-warehouse orders.
 */
@Component
@Interceptor(I_C_Order.class)
@RequiredArgsConstructor
public class C_Order_DropshipPO
{
	private static final AdMessageKey MSG_MISSING_VENDOR = AdMessageKey.of("DropshipWarehouse_MissingVendorOnLine");

	@NonNull private final DropshipPOFromSOService dropshipPOFromSOService;
	@NonNull private final VendorProductInfoService vendorProductInfoService;

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void validateVendorsBeforeComplete(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			// BEFORE_COMPLETE vendor-validation only makes sense for SOs (the auto-fill is upstream
			// of the PO aggregator). POs on a dropship warehouse don't need this check.
			return;
		}
		if (!isDropshipWarehouseOrder(order))
		{
			return;
		}

		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final List<de.metas.interfaces.I_C_OrderLine> lines = orderBL.getLinesByOrderIds(
				Collections.singleton(OrderId.ofRepoId(order.getC_Order_ID())));

		final List<String> offendingLineNumbers = new ArrayList<>();
		for (final de.metas.interfaces.I_C_OrderLine line : lines)
		{
			if (line.getC_BPartner_Vendor_ID() > 0)
			{
				// explicit vendor already set — line is fine
				continue;
			}
			final int productRepoId = line.getM_Product_ID();
			if (productRepoId <= 0)
			{
				// no product — cannot resolve a vendor
				offendingLineNumbers.add(String.valueOf(line.getLine()));
				continue;
			}
			final Optional<VendorProductInfo> vendorInfoOpt = vendorProductInfoService.getDefaultVendorProductInfo(
					ProductId.ofRepoId(productRepoId),
					orgId);
			if (vendorInfoOpt.isPresent())
			{
				// auto-fill the vendor from the canonical lookup and persist
				line.setC_BPartner_Vendor_ID(vendorInfoOpt.get().getVendorId().getRepoId());
				InterfaceWrapperHelper.saveRecord(line);
			}
			else
			{
				offendingLineNumbers.add(String.valueOf(line.getLine()));
			}
		}

		offendingLineNumbers.sort(Comparator.comparingInt(Integer::parseInt));

		if (!offendingLineNumbers.isEmpty())
		{
			throw new AdempiereException(MSG_MISSING_VENDOR, String.join(", ", offendingLineNumbers))
					.markAsUserValidationError();
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createDropshipPOAfterComplete(@NonNull final I_C_Order order)
	{
		if (!isDropshipWarehouseOrder(order))
		{
			return;
		}
		if (!order.isSOTrx())
		{
			// safety guard: never run on purchase orders even if accidentally invoked on a dropship warehouse
			return;
		}
		dropshipPOFromSOService.createDropshipPOForSO(OrderId.ofRepoId(order.getC_Order_ID()));
	}

	// -------

	private boolean isDropshipWarehouseOrder(@NonNull final I_C_Order order)
	{
		final int warehouseRepoId = order.getM_Warehouse_ID();
		if (warehouseRepoId <= 0)
		{
			return false;
		}
		final I_M_Warehouse warehouse = warehouseDAO.getById(WarehouseId.ofRepoId(warehouseRepoId));
		return warehouse != null && warehouse.isDropShipWarehouse();
	}
}
