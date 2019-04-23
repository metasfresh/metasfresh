package de.metas.shipment.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.Adempiere;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.inout.InOutId;
import de.metas.shipment.service.ShipmentDeclarationCreator;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_M_InOut.class)
@Component("de.metas.shipment.model.interceptor.M_InOut")
public class M_InOut
{

	final ShipmentDeclarationCreator shipmentDeclarationCreator = Adempiere.getBean(ShipmentDeclarationCreator.class);

	@ModelChange(timings = { ModelValidator.TIMING_AFTER_COMPLETE})
	public void createShipmentDeclarationIfNeeded(final I_M_InOut inout)
	{
		if (!inout.isSOTrx())
		{
			// Only applies to shipments
			return;
		}

		final InOutId shipmentId = InOutId.ofRepoId(inout.getM_InOut_ID());

		shipmentDeclarationCreator.createShipmentDeclarationsIfNeeded(shipmentId);

	}

}
