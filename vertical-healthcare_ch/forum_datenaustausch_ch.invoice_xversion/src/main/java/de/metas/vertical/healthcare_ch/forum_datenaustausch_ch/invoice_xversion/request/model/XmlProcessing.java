package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model;

import java.util.List;

import javax.annotation.Nullable;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlDemand;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlValidation;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.TransportMod;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public class XmlProcessing
{
	/** expecting default = false */
	@NonNull
	Boolean printAtIntermediate;

	/** expecting default = false */
	@NonNull
	Boolean printPatientCopy;

	@NonNull
	XmlTransport transport;

	@Singular
	List<XmlValidation> validations;

	@Nullable
	XmlDemand demand;

	public XmlProcessing withMod(@Nullable final ProcessingMod processingMod)
	{
		if (processingMod == null)
		{
			return this;
		}

		final XmlProcessingBuilder builder = toBuilder();
		return builder
				.transport(transport.withMod(processingMod.getTransportMod()))
				.build();
	}

	@Value
	@Builder
	public static class ProcessingMod
	{
		@Nullable
		TransportMod transportMod;
	}
}
