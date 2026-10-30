/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.material.cockpit.v2.jump;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.ShipmentScheduleQuery;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.material.cockpit.QtyDemandQtySupply;
import de.metas.material.cockpit.QtyDemandQtySupplyId;
import de.metas.material.cockpit.QtyDemandSupplyRepository;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionChecker;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRow;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Table;
import org.compiere.util.Env;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The Material Cockpit v2 jumps must be built from the CLICKED ROW (product / warehouse / org / attributesKey),
 * never from {@link QtyDemandSupplyRepository#getById(QtyDemandQtySupplyId)}: the row is already on the view, and
 * the id lookup is a second, selection-backed DB round trip per click and per precondition probe.
 * <p>
 * Both paths are driven the way production drives them: the precondition through {@link ProcessPreconditionChecker}
 * BY CLASSNAME, the execution through {@code doIt()} on a process initialised from the same preconditions context.
 * The repository's {@code getById} is stubbed to return a DIFFERENT row, so a jump that still reads it is caught
 * twice: the call itself, and the query that comes out wrong.
 */
class QtyDemandQtySupplyJumpSeamTest
{
	private static final WindowId WINDOW_ID = WindowId.of(540000);

	private static final int ROW_ID = 4711;
	private static final int AD_TABLE_ID = 540999;

	private static final int PRODUCT_ID = 101;
	private static final int WAREHOUSE_ID = 102;
	private static final int ORG_ID = 103;
	private static final String ATTRIBUTES_KEY = "1000001";

	/** What a (wrongly) consulted repository would hand out: nothing here equals the clicked row. */
	private static final int STALE_PRODUCT_ID = 999;

	private QtyDemandSupplyRepository qtyDemandSupplyRepository;
	private ShipmentScheduleRepository shipmentScheduleRepository;
	private IViewsRepository viewsRepository;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final I_AD_Table adTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		adTable.setAD_Table_ID(AD_TABLE_ID);
		adTable.setTableName(I_QtyDemand_QtySupply_V.Table_Name);
		InterfaceWrapperHelper.saveRecord(adTable);

		qtyDemandSupplyRepository = Mockito.mock(QtyDemandSupplyRepository.class);
		Mockito.when(qtyDemandSupplyRepository.getById(Mockito.any())).thenReturn(QtyDemandQtySupply.builder()
				.id(QtyDemandQtySupplyId.ofRepoId(ROW_ID))
				.productId(ProductId.ofRepoId(STALE_PRODUCT_ID))
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.orgId(OrgId.ofRepoId(ORG_ID))
				.attributesKey(AttributesKey.ofString(ATTRIBUTES_KEY))
				.build());

		shipmentScheduleRepository = Mockito.mock(ShipmentScheduleRepository.class);
		Mockito.when(shipmentScheduleRepository.existsByQuery(Mockito.any())).thenReturn(true);
		Mockito.when(shipmentScheduleRepository.getIdsByQuery(Mockito.any())).thenReturn(ImmutableSet.of());

		final QtyDemandQtySupplyJumpService service = new QtyDemandQtySupplyJumpService(
				qtyDemandSupplyRepository,
				shipmentScheduleRepository,
				Mockito.mock(PPOrderCandidateDAO.class));
		SpringContextHolder.registerJUnitBean(QtyDemandQtySupplyJumpService.class, service);
		// ViewBasedProcessTemplate (the jump base class) resolves it on construction
		viewsRepository = Mockito.mock(IViewsRepository.class);
		SpringContextHolder.registerJUnitBean(IViewsRepository.class, viewsRepository);
	}

	/** The one row the user clicked, carrying exactly the four fields a jump needs. */
	private static IViewRow clickedRow()
	{
		return ViewRow.builder(WINDOW_ID)
				.setRowId(DocumentId.of(ROW_ID))
				.putFieldValue(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Product_ID, PRODUCT_ID)
				.putFieldValue(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Warehouse_ID, WAREHOUSE_ID)
				.putFieldValue(I_QtyDemand_QtySupply_V.COLUMNNAME_AD_Org_ID, ORG_ID)
				.putFieldValue(I_QtyDemand_QtySupply_V.COLUMNNAME_AttributesKey, ATTRIBUTES_KEY)
				.build();
	}

	private static ViewAsPreconditionsContext contextWithClickedRowSelected()
	{
		final IViewRow row = clickedRow();
		final TableRecordReference rowRecordRef = TableRecordReference.of(AD_TABLE_ID, ROW_ID);

		final ViewId viewId = ViewId.random(WINDOW_ID);
		final IView view = Mockito.mock(IView.class);
		Mockito.when(view.getViewId()).thenReturn(viewId);
		Mockito.when(view.streamByIds(Mockito.any())).thenAnswer(invocation -> java.util.stream.Stream.of(row));
		Mockito.when(view.getById(Mockito.any())).thenReturn(row);
		Mockito.when(view.getTableRecordReferenceOrNull(Mockito.any())).thenReturn(rowRecordRef);

		return ViewAsPreconditionsContext.builder()
				.view(view)
				.viewRowIdsSelection(ViewRowIdsSelection.of(viewId, DocumentIdsSelection.of(ImmutableList.of(row.getId()))))
				.build();
	}

	private static ShipmentScheduleQuery expectedQuery()
	{
		return ShipmentScheduleQuery.builder()
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.orgId(OrgId.ofRepoId(ORG_ID))
				.productId(ProductId.ofRepoId(PRODUCT_ID))
				.attributesKey(AttributesKey.ofString(ATTRIBUTES_KEY))
				.onlyNonZeroReservedQty(true)
				.build();
	}

	@Test
	@DisplayName("precondition: the shipment jump probes with the CLICKED ROW's product/warehouse/org/attributesKey and never calls getById")
	void preconditionUsesTheClickedRow()
	{
		final ProcessPreconditionsResolution resolution = ProcessPreconditionChecker.newInstance()
				.setProcess(QtyDemand_QtySupply_V_to_ShipmentSchedule.class.getName())
				.setPreconditionsContext(contextWithClickedRowSelected())
				.checkApplies();

		Mockito.verify(qtyDemandSupplyRepository, Mockito.never()).getById(Mockito.any());

		final ArgumentCaptor<ShipmentScheduleQuery> query = ArgumentCaptor.forClass(ShipmentScheduleQuery.class);
		Mockito.verify(shipmentScheduleRepository).existsByQuery(query.capture());
		assertThat(query.getValue()).isEqualTo(expectedQuery());

		assertThat(resolution.isAccepted()).as("the probe found something, so the jump is offered").isTrue();
	}

	@Test
	@DisplayName("doIt: the shipment jump queries with the CLICKED ROW's product/warehouse/org/attributesKey and never calls getById")
	void doItUsesTheClickedRow() throws Exception
	{
		final QtyDemand_QtySupply_V_to_ShipmentSchedule process = new QtyDemand_QtySupply_V_to_ShipmentSchedule();
		initLikeThePlatform(process, contextWithClickedRowSelected());

		Throwable failure = null;
		try
		{
			process.doIt();
		}
		catch (final Throwable t)
		{
			// reported only AFTER the two contract assertions below, which are the point of this test
			failure = t;
		}

		Mockito.verify(qtyDemandSupplyRepository, Mockito.never()).getById(Mockito.any());

		final ArgumentCaptor<ShipmentScheduleQuery> query = ArgumentCaptor.forClass(ShipmentScheduleQuery.class);
		Mockito.verify(shipmentScheduleRepository).getIdsByQuery(query.capture());
		assertThat(query.getValue()).isEqualTo(expectedQuery());

		assertThat(failure).as("doIt() must run to completion").isNull();
	}

	/**
	 * {@code JavaProcess#init(IProcessPreconditionsContext)} is {@code protected} in another package, and the
	 * platform's {@code init(ProcessInfo)} is what hands the process its record: do both, as the platform does.
	 */
	private void initLikeThePlatform(final QtyDemandQtySupplyJumpProcess process, final IProcessPreconditionsContext context) throws Exception
	{
		final Method init = de.metas.process.JavaProcess.class.getDeclaredMethod("init", IProcessPreconditionsContext.class);
		init.setAccessible(true);
		init.invoke(process, context);

		// the platform passes the view + selection to a view-based process as internal process parameters
		final ViewAsPreconditionsContext viewContext = (ViewAsPreconditionsContext)context;
		final IView view = viewContext.getView();
		Mockito.when(viewsRepository.getView(view.getViewId().getViewId())).thenReturn(view);

		process.init(ProcessInfo.builder()
				.addParameter("$WEBUI_ViewId", view.getViewId().getViewId())
				.addParameter("$WEBUI_ViewSelectedIds", String.valueOf(ROW_ID))
				.setCtx(Env.getCtx())
				.setClassname(process.getClass().getName())
				.setAD_User_ID(100)
				.setRecord(AD_TABLE_ID, ROW_ID)
				.build());
	}
}
