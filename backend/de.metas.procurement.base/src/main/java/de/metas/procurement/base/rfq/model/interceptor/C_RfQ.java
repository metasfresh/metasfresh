package de.metas.procurement.base.rfq.model.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.procurement.base.rfq.model.I_C_RfQ;
import de.metas.procurement.base.rfq.model.I_C_RfQ_Topic;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Interceptor(I_C_RfQ.class)
@Callout(value = I_C_RfQ.class)
public class C_RfQ
{
	@Init
	public void onInit()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_RfQ.COLUMNNAME_C_RfQ_Topic_ID, skipIfCopying = true)
	public void onC_RfQTopic(final I_C_RfQ rfq)
	{
		final I_C_RfQ_Topic rfqTopic = InterfaceWrapperHelper.create(rfq.getC_RfQ_Topic(), I_C_RfQ_Topic.class);
		if (rfqTopic == null)
		{
			return;
		}

		final int flatrateConditionsId = rfqTopic.getC_Flatrate_Conditions_ID();
		if (flatrateConditionsId > 0)
		{
			rfq.setC_Flatrate_Conditions_ID(flatrateConditionsId);
		}
	}

}
