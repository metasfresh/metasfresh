/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.planning.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.planning.ProductPlanningId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.eevolution.model.I_PP_Order_Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class PPOrderCandidateRepositoryTest
{
	private static final int SCHEDULE_1 = 1_000_001;
	private static final int SCHEDULE_2 = 1_000_002;
	private static final int PLANNING_1 = 2_000_001;
	private static final int PLANNING_2 = 2_000_002;

	private PPOrderCandidateRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new PPOrderCandidateRepository();
	}

	private I_PP_Order_Candidate createCandidate(
			final int shipmentScheduleId,
			final int productPlanningId,
			final boolean active,
			final String qtyEntered)
	{
		final I_PP_Order_Candidate record = newInstance(I_PP_Order_Candidate.class);
		record.setM_ShipmentSchedule_ID(shipmentScheduleId);
		record.setPP_Product_Planning_ID(productPlanningId);
		record.setIsActive(active);
		record.setQtyEntered(new BigDecimal(qtyEntered));
		saveRecord(record);
		return record;
	}

	@Test
	void returns_only_active_records_of_the_given_schedule_and_planning_ordered_by_id()
	{
		final I_PP_Order_Candidate match1 = createCandidate(SCHEDULE_1, PLANNING_1, true, "10");
		final I_PP_Order_Candidate match2 = createCandidate(SCHEDULE_1, PLANNING_1, true, "5");
		createCandidate(SCHEDULE_1, PLANNING_1, false, "99"); // inactive -> excluded
		createCandidate(SCHEDULE_2, PLANNING_1, true, "7");   // other schedule -> excluded
		createCandidate(SCHEDULE_1, PLANNING_2, true, "8");   // other planning -> excluded

		final ImmutableList<I_PP_Order_Candidate> result =
				repository.retrieveActiveByShipmentScheduleAndPlanning(ShipmentScheduleId.ofRepoId(SCHEDULE_1), ProductPlanningId.ofRepoId(PLANNING_1));

		assertThat(result)
				.extracting(I_PP_Order_Candidate::getPP_Order_Candidate_ID)
				.containsExactly(match1.getPP_Order_Candidate_ID(), match2.getPP_Order_Candidate_ID());
	}

	@Test
	void returns_empty_when_nothing_matches()
	{
		createCandidate(SCHEDULE_2, PLANNING_2, true, "10");

		assertThat(repository.retrieveActiveByShipmentScheduleAndPlanning(ShipmentScheduleId.ofRepoId(SCHEDULE_1), ProductPlanningId.ofRepoId(PLANNING_1))).isEmpty();
	}
}
