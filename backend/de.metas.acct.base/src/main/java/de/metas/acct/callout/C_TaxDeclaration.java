package de.metas.acct.callout;

import de.metas.calendar.IPeriodBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.TimeUtil;

@Callout(I_C_TaxDeclaration.class)
public class C_TaxDeclaration
{
	@SuppressWarnings("unused")
	public C_TaxDeclaration()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_TaxDeclaration.COLUMNNAME_C_Period_ID)
	public void onC_Period_ID(final I_C_TaxDeclaration taxDeclaration)
	{
		final int periodId = taxDeclaration.getC_Period_ID();
		if (periodId <= 0)
		{
			return;
		}
		taxDeclaration.setDateAcct(TimeUtil.asTimestamp(
				Services.get(IPeriodBL.class).getEndDateById(periodId)));
	}
}
