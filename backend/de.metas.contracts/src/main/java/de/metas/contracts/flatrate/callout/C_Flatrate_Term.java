package de.metas.contracts.flatrate.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.util.Services;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;

@Callout(I_C_Flatrate_Term.class)
public class C_Flatrate_Term
{
	@CalloutMethod(columnNames=I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID)
	public void onC_Flatrate_Conditions_ID(final I_C_Flatrate_Term term)
	{
		Services.get(IFlatrateBL.class).updateFromConditions(term);
	}
}
