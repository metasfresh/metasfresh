package de.metas.handlingunits.inventory.draftlinescreator;

import java.util.Collection;

import de.metas.handlingunits.inventory.draftlinescreator.LeastRecentTransactionStrategy.LeastRecentTransactionStrategyBuilder;
import de.metas.handlingunits.inventory.draftlinescreator.LocatorAndProductStrategy.LocatorAndProductStrategyBuilder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class HUsForInventoryStrategies
{
	public static LocatorAndProductStrategyBuilder locatorAndProduct()
	{
		return LocatorAndProductStrategy.builder();
	}

	public static LeastRecentTransactionStrategyBuilder leastRecentTransaction()
	{
		return LeastRecentTransactionStrategy.builder();
	}

	public static PlainHUsForInventoryStrategy of(@NonNull final Collection<HuForInventoryLine> hus)
	{
		return PlainHUsForInventoryStrategy.of(hus);
	}

	public static PlainHUsForInventoryStrategy of(@NonNull final HuForInventoryLine hu)
	{
		return PlainHUsForInventoryStrategy.of(hu);
	}
}
