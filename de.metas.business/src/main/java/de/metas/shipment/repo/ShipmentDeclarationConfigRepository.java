package de.metas.shipment.repo;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Shipment_Declaration_Config;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

import de.metas.document.DocTypeId;
import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationConfigId;
import de.metas.util.Services;

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
@Repository
public class ShipmentDeclarationConfigRepository
{
	public ImmutableSet<ShipmentDeclarationConfig> retrieveShipmentDeclarationConfigs()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Shipment_Declaration_Config.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds(ShipmentDeclarationConfigId::ofRepoId)
				.stream()
				.map(id -> getConfigById(id))
				.collect(ImmutableSet.toImmutableSet());
	}

	public ShipmentDeclarationConfig getConfigById(final ShipmentDeclarationConfigId configId)
	{
		final I_M_Shipment_Declaration_Config config = load(configId, I_M_Shipment_Declaration_Config.class);

		return ShipmentDeclarationConfig.builder()
				.docTypeId(DocTypeId.ofRepoId(config.getC_DocType_ID()))
				.documentLinesNumber(config.getDocumentLinesNumber())
				.shipmentDeclarationConfigId(configId)
				.build();
	}
}
