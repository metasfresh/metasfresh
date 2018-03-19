package de.metas.contracts.inoutcandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLineProvider;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
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
public class ShipmentScheduleSubscriptionReferenceProvider implements ShipmentScheduleReferencedLineProvider
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
	public ShipmentScheduleReferencedLine provideFor(@NonNull final I_M_ShipmentSchedule sched)
	{
		final I_C_SubscriptionProgress subscriptionLine = load(sched.getRecord_ID(), I_C_SubscriptionProgress.class);
		Check.errorIf(subscriptionLine == null,
				"Unable to load the referenced C_SubscriptionProgress for M_ShipmentSchedule_ID={}; M_ShipmentSchedule.Record_ID={}",
				sched.getM_ShipmentSchedule_ID(), sched.getRecord_ID());

		return ShipmentScheduleReferencedLine.builder()
				.groupId(subscriptionLine.getC_Flatrate_Term_ID())
				.shipperId(0)
				.deliveryDate(subscriptionLine.getEventDate())
				.preparationDate(subscriptionLine.getEventDate())
				.warehouseId(getWarehouseId(subscriptionLine))
				.documentLineDescriptor(createDocumentLineDescriptor(subscriptionLine))
				.build();
	}

	public int getWarehouseId(@NonNull final I_C_SubscriptionProgress subscriptionLine)
	{
		return Services.get(IFlatrateBL.class).getWarehouseId(subscriptionLine.getC_Flatrate_Term());
	}

	private SubscriptionLineDescriptor createDocumentLineDescriptor(
			@NonNull final I_C_SubscriptionProgress subscriptionLine)
	{
		return SubscriptionLineDescriptor.builder()
				.flatrateTermId(subscriptionLine.getC_Flatrate_Term_ID())
				.subscriptionProgressId(subscriptionLine.getC_SubscriptionProgress_ID())
				.subscriptionBillBPartnerId(subscriptionLine.getC_Flatrate_Term().getBill_BPartner_ID())
				.build();
	};
}
