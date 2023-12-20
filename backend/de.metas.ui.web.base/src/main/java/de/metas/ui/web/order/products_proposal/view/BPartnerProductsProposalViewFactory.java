package de.metas.ui.web.order.products_proposal.view;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.BPartnerProductStatsService;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowsLoader;
import de.metas.ui.web.order.products_proposal.process.WEBUI_BPartner_ProductsProposal_Launcher;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_SaveProductPriceToCurrentPriceListVersion;
import de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ShowProductsToAddFromBasePriceList;
import de.metas.ui.web.order.products_proposal.service.OrderProductProposalsService;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

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

@ViewFactory(windowId = BPartnerProductsProposalViewFactory.WINDOW_ID_STRING)
public class BPartnerProductsProposalViewFactory extends ProductsProposalViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "bpartnerProductsProposal";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final BPartnerProductStatsService bpartnerProductStatsService;
	private final OrderProductProposalsService orderProductProposalsService;
	private final LookupDataSourceFactory lookupDataSourceFactory;
	protected BPartnerProductsProposalViewFactory(
			@NonNull final BPartnerProductStatsService bpartnerProductStatsService,
			@NonNull final OrderProductProposalsService orderProductProposalsService,
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory)
	{
		super(WINDOW_ID);

		this.bpartnerProductStatsService = bpartnerProductStatsService;
		this.orderProductProposalsService = orderProductProposalsService;
		this.lookupDataSourceFactory = lookupDataSourceFactory;
	}

	@Override
	protected ViewLayout createViewLayout(final ViewLayoutKey key)
	{
		final ITranslatableString caption = getProcessCaption(WEBUI_BPartner_ProductsProposal_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(key.getWindowId())
				.setCaption(caption)
				.addElementsFromViewRowClass(ProductsProposalRow.class, key.getViewDataType())
				.removeElementByFieldName(ProductsProposalRow.FIELD_Qty)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.setAllowOpeningRowDetails(false)
				.build();
	}

	@Override
	protected ProductsProposalRowsLoader createRowsLoaderFromRecord(final TableRecordReference recordRef)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

		recordRef.assertTableName(I_C_BPartner.Table_Name);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(recordRef.getRecord_ID());
		final Set<CountryId> countryIds = bpartnersRepo.retrieveBPartnerLocationCountryIds(bpartnerId);
		if (countryIds.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @C_BPartner_Location_ID@");
		}

		final I_C_BPartner bpartnerRecord = bpartnersRepo.getById(bpartnerId);
		PricingSystemId pricingSystemId = null;
		SOTrx soTrx = null;
		if (bpartnerRecord.isCustomer())
		{
			pricingSystemId = PricingSystemId.ofRepoIdOrNull(bpartnerRecord.getM_PricingSystem_ID());
			soTrx = SOTrx.SALES;
		}
		if (pricingSystemId == null && bpartnerRecord.isVendor())
		{
			pricingSystemId = PricingSystemId.ofRepoIdOrNull(bpartnerRecord.getPO_PricingSystem_ID());
			soTrx = SOTrx.PURCHASE;
		}
		if (pricingSystemId == null)
		{
			throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@");
		}

		final ZonedDateTime today = SystemTime.asZonedDateTime();
		final Set<PriceListVersionId> priceListVersionIds = priceListsRepo.retrievePriceListsCollectionByPricingSystemId(pricingSystemId)
				.filterAndStreamIds(countryIds)
				.map(priceListId -> priceListsRepo.retrievePriceListVersionId(priceListId, today))
				.collect(ImmutableSet.toImmutableSet());

		return ProductsProposalRowsLoader.builder()
				.lookupDataSourceFactory(lookupDataSourceFactory)
				.bpartnerProductStatsService(bpartnerProductStatsService)
				.orderProductProposalsService(orderProductProposalsService)
				.priceListVersionIds(priceListVersionIds)
				.bpartnerId(bpartnerId)
				.soTrx(soTrx)
				.build();
	}

	@Override
	protected List<RelatedProcessDescriptor> getRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptor(WEBUI_ProductsProposal_SaveProductPriceToCurrentPriceListVersion.class),
				createProcessDescriptor(WEBUI_ProductsProposal_ShowProductsToAddFromBasePriceList.class));
	}
}
