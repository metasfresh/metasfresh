package de.metas.vertical.pharma.shipment;

import org.springframework.stereotype.Component;

import de.metas.inout.InOutAndLineId;
import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationVetoer;
import de.metas.vertical.pharma.PharmaProductRepository;
import de.metas.vertical.pharma.shipment.repo.PharmaShipmentConfigRepository;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
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

@Component
public class NarcoticShipmentDeclarationVetoer implements ShipmentDeclarationVetoer
{
	private final PharmaShipmentConfigRepository pharmaShipmentDeclarationConfigRepo;
	private final PharmaProductRepository pharmaProductRepo;

	private NarcoticShipmentDeclarationVetoer(
			@NonNull final PharmaShipmentConfigRepository pharmaShipmentDeclarationConfigRepo,
			@NonNull final PharmaProductRepository pharmaProductRepo)
	{
		this.pharmaShipmentDeclarationConfigRepo = pharmaShipmentDeclarationConfigRepo;
		this.pharmaProductRepo = pharmaProductRepo;
	}

	@Override
	public OnShipmentDeclarationConfig foundShipmentLineForConfig(
			final InOutAndLineId shipmentLineId,
			final ShipmentDeclarationConfig shipmentDeclarationConfig)
	{
		if (!pharmaShipmentDeclarationConfigRepo.isOnlyNarcoticProducts(shipmentDeclarationConfig))
		{
			return OnShipmentDeclarationConfig.I_DONT_CARE;
		}

		if (pharmaProductRepo.isLineForNarcoticProduct(shipmentLineId))
		{
			return OnShipmentDeclarationConfig.I_VETO;
		}

		return OnShipmentDeclarationConfig.I_DONT_CARE;
	}
}
