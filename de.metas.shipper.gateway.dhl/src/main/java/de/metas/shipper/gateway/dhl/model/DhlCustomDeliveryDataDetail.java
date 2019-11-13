/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl.model;

import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class DhlCustomDeliveryDataDetail
{
	int packageId;

	@Nullable
	DhlSequenceNumber sequenceNumber;

	@Nullable
	byte[] pdfLabelData;

	@Nullable
	String awb;

	@Nullable
	String trackingUrl;

	boolean internationalDelivery;

	//	@Nullable
	//	DhlCustomsDocument customsDocument;

	@Builder(toBuilder = true)
	private DhlCustomDeliveryDataDetail(
			final int packageId,
			@Nullable final DhlSequenceNumber sequenceNumber,
			@Nullable final byte[] pdfLabelData,
			@Nullable final String awb,
			@Nullable final String trackingUrl,
			final boolean internationalDelivery
			//			@Nullable final DhlCustomsDocument customsDocument
	)
	{
		Check.assumeGreaterThanZero(packageId, "packageId");

		this.packageId = packageId;
		this.sequenceNumber = sequenceNumber;
		this.pdfLabelData = pdfLabelData;
		this.awb = awb;
		this.trackingUrl = trackingUrl;
		this.internationalDelivery = internationalDelivery;
//		if (internationalDelivery && customsDocument == null)
//		{
//			throw new AdempiereException("International delivery must have a valid Customs Document");
//		}
//		this.customsDocument = customsDocument;
	}

}

