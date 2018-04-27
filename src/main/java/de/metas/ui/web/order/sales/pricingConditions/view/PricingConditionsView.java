package de.metas.ui.web.order.sales.pricingConditions.view;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.i18n.ITranslatableString;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.view.AbstractCustomView;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

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

public class PricingConditionsView extends AbstractCustomView<PricingConditionsRow> implements IEditableView
{
	public static PricingConditionsView cast(final Object viewObj)
	{
		return (PricingConditionsView)viewObj;
	}

	private final PricingConditionsRowData rowsData;
	private final List<RelatedProcessDescriptor> relatedProcessDescriptors;

	@Builder
	private PricingConditionsView(
			final ViewId viewId,
			final PricingConditionsRowData rowsData,
			@Singular final List<RelatedProcessDescriptor> relatedProcessDescriptors,
			@NonNull final DocumentFilterDescriptorsProvider filterDescriptors)
	{
		super(viewId, ITranslatableString.empty(), rowsData, filterDescriptors);
		this.rowsData = rowsData;
		this.relatedProcessDescriptors = ImmutableList.copyOf(relatedProcessDescriptors);
	}

	private PricingConditionsView(final PricingConditionsView from, final PricingConditionsRowData rowsData)
	{
		super(from.getViewId(), from.getDescription(), rowsData, from.getFilterDescriptors());
		this.rowsData = rowsData;
		this.relatedProcessDescriptors = from.relatedProcessDescriptors;
	}

	public int getSalesOrderLineId()
	{
		return rowsData.getSalesOrderLineId();
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return relatedProcessDescriptors;
	}

	@Override
	public LookupValuesList getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query)
	{
		return getById(ctx.getRowId()).getFieldTypeahead(fieldName, query);
	}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		return getById(ctx.getRowId()).getFieldDropdown(fieldName);
	}

	public boolean hasEditableRow()
	{
		return rowsData.hasEditableRow();
	}

	public PricingConditionsRow getEditableRow()
	{
		return rowsData.getEditableRow();
	}

	public void patchEditableRow(@NonNull final PricingConditionsRowChangeRequest request)
	{
		rowsData.patchEditableRow(request);
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return rowsData.getFilters().getFilters();
	}

	public PricingConditionsView filter(final DocumentFiltersList filters)
	{
		return new PricingConditionsView(this, rowsData.filter(filters));
	}

	public void updateSalesOrderLineIfPossible()
	{
		if (!hasEditableRow())
		{
			return;
		}

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		final PricingConditionsRow editableRow = getEditableRow();

		final I_C_OrderLine salesOrderLine = load(getSalesOrderLineId(), I_C_OrderLine.class);
		salesOrderLine.setC_PaymentTerm_Override_ID(editableRow.getPaymentTermId());
		salesOrderLine.setIsTempPricingConditions(editableRow.isTemporary());

		if (editableRow.isTemporary())
		{
			salesOrderLine.setM_DiscountSchemaBreak_ID(-1);

			final Price price = editableRow.getPrice();
			final PriceType priceType = price.getPriceType();
			if (priceType == PriceType.NONE)
			{
				//
			}
			else if (priceType == PriceType.BASE_PRICING_SYSTEM)
			{
				salesOrderLine.setIsManualPrice(true);
				salesOrderLine.setBase_PricingSystem_ID(editableRow.getBasePriceSystemId());

			}
			else if (priceType == PriceType.FIXED_PRICED)
			{
				salesOrderLine.setIsManualPrice(true);
				salesOrderLine.setPriceEntered(price.getPriceValue());
			}

			salesOrderLine.setIsManualDiscount(true);
			salesOrderLine.setDiscount(editableRow.getDiscount());

			if (editableRow.getPaymentTermId() > 0)
			{
				salesOrderLine.setC_PaymentTerm_Override_ID(editableRow.getPaymentTermId());
			}
		}
		else
		{
			orderLineBL.updatePrices(OrderLinePriceUpdateRequest.ofOrderLine(salesOrderLine));
		}

		orderLineBL.updateLineNetAmt(salesOrderLine);
		orderLineBL.setTaxAmtInfo(salesOrderLine);
		
		InterfaceWrapperHelper.save(salesOrderLine);
	}
}
