package de.metas.contracts.flatrate.modelvalidator;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

@Validator(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{

	/**
	 * If a shipment schedule is to be deleted, then we make sure it is not referenced from and
	 * {@link I_C_SubscriptionProgress} record.
	 * 
	 * @param sched
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void afterDelete(final I_M_ShipmentSchedule sched)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sched);
		final String trxName = InterfaceWrapperHelper.getTrxName(sched);

		final String wc = I_C_SubscriptionProgress.COLUMNNAME_M_ShipmentSchedule_ID + "=?";

		final List<I_C_SubscriptionProgress> deliveries = new Query(ctx, I_C_SubscriptionProgress.Table_Name, wc, trxName)
				.setParameters(sched.getM_ShipmentSchedule_ID())
				.setOnlyActiveRecords(false)
				.setOrderBy(I_C_SubscriptionProgress.COLUMNNAME_C_SubscriptionProgress_ID)
				.list(I_C_SubscriptionProgress.class);
		
		for (final I_C_SubscriptionProgress delivery : deliveries)
		{
			delivery.setProcessed(false);
			delivery.setM_ShipmentSchedule_ID(0);
			InterfaceWrapperHelper.save(sched);
		}
	}
}
