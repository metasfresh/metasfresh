package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;

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

@Service
public class CrossVersionServiceRegistry
{
	private final ImmutableMap<String, CrossVersionRequestConverter<?>> typeName2converter;

	public CrossVersionServiceRegistry(@NonNull final Optional<List<CrossVersionRequestConverter<?>>> crossVersionRequestConverters)
	{
		final List<CrossVersionRequestConverter<?>> //
		converterList = crossVersionRequestConverters.orElse(ImmutableList.of());

		this.typeName2converter = Maps.uniqueIndex(converterList, CrossVersionRequestConverter::getSupportedType);
	}

	public CrossVersionRequestConverter<?> getConverter(@NonNull final String type)
	{
		return typeName2converter.get(type);
	}
}
