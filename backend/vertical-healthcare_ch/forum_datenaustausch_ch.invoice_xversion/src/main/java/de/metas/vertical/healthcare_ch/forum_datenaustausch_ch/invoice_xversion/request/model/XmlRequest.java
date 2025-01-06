package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model;

import javax.annotation.Nullable;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlMode;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlPayload.PayloadMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing.ProcessingMod;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
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
@Builder(toBuilder = true)
public class XmlRequest
{
	@NonNull
	String language;

	/** expecting default = production */
	@NonNull
	XmlMode modus;

	@Nullable
	Long validationStatus;

	@NonNull
	XmlProcessing processing;

	@NonNull
	XmlPayload payload;

	public XmlRequest withMod(@Nullable final RequestMod requestMod)
	{
		if (requestMod == null)
		{
			return this;
		}

		final XmlRequestBuilder builder = toBuilder();
		if (requestMod.getModus() != null)
		{
			builder.modus(requestMod.getModus());
		}

		return builder
				.processing(processing.withMod(requestMod.getProcessingMod()))
				.payload(payload.withMod(requestMod.getPayloadMod()))
				.build();
	}

	@Value
	@Builder
	public static class RequestMod
	{
		@Nullable
		XmlMode modus;

		@Nullable
		PayloadMod payloadMod;

		@Nullable
		ProcessingMod processingMod;
	}
}
