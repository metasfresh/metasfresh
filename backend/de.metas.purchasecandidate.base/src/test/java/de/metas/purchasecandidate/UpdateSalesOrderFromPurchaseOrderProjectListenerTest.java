/*
 * #%L
 * de.metas.purchasecandidate.base
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

package de.metas.purchasecandidate;

import de.metas.order.OrderAndLineId;
import de.metas.order.PurchaseOrderProjectListener.ProjectCreatedEvent;
import de.metas.project.ProjectId;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PO_OrderLine_Alloc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link UpdateSalesOrderFromPurchaseOrderProjectListener}.
 * <p>
 * The listener has two resolution paths from PO line → SO line:
 * <ol>
 *   <li><b>Primary (candidate)</b>: via {@link PurchaseCandidateRepository#getAllByPurchaseOrderLineIds(java.util.Collection)}
 *       → {@link PurchaseCandidate#getSalesOrderAndLineIdOrNull()}.</li>
 *   <li><b>Fallback (alloc)</b>: for PO lines NOT matched by a candidate, look up
 *       {@link I_C_PO_OrderLine_Alloc} and read its {@code C_SO_OrderLine_ID}.
 *       This is the path used by the dropship-PO flow, which skips the candidate table.</li>
 * </ol>
 * After union, SO lines without an existing {@code C_Project_ID} get the project applied.
 * <p>
 * Pattern: {@code AdempiereTestHelper.get().init()} + POJO infrastructure for DB-backed
 * lookups (alloc tables, order lines) + Mockito for {@link PurchaseCandidateRepository}.
 */
class UpdateSalesOrderFromPurchaseOrderProjectListenerTest
{
	private static final int USER_ID = 100;

	private PurchaseCandidateRepository purchaseCandidateRepo;
	private UpdateSalesOrderFromPurchaseOrderProjectListener listener;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		purchaseCandidateRepo = mock(PurchaseCandidateRepository.class);
		listener = new UpdateSalesOrderFromPurchaseOrderProjectListener(purchaseCandidateRepo);
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private I_C_Order createOrder()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);
		return order;
	}

	/**
	 * Creates an {@link I_C_OrderLine} attached to the given order, with an optional pre-existing project.
	 */
	private I_C_OrderLine createOrderLine(final I_C_Order order, final int projectId)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setC_Project_ID(projectId);
		saveRecord(line);
		return line;
	}

	/**
	 * Inserts a {@link I_C_PurchaseCandidate_Alloc} row so the listener's "matched"-by-candidate
	 * probe returns this PO line (i.e., the fallback path is skipped for it).
	 * <p>
	 * NB: the listener does not actually read the SO link from this row — only checks presence.
	 * The SO link comes from the mocked {@link PurchaseCandidateRepository}.
	 */
	private void insertCandidateAlloc(final int poLineId)
	{
		final I_C_PurchaseCandidate_Alloc alloc = newInstance(I_C_PurchaseCandidate_Alloc.class);
		alloc.setC_OrderLinePO_ID(poLineId);
		saveRecord(alloc);
	}

	private void insertPoOrderLineAlloc(final int poLineId, final int soLineId)
	{
		final I_C_PO_OrderLine_Alloc alloc = newInstance(I_C_PO_OrderLine_Alloc.class);
		alloc.setC_PO_OrderLine_ID(poLineId);
		alloc.setC_SO_OrderLine_ID(soLineId);
		alloc.setIsActive(true);
		saveRecord(alloc);
	}

	private ProjectCreatedEvent buildEvent(final ProjectId projectId, final OrderAndLineId... poLineIds)
	{
		return ProjectCreatedEvent.builder()
				.projectId(projectId)
				.purchaseOrderLineIds(com.google.common.collect.ImmutableSet.copyOf(poLineIds))
				.byUserId(UserId.ofRepoId(USER_ID))
				.build();
	}

	/**
	 * Mocks {@code purchaseCandidateRepo.getAllByPurchaseOrderLineIds(...)} to return a single
	 * candidate that resolves to the given SO line. The mock returns the same value regardless
	 * of input set — sufficient because the listener calls the method exactly once.
	 */
	private void givenCandidateMatch(final OrderAndLineId salesOrderAndLineId)
	{
		final PurchaseCandidate candidate = mock(PurchaseCandidate.class);
		when(candidate.getSalesOrderAndLineIdOrNull()).thenReturn(salesOrderAndLineId);
		when(purchaseCandidateRepo.getAllByPurchaseOrderLineIds(org.mockito.ArgumentMatchers.any()))
				.thenReturn(Collections.singletonList(candidate));
	}

	private void givenNoCandidateMatches()
	{
		when(purchaseCandidateRepo.getAllByPurchaseOrderLineIds(org.mockito.ArgumentMatchers.any()))
				.thenReturn(Collections.emptyList());
	}

	// -----------------------------------------------------------------------
	// Test (a) — candidate-only path: PO line linked via C_PurchaseCandidate (legacy flow).
	// Fallback alloc lookup is skipped for matched PO lines; SO line receives the project.
	// -----------------------------------------------------------------------

	@Test
	void candidateOnlyPath_setsProjectOnSalesOrderLine()
	{
		// Given:
		//   - one SO with one SO line (no project yet)
		//   - one PO line known to be linked to that SO line via C_PurchaseCandidate
		//   - presence of a C_PurchaseCandidate_Alloc row for the PO line so the listener
		//     considers it "matched" and SKIPS the C_PO_OrderLine_Alloc fallback
		final I_C_Order salesOrder = createOrder();
		final I_C_OrderLine soLine = createOrderLine(salesOrder, 0);
		final int poLineId = 5001;
		insertCandidateAlloc(poLineId);

		final OrderAndLineId poOrderAndLineId = OrderAndLineId.ofRepoIdsOrNull(9001, poLineId);
		final OrderAndLineId soOrderAndLineId = OrderAndLineId.ofRepoIdsOrNull(salesOrder.getC_Order_ID(), soLine.getC_OrderLine_ID());

		givenCandidateMatch(soOrderAndLineId);

		final ProjectId projectId = ProjectId.ofRepoId(7777);

		// When
		listener.onCreated(buildEvent(projectId, poOrderAndLineId));

		// Then: SO line carries the new project
		refresh(soLine);
		assertThat(soLine.getC_Project_ID()).isEqualTo(7777);
	}

	// -----------------------------------------------------------------------
	// Test (b) — alloc-only path (dropship): no C_PurchaseCandidate row, BUT
	// C_PO_OrderLine_Alloc resolves PO line → SO line. SO line receives the project.
	// -----------------------------------------------------------------------

	@Test
	void allocOnlyPath_dropshipFlow_setsProjectOnSalesOrderLine()
	{
		// Given: SO line, no candidate row for the PO line, but a C_PO_OrderLine_Alloc
		final I_C_Order salesOrder = createOrder();
		final I_C_OrderLine soLine = createOrderLine(salesOrder, 0);
		final int poLineId = 5101;
		insertPoOrderLineAlloc(poLineId, soLine.getC_OrderLine_ID());

		final OrderAndLineId poOrderAndLineId = OrderAndLineId.ofRepoIdsOrNull(9101, poLineId);

		givenNoCandidateMatches();

		final ProjectId projectId = ProjectId.ofRepoId(8888);

		// When
		listener.onCreated(buildEvent(projectId, poOrderAndLineId));

		// Then: SO line carries the new project
		refresh(soLine);
		assertThat(soLine.getC_Project_ID()).isEqualTo(8888);
	}

	// -----------------------------------------------------------------------
	// Test (c) — SO line already has a project. Listener must NOT overwrite.
	// (filter at line 148 of the listener: ProjectId.ofRepoIdOrNull(...) == null).
	// -----------------------------------------------------------------------

	@Test
	void salesOrderLineAlreadyHasProject_listenerDoesNotOverwrite()
	{
		final int preExistingProjectId = 1234;

		final I_C_Order salesOrder = createOrder();
		final I_C_OrderLine soLine = createOrderLine(salesOrder, preExistingProjectId);

		final int poLineId = 5201;
		insertPoOrderLineAlloc(poLineId, soLine.getC_OrderLine_ID());

		final OrderAndLineId poOrderAndLineId = OrderAndLineId.ofRepoIdsOrNull(9201, poLineId);
		givenNoCandidateMatches();

		// When
		listener.onCreated(buildEvent(ProjectId.ofRepoId(9999), poOrderAndLineId));

		// Then: SO line still carries the original project — not overwritten
		refresh(soLine);
		assertThat(soLine.getC_Project_ID()).isEqualTo(preExistingProjectId);
	}

	// -----------------------------------------------------------------------
	// Test (d) — BOTH paths resolve to the same SO line. The union de-dups
	// (ImmutableSet.builder()) so the SO line is updated exactly once and saved cleanly.
	// -----------------------------------------------------------------------

	@Test
	void candidateAndAllocBothResolveToSameSalesOrderLine_noDoubleUpdate()
	{
		// Given: a PO line that has BOTH a C_PurchaseCandidate_Alloc AND a C_PO_OrderLine_Alloc.
		// In real life: both would point to the same SO line. We simulate by having the
		// candidate-path repo return the SO line and ALSO setting up a C_PO_OrderLine_Alloc
		// to the SAME SO line. But — because the PO line IS matched by the candidate-alloc
		// table, the listener treats it as "matched" and SKIPS the fallback lookup for it.
		// To exercise the union-with-dedup logic, we use TWO PO lines:
		//   - poLineId_A: matched-by-candidate, resolved via repo to SO line X
		//   - poLineId_B: unmatched-by-candidate, resolved via alloc fallback ALSO to SO line X
		final I_C_Order salesOrder = createOrder();
		final I_C_OrderLine soLine = createOrderLine(salesOrder, 0);

		final int poLineIdA = 5301;
		final int poLineIdB = 5302;
		insertCandidateAlloc(poLineIdA); // poLineIdA is "matched" by the candidate alloc probe
		insertPoOrderLineAlloc(poLineIdB, soLine.getC_OrderLine_ID()); // poLineIdB → SO line via fallback

		final OrderAndLineId soOrderAndLineId = OrderAndLineId.ofRepoIdsOrNull(salesOrder.getC_Order_ID(), soLine.getC_OrderLine_ID());
		givenCandidateMatch(soOrderAndLineId);

		final OrderAndLineId poOrderAndLineIdA = OrderAndLineId.ofRepoIdsOrNull(9301, poLineIdA);
		final OrderAndLineId poOrderAndLineIdB = OrderAndLineId.ofRepoIdsOrNull(9301, poLineIdB);

		// When: listener invoked with both PO lines, both reaching the same SO line
		listener.onCreated(buildEvent(ProjectId.ofRepoId(5555), poOrderAndLineIdA, poOrderAndLineIdB));

		// Then: SO line has the project (single update, no exception from duplicate save)
		refresh(soLine);
		assertThat(soLine.getC_Project_ID()).isEqualTo(5555);
	}
}
