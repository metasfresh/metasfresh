package de.metas.tourplanning.model.validator;

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


import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.Services;

import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.impl.ShipmentScheduleDeliveryDayHandler;
import de.metas.tourplanning.model.I_M_ShipmentSchedule;

@Interceptor(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	@Init
	public void init()
	{
		Services.get(IDeliveryDayBL.class).registerDeliveryDayHandler(ShipmentScheduleDeliveryDayHandler.INSTANCE);
	}

}
