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

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.NullLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IDeliveryDayGenerator;
import de.metas.tourplanning.api.ITourDAO;
import de.metas.tourplanning.api.ITourVersionRange;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.tourplanning.model.I_M_TourVersionLine;

public class DeliveryDayGenerator implements IDeliveryDayGenerator
{
	//
	// Services
	private final ITourDAO tourDAO = Services.get(ITourDAO.class);
	private final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);

	//
	// Parameters
	private final IContextAware context;
	private final ILoggable loggable;
	private Date dateFrom = null;
	private Date dateTo = null;
	private List<I_M_Tour> tours = null;

	private final DateFormat dateTimeFormat;

	//
	// Statistics
	private int countGeneratedDeliveryDays = 0;

	public DeliveryDayGenerator(final IContextAware context)
	{
		super();

		Check.assumeNotNull(context, "context not null");
		this.context = context;

		if (context instanceof ILoggable)
		{
			this.loggable = (ILoggable)context;
		}
		else
		{
			this.loggable = NullLoggable.instance;
		}

		this.dateTimeFormat = DisplayType.getDateFormat(DisplayType.DateTime);
	}

	@Override
	public void setDateFrom(final Date dateFrom)
	{
		this.dateFrom = dateFrom;
	}

	private Date getDateFromToUse()
	{
		Check.assumeNotNull(dateFrom, "dateFrom not null");
		return dateFrom;
	}

	@Override
	public void setDateTo(final Date dateTo)
	{
		this.dateTo = dateTo;
	}

	private Date getDateToToUse()
	{
		Check.assumeNotNull(dateTo, "dateTo not null");
		return dateTo;
	}

	@Override
	public void setM_Tour(final I_M_Tour tour)
	{
		if (tour == null)
		{
			this.tours = null;
		}
		else
		{
			tours = Collections.singletonList(tour);
		}
	}

	private List<I_M_Tour> retrieveTours()
	{
		if (tours == null)
		{
			final Properties ctx = context.getCtx();
			return tourDAO.retriveAllTours(ctx);
		}
		else
		{
			return tours;
		}
	}

	@Override
	public final void generate(final String trxName)
	{
		final List<I_M_Tour> tours = retrieveTours();
		for (final I_M_Tour tour : tours)
		{
			generateForTour(tour, trxName);
		}
	}

	private void generateForTour(final I_M_Tour tour, String trxName)
	{
		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour, getDateFromToUse(), getDateToToUse());

		for (final ITourVersionRange tourVersionRange : tourVersionRanges)
		{
			//
			// Deactivate/Delete all delivery days in our range
			inactivateDeliveryDaysInRange(tourVersionRange, trxName);

			//
			// Iterate tour version lines and create delivery days
			final I_M_TourVersion tourVersion = tourVersionRange.getM_TourVersion();
			final List<I_M_TourVersionLine> tourVersionLines = tourDAO.retrieveTourVersionLines(tourVersion);
			if (tourVersionLines.isEmpty())
			{
				loggable.addLog("Skip {} because it has no lines", tourVersion);
				continue;
			}
			for (final I_M_TourVersionLine tourVersionLine : tourVersionLines)
			{
				createDeliveryDaysForLine(tourVersionRange, tourVersionLine);
			}
		}
	}

	@Override
	public final int getCountGeneratedDeliveryDays()
	{
		return countGeneratedDeliveryDays;
	}

	/**
	 * Create entries in Delivery Day
	 * 
	 * @param tourVersionRange
	 * @param tourVersionLine Tour version line
	 */
	private void createDeliveryDaysForLine(final ITourVersionRange tourVersionRange, final I_M_TourVersionLine tourVersionLine)
	{
		final Set<Date> deliveryDates = tourVersionRange.generateDeliveryDates();
		if (deliveryDates.isEmpty())
		{
			loggable.addLog("Skip {} because no eligible delivery days were found in {}", tourVersionLine, tourVersionRange);
			return;
		}

		//
		// Iterates each period (e.g. each X months, each X weeks etc)
		for (final Date currentDeliveryDate : deliveryDates)
		{
			createDeliveryDay(tourVersionLine, currentDeliveryDate);
		}
	}

	/**
	 * create delivery day
	 * 
	 * @param tourVersionLine
	 * @param deliveryDatge
	 * @return created delivery day or null
	 */
	private I_M_DeliveryDay createDeliveryDay(final I_M_TourVersionLine tourVersionLine, final Date deliveryDate)
	{
		final String trxName = context.getTrxName();
		final I_M_DeliveryDay deliveryDay = deliveryDayBL.createDeliveryDay(tourVersionLine, deliveryDate, trxName);
		if (deliveryDay == null)
		{
			// not generated
			return null;
		}

		loggable.addLog("@Created@ @M_DeliveryDay_ID@ " + dateTimeFormat.format(deliveryDay.getDeliveryDate()));

		countGeneratedDeliveryDays++;

		return deliveryDay;
	}

	private void inactivateDeliveryDaysInRange(final ITourVersionRange tourVersionRange, final String trxName)
	{
		final I_M_TourVersion tourVersion = tourVersionRange.getM_TourVersion();

		final DateTruncQueryFilterModifier dateTruncModifier = DateTruncQueryFilterModifier.forTruncString(TimeUtil.TRUNC_DAY);

		final IQueryBuilder<I_M_DeliveryDay> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DeliveryDay.class, tourVersion)
				.addCompareFilter(I_M_DeliveryDay.COLUMN_DeliveryDate, Operator.GREATER_OR_EQUAL, tourVersionRange.getValidFrom(), dateTruncModifier)
				.addCompareFilter(I_M_DeliveryDay.COLUMN_DeliveryDate, Operator.LESS_OR_EQUAL, tourVersionRange.getValidTo(), dateTruncModifier)
				.addEqualsFilter(I_M_DeliveryDay.COLUMN_IsManual, false) // not manual entries
				.addEqualsFilter(I_M_DeliveryDay.COLUMN_M_Tour_ID, tourVersion.getM_Tour_ID());

		queryBuilder.orderBy()
				.addColumn(I_M_DeliveryDay.COLUMN_DeliveryDate);

		queryBuilder.create()
				.update(new IQueryUpdater<I_M_DeliveryDay>()
				{

					@Override
					public boolean update(final I_M_DeliveryDay deliveryDay)
					{
						deliveryDayBL.inactivate(deliveryDay, trxName);
						return MODEL_UPDATED;
					}
				});
	}

}
