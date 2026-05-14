/*
 * #%L
 * de.metas.business
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

package de.metas.order.model.interceptor;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.PurchaseOrderProjectListenerDispatcher;
import de.metas.order.model.I_C_Order;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.service.ProjectService;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link C_Order_Project} — dropship-warehouse gate.
 * <p>
 * Style: {@code AdempiereTestHelper.get().init()} (POJO-backed IQueryBL) +
 * Mockito for BL services registered via {@link Services#registerService(Class, Object)}.
 * <p>
 * Services must be registered <em>before</em> instantiating the interceptor, because
 * its field initializers call {@link Services#get(Class)} eagerly.
 */
class C_Order_ProjectTest
{
	// Services injected via Services.get(...)
	private IOrderBL orderBL;
	private IWarehouseDAO warehouseDAO;

	// Constructor-injected dependencies
	private ProjectService projectService;
	private ProjectTypeRepository projectTypeRepository;
	private PurchaseOrderProjectListenerDispatcher eventDispatcher;

	// The instance under test
	private C_Order_Project interceptor;

	// Shared constants
	private static final int USER_ID = 1;
	private static final int WAREHOUSE_ID = 10;
	private static final int ORG_ID = 100;
	private static final int BPARTNER_ID = 200;
	private static final int BPARTNER_LOCATION_ID = 201;
	private static final int CURRENCY_ID = 300;
	private static final int PROJECT_TYPE_ID = 400;
	private static final int PROJECT_ID = 500;
	private static final int ORDER_LINE_PROJECT_ID = 600;

	@BeforeEach
	void beforeEach()
	{
		// Init the in-memory ADempiere environment (provides real POJO-backed IQueryBL, etc.)
		AdempiereTestHelper.get().init();

		// Set a logged-in user so Env.getLoggedUserId() does not throw
		// (needed for setProjectIdToOrderLines → fireProjectCreatedEvent → byUserId)
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, USER_ID);

		// Create mocks
		orderBL = mock(IOrderBL.class);
		warehouseDAO = mock(IWarehouseDAO.class);
		projectService = mock(ProjectService.class);
		projectTypeRepository = mock(ProjectTypeRepository.class);
		eventDispatcher = mock(PurchaseOrderProjectListenerDispatcher.class);

		// Register Services.get(...) mocks BEFORE instantiating the interceptor —
		// its field initializers call Services.get() eagerly.
		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IWarehouseDAO.class, warehouseDAO);
		// NOTE: Do NOT mock ITrxManager — AdempiereTestHelper.get().init() registers the real
		// in-memory ITrxManager. Mocking it would prevent POJOLookupMap.save() from executing
		// (it wraps the save in runInTrx), which would leave C_Order_ID == 0.

		// Construct the interceptor with its constructor-injected dependencies
		interceptor = new C_Order_Project(projectService, projectTypeRepository, eventDispatcher);
	}

	// -----------------------------------------------------------------------
	// Test 1 — non-dropship SO early-returns on isSOTrx()
	// -----------------------------------------------------------------------

	/**
	 * A sales order whose warehouse does NOT have {@code IsDropShipWarehouse='Y'} must
	 * cause {@code populateProjectIfNeeded} to return immediately — no project is created
	 * or promoted.
	 */
	@Test
	void beforeComplete_nonDropshipSO_earlyReturn()
	{
		// Given: SO on a non-dropship warehouse
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsDropShipWarehouse(false);

		when(warehouseDAO.getById(WarehouseId.ofRepoId(WAREHOUSE_ID))).thenReturn(warehouse);

		final I_C_Order order = buildOrder(true /*isSOTrx*/, WAREHOUSE_ID);
		order.setC_Project_ID(0); // no project yet
		saveRecord(order);

		// When
		interceptor.beforeComplete(order);

		// Then: no project was set on the order
		assertThat(order.getC_Project_ID()).isZero();
		// and orderBL was never consulted (early return before the line lookup)
		verify(orderBL, never()).getLinesByOrderIds(any());
		verify(projectService, never()).createProject(any());
	}

	// -----------------------------------------------------------------------
	// Test 2 — PO unchanged (regression protection)
	// -----------------------------------------------------------------------

	/**
	 * A purchase order (isSOTrx=false) with a single project on all lines must have
	 * that project promoted to the order header — the non-dropship existing PO flow
	 * must continue to work after the dropship gate was added.
	 */
	@Test
	void beforeComplete_purchaseOrder_promotesProjectFromLines()
	{
		// Given: PO without a project, but all lines share the same project
		final I_C_Order order = buildOrder(false /*isSOTrx*/, WAREHOUSE_ID);
		order.setC_Project_ID(0);
		saveRecord(order);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Project_ID(ORDER_LINE_PROJECT_ID);
		saveRecord(line);

		when(orderBL.getLinesByOrderIds(any())).thenReturn(Collections.singletonList(line));

		// When
		interceptor.beforeComplete(order);

		// Then: the single project from the line was promoted to the order
		assertThat(order.getC_Project_ID()).isEqualTo(ORDER_LINE_PROJECT_ID);
		verify(projectService, never()).createProject(any());
	}

	// -----------------------------------------------------------------------
	// Test 3 — dropship SO creates/promotes project from lines
	// -----------------------------------------------------------------------

	/**
	 * A sales order on a dropship warehouse with at least one order line that already has
	 * a {@code C_Project_ID} must have that project promoted to the order header.
	 */
	@Test
	void beforeComplete_dropshipSO_promotesProjectFromLines()
	{
		// Given: SO on a dropship warehouse with a project on the order line
		final I_M_Warehouse dropshipWarehouse = newInstance(I_M_Warehouse.class);
		dropshipWarehouse.setIsDropShipWarehouse(true);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(WAREHOUSE_ID))).thenReturn(dropshipWarehouse);

		final I_C_Order order = buildOrder(true /*isSOTrx*/, WAREHOUSE_ID);
		order.setC_Project_ID(0);
		saveRecord(order);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Project_ID(ORDER_LINE_PROJECT_ID);
		saveRecord(line);

		when(orderBL.getLinesByOrderIds(any())).thenReturn(Collections.singletonList(line));

		// When
		interceptor.beforeComplete(order);

		// Then: the single project from the line was promoted to the SO header
		assertThat(order.getC_Project_ID()).isEqualTo(ORDER_LINE_PROJECT_ID);
		verify(projectService, never()).createProject(any());
	}

	// -----------------------------------------------------------------------
	// Test 4 — dropship SO with no project on lines creates new project
	// -----------------------------------------------------------------------

	/**
	 * A sales order on a dropship warehouse where NO order lines have a project must
	 * trigger project creation, and the newly created project must be set on the order header.
	 */
	@Test
	void beforeComplete_dropshipSONoProjectOnLines_createsNewProject()
	{
		// Given: SO on a dropship warehouse, no project on lines
		final I_M_Warehouse dropshipWarehouse = newInstance(I_M_Warehouse.class);
		dropshipWarehouse.setIsDropShipWarehouse(true);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(WAREHOUSE_ID))).thenReturn(dropshipWarehouse);

		final I_C_Order order = buildOrder(true /*isSOTrx*/, WAREHOUSE_ID);
		order.setC_Project_ID(0);
		saveRecord(order);

		// Order line without a project — must reference the order so setProjectIdToOrderLines
		// can build OrderAndLineId (it calls ol.getC_Order_ID())
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setC_Project_ID(0); // no project
		saveRecord(line);

		when(orderBL.getLinesByOrderIds(any())).thenReturn(Collections.singletonList(line));

		// A ProjectType for SalesPurchaseOrder exists in the org
		when(projectTypeRepository.getFirstIdByProjectCategoryAndOrgOrNull(
				eq(de.metas.project.ProjectCategory.SalesPurchaseOrder),
				eq(de.metas.organization.OrgId.ofRepoId(ORG_ID)),
				eq(false)))
				.thenReturn(ProjectTypeId.ofRepoId(PROJECT_TYPE_ID));

		// projectService.createProject(...) returns a new ProjectId
		when(projectService.createProject(any())).thenReturn(ProjectId.ofRepoId(PROJECT_ID));

		// When
		interceptor.beforeComplete(order);

		// Then: the newly created project was set on the order header
		assertThat(order.getC_Project_ID()).isEqualTo(PROJECT_ID);
		verify(projectService).createProject(any());
	}

	// -----------------------------------------------------------------------
	// Test 5 — order already has a project — propagates to lines that are missing it
	// -----------------------------------------------------------------------

	/**
	 * When an order (SO or PO) already has a {@code C_Project_ID > 0}, the interceptor
	 * must propagate the header's project to any order lines that do not yet have one,
	 * then return — without creating a new project or consulting the warehouse.
	 * <p>
	 * Lines that already carry the same project ID must be left untouched.
	 */
	@Test
	void beforeComplete_orderAlreadyHasProject_propagatesToLines()
	{
		// Given: SO that already has a project assigned
		final I_C_Order order = buildOrder(true /*isSOTrx*/, WAREHOUSE_ID);
		order.setC_Project_ID(PROJECT_ID);
		saveRecord(order);

		// Two lines with no project yet
		final I_C_OrderLine lineNoProject1 = newInstance(I_C_OrderLine.class);
		lineNoProject1.setC_Order_ID(order.getC_Order_ID());
		lineNoProject1.setC_Project_ID(0);
		saveRecord(lineNoProject1);

		final I_C_OrderLine lineNoProject2 = newInstance(I_C_OrderLine.class);
		lineNoProject2.setC_Order_ID(order.getC_Order_ID());
		lineNoProject2.setC_Project_ID(0);
		saveRecord(lineNoProject2);

		// One line that already has the header's project
		final I_C_OrderLine lineAlreadyHasProject = newInstance(I_C_OrderLine.class);
		lineAlreadyHasProject.setC_Order_ID(order.getC_Order_ID());
		lineAlreadyHasProject.setC_Project_ID(PROJECT_ID);
		saveRecord(lineAlreadyHasProject);

		final List<I_C_OrderLine> allLines = Arrays.asList(lineNoProject1, lineNoProject2, lineAlreadyHasProject);
		when(orderBL.getLinesByOrderIds(any())).thenReturn(allLines);

		// When
		interceptor.beforeComplete(order);

		// Then: the two NULL lines now carry the header's project
		assertThat(lineNoProject1.getC_Project_ID()).isEqualTo(PROJECT_ID);
		assertThat(lineNoProject2.getC_Project_ID()).isEqualTo(PROJECT_ID);
		// The line that already had the project is unchanged
		assertThat(lineAlreadyHasProject.getC_Project_ID()).isEqualTo(PROJECT_ID);
		// Header is unchanged
		assertThat(order.getC_Project_ID()).isEqualTo(PROJECT_ID);
		// No new project was created — we're in the early-return branch
		verify(projectService, never()).createProject(any());
		// Warehouse was NOT consulted — we exited before the SO/dropship check
		verify(warehouseDAO, never()).getById(any());
	}

	// -----------------------------------------------------------------------
	// Test 6 — dropship PO: header has project — propagates to lines
	// -----------------------------------------------------------------------

	/**
	 * Regression test for the dropship-PO path: the aggregator
	 * {@code CreatePOFromSOsAggregator.createPurchaseOrder()} copies {@code C_Project_ID}
	 * to the PO header but NOT to the PO lines. This test verifies that the interceptor
	 * fills the gap by propagating the header project to any line that lacks one.
	 */
	@Test
	void beforeComplete_dropshipPOHeaderHasProject_propagatesToLines()
	{
		// Given: PO (isSOTrx=false) whose header already has a project, but lines do not
		final I_C_Order order = buildOrder(false /*isSOTrx*/, WAREHOUSE_ID);
		order.setC_Project_ID(PROJECT_ID);
		saveRecord(order);

		// Two lines with no project yet
		final I_C_OrderLine lineNoProject1 = newInstance(I_C_OrderLine.class);
		lineNoProject1.setC_Order_ID(order.getC_Order_ID());
		lineNoProject1.setC_Project_ID(0);
		saveRecord(lineNoProject1);

		final I_C_OrderLine lineNoProject2 = newInstance(I_C_OrderLine.class);
		lineNoProject2.setC_Order_ID(order.getC_Order_ID());
		lineNoProject2.setC_Project_ID(0);
		saveRecord(lineNoProject2);

		// One line that already has the header's project (should be left untouched)
		final I_C_OrderLine lineAlreadyHasProject = newInstance(I_C_OrderLine.class);
		lineAlreadyHasProject.setC_Order_ID(order.getC_Order_ID());
		lineAlreadyHasProject.setC_Project_ID(PROJECT_ID);
		saveRecord(lineAlreadyHasProject);

		final List<I_C_OrderLine> allLines = Arrays.asList(lineNoProject1, lineNoProject2, lineAlreadyHasProject);
		when(orderBL.getLinesByOrderIds(any())).thenReturn(allLines);

		// When
		interceptor.beforeComplete(order);

		// Then: the two NULL lines now carry the header's project
		assertThat(lineNoProject1.getC_Project_ID()).isEqualTo(PROJECT_ID);
		assertThat(lineNoProject2.getC_Project_ID()).isEqualTo(PROJECT_ID);
		// The line that already had the project is unchanged
		assertThat(lineAlreadyHasProject.getC_Project_ID()).isEqualTo(PROJECT_ID);
		// Header is unchanged
		assertThat(order.getC_Project_ID()).isEqualTo(PROJECT_ID);
		// No new project was created — header already had one
		verify(projectService, never()).createProject(any());
		// Warehouse was NOT consulted — we exited before the SO/dropship check
		verify(warehouseDAO, never()).getById(any());
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	/**
	 * Build and return a minimal {@link I_C_Order} POJO (not yet saved).
	 * Caller must call {@link org.adempiere.model.InterfaceWrapperHelper#saveRecord(Object)}
	 * after setting any additional fields.
	 */
	private I_C_Order buildOrder(final boolean isSOTrx, final int warehouseId)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(isSOTrx);
		order.setM_Warehouse_ID(warehouseId);
		order.setAD_Org_ID(ORG_ID);
		order.setC_BPartner_ID(BPARTNER_ID);
		order.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
		order.setC_Currency_ID(CURRENCY_ID);
		return order;
	}
}
