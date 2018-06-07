package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.adempiere.mm.attributes.api.AttributesKeys;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.money.Currency;
import de.metas.money.MoneyService;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
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
	@Builder(builderMethodName = "lineRowBuilder", builderClassName = "LineRowBuilder")
	private PurchaseRow createLineRow(
			@NonNull final PurchaseCandidatesGroup purchaseCandidatesGroup,
			@NonNull final PurchaseDemandId purchaseDemandId,
			@Nullable final Currency convertAmountsToCurrency,
			@NotNull final LocalDateTime datePromised)
	{
		final PurchaseProfitInfo profitInfo = convertToCurrencyIfPossible(purchaseCandidatesGroup.getProfitInfo(), convertAmountsToCurrency);

		return PurchaseRow.lineRowBuilder()
				.lookups(lookups)
				.purchaseCandidatesGroup(purchaseCandidatesGroup.changeProfitInfo(profitInfo))
				.purchaseDemandId(purchaseDemandId)
				.datePromised(datePromised)
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
		return PurchaseRow.groupRowBuilder()
				.lookups(lookups)
				.demand(demand)
				.qtyAvailableToPromise(getQtyAvailableToPromise(demand))
				.includedRows(rows)
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
