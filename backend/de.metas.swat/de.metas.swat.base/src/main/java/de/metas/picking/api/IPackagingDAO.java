/*
 * #%L
 * de.metas.swat.base
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

package de.metas.picking.api;

import de.metas.inout.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Packaging related DAO
 *
 * @author ts
 * @author tsa
 *
 * @see <a href="http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29">(2009_0022_G61)</a>
 */
public interface IPackagingDAO extends ISingletonService
{
	Stream<Packageable> stream(PackageableQuery query);

	/**
	 * @return The current QtyPickedPlanned (qty that was picked but not yet processed) for the given schedule if found
	 */
	Optional<Quantity> retrieveQtyPickedPlanned(ShipmentScheduleId shipmentScheduleId);

	Packageable getByShipmentScheduleId(ShipmentScheduleId shipmentScheduleId);

	List<Packageable> getByShipmentScheduleIds(Collection<ShipmentScheduleId> shipmentScheduleIds);
}
