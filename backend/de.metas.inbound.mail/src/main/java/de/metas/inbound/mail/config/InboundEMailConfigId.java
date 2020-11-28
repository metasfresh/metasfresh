package de.metas.inbound.mail.config;

import java.util.UUID;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.inbound.mail
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

@EqualsAndHashCode
@ToString
public final class InboundEMailConfigId
{
	public static final InboundEMailConfigId ofRepoId(final int repoId)
	{
		Check.assumeGreaterThanZero(repoId, "repoId");
		return new InboundEMailConfigId(String.valueOf(repoId));
	}

	public static final InboundEMailConfigId random()
	{
		return new InboundEMailConfigId(UUID.randomUUID().toString());
	}

	private final String id;

	private InboundEMailConfigId(@NonNull final String id)
	{
		this.id = id;
	}

	public String getAsString()
	{
		return id;
	}

}
