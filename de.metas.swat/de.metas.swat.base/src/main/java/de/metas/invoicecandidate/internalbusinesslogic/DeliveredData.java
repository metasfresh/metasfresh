package de.metas.invoicecandidate.internalbusinesslogic;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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

@Value
public class DeliveredData
{
	ShipmentData shipmentData;

	ReceiptData receiptData;

	@Builder
	@JsonCreator
	private DeliveredData(
			@JsonProperty("shipmentData") @Nullable final ShipmentData shipmentData,
			@JsonProperty("receiptData") @Nullable final ReceiptData receiptData)
	{
		this.shipmentData = shipmentData;
		this.receiptData = receiptData;

		if (shipmentData != null ^ /* XOR */ receiptData != null)
		{
			return; // OK
		}

		throw new AdempiereException("Exactly one of either shippedData or receiptQualityData needs to be not-null")
				.appendParametersToMessage()
				.setParameter("shippedData", shipmentData)
				.setParameter("receiptData", receiptData);
	}

}
