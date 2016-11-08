package de.metas.invoicecandidate.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.util.Services;

import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;

@Callout(I_C_Invoice_Candidate_Agg.class)
public class C_Invoice_Candidate_Agg
{
	@CalloutMethod(columnNames = I_C_Invoice_Candidate_Agg.COLUMNNAME_Classname)
	public void onClassname(final I_C_Invoice_Candidate_Agg icAgg)
	{
		Services.get(IAggregationBL.class).evalClassName(icAgg);
	}
}
