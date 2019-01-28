package de.metas.ui.web.order.products_proposal.view;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import com.google.common.collect.ImmutableList;

import de.metas.currency.Amount;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class ProductsProposalRowsLoader
{
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);

	private final PriceListId priceListId;
	private final LocalDate date;
	private final OrderId orderId;

	private final String currencyCode;
	private final LookupDataSource productLookup;

	@Builder
	public ProductsProposalRowsLoader(
			@NonNull final PriceListId priceListId,
			@NonNull final LocalDate date,
			@Nullable final OrderId orderId)
	{
		this.priceListId = priceListId;
		this.date = date;

		final I_M_PriceList priceList = priceListsRepo.getById(priceListId);
		currencyCode = currenciesRepo.getISOCodeById(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;
		productLookup = lookupFactory.searchInTableLookup(I_M_Product.Table_Name);

		this.orderId = orderId;
	}

	public ProductsProposalRowsData load()
	{
		final PriceListVersionId priceListVersionId = priceListsRepo.retrievePriceListVersionId(priceListId, date);
		final ImmutableList<ProductsProposalRow> rows = priceListsRepo.retrieveProductPrices(priceListVersionId)
				.map(this::toProductsProposalRow)
				.collect(ImmutableList.toImmutableList());

		return ProductsProposalRowsData.builder()
				.rows(rows)
				.orderId(orderId)
				.build();
	}

	private ProductsProposalRow toProductsProposalRow(final I_M_ProductPrice record)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID());

		return ProductsProposalRow.builder()
				.id(DocumentId.of(record.getM_ProductPrice_ID()))
				.product(productLookup.findById(record.getM_Product_ID()))
				.asiDescription(attributeSetInstanceBL.getASIDescriptionById(asiId))
				.price(Amount.of(record.getPriceStd(), currencyCode))
				.qty(null)
				.lastShipmentDays(null) // TODO
				.build();
	}

}
