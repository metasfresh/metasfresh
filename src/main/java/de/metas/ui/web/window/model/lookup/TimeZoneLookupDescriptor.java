package de.metas.ui.web.window.model.lookup;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.descriptor.SimpleLookupDescriptorTemplate;
import de.metas.ui.web.window.model.lookup.LookupValueFilterPredicates.LookupValueFilterPredicate;
import de.metas.util.Check;

/*
 * #%L
 * metasfresh-webui-api
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

public final class TimeZoneLookupDescriptor extends SimpleLookupDescriptorTemplate
{
	public static final TimeZoneLookupDescriptor instance = new TimeZoneLookupDescriptor();
	public static final LookupDescriptorProvider provider = LookupDescriptorProviders.singleton(instance);

	private transient LookupValuesList all; // lazy

	private TimeZoneLookupDescriptor()
	{
	}

	private LookupValuesList getAll()
	{
		LookupValuesList all = this.all;
		if (all == null)
		{
			all = this.all = ZoneId.getAvailableZoneIds()
					.stream()
					.map(zoneId -> fromZoneIdToLookupValue(zoneId))
					.collect(LookupValuesList.collect());
		}
		return all;
	}

	private static StringLookupValue fromZoneIdToLookupValue(final String zoneIdStr)
	{
		final ZoneId zoneId = ZoneId.of(zoneIdStr);
		final ITranslatableString displayName = TranslatableStrings.builder()
				.appendTimeZone(zoneId, TextStyle.FULL_STANDALONE)
				.append(" - ")
				.append(zoneId.getId())
				.build();
		final ITranslatableString helpText = TranslatableStrings.empty();
		return StringLookupValue.of(zoneIdStr, displayName, helpText);
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return Optional.empty();
	}

	@Override
	public boolean isNumericKey()
	{
		return false;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return ImmutableSet.of();
	}

	@Override
	public LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		final String zoneIdStr = evalCtx.getIdToFilterAsString();
		if (Check.isEmpty(zoneIdStr, true))
		{
			return null;
		}

		return getAll().getById(zoneIdStr.trim());
	}

	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		final LookupValueFilterPredicate filter = evalCtx.getFilterPredicate();
		final int offset = evalCtx.getOffset(0);
		final int limit = evalCtx.getLimit(50);

		return getAll().filter(filter, offset, limit);
	}
}
