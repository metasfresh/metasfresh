package de.metas.picking.api;

import de.metas.picking.qrcode.PickingSlotQRCode;
import org.adempiere.warehouse.WarehouseId;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


@Builder
@Value
public class PickingSlotQuery
{
	public static final PickingSlotQuery ALL = builder().build();

	/**
	 * {@code null} means "no restriction".
	 */
	BPartnerId availableForBPartnerId;

	/**
	 * {@code -1} means "no restriction".
	 */
	@Default
	BPartnerLocationId availableForBPartnerLocationId = null;
	
	BPartnerId assignedToBPartnerId;

	@Default
	BPartnerLocationId assignedToBPartnerLocationId = null;

	WarehouseId warehouseId;

	PickingSlotQRCode qrCode;
}