package de.metas.handlingunits.inventory.draftlinescreator;

import java.util.Collection;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class PlainHUsForInventoryStrategy implements HUsForInventoryStrategy
{
	public static PlainHUsForInventoryStrategy of(@NonNull final Collection<HuForInventoryLine> hus)
	{
		return new PlainHUsForInventoryStrategy(hus);
	}

	public static PlainHUsForInventoryStrategy of(@NonNull final HuForInventoryLine hu)
	{
		return new PlainHUsForInventoryStrategy(ImmutableList.of(hu));
	}

	private final ImmutableList<HuForInventoryLine> hus;

	private PlainHUsForInventoryStrategy(@NonNull final Collection<HuForInventoryLine> hus)
	{
		Check.assumeNotEmpty(hus, "hus is not empty");
		this.hus = ImmutableList.copyOf(hus);
	}

	@Override
	public Stream<HuForInventoryLine> streamHus()
	{
		return hus.stream();
	}

}
