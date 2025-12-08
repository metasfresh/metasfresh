package de.metas.contracts.flatrate.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.util.Services;

@Callout(I_C_Flatrate_Term.class)
public class C_Flatrate_Term
{
	@CalloutMethod(columnNames=I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID)
	public void onC_Flatrate_Conditions_ID(final I_C_Flatrate_Term term)
	{
		Services.get(IFlatrateBL.class).updateFromConditions(term);
	}

	@CalloutMethod(columnNames=I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Data_ID)
	public void updateBillBPartner(final I_C_Flatrate_Term term)
	{
		term.setBill_BPartner_ID(term.getC_Flatrate_Data().getC_BPartner_ID());
	}
}
