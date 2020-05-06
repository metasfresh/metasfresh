package de.metas.ui.web.order.pricingconditions.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.slf4j.Logger;

import java.util.Objects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerType;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.inout.IInOutDAO;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.conditions.PriceSpecification;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakMatchCriteria;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
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

class PricingConditionsRowsLoader
{
	// services
	private static final Logger logger = LogManager.getLogger(PricingConditionsRowsLoader.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);
	private final IInOutDAO inoutsRepo = Services.get(IInOutDAO.class);

	private static final Comparator<PricingConditionsRow> ROWS_SORTING = Comparator.<PricingConditionsRow, Integer> comparing(row -> row.isEditable() ? 0 : 1)
			.thenComparing(row -> row.getBpartnerDisplayName())
			.thenComparing(row -> row.isCustomer() ? 0 : 1);

	private final PricingConditionsRowLookups lookups;
	private final PricingConditionsBreaksExtractor pricingConditionsBreaksExtractor;
	private final BasePricingSystemPriceCalculator basePricingSystemPriceCalculator;
	private final DocumentFilterList filters;
	private final SourceDocumentLine sourceDocumentLine;

	private ImmutableSetMultimap<PricingConditionsId, PricingConditionsInfo> pricingConditionsInfoById; // lazy
	private final LoadingCache<LastInOutDateRequest, Optional<LocalDate>> lastInOutDates = CacheBuilder.newBuilder()
			.build(new CacheLoader<LastInOutDateRequest, Optional<LocalDate>>()
			{
				@Override
				public Optional<LocalDate> load(final LastInOutDateRequest key)
				{
					final LocalDate lastInOutDate = inoutsRepo.getLastInOutDate(key.getBpartnerId(), key.getProductId(), key.getSoTrx());
					return Optional.ofNullable(lastInOutDate);
				}

			});

	@Builder
	private PricingConditionsRowsLoader(
			@NonNull final PricingConditionsRowLookups lookups,
			@NonNull final PricingConditionsBreaksExtractor pricingConditionsBreaksExtractor,
			@NonNull final BasePricingSystemPriceCalculator basePricingSystemPriceCalculator,
			final DocumentFilterList filters,
			@Nullable final SourceDocumentLine sourceDocumentLine)
	{
		this.lookups = lookups;
		this.pricingConditionsBreaksExtractor = pricingConditionsBreaksExtractor;
		this.basePricingSystemPriceCalculator = basePricingSystemPriceCalculator;
		this.filters = filters != null ? filters : DocumentFilterList.EMPTY;
		this.sourceDocumentLine = sourceDocumentLine;
	}

	public PricingConditionsRowData load()
	{
		final Set<PricingConditionsId> pricingConditionsIds = getAllPricingConditionsId();

		final List<PricingConditionsRow> rows = pricingConditionsRepo.getPricingConditionsByIds(pricingConditionsIds)
				.stream()
				.flatMap(pricingConditionsBreaksExtractor::streamPricingConditionsBreaks)
				.filter(Objects::nonNull)
				.flatMap(this::createPricingConditionsRows)
				.sorted(ROWS_SORTING)
				.collect(ImmutableList.toImmutableList());

		final PricingConditionsRow editableRow = rows.stream()
				.filter(this::isCurrentConditions)
				.findFirst()
				.map(PricingConditionsRow::copyAndChangeToEditable)
				.orElseGet(this::createEditablePricingConditionsRowOrNull);

		return PricingConditionsRowData.builder()
				.editableRow(editableRow)
				.rows(rows)
				.orderLineId(sourceDocumentLine != null ? sourceDocumentLine.getOrderLineId() : null)
				.build()
				.filter(filters);
	}

	private Set<PricingConditionsId> getAllPricingConditionsId()
	{
		return getPricingConditionsInfosIndexedById().keySet();
	}

	private Set<PricingConditionsInfo> getPricingConditionsInfos(final PricingConditionsId pricingConditionId)
	{
		return getPricingConditionsInfosIndexedById().get(pricingConditionId);
	}

	private ImmutableSetMultimap<PricingConditionsId, PricingConditionsInfo> getPricingConditionsInfosIndexedById()
	{
		if (pricingConditionsInfoById == null)
		{
			final Stream<PricingConditionsInfo> vendorPricingConditions = streamPricingConditionsInfos(BPartnerType.VENDOR);
			final Stream<PricingConditionsInfo> customerPricingConditions = streamPricingConditionsInfos(BPartnerType.CUSTOMER);

			pricingConditionsInfoById = Stream
					.concat(vendorPricingConditions, customerPricingConditions)
					.collect(ImmutableSetMultimap.toImmutableSetMultimap(PricingConditionsInfo::getPricingConditionsId, Function.identity()));
		}
		return pricingConditionsInfoById;
	}

	private Stream<PricingConditionsInfo> streamPricingConditionsInfos(@NonNull final BPartnerType bpartnerType)
	{
		final Map<BPartnerId, Integer> discountSchemaIdsByBPartnerId = bpartnersRepo.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(bpartnerType);

		return discountSchemaIdsByBPartnerId.keySet()
				.stream()
				.map(lookups::lookupBPartner)
				.filter(Objects::nonNull)
				.map(bpartner -> PricingConditionsInfo.builder()
						.bpartner(bpartner)
						.pricingConditionsId(getPricingConditionsIdByBPartner(bpartner, discountSchemaIdsByBPartnerId))
						.bpartnerType(bpartnerType)
						.build());
	}

	private static final PricingConditionsId getPricingConditionsIdByBPartner(final LookupValue bpartner, final Map<BPartnerId, Integer> discountSchemaIdsByBPartnerId)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getIdAsInt());
		final Integer discountSchemaId = discountSchemaIdsByBPartnerId.get(bpartnerId);
		if (discountSchemaId == null)
		{
			return null;
		}

		return PricingConditionsId.ofRepoId(discountSchemaId);
	}

	private Stream<PricingConditionsRow> createPricingConditionsRows(final PricingConditionsBreak pricingConditionsBreak)
	{
		return getPricingConditionsInfos(pricingConditionsBreak.getPricingConditionsId())
				.stream()
				.map(pricingConditionsInfo -> createPricingConditionsRow(pricingConditionsBreak, pricingConditionsInfo));
	}

	private PricingConditionsRow createPricingConditionsRow(final PricingConditionsBreak pricingConditionsBreak, final PricingConditionsInfo pricingConditionsInfo)
	{
		final SOTrx soTrx = pricingConditionsInfo.getBpartnerType().getSOTrx();
		return PricingConditionsRow.builder()
				.lookups(lookups)
				.editable(false)
				//
				.bpartner(pricingConditionsInfo.getBpartner())
				.customer(soTrx.isSales())
				//
				.pricingConditionsId(pricingConditionsInfo.getPricingConditionsId())
				.pricingConditionsBreak(pricingConditionsBreak)
				.dateLastInOut(getLastInOutDate(pricingConditionsInfo.getBPartnerId(), soTrx, pricingConditionsBreak))
				.basePricingSystemPriceCalculator(basePricingSystemPriceCalculator)
				//
				.build();
	}

	private boolean isCurrentConditions(final PricingConditionsRow row)
	{
		if (sourceDocumentLine == null)
		{
			return false;
		}
		else if (sourceDocumentLine.getPricingConditionsBreakId() != null)
		{
			return sourceDocumentLine.getPricingConditionsBreakId().equals(row.getPricingConditionsBreak().getId());
		}
		else
		{
			return Objects.equals(row.getBpartnerId(), sourceDocumentLine.getBpartnerId())
					&& (sourceDocumentLine.getSoTrx().isSales() ? row.isCustomer() : row.isVendor());
		}
	}

	/**
	 * On-the-fly create a PricingConditionsRow using this instance's {@link #sourceDocumentLine}.
	 */
	private PricingConditionsRow createEditablePricingConditionsRowOrNull()
	{
		if (sourceDocumentLine == null)
		{
			return null;
		}

		final int discountSchemaId = bpartnerBL.getDiscountSchemaId(sourceDocumentLine.getBpartnerId(), sourceDocumentLine.getSoTrx());
		final PricingConditionsId pricingConditionsId = PricingConditionsId.ofRepoIdOrNull(discountSchemaId);

		final Money priceEntered = sourceDocumentLine.getPriceEntered();

		final PricingConditionsBreak pricingConditionsBreak = PricingConditionsBreak.builder()
				.id(null) // N/A
				.matchCriteria(PricingConditionsBreakMatchCriteria.builder()
						.breakValue(BigDecimal.ZERO)
						.productId(sourceDocumentLine.getProductId())
						.productCategoryId(sourceDocumentLine.getProductCategoryId())
						.build())
				.priceSpecification(PriceSpecification.fixedPrice(priceEntered))

				.paymentTermIdOrNull(sourceDocumentLine.getPaymentTermId())
				.discount(sourceDocumentLine.getDiscount())
				.dateCreated(null) // N/A; the PricingConditionsBreak hasn't been created (i.e. persisted on DB) yet
				.createdById(null)
				.build();

		return PricingConditionsRow.builder()
				.lookups(lookups)
				.editable(true)
				//
				.bpartner(lookups.lookupBPartner(sourceDocumentLine.getBpartnerId()))
				.customer(sourceDocumentLine.getSoTrx().isSales())
				//
				.pricingConditionsId(pricingConditionsId)
				.pricingConditionsBreak(pricingConditionsBreak)
				.basePricingSystemPriceCalculator(basePricingSystemPriceCalculator)
				//
				.dateLastInOut(getLastInOutDate(sourceDocumentLine.getBpartnerId(), sourceDocumentLine.getSoTrx(), pricingConditionsBreak))
				//
				.build();
	}

	private LocalDate getLastInOutDate(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final SOTrx soTrx,
			@NonNull final PricingConditionsBreak pricingConditionsBreak)
	{
		final ProductId productId = pricingConditionsBreak.getMatchCriteria().getProductId();
		if (productId == null)
		{
			return null;
		}

		final LastInOutDateRequest request = LastInOutDateRequest.builder()
				.bpartnerId(bpartnerId)
				.productId(productId)
				.soTrx(soTrx)
				.build();

		try
		{
			return lastInOutDates.get(request).orElse(null);
		}
		catch (ExecutionException ex)
		{
			logger.warn("Failed fetching last InOut date for {}. Returning null.", request, ex);
			return null;
		}
	}

	@lombok.Value
	@lombok.Builder
	private static class PricingConditionsInfo
	{
		@lombok.NonNull
		PricingConditionsId pricingConditionsId;
		@lombok.NonNull
		LookupValue bpartner;
		@lombok.NonNull
		BPartnerType bpartnerType;

		public BPartnerId getBPartnerId()
		{
			return BPartnerId.ofRepoId(bpartner.getIdAsInt());
		}
	}

	@FunctionalInterface
	public interface PricingConditionsBreaksExtractor
	{
		Stream<PricingConditionsBreak> streamPricingConditionsBreaks(PricingConditions pricingConditions);
	}

	@lombok.Value
	@lombok.Builder
	public static final class SourceDocumentLine
	{
		@Nullable
		OrderLineId orderLineId;
		@NonNull
		SOTrx soTrx;

		@NonNull
		BPartnerId bpartnerId;

		@NonNull
		ProductId productId;
		@NonNull
		ProductCategoryId productCategoryId;

		@NonNull
		Money priceEntered;

		@lombok.Builder.Default
		@NonNull
		Percent discount = Percent.ZERO;

		@Nullable
		PaymentTermId paymentTermId;

		@Nullable
		PricingConditionsBreakId pricingConditionsBreakId;
	}

	@lombok.Value
	@lombok.Builder
	private static final class LastInOutDateRequest
	{
		@NonNull
		BPartnerId bpartnerId;
		@NonNull
		ProductId productId;
		@NonNull
		SOTrx soTrx;
	}

	//
	//
	//
	//
	//

	public static class PricingConditionsRowsLoaderBuilder
	{
		public PricingConditionsRowData load()
		{
			return build().load();
		}
	}
}
