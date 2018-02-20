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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import lombok.NonNull;

@Interceptor(I_C_SubscriptionProgress.class)
public class C_SubscriptionProgress
{
	static final transient C_SubscriptionProgress instance = new C_SubscriptionProgress();

	private C_SubscriptionProgress()
	{
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void createMissingShipmentSchedules(@NonNull final I_C_SubscriptionProgress subscriptionProgress)
	{
		if (!isGoingToHaveAShipmentScheduleSomeDay(subscriptionProgress))
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(subscriptionProgress);
		final String trxName = InterfaceWrapperHelper.getTrxName(subscriptionProgress);
		CreateMissingShipmentSchedulesWorkpackageProcessor.schedule(ctx, trxName);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteShipmentSchedules(@NonNull final I_C_SubscriptionProgress subscriptionProgress)
	{
		Services.get(IShipmentSchedulePA.class)
				.deleteAllForReference(TableRecordReference.of(subscriptionProgress));
	}

	@ModelChange(//
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = { I_C_SubscriptionProgress.COLUMNNAME_DropShip_BPartner_ID, I_C_SubscriptionProgress.COLUMNNAME_DropShip_Location_ID })
	public void assertDropshipFieldsAreValid(@NonNull final I_C_SubscriptionProgress subscriptionProgress)
	{
		if (!isGoingToHaveAShipmentScheduleSomeDay(subscriptionProgress))
		{
			return;
		}

		Check.errorIf(subscriptionProgress.getDropShip_BPartner_ID() <= 0,
				"The given subscription progress needs to have DropShip_BPartner_ID > 0; subscriptionProgress={}",
				subscriptionProgress);

		Check.errorIf(subscriptionProgress.getDropShip_Location_ID() <= 0,
				"The given subscription progress needs to have DropShip_Location_ID > 0; subscriptionProgress={}",
				subscriptionProgress);
	}

	private boolean isGoingToHaveAShipmentScheduleSomeDay(@NonNull final I_C_SubscriptionProgress subscriptionProgress)
	{
		if (!X_C_SubscriptionProgress.EVENTTYPE_Delivery.equals(subscriptionProgress.getEventType()))
		{
			return false;
		}
		if (!X_C_SubscriptionProgress.STATUS_Planned.equals(subscriptionProgress.getStatus()))
		{
			return false;
		}
		if (!X_C_SubscriptionProgress.CONTRACTSTATUS_Running.equals(subscriptionProgress.getContractStatus()))
		{
			return false;
		}
		if (subscriptionProgress.getM_ShipmentSchedule_ID() > 0)
		{
			return false;
		}
		return true;
	}
}
