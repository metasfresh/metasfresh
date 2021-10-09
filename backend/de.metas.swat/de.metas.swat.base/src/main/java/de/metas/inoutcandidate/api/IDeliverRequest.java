package de.metas.inoutcandidate.api;

import java.math.BigDecimal;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import org.compiere.model.I_C_OrderLine;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Instances of this class are provided by {@link ShipmentScheduleHandler#createDeliverRequest(I_M_ShipmentSchedule, I_C_OrderLine)}, 
 * but the actual implementation is in the implementers of {@link ShipmentScheduleHandler}.
 */
public interface IDeliverRequest
{
	/**
	 * The qty that still needs to be delivered, in the product's UOM.
	 */
	BigDecimal getQtyOrdered();
}
