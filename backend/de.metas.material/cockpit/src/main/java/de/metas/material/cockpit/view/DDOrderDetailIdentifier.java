/*
 * #%L
 * metasfresh-material-cockpit
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

package de.metas.material.cockpit.view;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

@Value
public class DDOrderDetailIdentifier
{
	int ddOrderLineId;
	WarehouseId warehouseId;

	public static DDOrderDetailIdentifier of(@NonNull final WarehouseId warehouseId, final int ddOrderLineId)
	{
		return new DDOrderDetailIdentifier(warehouseId, ddOrderLineId);
	}

	private DDOrderDetailIdentifier(@NonNull final WarehouseId warehouseId, final int ddOrderLineId)
	{
		Check.assumeGreaterThanZero(ddOrderLineId, "ddOrderLineId");

		this.ddOrderLineId = ddOrderLineId;
		this.warehouseId = warehouseId;
	}
}
