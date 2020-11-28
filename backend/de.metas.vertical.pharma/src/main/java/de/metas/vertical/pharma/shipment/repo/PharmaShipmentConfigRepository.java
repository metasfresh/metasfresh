package de.metas.vertical.pharma.shipment.repo;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.springframework.stereotype.Repository;

import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationConfigId;
import de.metas.vertical.pharma.shipment.model.I_M_Shipment_Declaration_Config;

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
@Repository
public class PharmaShipmentConfigRepository
{

	public boolean isOnlyNarcoticProducts(final ShipmentDeclarationConfig config)
	{
		final ShipmentDeclarationConfigId configId = config.getId();
		final I_M_Shipment_Declaration_Config configRecord = load(configId, I_M_Shipment_Declaration_Config.class);

		return configRecord.isOnlyNarcoticProducts();
	}
}
