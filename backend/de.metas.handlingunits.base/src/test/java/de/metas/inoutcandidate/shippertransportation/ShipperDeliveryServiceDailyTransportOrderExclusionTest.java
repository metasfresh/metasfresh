package de.metas.inoutcandidate.shippertransportation;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.handlingunits.shipmentschedule.api.DeliveryOrderCarrierResolver;
import de.metas.handlingunits.shipping.InOutToTransportationOrderService;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.organization.IOrgDAO;
import de.metas.shipper.gateway.commons.ShipperGatewayFacade;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for AC-13d: a shipper with {@code IsCreateDeliveryPlanning='Y'} must NOT be added
 * to the daily (outgoing) transport order.  A flag='N' shipper IS added normally.
 */
class ShipperDeliveryServiceDailyTransportOrderExclusionTest
{
	// ---- mocked Services.get() dependencies ----
	private IInOutBL inOutBL;
	private IInOutDAO inOutDAO;
	private IOrgDAO orgDAO;
	private IWarehouseDAO warehouseDAO;
	private IShipperTransportationDAO shipperTransportationDAO;
	private IShipperDAO shipperDAO;

	// ---- mocked Spring-injected (constructor) dependencies ----
	private InOutToTransportationOrderService inOutToTransportationOrderService;
	private ShipperGatewayFacade shipperGatewayFacade;
	private DeliveryOrderCarrierResolver deliveryOrderCarrierResolver;

	private ShipperDeliveryService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		inOutBL = mock(IInOutBL.class);
		inOutDAO = mock(IInOutDAO.class);
		orgDAO = mock(IOrgDAO.class);
		warehouseDAO = mock(IWarehouseDAO.class);
		shipperTransportationDAO = mock(IShipperTransportationDAO.class);
		shipperDAO = mock(IShipperDAO.class);

		Services.registerService(IInOutBL.class, inOutBL);
		Services.registerService(IInOutDAO.class, inOutDAO);
		Services.registerService(IOrgDAO.class, orgDAO);
		Services.registerService(IWarehouseDAO.class, warehouseDAO);
		Services.registerService(IShipperTransportationDAO.class, shipperTransportationDAO);
		Services.registerService(IShipperDAO.class, shipperDAO);

		inOutToTransportationOrderService = mock(InOutToTransportationOrderService.class);
		shipperGatewayFacade = mock(ShipperGatewayFacade.class);
		deliveryOrderCarrierResolver = mock(DeliveryOrderCarrierResolver.class);

		service = new ShipperDeliveryService(
				inOutToTransportationOrderService,
				shipperGatewayFacade,
				deliveryOrderCarrierResolver);
	}

	/**
	 * AC-13d (negative): shipper with {@code IsCreateDeliveryPlanning=true}
	 * must NOT be added to the daily transport order.
	 * Verifies that {@link IShipperTransportationDAO#getOrCreate} is never called.
	 */
	@Test
	void givenShipperWithDeliveryPlanningFlagY_whenDailyTransportOrder_thenSkipped()
	{
		final ShipperId shipperId = setupShipment(true /* isCreateDeliveryPlanning */);

		service.createTransportationAndPackagesForShipment(
				InOutId.ofRepoId(1),
				true /* createOneTransportationOrderPerDay */);

		verify(shipperTransportationDAO, never()).getOrCreate(any());
		verify(shipperTransportationDAO, never()).create(any());
		verify(inOutToTransportationOrderService, never()).addShipmentsToTransportationOrder(any(), any());
	}

	/**
	 * AC-13d (positive): shipper with {@code IsCreateDeliveryPlanning=false}
	 * IS added to the daily transport order — {@link IShipperTransportationDAO#getOrCreate} is called.
	 */
	@Test
	void givenShipperWithDeliveryPlanningFlagN_whenDailyTransportOrder_thenAdded()
	{
		final ShipperId shipperId = setupShipment(false /* isCreateDeliveryPlanning */);

		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(99);
		when(shipperTransportationDAO.getOrCreate(any())).thenReturn(transportationId);
		when(inOutToTransportationOrderService.addShipmentsToTransportationOrder(any(), any()))
				.thenReturn(java.util.Collections.emptyList());

		service.createTransportationAndPackagesForShipment(
				InOutId.ofRepoId(1),
				true /* createOneTransportationOrderPerDay */);

		verify(shipperTransportationDAO).getOrCreate(any());
	}

	// ---- helpers ----

	/**
	 * Sets up a minimal {@link I_M_InOut} with the given shipper flag and stubs all necessary DAO calls.
	 */
	@NonNull
	private ShipperId setupShipment(final boolean isCreateDeliveryPlanning)
	{
		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
		shipper.setIsCreateDeliveryPlanning(isCreateDeliveryPlanning);
		// CreateShipperTransportationRequest.pickupTimeFrom is @NonNull — provide non-null timestamps
		shipper.setPickupTimeFrom(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0))));
		shipper.setPickupTimeTo(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0))));
		save(shipper);
		final ShipperId shipperId = ShipperId.ofRepoId(shipper.getM_Shipper_ID());

		final I_M_InOut shipment = mock(I_M_InOut.class);
		when(shipment.getM_Shipper_ID()).thenReturn(shipperId.getRepoId());
		when(shipment.getM_Warehouse_ID()).thenReturn(1);
		when(shipment.getAD_Org_ID()).thenReturn(0);

		when(inOutDAO.getById(InOutId.ofRepoId(1))).thenReturn(shipment);
		when(shipperDAO.getById(shipperId)).thenReturn(shipper);
		when(inOutBL.retrieveMovementDate(shipment)).thenReturn(LocalDate.now());

		// BPartnerLocationAndCaptureId is final — use real factory method instead of mock
		final BPartnerLocationAndCaptureId bpLoc = BPartnerLocationAndCaptureId.ofRepoId(1, 1);
		when(warehouseDAO.getWarehouseLocationById(any())).thenReturn(bpLoc);

		return shipperId;
	}
}
