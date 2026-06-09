/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.picking.job_schedule.service.commands.PickingJobScheduleAutoAssignRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderPickingType;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceCreateRequest;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceRepository;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith({AdempiereTestWatcher.class})
public class PickingJobScheduleAutoAssignCommandTest
{
	private PickingJobScheduleService pickingJobScheduleService;
	private WorkplaceRepository workplaceRepository;

	private I_C_UOM uom;
	private ProductCategoryId productCategoryId;
	private ProductId productId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL = Mockito.mock(IShipmentScheduleInvalidateBL.class);
		Services.registerService(IShipmentScheduleInvalidateBL.class, shipmentScheduleInvalidateBL);

		workplaceRepository = WorkplaceRepository.newInstanceForUnitTesting();
		pickingJobScheduleService = PickingJobScheduleService.newInstanceForUnitTesting();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());

		uom = BusinessTestHelper.createUOM("stockUOM");
		productCategoryId = BusinessTestHelper.createProductCategory("standardCategory", null);
		productId = BusinessTestHelper.createProduct("product", uom, productCategoryId);
	}

	@Test
	public void testAutoAssign_max_picking_jobs_zero()
	{
		final ShipmentScheduleId shipmentScheduleId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.TEN)
				.build();
		final ShipmentScheduleId shipmentScheduleId2 = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE)
				.build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN)
				.name("Test1")
				.seqNo(SeqNo.ofInt(10))
				.maxPickingJobs(0)
				.build());

		pickingJobScheduleService.autoAssign(PickingJobScheduleAutoAssignRequest.builder()
				.preparationDate(SystemTime.asLocalDate())
				.build());

		final ImmutableList<PickingJobSchedule> pickingJobs = pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
				.onlyShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId, shipmentScheduleId2))
				.build()).collect(ImmutableList.toImmutableList());

		assertThat(pickingJobs.size()).as("PickingJobs count").isEqualTo(0);
	}

	@Test
	public void testAutoAssign_max_picking_jobs()
	{
		final ShipmentScheduleId shipmentScheduleId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.TEN)
				.build();
		final ShipmentScheduleId shipmentScheduleId2 = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE)
				.build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN)
				.name("Test1")
				.seqNo(SeqNo.ofInt(10))
				.maxPickingJobs(1)
				.build());

		pickingJobScheduleService.autoAssign(PickingJobScheduleAutoAssignRequest.builder()
				.preparationDate(SystemTime.asLocalDate())
				.build());

		final ImmutableList<PickingJobSchedule> pickingJobs = pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
				.onlyShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId, shipmentScheduleId2))
				.build()).collect(ImmutableList.toImmutableList());

		assertThat(pickingJobs.size()).as("PickingJobs count").isEqualTo(1);
	}

	@Test
	public void testAutoAssign_seqNo_Order_respected()
	{
		final ShipmentScheduleId shipmentScheduleId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE)
				.build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN)
				.name("Test1")
				.seqNo(SeqNo.ofInt(20))
				.maxPickingJobs(10)
				.build());

		final Workplace workplace2 = workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN)
				.name("Test2")
				.seqNo(SeqNo.ofInt(10))
				.maxPickingJobs(10)
				.build());

		pickingJobScheduleService.autoAssign(PickingJobScheduleAutoAssignRequest.builder()
				.preparationDate(SystemTime.asLocalDate())
				.build());

		final ImmutableList<PickingJobSchedule> pickingJobs = pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
				.onlyShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId))
				.build()).collect(ImmutableList.toImmutableList());

		assertThat(pickingJobs.size()).as("PickingJobs count").isEqualTo(1);
		assertThat(WorkplaceId.equals(pickingJobs.get(0).getWorkplaceId(), workplace2.getId())).as("Correct Workplace used").isTrue();
	}

	@Test
	public void testAutoAssign_matching_warehouse()
	{
		final ShipmentScheduleId shipmentScheduleId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE)
				.build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.ofRepoId(10))
				.name("Test1")
				.seqNo(SeqNo.ofInt(10))
				.maxPickingJobs(10)
				.build());

		final Workplace workplace2 = workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN)
				.name("Test2")
				.seqNo(SeqNo.ofInt(20))
				.maxPickingJobs(10)
				.build());

		pickingJobScheduleService.autoAssign(PickingJobScheduleAutoAssignRequest.builder()
				.preparationDate(SystemTime.asLocalDate())
				.build());

		final ImmutableList<PickingJobSchedule> pickingJobs = pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
				.onlyShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId))
				.build()).collect(ImmutableList.toImmutableList());

		assertThat(pickingJobs.size()).as("PickingJobs count").isEqualTo(1);
		assertThat(WorkplaceId.equals(pickingJobs.get(0).getWorkplaceId(), workplace2.getId())).as("Correct Workplace used").isTrue();
	}

	@Test
	public void testAutoAssign_matching_order_type_single_no_order_id()
	{
		final ShipmentScheduleId shipmentScheduleId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE)
				.build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN)
				.name("Test1")
				.orderPickingType(OrderPickingType.Multiple)
				.seqNo(SeqNo.ofInt(10))
				.maxPickingJobs(10)
				.build());

		final Workplace workplace2 = workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN)
				.name("Test2")
				.orderPickingType(OrderPickingType.Single)
				.seqNo(SeqNo.ofInt(20))
				.maxPickingJobs(10)
				.build());

		pickingJobScheduleService.autoAssign(PickingJobScheduleAutoAssignRequest.builder()
				.preparationDate(SystemTime.asLocalDate())
				.build());

		final ImmutableList<PickingJobSchedule> pickingJobs = pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
				.onlyShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId))
				.build()).collect(ImmutableList.toImmutableList());

		assertThat(pickingJobs.size()).as("PickingJobs count").isEqualTo(1);
		assertThat(WorkplaceId.equals(pickingJobs.get(0).getWorkplaceId(), workplace2.getId())).as("Correct Workplace used").isTrue();
	}

	@Test
	public void testAutoAssign_bpGroup_noRestriction_matches()
	{
		final BPartnerId bpartnerId = createBPartner(createBPGroup("g1", null));
		final ShipmentScheduleId schedId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE)
				.bpartnerId(bpartnerId.getRepoId())
				.build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN).name("WP").seqNo(SeqNo.ofInt(10)).maxPickingJobs(10)
				.build());

		assertThat(countAssignedJobs(schedId)).as("no BP-group restriction matches any partner").isEqualTo(1);
	}

	@Test
	public void testAutoAssign_bpGroup_directMatch()
	{
		final BPGroupId g1 = createBPGroup("g1", null);
		final BPartnerId bpartnerId = createBPartner(g1);
		final ShipmentScheduleId schedId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE).bpartnerId(bpartnerId.getRepoId()).build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN).name("WP").seqNo(SeqNo.ofInt(10)).maxPickingJobs(10)
				.bpGroupId(g1)
				.build());

		assertThat(countAssignedJobs(schedId)).as("direct BP-group match").isEqualTo(1);
	}

	@Test
	public void testAutoAssign_bpGroup_multiGroupRestriction_match()
	{
		final BPGroupId g1 = createBPGroup("g1", null);
		final BPGroupId g2 = createBPGroup("g2", null);
		final BPartnerId bpartnerId = createBPartner(g1);
		final ShipmentScheduleId schedId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE).bpartnerId(bpartnerId.getRepoId()).build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN).name("WP").seqNo(SeqNo.ofInt(10)).maxPickingJobs(10)
				.bpGroupId(g1)
				.bpGroupId(g2)
				.build());

		assertThat(countAssignedJobs(schedId)).as("workplace restricted to several groups matches a partner in one of them").isEqualTo(1);
	}

	@Test
	public void testAutoAssign_bpGroup_parentMatch()
	{
		final BPGroupId parent = createBPGroup("parent", null);
		final BPGroupId child = createBPGroup("child", parent);
		final BPartnerId bpartnerId = createBPartner(child);
		final ShipmentScheduleId schedId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE).bpartnerId(bpartnerId.getRepoId()).build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN).name("WP").seqNo(SeqNo.ofInt(10)).maxPickingJobs(10)
				.bpGroupId(parent)
				.build());

		assertThat(countAssignedJobs(schedId)).as("parent BP-group match").isEqualTo(1);
	}

	@Test
	public void testAutoAssign_bpGroup_noMatch()
	{
		final BPGroupId g1 = createBPGroup("g1", null);
		final BPGroupId g2 = createBPGroup("g2", null);
		final BPartnerId bpartnerId = createBPartner(g1);
		final ShipmentScheduleId schedId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE).bpartnerId(bpartnerId.getRepoId()).build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN).name("WP").seqNo(SeqNo.ofInt(10)).maxPickingJobs(10)
				.bpGroupId(g2)
				.build());

		assertThat(countAssignedJobs(schedId)).as("non-matching BP-group → not assigned").isEqualTo(0);
	}

	@Test
	public void testAutoAssign_docType_match()
	{
		final DocTypeId docTypeId = createSalesOrderDocType();
		final OrderAndLineId orderAndLineId = createCompletedOrderWithLine(docTypeId);
		final ShipmentScheduleId schedId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE)
				.orderId(orderAndLineId.getOrderId())
				.orderLineId(orderAndLineId.getOrderLineId().getRepoId())
				.build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN).name("WP").seqNo(SeqNo.ofInt(10)).maxPickingJobs(10)
				.docTypeId(docTypeId)
				.build());

		assertThat(countAssignedJobs(schedId)).as("matching order doctype").isEqualTo(1);
	}

	@Test
	public void testAutoAssign_docType_noMatch()
	{
		final DocTypeId docTypeOnOrder = createSalesOrderDocType();
		final DocTypeId docTypeOnWorkplace = createSalesOrderDocType();
		final OrderAndLineId orderAndLineId = createCompletedOrderWithLine(docTypeOnOrder);
		final ShipmentScheduleId schedId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE)
				.orderId(orderAndLineId.getOrderId())
				.orderLineId(orderAndLineId.getOrderLineId().getRepoId())
				.build();

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN).name("WP").seqNo(SeqNo.ofInt(10)).maxPickingJobs(10)
				.docTypeId(docTypeOnWorkplace)
				.build());

		assertThat(countAssignedJobs(schedId)).as("non-matching order doctype → not assigned").isEqualTo(0);
	}

	@Test
	public void testAutoAssign_docType_nullOrder_noMatch()
	{
		final DocTypeId docTypeId = createSalesOrderDocType();
		final ShipmentScheduleId schedId = createShipmentSchedule()
				.qtyToDeliver(BigDecimal.ONE)
				.build(); // no order

		workplaceRepository.create(WorkplaceCreateRequest.builder()
				.warehouseId(WarehouseId.MAIN).name("WP").seqNo(SeqNo.ofInt(10)).maxPickingJobs(10)
				.docTypeId(docTypeId)
				.build());

		assertThat(countAssignedJobs(schedId)).as("DocType-restricted workplace skips schedule without an order").isEqualTo(0);
	}

	private int countAssignedJobs(final ShipmentScheduleId... shipmentScheduleIds)
	{
		pickingJobScheduleService.autoAssign(PickingJobScheduleAutoAssignRequest.builder()
				.preparationDate(SystemTime.asLocalDate())
				.build());

		return pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
						.onlyShipmentScheduleIds(ImmutableSet.copyOf(shipmentScheduleIds))
						.build())
				.collect(ImmutableList.toImmutableList())
				.size();
	}

	private BPGroupId createBPGroup(@NonNull final String name, @Nullable final BPGroupId parentGroupId)
	{
		final I_C_BP_Group group = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		group.setName(name);
		if (parentGroupId != null)
		{
			group.setParent_BP_Group_ID(parentGroupId.getRepoId());
		}
		saveRecord(group);
		return BPGroupId.ofRepoId(group.getC_BP_Group_ID());
	}

	private BPartnerId createBPartner(@NonNull final BPGroupId bpGroupId)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setName("bp_" + bpGroupId.getRepoId());
		bpartner.setC_BP_Group_ID(bpGroupId.getRepoId());
		saveRecord(bpartner);
		return BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
	}

	private DocTypeId createSalesOrderDocType()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("SalesOrderDocType");
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_SalesOrder);
		saveRecord(docType);
		return DocTypeId.ofRepoId(docType.getC_DocType_ID());
	}

	private OrderAndLineId createCompletedOrderWithLine(@NonNull final DocTypeId docTypeId)
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_DocType_ID(docTypeId.getRepoId());
		order.setDocStatus(X_C_Order.DOCSTATUS_Completed);
		saveRecord(order);

		// the shipment schedule needs a C_OrderLine_ID for its OrderAndLineId (and thus getOrderId()) to be non-null
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(productId.getRepoId());
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		orderLine.setQtyOrdered(BigDecimal.ONE);
		orderLine.setQtyEntered(BigDecimal.ONE);
		saveRecord(orderLine);

		return OrderAndLineId.ofRepoIds(order.getC_Order_ID(), orderLine.getC_OrderLine_ID());
	}

	@Builder(builderMethodName = "createShipmentSchedule", builderClassName = "$ShipmentScheduleBuilder")
	private ShipmentScheduleId setupShipmentSchedule(
			@Nullable final WarehouseId warehouseId,
			@Nullable final OrderId orderId,
			@Nullable final Integer orderLineId,
			@Nullable final LocalDate preparationDate,
			@Nullable final Integer bpartnerId,
			@NonNull final BigDecimal qtyToDeliver
	)
	{
		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		sched.setC_BPartner_ID(bpartnerId != null ? bpartnerId : 1);
		sched.setC_BPartner_Location_ID(1);
		sched.setM_Product_ID(productId.getRepoId());
		sched.setM_Warehouse_ID(warehouseId != null ? warehouseId.getRepoId() : WarehouseId.MAIN.getRepoId());
		sched.setC_Order_ID(OrderId.toRepoId(orderId));
		if (orderLineId != null)
		{
			sched.setC_OrderLine_ID(orderLineId);
		}
		sched.setPreparationDate(preparationDate != null ? TimeUtil.asTimestamp(preparationDate) : TimeUtil.asTimestamp(SystemTime.asLocalDate()));
		sched.setProcessed(false);
		sched.setQtyToDeliver(qtyToDeliver);
		sched.setExportStatus(APIExportStatus.DontExport.getCode());
		sched.setCarrier_Advising_Status(CarrierAdviseStatus.Completed.getCode());

		saveRecord(sched);
		return ShipmentScheduleId.ofRepoId(sched.getM_ShipmentSchedule_ID());
	}
}
