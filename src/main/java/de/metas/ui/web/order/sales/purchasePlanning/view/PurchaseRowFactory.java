package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.translate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.apache.commons.lang3.RandomStringUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import de.metas.lang.Percent;
import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.money.Currency;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
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

	public PurchaseRowFactory(
			@NonNull final AvailableToPromiseRepository availableToPromiseRepository,
			@NonNull final MoneyService moneyService)
	{
		this.moneyService = moneyService;
		this.availableToPromiseRepository = availableToPromiseRepository;
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
		final BPartnerId bpartnerId = purchaseCandidate.getVendorBPartnerId();
		final ProductId productId;
		final JSONLookupValue vendorBPartner = createBPartnerLookupValue(bpartnerId);
		final JSONLookupValue product;
		if (vendorProductInfo != null)
		{
			productId = vendorProductInfo.getProductId();
			product = createProductLookupValue(
					productId,
					vendorProductInfo.getProductNo(),
					vendorProductInfo.getProductName());
		}
		else
		{
			productId = purchaseCandidate.getProductId();
			product = createProductLookupValue(productId);
		}
		final String uom = createUOMLookupValueForProductId(product.getKeyAsInt());

		final int processedPurchaseCandidateId = purchaseCandidate.isProcessed()
				? purchaseCandidate.getPurchaseCandidateId()
				: 0;

		final PurchaseDemandId demandId = purchaseCandidate.getSalesOrderLineIdAsDemandId();

		final PurchaseProfitInfo profitInfo = purchaseCandidate.getProfitInfo();
		final Money purchasePriceActual = moneyService.convertMoneyToCurrency(profitInfo.getPurchasePriceActual(), currencyOfParentRow);
		final Money customerPriceGrossProfit = moneyService.convertMoneyToCurrency(profitInfo.getCustomerPriceGrossProfit(), currencyOfParentRow);
		final Money priceGrossProfit = moneyService.convertMoneyToCurrency(profitInfo.getPriceGrossProfit(), currencyOfParentRow);

		final Percent percentGrossProfit = Percent
				.ofDelta(priceGrossProfit.getValue(), customerPriceGrossProfit.getValue())
				.roundToHalf(RoundingMode.HALF_UP);

		return PurchaseRow.builder()
				.rowId(PurchaseRowId.lineId(demandId, bpartnerId, processedPurchaseCandidateId))
				.rowType(PurchaseRowType.LINE)
				.product(product)
				.purchasePriceActual(purchasePriceActual.getValue())
				.customerPriceGrossProfit(customerPriceGrossProfit.getValue())
				.percentGrossProfit(percentGrossProfit.getValueAsBigDecimal())
				.uomOrAvailablility(uom)
				.qtyToPurchase(purchaseCandidate.getQtyToPurchase())
				.purchasedQty(purchaseCandidate.getPurchasedQty())
				.datePromised(datePromised)
				.vendorBPartner(vendorBPartner)
				.purchaseCandidateId(purchaseCandidate.getPurchaseCandidateId())
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				.readonly(purchaseCandidate.isProcessedOrLocked())
				.build();
	}

	public PurchaseRow createGroupRow(final PurchaseDemand demand, final List<PurchaseRow> rows)
	{
		final JSONLookupValue product = createProductLookupValue(demand.getProductId());
		final JSONLookupValue attributeSetInstance = createASILookupValue(demand.getAttributeSetInstanceId());

		final Quantity qtyToDeliver = demand.getQtyToDeliver();
		final LocalDateTime preparationDate = demand.getPreparationDate();

		final BigDecimal qtyAvailableToPromise = availableToPromiseRepository.retrieveAvailableStockQtySum(AvailableToPromiseQuery.builder()
				.productId(demand.getProductId().getRepoId())
				.date(preparationDate != null ? preparationDate : null)
				.storageAttributesKey(AttributesKeys
						.createAttributesKeyFromASIStorageAttributes(demand.getAttributeSetInstanceId().getRepoId())
						.orElse(AttributesKey.ALL))
				.build());

		final PurchaseRow groupRow = PurchaseRow.builder()
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
				.rowId(parentRow
						.getRowId()
						.withAvailability(availabilityResult.getType(), createRandomString()))
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

	private static String createRandomString()
	{
		final boolean includeLetters = true;
		final boolean includeNumbers = true;

		return RandomStringUtils.random(8, includeLetters, includeNumbers);
	}

	private static JSONLookupValue createProductLookupValue(final ProductId productId)
	{
		final String productValue = null;
		final String productName = null;
		return createProductLookupValue(productId, productValue, productName);
	}

	private static JSONLookupValue createProductLookupValue(
			final ProductId productId,
			final String productValue,
			final String productName)
	{
		if (productId == null)
		{
			return null;
		}

		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		if (product == null)
		{
			return JSONLookupValue.unknown(productId.getRepoId());
		}

		final String productValueEffective = !Check.isEmpty(productValue, true) ? productValue.trim() : product.getValue();
		final String productNameEffective = !Check.isEmpty(productName, true) ? productName.trim() : product.getName();
		final String displayName = productValueEffective + "_" + productNameEffective;
		return JSONLookupValue.of(product.getM_Product_ID(), displayName);
	}

	private static JSONLookupValue createASILookupValue(final AttributeSetInstanceId attributeSetInstanceId)
	{
		if (attributeSetInstanceId == null)
		{
			return null;
		}

		final I_M_AttributeSetInstance asi = loadOutOfTrx(attributeSetInstanceId.getRepoId(), I_M_AttributeSetInstance.class);
		if (asi == null)
		{
			return null;
		}

		String description = asi.getDescription();
		if (Check.isEmpty(description, true))
		{
			description = "<" + attributeSetInstanceId.getRepoId() + ">";
		}
		
		return JSONLookupValue.of(attributeSetInstanceId.getRepoId(), description);
	}

	private static JSONLookupValue createBPartnerLookupValue(final BPartnerId bpartnerId)
	{
		if (bpartnerId == null)
		{
			return null;
		}

		final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(bpartnerId);
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

		return createUOMLookupValue(uom);
	}

	private static String createUOMLookupValue(final I_C_UOM uom)
	{
		return translate(uom, I_C_UOM.class).getUOMSymbol();
	}

}
