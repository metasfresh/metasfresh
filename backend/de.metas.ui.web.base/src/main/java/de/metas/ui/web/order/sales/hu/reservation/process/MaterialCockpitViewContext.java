package de.metas.ui.web.order.sales.hu.reservation.process;

import com.google.common.collect.ImmutableMap;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.ui.web.view.IView;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

@Value
@Builder
@Jacksonized
public class MaterialCockpitViewContext
{
	@NonNull public static final String VIEW_PARAMETER_NAME = "MaterialCockpitViewContext";

	@NonNull PInstanceId sourceSelectionId;
	@NonNull OrderAndLineId salesOrderAndLineId;

	@NonNull
	public static MaterialCockpitViewContext of(@NonNull final IView view)
	{
		final ImmutableMap<String, Object> params = view.getParameters();
		final Object value = params.get(VIEW_PARAMETER_NAME);
		if (value instanceof MaterialCockpitViewContext)
		{
			return (MaterialCockpitViewContext)value;
		}
		else
		{
			throw new AdempiereException("Cannot extract " + MaterialCockpitViewContext.class + " from " + view + " because it does not have the parameter " + VIEW_PARAMETER_NAME + " set to it.");
		}
	}

	@NonNull
	public OrderLineId getSalesOrderLineId()
	{
		return salesOrderAndLineId.getOrderLineId();
	}
}
