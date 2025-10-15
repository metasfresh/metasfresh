package de.metas.shipping.model.validator;

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

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.order.paymentschedule.OrderPayScheduleService;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

@Validator(I_M_ShipperTransportation.class)
@RequiredArgsConstructor
public class M_ShipperTransportation
{
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final OrderPayScheduleService orderPayScheduleService;

	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.shipping.callout.M_ShipperTransportation());

		CopyRecordFactory.enableForTableName(I_M_ShipperTransportation.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_M_ShipperTransportation.COLUMNNAME_ETA, I_M_ShipperTransportation.COLUMNNAME_BLDate })
	public void updateOrderPaySchedules(final I_M_ShipperTransportation transportOrder)
	{
		shipperTransportationDAO.retrieveOrderIds(ShipperTransportationId.ofRepoId(transportOrder.getM_ShipperTransportation_ID()))
				.forEach(orderPayScheduleService::updatePayScheduleStatus);
	}
}
