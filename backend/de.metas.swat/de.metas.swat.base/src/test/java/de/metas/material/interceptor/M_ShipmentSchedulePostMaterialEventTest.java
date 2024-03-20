package de.metas.material.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLineFactory;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.shipmentschedule.AbstractShipmentScheduleEvent;
import de.metas.material.event.shipmentschedule.OldShipmentScheduleData;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDetail;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class M_ShipmentSchedulePostMaterialEventTest
{
	private ShipmentScheduleReferencedLineFactory shipmentScheduleReferencedLineFactory;

	private static final BPartnerId BPARTNER_ID1 = BPartnerId.ofRepoId(40);
	private static final BPartnerId BPARTNER_ID2 = BPartnerId.ofRepoId(45);

	//private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(30);

	//private static final int PRODUCT_ID = 20;

	private static final BigDecimal ONE = BigDecimal.ONE;
	private static final BigDecimal FOUR = new BigDecimal("4");
	private static final BigDecimal FIVE = new BigDecimal("5");
	private static final BigDecimal TEN = BigDecimal.TEN;
	private static final BigDecimal TWENTY = new BigDecimal("20");

	private I_M_ShipmentSchedule shipmentSchedule;
	private WarehouseId warehouseId;
	private ProductId productId;

	@Interceptor(I_M_ShipmentSchedule.class)
	private static class M_ShipmentSchedule_Mocked extends M_ShipmentSchedule_PostMaterialEvent
	{
		@Setter
		private I_M_ShipmentSchedule oldShipmentSchedule;

		public M_ShipmentSchedule_Mocked(
				@NonNull final PostMaterialEventService postMaterialEventService,
				@NonNull final ShipmentScheduleReferencedLineFactory referencedLineFactory,
				@NonNull final ModelProductDescriptorExtractor productDescriptorFactory,
				@NonNull final ReplenishInfoRepository replenishInfoRepository)
		{
			super(postMaterialEventService, referencedLineFactory, productDescriptorFactory, replenishInfoRepository);
		}

		@Override
		I_M_ShipmentSchedule toOldValues(final I_M_ShipmentSchedule shipmentSchedule)
		{
			return oldShipmentSchedule;
		}
	}

	private M_ShipmentSchedule_Mocked shipmentScheduleInterceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		shipmentScheduleReferencedLineFactory = Mockito.mock(ShipmentScheduleReferencedLineFactory.class);

		shipmentScheduleInterceptor = new M_ShipmentSchedule_Mocked(
				Mockito.mock(PostMaterialEventService.class),
				shipmentScheduleReferencedLineFactory,
				new ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory(),
				new ReplenishInfoRepository());

		final I_M_Product productRecord = BusinessTestHelper.createProduct("Product", BusinessTestHelper.createUomKg());
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_M_Warehouse warehouseRecord = BusinessTestHelper.createWarehouse("Warehouse");
		warehouseId = WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());

		final I_M_ShipmentSchedule oldShipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		oldShipmentSchedule.setQtyOrdered_Calculated(TWENTY); // note that setQtyOrdered is just for display!, QtyOrdered_Calculated one or QtyOrdered_Override is where the qty is!
		oldShipmentSchedule.setQtyReserved(FOUR);
		oldShipmentSchedule.setM_Product_ID(productId.getRepoId());
		oldShipmentSchedule.setM_Warehouse_ID(warehouseId.getRepoId());
		oldShipmentSchedule.setC_BPartner_ID(BPARTNER_ID1.getRepoId());
		oldShipmentSchedule.setC_BPartner_Override_ID(BPARTNER_ID2.getRepoId());
		save(oldShipmentSchedule);
		shipmentScheduleInterceptor.setOldShipmentSchedule(oldShipmentSchedule);

		shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setQtyOrdered_Calculated(TEN); // decrease by ten
		shipmentSchedule.setQtyReserved(FIVE); // increase by one
		shipmentSchedule.setM_Product_ID(productId.getRepoId());
		shipmentSchedule.setM_Warehouse_ID(warehouseId.getRepoId());
		shipmentSchedule.setC_BPartner_ID(BPARTNER_ID1.getRepoId());
		shipmentSchedule.setC_BPartner_Override_ID(BPARTNER_ID2.getRepoId());
		save(shipmentSchedule);
	}

	@Test
	public void createShipmentscheduleEvent_after_new()
	{
		final OrderLineDescriptor orderLineDescriptor = OrderLineDescriptor.builder()
				.orderBPartnerId(10)
				.orderId(20)
				.orderLineId(30)
				.build();
		setupShipmentScheduleReferencedLineFactory(orderLineDescriptor);

		final AbstractShipmentScheduleEvent result = shipmentScheduleInterceptor
				.createShipmentScheduleEvent(shipmentSchedule, ModelChangeType.AFTER_NEW);

		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(ShipmentScheduleCreatedEvent.class);

		final ShipmentScheduleCreatedEvent createdEvent = (ShipmentScheduleCreatedEvent)result;
		final ShipmentScheduleDetail shipmentScheduleDetail = createdEvent.getShipmentScheduleDetail();
		assertThat(shipmentScheduleDetail).isNotNull();
		assertThat(createdEvent.getShipmentScheduleId()).isEqualTo(shipmentSchedule.getM_ShipmentSchedule_ID());

		assertThat(createdEvent.getMaterialDescriptor().getCustomerId()).isEqualTo(BPARTNER_ID2);
		assertThat(createdEvent.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(TEN);
		assertThat(createdEvent.getMaterialDescriptor().getProductId()).isEqualTo(productId.getRepoId());
		assertThat(createdEvent.getMaterialDescriptor().getWarehouseId()).isEqualTo(warehouseId);
		assertThat(shipmentScheduleDetail.getReservedQuantity()).isEqualByComparingTo(FIVE);
		assertThat(createdEvent.getDocumentLineDescriptor()).isEqualTo(orderLineDescriptor);
	}

	/**
	 * Make sure that {@link ShipmentScheduleReferencedLineFactory} will return a {@link ShipmentScheduleReferencedLine}
	 * that contains the given {@code orderLineDescriptor}.
	 */
	private void setupShipmentScheduleReferencedLineFactory(@NonNull final OrderLineDescriptor orderLineDescriptor)
	{
		final ShipmentScheduleReferencedLine shipmentScheduleReferencedLine = //
				ShipmentScheduleReferencedLine.builder()
						.recordRef(TableRecordReference.of(I_C_Order.Table_Name, 10))
						.shipperId(ShipperId.optionalOfRepoId(20))
						.warehouseId(warehouseId)
						.documentLineDescriptor(orderLineDescriptor).build();

		Mockito.when(shipmentScheduleReferencedLineFactory.createFor(shipmentSchedule))
				.thenReturn(shipmentScheduleReferencedLine);
	}

	@Test
	public void createShipmentscheduleEvent_after_change()
	{
		final OrderLineDescriptor orderLineDescriptor = OrderLineDescriptor.builder()
				.orderBPartnerId(10)
				.orderId(20)
				.orderLineId(30)
				.build();
		setupShipmentScheduleReferencedLineFactory(orderLineDescriptor);

		final AbstractShipmentScheduleEvent result = shipmentScheduleInterceptor
				.createShipmentScheduleEvent(shipmentSchedule, ModelChangeType.AFTER_CHANGE);

		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(ShipmentScheduleUpdatedEvent.class);

		final ShipmentScheduleUpdatedEvent updatedEvent = (ShipmentScheduleUpdatedEvent)result;
		final ShipmentScheduleDetail shipmentScheduleDetail = updatedEvent.getShipmentScheduleDetail();
		assertThat(shipmentScheduleDetail).isNotNull();

		assertThat(updatedEvent.getShipmentScheduleId()).isEqualTo(shipmentSchedule.getM_ShipmentSchedule_ID());
		assertThat(updatedEvent.getMaterialDescriptor().getCustomerId()).isEqualTo(BPARTNER_ID2);
		assertThat(updatedEvent.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(TEN);
		assertThat(updatedEvent.getMaterialDescriptor().getProductId()).isEqualTo(productId.getRepoId());
		assertThat(updatedEvent.getMaterialDescriptor().getWarehouseId()).isEqualTo(warehouseId);

		assertThat(shipmentScheduleDetail.getReservedQuantity()).isEqualByComparingTo(FIVE);
		assertThat(shipmentScheduleDetail.getReservedQuantityDelta()).isEqualByComparingTo(FIVE);

		final OldShipmentScheduleData oldShipmentScheduleData = shipmentScheduleDetail.getOldShipmentScheduleData();
		assertThat(oldShipmentScheduleData).isNotNull();
		final MaterialDescriptor oldMaterialDescriptor = oldShipmentScheduleData.getOldMaterialDescriptor();
		assertThat(oldMaterialDescriptor).isNotNull();
		assertThat(oldMaterialDescriptor.getDate()).isNotEqualTo(updatedEvent.getMaterialDescriptor().getDate());
		assertThat(oldMaterialDescriptor.getQuantity()).isEqualByComparingTo(TWENTY);
		assertThat(oldShipmentScheduleData.getOldReservedQuantity()).isEqualByComparingTo(FOUR);
	}

	@Test
	public void createShipmentscheduleEvent_before_delete()
	{
		final AbstractShipmentScheduleEvent result = shipmentScheduleInterceptor
				.createShipmentScheduleEvent(shipmentSchedule, ModelChangeType.BEFORE_DELETE);

		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(ShipmentScheduleDeletedEvent.class);

		final ShipmentScheduleDeletedEvent deletedEvent = (ShipmentScheduleDeletedEvent)result;
		final ShipmentScheduleDetail shipmentScheduleDetail = deletedEvent.getShipmentScheduleDetail();
		assertThat(shipmentScheduleDetail).isNotNull();

		assertThat(deletedEvent.getShipmentScheduleId()).isEqualTo(shipmentSchedule.getM_ShipmentSchedule_ID());
		assertThat(deletedEvent.getMaterialDescriptor().getCustomerId()).isEqualTo(BPARTNER_ID2);
		assertThat(deletedEvent.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(TEN);
		assertThat(deletedEvent.getMaterialDescriptor().getProductId()).isEqualTo(productId.getRepoId());
		assertThat(deletedEvent.getMaterialDescriptor().getWarehouseId()).isEqualTo(warehouseId);
		assertThat(shipmentScheduleDetail.getReservedQuantity()).isEqualByComparingTo(FIVE);
	}
}
