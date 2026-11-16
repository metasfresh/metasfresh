package de.metas.inoutcandidate.spi;

import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Pins that the stock-changed handler flags out of transaction even though it runs inside the (shared) event transaction:
 * the event is posted after the stock change committed, so there is no lost flag to close, and the shared event trx must not carry our insert.
 */
class ShipmentScheduleStockChangedEventHandlerTest
{
	private ITrxManager trxManager;
	private IShipmentScheduleInvalidateRepository invalidateRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		invalidateRepository = Mockito.mock(IShipmentScheduleInvalidateRepository.class);
		Services.registerService(IShipmentScheduleInvalidateRepository.class, invalidateRepository);
	}

	@Test
	void handleEvent_insideActiveThreadTrx_flagsOutOfTrx()
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		saveRecord(warehouse);

		final ShipmentScheduleInvalidateBL invalidateBL = new ShipmentScheduleInvalidateBL(new PickingBOMService());
		final ShipmentScheduleStockChangedEventHandler handler = new ShipmentScheduleStockChangedEventHandler(invalidateBL);

		final StockChangedEvent event = StockChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(Env.getClientId(), Env.getOrgId()))
				.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(555))
				.warehouseId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()))
				.qtyOnHand(BigDecimal.TEN)
				.qtyOnHandOld(BigDecimal.ONE)
				.build();

		// the event listener runs handlers inside runInNewTrx
		trxManager.runInNewTrx(() -> handler.handleEvent(event));

		verify(invalidateRepository, times(1)).invalidateStorageSegments(any(), isNull());
		verify(invalidateRepository, never()).invalidateStorageSegmentsInThreadInheritedTrx(any());
	}
}
