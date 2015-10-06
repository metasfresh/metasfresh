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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * Implementations of this class are responsible for creating/updating shipment schedules from given <code>model</code>
 * 
 * <b>IMPORTANT:</b> this interface is a work in progress; currently there is only one implementation which only updates HU related schedules and which is not even called from the inout-candidate-BL,
 * but from a model-validator. The main work is still done in the implementation of
 * {@link IShipmentScheduleBL#updateSchedules(java.util.Properties, List, boolean, java.sql.Timestamp, org.adempiere.inout.util.CachedObjects, String)}.
 * 
 * @author cg
 * 
 */
public interface IShipmentScheduleProducer
{
	List<I_M_ShipmentSchedule> createOrUpdateShipmentSchedules(Object model, List<I_M_ShipmentSchedule> previousSchedules);

	void inactivateShipmentSchedules(Object model);
}
