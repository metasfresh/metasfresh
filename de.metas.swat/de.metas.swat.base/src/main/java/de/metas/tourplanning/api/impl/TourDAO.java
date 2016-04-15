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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.tourplanning.api.ITourDAO;
import de.metas.tourplanning.api.ITourVersionRange;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.tourplanning.model.I_M_TourVersionLine;

/**
 * @author cg
 * 
 */
public class TourDAO implements ITourDAO
{
	@Override
	public List<I_M_Tour> retriveAllTours(final Properties ctx)
	{
		final IQueryBuilder<I_M_Tour> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Tour.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_Tour.COLUMNNAME_M_Tour_ID);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public List<I_M_TourVersion> retrieveTourVersions(final I_M_Tour tour)
	{
		final Date validFrom = null;
		final Date validTo = null;
		return retrieveTourVersions(tour, validFrom, validTo);
	}

	private IQueryBuilder<I_M_TourVersion> createTourVersionQueryBuilder(final I_M_Tour tour, final Date validFrom, final Date validTo)
	{
		Check.assumeNotNull(tour, "tour not null");

		final IQueryBuilder<I_M_TourVersion> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_TourVersion.class, tour)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_TourVersion.COLUMN_M_Tour_ID, tour.getM_Tour_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		if (validFrom != null)
		{
			queryBuilder.addCompareFilter(I_M_TourVersion.COLUMNNAME_ValidFrom, Operator.GREATER_OR_EQUAL, validFrom);
		}
		if (validTo != null)
		{
			queryBuilder.addCompareFilter(I_M_TourVersion.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, validTo);
		}

		return queryBuilder;
	}

	private List<I_M_TourVersion> retrieveTourVersions(final I_M_Tour tour, final Date validFrom, final Date validTo)
	{
		Check.assumeNotNull(tour, "tour not null");

		final IQueryBuilder<I_M_TourVersion> queryBuilder = createTourVersionQueryBuilder(tour, validFrom, validTo);

		queryBuilder.orderBy()
				.clear()
				.addColumn(I_M_TourVersion.COLUMN_ValidFrom, Direction.Ascending, Nulls.First);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public List<ITourVersionRange> retrieveTourVersionRanges(final I_M_Tour tour, final Date dateFrom, final Date dateTo)
	{
		Check.assumeNotNull(dateFrom, "dateFrom not null");
		Check.assumeNotNull(dateTo, "dateTo not null");
		Check.assume(dateFrom.compareTo(dateTo) <= 0, "dateFrom({}) <= dateTo({})", dateFrom, dateTo);

		//
		// Retrieve all tour versions in our scope
		// NOTE: we assume they are already ordered by ValidFrom
		// NOTE2: we are not restricting the dateFrom because we want to also get the tour version which is currently active at the beginning of our interval
		final List<I_M_TourVersion> tourVersions = retrieveTourVersions(tour, null, dateTo);
		if (tourVersions.isEmpty())
		{
			return Collections.emptyList();
		}

		//
		// Continue iterating the tour versions and create Tour Version Ranges
		List<ITourVersionRange> tourVersionRanges = new ArrayList<ITourVersionRange>();
		boolean previousTourVersionValid = false;
		I_M_TourVersion previousTourVersion = null;
		Date previousTourVersionValidFrom = null;

		final Iterator<I_M_TourVersion> tourVersionsIterator = tourVersions.iterator();
		while (tourVersionsIterator.hasNext())
		{
			final I_M_TourVersion tourVersion = tourVersionsIterator.next();

			final Timestamp tourVersionValidFrom = tourVersion.getValidFrom();
			Check.assumeNotNull(tourVersionValidFrom, "tourVersionValidFrom not null");

			//
			// Guard: tour version's ValidFrom shall be before "dateTo"
			if (tourVersionValidFrom.compareTo(dateTo) > 0)
			{
				// shall not happen because we retrieved until dateTo, but just to make sure
				break;
			}

			//
			// Case: We are still searching for first tour version to consider
			if (!previousTourVersionValid)
			{
				// Case: our current tour version is before given dateFrom
				if (tourVersionValidFrom.compareTo(dateFrom) < 0)
				{
					if (tourVersionsIterator.hasNext())
					{
						// do nothing, let's see what we get next
					}
					else
					{
						// there is no other next, so we need to consider this one
						previousTourVersion = tourVersion;
						previousTourVersionValidFrom = dateFrom;
						previousTourVersionValid = true;
						continue;
					}
				}
				// Case: our current tour version starts exactly on our given dateFrom
				else if (tourVersionValidFrom.compareTo(dateFrom) == 0)
				{
					previousTourVersion = tourVersion;
					previousTourVersionValidFrom = dateFrom;
					previousTourVersionValid = true;
					continue;
				}
				// Case: our current tour version start after our given dateFrom
				else
				{
					// Check if we have a previous tour version, because if we have, that shall be the first tour version to consider
					if (previousTourVersion != null)
					{
						// NOTE: we consider dateFrom as first date because tour version's ValidFrom is before dateFrom
						previousTourVersionValidFrom = dateFrom;
						previousTourVersionValid = true;
						// don't continue: we got it right now
						// continue;
					}
					// ... else it seems this is the first tour version which actually starts after our dateFrom
					else
					{
						previousTourVersion = tourVersion;
						previousTourVersionValidFrom = tourVersionValidFrom;
						previousTourVersionValid = true;
						continue;
					}
				}

			}

			//
			// Case: we do have a previous valid tour version to consider so we can generate tour ranges
			if (previousTourVersionValid)
			{
				final Date previousTourVersionValidTo = TimeUtil.addDays(tourVersionValidFrom, -1);
				final ITourVersionRange previousTourVersionRange = new TourVersionRange(previousTourVersion, previousTourVersionValidFrom, previousTourVersionValidTo);
				tourVersionRanges.add(previousTourVersionRange);
			}

			//
			// Set current as previous and move on
			previousTourVersion = tourVersion;
			previousTourVersionValidFrom = tourVersionValidFrom;
		}

		//
		// Create Tour Version Range for last version
		if (previousTourVersionValid)
		{
			final ITourVersionRange lastTourVersionRange = new TourVersionRange(previousTourVersion, previousTourVersionValidFrom, dateTo);
			tourVersionRanges.add(lastTourVersionRange);
		}

		return tourVersionRanges;
	}

	@Override
	public List<I_M_TourVersionLine> retrieveTourVersionLines(final I_M_TourVersion tourVersion)
	{
		Check.assumeNotNull(tourVersion, "tourVersion not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(tourVersion);
		final String trxName = InterfaceWrapperHelper.getTrxName(tourVersion);
		final int tourVersionId = tourVersion.getM_TourVersion_ID();

		final List<I_M_TourVersionLine> tourVersionLines = retrieveTourVersionLines(ctx, tourVersionId, trxName);

		// Optimization: set M_TourVersion model in case somebody want to get it, it shall retrieve the cached version
		for (I_M_TourVersionLine tourVersionLine : tourVersionLines)
		{
			tourVersionLine.setM_TourVersion(tourVersion);
		}

		return tourVersionLines;
	}

	@Cached(cacheName = I_M_TourVersionLine.Table_Name
			+ "#by"
			+ "#" + I_M_TourVersionLine.COLUMNNAME_M_TourVersion_ID)
	List<I_M_TourVersionLine> retrieveTourVersionLines(@CacheCtx final Properties ctx, final int tourVersionId, @CacheTrx final String trxName)
	{
		final IQueryBuilder<I_M_TourVersionLine> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_TourVersionLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_TourVersionLine.COLUMN_M_TourVersion_ID, tourVersionId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		queryBuilder.orderBy()
				.addColumn(I_M_TourVersionLine.COLUMN_SeqNo, Direction.Ascending, Nulls.First);

		final List<I_M_TourVersionLine> tourVersionLines = queryBuilder
				.create()
				.list();

		return tourVersionLines;
	}
}
