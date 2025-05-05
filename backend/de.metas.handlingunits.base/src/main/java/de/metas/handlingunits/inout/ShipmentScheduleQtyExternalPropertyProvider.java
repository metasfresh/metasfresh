/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.inout;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.material.interceptor.transactionevent.HUDescriptorService;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.inout.util.IShipmentScheduleQtyOnHandProvider;
import org.adempiere.inout.util.IShipmentScheduleQtyOnHandStorage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This implementation should provide details about external property quantities reserved for Order lines.
 * Main driver of this is because products in the service/repair are considered external property and should no longer count against stock.
 * But they still need to be shipped back to the client.
 */
@Service
public class ShipmentScheduleQtyExternalPropertyProvider implements IShipmentScheduleQtyOnHandProvider
{
	private final HUReservationRepository huReservationRepository;
	private final HUDescriptorService huDescriptorService;
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	public ShipmentScheduleQtyExternalPropertyProvider(@NonNull final HUReservationRepository huReservationRepository, @NonNull final HUDescriptorService huDescriptorService)
	{
		this.huReservationRepository = huReservationRepository;
		this.huDescriptorService = huDescriptorService;
	}

	@Override
	public IShipmentScheduleQtyOnHandStorage getStorageFor(@NonNull final List<I_M_ShipmentSchedule> lines)
	{
		return new ShipmentScheduleQtyExternalPropertyStorage(huReservationRepository, huDescriptorService, uomConversionBL, productBL, handlingUnitsBL);
	}

}
