/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.issue;

import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.serviceprovider.issue.interceptor.AddIssueProgressRequest;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.serviceprovider.timebooking.TimeBooking;
import de.metas.serviceprovider.timebooking.TimeBookingRepository;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;

import static de.metas.serviceprovider.TestConstants.MOCK_DATE_2020_03_07;
import static de.metas.serviceprovider.TestConstants.MOCK_DATE_2020_03_12;
import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_1_00;
import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_1_30;
import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_2_30;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_USER_ID;
import static de.metas.serviceprovider.issue.IssueTestHelper.buildAndStoreIssueRecord;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IssueServiceTest
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IssueRepository issueRepository = new IssueRepository(queryBL, ModelCacheInvalidationService.newInstanceForUnitTesting());
	private final TimeBookingRepository timeBookingRepository = new TimeBookingRepository(queryBL);
	private final IssueService issueService = new IssueService(issueRepository, timeBookingRepository);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Given the following issue hierarchy:
	 * <p>
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 * <p>
	 * with issues having:
	 * node 1) - latestActivityOnIssue = null
	 * - latestActivityOnSubIssues = MOCK_DATE_2020_03_07
	 * - aggregatedEffort = 1:30
	 * - issueEffort = 0:00
	 * <p>
	 * node 2) - latestActivityOnIssue = null
	 * - latestActivityOnSubIssues = MOCK_DATE_2020_03_07
	 * - aggregatedEffort = 1:30
	 * - issueEffort = 0:00
	 * <p>
	 * node 4) - latestActivityOnIssue = null
	 * - latestActivityOnSubIssues = null
	 * - aggregatedEffort = 1:30
	 * - issueEffort = 0:00
	 * <p>
	 * node 8) - latestActivityOnIssue = MOCK_DATE_2020_03_07
	 * - latestActivityOnSubIssues = null
	 * - aggregatedEffort = 1:30
	 * - issueEffort = 1:30
	 * When: {@link IssueService#addIssueProgress(AddIssueProgressRequest)} where request is:
	 * - IssueId = 8
	 * - bookedEffort = 1:00
	 * - bookedDate of the latest added S_TimeBooking = MOCK_DATE_2020_03_12
	 * Then:
	 * node 1) - latestActivityOnIssue = null
	 * - latestActivityOnSubIssues = MOCK_DATE_2020_03_12
	 * - aggregatedEffort = 2:30
	 * - issueEffort = 0:00
	 * <p>
	 * node 2) - latestActivityOnIssue = null
	 * - latestActivityOnSubIssues = MOCK_DATE_2020_03_12
	 * - aggregatedEffort = 2:30
	 * - issueEffort = 0:00
	 * <p>
	 * node 4) - latestActivityOnIssue = null
	 * - latestActivityOnSubIssues = MOCK_DATE_2020_03_12
	 * - aggregatedEffort = 2:30
	 * - issueEffort = 0:00
	 * <p>
	 * node 8) - latestActivityOnIssue = MOCK_DATE_2020_03_12
	 * - latestActivityOnSubIssues = null
	 * - aggregatedEffort = 2:30
	 * - issueEffort = 2:30
	 * <p>
	 * - meanwhile [3,5,6,7] will not be altered
	 */
	@Test
	public void addIssueProgress()
	{
		//Given
		prepareDataContext();

		final Instant instant_2020_03_12 = MOCK_DATE_2020_03_12.atStartOfDay(ZoneId.systemDefault()).toInstant();
		final Effort bookedEffort = Effort.ofNullable(MOCK_EFFORT_1_00);

		final TimeBooking timeBooking = TimeBooking
				.builder()
				.issueId(IssueId.ofRepoId(8))
				.performingUserId(MOCK_USER_ID)
				.hoursAndMins(bookedEffort.getHmm())
				.bookedSeconds(bookedEffort.getSeconds())
				.bookedDate(instant_2020_03_12)
				.orgId(MOCK_ORG_ID)
				.build();

		timeBookingRepository.save(timeBooking);

		final AddIssueProgressRequest addIssueProgressRequest = AddIssueProgressRequest
				.builder()
				.issueId(IssueId.ofRepoId(8))
				.bookedEffort(Effort.ofNullable(MOCK_EFFORT_1_00))
				.build();

		//when
		issueService.addIssueProgress(addIssueProgressRequest);

		//then
		assertEquals(MOCK_EFFORT_2_30, issueRepository.getById(IssueId.ofRepoId(8)).getIssueEffort().getHmm());
		assertEquals(MOCK_EFFORT_2_30, issueRepository.getById(IssueId.ofRepoId(8)).getAggregatedEffort().getHmm());
		assertEquals(instant_2020_03_12, issueRepository.getById(IssueId.ofRepoId(8)).getLatestActivityOnIssue());
		assertNull(issueRepository.getById(IssueId.ofRepoId(8)).getLatestActivityOnSubIssues());

		assertEquals(Effort.ZERO, issueRepository.getById(IssueId.ofRepoId(4)).getIssueEffort());
		assertEquals(MOCK_EFFORT_2_30, issueRepository.getById(IssueId.ofRepoId(4)).getAggregatedEffort().getHmm());
		assertNull(issueRepository.getById(IssueId.ofRepoId(4)).getLatestActivityOnIssue());
		assertEquals(instant_2020_03_12, issueRepository.getById(IssueId.ofRepoId(4)).getLatestActivityOnSubIssues());

		assertEquals(Effort.ZERO, issueRepository.getById(IssueId.ofRepoId(2)).getIssueEffort());
		assertEquals(MOCK_EFFORT_2_30, issueRepository.getById(IssueId.ofRepoId(2)).getAggregatedEffort().getHmm());
		assertNull(issueRepository.getById(IssueId.ofRepoId(2)).getLatestActivityOnIssue());
		assertEquals(instant_2020_03_12, issueRepository.getById(IssueId.ofRepoId(2)).getLatestActivityOnSubIssues());

		assertEquals(Effort.ZERO, issueRepository.getById(IssueId.ofRepoId(1)).getIssueEffort());
		assertEquals(MOCK_EFFORT_2_30, issueRepository.getById(IssueId.ofRepoId(1)).getAggregatedEffort().getHmm());
		assertEquals(instant_2020_03_12, issueRepository.getById(IssueId.ofRepoId(1)).getLatestActivityOnSubIssues());
		assertNull(issueRepository.getById(IssueId.ofRepoId(1)).getLatestActivityOnIssue());

		assertNull(issueRepository.getById(IssueId.ofRepoId(7)).getLatestActivity());
		assertNull(issueRepository.getById(IssueId.ofRepoId(6)).getLatestActivity());
		assertNull(issueRepository.getById(IssueId.ofRepoId(5)).getLatestActivity());
		assertNull(issueRepository.getById(IssueId.ofRepoId(3)).getLatestActivity());
	}

	/**
	 * Builds a Issue hierarchy as described below:
	 * <p>
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 */
	static void prepareDataContext()
	{
		final I_C_UOM mockUOMRecord = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.saveRecord(mockUOMRecord);
		final UomId effortUomId = UomId.ofRepoId(mockUOMRecord.getC_UOM_ID());

		buildAndStoreIssueRecord(null, IssueId.ofRepoId(1), null, effortUomId, null, MOCK_DATE_2020_03_07.atStartOfDay(ZoneId.systemDefault()).toInstant());
		buildAndStoreIssueRecord(IssueId.ofRepoId(1), IssueId.ofRepoId(2), null, effortUomId, null, MOCK_DATE_2020_03_07.atStartOfDay(ZoneId.systemDefault()).toInstant());
		buildAndStoreIssueRecord(IssueId.ofRepoId(1), IssueId.ofRepoId(3), null, effortUomId, null, null);
		buildAndStoreIssueRecord(IssueId.ofRepoId(2), IssueId.ofRepoId(4), null, effortUomId, null, MOCK_DATE_2020_03_07.atStartOfDay(ZoneId.systemDefault()).toInstant());
		buildAndStoreIssueRecord(IssueId.ofRepoId(3), IssueId.ofRepoId(5), null, effortUomId, null, null);
		buildAndStoreIssueRecord(IssueId.ofRepoId(4), IssueId.ofRepoId(6), null, effortUomId, null, null);
		buildAndStoreIssueRecord(IssueId.ofRepoId(4), IssueId.ofRepoId(7), null, effortUomId, null, null);
		buildAndStoreIssueRecord(IssueId.ofRepoId(4), IssueId.ofRepoId(8), MOCK_EFFORT_1_30, effortUomId, MOCK_DATE_2020_03_07.atStartOfDay(ZoneId.systemDefault()).toInstant(), null);
	}
}
