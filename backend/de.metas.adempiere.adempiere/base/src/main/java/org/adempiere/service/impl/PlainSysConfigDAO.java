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

import com.google.common.collect.ImmutableListMultimap;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.GuavaCollectors;
import org.adempiere.ad.wrapper.POJOLookupMap;
import lombok.NonNull;
import org.compiere.model.I_AD_SysConfig;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PlainSysConfigDAO extends SysConfigDAO
{
	@Override
	protected ImmutableListMultimap<String, SysConfigEntryValue> retrieveSysConfigEntryValues()
	{
		return POJOLookupMap.get().getRecords(I_AD_SysConfig.class)
				.stream()
				.map(PlainSysConfigDAO::toSysConfigEntryValue)
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableListMultimap());
	}

	/** Mirrors the SQL of {@link SysConfigDAO#getConfigurationLevel(String)} on the in-memory records, so unit tests need no DB connection. */
	@Override
	public Optional<String> getConfigurationLevel(@NonNull final String name)
	{
		return POJOLookupMap.get().getRecords(I_AD_SysConfig.class)
				.stream()
				.filter(record -> name.equals(record.getName()) && record.getAD_Client_ID() == 0 && record.getAD_Org_ID() == 0)
				.map(I_AD_SysConfig::getConfigurationLevel)
				.filter(Objects::nonNull)
				.findFirst();
	}

	private static Map.Entry<String, SysConfigEntryValue> toSysConfigEntryValue(final I_AD_SysConfig record)
	{
		if (!record.isActive())
		{
			return null;
		}

		return GuavaCollectors.entry(
				record.getName(),
				SysConfigEntryValue.of(
						record.getValue(),
						ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID())));
	}
}