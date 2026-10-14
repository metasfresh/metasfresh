package de.metas.inoutcandidate.api;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ModelWithoutShipmentScheduleVetoer;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.util.ISingletonService;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;

import java.util.Properties;
import java.util.Set;

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
	 * <p>
	 * Unlimited variant of {@link #createMissingCandidates(Properties, QueryLimit)}; delegates with {@link QueryLimit#NO_LIMIT}.
	 *
	 * @deprecated unbounded: processes the whole backlog in one go and can OOM on a large backlog. Use the bounded
	 * {@link #createMissingCandidates(Properties, QueryLimit)} overload instead.
	 */
	@Deprecated
	Set<ShipmentScheduleId> createMissingCandidates(Properties ctx);

	/**
	 * Invokes all registered {@link ShipmentScheduleHandler}s to create missing InOut candidates, processing at most
	 * {@code maxToProcess} models (created-or-vetoed, not schedules created) across all handlers combined.
	 *
	 * @param maxToProcess budget of models to process; use {@link QueryLimit#NO_LIMIT} for unlimited.
	 * @return the created shipment schedule ids, plus whether the budget was exhausted with more work remaining.
	 */
	CreateMissingCandidatesResult createMissingCandidates(Properties ctx, QueryLimit maxToProcess);

	/**
	 * Invokes the given <code>sched</code>'s {@link ShipmentScheduleHandler} to get a {@link IDeliverRequest} instance.
	 */
	IDeliverRequest createDeliverRequest(I_M_ShipmentSchedule sched, final I_C_OrderLine salesOrderLine);

	ShipmentScheduleHandler getHandlerFor(I_M_ShipmentSchedule sched);

	/** @return the handler for the given schedule, or {@code null} if none is registered for its table (e.g. a schedule that carries no handler context yet). */
	@Nullable
	ShipmentScheduleHandler getHandlerForOrNull(I_M_ShipmentSchedule sched);

	void updateShipmentScheduleFromReferencedRecord(I_M_ShipmentSchedule shipmentScheduleRecord);
}
