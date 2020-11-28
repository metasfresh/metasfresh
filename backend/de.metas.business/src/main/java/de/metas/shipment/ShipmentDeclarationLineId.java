package de.metas.shipment;

import javax.annotation.Nullable;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
public class ShipmentDeclarationLineId implements RepoIdAware
{
	int repoId;

	@NonNull
	ShipmentDeclarationId shipmentDeclarationId;

	public static ShipmentDeclarationLineId ofRepoId(@NonNull final ShipmentDeclarationId shipmentDeclarationId, final int shipmentDeclarationLineId)
	{
		return new ShipmentDeclarationLineId(shipmentDeclarationId, shipmentDeclarationLineId);
	}

	public static ShipmentDeclarationLineId ofRepoId(final int shipmentDeclarationId, final int shipmentDeclarationLineId)
	{
		return new ShipmentDeclarationLineId(ShipmentDeclarationId.ofRepoId(shipmentDeclarationId), shipmentDeclarationLineId);
	}

	public static ShipmentDeclarationLineId ofRepoIdOrNull(
			@Nullable final ShipmentDeclarationId shipmentDeclarationId,
			final int shipmentDeclarationLineId)
	{
		return shipmentDeclarationId != null && shipmentDeclarationLineId > 0 ? ofRepoId(shipmentDeclarationId, shipmentDeclarationLineId) : null;
	}

	private ShipmentDeclarationLineId(@NonNull final ShipmentDeclarationId shipmentDeclarationId, final int shipmentDeclarationLineId)
	{
		this.repoId = Check.assumeGreaterThanZero(shipmentDeclarationLineId, "shipmentDeclarationLineId");
		this.shipmentDeclarationId = shipmentDeclarationId;
	}

}


