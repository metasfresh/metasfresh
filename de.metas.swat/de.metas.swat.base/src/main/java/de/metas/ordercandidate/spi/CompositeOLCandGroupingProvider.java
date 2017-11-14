package de.metas.ordercandidate.spi;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import de.metas.ordercandidate.api.OLCand;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ToString
public class CompositeOLCandGroupingProvider implements IOLCandGroupingProvider
{
	private final CopyOnWriteArrayList<IOLCandGroupingProvider> providers = new CopyOnWriteArrayList<>();

	public void add(@NonNull final IOLCandGroupingProvider provider)
	{
		providers.addIfAbsent(provider);
	}

	@Override
	public List<Object> provideLineGroupingValues(final OLCand cand)
	{
		return providers.stream()
				.flatMap(provider -> provider.provideLineGroupingValues(cand).stream())
				.collect(Collectors.toList());
	}

}
