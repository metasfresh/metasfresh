package de.metas.ui.web.order.products_proposal.view;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.product.stats.BPartnerProductStatsService;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.pricing.PriceListVersionId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.order.products_proposal.filters.ProductsProposalViewFilter;
import de.metas.ui.web.order.products_proposal.filters.ProductsProposalViewFilters;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsData;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsLoader;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_AddProductFromBasePriceList;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ShowProductsToAddFromBasePriceList;
import de.metas.ui.web.order.products_proposal.service.OrderProductProposalsService;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Supplier;

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

@ViewFactory(windowId = BasePLVProductsProposalViewFactory.WINDOW_ID_STRING)
public class BasePLVProductsProposalViewFactory extends ProductsProposalViewFactoryTemplate
{
	private static final Logger logger = LogManager.getLogger(BasePLVProductsProposalViewFactory.class);

	public static final String WINDOW_ID_STRING = "basePLVProductsProposal";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final BPartnerProductStatsService bpartnerProductStatsService;
	private final OrderProductProposalsService orderProductProposalsService;

	protected BasePLVProductsProposalViewFactory(final BPartnerProductStatsService bpartnerProductStatsService,
			final OrderProductProposalsService orderProductProposalsService)
	{
		super(WINDOW_ID);

		this.bpartnerProductStatsService = bpartnerProductStatsService;
		this.orderProductProposalsService = orderProductProposalsService;
	}

	@Override
	protected ViewLayout createViewLayout(final ViewLayoutKey key)
	{
		final ITranslatableString caption = getProcessCaption(WEBUI_ProductsProposal_ShowProductsToAddFromBasePriceList.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(key.getWindowId())
				.setCaption(caption)
				.allowViewCloseAction(ViewCloseAction.BACK)
				.setFilters(ProductsProposalViewFilters.getDescriptors().getAll())
				//
				.addElementsFromViewRowClass(ProductsProposalRow.class, key.getViewDataType())
				.removeElementByFieldName(ProductsProposalRow.FIELD_Qty)
				.setAllowOpeningRowDetails(false)
				//
				.build();
	}

	@Override
	protected ProductsProposalRowsLoader createRowsLoaderFromRecord(final TableRecordReference recordRef)
	{
		throw new UnsupportedOperationException();
	}

	public final ProductsProposalView createView(@NonNull final ProductsProposalView parentView)
	{
		final PriceListVersionId basePriceListVersionId = parentView.getBasePriceListVersionIdOrFail();

		final ProductsProposalRowsData rowsData = ProductsProposalRowsLoader.builder()
				.bpartnerProductStatsService(bpartnerProductStatsService)
				.orderProductProposalsService(orderProductProposalsService)
				//
				.priceListVersionId(basePriceListVersionId)
				.productIdsToExclude(parentView.getProductIds())
				.bpartnerId(parentView.getBpartnerId().orElse(null))
				.currencyId(parentView.getCurrencyId())
				.soTrx(parentView.getSoTrx())
				//
				.build().load();
		logger.debug("loaded ProductsProposalRowsData with size={} for basePriceListVersionId={}", basePriceListVersionId, rowsData.size());

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
	protected List<RelatedProcessDescriptor> getRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptor(WEBUI_ProductsProposal_AddProductFromBasePriceList.class));
	}

	@Override
	public ProductsProposalView filterView(
			final IView view,
			final JSONFilterViewRequest filterViewRequest,
			final Supplier<IViewsRepository> viewsRepo)
	{
		final ProductsProposalView productsProposalView = ProductsProposalView.cast(view);
		final ProductsProposalViewFilter filter = ProductsProposalViewFilters.extractPackageableViewFilterVO(filterViewRequest);

		productsProposalView.filter(filter);

		return productsProposalView;
	}
}
