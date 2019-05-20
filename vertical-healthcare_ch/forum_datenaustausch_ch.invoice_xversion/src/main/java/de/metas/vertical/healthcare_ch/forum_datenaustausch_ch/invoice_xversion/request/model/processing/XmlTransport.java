package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

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
public class XmlTransport
{
	@NonNull
	String from;

	@NonNull
	String to;

	@Singular
	List<XmlVia> vias;

	@Value
	@Builder
	public static class XmlVia
	{
		@NonNull
		String via;

		@NonNull
		Integer sequenceId;
	}

	public XmlTransport withMod(@Nullable final TransportMod transportMod)
	{
		if (transportMod == null)
		{
			return this;
		}

		final XmlTransportBuilder builder = toBuilder();
		if (transportMod.getFrom() != null)
		{
			builder.from(transportMod.getFrom());
		}

		final List<String> replacementViaEANs = transportMod.getReplacementViaEANs();
		if (replacementViaEANs != null && !replacementViaEANs.isEmpty())
		{
			builder.clearVias();
			int currentMaxSeqNo = 0;

			for (final String replacementViaEAN : replacementViaEANs)
			{
				currentMaxSeqNo += 1;
				final XmlVia xmlVia = XmlVia.builder()
						.via(replacementViaEAN)
						.sequenceId(currentMaxSeqNo)
						.build();
				builder.via(xmlVia);
			}
		}

		final List<String> additionalViaEANs = transportMod.getAdditionalViaEANs();
		if (additionalViaEANs != null)
		{
			int currentMaxSeqNo = getVias()
					.stream()
					.map(XmlVia::getSequenceId) // is never null
					.max(Comparator.naturalOrder())
					.orElse(0);

			for (final String additionalViaEAN : additionalViaEANs)
			{
				currentMaxSeqNo += 1;
				final XmlVia xmlVia = XmlVia.builder()
						.via(additionalViaEAN)
						.sequenceId(currentMaxSeqNo)
						.build();
				builder.via(xmlVia);
			}
		}

		return builder
				.build();
	}

	@Value
	@Builder
	public static class TransportMod
	{
		@Nullable
		String from;

		/** {@code null} or an empty list both mean "don't replace" */
		@Singular
		List<String> replacementViaEANs;

		@Singular
		List<String> additionalViaEANs;
	}
}
