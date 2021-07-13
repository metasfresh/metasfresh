package de.metas.inoutcandidate.api;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.util.ISingletonService;

/**
 * Implementors update invalid {@link I_M_ShipmentSchedule} instance and make them valid again.
 *
 * @author ts
 *
 */
public interface IShipmentScheduleUpdater extends ISingletonService
{
	void registerCandidateProcessor(IShipmentSchedulesAfterFirstPassUpdater processor);

	/**
	 * @return the number of updated schedule entries.
	 */
	int updateShipmentSchedules(ShipmentScheduleUpdateInvalidRequest request);

	/**
	 * @return true if updater is currently running in this thread
	 */
	boolean isRunning();
}
