package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.util.Check;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.money.Currency;
import de.metas.money.MoneyService;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.quantity.Quantity;
import de.metas.ui.web.window.datatypes.LookupValue;
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
	// services
	private static final Logger logger = LogManager.getLogger(PurchaseRowFactory.class);
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
			@NonNull final PurchaseDemandId purchaseDemandId,
			@Nullable final Currency convertAmountsToCurrency,
			@NotNull final LocalDateTime datePromised)
	{
		final BPartnerId vendorId = purchaseCandidate.getVendorId();
		final LookupValue vendor = lookups.createBPartnerLookupValue(vendorId);

		final ProductId productId;
		final LookupValue product;
		final VendorProductInfo vendorProductInfo = purchaseCandidate.getVendorProductInfo();
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

		final PurchaseCandidateId processedPurchaseCandidateId = purchaseCandidate.isProcessed()
				? purchaseCandidate.getId()
				: null;

		final PurchaseProfitInfo profitInfo = convertToCurrencyIfPossible(purchaseCandidate.getProfitInfo(), convertAmountsToCurrency);

		return PurchaseRow.builder()
				.rowId(PurchaseRowId.lineId(purchaseDemandId, vendorId, processedPurchaseCandidateId))
				.rowType(PurchaseRowType.LINE)
				.product(product)
				.profitInfo(profitInfo)
				.uomOrAvailablility(uom)
				.qtyToPurchase(purchaseCandidate.getQtyToPurchase())
				.purchasedQty(purchaseCandidate.getPurchasedQty())
				.datePromised(datePromised)
				.vendorBPartner(vendor)
				.purchaseCandidateId(purchaseCandidate.getId())
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				.readonly(purchaseCandidate.isProcessedOrLocked())
				.build();
	}

	private PurchaseProfitInfo convertToCurrencyIfPossible(final PurchaseProfitInfo profitInfo, final Currency currencyTo)
	{
		if (profitInfo == null)
		{
			return null;
		}
		if (currencyTo == null)
		{
			return profitInfo;
		}

		try
		{
			return profitInfo.toBuilder()
					.salesNetPrice(profitInfo.getSalesNetPrice().map(price -> moneyService.convertMoneyToCurrency(price, currencyTo)))
					.purchaseNetPrice(moneyService.convertMoneyToCurrency(profitInfo.getPurchaseNetPrice(), currencyTo))
					.purchaseGrossPrice(moneyService.convertMoneyToCurrency(profitInfo.getPurchaseGrossPrice(), currencyTo))
					.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting {} to {}. Returning profitInfo as is.", profitInfo, currencyTo, ex);
			return profitInfo;
		}
	}

	public PurchaseRow createGroupRow(final PurchaseDemand demand, final List<PurchaseRow> rows)
	{
		final LookupValue product = lookups.createProductLookupValue(demand.getProductId());
		final LookupValue attributeSetInstance = lookups.createASILookupValue(demand.getAttributeSetInstanceId());

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
				.uomOrAvailablility(AdempiereException.extractMessage(throwable))
				.datePromised(null)
				.build();
	}
}
