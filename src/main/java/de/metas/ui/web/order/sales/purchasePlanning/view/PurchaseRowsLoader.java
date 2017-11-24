package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;

import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.product.IProductBL;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasing.api.IBPartnerProductDAO;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

class PurchaseRowsLoader
{
	// services
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerProductDAO partnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final PurchaseCandidateRepository purchaseCandidatesRepo;

	// parameters
	private final Set<Integer> salesOrderLineIds;

	//
	private List<I_C_OrderLine> salesOrderLines;
	private ImmutableListMultimap<Integer, PurchaseCandidate> purchaseCandidatesBySalesOrderLineId;

	@Builder
	private PurchaseRowsLoader(
			@NonNull final Set<Integer> salesOrderLineIds,
			@NonNull final PurchaseCandidateRepository purchaseCandidatesRepo)
	{
		Check.assumeNotEmpty(salesOrderLineIds, "salesOrderLineIds is not empty");

		this.salesOrderLineIds = ImmutableSet.copyOf(salesOrderLineIds);
		this.purchaseCandidatesRepo = purchaseCandidatesRepo;
	}

	public List<PurchaseRow> load()
	{
		this.salesOrderLines = queryBL
				.createQueryBuilder(I_C_OrderLine.class)
				.addInArrayFilter(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID, salesOrderLineIds)
				.create()
				.list(I_C_OrderLine.class);

		this.purchaseCandidatesBySalesOrderLineId = purchaseCandidatesRepo.streamAllBySalesOrderLineIds(salesOrderLineIds)
				.collect(GuavaCollectors.toImmutableListMultimap(PurchaseCandidate::getSalesOrderLineId));

		return salesOrderLines
				.stream()
				.map(salesOrderLine -> createPurchaseRowsGroup(salesOrderLine))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());

	}

	private PurchaseRow createPurchaseRowsGroup(final I_C_OrderLine salesOrderLine)
	{
		final int salesOrderId = salesOrderLine.getC_Order_ID();
		final int salesOrderLineId = salesOrderLine.getC_OrderLine_ID();
		final Timestamp datePromised = salesOrderLine.getDatePromised();
		final int orgId = salesOrderLine.getAD_Org_ID();
		final int warehouseId = salesOrderLine.getM_Warehouse_ID();

		final Map<Integer, I_C_BPartner_Product> vendorProductInfosByVendorId = getVendorProductInfosIndexedByVendorId(salesOrderLine);

		//
		// Create rows from existing Purchase candidates
		final List<PurchaseCandidate> purchaseCandidates = purchaseCandidatesBySalesOrderLineId.get(salesOrderLineId);
		final ArrayList<PurchaseRow> rows = purchaseCandidates.stream()
				.map(purchaseCandidate -> rowFromPurchaseCandidateBuilder()
						.purchaseCandidate(purchaseCandidate)
						.vendorProductInfo(vendorProductInfosByVendorId.get(purchaseCandidate.getVendorBPartnerId()))
						.datePromised(datePromised)
						.build())
				.collect(Collectors.toCollection(ArrayList::new));

		//
		// Create rows from vendor product info
		final Set<Integer> vendorIdsConsidered = rows.stream().map(PurchaseRow::getVendorBPartnerId).collect(ImmutableSet.toImmutableSet());
		vendorProductInfosByVendorId.values()
				.stream()
				.filter(vendorProductInfo -> !vendorIdsConsidered.contains(vendorProductInfo.getC_BPartner_ID())) // only if vendor was not already considered (i.e. there was no purchase candidate for it)
				.map(vendorProductInfo -> rowFromVendorProductInfoBuilder()
						.salesOrderId(salesOrderId)
						.salesOrderLineId(salesOrderLineId)
						.vendorProductInfo(vendorProductInfo)
						.datePromised(datePromised)
						.orgId(orgId)
						.warehouseId(warehouseId)
						.build())
				.forEach(rows::add);

		if (rows.isEmpty())
		{
			return null;
		}

		final JSONLookupValue product = createProductLookupValue(salesOrderLine.getM_Product_ID());
		final BigDecimal qtyToDeliver = salesOrderLine.getQtyOrdered().subtract(salesOrderLine.getQtyDelivered());
		final JSONLookupValue uom = createUOMLookupValueForProductId(product.getKeyAsInt());
		final PurchaseRow groupRow = PurchaseRow.builder()
				.rowId(PurchaseRowId.groupId(salesOrderLineId))
				.salesOrderId(salesOrderLine.getC_Order_ID())
				.rowType(PurchaseRowType.GROUP)
				.product(product)
				.uom(uom)
				.qtyToDeliver(qtyToDeliver)
				.datePromised(datePromised)
				.orgId(orgId)
				.warehouseId(warehouseId)
				.includedRows(rows)
				.readonly(true) // grouping lines are always readonly
				.build();

		return groupRow;
	}

	private Map<Integer, I_C_BPartner_Product> getVendorProductInfosIndexedByVendorId(final I_C_OrderLine salesOrderLine)
	{
		final int productId = salesOrderLine.getM_Product_ID();
		final int adOrgId = salesOrderLine.getAD_Org_ID();

		return partnerProductDAO
				.retrieveAllVendors(productId, adOrgId)
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(I_C_BPartner_Product::getC_BPartner_ID));
	}

	@Builder(builderMethodName = "rowFromPurchaseCandidateBuilder", builderClassName = "RowFromPurchaseCandidateBuilder")
	private PurchaseRow buildRowFromPurchaseCandidate(
			@NonNull final PurchaseCandidate purchaseCandidate,
			@Nullable final I_C_BPartner_Product vendorProductInfo,
			@NotNull final Date datePromised)
	{
		final int bpartnerId = purchaseCandidate.getVendorBPartnerId();
		final JSONLookupValue vendorBPartner = createBPartnerLookupValue(bpartnerId);
		final JSONLookupValue product;
		if (vendorProductInfo != null)
		{
			product = createProductLookupValue(vendorProductInfo.getM_Product_ID(), vendorProductInfo.getProductNo(), vendorProductInfo.getProductName());
		}
		else
		{
			product = createProductLookupValue(purchaseCandidate.getProductId());
		}
		final JSONLookupValue uom = createUOMLookupValueForProductId(product.getKeyAsInt());

		return PurchaseRow.builder()
				.rowId(PurchaseRowId.lineId(purchaseCandidate.getSalesOrderLineId(), bpartnerId))
				.salesOrderId(purchaseCandidate.getSalesOrderId())
				.rowType(PurchaseRowType.LINE)
				.product(product)
				.uom(uom)
				.qtyToPurchase(purchaseCandidate.getQtyRequired())
				.datePromised(datePromised)
				.vendorBPartner(vendorBPartner)
				.purcaseCandidateRepoId(purchaseCandidate.getRepoId())
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				.readonly(purchaseCandidate.isProcessedOrLocked())
				.build();
	}

	@Builder(builderMethodName = "rowFromVendorProductInfoBuilder", builderClassName = "RowFromVendorProductInfoBuilder")
	private static PurchaseRow buildRowFromVendorProductInfo(
			final int salesOrderId,
			final int salesOrderLineId,
			@Nullable final I_C_BPartner_Product vendorProductInfo,
			@NotNull final Date datePromised,
			final int orgId,
			final int warehouseId)
	{
		final int bpartnerId = vendorProductInfo.getC_BPartner_ID();
		final JSONLookupValue vendorBPartner = createBPartnerLookupValue(bpartnerId);
		final JSONLookupValue product = createProductLookupValue(vendorProductInfo.getM_Product_ID(), vendorProductInfo.getProductNo(), vendorProductInfo.getProductName());
		final JSONLookupValue uom = createUOMLookupValueForProductId(product.getKeyAsInt());

		return PurchaseRow.builder()
				.rowId(PurchaseRowId.lineId(salesOrderLineId, bpartnerId))
				.salesOrderId(salesOrderId)
				.rowType(PurchaseRowType.LINE)
				.product(product)
				.uom(uom)
				.datePromised(datePromised)
				.vendorBPartner(vendorBPartner)
				.orgId(orgId)
				.warehouseId(warehouseId)
				.readonly(false)
				.build();
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

	private static JSONLookupValue createUOMLookupValueForProductId(final int productId)
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
		return JSONLookupValue.of(uom.getC_UOM_ID(), uom.getUOMSymbol());
	}

}
