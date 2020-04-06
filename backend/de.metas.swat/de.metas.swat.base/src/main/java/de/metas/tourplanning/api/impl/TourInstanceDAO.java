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

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;

import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.tourplanning.api.ITourInstanceDAO;
import de.metas.tourplanning.api.ITourInstanceQueryParams;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.util.Check;
import de.metas.util.Services;

public class TourInstanceDAO implements ITourInstanceDAO
{
	private IQueryFilter<I_M_Tour_Instance> createTourInstanceMatcher(final ITourInstanceQueryParams params)
	{
		Check.assumeNotNull(params, "params not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_M_Tour_Instance> filters = queryBL.createCompositeQueryFilter(I_M_Tour_Instance.class);

		//
		// Filter: Tour
		final I_M_Tour tour = params.getM_Tour();
		if (tour != null)
		{
			final int tourId = tour.getM_Tour_ID();
			Check.assume(tourId > 0, "Tour shall be saved: {}", tour);
			filters.addEqualsFilter(I_M_Tour_Instance.COLUMN_M_Tour_ID, tourId);
		}

		//
		// Filter: DeliveryDate
		final Date deliveryDate = params.getDeliveryDate();
		if (deliveryDate != null)
		{
			filters.addEqualsFilter(I_M_Tour_Instance.COLUMN_DeliveryDate, deliveryDate);
		}

		//
		// Filter: Processed
		final Boolean processed = params.getProcessed();
		if (processed != null)
		{
			filters.addEqualsFilter(I_M_Tour_Instance.COLUMN_Processed, processed);
		}

		//
		// Filter: Generic Tour Instance (i.e. M_ShipperTransportation_ID is null)
		final Boolean genericTourInstanceObj = params.getGenericTourInstance();
		if (genericTourInstanceObj == null)
		{
			// skip generic tour instance filtering
		}
		else if (genericTourInstanceObj == true)
		{
			filters.addEqualsFilter(I_M_Tour_Instance.COLUMN_M_ShipperTransportation_ID, null);
		}
		else
		// genericTourInstance == false
		{
			filters.addNotEqualsFilter(I_M_Tour_Instance.COLUMN_M_ShipperTransportation_ID, null);
		}

		//
		// Filter by M_ShipperTransportation_ID
		if (params.getM_ShipperTransportation_ID() > 0)
		{
			Check.assume(!Boolean.TRUE.equals(genericTourInstanceObj), "When filtering by M_ShipperTransportation_ID, genericTourInstance shall not be true: {}", params);
			filters.addEqualsFilter(I_M_Tour_Instance.COLUMN_M_ShipperTransportation_ID, params.getM_ShipperTransportation_ID());
		}

		return filters;
	}

	private boolean isOnlyOneTourInstanceExpected(final ITourInstanceQueryParams params)
	{
		Check.assumeNotNull(params, "params not null");

		if (params.getM_ShipperTransportation_ID() > 0)
		{
			return true;
		}

		return false;
	}

	@Override
	public I_M_Tour_Instance retrieveTourInstance(final Object contextProvider, final ITourInstanceQueryParams params)
	{
		Check.assumeNotNull(contextProvider, "contextProvider not null");

		final IQueryBuilder<I_M_Tour_Instance> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Tour_Instance.class, contextProvider)
				// Only active tour instances are relevant for us
				.addOnlyActiveRecordsFilter()
				// Matching our params
				.filter(createTourInstanceMatcher(params));

		queryBuilder.orderBy()
				.addColumn(I_M_Tour_Instance.COLUMNNAME_M_ShipperTransportation_ID, Direction.Descending, Nulls.Last);

		if (isOnlyOneTourInstanceExpected(params))
		{
			return queryBuilder
					.create()
					.firstOnly(I_M_Tour_Instance.class);
		}
		else
		{
			return queryBuilder
					.create()
					.first(I_M_Tour_Instance.class);
		}
	}

	@Override
	public boolean isTourInstanceMatches(final I_M_Tour_Instance tourInstance, final ITourInstanceQueryParams params)
	{
		if (tourInstance == null)
		{
			return false;
		}

		return createTourInstanceMatcher(params)
				.accept(tourInstance);
	}

	@Override
	public boolean hasDeliveryDays(final I_M_Tour_Instance tourInstance)
	{
		Check.assumeNotNull(tourInstance, "tourInstance not null");

		final int tourInstanceId = tourInstance.getM_Tour_Instance_ID();
		Check.assume(tourInstanceId > 0, "tourInstance shall not be a new/not saved one");

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DeliveryDay.class, tourInstance)
				// .addOnlyActiveRecordsFilter() // check all records
				// Delivery days for our tour instance
				.addEqualsFilter(I_M_DeliveryDay.COLUMN_M_Tour_Instance_ID, tourInstanceId)
				.create()
				.anyMatch();
	}

	public I_M_Tour_Instance retrieveTourInstanceForShipperTransportation(final I_M_ShipperTransportation shipperTransportation)
	{
		Check.assumeNotNull(shipperTransportation, "shipperTransportation not null");

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Tour_Instance.class, shipperTransportation)
				// .addOnlyActiveRecordsFilter()
				// Delivery days for our tour instance
				.addEqualsFilter(I_M_Tour_Instance.COLUMN_M_ShipperTransportation_ID, shipperTransportation.getM_ShipperTransportation_ID())
				.create()
				.firstOnly(I_M_Tour_Instance.class);

	}
}
