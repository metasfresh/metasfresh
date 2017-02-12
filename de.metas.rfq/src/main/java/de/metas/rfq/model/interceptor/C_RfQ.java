package de.metas.rfq.model.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQ_Topic;
import de.metas.rfq.util.IRfQWorkDatesAware;
import de.metas.rfq.util.RfQWorkDatesUtil;

/*
 * #%L
 * de.metas.business
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
@Callout(I_C_RfQ.class)
public class C_RfQ
{
	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@Init
	void configureCopyWithDetailsSupport()
	{
		CopyRecordFactory.enableForTableName(I_C_RfQ.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_RfQ rfq)
	{
		final IRfQWorkDatesAware workDatesAware = InterfaceWrapperHelper.create(rfq, IRfQWorkDatesAware.class);
		RfQWorkDatesUtil.updateWorkDates(workDatesAware);
	}

	@CalloutMethod(columnNames = I_C_RfQ.COLUMNNAME_DateWorkStart, skipIfCopying = true)
	public void onDateWorkStart(final I_C_RfQ rfq)
	{
		final IRfQWorkDatesAware workDatesAware = InterfaceWrapperHelper.create(rfq, IRfQWorkDatesAware.class);
		RfQWorkDatesUtil.updateFromDateWorkStart(workDatesAware);
	}

	@CalloutMethod(columnNames = I_C_RfQ.COLUMNNAME_DateWorkComplete, skipIfCopying = true)
	public void onDateWorkComplete(final I_C_RfQ rfq)
	{
		final IRfQWorkDatesAware workDatesAware = InterfaceWrapperHelper.create(rfq, IRfQWorkDatesAware.class);
		RfQWorkDatesUtil.updateFromDateWorkComplete(workDatesAware);
	}

	@CalloutMethod(columnNames = I_C_RfQ.COLUMNNAME_DeliveryDays, skipIfCopying = true)
	public void onDeliveryDays(final I_C_RfQ rfq)
	{
		if (InterfaceWrapperHelper.isNull(rfq, I_C_RfQ.COLUMNNAME_DeliveryDays))
		{
			return;
		}
		final IRfQWorkDatesAware workDatesAware = InterfaceWrapperHelper.create(rfq, IRfQWorkDatesAware.class);
		RfQWorkDatesUtil.updateFromDeliveryDays(workDatesAware);
	}

	@CalloutMethod(columnNames = I_C_RfQ.COLUMNNAME_C_RfQ_Topic_ID, skipIfCopying = true)
	public void onC_RfQTopic(final I_C_RfQ rfq)
	{
		final I_C_RfQ_Topic rfqTopic = rfq.getC_RfQ_Topic();
		if (rfqTopic == null)
		{
			return;
		}

		final String rfqTypeDefault = rfqTopic.getRfQType();
		if (rfqTypeDefault != null)
		{
			rfq.setRfQType(rfqTypeDefault);
		}
	}
}
