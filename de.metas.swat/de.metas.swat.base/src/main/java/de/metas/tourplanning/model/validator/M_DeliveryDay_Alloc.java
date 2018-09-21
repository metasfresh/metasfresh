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


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.util.Services;

@Interceptor(I_M_DeliveryDay_Alloc.class)
public class M_DeliveryDay_Alloc
{
	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onCreate(final I_M_DeliveryDay_Alloc deliveryDayAlloc)
	{
		final I_M_DeliveryDay deliveryDay = deliveryDayAlloc.getM_DeliveryDay();

		final I_M_DeliveryDay_Alloc deliveryDayAllocNew = deliveryDayAlloc;
		final I_M_DeliveryDay_Alloc deliveryDayAllocOld = null;
		updateDeliveryDayWhenAllocationChanged(deliveryDay, deliveryDayAllocNew, deliveryDayAllocOld);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void onChange(final I_M_DeliveryDay_Alloc deliveryDayAlloc)
	{
		final I_M_DeliveryDay deliveryDay = deliveryDayAlloc.getM_DeliveryDay();

		final I_M_DeliveryDay_Alloc deliveryDayAllocNew = deliveryDayAlloc;
		final I_M_DeliveryDay_Alloc deliveryDayAllocOld = InterfaceWrapperHelper.createOld(deliveryDayAlloc, I_M_DeliveryDay_Alloc.class);
		updateDeliveryDayWhenAllocationChanged(deliveryDay, deliveryDayAllocNew, deliveryDayAllocOld);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onBeforeDelete(final I_M_DeliveryDay_Alloc deliveryDayAlloc)
	{
		final I_M_DeliveryDay deliveryDay = deliveryDayAlloc.getM_DeliveryDay();
		if (deliveryDay.isProcessed())
		{
			throw new AdempiereException("@CannotDelete@: @M_DeliveryDay_Alloc_ID@ (@M_DeliveryDay_ID@ @Processed@=@Y@)");
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onAfterDelete(final I_M_DeliveryDay_Alloc deliveryDayAlloc)
	{
		final I_M_DeliveryDay deliveryDay = deliveryDayAlloc.getM_DeliveryDay();

		final I_M_DeliveryDay_Alloc deliveryDayAllocNew = null;
		final I_M_DeliveryDay_Alloc deliveryDayAllocOld = deliveryDayAlloc;
		updateDeliveryDayWhenAllocationChanged(deliveryDay, deliveryDayAllocNew, deliveryDayAllocOld);
	}

	private final void updateDeliveryDayWhenAllocationChanged(final I_M_DeliveryDay deliveryDay,
			final I_M_DeliveryDay_Alloc deliveryDayAllocNew,
			final I_M_DeliveryDay_Alloc deliveryDayAllocOld)
	{
		final boolean deliveryDayProcessed = deliveryDay.isProcessed();

		//
		// Call handlers to update the delivery day record
		Services.get(IDeliveryDayBL.class)
				.getDeliveryDayHandlers()
				.updateDeliveryDayWhenAllocationChanged(deliveryDay, deliveryDayAllocNew, deliveryDayAllocOld);

		//
		// Prohibit changing delivery day if it was processed
		if (deliveryDayProcessed)
		{
			if (InterfaceWrapperHelper.hasChanges(deliveryDay))
			{
				throw new AdempiereException("Cannot change a delivery day which was already processed"
						+ "\n @M_DeliveryDay_ID@: " + deliveryDay
						+ "\n @M_DeliveryDay_Alloc_ID@ @New@: " + deliveryDayAllocNew
						+ "\n @M_DeliveryDay_Alloc_ID@ @Old@: " + deliveryDayAllocOld);
			}

			// return it because there is no need to save the changes
			return;
		}

		//
		// Save delivery day changes
		InterfaceWrapperHelper.save(deliveryDay);
	}
}
