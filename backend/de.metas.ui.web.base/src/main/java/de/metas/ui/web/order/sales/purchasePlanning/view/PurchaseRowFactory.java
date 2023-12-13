package de.metas.ui.web.order.sales.purchasePlanning.view;

import de.metas.logging.LogManager;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.money.CurrencyId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoService;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

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
	private final PurchaseProfitInfoService purchaseProfitInfoService;
	private final PurchaseRowLookups lookups;
	private final IProductBL productsBL = Services.get(IProductBL.class);

	public PurchaseRowFactory(
			@NonNull final AvailableToPromiseRepository availableToPromiseRepository,
			@NonNull final PurchaseProfitInfoService purchaseProfitInfoService)
	{
		this.availableToPromiseRepository = availableToPromiseRepository;
		this.purchaseProfitInfoService = purchaseProfitInfoService;

		lookups = PurchaseRowLookups.newInstance();
	}

	/**
	 * Create a purchase row from a {@link PurchaseCandidate}.
	 */
	@Builder(builderMethodName = "lineRowBuilder", builderClassName = "LineRowBuilder")
	private PurchaseRow createLineRow(
			@NonNull final PurchaseCandidatesGroup purchaseCandidatesGroup,
			@Nullable final CurrencyId convertAmountsToCurrencyId)
	{
		final PurchaseProfitInfo profitInfo = convertToCurrencyIfPossible(
				purchaseCandidatesGroup.getProfitInfoOrNull(),
				convertAmountsToCurrencyId);

		return PurchaseRow.lineRowBuilder()
				.purchaseProfitInfoService(purchaseProfitInfoService)
				.lookups(lookups)
				.purchaseCandidatesGroup(purchaseCandidatesGroup.withProfitInfo(profitInfo))
				.build();
	}

	private PurchaseProfitInfo convertToCurrencyIfPossible(
			final PurchaseProfitInfo profitInfo,
			final CurrencyId currencyIdTo)
	{
		if (profitInfo == null)
		{
			return null;
		}
		if (currencyIdTo == null)
		{
			return profitInfo;
		}

		try
		{
			return purchaseProfitInfoService.convertToCurrency(profitInfo, currencyIdTo);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting {} to {}. Returning profitInfo as is.", profitInfo, currencyIdTo, ex);
			return profitInfo;
		}
	}

	public PurchaseRow createGroupRow(
			@NonNull final PurchaseDemand demand,
			@NonNull final List<PurchaseRow> rows)
	{
		return PurchaseRow.groupRowBuilder()
				.lookups(lookups)
				.demand(demand)
				.qtyAvailableToPromise(getQtyAvailableToPromise(demand))
				.includedRows(rows)
				.build();
	}

	private Quantity getQtyAvailableToPromise(final PurchaseDemand demand)
	{
		final ProductId productId = demand.getProductId();

		final AttributesKey attributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(demand.getAttributeSetInstanceId())
				.orElse(AttributesKey.ALL);

		final AvailableToPromiseQuery query = AvailableToPromiseQuery.builder()
				.productId(productId.getRepoId())
				.date(demand.getSalesPreparationDate())
				.storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(attributesKey))
				.build();

		final BigDecimal qtyAvailableToPromise = availableToPromiseRepository.retrieveAvailableStockQtySum(query);

		final I_C_UOM uom = productsBL.getStockUOM(productId);

		return Quantity.of(qtyAvailableToPromise, uom);
	}

	@Builder(builderMethodName = "availabilityDetailSuccessBuilder", builderClassName = "AvailabilityDetailSuccessBuilder")
	private PurchaseRow buildRowFromFromAvailabilityResult(
			@NonNull final PurchaseRow lineRow,
			@NonNull final AvailabilityResult availabilityResult)
	{
		return PurchaseRow.availabilityDetailSuccessBuilder()
				.lineRow(lineRow)
				.availabilityResult(availabilityResult)
				.build();
	}

	@Builder(builderMethodName = "availabilityDetailErrorBuilder", builderClassName = "AvailabilityDetailErrorBuilder")
	private PurchaseRow buildRowFromFromThrowable(
			@NonNull final PurchaseRow lineRow,
			@NonNull final Throwable throwable)
	{
		return PurchaseRow.availabilityDetailErrorBuilder()
				.lineRow(lineRow)
				.throwable(throwable)
				.build();
	}
}
