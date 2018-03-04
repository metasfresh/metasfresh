package de.metas.ui.web.pickingslotsClearing.process;

import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Loggables;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.HUShippingFacade;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromHU.BillAssociatedInvoiceCandidates;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_PackingHUsView_AddHUsToShipperTransportationShipAndInvoice extends PackingHUsViewBasedProcess
{
	@Param(parameterName = I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, mandatory = true)
	private int shipperTransportationId;

	@Override
	protected final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final boolean eligibleHUsFound = streamEligibleHURows()
				.findAny()
				.isPresent();
		if (!eligibleHUsFound)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final String doIt() throws Exception
	{
		Check.assume(shipperTransportationId > 0, "shipperTransportationId > 0");

		final List<I_M_HU> hus = retrieveEligibleHUs();

		HUShippingFacade.builder()
				.loggable(Loggables.get())
				.hus(hus)
				.addToShipperTransportationId(shipperTransportationId)
				.completeShipments(true)
				.invoiceMode(BillAssociatedInvoiceCandidates.IF_INVOICE_SCHEDULE_PERMITS)
				.createShipperDeliveryOrders(true)
				.build()
				.generateShippingDocuments();

		return MSG_OK;
	}

	@Override
	protected final void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		getView().invalidateAll();
	}

}
