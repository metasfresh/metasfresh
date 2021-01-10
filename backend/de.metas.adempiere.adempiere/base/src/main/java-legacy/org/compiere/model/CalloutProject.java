package org.compiere.model;

import org.adempiere.ad.callout.api.ICalloutField;

import java.math.BigDecimal;

/**
 * Project Callouts
 *
 * @author Jorg Janke
 */
public class CalloutProject extends CalloutEngine
{
	/**
	 * Project Planned - Price + Qty.
	 * - called from PlannedPrice, PlannedQty
	 * - calculates PlannedAmt (same as Trigger)
	 */
	public String planned(final ICalloutField calloutField)
	{
		if (isCalloutActive() || calloutField.getValue() == null)
		{
			return NO_ERROR;
		}

		final I_C_ProjectLine projectLine = calloutField.getModel(I_C_ProjectLine.class);

		final BigDecimal plannedQty = projectLine.getPlannedQty();
		final BigDecimal plannedPrice = projectLine.getPlannedPrice();
		final BigDecimal plannedAmt = plannedQty.multiply(plannedPrice);
		projectLine.setPlannedAmt(plannedAmt);

		return NO_ERROR;
	}
}
