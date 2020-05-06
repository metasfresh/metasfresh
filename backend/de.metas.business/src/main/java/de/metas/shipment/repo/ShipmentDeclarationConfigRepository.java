package de.metas.shipment.repo;

import java.util.Collection;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Shipment_Declaration_Config;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;

import de.metas.cache.CCache;
import de.metas.document.DocTypeId;
import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationConfigId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

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
	private final CCache<Integer, ImmutableMap<ShipmentDeclarationConfigId, ShipmentDeclarationConfig>> //
	cache = CCache.<Integer, ImmutableMap<ShipmentDeclarationConfigId, ShipmentDeclarationConfig>> builder()
			.tableName(I_M_Shipment_Declaration_Config.Table_Name)
			.build();

	public Collection<ShipmentDeclarationConfig> getAll()
	{
		return getAllIndexedById().values();
	}

	public ShipmentDeclarationConfig getConfigById(@NonNull final ShipmentDeclarationConfigId configId)
	{
		final ShipmentDeclarationConfig config = getAllIndexedById().get(configId);
		if (config == null)
		{
			throw new AdempiereException("@NotFound@ " + configId);
		}
		return config;
	}

	private ImmutableMap<ShipmentDeclarationConfigId, ShipmentDeclarationConfig> getAllIndexedById()
	{
		return cache.getOrLoad(0, () -> retrieveAll());
	}

	private ImmutableMap<ShipmentDeclarationConfigId, ShipmentDeclarationConfig> retrieveAll()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Shipment_Declaration_Config.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toShipmentDeclarationConfig(record))
				.collect(GuavaCollectors.toImmutableMapByKey(ShipmentDeclarationConfig::getId));
	}

	private static ShipmentDeclarationConfig toShipmentDeclarationConfig(final I_M_Shipment_Declaration_Config record)
	{
		return ShipmentDeclarationConfig.builder()
				.id(ShipmentDeclarationConfigId.ofRepoId(record.getM_Shipment_Declaration_Config_ID()))
				.docTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.docTypeCorrectionId(DocTypeId.ofRepoIdOrNull(record.getC_DocType_Correction_ID()))
				.documentLinesNumber(record.getDocumentLinesNumber())
				.name(record.getName())
				.build();
	}
}
