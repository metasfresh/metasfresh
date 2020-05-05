package de.metas.tourplanning.model.validator;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.tourplanning.api.IDeliveryDayDAO;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.util.Services;

@Interceptor(I_M_Tour_Instance.class)
public class M_Tour_Instance
{
	/**
	 * Propagate fields from {@link I_M_Tour_Instance} to assigned {@link I_M_DeliveryDay}s.
	 * 
	 * @param tourInstance
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = { I_M_Tour_Instance.COLUMNNAME_Processed })
	public void updateDeliveryDays(final I_M_Tour_Instance tourInstance)
	{
		//
		// Fields that we need to propagate from tour instance to delivery days
		final boolean tourInstanceProcessed = tourInstance.isProcessed();

		//
		// Iterate all delivery days assigned to this tour instance and propagate the fields
		final IDeliveryDayDAO deliveryDayDAO = Services.get(IDeliveryDayDAO.class);
		final List<I_M_DeliveryDay> deliveryDays = deliveryDayDAO.retrieveDeliveryDaysForTourInstance(tourInstance);
		for (final I_M_DeliveryDay deliveryDay : deliveryDays)
		{
			deliveryDay.setProcessed(tourInstanceProcessed);
			InterfaceWrapperHelper.save(deliveryDay);
		}
	}
}
