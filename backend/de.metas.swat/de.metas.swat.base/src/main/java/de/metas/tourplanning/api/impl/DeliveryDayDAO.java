/**
 *
 */
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.lang.IContextAware;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IDeliveryDayDAO;
import de.metas.tourplanning.api.IDeliveryDayQueryParams;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * @author cg
 *
 */
public class DeliveryDayDAO implements IDeliveryDayDAO
{
	/**
	 * Creates {@link I_M_DeliveryDay} filter which will match delivery days for given parameters.
	 *
	 * @param params
	 * @return filter
	 */
	private final IQueryFilter<I_M_DeliveryDay> createDeliveryDayMatcher(@NonNull final IDeliveryDayQueryParams params)
	{
		Check.assumeNotNull(params, "params not null");
		
		final BPartnerLocationId partnerLocationId = params.getBPartnerLocationId() == null ? null : params.getBPartnerLocationId();
		final BPartnerId partnerId = partnerLocationId == null ? null : partnerLocationId.getBpartnerId();

		final ICompositeQueryFilter<I_M_DeliveryDay> filter = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(I_M_DeliveryDay.class)
				//
				// Only active delivery days
				.addOnlyActiveRecordsFilter()
				//
				// for given BP & Location
				.addInArrayFilter(I_M_DeliveryDay.COLUMNNAME_C_BPartner_ID, null, partnerId)
				.addInArrayFilter(I_M_DeliveryDay.COLUMNNAME_C_BPartner_Location_ID, null, partnerLocationId)
				//
				// DeliveryDateTimeMax <= given DeliveryDate
				.addCompareFilter(I_M_DeliveryDay.COLUMNNAME_DeliveryDateTimeMax, Operator.LESS_OR_EQUAL, params.getDeliveryDate())
				//
				// IsToBeFetched flag shall match
				.addEqualsFilter(I_M_DeliveryDay.COLUMNNAME_IsToBeFetched, params.isToBeFetched());

		// task 09004 : In case calculation time is set, fetch the next date that fits.
		final LocalDate preparationDay = params.getPreparationDay();

		if (preparationDay != null)
		{
			filter.addCompareFilter(I_M_DeliveryDay.COLUMNNAME_DeliveryDateTimeMax, Operator.GREATER_OR_EQUAL, preparationDay);
		}

		final Boolean processed = params.getProcessed();
		if (processed != null)
		{
			filter.addEqualsFilter(I_M_DeliveryDay.COLUMNNAME_Processed, processed);
		}

		return filter;
	}

	@Override
	public I_M_DeliveryDay retrieveDeliveryDay(final IContextAware context, final IDeliveryDayQueryParams params)
	{
		final IQueryBuilder<I_M_DeliveryDay> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DeliveryDay.class, context)
				.filter(createDeliveryDayMatcher(params));

		// Make sure the deliveryDay is after the time of calculation
		final ZonedDateTime calculationTime = params.getCalculationTime();
		if (calculationTime != null)
		{
			queryBuilder.addCompareFilter(I_M_DeliveryDay.COLUMN_DeliveryDate, Operator.GREATER_OR_EQUAL, calculationTime);
		}

		// the delivery days that are in the same day as the datePromised have priority over the earlier ones.
		final LocalDate preparationDay = params.getPreparationDay();
		if (preparationDay != null)
		{
			queryBuilder.addCompareFilter(I_M_DeliveryDay.COLUMN_DeliveryDate, Operator.GREATER_OR_EQUAL, preparationDay);

			queryBuilder.orderBy()
					.addColumn(I_M_DeliveryDay.COLUMN_DeliveryDate, Direction.Ascending, Nulls.Last);
		}
		else
		{
			// fallback to what it used to be: In case there is no calculation time set, simply fetch the
			// delivery date that is closest to the promiseDate
			queryBuilder.orderBy()
					.addColumn(I_M_DeliveryDay.COLUMN_DeliveryDate, Direction.Descending, Nulls.Last)
					.addColumn(I_M_DeliveryDay.COLUMN_C_BPartner_Location_ID, Direction.Descending, Nulls.Last);
		}

		final I_M_DeliveryDay deliveryDay = queryBuilder
				.create()
				.first();

		return deliveryDay;
	}

	@Override
	public List<I_M_DeliveryDay> retrieveDeliveryDaysForTourInstance(final I_M_Tour_Instance tourInstance)
	{
		Check.assumeNotNull(tourInstance, "tourInstance not null");

		final IQueryBuilder<I_M_DeliveryDay> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DeliveryDay.class, tourInstance)
				// .addOnlyActiveRecordsFilter() // get all
				.addEqualsFilter(I_M_DeliveryDay.COLUMN_M_Tour_Instance_ID, tourInstance.getM_Tour_Instance_ID());

		queryBuilder.orderBy()
				.addColumn(I_M_DeliveryDay.COLUMN_SeqNo, Direction.Ascending, Nulls.Last);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public boolean isDeliveryDayMatches(final I_M_DeliveryDay deliveryDay, final IDeliveryDayQueryParams params)
	{
		if (deliveryDay == null)
		{
			return false;
		}

		return createDeliveryDayMatcher(params)
				.accept(deliveryDay);
	}

	@Override
	public List<I_M_DeliveryDay> retrieveDeliveryDays(final I_M_Tour tour, final Timestamp deliveryDate)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_M_DeliveryDay> queryBuilder = queryBL.createQueryBuilder(I_M_DeliveryDay.class, tour)
				.addEqualsFilter(I_M_DeliveryDay.COLUMN_M_Tour_ID, tour.getM_Tour_ID())
				.addEqualsFilter(I_M_DeliveryDay.COLUMN_DeliveryDate, deliveryDate, DateTruncQueryFilterModifier.DAY)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_DeliveryDay.COLUMN_DeliveryDate, Direction.Ascending, Nulls.Last);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public I_M_DeliveryDay_Alloc retrieveDeliveryDayAllocForModel(final IContextAware context, final IDeliveryDayAllocable deliveryDayAllocable)
	{
		Check.assumeNotNull(deliveryDayAllocable, "deliveryDayAllocable not null");

		final String tableName = deliveryDayAllocable.getTableName();
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final int recordId = deliveryDayAllocable.getRecord_ID();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DeliveryDay_Alloc.class, context)
				.addEqualsFilter(I_M_DeliveryDay_Alloc.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_DeliveryDay_Alloc.COLUMN_Record_ID, recordId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_DeliveryDay_Alloc.class);
	}

	@Override
	public boolean hasAllocations(final I_M_DeliveryDay deliveryDay)
	{
		Check.assumeNotNull(deliveryDay, "deliveryDay not null");

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DeliveryDay_Alloc.class, deliveryDay)
				.addEqualsFilter(I_M_DeliveryDay_Alloc.COLUMN_M_DeliveryDay_ID, deliveryDay.getM_DeliveryDay_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}
}
