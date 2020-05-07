package de.metas.rfq;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.base.MoreObjects;

import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.rfq
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class RfQResponsePublisherRequest
{
	public static final RfQResponsePublisherRequest of(final I_C_RfQResponse rfqResponse, final PublishingType publishingType)
	{
		return new RfQResponsePublisherRequest(rfqResponse, publishingType);
	}

	public static enum PublishingType
	{
		Invitation, Close,
	};

	private final I_C_RfQResponse rfqResponse;
	private final PublishingType publishingType;

	private RfQResponsePublisherRequest(final I_C_RfQResponse rfqResponse, final PublishingType publishingType)
	{
		super();

		Check.assumeNotNull(rfqResponse, "rfqResponse not null");
		this.rfqResponse = rfqResponse;

		Check.assumeNotNull(publishingType, "publishingType not null");
		this.publishingType = publishingType;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("rfqResponse", rfqResponse)
				.add("publishingType", publishingType)
				.toString();
	}

	public String getSummary()
	{
		return Services.get(IRfqBL.class).getSummary(rfqResponse)
				+ "/" + publishingType;
	}

	public I_C_RfQResponse getC_RfQResponse()
	{
		return rfqResponse;
	}

	public PublishingType getPublishingType()
	{
		return publishingType;
	}

}
