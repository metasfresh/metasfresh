package de.metas.ui.web.order.products_proposal.view;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.BPartnerProductStats;
import de.metas.bpartner.product.stats.BPartnerProductStats.LastInvoiceInfo;
import de.metas.bpartner.product.stats.BPartnerProductStatsService;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.order.OrderId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.product.ProductId;
import de.metas.ui.web.order.products_proposal.model.ProductProposalPrice;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsData;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsLoader;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_AddProductFromBasePriceList;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ShowProductsSoldToOtherCustomers;
import de.metas.ui.web.order.products_proposal.service.Order;
import de.metas.ui.web.order.products_proposal.service.OrderProductProposalsService;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.ClassViewColumnOverrides;
import de.metas.ui.web.window.datatypes.DocumentIdIntSequence;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
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

@ViewFactory(windowId = OtherSalePricesProductsProposalViewFactory.WINDOW_ID_STRING)
public class OtherSalePricesProductsProposalViewFactory extends ProductsProposalViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "otherSalePricesProductsProposal";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	// services
	private final BPartnerProductStatsService bpartnerProductStatsService;
	private final OrderProductProposalsService orderProductProposalsService;
	private final MoneyService moneyService;

	protected OtherSalePricesProductsProposalViewFactory(
			@NonNull final BPartnerProductStatsService bpartnerProductStatsService,
			@NonNull final OrderProductProposalsService orderProductProposalsService,
			@NonNull final MoneyService moneyService)
	{
		super(WINDOW_ID);

		this.bpartnerProductStatsService = bpartnerProductStatsService;
		this.orderProductProposalsService = orderProductProposalsService;
		this.moneyService = moneyService;
	}

	@Override
	protected ViewLayout createViewLayout(final ViewLayoutKey key)
	{
		return ViewLayout.builder()
				.setWindowId(key.getWindowId())
				.setCaption(getProcessCaption(WEBUI_ProductsProposal_ShowProductsSoldToOtherCustomers.class).orElse(null))
				.allowViewCloseAction(ViewCloseAction.BACK)
				.addElementsFromViewRowClassAndFieldNames(
						ProductsProposalRow.class,
						key.getViewDataType(),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_BPartner),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Product),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Price),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Currency),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_LastSalesInvoiceDate))
				.build();
	}

	public final ProductsProposalView createView(
			@NonNull final ProductsProposalView parentView,
			@NonNull final List<ProductsProposalRow> selectedRows)
	{
		final ImmutableSet<ProductId> productIds = selectedRows
				.stream()
				.map(ProductsProposalRow::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		final CurrencyId currencyId;

		final Optional<OrderId> orderId = parentView.getOrderId();

		if (orderId.isPresent())
		{
			final Order order = orderProductProposalsService.getOrderById(orderId.get());
			currencyId = order.getCurrency().getId();
		}
		else
		{
			currencyId = null;
		}

		final ProductsProposalRowsData rowsData = RowsLoader.builder()
				.bpartnerProductStatsService(bpartnerProductStatsService)
				.moneyService(moneyService)
				//
				.excludeBPartnerId(parentView.getBpartnerId().orElse(null))
				.productIds(productIds)
				.curencyId(currencyId)
				//
				.build()
				.load();

		final ProductsProposalView view = ProductsProposalView.builder()
				.windowId(getWindowId())
				.rowsData(rowsData)
				.processes(getRelatedProcessDescriptors())
				.initialViewId(parentView.getInitialViewId())
				.build();

		put(view);

		return view;
	}

	@Override
	protected ProductsProposalRowsLoader createRowsLoaderFromRecord(final TableRecordReference recordRef)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected List<RelatedProcessDescriptor> getRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptor(WEBUI_ProductsProposal_AddProductFromBasePriceList.class));
	}

	private static class RowsLoader
	{
		private final BPartnerProductStatsService bpartnerProductStatsService;
		private final MoneyService moneyService;
		private final LookupDataSource bpartnerLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
		private final LookupDataSource productLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_M_Product.Table_Name);

		private final DocumentIdIntSequence nextRowIdSequence = DocumentIdIntSequence.newInstance();

		private final BPartnerId excludeBPartnerId;

		private final Set<ProductId> productIds;

		private final CurrencyId currencyId;

		@Builder
		private RowsLoader(
				@NonNull final BPartnerProductStatsService bpartnerProductStatsService,
				@NonNull final MoneyService moneyService,
				//
				@Nullable final BPartnerId excludeBPartnerId,
				@NonNull final Set<ProductId> productIds,
				@Nullable CurrencyId curencyId)
		{
			Check.assumeNotEmpty(productIds, "productIds is not empty");

			this.bpartnerProductStatsService = bpartnerProductStatsService;
			this.moneyService = moneyService;

			this.excludeBPartnerId = excludeBPartnerId;
			this.productIds = ImmutableSet.copyOf(productIds);

			this.currencyId = curencyId;
		}

		public ProductsProposalRowsData load()
		{
			final List<ProductsProposalRow> rows = bpartnerProductStatsService.getByProductIds(productIds, currencyId)
					.stream()
					// .filter(stats -> excludeBPartnerId == null || !BPartnerId.equals(stats.getBpartnerId(), excludeBPartnerId))
					.map(this::toProductsProposalRowOrNull)
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(ProductsProposalRow::getProductName)
							.thenComparing(Comparator.comparing(ProductsProposalRow::getLastSalesInvoiceDate)).reversed())
					.collect(ImmutableList.toImmutableList());

			return ProductsProposalRowsData.builder()
					.nextRowIdSequence(nextRowIdSequence)
					.rows(rows)
					.soTrx(SOTrx.SALES)
					.currencyId(currencyId)
					.build();
		}

		private ProductsProposalRow toProductsProposalRowOrNull(@NonNull final BPartnerProductStats stats)
		{
			final LastInvoiceInfo lastSalesInvoice = stats.getLastSalesInvoice();
			if (lastSalesInvoice == null)
			{
				return null;
			}

			return ProductsProposalRow.builder()
					.id(nextRowIdSequence.nextDocumentId())
					.bpartner(bpartnerLookup.findById(stats.getBpartnerId()))
					.product(productLookup.findById(stats.getProductId()))
					.price(ProductProposalPrice.builder()
							.priceListPrice(moneyService.toAmount(lastSalesInvoice.getPrice()))
							.build())
					.lastShipmentDays(stats.getLastShipmentInDays())
					.lastSalesInvoiceDate(lastSalesInvoice.getInvoiceDate())
					.build();
		}
	}
}
