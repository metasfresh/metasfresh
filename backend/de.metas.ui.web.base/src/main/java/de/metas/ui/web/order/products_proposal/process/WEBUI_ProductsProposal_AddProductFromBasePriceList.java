package de.metas.ui.web.order.products_proposal.process;

import com.google.common.collect.ImmutableList;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.ui.web.order.products_proposal.model.ProductProposalPrice;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowAddRequest;
import de.metas.ui.web.order.products_proposal.service.Order;
import de.metas.ui.web.order.products_proposal.service.OrderProductProposalsService;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalView;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Incoterms;
import org.compiere.model.I_C_UOM;

import java.util.List;

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

public class WEBUI_ProductsProposal_AddProductFromBasePriceList extends ProductsProposalViewBasedProcess
{
	@NonNull
	private final OrderProductProposalsService orderProductProposalsService = SpringContextHolder.instance.getBean(OrderProductProposalsService.class);
	@NonNull
	private final LookupDataSource incoTermsLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_Incoterms.Table_Name);
	private final LookupDataSource uomLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_UOM.Table_Name);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		addSelectedRowsToInitialView();
		closeAllViewsAndShowInitialView();
		return MSG_OK;
	}

	private void addSelectedRowsToInitialView()
	{
		final ProductsProposalView initialView = getInitialView();

		final List<ProductsProposalRowAddRequest> addRequests = getSelectedRows()
				.stream()
				.map(this::toProductsProposalRowAddRequest)
				.collect(ImmutableList.toImmutableList());

		initialView.addOrUpdateRows(addRequests);
	}

	private ProductsProposalRowAddRequest toProductsProposalRowAddRequest(final ProductsProposalRow row)
	{
		final ProductProposalPrice currentProductProposalPrice = row.getPrice();

		final ProductsProposalRowAddRequest.ProductsProposalRowAddRequestBuilder productsProposalRowAddRequestBuilder = ProductsProposalRowAddRequest.builder()
				.product(row.getProduct())
				.asiDescription(row.getAsiDescription())
				.priceListPrice(currentProductProposalPrice.getUserEnteredPrice())
				.lastShipmentDays(row.getLastShipmentDays())
				.copiedFromProductPriceId(row.getProductPriceId())
				.packingMaterialId(row.getPackingMaterialId())
				.packingDescription(row.getPackingDescription());

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(getClientID(), getOrgId());

		orderProductProposalsService.getLastQuotation(clientAndOrgId, row.getBPartnerId(), row.getProductId())
				.ifPresent((lastQuotation) -> setQuotationInfo(lastQuotation, productsProposalRowAddRequestBuilder, row.getProductId(), currentProductProposalPrice));

		return productsProposalRowAddRequestBuilder
				.build();
	}

	private void setQuotationInfo(
			@NonNull final Order quotation,
			@NonNull final ProductsProposalRowAddRequest.ProductsProposalRowAddRequestBuilder productsProposalRowAddRequestBuilder,
			@NonNull final ProductId productId,
			@NonNull final ProductProposalPrice currentProductProposalPrice)
	{
		final ProductPrice quotationPrice = orderProductProposalsService.getQuotationPrice(quotation, productId, currentProductProposalPrice.getCurrencyCode());

		productsProposalRowAddRequestBuilder.lastQuotationDate(quotation.getDateOrdered().toLocalDate())
				.lastQuotationPrice(quotationPrice.toBigDecimal())
				.lastQuotationPriceUOM(uomLookup.findById(quotationPrice.getUomId()))
				.incoterms(incoTermsLookup.findById(quotation.getIncoTermsId()))
				.quotationOrdered(quotation.getRefOrderId() != null);
	}
}
