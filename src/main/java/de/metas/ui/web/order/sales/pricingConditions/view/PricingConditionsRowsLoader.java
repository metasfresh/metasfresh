package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.conditions.PricingConditions;
import org.adempiere.pricing.conditions.PricingConditionsBreak;
import org.adempiere.pricing.conditions.PricingConditionsBreakQuery;
import org.adempiere.pricing.conditions.PricingConditionsBreak.PriceOverrideType;
import org.adempiere.pricing.conditions.service.IPricingConditionsRepository;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_PricingSystem;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.logging.LogManager;
import de.metas.product.IProductDAO;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
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
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);

	private static final Comparator<PricingConditionsRow> ROWS_SORTING = Comparator.<PricingConditionsRow, Integer> comparing(row -> row.isEditable() ? 0 : 1)
			.thenComparing(row -> row.getBpartner().getDisplayName())
			.thenComparing(row -> row.isCustomer() ? 0 : 1);

	private final LookupDataSource bpartnerLookup;
	private final LookupDataSource pricingSystemLookup;
	private final LookupDataSource paymentTermLookup;

	private final int salesOrderLineId;
	private final int targetBPartnerId;
	private final boolean isSOTrx;
	private final int adClientId;
	private final List<I_M_AttributeInstance> attributeInstances;
	private final int productId;
	private final int productCategoryId;
	private final BigDecimal qty;
	private final BigDecimal amt;
	private final DocumentFiltersList filters;

	private ImmutableSetMultimap<Integer, DiscountSchemaInfo> discountSchemasInfoByDiscountSchemaId; // lazy

	@Builder
	private PricingConditionsRowsLoader(
			final int salesOrderLineId,
			@NonNull final DocumentFiltersList filters)
	{
		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;
		bpartnerLookup = lookupFactory.searchInTableLookup(I_C_BPartner.Table_Name);
		pricingSystemLookup = lookupFactory.searchInTableLookup(I_M_PricingSystem.Table_Name);
		paymentTermLookup = lookupFactory.searchInTableLookup(I_C_PaymentTerm.Table_Name);

		this.salesOrderLineId = salesOrderLineId;
		final I_C_OrderLine salesOrderLine = InterfaceWrapperHelper.load(salesOrderLineId, I_C_OrderLine.class);
		targetBPartnerId = salesOrderLine.getC_BPartner_ID();
		isSOTrx = true;
		adClientId = salesOrderLine.getAD_Client_ID();
		attributeInstances = attributesRepo.retrieveAttributeInstances(salesOrderLine.getM_AttributeSetInstance_ID());
		productId = salesOrderLine.getM_Product_ID();
		productCategoryId = Services.get(IProductDAO.class).retrieveProductCategoryByProductId(productId);
		qty = salesOrderLine.getQtyOrdered();
		final BigDecimal price = salesOrderLine.getPriceActual();
		amt = qty.multiply(price);

		this.filters = filters;
	}

	public PricingConditionsRowData load()
	{
		final ImmutableList<PricingConditionsRow> rows = getAllDiscountSchemaIds()
				.stream()
				.map(this::findMatchingSchemaBreakOrNull)
				.filter(Predicates.notNull())
				.flatMap(this::createPricingConditionsRows)
				.sorted(ROWS_SORTING)
				.collect(ImmutableList.toImmutableList());

		final PricingConditionsRow editableRow = containsCurrentPricingConditionsRow(rows) ? null : createEditablePricingConditionsRow();

		return PricingConditionsRowData.builder()
				.editableRow(editableRow)
				.rows(rows)
				.salesOrderLineId(salesOrderLineId)
				.build()
				.filter(filters);
	}

	private LookupValue lookupBPartner(final int bpartnerId)
	{
		return bpartnerLookup.findById(bpartnerId);
	}

	private LookupValue lookupPricingSystem(final int pricingSystemId)
	{
		return pricingSystemLookup.findById(pricingSystemId);
	}

	private Set<Integer> getAllDiscountSchemaIds()
	{
		return getBPartnerIdsIndexedByDiscountSchemaId().keySet();
	}

	private Set<DiscountSchemaInfo> getDiscountSchemaInfos(final int discountSchemaId)
	{
		return getBPartnerIdsIndexedByDiscountSchemaId().get(discountSchemaId);
	}

	private ImmutableSetMultimap<Integer, DiscountSchemaInfo> getBPartnerIdsIndexedByDiscountSchemaId()
	{
		if (discountSchemasInfoByDiscountSchemaId == null)
		{
			final ImmutableSetMultimap.Builder<Integer, DiscountSchemaInfo> builder = ImmutableSetMultimap.builder();

			// Vendor Discount Schemas
			bpartnersRepo.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(adClientId, /* isSOTrx */false)
					.forEach((bpartnerId, discountSchemaId) -> builder.put(discountSchemaId, DiscountSchemaInfo.builder()
							.bpartnerId(bpartnerId)
							.discountSchemaId(discountSchemaId)
							.isSOTrx(false)
							.build()));

			// Customer Discount Schemas
			bpartnersRepo.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(adClientId, /* isSOTrx */true)
					.forEach((bpartnerId, discountSchemaId) -> builder.put(discountSchemaId, DiscountSchemaInfo.builder()
							.bpartnerId(bpartnerId)
							.discountSchemaId(discountSchemaId)
							.isSOTrx(true)
							.build()));

			discountSchemasInfoByDiscountSchemaId = builder.build();
		}
		return discountSchemasInfoByDiscountSchemaId;
	}

	private PricingConditionsBreak findMatchingSchemaBreakOrNull(final int discountSchemaId)
	{
		final PricingConditions pricingConditions = pricingConditionsRepo.getPricingConditionsById(discountSchemaId);
		return pricingConditions.pickApplyingBreak(PricingConditionsBreakQuery.builder()
				.attributeInstances(attributeInstances)
				.productId(productId)
				.productCategoryId(productCategoryId)
				.qty(qty)
				.amt(amt)
				.build());
	}

	private Stream<PricingConditionsRow> createPricingConditionsRows(final PricingConditionsBreak pricingConditionsBreak)
	{
		return getDiscountSchemaInfos(pricingConditionsBreak.getDiscountSchemaId())
				.stream()
				.map(discountSchemaInfo -> createPricingConditionsRow(pricingConditionsBreak, discountSchemaInfo));
	}

	private PricingConditionsRow createPricingConditionsRow(final PricingConditionsBreak pricingConditionsBreak, final DiscountSchemaInfo discountSchemaInfo)
	{
		return PricingConditionsRow.builder()
				.bpartner(lookupBPartner(discountSchemaInfo.getBpartnerId()))
				.customer(discountSchemaInfo.isSOTrx())
				.price(extractPrice(pricingConditionsBreak))
				.discount(pricingConditionsBreak.getDiscount())
				.paymentTerm(paymentTermLookup.findById(pricingConditionsBreak.getPaymentTermId()))
				.paymentTermLookup(paymentTermLookup)
				.editable(false)
				.discountSchemaId(pricingConditionsBreak.getDiscountSchemaId())
				.discountSchemaBreakId(pricingConditionsBreak.getDiscountSchemaBreakId())
				.build();
	}

	private Price extractPrice(final PricingConditionsBreak pricingConditionsBreak)
	{
		final PriceOverrideType priceOverride = pricingConditionsBreak.getPriceOverride();
		if (priceOverride == PriceOverrideType.NONE)
		{
			return Price.none();
		}
		else if (priceOverride == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			final LookupValue pricingSystem = lookupPricingSystem(pricingConditionsBreak.getBasePricingSystemId());
			if (pricingSystem == null)
			{
				logger.warn("Cannot extract pricing system from {}. Returning NO price", pricingConditionsBreak);
				return Price.none();
			}

			final BigDecimal basePriceAddAmt = pricingConditionsBreak.getBasePriceAddAmt();

			return Price.basePricingSystem(pricingSystem, basePriceAddAmt);
		}
		else if (priceOverride == PriceOverrideType.FIXED_PRICED)
		{
			return Price.fixedPrice(pricingConditionsBreak.getFixedPrice());
		}
		else
		{
			logger.warn("Unknown priceOverride={} of {}. Returning NO price", priceOverride, pricingConditionsBreak);
			return Price.none();
		}
	}

	private boolean containsCurrentPricingConditionsRow(final Collection<PricingConditionsRow> rows)
	{
		return rows.stream().anyMatch(this::isCurrentConditions);
	}

	private boolean isCurrentConditions(final PricingConditionsRow row)
	{
		return row.getBpartnerId() == targetBPartnerId
				&& row.isCustomer();
	}

	private PricingConditionsRow createEditablePricingConditionsRow()
	{
		final int discountSchemaId = bpartnerBL.getDiscountSchemaId(targetBPartnerId, isSOTrx);
		return PricingConditionsRow.builder()
				.discountSchemaId(discountSchemaId)
				.bpartner(lookupBPartner(targetBPartnerId))
				.customer(isSOTrx)
				.price(Price.fixedZeroPrice())
				.paymentTerm(null)
				.paymentTermLookup(paymentTermLookup)
				.discount(BigDecimal.ZERO)
				.editable(true)
				.build();
	}

	@lombok.Value
	@lombok.Builder
	private static class DiscountSchemaInfo
	{
		int discountSchemaId;
		int bpartnerId;
		boolean isSOTrx;
	}
}
