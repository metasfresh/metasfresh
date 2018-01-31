package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.translate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.apache.commons.lang3.RandomStringUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import de.metas.product.IProductBL;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class PurchaseRowFactory
{
	@Builder(builderMethodName = "rowFromPurchaseCandidateBuilder", builderClassName = "RowFromPurchaseCandidateBuilder")
	private PurchaseRow buildRowFromPurchaseCandidate(
			@NonNull final PurchaseCandidate purchaseCandidate,
			@Nullable final VendorProductInfo vendorProductInfo,
			@NotNull final Date datePromised)
	{
		final int bpartnerId = purchaseCandidate.getVendorBPartnerId();
		final JSONLookupValue vendorBPartner = createBPartnerLookupValue(bpartnerId);
		final JSONLookupValue product;
		if (vendorProductInfo != null)
		{
			product = createProductLookupValue(
					vendorProductInfo.getM_Product_ID(),
					vendorProductInfo.getProductNo(),
					vendorProductInfo.getProductName());
		}
		else
		{
			product = createProductLookupValue(purchaseCandidate.getProductId());
		}
		final String uom = createUOMLookupValueForProductId(product.getKeyAsInt());

		return PurchaseRow.builder()
				.rowId(PurchaseRowId.lineId(purchaseCandidate.getSalesOrderLineId(), bpartnerId))
				.salesOrderId(purchaseCandidate.getSalesOrderId())
				.rowType(PurchaseRowType.LINE)
				.product(product)
				.uomOrAvailablility(uom)
				.qtyToPurchase(purchaseCandidate.getQtyToPurchase())
				.datePromised(datePromised)
				.vendorBPartner(vendorBPartner)
				.purchaseCandidateId(purchaseCandidate.getPurchaseCandidateId())
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				.readonly(purchaseCandidate.isProcessedOrLocked())
				.build();
	}

	public PurchaseRow createGroupRow(final I_C_OrderLine salesOrderLine, final List<PurchaseRow> rows)
	{
		final JSONLookupValue product = createProductLookupValue(salesOrderLine.getM_Product_ID());
		final BigDecimal qtyToDeliver = salesOrderLine.getQtyOrdered().subtract(salesOrderLine.getQtyDelivered());
		final String uom = createUOMLookupValueForProductId(product.getKeyAsInt());

		final PurchaseRow groupRow = PurchaseRow.builder()
				.rowId(PurchaseRowId.groupId(salesOrderLine.getC_OrderLine_ID()))
				.salesOrderId(salesOrderLine.getC_Order_ID())
				.rowType(PurchaseRowType.GROUP)
				.product(product)
				.uomOrAvailablility(uom)
				.qtyToDeliver(qtyToDeliver)
				.datePromised(salesOrderLine.getDatePromised())
				.orgId(salesOrderLine.getAD_Org_ID())
				.warehouseId(salesOrderLine.getM_Warehouse_ID())
				.includedRows(rows)
				.readonly(true) // grouping lines are always readonly
				.build();
		return groupRow;
	}

	@Builder(builderMethodName = "rowFromAvailabilityResultBuilder", builderClassName = "RowFromAvailabilityResultBuilder")
	private PurchaseRow buildRowFromFromAvailabilityResult(
			@NonNull PurchaseRow parentRow,
			@NonNull final AvailabilityResult availabilityResult)
	{
		final String availability = !Check.isEmpty(availabilityResult.getAvailabilityText(), true)
				? availabilityResult.getAvailabilityText()
				: availabilityResult.getType().translate();

		return parentRow.toBuilder()
				.rowId(parentRow.getRowId().withAvailability(availabilityResult.getType(), createRandomString()))
				.salesOrderId(parentRow.getSalesOrderId())
				.rowType(PurchaseRowType.AVAILABILITY_DETAIL)
				.qtyToPurchase(availabilityResult.getQty())
				.readonly(true)
				.uomOrAvailablility(availability)
				.datePromised(availabilityResult.getDatePromised())
				.build();
	}

	@Builder(builderMethodName = "rowFromThrowableBuilder", builderClassName = "RowFromThrowableBuilder")
	private PurchaseRow buildRowFromFromThrowable(
			@NonNull final PurchaseRow parentRow,
			@NonNull final Throwable throwable)
	{
		return parentRow.toBuilder()
				.rowId(parentRow.getRowId().withAvailability(Type.NOT_AVAILABLE, createRandomString()))
				.salesOrderId(parentRow.getSalesOrderId())
				.rowType(PurchaseRowType.AVAILABILITY_DETAIL)
				.qtyToPurchase(BigDecimal.ZERO)
				.readonly(true)
				.uomOrAvailablility(Util.coalesce(throwable.getLocalizedMessage(), throwable.getMessage(), throwable.getClass().getName()))
				.datePromised(null)
				.build();
	}

	private static String createRandomString()
	{
		final boolean includeLetters = true;
		final boolean includeNumbers = true;

		return RandomStringUtils.random(8, includeLetters, includeNumbers);
	}

	private static JSONLookupValue createProductLookupValue(final int productId)
	{
		final String productValue = null;
		final String productName = null;
		return createProductLookupValue(productId, productValue, productName);
	}

	private static JSONLookupValue createProductLookupValue(final int productId, final String productValue, final String productName)
	{
		if (productId <= 0)
		{
			return null;
		}

		final I_M_Product product = loadOutOfTrx(productId, I_M_Product.class);
		if (product == null)
		{
			return JSONLookupValue.unknown(productId);
		}

		String productValueEffective = !Check.isEmpty(productValue, true) ? productValue.trim() : product.getValue();
		String productNameEffective = !Check.isEmpty(productName, true) ? productName.trim() : product.getName();
		final String displayName = productValueEffective + "_" + productNameEffective;
		return JSONLookupValue.of(product.getM_Product_ID(), displayName);
	}

	private static JSONLookupValue createBPartnerLookupValue(final int bpartnerId)
	{
		if (bpartnerId <= 0)
		{
			return null;
		}

		final I_C_BPartner bpartner = loadOutOfTrx(bpartnerId, I_C_BPartner.class);
		if (bpartner == null)
		{
			return null;
		}

		final String displayName = bpartner.getValue() + "_" + bpartner.getName();
		return JSONLookupValue.of(bpartner.getC_BPartner_ID(), displayName);
	}

	private static String createUOMLookupValueForProductId(final int productId)
	{
		if (productId <= 0)
		{
			return null;
		}

		final I_M_Product product = loadOutOfTrx(productId, I_M_Product.class);
		if (product == null)
		{
			return null;
		}

		final I_C_UOM uom = Services.get(IProductBL.class).getStockingUOM(product);
		if (uom == null)
		{
			return null;
		}
		return translate(uom, I_C_UOM.class).getUOMSymbol();
	}
}
