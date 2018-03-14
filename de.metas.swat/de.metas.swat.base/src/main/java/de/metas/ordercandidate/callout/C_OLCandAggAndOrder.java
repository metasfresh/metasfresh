package de.metas.ordercandidate.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Column;
import org.compiere.util.DisplayType;

import de.metas.ordercandidate.model.I_C_OLCandAggAndOrder;
import de.metas.ordercandidate.model.X_C_OLCandAggAndOrder;

@Callout(I_C_OLCandAggAndOrder.class)
public class C_OLCandAggAndOrder
{
	@CalloutMethod(columnNames = { I_C_OLCandAggAndOrder.COLUMNNAME_AD_Column_OLCand_ID, I_C_OLCandAggAndOrder.COLUMNNAME_GroupBy })
	public void setGranularity(final I_C_OLCandAggAndOrder aggAndOrder)
	{
		if (aggAndOrder.getAD_Column_OLCand_ID() <= 0)
		{
			return;
		}

		final I_AD_Column colOLCand = aggAndOrder.getAD_Column_OLCand();
		// aggAndOrder.setAD_Reference_OLCand_ID(colOLCand.getAD_Reference_ID());

		if (!aggAndOrder.isGroupBy()
				|| (colOLCand.getAD_Reference_ID() != DisplayType.Date && colOLCand.getAD_Reference_ID() != DisplayType.DateTime))
		{
			aggAndOrder.setGranularity(null);
		}
		else if (Check.isEmpty(aggAndOrder.getGranularity()))
		{
			aggAndOrder.setGranularity(X_C_OLCandAggAndOrder.GRANULARITY_Tag);
		}
	}

}
