/**
 * 
 */
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
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;

import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.ITourInstanceBL;
import de.metas.tourplanning.api.ITourInstanceDAO;
import de.metas.tourplanning.exceptions.BPartnerNotVendorException;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour_Instance;

/**
 * @author cg
 *
 */
@Interceptor(I_M_DeliveryDay.class)
public class M_DeliveryDay
{
	/**
	 * Validate IsToBeFetched flag shall be compatible with BPartner.
	 * 
	 * @param deliveryDay
	 */
	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			})
	public void checkIsToBeFetched(final I_M_DeliveryDay deliveryDay)
	{
		final I_C_BPartner partner = deliveryDay.getC_BPartner();
		if (partner != null && !partner.isVendor() && deliveryDay.isToBeFetched())
		{
			throw new BPartnerNotVendorException(partner);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void preventChangingProcessedDeliveryDay(final I_M_DeliveryDay deliveryDay)
	{
		// If not processed => OK
		if (!deliveryDay.isProcessed())
		{
			return;
		}

		// If just changed to processed => accept it
		if (InterfaceWrapperHelper.isValueChanged(deliveryDay, I_M_DeliveryDay.COLUMNNAME_Processed))
		{
			return;
		}

		throw new AdempiereException("Changing a delivery day which was already processed is not allowed"
				+ "\n @M_DeliveryDay_ID@: " + deliveryDay);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateTourInstanceAssigment(final I_M_DeliveryDay deliveryDay)
	{
		final ITourInstanceBL tourInstanceBL = Services.get(ITourInstanceBL.class);
		tourInstanceBL.updateTourInstanceAssigment(deliveryDay);
		// NOTE: don't save it, we are in BEFORE... interceptor
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void updateTourInstanceOnDeliveryDayCreated(final I_M_DeliveryDay deliveryDay)
	{
		final I_M_Tour_Instance tourInstance = deliveryDay.getM_Tour_Instance();

		final I_M_DeliveryDay deliveryDayNew = deliveryDay;
		final I_M_DeliveryDay deliveryDayOld = null;
		updateTourInstanceFromDeliveryDay(tourInstance, deliveryDayNew, deliveryDayOld);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void updateTourInstanceOnDeliveryDayChanged(final I_M_DeliveryDay deliveryDay)
	{
		final I_M_DeliveryDay deliveryDayNew = deliveryDay;
		final I_M_DeliveryDay deliveryDayOld = InterfaceWrapperHelper.createOld(deliveryDay, I_M_DeliveryDay.class);

		final I_M_Tour_Instance tourInstanceNew = deliveryDay.getM_Tour_Instance_ID() <= 0 ? null : deliveryDay.getM_Tour_Instance();

		//
		// Case: Tour Instance have not changed
		final int tourInstanceIdOld = deliveryDayOld.getM_Tour_Instance_ID();
		if (tourInstanceNew != null && tourInstanceNew.getM_Tour_Instance_ID() == tourInstanceIdOld)
		{
			updateTourInstanceFromDeliveryDay(tourInstanceNew, deliveryDayNew, deliveryDayOld);
		}
		//
		// Case: Tour Instance have changed but there was no old tour instance
		// => delivery day was just added to tour instance
		else if (tourInstanceIdOld <= 0)
		{
			updateTourInstanceFromDeliveryDay(tourInstanceNew,
					deliveryDayNew,
					(I_M_DeliveryDay)null); // we are not using the old value because it's pointless
		}
		//
		// Case: Tour Instance have changed and there was another tour instance before
		// => remove old delivery day from old tour instance
		// => add new delivery day from new tour instance
		else
		{
			final I_M_Tour_Instance tourInstanceOld = deliveryDayOld.getM_Tour_Instance();

			updateTourInstanceFromDeliveryDay(tourInstanceOld,
					(I_M_DeliveryDay)null, // there is no new value
					deliveryDayOld);

			updateTourInstanceFromDeliveryDay(tourInstanceNew,
					deliveryDayNew,
					(I_M_DeliveryDay)null); // there is no old value

			deleteTourInstanceIfGenericAndNoDeliveryDays(tourInstanceOld);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void updateTourInstanceOnDeliveryDayDeleted(final I_M_DeliveryDay deliveryDay)
	{
		final I_M_Tour_Instance tourInstance = deliveryDay.getM_Tour_Instance();

		final I_M_DeliveryDay deliveryDayNew = null;
		final I_M_DeliveryDay deliveryDayOld = deliveryDay;
		updateTourInstanceFromDeliveryDay(tourInstance, deliveryDayNew, deliveryDayOld);

		deleteTourInstanceIfGenericAndNoDeliveryDays(tourInstance);
	}

	private final void updateTourInstanceFromDeliveryDay(final I_M_Tour_Instance tourInstance,
			final I_M_DeliveryDay deliveryDayNew,
			final I_M_DeliveryDay deliveryDayOld)
	{
		if (tourInstance == null || tourInstance.getM_Tour_Instance_ID() <= 0)
		{
			return;
		}

		Services.get(IDeliveryDayBL.class)
				.getDeliveryDayHandlers()
				.updateTourInstanceWhenDeliveryDayChanged(tourInstance, deliveryDayNew, deliveryDayOld);

		InterfaceWrapperHelper.save(tourInstance);
	}

	/**
	 * Deletes given tour instance if
	 * <ul>
	 * <li>it's a generic tour instance (see {@link ITourInstanceBL#isGenericTourInstance(I_M_Tour_Instance)})
	 * <li>has no {@link I_M_DeliveryDay}s assigned to it
	 * </ul>
	 * 
	 * In case given tour instance is null or it's not saved, it will be ignored.
	 * 
	 * @param tourInstance
	 * @return true if it was deleted
	 */
	private boolean deleteTourInstanceIfGenericAndNoDeliveryDays(final I_M_Tour_Instance tourInstance)
	{
		// Skip tour instances which are null or not already saved
		if (tourInstance == null)
		{
			return false;
		}
		if (tourInstance.getM_Tour_Instance_ID() <= 0)
		{
			return false;
		}

		// If it's not a generic tour instance => don't touch it
		if (!Services.get(ITourInstanceBL.class).isGenericTourInstance(tourInstance))
		{
			return false;
		}

		// If tour instance has assigned delivery days => don't touch it
		if (Services.get(ITourInstanceDAO.class).hasDeliveryDays(tourInstance))
		{
			return false;
		}

		//
		// Delete tour instance
		InterfaceWrapperHelper.delete(tourInstance);

		return true; // deleted
	}
	
	/**
	 * Make sure that delivery date time max is updated when delivery date or buffer are changed
	 */
	@ModelChange (timings = ModelValidator.TYPE_BEFORE_CHANGE, 
			ifColumnsChanged = {I_M_DeliveryDay.COLUMNNAME_DeliveryDate, I_M_DeliveryDay.COLUMNNAME_BufferHours})
	public void changeDeliveryDateMax(final I_M_DeliveryDay deliveryDay)
	{
		// Services
		final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);
		
		deliveryDayBL.setDeliveryDateTimeMax(deliveryDay);
		
	}
}
