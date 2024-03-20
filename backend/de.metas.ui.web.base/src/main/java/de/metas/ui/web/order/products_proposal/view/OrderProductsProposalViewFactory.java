package de.metas.ui.web.order.products_proposal.view;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.product.stats.BPartnerProductStatsService;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.pricing.rules.campaign_price.CampaignPriceService;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.order.products_proposal.campaign_price.CampaignPriceProvider;
import de.metas.ui.web.order.products_proposal.campaign_price.CampaignPriceProviders;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsLoader;
import de.metas.ui.web.order.products_proposal.process.WEBUI_Order_ProductsProposal_Launcher;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_Delete;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_QuotationHistory;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_SaveProductPriceToCurrentPriceListVersion;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ShowProductsSoldToOtherCustomers;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ShowProductsToAddFromBasePriceList;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ZoomToQuotations;
import de.metas.ui.web.order.products_proposal.service.Order;
import de.metas.ui.web.order.products_proposal.service.OrderLinesFromProductProposalsProducer;
import de.metas.ui.web.order.products_proposal.service.OrderProductProposalsService;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.ClassViewColumnOverrides;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.FIELD_LastQuotationDate;
import static de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.FIELD_LastQuotationPrice;
import static de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.FIELD_LastQuotationUOM;
import static de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.FIELD_QuotationOrdered;
import static de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.FIELD_TermsOfDelivery;

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

@ViewFactory(windowId = OrderProductsProposalViewFactory.WINDOW_ID_STRING)
public class OrderProductsProposalViewFactory extends ProductsProposalViewFactoryTemplate
{
	private static final String SYSCONFIG_PREFIX = "de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.field.";
	private static final String SYSCONFIG_SUFIX = ".IsDisplayed";

	static final String WINDOW_ID_STRING = "orderProductsProposal";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final OrderProductProposalsService orderProductProposalsService;
	private final BPartnerProductStatsService bpartnerProductStatsService;
	private final CampaignPriceService campaignPriceService;
	private final LookupDataSourceFactory lookupDataSourceFactory;

	public OrderProductsProposalViewFactory(
			@NonNull final OrderProductProposalsService orderProductProposalsService,
			@NonNull final BPartnerProductStatsService bpartnerProductStatsService,
			@NonNull final CampaignPriceService campaignPriceService,
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory)
	{
		super(WINDOW_ID);

		this.orderProductProposalsService = orderProductProposalsService;
		this.bpartnerProductStatsService = bpartnerProductStatsService;
		this.campaignPriceService = campaignPriceService;
		this.lookupDataSourceFactory = lookupDataSourceFactory;
	}

	@Override
	protected ViewLayout createViewLayout(final ViewLayoutKey key)
	{
		final ITranslatableString caption = getProcessCaption(WEBUI_Order_ProductsProposal_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(key.getWindowId())
				.setCaption(caption)
				.allowViewCloseAction(ViewCloseAction.CANCEL)
				.allowViewCloseAction(ViewCloseAction.DONE)
				//
				.setFocusOnFieldName(ProductsProposalRow.FIELD_Qty)
				.addElementsFromViewRowClassAndFieldNames(
						ProductsProposalRow.class,
						key.getViewDataType(),
						getColumnsToBeDisplayed())
				//
				.setAllowOpeningRowDetails(false)
				.build();
	}

	@Override
	protected ProductsProposalRowsLoader createRowsLoaderFromRecord(@NonNull final TableRecordReference recordRef)
	{
		recordRef.assertTableName(I_C_Order.Table_Name);
		final OrderId orderId = OrderId.ofRepoId(recordRef.getRecord_ID());
		return createRowsLoaderFromOrderId(orderId);
	}

	@NonNull
	private ViewColumnHelper.ClassViewColumnOverrides[] getColumnsToBeDisplayed()
	{
		final List<ClassViewColumnOverrides> columnsToBeDisplayed =
				new ArrayList<>(ImmutableList.of(ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Product),
												 ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Qty),
												 ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_PackDescription),
												 ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_ASI),
												 ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_LastShipmentDays),
												 ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Price),
												 ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Currency)));

		Stream.of(FIELD_LastQuotationDate, FIELD_LastQuotationPrice, FIELD_LastQuotationUOM, FIELD_TermsOfDelivery, FIELD_QuotationOrdered)
				.filter(this::isDisplayed)
				.map(ClassViewColumnOverrides::ofFieldName)
				.forEach(columnsToBeDisplayed::add);

		columnsToBeDisplayed.add(ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_IsCampaignPrice));
		columnsToBeDisplayed.add(ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Description));

		return columnsToBeDisplayed.toArray(new ClassViewColumnOverrides[0]);
	}

	private ProductsProposalRowsLoader createRowsLoaderFromOrderId(@NonNull final OrderId orderId)
	{
		final Order order = orderProductProposalsService.getOrderById(orderId);

		final CampaignPriceProvider campaignPriceProvider = createCampaignPriceProvider(order);

		return ProductsProposalRowsLoader.builder()
				.lookupDataSourceFactory(lookupDataSourceFactory)
				.bpartnerProductStatsService(bpartnerProductStatsService)
				.orderProductProposalsService(orderProductProposalsService)
				.campaignPriceProvider(campaignPriceProvider)
				.orderProductProposalsService(orderProductProposalsService)
				//
				.priceListVersionId(order.getPriceListVersionId())
				.order(order)
				.bpartnerId(order.getBpartnerId())
				.soTrx(order.getSoTrx())
				.currencyId(order.getCurrency().getId())
				//
				.build();
	}

	private CampaignPriceProvider createCampaignPriceProvider(@NonNull final Order order)
	{
		if (!order.getSoTrx().isSales())
		{
			return CampaignPriceProviders.none();
		}

		if (order.getCountryId() == null)
		{
			return CampaignPriceProviders.none();
		}

		if (!bpartnersRepo.isCampaignPriceAllowed(order.getBpartnerId()))
		{
			return CampaignPriceProviders.none();
		}

		final BPGroupId bpGroupId = bpartnersRepo.getBPGroupIdByBPartnerId(order.getBpartnerId());

		return CampaignPriceProviders.standard()
				.campaignPriceService(campaignPriceService)
				.bpartnerId(order.getBpartnerId())
				.bpGroupId(bpGroupId)
				.pricingSystemId(order.getPricingSystemId())
				.countryId(order.getCountryId())
				.currencyId(order.getCurrency().getId())
				.date(TimeUtil.asLocalDate(order.getDatePromised()))
				.build();
	}

	@Override
	protected List<RelatedProcessDescriptor> getRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptor(WEBUI_ProductsProposal_SaveProductPriceToCurrentPriceListVersion.class),
				createProcessDescriptor(WEBUI_ProductsProposal_ShowProductsToAddFromBasePriceList.class),
				createProcessDescriptor(WEBUI_ProductsProposal_ShowProductsSoldToOtherCustomers.class),
				createProcessDescriptor(WEBUI_ProductsProposal_Delete.class),
				createProcessDescriptor(WEBUI_ProductsProposal_QuotationHistory.class),
				createProcessDescriptor(WEBUI_ProductsProposal_ZoomToQuotations.class));
	}

	@Override
	protected void beforeViewClose(@NonNull final ViewId viewId, @NonNull final ViewCloseAction closeAction)
	{
		if (ViewCloseAction.DONE.equals(closeAction))
		{
			final ProductsProposalView view = getById(viewId);
			if (view.getOrderId() != null)
			{
				createOrderLines(view);
			}
		}
	}

	private void createOrderLines(final ProductsProposalView view)
	{
		OrderLinesFromProductProposalsProducer.builder()
				.orderId(view.getOrderId().get())
				.rows(view.getAllRows())
				.build()
				.produce();
	}

	private boolean isDisplayed(@NonNull final String fieldName)
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_PREFIX + fieldName + SYSCONFIG_SUFIX, false);
	}
}
