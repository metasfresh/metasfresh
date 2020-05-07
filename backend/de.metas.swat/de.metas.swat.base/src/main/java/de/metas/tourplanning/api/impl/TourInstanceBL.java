package de.metas.tourplanning.api.impl;

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


import java.util.Date;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;

import de.metas.tourplanning.api.IDeliveryDayDAO;
import de.metas.tourplanning.api.ITourInstanceBL;
import de.metas.tourplanning.api.ITourInstanceDAO;
import de.metas.tourplanning.api.ITourInstanceQueryParams;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_Tour_Instance;

public class TourInstanceBL implements ITourInstanceBL
{
	private I_M_Tour_Instance createGenericTourInstance(final IContextAware context, final ITourInstanceQueryParams params)
	{
		final I_M_Tour_Instance tourInstanceNew = createTourInstanceDraft(context, params);
		tourInstanceNew.setM_ShipperTransportation_ID(-1); // a generic tour instance does not have a shipper transportation
		InterfaceWrapperHelper.save(tourInstanceNew);
		return tourInstanceNew;
	}

	@Override
	public boolean isGenericTourInstance(final I_M_Tour_Instance tourInstance)
	{
		Check.assumeNotNull(tourInstance, "tourInstance not null");

		final ITourInstanceQueryParams params = new PlainTourInstanceQueryParams();
		params.setGenericTourInstance(true);

		return Services.get(ITourInstanceDAO.class).isTourInstanceMatches(tourInstance, params);
	}

	/**
	 * Creates a draft(not saved) {@link I_M_Tour_Instance}.
	 *
	 * @param params
	 * @return
	 */
	private I_M_Tour_Instance createTourInstanceDraft(final IContextAware context, final ITourInstanceQueryParams params)
	{
		Check.assumeNotNull(params, "params not null");

		final I_M_Tour tour = params.getM_Tour();
		Check.assumeNotNull(tour, "tour not null");

		final Date deliveryDate = params.getDeliveryDate();
		Check.assumeNotNull(deliveryDate, "deliveryDate not null");

		final I_M_Tour_Instance tourInstance = InterfaceWrapperHelper.newInstance(I_M_Tour_Instance.class, context);
		tourInstance.setM_Tour(tour);
		tourInstance.setDeliveryDate(TimeUtil.asTimestamp(deliveryDate));
		tourInstance.setProcessed(false);

		return tourInstance;
	}

	@Override
	public I_M_Tour_Instance createTourInstanceDraft(final IContextAware context, final I_M_DeliveryDay deliveryDay)
	{
		Check.assumeNotNull(deliveryDay, "deliveryDay not null");

		final ITourInstanceQueryParams tourInstanceParams = createTourInstanceQueryParams(deliveryDay);
		return createTourInstanceDraft(context, tourInstanceParams);
	}

	@Override
	public void updateTourInstanceAssigment(final I_M_DeliveryDay deliveryDay)
	{
		final ITourInstanceDAO tourInstanceDAO = Services.get(ITourInstanceDAO.class);

		//
		// Case: Tour Instance Assignment Required
		final boolean tourInstanceAssignmentRequired = isTourInstanceAssignmentRequired(deliveryDay);
		if (tourInstanceAssignmentRequired)
		{
			//
			// Create Tour Instance matcher
			final ITourInstanceQueryParams genericTourInstanceMatcher = createTourInstanceQueryParams(deliveryDay);
			// we are searching only for tour instances which are not processed
			genericTourInstanceMatcher.setProcessed(false);
			// we are searching only for generic tour instances, because assigning to a non-generic tour instance is allowed only by user
			genericTourInstanceMatcher.setGenericTourInstance(true);

			//
			// If this delivery day is already assigned to a tour instance
			// => do nothing, we are not allowed to change the tour instance because it will fuck-up the planning
			if (deliveryDay.getM_Tour_Instance_ID() > 0)
			{
				final I_M_Tour_Instance tourInstance = deliveryDay.getM_Tour_Instance();
				if (!isAllowChangingTourInstanceAutomatically(tourInstance))
				{
					// we are not allowed to un-assign from this tour
					return;
				}

				// If tour instance is not matching, un-assign from it
				if (!tourInstanceDAO.isTourInstanceMatches(tourInstance, genericTourInstanceMatcher))
				{
					unassignFromTourInstance(deliveryDay);
				}
			}

			//
			// If there is no tour instance assignment => find one => if not found, create one
			if (deliveryDay.getM_Tour_Instance_ID() <= 0)
			{
				final IContextAware contextProvider = Services.get(ITrxManager.class).createThreadContextAware(deliveryDay);
				I_M_Tour_Instance tourInstanceNew = tourInstanceDAO.retrieveTourInstance(contextProvider, genericTourInstanceMatcher);
				if (tourInstanceNew == null)
				{
					tourInstanceNew = createGenericTourInstance(contextProvider, genericTourInstanceMatcher);
				}
				assignToTourInstance(deliveryDay, tourInstanceNew);
			}
		}
		//
		// Case: Tour Instance Assignment is Not Required
		else
		{
			final I_M_Tour_Instance tourInstance = deliveryDay.getM_Tour_Instance();

			// If we have an tour instance which allows us to change the assignments automatically then unassign from it
			if (isAllowChangingTourInstanceAutomatically(tourInstance))
			{
				unassignFromTourInstance(deliveryDay);
			}
			// Else, there is no tour instance assignment, or the tour instance assignment is not allowing us to change the assignment
			else
			{
				// => do nothing
			}
		}
	}

	/**
	 * Check if given tour instance allows us to change the assignments (assign/unassign) automatically (i.e. no user input).
	 *
	 * @param tourInstance
	 * @return true if automatically assignments change is allowed
	 */
	private final boolean isAllowChangingTourInstanceAutomatically(final I_M_Tour_Instance tourInstance)
	{
		if (tourInstance == null)
		{
			return false;
		}

		// Don't allow to assign/unassign delivery days to a not saved tour instance
		if (tourInstance.getM_Tour_Instance_ID() <= 0)
		{
			return false;
		}

		// Don't allow to assign/unassign delivery days to a Processed tour instance
		if (tourInstance.isProcessed())
		{
			return false;
		}

		// Allow changing tour instance assignments only if it's a generic tour
		return isGenericTourInstance(tourInstance);
	}

	@Override
	public void assignToTourInstance(final I_M_DeliveryDay deliveryDay, final I_M_Tour_Instance tourInstance)
	{
		Check.assumeNotNull(tourInstance, "tourInstance not null");
		Check.assume(tourInstance.getM_Tour_Instance_ID() > 0, "tourInstance shall be saved: {}", tourInstance);

		deliveryDay.setM_Tour_Instance(tourInstance);
		// NOTE: please don't save it because it could be that is not the only change or we are in a before/after save model interceptor
	}

	private final void unassignFromTourInstance(final I_M_DeliveryDay deliveryDay)
	{
		deliveryDay.setM_Tour_Instance(null);
		// NOTE: please don't save it because it could be that is not the only change or we are in a before/after save model interceptor
	}

	/**
	 * Checks if we need to have a tour instance assignment for our given delivery day
	 *
	 * @param deliveryDay
	 * @return true if an assignment is required
	 */
	private final boolean isTourInstanceAssignmentRequired(final I_M_DeliveryDay deliveryDay)
	{
		// Tour assignment (change) is not allowed if our delivery day is already processed
		if (deliveryDay.isProcessed())
		{
			return false;
		}

		//
		// If Delivery Day has documents allocated to it, we really need to assign it to a tour
		final IDeliveryDayDAO deliveryDayDAO = Services.get(IDeliveryDayDAO.class);
		final boolean tourInstanceAssignmentRequired = deliveryDayDAO.hasAllocations(deliveryDay);
		return tourInstanceAssignmentRequired;
	}

	private ITourInstanceQueryParams createTourInstanceQueryParams(final I_M_DeliveryDay deliveryDay)
	{
		final ITourInstanceQueryParams tourInstanceMatchingParams = new PlainTourInstanceQueryParams();
		tourInstanceMatchingParams.setM_Tour(deliveryDay.getM_Tour());
		tourInstanceMatchingParams.setDeliveryDate(deliveryDay.getDeliveryDate());
		return tourInstanceMatchingParams;
	}

	@Override
	public void process(final I_M_Tour_Instance tourInstance)
	{
		Check.assumeNotNull(tourInstance, "tourInstance not null");
		tourInstance.setProcessed(true);
		InterfaceWrapperHelper.save(tourInstance);
	}

	@Override
	public void unprocess(final I_M_Tour_Instance tourInstance)
	{
		Check.assumeNotNull(tourInstance, "tourInstance not null");
		tourInstance.setProcessed(false);
		InterfaceWrapperHelper.save(tourInstance);
	}

}
