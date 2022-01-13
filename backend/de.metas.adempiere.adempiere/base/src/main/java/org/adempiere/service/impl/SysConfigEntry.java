/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.adempiere.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@ToString
class SysConfigEntry
{
	@Getter
	private final String name;
	private final ImmutableMap<ClientAndOrgId, SysConfigEntryValue> entryValues;

	@Builder
	public SysConfigEntry(
			@NonNull final String name,
			@NonNull @Singular final List<SysConfigEntryValue> entryValues)
	{
		this.name = name;
		this.entryValues = Maps.uniqueIndex(entryValues, SysConfigEntryValue::getClientAndOrgId);
	}

	public boolean isNameStartsWith(@NonNull final String prefix)
	{
		if (Check.isBlank(prefix))
		{
			throw new AdempiereException("Blank prefix is not allowed");
		}

		return name.startsWith(prefix);
	}

	public boolean isClientAndOrgMatching(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		for (ClientAndOrgId currentClientAndOrgId = clientAndOrgId; currentClientAndOrgId != null; currentClientAndOrgId = getFallbackClientAndOrgId(currentClientAndOrgId))
		{
			if (entryValues.containsKey(currentClientAndOrgId))
			{
				return true;
			}
		}

		return false;
	}

	@Nullable
	private ClientAndOrgId getFallbackClientAndOrgId(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		if (!clientAndOrgId.getOrgId().isAny())
		{
			return clientAndOrgId.withAnyOrgId();
		}
		else if (!clientAndOrgId.getClientId().isSystem())
		{
			return clientAndOrgId.withSystemClientId();
		}
		else
		{
			return null;
		}
	}

	public Optional<String> getValueAsString(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		for (ClientAndOrgId currentClientAndOrgId = clientAndOrgId; currentClientAndOrgId != null; currentClientAndOrgId = getFallbackClientAndOrgId(currentClientAndOrgId))
		{
			final SysConfigEntryValue entryValue = entryValues.get(currentClientAndOrgId);
			if (entryValue != null)
			{
				return Optional.of(entryValue.getValue());
			}
		}

		return Optional.empty();
	}

	//
	//
	// -------------------------------
	//
	//

	public static class SysConfigEntryBuilder
	{
		public String getName()
		{
			return name;
		}
	}
}
