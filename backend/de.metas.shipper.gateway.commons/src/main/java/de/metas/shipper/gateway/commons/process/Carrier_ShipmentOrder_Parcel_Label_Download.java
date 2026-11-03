/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipper.gateway.commons.model.ShipmentOrderRepository;
import de.metas.shipper.gateway.spi.model.DeliveryOrderParcel;
import de.metas.shipper.gateway.spi.model.DeliveryOrderParcelId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.util.MimeType;
import org.springframework.core.io.ByteArrayResource;

import java.util.Objects;

public class Carrier_ShipmentOrder_Parcel_Label_Download extends JavaProcess implements IProcessPrecondition
{
	private final ShipmentOrderRepository orderRepository = SpringContextHolder.instance.getBean(ShipmentOrderRepository.class);

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
		if (!Objects.equals(context.getTableName(), I_Carrier_ShipmentOrder_Parcel.Table_Name))
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		final DeliveryOrderParcelId parcelId = DeliveryOrderParcelId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (parcelId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No parcel selected");
		}
		final DeliveryOrderParcel parcel = orderRepository.getParcelById(parcelId);
		if (parcel == null || parcel.getLabelPdfBase64() == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No label available");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final DeliveryOrderParcelId parcelId = DeliveryOrderParcelId.ofRepoIdOrNull(getRecord_ID());
		if (parcelId == null)
		{
			return MSG_OK;
		}
		final DeliveryOrderParcel parcel = orderRepository.getParcelById(parcelId);
		if (parcel == null || parcel.getLabelPdfBase64() == null)
		{
			return MSG_OK;
		}

		getResult().setReportData(new ByteArrayResource(parcel.getLabelPdfBase64()), "Awb_" + parcel.getAwb(), MimeType.TYPE_PDF);

		return MSG_OK;
	}

}