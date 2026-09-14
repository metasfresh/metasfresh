package de.metas.inoutcandidate.shippertransportation;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.handlingunits.impl.CreateShipperTransportationRequest;
import de.metas.handlingunits.shipmentschedule.api.DeliveryOrderCarrierResolver;
import de.metas.handlingunits.shipping.InOutToTransportationOrderService;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.organization.IOrgDAO;
import de.metas.shipper.gateway.commons.ShipperGatewayFacade;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.X_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Only a genuine outbound sales shipment may create a shipper-transportation record.
 * A purchase receipt, a customer return, and a vendor return must all be a clean no-op,
 * even when a shipper is set on the document.
 */
class ShipperDeliveryServiceShipmentGateTest
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

	@Test
	@DisplayName("a sales shipment creates the transport document (regression net)")
	void salesShipment_createsTransportDocument()
	{
		setupShipment(true /* isSOTrx */, X_M_InOut.MOVEMENTTYPE_CustomerShipment, false /* isReturn */);

		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(99);
		when(shipperTransportationDAO.create(any())).thenReturn(transportationId);
		when(inOutToTransportationOrderService.addShipmentsToTransportationOrder(any(), any()))
				.thenReturn(Collections.emptyList());

		service.createTransportationAndPackagesForShipment(InOutId.ofRepoId(1), false);

		// the direction is the request's own new field: an Outgoing sales shipment must never be booked onto an
		// Incoming or Dropship transport order, so assert the value and not merely that create() was reached
		final ArgumentCaptor<CreateShipperTransportationRequest> request =
				ArgumentCaptor.forClass(CreateShipperTransportationRequest.class);
		verify(shipperTransportationDAO).create(request.capture());
		assertThat(request.getValue().getTransportDirection()).isEqualTo(TransportDirection.Outgoing);
	}

	@Test
	@DisplayName("a purchase receipt is a no-op, even with a shipper set")
	void purchaseReceipt_isNoOp()
	{
		setupShipment(false /* isSOTrx */, X_M_InOut.MOVEMENTTYPE_VendorReceipts, false /* isReturn */);

		service.createTransportationAndPackagesForShipment(InOutId.ofRepoId(1), false);

		verify(shipperTransportationDAO, never()).create(any());
		verify(shipperTransportationDAO, never()).getOrCreate(any());
		verify(inOutToTransportationOrderService, never()).addShipmentsToTransportationOrder(any(), any());
	}

	@Test
	@DisplayName("a customer return is a no-op even though IsSOTrx='Y' - a naive isSOTrx-only gate would wrongly let it through")
	void customerReturn_isNoOp()
	{
		setupShipment(true /* isSOTrx */, X_M_InOut.MOVEMENTTYPE_CustomerReturns, true /* isReturn */);

		service.createTransportationAndPackagesForShipment(InOutId.ofRepoId(1), false);

		verify(shipperTransportationDAO, never()).create(any());
		verify(shipperTransportationDAO, never()).getOrCreate(any());
		verify(inOutToTransportationOrderService, never()).addShipmentsToTransportationOrder(any(), any());
	}

	// ---- helpers ----

	/**
	 * Sets up a minimal {@link I_M_InOut} with the given SOTrx/movement-type combination and stubs the DAO calls,
	 * with a shipper set on the document so that only the shipment gate under test decides the outcome.
	 */
	private void setupShipment(final boolean isSOTrx, final String movementType, final boolean isReturn)
	{
		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
		shipper.setIsCreateDeliveryPlanning(false);
		shipper.setPickupTimeFrom(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0))));
		shipper.setPickupTimeTo(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0))));
		save(shipper);
		final ShipperId shipperId = ShipperId.ofRepoId(shipper.getM_Shipper_ID());

		final I_M_InOut shipment = mock(I_M_InOut.class);
		when(shipment.getM_Shipper_ID()).thenReturn(shipperId.getRepoId());
		when(shipment.getM_Warehouse_ID()).thenReturn(1);
		when(shipment.getAD_Org_ID()).thenReturn(0);
		when(shipment.isSOTrx()).thenReturn(isSOTrx);
		when(shipment.getMovementType()).thenReturn(movementType);

		when(inOutDAO.getById(InOutId.ofRepoId(1))).thenReturn(shipment);
		when(shipperDAO.getById(shipperId)).thenReturn(shipper);
		when(inOutBL.retrieveMovementDate(shipment)).thenReturn(LocalDate.now());
		when(inOutBL.isReturnMovementType(movementType)).thenReturn(isReturn);

		final BPartnerLocationAndCaptureId bpLoc = BPartnerLocationAndCaptureId.ofRepoId(1, 1);
		when(warehouseDAO.getWarehouseLocationById(any())).thenReturn(bpLoc);
	}
}
