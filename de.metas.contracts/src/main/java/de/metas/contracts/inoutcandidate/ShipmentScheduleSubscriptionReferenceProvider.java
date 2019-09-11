package de.metas.contracts.inoutcandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.time.ZonedDateTime;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLineProvider;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
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

@Component
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
		final int subscriptionProgressId = sched.getRecord_ID();
		final I_C_SubscriptionProgress subscriptionLine = load(subscriptionProgressId, I_C_SubscriptionProgress.class);
		Check.errorIf(subscriptionLine == null,
				"Unable to load the referenced C_SubscriptionProgress for M_ShipmentSchedule_ID={}; M_ShipmentSchedule.Record_ID={}",
				sched.getM_ShipmentSchedule_ID(), subscriptionProgressId);

		final ZonedDateTime eventDate = TimeUtil.asZonedDateTime(subscriptionLine.getEventDate());

		return ShipmentScheduleReferencedLine.builder()
				.recordRef(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, subscriptionLine.getC_Flatrate_Term_ID()))
				.shipperId(ShipperId.optionalOfRepoId(1))
				.deliveryDate(eventDate)
				.preparationDate(eventDate)
				.warehouseId(getWarehouseId(subscriptionLine))
				.documentLineDescriptor(createDocumentLineDescriptor(subscriptionLine))
				.build();
	}

	public WarehouseId getWarehouseId(@NonNull final I_C_SubscriptionProgress subscriptionLine)
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
	}
}
