package de.metas.contracts.subscription.inoutcandidate.spi.impl;

import java.util.function.Function;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleOrderDoc;
import de.metas.inoutcandidate.spi.impl.ShipmentScheduleOrderDocForOrderLine;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ShipmentScheduleOrderDocForSubscriptionLine implements Function<I_M_ShipmentSchedule, ShipmentScheduleOrderDoc>
{

	public static final ShipmentScheduleOrderDocForSubscriptionLine INSTANCE = new ShipmentScheduleOrderDocForSubscriptionLine();

	private ShipmentScheduleOrderDocForSubscriptionLine()
	{
	}

	@Override
	public ShipmentScheduleOrderDoc apply(I_M_ShipmentSchedule schipmentSchedule)
	{
		// TODO Auto-generated method stub
		return null;
	};


}
