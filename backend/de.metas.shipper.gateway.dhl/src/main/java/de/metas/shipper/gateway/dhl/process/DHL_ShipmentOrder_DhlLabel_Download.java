/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.shipper.gateway.dhl.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlShipmentOrderId;
import de.metas.shipper.gateway.dhl.model.IDhlShipmentOrderDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.MimeType;
import org.springframework.core.io.ByteArrayResource;

public class DHL_ShipmentOrder_DhlLabel_Download extends JavaProcess implements IProcessPrecondition
{
	private final transient IDhlShipmentOrderDAO dhlShipmentOrderDAO = Services.get(IDhlShipmentOrderDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		final DhlShipmentOrderId shipmentOrderId = DhlShipmentOrderId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (shipmentOrderId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No shipment order selected");
		}
		final DhlCustomDeliveryDataDetail dhlShipmentOrder = dhlShipmentOrderDAO.getByIdOrNull(shipmentOrderId);
		if (dhlShipmentOrder == null || dhlShipmentOrder.getPdfLabelData() == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No label available");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final DhlShipmentOrderId shipmentOrderId = DhlShipmentOrderId.ofRepoIdOrNull(getRecord_ID());
		if (shipmentOrderId == null)
		{
			return MSG_OK;
		}
		final DhlCustomDeliveryDataDetail dhlShipmentOrder = dhlShipmentOrderDAO.getByIdOrNull(shipmentOrderId);
		if (dhlShipmentOrder == null || dhlShipmentOrder.getPdfLabelData() == null)
		{
			return MSG_OK;
		}

		getResult().setReportData(new ByteArrayResource(dhlShipmentOrder.getPdfLabelData()), "Dhl_Awb_" + dhlShipmentOrder.getAwb(), MimeType.TYPE_PDF);

		return MSG_OK;
	}

}