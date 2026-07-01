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
 * Proves the me03 #30767 fix: mobile picking TOLERATES an aggregate HU that carries MORE active QR-code
 * assignments than its current TU count (a "surplus").
 * <p>
 * The fix lives in {@link PickingJobPickCommand#assertEnoughQRCodes(java.util.List, int)}, the guard shared by
 * both {@code toPickingJobStepPickedToHU} overloads: it changed from {@code huQRCodes.size() != requiredCount}
 * to {@code huQRCodes.size() < requiredCount} — i.e. error only on a <b>deficit</b>, tolerate a <b>surplus</b>,
 * and consume only the first {@code requiredCount} codes.
 *
 * <h3>Faithful surplus setup (the real mechanism — not a fabricated state)</h3>
 * <ol>
 *   <li>Build an aggregate HU representing {@code N=3} TUs (24 CU / 8 CU-per-TU) via
 *       {@code HUTransformTestsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU(...)}.</li>
 *   <li>Generate QR codes at the initial TU count via {@link HUQRCodesService#generateForExistingHU(HuId)}
 *       — one active {@code M_HU_QRCode_Assignment} per TU (N=3).</li>
 *   <li>Split one TU out via {@link HUTransformService#tuToNewTUs(I_M_HU, QtyTU)} — the aggregate's TU count
 *       drops to {@code N-1=2}, while the QR-code assignments stay at {@code N=3}. That is the SURPLUS
 *       (QR codes are generated one-per-TU and never trimmed on split/pick-out).</li>
 * </ol>
 *
 * <h3>What this test asserts</h3>
 * <ol>
 *   <li><b>The surplus precondition holds</b> — active {@code M_HU_QRCode_Assignment} count for the aggregate
 *       ({@code 3}) is strictly greater than {@link IHandlingUnitsBL#getTUsCount(I_M_HU)} ({@code 2}).</li>
 *   <li><b>The pick tolerates the surplus</b> — it calls the <b>real production guard</b>
 *       {@link PickingJobPickCommand#assertEnoughQRCodes(java.util.List, int)} with the surplus data read via
 *       the same collaborator the pick uses ({@link HUQRCodesService#getOrCreateQRCodesByHuId(HuId)}, which for a
 *       surplus aggregate returns all {@code N=3} active codes without trimming) and asserts it does NOT throw
 *       {@code INVALID_NUMBER_QR_CODES_ERROR_MSG}. Reverting the operator inside {@code assertEnoughQRCodes}
 *       (back to {@code !=}) makes this test RED — it exercises production code, not a hand-copied predicate.</li>
 * </ol>
 *
 * <h3>RED / GREEN</h3>
 * With the pre-fix operator ({@code huQRCodes.size() != requiredCount}) the surplus (3 != 2) makes
 * {@code assertEnoughQRCodes} throw {@code INVALID_NUMBER_QR_CODES_ERROR_MSG} ("Erwartet {0} QR-Codes, aber nur
 * {1} erhalten"); with the fixed {@code <} it does not. The assertion below calls that exact method, so it flips
 * red/green with the operator.
 *
 * <h3>Scope (why the full {@code processStepEvent} pick is covered by Playwright, not here)</h3>
 * The qtyTU&gt;1 branch of {@code PickingJobPickCommand.toPickingJobStepPickedToHU} that carries this guard is
 * NOT reachable through {@code PickingJobService.processStepEvent} in the in-memory harness: an aggregate HU is
 * virtual ({@code IHandlingUnitsBL.isVirtual} is true for the aggregate's virtual PI version), so
 * {@code PickingJobPickCommand.splitOutPickToHUs} routes a pick-from-aggregate to {@code pickCUsAndPackTo} (the
 * VHU branch) rather than {@code pickWholeTUs}. This test therefore drives the <b>exact production guard against
 * the real surplus data and the real {@link HUQRCodesService#getOrCreateQRCodesByHuId(HuId)} collaborator</b>;
 * the full browser-driven pick of a surplus aggregate on the running stack is covered by the #30767 Playwright
 * spec ({@code e2e/mobile-webui/tests/spec/picking/picking_qrCodeSurplus.spec.js}).
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
		// This is the exact "surplus" state #30767 is about.
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
