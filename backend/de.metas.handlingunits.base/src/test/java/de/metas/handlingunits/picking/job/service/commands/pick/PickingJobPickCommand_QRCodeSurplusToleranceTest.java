/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformTestsBase;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_QRCode_Assignment;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Proves the mobile-picking QR-code surplus tolerance in the shared guard
 * {@link PickingJobPickCommand#assertEnoughQRCodes(java.util.List, int)} (changed from {@code size() != required}
 * to {@code size() < required}): a pick must tolerate an aggregate that carries MORE active QR-code assignments
 * than its current TU count and consume only the first N. The test builds that surplus faithfully (generate codes
 * at the TU count, split a TU out so codes outnumber TUs) and calls the real guard — see the inline steps below.
 * <p>
 * It drives the guard directly rather than through {@code PickingJobService.processStepEvent} because the
 * qtyTU&gt;1 branch that carries the guard is not reachable in the in-memory harness: an aggregate HU is virtual,
 * so {@code splitOutPickToHUs} routes a pick-from-aggregate to the VHU branch, not {@code pickWholeTUs}. The full
 * browser-driven pick of a surplus aggregate is covered by the Playwright spec
 * {@code e2e/mobile-webui/tests/spec/picking/picking_qrCodeSurplus.spec.js}.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class PickingJobPickCommand_QRCodeSurplusToleranceTest
{
	private IHandlingUnitsBL handlingUnitsBL;
	private HUTransformTestsBase testsBase;
	private HUTransformService huTransformService;
	private HUQRCodesService huQRCodesService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		testsBase = new HUTransformTestsBase();
		huTransformService = HUTransformService.newInstance(testsBase.getData().helper.getHUContext());

		huQRCodesService = HUQRCodesService.newInstanceForUnitTesting();
		SpringContextHolder.registerJUnitBean(huQRCodesService);
	}

	private long countActiveAssignments(final HuId huId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_QRCode_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_QRCode_Assignment.COLUMNNAME_M_HU_ID, huId)
				.create()
				.count();
	}

	private int tuCountOf(final I_M_HU aggregateTU)
	{
		return handlingUnitsBL.getTUsCount(aggregateTU).toInt();
	}

	@Test
	public void pickTolerates_surplusQRCodeAssignments_onAggregate()
	{
		// given: an aggregate HU representing 3 TUs (24 CU / 8 CU-per-TU)
		final I_M_HU aggregateTU = testsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("24", 8);
		assertThat(handlingUnitsBL.isAggregateHU(aggregateTU)).isTrue(); // guard
		assertThat(tuCountOf(aggregateTU)).isEqualTo(3); // guard

		final HuId aggregateHuId = HuId.ofRepoId(aggregateTU.getM_HU_ID());

		// and: QR codes generated/assigned at the initial TU count (one active assignment per TU)
		huQRCodesService.generateForExistingHU(aggregateHuId);
		assertThat(countActiveAssignments(aggregateHuId))
				.as("one active assignment per TU before the split")
				.isEqualTo(3);

		// when: one TU is split out of the aggregate -> its TU count drops to 2, but QR-code assignments stay at 3
		huTransformService.tuToNewTUs(aggregateTU, QtyTU.ONE);
		InterfaceWrapperHelper.refresh(aggregateTU);

		// then (surplus precondition): active QR-code assignments (3) now EXCEED the current TU count (2).
		// This is the exact "surplus" state under test.
		final int tuCountAfterSplit = tuCountOf(aggregateTU);
		final long activeAssignmentsAfterSplit = countActiveAssignments(aggregateHuId);
		assertThat(tuCountAfterSplit)
				.as("aggregate now represents one less TU")
				.isEqualTo(2);
		assertThat(activeAssignmentsAfterSplit)
				.as("QR-code assignments are NOT trimmed on split -> surplus (count > TU count)")
				.isEqualTo(3)
				.isGreaterThan(tuCountAfterSplit);

		// then (the fix): the pick reads the aggregate's QR codes via exactly the collaborator method
		// PickingJobPickCommand.toPickingJobStepPickedToHU calls (PickingJobHUService#getOrCreateQRCodesByHuId
		// -> HUQRCodesService#getOrCreateQRCodesByHuId). For a surplus aggregate this returns ALL active codes (3)
		// without trimming down to the TU count.
		final List<HUQRCode> huQRCodes = huQRCodesService.getOrCreateQRCodesByHuId(aggregateHuId);
		assertThat(huQRCodes)
				.as("getOrCreateQRCodesByHuId returns the surplus codes untrimmed (one per originally-generated TU)")
				.hasSize(3);

		// then (the fix): call the REAL production guard PickingJobPickCommand.assertEnoughQRCodes with the surplus
		// data and the required count the pick uses (tu.getQtyTU()=2). With the fixed operator (`< requiredCount`,
		// error only on a DEFICIT) it does NOT throw for a surplus (3 codes, 2 required). Reverting the operator
		// inside assertEnoughQRCodes (back to `!=`) makes this assertion throw INVALID_NUMBER_QR_CODES_ERROR_MSG,
		// i.e. this test exercises production code, not a hand-copied predicate.
		assertThatCode(() -> PickingJobPickCommand.assertEnoughQRCodes(huQRCodes, tuCountAfterSplit))
				.as("production guard tolerates a surplus (3 codes >= 2 required) -> no INVALID_NUMBER_QR_CODES error")
				.doesNotThrowAnyException();

		// and (regression anchor): the same production guard DOES throw on a genuine DEFICIT (fewer codes than
		// required), so the fix only relaxed the surplus case, not the deficit case.
		assertThatThrownBy(() -> PickingJobPickCommand.assertEnoughQRCodes(huQRCodes, huQRCodes.size() + 1))
				.as("production guard still errors on a deficit (required > codes)")
				.isInstanceOf(AdempiereException.class);
	}
}
