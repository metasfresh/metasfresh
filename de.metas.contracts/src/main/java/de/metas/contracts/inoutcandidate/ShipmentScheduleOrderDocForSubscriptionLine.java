package de.metas.contracts.inoutcandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import de.metas.contracts.flatrate.api.IFlatrateBL;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleOrderDoc;
import de.metas.inoutcandidate.spi.ShipmentScheduleOrderDocProvider;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class ShipmentScheduleOrderDocForSubscriptionLine implements ShipmentScheduleOrderDocProvider
{
	/**
	 * @return {@link I_C_SubscriptionProgress#Table_Name}.
	 */
	@Override
	public String getTableName()
	{
		return I_C_SubscriptionProgress.Table_Name;
	}

	@Override
	public ShipmentScheduleOrderDoc provideFor(@NonNull final I_M_ShipmentSchedule sched)
	{
		final I_C_SubscriptionProgress subscriptionLine = load(sched.getRecord_ID(), I_C_SubscriptionProgress.class);

		return ShipmentScheduleOrderDoc.builder()
				.groupId(subscriptionLine.getC_Flatrate_Term_ID())
				.shipperId(0)
				.deliveryDate(subscriptionLine.getEventDate())
				.preparationDate(subscriptionLine.getEventDate())
				.warehouseId(getWarehouseId(subscriptionLine))
				.build();
	};

	public int getWarehouseId(@NonNull final I_C_SubscriptionProgress subscriptionLine)
	{
		return Services.get(IFlatrateBL.class).getWarehouseId(subscriptionLine.getC_Flatrate_Term());
	}
}
