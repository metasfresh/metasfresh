package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.util.Check;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.money.Currency;
import de.metas.money.MoneyService;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.quantity.Quantity;
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
	private final AvailableToPromiseRepository availableToPromiseRepository;
	private final MoneyService moneyService;
	private final PurchaseRowLookups lookups;

	public PurchaseRowFactory(
			@NonNull final AvailableToPromiseRepository availableToPromiseRepository,
			@NonNull final MoneyService moneyService)
	{
		this.moneyService = moneyService;
		this.availableToPromiseRepository = availableToPromiseRepository;
		lookups = PurchaseRowLookups.newInstance();
	}

	/**
	 * Create a purchase row from a {@link PurchaseCandidate}.
	 */
	@Builder(builderMethodName = "rowFromPurchaseCandidateBuilder", builderClassName = "RowFromPurchaseCandidateBuilder")
	private PurchaseRow buildRowFromPurchaseCandidate(
			@NonNull final PurchaseCandidate purchaseCandidate,
			@NonNull final Currency currencyOfParentRow,
			@Nullable final VendorProductInfo vendorProductInfo,
			@NotNull final LocalDateTime datePromised)
	{
		final BPartnerId vendorId = purchaseCandidate.getVendorId();
		final JSONLookupValue vendor = lookups.createBPartnerLookupValue(vendorId);

		final ProductId productId;
		final JSONLookupValue product;
		if (vendorProductInfo != null)
		{
			productId = vendorProductInfo.getProductId();
			product = lookups.createProductLookupValue(productId, vendorProductInfo.getProductNo(), vendorProductInfo.getProductName());
		}
		else
		{
			productId = purchaseCandidate.getProductId();
			product = lookups.createProductLookupValue(productId);
		}
		final String uom = lookups.createUOMLookupValueForProductId(productId);

		final int processedPurchaseCandidateId = purchaseCandidate.isProcessed()
				? purchaseCandidate.getPurchaseCandidateId()
				: 0;

		final PurchaseDemandId demandId = purchaseCandidate.getSalesOrderLineIdAsDemandId();
		final PurchaseProfitInfo profitInfo = convertToCurrency(purchaseCandidate.getProfitInfo(), currencyOfParentRow);

		return PurchaseRow.builder()
				.rowId(PurchaseRowId.lineId(demandId, vendorId, processedPurchaseCandidateId))
				.rowType(PurchaseRowType.LINE)
				.product(product)
				.salesNetPrice(profitInfo.getSalesNetPrice().getValue())
				.purchaseNetPrice(profitInfo.getPurchaseNetPrice().getValue())
				.profitPercent(profitInfo.getProfitPercent().roundToHalf(RoundingMode.HALF_UP).getValueAsBigDecimal())
				.uomOrAvailablility(uom)
				.qtyToPurchase(purchaseCandidate.getQtyToPurchase())
				.purchasedQty(purchaseCandidate.getPurchasedQty())
				.datePromised(datePromised)
				.vendorBPartner(vendor)
				.purchaseCandidateId(purchaseCandidate.getPurchaseCandidateId())
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				.readonly(purchaseCandidate.isProcessedOrLocked())
				.build();
	}

	private PurchaseProfitInfo convertToCurrency(final PurchaseProfitInfo profitInfo, final Currency currencyTo)
	{
		return profitInfo.toBuilder()
				.salesNetPrice(moneyService.convertMoneyToCurrency(profitInfo.getSalesNetPrice(), currencyTo))
				.purchaseNetPrice(moneyService.convertMoneyToCurrency(profitInfo.getPurchaseNetPrice(), currencyTo))
				.purchaseGrossPrice(moneyService.convertMoneyToCurrency(profitInfo.getPurchaseGrossPrice(), currencyTo))
				.build();
	}

	public PurchaseRow createGroupRow(final PurchaseDemand demand, final List<PurchaseRow> rows)
	{
		final JSONLookupValue product = lookups.createProductLookupValue(demand.getProductId());
		final JSONLookupValue attributeSetInstance = lookups.createASILookupValue(demand.getAttributeSetInstanceId());

		final Quantity qtyToDeliver = demand.getQtyToDeliver();
		final BigDecimal qtyAvailableToPromise = getQtyAvailableToPromise(demand);

		return PurchaseRow.builder()
				.rowId(PurchaseRowId.groupId(demand.getId()))
				.rowType(PurchaseRowType.GROUP)
				.product(product)
				.attributeSetInstance(attributeSetInstance)
				.uomOrAvailablility(qtyToDeliver.getUOMSymbol())
				.qtyAvailableToPromise(qtyAvailableToPromise)
				.qtyToDeliver(qtyToDeliver.getQty())
				.datePromised(demand.getDatePromised())
				.orgId(demand.getOrgId())
				.warehouseId(demand.getWarehouseId())
				.includedRows(rows)
				.readonly(true) // grouping lines are always readonly
				.build();
	}

	private BigDecimal getQtyAvailableToPromise(final PurchaseDemand demand)
	{
		return availableToPromiseRepository.retrieveAvailableStockQtySum(AvailableToPromiseQuery.builder()
				.productId(demand.getProductId().getRepoId())
				.date(demand.getPreparationDate())
				.storageAttributesKey(AttributesKeys
						.createAttributesKeyFromASIStorageAttributes(demand.getAttributeSetInstanceId().getRepoId())
						.orElse(AttributesKey.ALL))
				.build());
	}

	@Builder(builderMethodName = "rowFromAvailabilityResultBuilder", builderClassName = "RowFromAvailabilityResultBuilder")
	private PurchaseRow buildRowFromFromAvailabilityResult(
			@NonNull final PurchaseRow parentRow,
			@NonNull final AvailabilityResult availabilityResult)
	{
		final String availabilityText = !Check.isEmpty(availabilityResult.getAvailabilityText(), true)
				? availabilityResult.getAvailabilityText()
				: availabilityResult.getType().translate();

		return parentRow.toBuilder()
				.rowId(parentRow.getRowId().withAvailabilityAndRandomDistinguisher(availabilityResult.getType()))
				.rowType(PurchaseRowType.AVAILABILITY_DETAIL)
				.qtyToPurchase(availabilityResult.getQty())
				.readonly(true)
				.uomOrAvailablility(availabilityText)
				.datePromised(availabilityResult.getDatePromised())
				.build();
	}

	@Builder(builderMethodName = "rowFromThrowableBuilder", builderClassName = "RowFromThrowableBuilder")
	private PurchaseRow buildRowFromFromThrowable(
			@NonNull final PurchaseRow parentRow,
			@NonNull final Throwable throwable)
	{
		return parentRow.toBuilder()
				.rowId(parentRow.getRowId().withAvailabilityAndRandomDistinguisher(Type.NOT_AVAILABLE))
				.rowType(PurchaseRowType.AVAILABILITY_DETAIL)
				.qtyToPurchase(BigDecimal.ZERO)
				.readonly(true)
				.uomOrAvailablility(Util
						.coalesce(
								throwable.getLocalizedMessage(),
								throwable.getMessage(),
								throwable.getClass().getName()))
				.datePromised(null)
				.build();
	}
}
