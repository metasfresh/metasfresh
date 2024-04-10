package de.metas.handlingunits.shipmentschedule.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.acct.GLCategoryId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.impl.ShipperTransportationRepository;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUFactory;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUSupportingServices;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.DefaultInOutGenerateResult;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.order.DeliveryRule;
import de.metas.order.OrderId;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule.DELIVERY_DATE;
import static de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule.DELIVERY_DATE_OR_TODAY;
import static de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule.TODAY;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class InOutProducerFromShipmentScheduleWithHUTest
{
	private ShipmentScheduleWithHUFactory shipmentScheduleWithHUFactory;
	private BPartnerLocationId bpartnerAndLocationId;
	private WarehouseId warehouseId;
	private I_C_UOM uom;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new ShipperTransportationRepository());

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));

		//noinspection resource
		Loggables.temporarySetLoggable(Loggables.console());

		shipmentScheduleWithHUFactory = ShipmentScheduleWithHUFactory.builder()
				.supportingServices(ShipmentScheduleWithHUSupportingServices.getInstance())
				.huContext(Services.get(IHUContextFactory.class).createMutableHUContext())
				.build();

		this.uom = uom("uom");
	}

	@SuppressWarnings("SameParameterValue")
	private I_C_UOM uom(final String name)
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setX12DE355(name);
		saveRecord(uom);
		return uom;
	}

	@SuppressWarnings("SameParameterValue")
	private ProductId product(final String name, final I_C_UOM uom)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Builder(builderMethodName = "shipmentSchedule", builderClassName = "ShipmentScheduleBuilder")
	private ShipmentScheduleWithHU createShipmentSchedule(
			@NonNull final ProductId productId,
			@NonNull final String qtyOrdered,
			@NonNull final String qtyToDeliver,
			@NonNull final DeliveryRule deliveryRule,
			@Nullable final OrderId orderId)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		saveRecord(orderLine);

		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setM_Warehouse_ID(warehouseId.getRepoId());
		shipmentSchedule.setC_BPartner_ID(bpartnerAndLocationId.getBpartnerId().getRepoId());
		shipmentSchedule.setC_BPartner_Location_ID(bpartnerAndLocationId.getRepoId());
		shipmentSchedule.setM_Product_ID(productId.getRepoId());
		// shipmentSchedule.setQtyOrdered(new BigDecimal(qtyOrdered)); // not needed
		shipmentSchedule.setQtyOrdered_Calculated(new BigDecimal(qtyOrdered));
		shipmentSchedule.setQtyToDeliver(new BigDecimal(qtyToDeliver));

		shipmentSchedule.setDeliveryRule(deliveryRule.getCode());

		shipmentSchedule.setC_Order_ID(OrderId.toRepoId(orderId));
		shipmentSchedule.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		shipmentSchedule.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_OrderLine.class));
		shipmentSchedule.setRecord_ID(orderLine.getC_OrderLine_ID());

		saveRecord(shipmentSchedule);

		return shipmentScheduleWithHUFactory.ofShipmentScheduleWithoutHu(
				shipmentSchedule,
				StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(qtyToDeliver), productId),
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);
	}

	@Nested
	public class isShipmentDeliveryDateBetterThanMovementDate
	{
		private I_M_InOut createShipment(final LocalDate date)
		{
			final I_M_InOut shipment = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
			shipment.setMovementDate(TimeUtil.asTimestamp(date));
			InterfaceWrapperHelper.save(shipment);

			return shipment;
		}

		@Test
		public void SameDate()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T18:00:00+01:00");

			final LocalDate today = LocalDate.of(2017, 11, 10);

			final I_M_InOut shipment = createShipment(today);

			final boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, today);

			// the candidate date is not better than the already existing date in shipment, because it's the same date
			assertThat(isTodayBestForShipmentDate).isFalse();
		}

		@Test
		public void CandidateBeforeToday()
		{
			SystemTime.setFixedTimeSource("2017-11-10T18:15:16+01:00");

			final LocalDate yesterday = LocalDate.of(2017, 11, 9);
			final LocalDate today = LocalDate.of(2017, 11, 10);

			final I_M_InOut shipment = createShipment(today);

			final boolean isYesterdayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, yesterday);

			// the candidate date is not better than the already existing date in shipment because it's in the past
			assertThat(isYesterdayBestForShipmentDate).isFalse();
		}

		@Test
		public void CurrentYesterday_CandidateToday()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T02:30:20+01:00");

			final LocalDate yesterday = LocalDate.of(2017, 11, 9);

			final LocalDate today = de.metas.common.util.time.SystemTime.asLocalDate();

			final I_M_InOut shipment = createShipment(yesterday);

			final boolean isNowBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, today);

			// the candidate date is better than the already existing date in shipment because the existing date is in the past
			assertThat(isNowBestForShipmentDate).isTrue();
		}

		@Test
		public void CurrentToday_CandidateTomorrow()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T00:51:41+01:00");

			final LocalDate tomorrow = LocalDate.of(2017, 11, 12);

			final LocalDate today = de.metas.common.util.time.SystemTime.asLocalDate();

			final I_M_InOut shipment = createShipment(today);

			final boolean isNowBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, tomorrow);

			// the candidate date is not better than the already existing date in shipment because the existing date is before the candidate and not in the past
			assertThat(isNowBestForShipmentDate).isFalse();
		}

		@Test
		public void CurrentNextWeek_CandidateTomorrow()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T12:30:15+01:00");

			final LocalDate tomorrow = LocalDate.of(2017, 11, 12);
			final LocalDate nextWeek = LocalDate.of(2017, 11, 17);

			final I_M_InOut shipment = createShipment(nextWeek);

			final boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, tomorrow);

			// the candidate date is better than the already existing date in shipment because the existing date is after the candidate, and they are both in the future
			assertThat(isTodayBestForShipmentDate).isTrue();
		}

		@Test
		public void BothDatesInThePast()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T01:01:01+01:00");

			final LocalDate yesterday = LocalDate.of(2017, 11, 9);
			final LocalDate lastWeek = LocalDate.of(2017, 11, 3);

			final I_M_InOut shipment = createShipment(yesterday);

			final boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, lastWeek);

			// the candidate date is not better than the already existing date in shipment because they are both in the past.
			assertThat(isTodayBestForShipmentDate).isFalse();
		}
	}

	@Nested
	public class calculateShipmentDate
	{
		private ShipmentScheduleWithHU createSchedule(final LocalDate deliveryDate)
		{
			final ProductId productId = product("product", uom);

			final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
			schedule.setDeliveryDate(TimeUtil.asTimestamp(deliveryDate));
			InterfaceWrapperHelper.save(schedule);
			
			return shipmentScheduleWithHUFactory.ofShipmentScheduleWithoutHu(
					schedule,
					StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("100"), productId),
					M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);
		}

		@NonNull
		private InOutProducerFromShipmentScheduleWithHU newInOutProducerFromShipmentScheduleWithHU()
		{
			return new InOutProducerFromShipmentScheduleWithHU(new DefaultInOutGenerateResult());
		}

		@Test
		public void Today_CalculateShipmentRule_ForceToday()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T10:15:00+01:00");

			final LocalDate today = de.metas.common.util.time.SystemTime.asLocalDate();

			final ShipmentScheduleWithHU schedule = createSchedule(today);

			final LocalDate shipmentDate = newInOutProducerFromShipmentScheduleWithHU().calculateShipmentDate(schedule, TODAY);

			assertThat(shipmentDate).isEqualTo(today);
		}

		@Test
		public void Today_CalculateShipmentRule_None()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T19:17:16+01:00");

			final LocalDate today = de.metas.common.util.time.SystemTime.asLocalDate();

			final ShipmentScheduleWithHU schedule = createSchedule(today);

			final LocalDate shipmentDate = newInOutProducerFromShipmentScheduleWithHU().calculateShipmentDate(schedule, DELIVERY_DATE_OR_TODAY);

			assertThat(shipmentDate).isEqualTo(today);
		}

		@Test
		public void AnotherDate_CalculateShipmentRule_ForceToday()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T13:13:13+01:00");

			final LocalDate today = de.metas.common.util.time.SystemTime.asLocalDate();

			final LocalDate anotherDate = LocalDate.of(2017, 11, 17);

			final ShipmentScheduleWithHU schedule = createSchedule(anotherDate);

			final LocalDate shipmentDate = newInOutProducerFromShipmentScheduleWithHU().calculateShipmentDate(schedule, TODAY);

			assertThat(shipmentDate).isEqualTo(today);
		}

		@Test
		public void DateInFuture_CalculateShipmentRule_None()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T19:04:04+01:00");

			final LocalDate dateInFuture = LocalDate.of(2017, 11, 17);

			final ShipmentScheduleWithHU schedule = createSchedule(dateInFuture);

			final LocalDate shipmentDate = newInOutProducerFromShipmentScheduleWithHU().calculateShipmentDate(schedule, DELIVERY_DATE_OR_TODAY);

			assertThat(shipmentDate).isEqualTo(dateInFuture);
		}

		@Test
		public void DateInPast_CalculateShipmentRule_None()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T01:02:30+01:00");

			final LocalDate today = de.metas.common.util.time.SystemTime.asLocalDate();
			final LocalDate dateInPast = LocalDate.of(2017, 11, 3);

			final ShipmentScheduleWithHU schedule = createSchedule(dateInPast);

			final LocalDate shipmentDate = newInOutProducerFromShipmentScheduleWithHU().calculateShipmentDate(schedule, DELIVERY_DATE_OR_TODAY);

			assertThat(shipmentDate).isEqualTo(today);
		}

		@Test
		public void DateInPast_CalculateShipmentRule_ForceDeliveryDate()
		{
			//set up today
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T01:02:30+01:00");

			final LocalDate dateInPast = LocalDate.of(2017, 11, 3);

			final ShipmentScheduleWithHU schedule = createSchedule(dateInPast);

			final LocalDate shipmentDate = newInOutProducerFromShipmentScheduleWithHU().calculateShipmentDate(schedule, DELIVERY_DATE);

			assertThat(shipmentDate).isEqualTo(dateInPast);
		}

		@Test
		public void fixedDate()
		{
			de.metas.common.util.time.SystemTime.setFixedTimeSource("2017-11-10T01:02:30+01:00");

			final ShipmentScheduleWithHU schedule = createSchedule(LocalDate.parse("2018-11-03"));
			final LocalDate shipmentDate = newInOutProducerFromShipmentScheduleWithHU().calculateShipmentDate(
					schedule,
					CalculateShippingDateRule.fixedDate(LocalDate.parse("2010-02-03")));

			assertThat(shipmentDate).isEqualTo("2010-02-03");
		}
	}

	@Nested
	public class createShipments
	{
		private ITrxItemProcessorExecutorService trxItemProcessorExecutorService;

		@BeforeEach
		public void beforeEach()
		{
			Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
			Services.registerService(IShipmentScheduleInvalidateBL.class, new ShipmentScheduleInvalidateBL(new PickingBOMService()));
			Services.get(IShipmentScheduleHandlerBL.class).registerHandler(OrderLineShipmentScheduleHandler.newInstanceWithoutExtensions());

			trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);

			Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH); // needed for notifications

			createDocType(DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_MaterialDelivery));
			bpartnerAndLocationId = bpartnerAndLocation("BP");
			warehouseId = warehouse("WH");

			// TODO: change the underlying code to not need the whole shit if it's not needed
			// (i.e. no packing materials are really used)
			{
				final I_M_HU_PI pi = newInstance(I_M_HU_PI.class);
				pi.setM_HU_PI_ID(HuPackingInstructionsId.VIRTUAL.getRepoId());
				saveRecord(pi);

				final I_M_HU_PI_Version piv = newInstance(I_M_HU_PI_Version.class);
				piv.setM_HU_PI_ID(pi.getM_HU_PI_ID());
				piv.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
				piv.setIsCurrent(true);
				saveRecord(piv);

				final I_M_HU_PI_Item pii = newInstance(I_M_HU_PI_Item.class);
				pii.setM_HU_PI_Version_ID(piv.getM_HU_PI_Version_ID());
				pii.setM_HU_PI_Item_ID(HuPackingInstructionsItemId.VIRTUAL.getRepoId());
				saveRecord(pii);

				final I_M_HU_PI_Item_Product pip = newInstance(I_M_HU_PI_Item_Product.class);
				pip.setM_HU_PI_Item_ID(pii.getM_HU_PI_Item_ID());
				pip.setM_HU_PI_Item_Product_ID(HUPIItemProductId.VIRTUAL_HU.getRepoId());
				pip.setIsInfiniteCapacity(true);
				pip.setIsAllowAnyProduct(true);
				saveRecord(pip);
			}
		}

		@SuppressWarnings("SameParameterValue")
		private BPartnerLocationId bpartnerAndLocation(final String name)
		{
			final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
			bpartner.setValue(name);
			bpartner.setName(name);
			saveRecord(bpartner);

			final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
			bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
			saveRecord(bpLocation);

			return BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
		}

		@SuppressWarnings("SameParameterValue")
		private WarehouseId warehouse(String name)
		{
			final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
			warehouse.setName(name);
			saveRecord(warehouse);
			return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
		}

		private void createDocType(final DocBaseAndSubType docBaseAndSubType)
		{
			final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
			docTypeDAO.createDocType(IDocTypeDAO.DocTypeCreateRequest.builder()
					.ctx(Env.getCtx())
					.name(docBaseAndSubType.toString())
					.docBaseType(docBaseAndSubType.getDocBaseType())
					.docSubType(docBaseAndSubType.getDocSubType())
					.glCategoryId(GLCategoryId.ofRepoId(123))
					.build());
		}

		private OrderId order()
		{
			final I_C_DocType salesOrderDoctype = InterfaceWrapperHelper.create(Env.getCtx(), I_C_DocType.class, ITrx.TRXNAME_None);
			salesOrderDoctype.setDocBaseType(X_C_DocType.DOCBASETYPE_SalesOrder);
			salesOrderDoctype.setAD_Org_ID(0);
			saveRecord(salesOrderDoctype);

			final I_C_Order order = newInstance(I_C_Order.class);
			order.setC_DocType_ID(salesOrderDoctype.getC_DocType_ID());
			saveRecord(order);

			return OrderId.ofRepoId(order.getC_Order_ID());
		}

		private InOutGenerateResult process(final List<ShipmentScheduleWithHU> candidates)
		{
			System.out.println("Processing: " + candidates);

			final InOutProducerFromShipmentScheduleWithHU producer = new InOutProducerFromShipmentScheduleWithHU(new DefaultInOutGenerateResult(true));
			final InOutGenerateResult result = trxItemProcessorExecutorService
					.<ShipmentScheduleWithHU, InOutGenerateResult>createExecutor()
					.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
					.setProcessor(producer)
					.setExceptionHandler(FailTrxItemExceptionHandler.instance)
					.process(candidates);
			System.out.println("Got " + result);

			return result;

		}

		@Test
		public void allLinesAreCompleted()
		{
			final ShipmentScheduleBuilder candidateBuilder = shipmentSchedule()
					.deliveryRule(DeliveryRule.COMPLETE_ORDER)
					.orderId(order())
					.productId(product("product", uom("uom")));

			final List<ShipmentScheduleWithHU> candidates = Arrays.asList(
					candidateBuilder.qtyOrdered("100").qtyToDeliver("100").build(), //
					candidateBuilder.qtyOrdered("1").qtyToDeliver("1").build(), //
					candidateBuilder.qtyOrdered("2").qtyToDeliver("2").build(), //
					candidateBuilder.qtyOrdered("3").qtyToDeliver("3").build(), //
					candidateBuilder.qtyOrdered("4").qtyToDeliver("4").build() //
			);

			final InOutGenerateResult result = process(candidates);
			assertThat(result.getInOuts()).hasSize(1);
		}

		@Test
		public void allLinesAreCompleted_but_OneLineOutOfSelection()
		{
			final ShipmentScheduleBuilder candidateBuilder = shipmentSchedule()
					.deliveryRule(DeliveryRule.COMPLETE_ORDER)
					.orderId(order())
					.productId(product("product", uom("uom")));

			final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
					candidateBuilder.qtyOrdered("100").qtyToDeliver("100").build() //
			);

			// one line is out of our generate selection
			// don't care if is completed or not
			candidateBuilder.qtyOrdered("100").qtyToDeliver("100").build();

			final InOutGenerateResult result = process(candidates);
			assertThat(result.getInOuts()).isEmpty();
		}

		@Test
		public void oneIncompleteLine()
		{
			final ShipmentScheduleBuilder candidateBuilder = shipmentSchedule()
					.deliveryRule(DeliveryRule.COMPLETE_ORDER)
					.orderId(order())
					.productId(product("product", uom("uom")));

			final List<ShipmentScheduleWithHU> candidates = Arrays.asList(
					candidateBuilder.qtyOrdered("100").qtyToDeliver("100").build(), //
					candidateBuilder.qtyOrdered("1").qtyToDeliver("1").build(), //
					candidateBuilder.qtyOrdered("2").qtyToDeliver("2").build(), //
					candidateBuilder.qtyOrdered("3").qtyToDeliver("3").build(), //
					candidateBuilder.qtyOrdered("4").qtyToDeliver("2").build() // incomplete line
			);

			final InOutGenerateResult result = process(candidates);
			assertThat(result.getInOuts()).isEmpty();
		}

		@Test
		public void deliveryRule_not_CompleteOrder()
		{
			final ShipmentScheduleBuilder candidateBuilder = shipmentSchedule()
					.deliveryRule(DeliveryRule.AVAILABILITY)
					.orderId(order())
					.productId(product("product", uom("uom")));

			final List<ShipmentScheduleWithHU> candidates = Arrays.asList(
					candidateBuilder.qtyOrdered("100").qtyToDeliver("100").build(), //
					candidateBuilder.qtyOrdered("1").qtyToDeliver("1").build(), //
					candidateBuilder.qtyOrdered("2").qtyToDeliver("2").build(), //
					candidateBuilder.qtyOrdered("3").qtyToDeliver("3").build(), //
					candidateBuilder.qtyOrdered("4").qtyToDeliver("2").build() // incomplete line
			);

			final InOutGenerateResult result = process(candidates);
			assertThat(result.getInOuts()).hasSize(1);
		}

	}

}
