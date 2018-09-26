package de.metas.picking.service;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Helper class used to manage {@link IPackingItem} instances.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public final class PackingItems
{
	/**
	 * @return a new {@link TransactionalPackingItem} from the given map.
	 */
	public static IPackingItem newPackingItem(@NonNull final ShipmentScheduleQtyPickedMap scheds2Qtys)
	{
		return new TransactionalPackingItem(scheds2Qtys);
	}
}
