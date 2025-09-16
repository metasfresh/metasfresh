/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem;

import com.google.common.collect.ImmutableBiMap;
import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class ExternalSystemRepository
{
	private static final ImmutableBiMap<ExternalSystem.SystemValue, String> LEGACY_CODES = ImmutableBiMap.<ExternalSystem.SystemValue, String>builder()
			.put(ExternalSystem.SystemValue.Alberta, "A")
			.put(ExternalSystem.SystemValue.Shopware6, "S6")
			.put(ExternalSystem.SystemValue.Other, "Other")
			.put(ExternalSystem.SystemValue.WOO, "WOO")
			.put(ExternalSystem.SystemValue.RabbitMQ, "RabbitMQ")
			.put(ExternalSystem.SystemValue.GRSSignum, "GRS")
			.put(ExternalSystem.SystemValue.LeichUndMehl, "LM")
			.put(ExternalSystem.SystemValue.PrintingClient, "PC")
			.put(ExternalSystem.SystemValue.ProCareManagement, "PCM")
			.build();

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ExternalSystemMap> cache = CCache.<Integer, ExternalSystemMap>builder()
			.tableName(I_ExternalSystem.Table_Name)
			.build();

	private ExternalSystemMap getMap() {return cache.getOrLoad(0, this::retrieveMap);}

	private ExternalSystemMap retrieveMap()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(ExternalSystemRepository::fromRecord)
				.collect(ExternalSystemMap.collect());
	}

	public ExternalSystem getByValue(@NonNull final String value)
	{
		return getMap().getByValue(value);
	}

	public Optional<ExternalSystem> getOptionalByValue(@NonNull final String value)
	{
		return getMap().getOptionalByValue(value);
	}

	@NonNull
	public ExternalSystem getById(@NonNull final ExternalSystemId id)
	{
		return getMap().getById(id);
	}

	private static ExternalSystem fromRecord(@NonNull final I_ExternalSystem externalSystemRecord)
	{
		return ExternalSystem.builder()
				.externalSystemId(ExternalSystemId.ofRepoId(externalSystemRecord.getExternalSystem_ID()))
				.name(externalSystemRecord.getName())
				.value(externalSystemRecord.getValue())
				.build();
	}

	public ExternalSystem save(@NonNull final ExternalSystem externalSystem)
	{
		final I_ExternalSystem externalSystemRecord;
		if (externalSystem.getExternalSystemId() == null)
		{
			externalSystemRecord = InterfaceWrapperHelper.newInstance(I_ExternalSystem.class);
		}
		else
		{
			externalSystemRecord = InterfaceWrapperHelper.load(externalSystem.getExternalSystemId(), I_ExternalSystem.class);
		}

		externalSystemRecord.setName(externalSystem.getName());
		externalSystemRecord.setValue(externalSystem.getValue());

		InterfaceWrapperHelper.save(externalSystemRecord);

		final ExternalSystemId externalSystemId = ExternalSystemId.ofRepoId(externalSystemRecord.getExternalSystem_ID());

		return externalSystem.toBuilder().externalSystemId(externalSystemId).build();
	}

	@Nullable
	public ExternalSystem getByLegacyCodeOrValueOrNull(@NonNull final String value)
	{
		final ExternalSystemMap map = getMap();
		return CoalesceUtil.coalesceSuppliers(
				()->map.getByValueOrNull(value),
				()-> {
					final ExternalSystem.SystemValue systemValue = LEGACY_CODES.inverse().get(value);
					return systemValue != null ? map.getByValueOrNull(systemValue.getValue()) : null;
				}
		);
	}
}
