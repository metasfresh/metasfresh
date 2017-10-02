package de.metas.contracts.interceptor;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;

@Interceptor(I_C_SubscriptionProgress.class)
public class C_SubscriptionProgress
{
	public static final transient C_SubscriptionProgress instance = new C_SubscriptionProgress();

	private C_SubscriptionProgress()
	{
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void createMissingShipmentSchedules(final I_C_SubscriptionProgress subscription)
	{
		// TODO: filter based on subscription EventType and other fields
		final Properties ctx = InterfaceWrapperHelper.getCtx(subscription);
		final String trxName = InterfaceWrapperHelper.getTrxName(subscription);
		CreateMissingShipmentSchedulesWorkpackageProcessor.schedule(ctx, trxName);
	}
}
