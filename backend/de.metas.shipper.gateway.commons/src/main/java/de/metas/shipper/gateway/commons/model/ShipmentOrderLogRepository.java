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

package de.metas.shipper.gateway.commons.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Carrier_ShipmentOrder_Log;
import org.springframework.stereotype.Repository;

@Repository
public class ShipmentOrderLogRepository
{
	public void save(@NonNull final ShipmentOrderLogCreateRequest logRequest)
	{
		final I_Carrier_ShipmentOrder_Log log = InterfaceWrapperHelper.newInstance(I_Carrier_ShipmentOrder_Log.class);
		final JsonDeliveryRequest request = logRequest.getRequest();
		final JsonDeliveryResponse response = logRequest.getResponse();
		log.setCarrier_ShipmentOrder_ID(request.getDeliveryOrderId());
		log.setRequestMessage(toJson(request));
		log.setResponseMessage(toJson(response.withoutPDFContents()));//Don't save the PDF contents
		log.setDurationMillis((int)logRequest.getDurationMillis());
		log.setIsError(response.isError());
		log.setRequestID(request.getId());
		InterfaceWrapperHelper.saveRecord(log);
	}

	@NonNull
	private String toJson(@NonNull final Object object)
	{
		if (object instanceof String)
		{
			return (String)object;
		}
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(object);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed to parse object!", e);
		}
	}

}
