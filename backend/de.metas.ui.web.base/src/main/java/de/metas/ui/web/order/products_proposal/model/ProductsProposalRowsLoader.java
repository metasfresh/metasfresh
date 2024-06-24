/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.order.products_proposal.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.BPartnerProductStats;
import de.metas.bpartner.product.stats.BPartnerProductStatsService;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.ui.web.order.products_proposal.campaign_price.CampaignPriceProvider;
import de.metas.ui.web.order.products_proposal.campaign_price.CampaignPriceProviders;
import de.metas.ui.web.order.products_proposal.service.Order;
import de.metas.ui.web.order.products_proposal.service.OrderLine;
import de.metas.ui.web.order.products_proposal.service.OrderProductProposalsService;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewHeaderProperties.ViewHeaderPropertiesBuilder;
import de.metas.ui.web.view.ViewHeaderPropertiesGroup;
import de.metas.ui.web.view.ViewHeaderProperty;
import de.metas.ui.web.window.datatypes.DocumentIdIntSequence;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public final class ProductsProposalRowsLoader
{
	private static final Logger logger = LogManager.getLogger(ProductsProposalRowsLoader.class);

	// services
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
	private final BPartnerProductStatsService bpartnerProductStatsService;
	private final IHUPIItemProductBL packingMaterialsService = Services.get(IHUPIItemProductBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final CampaignPriceProvider campaignPriceProvider;
	private final OrderProductProposalsService orderProductProposalsService;
	private final LookupDataSource productLookup;
	private final DocumentIdIntSequence nextRowIdSequence = DocumentIdIntSequence.newInstance();

	private final ImmutableSet<PriceListVersionId> priceListVersionIds;
	private final Order order;
	private final BPartnerId bpartnerId;
	private final SOTrx soTrx;
	private final CurrencyId currencyId;
	private final ImmutableSet<ProductId> productIdsToExclude;
	private final Map<PriceListVersionId, CurrencyCode> currencyCodesByPriceListVersionId = new HashMap<>();
	private Map<ProductPriceId, OrderLine> bestMatchingProductPriceIdToOrderLine;

	@Builder
	private ProductsProposalRowsLoader(
			@NonNull final BPartnerProductStatsService bpartnerProductStatsService,
			@Nullable final CampaignPriceProvider campaignPriceProvider,
			@Nullable final OrderProductProposalsService orderProductProposalsService,
			//
			@NonNull @Singular final ImmutableSet<PriceListVersionId> priceListVersionIds,
			@Nullable final Order order,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final SOTrx soTrx,
			@Nullable final CurrencyId currencyId,
			@Nullable final Set<ProductId> productIdsToExclude)
	{
		Check.assumeNotEmpty(priceListVersionIds, "priceListVersionIds is not empty");

		this.bpartnerProductStatsService = bpartnerProductStatsService;
		this.campaignPriceProvider = campaignPriceProvider != null ? campaignPriceProvider : CampaignPriceProviders.none();
		this.orderProductProposalsService = orderProductProposalsService;

		productLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_M_Product.Table_Name);

		this.priceListVersionIds = priceListVersionIds;

		this.order = order;
		this.bpartnerId = bpartnerId;
		this.soTrx = soTrx;
		this.currencyId = currencyId;
		this.productIdsToExclude = productIdsToExclude != null ? ImmutableSet.copyOf(productIdsToExclude) : ImmutableSet.of();
	}

	public ProductsProposalRowsData load()
	{
		List<ProductsProposalRow> rows = loadRows();
		logger.debug("loaded {} productsProposalRows for priceListVersionIds={}", rows.size(), priceListVersionIds);
		rows = updateLastShipmentDays(rows);

		final PriceListVersionId singlePriceListVersionId = priceListVersionIds.size() == 1 ? priceListVersionIds.iterator().next() : null;
		final PriceListVersionId basePriceListVersionId;
		if (singlePriceListVersionId != null)
		{
			final ZonedDateTime datePromised = order == null ? SystemTime.asZonedDateTime() : order.getDatePromised();
			basePriceListVersionId = priceListsRepo.getBasePriceListVersionIdForPricingCalculationOrNull(singlePriceListVersionId, datePromised);
			logger.debug("singlePriceListVersionId={} and datePromised={}; -> basePriceListVersionId={}", PriceListVersionId.toRepoId(singlePriceListVersionId), datePromised, PriceListVersionId.toRepoId(basePriceListVersionId));
		}
		else
		{
			basePriceListVersionId = null;
		}

		//
		final ViewHeaderPropertiesBuilder headerProperties = ViewHeaderProperties.builder();

		if (order != null)
		{
			logger.debug("order!=null; -> add bpartnerName={} to headerProperties", order.getBpartnerName());
			headerProperties
					.group(ViewHeaderPropertiesGroup.builder()
							.entry(ViewHeaderProperty.builder()
									.caption(msgBL.translatable("C_BPartner_ID"))
									.value(order.getBpartnerName())
									.build())
							.build());
		}

		return ProductsProposalRowsData.builder()
				.nextRowIdSequence(nextRowIdSequence)
				.campaignPriceProvider(campaignPriceProvider)
				.orderProductProposalsService(orderProductProposalsService)
				//
				.singlePriceListVersionId(singlePriceListVersionId)
				.basePriceListVersionId(basePriceListVersionId)
				.order(order)
				.bpartnerId(bpartnerId)
				.soTrx(soTrx)
				.currencyId(currencyId)
				.headerProperties(headerProperties.build())
				//
				.rows(rows)
				//
				.build();
	}

	private List<ProductsProposalRow> loadRows()
	{
		final ZoneId zoneId = orgDAO.getTimeZone(Env.getOrgId());
		final LocalDate currentDate = LocalDate.now(zoneId);

		return priceListVersionIds.stream()
				.flatMap(this::loadAndStreamRowsForPriceListVersionId)
				.sorted(Comparator.comparing(ProductsProposalRow::getSeqNo)
								.thenComparing(ProductsProposalRow::getProductName))
				.filter(p -> !productBL.isDiscontinuedAt(productsRepo.getById(p.getProductId()), currentDate))
				.collect(ImmutableList.toImmutableList());
	}

	private Stream<ProductsProposalRow> loadAndStreamRowsForPriceListVersionId(final PriceListVersionId priceListVersionId)
	{
		final List<I_M_ProductPrice> productPrices = priceListsRepo.retrieveProductPrices(priceListVersionId, productIdsToExclude)
				.map(productPriceRecord -> InterfaceWrapperHelper.create(productPriceRecord, I_M_ProductPrice.class))
				.collect(ImmutableList.toImmutableList());
		if(order != null && orderProductProposalsService != null)
		{
			bestMatchingProductPriceIdToOrderLine = orderProductProposalsService.findBestMatchesForOrderLineFromProductPrices(order, productPrices);
		}
		else
		{
			bestMatchingProductPriceIdToOrderLine = ImmutableMap.of();
		}
		return productPrices
				.stream()
				.map(this::toProductsProposalRowOrNull)
				.filter(Objects::nonNull);
	}

	@Nullable
	private ProductsProposalRow toProductsProposalRowOrNull(@NonNull final I_M_ProductPrice record)
	{
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final LookupValue product = productLookup.findById(productId);
		if (product == null || !product.isActive())
		{
			return null;
		}

		final HUPIItemProductId packingMaterialId = HUPIItemProductId.ofRepoIdOrNull(record.getM_HU_PI_Item_Product_ID());
		final ITranslatableString packingDescription = packingMaterialId != null
				? packingMaterialsService.getDisplayName(packingMaterialId)
				: TranslatableStrings.empty();

		final ProductProposalPrice currentProductProposalPrice = extractProductProposalPrice(record);

		final ProductPriceId productPriceId = ProductPriceId.ofRepoId(record.getM_ProductPrice_ID());
		return ProductsProposalRow.builder()
				.id(nextRowIdSequence.nextDocumentId())
				.product(product)
				.packingMaterialId(packingMaterialId)
				.packingDescription(packingDescription)
				.asiDescription(extractProductASIDescription(record))
				.asiId(OrderProductProposalsService.extractProductASI(record))
				.price(currentProductProposalPrice)
				.qty(null)
				.lastShipmentDays(null) // will be populated later
				.seqNo(record.getSeqNo())
				.productPriceId(productPriceId)
				.build()
				//
				.withExistingOrderLine(bestMatchingProductPriceIdToOrderLine.get(productPriceId));
	}

	private ProductASIDescription extractProductASIDescription(final I_M_ProductPrice record)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID());
		return ProductASIDescription.ofString(attributeSetInstanceBL.getASIDescriptionById(asiId));
	}

	private ProductProposalPrice extractProductProposalPrice(final I_M_ProductPrice record)
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(record.getM_PriceList_Version_ID());
		final Amount priceListPrice = Amount.of(record.getPriceStd(), getCurrencyCode(priceListVersionId));

		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final ProductProposalCampaignPrice campaignPrice = campaignPriceProvider.getCampaignPrice(productId).orElse(null);

		return ProductProposalPrice.builder()
				.priceListPrice(priceListPrice)
				.campaignPrice(campaignPrice)
				.build();
	}

	private CurrencyCode getCurrencyCode(final PriceListVersionId priceListVersionId)
	{
		return currencyCodesByPriceListVersionId.computeIfAbsent(priceListVersionId, this::retrieveCurrencyCode);
	}

	private CurrencyCode retrieveCurrencyCode(final PriceListVersionId priceListVersionId)
	{
		final I_M_PriceList priceList = priceListsRepo.getPriceListByPriceListVersionId(priceListVersionId);
		final CurrencyId currencyId = CurrencyId.ofRepoId(priceList.getC_Currency_ID());
		return currenciesRepo.getCurrencyCodeById(currencyId);
	}

	private List<ProductsProposalRow> updateLastShipmentDays(final List<ProductsProposalRow> rows)
	{
		final Set<ProductId> productIds = rows.stream().map(ProductsProposalRow::getProductId).collect(ImmutableSet.toImmutableSet());
		if (productIds.isEmpty())
		{
			return rows;
		}

		final Map<ProductId, BPartnerProductStats> statsByProductId = bpartnerProductStatsService.getByPartnerAndProducts(bpartnerId, productIds);

		return rows.stream()
				.map(row -> updateRowFromStats(row, statsByProductId.get(row.getProductId())))
				.collect(ImmutableList.toImmutableList());
	}

	private ProductsProposalRow updateRowFromStats(
			@NonNull final ProductsProposalRow row,
			@Nullable final BPartnerProductStats stats)
	{
		final Integer lastShipmentOrReceiptInDays = calculateLastShipmentOrReceiptInDays(stats);
		return row.withLastShipmentDays(lastShipmentOrReceiptInDays);
	}

	@Nullable
	private Integer calculateLastShipmentOrReceiptInDays(@Nullable final BPartnerProductStats stats)
	{
		if (stats == null)
		{
			return null;
		}
		else if (soTrx.isSales())
		{
			return stats.getLastShipmentInDays();
		}
		else
		{
			return stats.getLastReceiptInDays();
		}
	}
}
