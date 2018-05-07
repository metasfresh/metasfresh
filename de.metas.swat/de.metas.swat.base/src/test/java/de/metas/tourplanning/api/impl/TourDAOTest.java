package de.metas.tourplanning.api.impl;

import java.time.LocalDate;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Assert;
import org.junit.Test;

import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.api.ITourVersionRange;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;

public class TourDAOTest extends TourPlanningTestBase
{
	@Override
	protected void afterInit()
	{
		// nothing
	}
	
	@Test
	public void test_retrieveTourVersionRanges_MultipleVersions_AcrossInterval()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2013, 1, 1));
		final I_M_TourVersion tourVersion2 = createTourVersion(tour, LocalDate.of(2014, 2, 1));
		final I_M_TourVersion tourVersion3 = createTourVersion(tour, LocalDate.of(2014, 5, 1));
		createTourVersion(tour, LocalDate.of(2015, 1, 1));

		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour,
				LocalDate.of(2014, 1, 1),
				LocalDate.of(2014, 12, 31)
				);

		Assert.assertEquals("Invalid ranges count: " + tourVersionRanges, 3, tourVersionRanges.size());

		assertTourVersionRange(tourVersionRanges.get(0), tourVersion1, "2014-01-01", "2014-01-31");
		assertTourVersionRange(tourVersionRanges.get(1), tourVersion2, "2014-02-01", "2014-04-30");
		assertTourVersionRange(tourVersionRanges.get(2), tourVersion3, "2014-05-01", "2014-12-31");
	}

	@Test
	public void test_retrieveTourVersionRanges_OneVersion_BeforeRangeStart()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2014, 8, 1));

		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour,
				LocalDate.of(2014, 9, 1),
				LocalDate.of(2014, 9, 30)
				);

		Assert.assertEquals("Invalid ranges count: " + tourVersionRanges, 1, tourVersionRanges.size());

		assertTourVersionRange(tourVersionRanges.get(0), tourVersion1, "2014-09-01", "2014-09-30");
	}

	@Test
	public void test_retrieveTourVersionRanges_OneVersion_OnRangeStart()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2014, 1, 1));

		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour,
				LocalDate.of(2014, 1, 1),
				LocalDate.of(2014, 12, 31)
				);

		Assert.assertEquals("Invalid ranges count: " + tourVersionRanges, 1, tourVersionRanges.size());

		assertTourVersionRange(tourVersionRanges.get(0), tourVersion1, "2014-01-01", "2014-12-31");
	}

	@Test
	public void test_retrieveTourVersionRanges_OneVersion_OnRangeEnd()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2014, 12, 31));

		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour,
				LocalDate.of(2014, 1, 1),
				LocalDate.of(2014, 12, 31)
				);

		Assert.assertEquals("Invalid ranges count: " + tourVersionRanges, 1, tourVersionRanges.size());

		assertTourVersionRange(tourVersionRanges.get(0), tourVersion1, "2014-12-31", "2014-12-31");
	}

	@Test
	public void test_retrieveTourVersionRanges_OneVersion_AfterRangeEnd()
	{
		final I_M_Tour tour = createTour("tour");
		createTourVersion(tour, LocalDate.of(2014, 10, 1));

		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour,
				LocalDate.of(2014, 9, 1),
				LocalDate.of(2014, 9, 30)
				);

		Assert.assertEquals("Invalid ranges count: " + tourVersionRanges, 0, tourVersionRanges.size());
	}

	@Test
	public void test_retrieveTourVersionRanges_OneVersion_OnMiddleOfRange()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2014, 6, 1));

		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour,
				LocalDate.of(2014, 1, 1),
				LocalDate.of(2014, 12, 31)
				);

		Assert.assertEquals("Invalid ranges count: " + tourVersionRanges, 1, tourVersionRanges.size());

		assertTourVersionRange(tourVersionRanges.get(0), tourVersion1, "2014-06-01", "2014-12-31");
	}

	@Test
	public void test_retrieveTourVersionRanges_TwoVersion_OnSubsequentDates()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2014, 6, 1));
		final I_M_TourVersion tourVersion2 = createTourVersion(tour, LocalDate.of(2014, 6, 2));

		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour,
				LocalDate.of(2014, 1, 1),
				LocalDate.of(2014, 12, 31)
				);

		Assert.assertEquals("Invalid ranges count: " + tourVersionRanges, 2, tourVersionRanges.size());

		assertTourVersionRange(tourVersionRanges.get(0), tourVersion1, "2014-06-01", "2014-06-01");
		assertTourVersionRange(tourVersionRanges.get(1), tourVersion2, "2014-06-02", "2014-12-31");
	}

	@Test
	public void test_retrieveTourVersionRanges_NoVersionsInRange()
	{
		final I_M_Tour tour = createTour("tour");
		createTourVersion(tour, LocalDate.of(2014, 6, 1));
		createTourVersion(tour, LocalDate.of(2014, 6, 2));

		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour,
				LocalDate.of(2014, 1, 1),
				LocalDate.of(2014, 5, 31)
				);

		Assert.assertEquals("Invalid ranges count: " + tourVersionRanges, 0, tourVersionRanges.size());
	}

	@Test
	public void test_retrieveTourVersionRanges_OnlyInactiveVersions()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2014, 1, 1));
		tourVersion1.setIsActive(false);
		InterfaceWrapperHelper.save(tourVersion1);

		final List<ITourVersionRange> tourVersionRanges = tourDAO.retrieveTourVersionRanges(tour,
				LocalDate.of(2014, 1, 1),
				LocalDate.of(2014, 12, 31)
				);

		Assert.assertEquals("Invalid ranges count: " + tourVersionRanges, 0, tourVersionRanges.size());
	}

	private void assertTourVersionRange(ITourVersionRange tourVersionRange,
			I_M_TourVersion expectedTourVersion,
			String expectedValidFrom,
			String expectedValidTo)
	{
		final I_M_TourVersion actualTourVersion = tourVersionRange.getM_TourVersion();

		Assert.assertEquals("Invalid Tour Version: " + tourVersionRange, expectedTourVersion, actualTourVersion);
		Assert.assertEquals("Invalid ValidFrom: " + tourVersionRange, createDate(expectedValidFrom), tourVersionRange.getValidFrom());
		Assert.assertEquals("Invalid ValidTo: " + tourVersionRange, createDate(expectedValidTo), tourVersionRange.getValidTo());
	}
}
