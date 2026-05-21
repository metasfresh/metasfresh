/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_Desadv_M_InOut;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DesadvDAO}.
 * <p>
 * Pinning the post-T4 (PLAN_ARCH) behaviour of {@link IDesadvDAO#retrieveShipmentsWithStatus}:
 * after the rewrite (commit {@code 01484e93da3}) the lookup enumerates shipments via the
 * {@code EDI_Desadv_M_InOut} junction instead of the single-FK {@code M_InOut.EDI_Desadv_ID}.
 * That means a "non-winner" DESADV — i.e. one that does NOT hold the single-FK on the shared
 * shipment — must still resolve to that shipment as long as a junction row links them.
 * Pre-T4 this returned an empty list for the non-winner; post-T4 it returns the shipment.
 */
@ExtendWith(AdempiereTestWatcher.class)
class DesadvDAOTest
{
	private IDesadvDAO desadvDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		desadvDAO = Services.get(IDesadvDAO.class);
	}

	/**
	 * Two DESADVs (A, B) share one {@code M_InOut}. The single-FK
	 * {@code M_InOut.EDI_Desadv_ID} points to A (the "winner"), but TWO junction rows exist —
	 * {@code (A, inOut)} and {@code (B, inOut)}.
	 * <p>
	 * Key assertion: {@code retrieveShipmentsWithStatus(B, {Pending})} returns the shared
	 * shipment. Pre-T4 this returned empty; post-T4 it returns the shipment via the junction.
	 */
	@Test
	void test_retrieveShipmentsWithStatus_returns_shared_inout_for_non_winner_desadv()
	{
		// ── DESADV A (winner of the single-FK) ──
		final I_EDI_Desadv desadvA = newInstance(I_EDI_Desadv.class);
		desadvA.setIsActive(true);
		saveRecord(desadvA);

		// ── DESADV B (non-winner; only reachable via the junction) ──
		final I_EDI_Desadv desadvB = newInstance(I_EDI_Desadv.class);
		desadvB.setIsActive(true);
		saveRecord(desadvB);

		assertThat(desadvA.getEDI_Desadv_ID()).isNotEqualTo(desadvB.getEDI_Desadv_ID()); // guard

		// ── Shared M_InOut: single-FK points to A (the winner), status = Pending ──
		final I_M_InOut sharedInOut = newInstance(I_M_InOut.class);
		sharedInOut.setIsActive(true);
		sharedInOut.setEDI_Desadv_ID(desadvA.getEDI_Desadv_ID());
		sharedInOut.setEDI_ExportStatus(EDIExportStatus.Pending.getCode());
		saveRecord(sharedInOut);

		// ── Two junction rows: (A, inOut) and (B, inOut) ──
		final I_EDI_Desadv_M_InOut junctionA = newInstance(I_EDI_Desadv_M_InOut.class);
		junctionA.setIsActive(true);
		junctionA.setEDI_Desadv_ID(desadvA.getEDI_Desadv_ID());
		junctionA.setM_InOut_ID(sharedInOut.getM_InOut_ID());
		saveRecord(junctionA);

		final I_EDI_Desadv_M_InOut junctionB = newInstance(I_EDI_Desadv_M_InOut.class);
		junctionB.setIsActive(true);
		junctionB.setEDI_Desadv_ID(desadvB.getEDI_Desadv_ID());
		junctionB.setM_InOut_ID(sharedInOut.getM_InOut_ID());
		saveRecord(junctionB);

		// ── Assertions ──

		// (1) Winner DESADV A resolves the shared shipment via its junction row.
		final List<I_M_InOut> shipmentsForA = desadvDAO.retrieveShipmentsWithStatus(
				desadvA, ImmutableSet.of(EDIExportStatus.Pending));
		assertThat(shipmentsForA)
				.as("DESADV A (winner) must resolve the shared shipment via the junction")
				.hasSize(1);
		assertThat(shipmentsForA.get(0).getM_InOut_ID())
				.as("DESADV A must return the shared M_InOut")
				.isEqualTo(sharedInOut.getM_InOut_ID());

		// (2) KEY ASSERTION — non-winner DESADV B also resolves the shared shipment via its
		//     junction row. Pre-T4 this returned empty because the lookup walked the single-FK
		//     M_InOut.EDI_Desadv_ID (which points to A, not B). Post-T4 it walks the junction.
		final List<I_M_InOut> shipmentsForB = desadvDAO.retrieveShipmentsWithStatus(
				desadvB, ImmutableSet.of(EDIExportStatus.Pending));
		assertThat(shipmentsForB)
				.as("DESADV B (non-winner) must resolve the shared shipment via the junction "
						+ "— pre-T4 returned empty, post-T4 returns the shipment")
				.hasSize(1);
		assertThat(shipmentsForB.get(0).getM_InOut_ID())
				.as("DESADV B must return the same shared M_InOut as DESADV A")
				.isEqualTo(sharedInOut.getM_InOut_ID());

		// (3) Status filter still works — asking for Sent shipments under DESADV B returns empty
		//     (the shared shipment is Pending, not Sent).
		final List<I_M_InOut> sentShipmentsForB = desadvDAO.retrieveShipmentsWithStatus(
				desadvB, ImmutableSet.of(EDIExportStatus.Sent));
		assertThat(sentShipmentsForB)
				.as("Status filter must still apply — Sent should return empty for DESADV B")
				.isEmpty();
	}
}
