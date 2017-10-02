package de.metas.contracts.flatrate.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.CalloutEngine;

import de.metas.contracts.model.I_C_Flatrate_Matching;

/**
 * Callout for
 * 
 * @author ts
 * 
 */
public class C_Flatrate_Matching extends CalloutEngine
{

	public String onM_PricingSystem_ID(final ICalloutField calloutField)
	{

		final I_C_Flatrate_Matching matching = calloutField.getModel(I_C_Flatrate_Matching.class);

		if (matching.getM_PricingSystem_ID() <= 0 && matching.getC_Flatrate_Conditions_ID() > 0)
		{
			matching.setM_PricingSystem_ID(matching.getC_Flatrate_Conditions().getM_PricingSystem_ID());
		}

		return NO_ERROR;
	}

	public String onC_Flatrate_Transition_ID(final ICalloutField calloutField)
	{

		final I_C_Flatrate_Matching matching = calloutField.getModel(I_C_Flatrate_Matching.class);

		if (matching.getC_Flatrate_Transition_ID() <= 0 && matching.getC_Flatrate_Conditions_ID() > 0)
		{
			matching.setC_Flatrate_Transition_ID(matching.getC_Flatrate_Conditions().getC_Flatrate_Transition_ID());
		}

		return NO_ERROR;
	}

}
