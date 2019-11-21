package de.metas.ui.web.order.products_proposal.view;

import java.time.LocalDate;
import java.util.List;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.BPartnerProductStatsService;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.rules.campaign_price.CampaignPriceService;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.order.products_proposal.campaign_price.CampaignPriceProvider;
import de.metas.ui.web.order.products_proposal.campaign_price.CampaignPriceProviders;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsLoader;
import de.metas.ui.web.order.products_proposal.process.WEBUI_Order_ProductsProposal_Launcher;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_Delete;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_SaveProductPriceToCurrentPriceListVersion;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ShowProductsSoldToOtherCustomers;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ShowProductsToAddFromBasePriceList;
import de.metas.ui.web.order.products_proposal.service.OrderLinesFromProductProposalsProducer;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.ClassViewColumnOverrides;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
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

@ViewFactory(windowId = OrderProductsProposalViewFactory.WINDOW_ID_STRING)
public class OrderProductsProposalViewFactory extends ProductsProposalViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "orderProductsProposal";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final BPartnerProductStatsService bpartnerProductStatsService;
	private final CampaignPriceService campaignPriceService;

	public OrderProductsProposalViewFactory(
			@NonNull final BPartnerProductStatsService bpartnerProductStatsService,
			@NonNull final CampaignPriceService campaignPriceService)
	{
		super(WINDOW_ID);

		this.bpartnerProductStatsService = bpartnerProductStatsService;
		this.campaignPriceService = campaignPriceService;
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
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Product),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Qty),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_PackDescription),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_ASI),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_LastShipmentDays),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Price),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_Currency),
						ClassViewColumnOverrides.ofFieldName(ProductsProposalRow.FIELD_IsCampaignPrice))
				//
				.build();
	}

	@Override
	protected ProductsProposalRowsLoader createRowsLoaderFromRecord(@NonNull final TableRecordReference recordRef)
	{
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

		recordRef.assertTableName(I_C_Order.Table_Name);
		final OrderId orderId = OrderId.ofRepoId(recordRef.getRecord_ID());

		final I_C_Order orderRecord = ordersRepo.getById(orderId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());
		final SOTrx soTrx = SOTrx.ofBoolean(orderRecord.isSOTrx());

		final PriceListId priceListId = PriceListId.ofRepoId(orderRecord.getM_PriceList_ID());
		final LocalDate date = TimeUtil.asLocalDate(orderRecord.getDatePromised());
		final PriceListVersionId priceListVersionId = priceListsRepo.retrievePriceListVersionId(priceListId, date);

		final CampaignPriceProvider campaignPriceProvider = createCampaignPriceProvider(priceListId, bpartnerId, date, soTrx);

		return ProductsProposalRowsLoader.builder()
				.bpartnerProductStatsService(bpartnerProductStatsService)
				.campaignPriceProvider(campaignPriceProvider)
				.priceListVersionId(priceListVersionId)
				.orderId(orderId)
				.bpartnerId(bpartnerId)
				.soTrx(soTrx)
				.build();
	}

	private CampaignPriceProvider createCampaignPriceProvider(
			@NonNull final PriceListId priceListId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final LocalDate date,
			@NonNull final SOTrx soTrx)
	{
		if (!soTrx.isSales())
		{
			return CampaignPriceProviders.none();
		}

		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

		if (!bpartnersRepo.isActionPriceAllowed(bpartnerId))
		{
			return CampaignPriceProviders.none();
		}

		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
		final I_M_PriceList priceList = priceListsRepo.getById(priceListId);
		final PricingSystemId pricingSystemId = priceListsRepo.getPricingSystemId(priceListId);
		final CountryId countryId = CountryId.ofRepoIdOrNull(priceList.getC_Country_ID());
		if (countryId == null)
		{
			return CampaignPriceProviders.none();
		}
		final CurrencyId currencyId = CurrencyId.ofRepoId(priceList.getC_Currency_ID());

		final BPGroupId bpGroupId = bpartnersRepo.getBPGroupIdByBPartnerId(bpartnerId);

		return CampaignPriceProviders.standard()
				.campaignPriceService(campaignPriceService)
				.bpartnerId(bpartnerId)
				.bpGroupId(bpGroupId)
				.pricingSystemId(pricingSystemId)
				.countryId(countryId)
				.currencyId(currencyId)
				.date(date)
				.build();
	}

	@Override
	protected List<RelatedProcessDescriptor> getRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptor(WEBUI_ProductsProposal_SaveProductPriceToCurrentPriceListVersion.class),
				createProcessDescriptor(WEBUI_ProductsProposal_ShowProductsToAddFromBasePriceList.class),
				createProcessDescriptor(WEBUI_ProductsProposal_ShowProductsSoldToOtherCustomers.class),
				createProcessDescriptor(WEBUI_ProductsProposal_Delete.class));
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
				.rows(view.getRowsWithQtySet())
				.build()
				.produce();
	}
}
