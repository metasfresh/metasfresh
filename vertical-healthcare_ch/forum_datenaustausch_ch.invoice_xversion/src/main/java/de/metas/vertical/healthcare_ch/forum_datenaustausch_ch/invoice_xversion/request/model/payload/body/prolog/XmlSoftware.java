package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.prolog;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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
public class XmlSoftware
{
	@NonNull
	String name;

	@Nullable
	String copyright;

	@Nullable
	String description;

	@Nullable
	Long version;

	@Nullable
	Long id;

	public XmlSoftware withMod(@Nullable final SoftwareMod softwareMod)
	{
		if (softwareMod == null)
		{
			return this;
		}

		final XmlSoftwareBuilder builder = toBuilder();

		if (softwareMod.getName() != null)
		{
			builder.name(softwareMod.getName());
		}
		if (softwareMod.getCopyright() != null)
		{
			builder.copyright(softwareMod.getCopyright());
		}
		if (softwareMod.getDescription() != null)
		{
			builder.description(softwareMod.getDescription());
		}
		if (softwareMod.getVersion() != null)
		{
			builder.version(softwareMod.getVersion());
		}
		if (softwareMod.getId() != null)
		{
			builder.id(softwareMod.getId());
		}
		return builder.build();
	}

	@Value
	@Builder
	public static class SoftwareMod
	{
		@Nullable
		String name;

		@Nullable
		String copyright;

		@Nullable
		String description;

		@Nullable
		Long version;

		@Nullable
		Long id;
	}
}
