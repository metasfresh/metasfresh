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

package de.metas.ui.web.order.products_proposal.process;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.i18n.IMsgBL;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.service.OrderProductProposalsService;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalView;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.ADWindowService;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import java.util.List;
import java.util.Set;

import static de.metas.document.engine.IDocument.STATUS_Completed;

public class WEBUI_ProductsProposal_ZoomToQuotations extends ProductsProposalViewBasedProcess
{
	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final OrderProductProposalsService orderProductProposalsService = SpringContextHolder.instance.getBean(OrderProductProposalsService.class);
	private final ADWindowService adWindowService = SpringContextHolder.instance.getBean(ADWindowService.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public static final String WINDOW_SalesOrder_InternalName = "C_Order_Sales";
	private static final String QUOTATION_IDS_BY_PRODUCTS_AND_BPARTNER = "quotationIdsByProductsAndBPartner";

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("nothing selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ProductsProposalView view = getView();
		final List<ProductsProposalRow> selectedRows = getSelectedRows();

		if (!getView().getBpartnerId().isPresent())
		{
			return MSG_Error + ": No BPartner selected";
		}

		final ViewId viewId = createView(selectedRows, view.getBpartnerId().get());

		afterCloseOpenViewInNewTab(viewId);

		return MSG_OK;
	}

	@NonNull
	private ViewId createView(
			@NonNull final List<ProductsProposalRow> selectedRows,
			@NonNull final BPartnerId selectedBPartnerId)
	{
		final ViewId parentViewId = getView().getViewId();

		final Set<ProductId> selectedProductIds = selectedRows
				.stream()
				.map(ProductsProposalRow::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		final CreateViewRequest viewRequest = buildCreateViewRequest(parentViewId, selectedProductIds, selectedBPartnerId);

		final IView view = viewsRepo.createView(viewRequest);
		return view.getViewId();
	}

	@NonNull
	private CreateViewRequest buildCreateViewRequest(
			@NonNull final ViewId parentViewId,
			@NonNull final Set<ProductId> selectedProductIds,
			@NonNull final BPartnerId selectedBPartnerId)
	{
		final DocumentFilter filter = DocumentFilter.builder()
				.setFilterId(QUOTATION_IDS_BY_PRODUCTS_AND_BPARTNER)
				.setCaption(msgBL.getTranslatableMsgText("Default"))
				.addParameter(getDocumentFilterParamOrderIds(selectedProductIds, selectedBPartnerId))
				.build();

		final WindowId windowId = WindowId.of(getQuotationWindowId());

		return CreateViewRequest
				.builder(windowId)
				.setParentViewId(parentViewId)
				.addStickyFilters(filter)
				.build();
	}

	@NonNull
	private AdWindowId getQuotationWindowId()
	{
		return adWindowService.getEffectiveWindowIdByInternalName(WINDOW_SalesOrder_InternalName);
	}

	@NonNull
	private DocumentFilterParam getDocumentFilterParamOrderIds(
			@NonNull final Set<ProductId> selectedProductIds,
			@NonNull final BPartnerId selectedBPartnerId)
	{
		final SqlAndParams sqlAndParams = getSqlWhereClause(selectedProductIds, selectedBPartnerId);

		return DocumentFilterParam.of(sqlAndParams);
	}

	@NonNull
	private SqlAndParams getSqlWhereClause(
			@NonNull final Set<ProductId> selectedProductIds,
			@NonNull final BPartnerId selectedBPartnerId)
	{
		final DocTypeId quotationDocTypeId = orderProductProposalsService.getQuotationDocTypeId(ClientAndOrgId.ofClientAndOrg(getClientID(), getOrgId()));

		final IQuery<I_C_Order> query = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addInArrayFilter(I_C_OrderLine.COLUMNNAME_M_Product_ID, selectedProductIds)
				.andCollect(I_C_OrderLine.COLUMNNAME_C_Order_ID, I_C_Order.class)
				.create();

		final ICompositeQueryFilter<I_C_Order> orderIQueryFilter = queryBL.createCompositeQueryFilter(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_ID, selectedBPartnerId)
				.addEqualsFilter(I_C_Order.COLUMNNAME_C_DocTypeTarget_ID, quotationDocTypeId)
				.addEqualsFilter(I_C_Order.COLUMNNAME_DocStatus, STATUS_Completed)
				.addInSubQueryFilter(I_C_Order.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, query);

		return SqlAndParams.of(orderIQueryFilter.getSqlFiltersWhereClause(), orderIQueryFilter.getSqlFiltersParams(getCtx()));
	}
}
