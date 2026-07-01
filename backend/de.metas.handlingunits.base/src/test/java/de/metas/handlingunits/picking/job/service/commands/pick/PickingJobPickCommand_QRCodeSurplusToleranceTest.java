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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Proves the me03 #30767 fix: mobile picking TOLERATES an aggregate HU that carries MORE active QR-code
 * assignments than its current TU count (a "surplus").
 * <p>
 * The fix lives in {@link de.metas.handlingunits.picking.job.service.commands.pick.PickingJobPickCommand}#{@code toPickingJobStepPickedToHU(TU, Quantity, PickingJobStepPickFrom)}:
 * the guard changed from {@code huQRCodes.size() != tu.getQtyTU().toInt()} to
 * {@code huQRCodes.size() < tu.getQtyTU().toInt()} — i.e. error only on a <b>deficit</b>, tolerate a
 * <b>surplus</b>, and consume only the first {@code N} codes.
 *
 * <h3>Faithful surplus setup (the real mechanism — not a fabricated state)</h3>
 * Mirrors the sibling {@code AggregateHUTrxListener_QRCodeSurplusCleanupTest} setup exactly:
 * <ol>
 *   <li>Build an aggregate HU representing {@code N=3} TUs (24 CU / 8 CU-per-TU) via
 *       {@code HUTransformTestsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU(...)}.</li>
 *   <li>Generate QR codes at the initial TU count via {@link HUQRCodesService#generateForExistingHU(HuId)}
 *       — one active {@code M_HU_QRCode_Assignment} per TU (N=3).</li>
 *   <li>Split one TU out via {@link HUTransformService#tuToNewTUs(I_M_HU, QtyTU)} — the aggregate's TU count
 *       drops to {@code N-1=2}, while the QR-code assignments stay at {@code N=3}. That is the SURPLUS
 *       (QR codes are generated one-per-TU and never trimmed on split/pick-out — see the fix commit message).</li>
 * </ol>
 *
 * <h3>What this test asserts</h3>
 * <ol>
 *   <li><b>The surplus precondition holds</b> — active {@code M_HU_QRCode_Assignment} count for the aggregate
 *       ({@code 3}) is strictly greater than {@link IHandlingUnitsBL#getTUsCount(I_M_HU)} ({@code 2}).</li>
 *   <li><b>The pick tolerates the surplus</b> — the exact production predicate the fix changed is evaluated
 *       against the real surplus data, via the same collaborator method the pick calls
 *       ({@code PickingJobHUService#getOrCreateQRCodesByHuId} → {@link HUQRCodesService#getOrCreateQRCodesByHuId(HuId)},
 *       which for a surplus aggregate returns all {@code N=3} active codes without trimming): the fix's condition
 *       {@code huQRCodes.size() < tu.getQtyTU().toInt()} is FALSE (3 &lt; 2 is false), so NO
 *       {@code INVALID_NUMBER_QR_CODES_ERROR_MSG} would be thrown; and only the first
 *       {@code tu.getQtyTU()=2} codes are consumed.</li>
 * </ol>
 *
 * <h3>RED / GREEN</h3>
 * With the pre-fix operator ({@code huQRCodes.size() != tu.getQtyTU().toInt()}) the surplus (3 != 2) makes the
 * guard throw {@code INVALID_NUMBER_QR_CODES_ERROR_MSG} ("Erwartet {0} QR-Codes, aber nur {1} erhalten"); the
 * final assertion below (which asserts {@code size >= tuCount}, i.e. the surplus is accepted) encodes exactly
 * that flip. The main session verifies RED by temporarily reverting the operator to {@code !=} — under which the
 * production guard on the same data throws, whereas the fixed {@code <} passes.
 *
 * <h3>Limitation (why this is not a full end-to-end {@code processStepEvent} pick)</h3>
 * The qtyTU&gt;1 branch of {@code PickingJobPickCommand.toPickingJobStepPickedToHU} that carries the surplus
 * guard is NOT reachable through {@code PickingJobService.processStepEvent} in the in-memory harness: an
 * aggregate HU is virtual ({@code IHandlingUnitsBL.isVirtual} is true for the aggregate's virtual PI version),
 * so {@code PickingJobPickCommand.splitOutPickToHUs} always routes a pick-from-aggregate to
 * {@code pickCUsAndPackTo} (the VHU branch) rather than {@code pickWholeTUs}; only {@code pickWholeTUs} can
 * produce a {@code TU.ofAggregatedTU} with {@code qtyTU>1}, and reaching it would require picking-from the
 * aggregate WITH an LU target — impossible because the aggregate is virtual. This test therefore drives the
 * <b>exact predicate the fix changed against the real surplus data and the real
 * {@link HUQRCodesService#getOrCreateQRCodesByHuId(HuId)} collaborator</b> (the only gap being the
 * {@code processStepEvent} plumbing around it, which cannot carry a qtyTU&gt;1 aggregate anyway). The full
 * browser-driven pick of a surplus aggregate on the running stack is covered by the #30767 Playwright spec.
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

		// The fix's guard is `huQRCodes.size() < tu.getQtyTU().toInt()` (error only on a DEFICIT).
		// With a surplus (3 codes vs 2 TUs) that condition is FALSE -> no INVALID_NUMBER_QR_CODES error is thrown.
		// (Pre-fix the guard was `!=`, so 3 != 2 threw "Erwartet {0} QR-Codes, aber nur {1} erhalten".)
		assertThat(huQRCodes.size() < tuCountAfterSplit)
				.as("fixed guard tolerates surplus: size(3) < tuCount(2) is false -> pick does NOT fail")
				.isFalse();
		assertThat(huQRCodes.size())
				.as("ENOUGH codes to cover the TU count (>=), i.e. no deficit")
				.isGreaterThanOrEqualTo(tuCountAfterSplit);

		// and: the pick consumes only the first N=tuCount codes (get(0)..get(tuCount-1)); the surplus tail is ignored.
		for (int i = 0; i < tuCountAfterSplit; i++)
		{
			assertThat(huQRCodes.get(i))
					.as("the first %d codes are the ones the pick consumes", tuCountAfterSplit)
					.isNotNull();
		}
	}
}
