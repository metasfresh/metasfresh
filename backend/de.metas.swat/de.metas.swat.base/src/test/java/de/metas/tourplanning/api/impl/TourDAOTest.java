package de.metas.tourplanning.api.impl;

import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.api.ITourVersionRange;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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

		Assertions.assertEquals(3, tourVersionRanges.size(), "Invalid ranges count: " + tourVersionRanges);

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

		Assertions.assertEquals(1, tourVersionRanges.size(), "Invalid ranges count: " + tourVersionRanges);

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

		Assertions.assertEquals(1, tourVersionRanges.size(), "Invalid ranges count: " + tourVersionRanges);

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

		Assertions.assertEquals(1, tourVersionRanges.size(), "Invalid ranges count: " + tourVersionRanges);

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

		Assertions.assertEquals(0, tourVersionRanges.size(), "Invalid ranges count: " + tourVersionRanges);
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

		Assertions.assertEquals(1, tourVersionRanges.size(), "Invalid ranges count: " + tourVersionRanges);

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

		Assertions.assertEquals(2, tourVersionRanges.size(), "Invalid ranges count: " + tourVersionRanges);

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

		Assertions.assertEquals(0, tourVersionRanges.size(), "Invalid ranges count: " + tourVersionRanges);
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

		Assertions.assertEquals(0, tourVersionRanges.size(), "Invalid ranges count: " + tourVersionRanges);
	}

	@Test
	public void test_generateTourVersionRange_MultipleVersions()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2013, 5, 1));
		createTourVersion(tour, LocalDate.of(2014, 2, 1));
		createTourVersion(tour, LocalDate.of(2014, 5, 1));

		final ITourVersionRange tourVersionRange = tourDAO.generateTourVersionRange(tourVersion1,
				LocalDate.of(2013, 4, 1),
				LocalDate.of(2014, 12, 31)
		);
		final ITourVersionRange expextedTourVersionRange = TourVersionRange
				.builder()
				.tourVersion(tourVersion1)
				.validFrom(LocalDate.of(2013, 5, 1))
				.validTo(LocalDate.of(2014, 2, 1))
				.build();

		Assertions.assertEquals(expextedTourVersionRange.getM_TourVersion(), tourVersionRange.getM_TourVersion(), "Tourversion Range TourVersions match");
		Assertions.assertEquals(expextedTourVersionRange.getValidFrom(), tourVersionRange.getValidFrom(), "Tourversion Range Valid From match");
		Assertions.assertEquals(expextedTourVersionRange.getValidTo(), tourVersionRange.getValidTo(), "Tourversion Range Valid To match");
	}

	@Test
	public void test_generateTourVersionRange_OneVersion()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2013, 5, 1));

		final ITourVersionRange tourVersionRange = tourDAO.generateTourVersionRange(tourVersion1,
				LocalDate.of(2013, 1, 1),
				LocalDate.of(2014, 12, 31)
		);
		final ITourVersionRange expextedTourVersionRange = TourVersionRange
				.builder()
				.tourVersion(tourVersion1)
				.validFrom(LocalDate.of(2013, 5, 1))
				.validTo(LocalDate.of(2014, 12, 31))
				.build();

		Assertions.assertEquals(expextedTourVersionRange.getM_TourVersion(), tourVersionRange.getM_TourVersion(), "Tourversion Range TourVersions match");
		Assertions.assertEquals(expextedTourVersionRange.getValidFrom(), tourVersionRange.getValidFrom(), "Tourversion Range Valid From match");
		Assertions.assertEquals(expextedTourVersionRange.getValidTo(), tourVersionRange.getValidTo(), "Tourversion Range Valid To match");
	}

	@Test
	public void test_generateTourVersionRange_NoValidVersions()
	{
		final I_M_Tour tour = createTour("tour");
		final I_M_TourVersion tourVersion1 = createTourVersion(tour, LocalDate.of(2013, 5, 1));
		createTourVersion(tour, LocalDate.of(2014, 5, 5));

		final ITourVersionRange tourVersionRange = tourDAO.generateTourVersionRange(tourVersion1,
				LocalDate.of(2016, 1, 1),
				LocalDate.of(2017, 12, 31)
		);

		Assertions.assertEquals(null, tourVersionRange, "Tourversion Range TourVersions match");

	}

	private void assertTourVersionRange(ITourVersionRange tourVersionRange,
										I_M_TourVersion expectedTourVersion,
										String expectedValidFrom,
										String expectedValidTo)
	{
		final I_M_TourVersion actualTourVersion = tourVersionRange.getM_TourVersion();

		Assertions.assertEquals(expectedTourVersion, actualTourVersion, "Invalid Tour Version: " + tourVersionRange);
		Assertions.assertEquals(createDate(expectedValidFrom), tourVersionRange.getValidFrom(), "Invalid ValidFrom: " + tourVersionRange);
		Assertions.assertEquals(createDate(expectedValidTo), tourVersionRange.getValidTo(), "Invalid ValidTo: " + tourVersionRange);
	}
}
