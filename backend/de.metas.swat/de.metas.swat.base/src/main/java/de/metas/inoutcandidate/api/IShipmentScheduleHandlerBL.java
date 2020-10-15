package de.metas.inoutcandidate.api;

import java.util.Properties;
import java.util.Set;

import de.metas.inoutcandidate.ShipmentScheduleId;
import org.compiere.model.I_C_OrderLine;

import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ModelWithoutShipmentScheduleVetoer;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.util.ISingletonService;

/**
 * This interface declares methods to
 * <ul>
 * <li>register SPI implementations in the framework</li>
 * <li>invoke the registered implementations</li>
 * </ul>
 *
 */
public interface IShipmentScheduleHandlerBL extends ISingletonService
{
	/**
	 * Registers a handler instance for the given table name. This method is intended to be called by various specific
	 * modules to register their SPI implementations.
	 * <p>
	 * <b>Important:</b> the implementation
	 * <ul>
	 * <li>assumes that there is <b>one</b> handler registered per table name</li>
	 * <li>makes sure that a {@link I_M_IolCandHandler} record is created for every registered handler</li>
	 * </ul>
	 *
	 * @param handler
	 *            the implementation to register. This method will call {@link ShipmentScheduleHandler#getSourceTable()} to
	 *            find out for which table the handler is registered.
	 */
	<T extends ShipmentScheduleHandler> void registerHandler(T handler);

	/**
	 * Registers a listener for the given table name. The listener is informed if a handler found a data record with a
	 * missing {@link I_M_ShipmentSchedule}. In that case the listener may veto the creation of the shipment schedule.
	 * <p>
	 * Note that
	 * <ul>
	 * <li>there can be zero, one or many listeners for each table name</li>
	 * <li>it is allowed to register a listener for a table name when no handler has (yet) been registered for the same table name</li>
	 * </ul>
	 */
	void registerVetoer(ModelWithoutShipmentScheduleVetoer vetoer, String tableName);

	/**
	 * Invokes all registered {@link ShipmentScheduleHandler}s to create missing InOut candidates.
	 */
	Set<ShipmentScheduleId> createMissingCandidates(Properties ctx);

	/**
	 * Invokes the given <code>sched</code>'s {@link ShipmentScheduleHandler} to get a {@link IDeliverRequest} instance.
	 *
	 * @param sched
	 * @return
	 */
	IDeliverRequest createDeliverRequest(I_M_ShipmentSchedule sched, final I_C_OrderLine salesOrderLine);

	ShipmentScheduleHandler getHandlerFor(I_M_ShipmentSchedule sched);

	void updateShipmentScheduleFromReferencedRecord(I_M_ShipmentSchedule shipmentScheduleRecord);
}
