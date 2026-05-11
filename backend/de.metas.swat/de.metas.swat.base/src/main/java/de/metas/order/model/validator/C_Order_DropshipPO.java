/*
 * #%L
 * de.metas.swat.base
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
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model interceptor for {@link I_C_Order} that handles dropship-warehouse purchase order creation.
 * <p>
 * Responsibilities:
 * <ul>
 * <li>BEFORE_COMPLETE: validates that every sales-order line on a dropship-warehouse SO has a vendor
 *     (either via {@code C_BPartner_Vendor_ID} on the line, or via an active {@code C_BPartner_Product}
 *     row with {@code UsedForVendor='Y'} for the product). Throws an {@link AdempiereException} listing
 *     offending line numbers if validation fails.</li>
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

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void validateVendorsBeforeComplete(@NonNull final I_C_Order order)
	{
		if (!isDropshipWarehouseOrder(order))
		{
			return;
		}

		final List<de.metas.interfaces.I_C_OrderLine> lines = orderBL.getLinesByOrderIds(
				Collections.singleton(OrderId.ofRepoId(order.getC_Order_ID())));

		final List<String> offendingLineNumbers = lines.stream()
				.filter(this::isVendorMissing)
				.map(ol -> String.valueOf(ol.getLine()))
				.sorted(Comparator.comparingInt(Integer::parseInt))
				.collect(Collectors.toList());

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

	/**
	 * Returns {@code true} if the given order line is missing a vendor.
	 * <p>
	 * A line is considered "missing a vendor" when:
	 * <ol>
	 * <li>The line has no explicit vendor set ({@code C_BPartner_Vendor_ID <= 0}), AND</li>
	 * <li>No active {@code C_BPartner_Product} row with {@code UsedForVendor='Y'} exists for the product.</li>
	 * </ol>
	 * Lines without a product ({@code M_Product_ID <= 0}) are flagged as offending because
	 * they cannot be matched to a vendor.
	 */
	private boolean isVendorMissing(@NonNull final I_C_OrderLine ol)
	{
		if (ol.getC_BPartner_Vendor_ID() > 0)
		{
			// line already has an explicit vendor — not offending
			return false;
		}
		final int productRepoId = ol.getM_Product_ID();
		if (productRepoId <= 0)
		{
			// no product — cannot determine a vendor; flag as offending
			return true;
		}
		final ProductId productId = ProductId.ofRepoId(productRepoId);
		return !hasAnyVendorForProduct(productId);
	}

	/**
	 * Returns {@code true} if at least one active {@code C_BPartner_Product} row with
	 * {@code UsedForVendor='Y'} exists for the given product (any org, any vendor).
	 */
	private boolean hasAnyVendorForProduct(@NonNull final ProductId productId)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_UsedForVendor, true)
				.create()
				.anyMatch();
	}
}
