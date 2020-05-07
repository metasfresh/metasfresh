package de.metas.inoutcandidate.spi;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * Implementors of this interface can be registered using
 * {@link IShipmentScheduleHandlerBL#registerListener(ModelWithoutInOutCandidateListener)}. They are notified if a given
 * {@link ShipmentScheduleHandler} wants to create a {@link I_M_ShipmentSchedule} for a given data record and they can veto
 * that creation.
 *
 * A use case is that a subscription module registers a listener to veto the immediate creation of shipment schedules
 * for subscription order lines. The reason for that would be that the module wants to create those records itself at
 * the actual subscription delivery dates.
 *
 * @author ts
 *
 */
@FunctionalInterface
public interface ModelWithoutShipmentScheduleVetoer
{
	public enum OnMissingCandidate
	{
		I_VETO,

		I_DONT_CARE
	}

	/**
	 * This method is called when a handler found a data record (e.g. a C_OrderLine) for which would like to create a
	 * {@link I_M_ShipmentSchedule}.
	 *
	 * @param model
	 *            the record that the given
	 *            <code>handler</code> identified as "lacking a shipment schedule". The implementor can assume that this model can be accessed using {@link InterfaceWrapperHelper}.
	 * @param handler
	 *            the handler that found the model and would also create the shipment schedule(s)
	 * @return {@link OnMissingCandidate#I_VETO} if there are reasons that no <code>I_M_ShipmentSchedule</code>
	 *         should be created. Each implementation can assume that no <code>I_M_ShipmentSchedule</code> is create if
	 *         at least one
	 */
	public OnMissingCandidate foundModelWithoutInOutCandidate(Object model);
}
