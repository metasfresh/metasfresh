package de.metas.ui.web.material.cockpit.v2.reservation;

import com.google.common.collect.ImmutableMap;
import de.metas.order.OrderLineId;
import de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Launch_HUEditor;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

public abstract class MaterialCockpitV2BasedProcess extends ViewBasedProcessTemplate
{
	@NonNull
	protected OrderLineId getSalesOrderLineId()
	{
		final OrderLineId orderLineId = getSalesOrderLineIdOrNull();
		if (orderLineId == null)
		{
			throw new AdempiereException("No sales order line context");
		}
		return orderLineId;
	}

	@Nullable
	protected OrderLineId getSalesOrderLineIdOrNull()
	{
		final ImmutableMap<String, Object> params = getView().getParameters();
		final Object value = params.get(WEBUI_C_OrderLineSO_Launch_HUEditor.VIEW_PARAM_PARENT_SALES_ORDER_LINE_ID);
		if (value instanceof OrderLineId)
		{
			return (OrderLineId)value;
		}
		return null;
	}
}
