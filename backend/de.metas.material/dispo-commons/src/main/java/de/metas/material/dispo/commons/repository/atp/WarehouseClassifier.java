package de.metas.material.dispo.commons.repository.atp;

import javax.annotation.Nullable;

import org.adempiere.warehouse.WarehouseId;

import com.google.common.base.MoreObjects;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

@EqualsAndHashCode(doNotUseGetters = true)
public final class WarehouseClassifier
{
	public static WarehouseClassifier any()
	{
		return ANY;
	}

	public static WarehouseClassifier specific(@NonNull final WarehouseId warehouseId)
	{
		return new WarehouseClassifier(warehouseId);
	}

	public static WarehouseClassifier specificOrAny(@Nullable final WarehouseId warehouseId)
	{
		return warehouseId != null ? specific(warehouseId) : any();
	}

	private static final WarehouseClassifier ANY = new WarehouseClassifier(null);

	private WarehouseId warehouseId;

	private WarehouseClassifier(@Nullable final WarehouseId warehouseId)
	{
		this.warehouseId = warehouseId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(warehouseId != null ? warehouseId : "ANY")
				.toString();
	}

	public boolean isMatching(@Nullable final WarehouseId warehouseId)
	{
		return this.warehouseId == null
				|| WarehouseId.equals(this.warehouseId, warehouseId);
	}
}
