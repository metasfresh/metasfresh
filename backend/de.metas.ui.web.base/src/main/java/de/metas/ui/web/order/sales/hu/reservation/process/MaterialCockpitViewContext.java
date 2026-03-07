package de.metas.ui.web.order.sales.hu.reservation.process;

import com.google.common.collect.ImmutableMap;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
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

	@NonNull OrderId salesOrderId;
	@NonNull OrderLineId salesOrderLineId;

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
}
