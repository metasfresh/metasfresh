package de.metas.ui.web.invoice.match_receipt_costs;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderId;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Cost_Type;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;

@UtilityClass
class ReceiptCostsViewFilterHelper
{
	public static final String FILTER_ID = "default";

	public static String PARAM_C_BPartner_ID = "C_BPartner_ID";
	public static String PARAM_C_Order_ID = "C_Order_ID";
	public static String PARAM_C_Cost_Type_ID = I_C_Cost_Type.COLUMNNAME_C_Cost_Type_ID;

	public static DocumentFilterDescriptor createFilterDescriptor(@NonNull final LookupDescriptorProviders lookupDescriptorProviders)
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID)
				.setFrequentUsed(false)
				//.setInlineRenderMode(DocumentFilterInlineRenderMode.INLINE_PARAMETERS)
				.setDisplayName("default")
				.addParameter(newParamDescriptor(PARAM_C_BPartner_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_C_BPartner.Table_Name).provideForFilter()))
				.addParameter(newParamDescriptor(PARAM_C_Order_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_C_Order.Table_Name).provideForFilter()))
				.addParameter(newParamDescriptor(PARAM_C_Cost_Type_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_C_Cost_Type.Table_Name).provideForFilter()))
				.build();
	}

	private static DocumentFilterParamDescriptor.Builder newParamDescriptor(final String fieldName)
	{
		return DocumentFilterParamDescriptor.builder()
				.fieldName(fieldName)
				.displayName(TranslatableStrings.adElementOrMessage(fieldName));
	}

	public static ReceiptCostRowsQuery toRowsQuery(final @Nullable DocumentFilter filter)
	{
		final ReceiptCostRowsQuery.ReceiptCostRowsQueryBuilder rowsQuery = ReceiptCostRowsQuery.builder()
				.queryLimit(QueryLimit.ONE_HUNDRED);

		if (filter != null)
		{
			BPartnerId.optionalOfRepoId(filter.getParameterValueAsInt(ReceiptCostsViewFilterHelper.PARAM_C_BPartner_ID, -1))
					.ifPresent(rowsQuery::bpartnerId);
			OrderId.optionalOfRepoId(filter.getParameterValueAsInt(ReceiptCostsViewFilterHelper.PARAM_C_Order_ID, -1))
					.ifPresent(rowsQuery::orderId);
			OrderCostTypeId.optionalOfRepoId(filter.getParameterValueAsInt(ReceiptCostsViewFilterHelper.PARAM_C_Cost_Type_ID, -1))
					.ifPresent(rowsQuery::costTypeId);
		}

		return rowsQuery.build();
	}

}
