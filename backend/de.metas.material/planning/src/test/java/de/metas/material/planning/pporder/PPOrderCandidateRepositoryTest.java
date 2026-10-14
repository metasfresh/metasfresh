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

import de.metas.business.BusinessTestHelper;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_Order_Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

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
	private UomId uomId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new PPOrderCandidateRepository();
		final I_C_UOM uom = BusinessTestHelper.createUomPCE();
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
	}

	private void createCandidate(
			final int shipmentScheduleId,
			final int productPlanningId,
			final boolean processed,
			final boolean closed,
			final String qtyEntered)
	{
		final I_PP_Order_Candidate record = newInstance(I_PP_Order_Candidate.class);
		record.setM_ShipmentSchedule_ID(shipmentScheduleId);
		record.setPP_Product_Planning_ID(productPlanningId);
		record.setProcessed(processed);
		record.setIsClosed(closed);
		record.setC_UOM_ID(uomId.getRepoId());
		record.setQtyEntered(new BigDecimal(qtyEntered));
		saveRecord(record);
	}

	@Test
	void sums_qtyEntered_of_immovable_candidates_of_the_schedule_and_planning()
	{
		createCandidate(SCHEDULE_1, PLANNING_1, true, false, "10");  // processed -> counted
		createCandidate(SCHEDULE_1, PLANNING_1, false, true, "5");   // closed -> counted
		createCandidate(SCHEDULE_1, PLANNING_1, false, false, "99"); // open -> excluded (advisor grows it)
		createCandidate(SCHEDULE_2, PLANNING_1, true, false, "7");   // other schedule -> excluded
		createCandidate(SCHEDULE_1, PLANNING_2, true, false, "8");   // other planning -> excluded

		final Optional<Quantity> result = repository.retrieveProcessedQtyByShipmentScheduleAndPlanning(
				ShipmentScheduleId.ofRepoId(SCHEDULE_1), ProductPlanningId.ofRepoId(PLANNING_1));

		assertThat(result).isPresent();
		assertThat(result.get().toBigDecimal()).isEqualByComparingTo("15");
		assertThat(result.get().getUomId()).isEqualTo(uomId);
	}

	@Test
	void empty_when_no_immovable_candidate()
	{
		createCandidate(SCHEDULE_1, PLANNING_1, false, false, "20"); // only open

		assertThat(repository.retrieveProcessedQtyByShipmentScheduleAndPlanning(
				ShipmentScheduleId.ofRepoId(SCHEDULE_1), ProductPlanningId.ofRepoId(PLANNING_1)))
				.isEmpty();
	}
}
