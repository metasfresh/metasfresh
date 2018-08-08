package de.metas.contracts.refund.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.springframework.stereotype.Component;

import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component("de.metas.contracts.refund.interceptor.C_Flatrate_RefundConfig")
@Callout(I_C_Flatrate_RefundConfig.class)
public class C_Flatrate_RefundConfig
{
	public C_Flatrate_RefundConfig()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}
	
	@CalloutMethod(columnNames = I_C_Flatrate_RefundConfig.COLUMNNAME_RefundBase)
	public void resetRefundValue(I_C_Flatrate_RefundConfig configRecord)
	{
		final String refundBase = configRecord.getRefundBase();
		if (X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage.equals(refundBase))
		{
			configRecord.setRefundAmt(null);
		}
		else if (X_C_Flatrate_RefundConfig.REFUNDBASE_Amount.equals(refundBase))
		{
			configRecord.setRefundPercent(null);
		}
		else
		{
			Check.fail("Unsupported C_Flatrate_RefundConfig.RefundBase value={}; configRecord={}", refundBase, configRecord);
		}
	}
}
