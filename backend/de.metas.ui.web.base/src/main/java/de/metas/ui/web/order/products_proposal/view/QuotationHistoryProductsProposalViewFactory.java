/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.order.products_proposal.view;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.document.IDocTypeDAO;
import de.metas.lang.SOTrx;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.product.ProductId;
import de.metas.ui.web.order.products_proposal.model.ProductProposalPrice;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsData;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsLoader;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_QuotationHistory;
import de.metas.ui.web.order.products_proposal.service.Order;
import de.metas.ui.web.order.products_proposal.service.OrderProductProposalsService;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.datatypes.DocumentIdIntSequence;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Incoterms;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.util.Comparator;
import java.util.List;

@ViewFactory(windowId = QuotationHistoryProductsProposalViewFactory.WINDOW_ID_STRING)
public class QuotationHistoryProductsProposalViewFactory extends ProductsProposalViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "quotationHistoryProductsProposal";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	private final OrderProductProposalsService orderProductProposalsService;

	protected QuotationHistoryProductsProposalViewFactory(@NonNull final OrderProductProposalsService orderProductProposalsService)
	{
		super(WINDOW_ID);
		this.orderProductProposalsService = orderProductProposalsService;
	}

	@Override
	protected ViewLayout createViewLayout(final ViewLayoutKey key)
	{
		return ViewLayout.builder()
				.setWindowId(key.getWindowId())
				.setCaption(getProcessCaption(WEBUI_ProductsProposal_QuotationHistory.class).orElse(null))
				.allowViewCloseAction(ViewCloseAction.BACK)
				.addElementsFromViewRowClassAndFieldNames(
						ProductsProposalRow.class,
						key.getViewDataType(),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_BPartner),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Product),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Price),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Currency),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_TermsOfDelivery),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_OfferDate))
				.setAllowOpeningRowDetails(false)
				.build();
	}

	@NonNull
	public final ProductsProposalView createView(
			@NonNull final ProductsProposalView parentView,
			@NonNull final List<ProductsProposalRow> selectedRows)
	{
		final List<Order> orders = getOrderAndOrderLines(parentView, selectedRows);

		final ProductsProposalRowsData rowsData = RowsLoader.builder()
				.orders(orders)
				.currencyBL(currencyBL)
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

	@NonNull
	private List<Order> getOrderAndOrderLines(
			@NonNull final ProductsProposalView parentView,
			@NonNull final List<ProductsProposalRow> selectedRows)
	{
		final ImmutableSet<ProductId> selectedProductIds = selectedRows
				.stream()
				.map(ProductsProposalRow::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		final BPartnerId selectedBPartnerId = parentView.getBpartnerId()
				.orElseThrow(() -> new AdempiereException("BPartner cannot be missing at this stage!"));

		final ClientAndOrgId clientAndOrgId = parentView.getOrderClientAndOrg()
				.orElseGet(() -> ClientAndOrgId.ofClientAndOrg(Env.getAD_Client_ID(), Env.getOrgId().getRepoId()));

		return orderProductProposalsService.getOrdersByQuery(clientAndOrgId, selectedBPartnerId, selectedProductIds);
	}

	@Override
	protected ProductsProposalRowsLoader createRowsLoaderFromRecord(final TableRecordReference recordRef)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected List<RelatedProcessDescriptor> getRelatedProcessDescriptors()
	{
		return ImmutableList.of();
	}

	private static class RowsLoader
	{
		private final LookupDataSource bpartnerLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_BPartner.Table_Name);
		private final LookupDataSource productLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_M_Product.Table_Name);
		private final LookupDataSource termsLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_Incoterms.Table_Name);

		private final DocumentIdIntSequence nextRowIdSequence = DocumentIdIntSequence.newInstance();

		private final List<Order> orders;
		private final ICurrencyBL currencyBL;

		@Builder
		private RowsLoader(
				@NonNull final List<Order> orders,
				@NonNull final ICurrencyBL currencyBL)
		{
			this.orders = orders;
			this.currencyBL = currencyBL;
		}

		@NonNull
		public ProductsProposalRowsData load()
		{
			final List<ProductsProposalRow> rows = orders
					.stream()
					.map(this::toProductsProposalRows)
					.flatMap(List::stream)
					.sorted(Comparator.comparing(ProductsProposalRow::getOfferDate).reversed())
					.collect(ImmutableList.toImmutableList());

			return ProductsProposalRowsData.builder()
					.nextRowIdSequence(nextRowIdSequence)
					.rows(rows)
					.soTrx(SOTrx.SALES)
					.build();
		}

		@NonNull
		private List<ProductsProposalRow> toProductsProposalRows(@NonNull final Order order)
		{
			return order.getLines()
					.stream()
					.map(orderLine -> {
						final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(orderLine.getCurrencyId());

						return ProductsProposalRow.builder()
								.id(nextRowIdSequence.nextDocumentId())
								.bpartner(bpartnerLookup.findById(order.getBpartnerId()))
								.product(productLookup.findById(orderLine.getProductId()))
								.price(ProductProposalPrice.builder()
											   .priceListPrice(Amount.of(orderLine.getPriceEntered(), currencyCode))
											   .build())
								.incoterms(termsLookup.findById(order.getIncoTermsId()))
								.offerDate(order.getDateOrdered().toLocalDate())
								// dev-note: READ-ONLY
								.viewEditorRenderModes(ImmutableMap.of())
								.build();
					})
					.collect(ImmutableList.toImmutableList());
		}
	}
}
