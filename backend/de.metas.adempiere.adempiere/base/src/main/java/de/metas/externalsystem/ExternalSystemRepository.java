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

import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class ExternalSystemRepository
{
	private static final ImmutableMap<ExternalSystem.SystemValue, String> LEGACY_CODES_BY_SYSTEM_VALUE = ImmutableMap.<ExternalSystem.SystemValue, String>builder()
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

	private static final ImmutableMap<String, ExternalSystem.SystemValue> SYSTEM_VALUE_BY_LEGACY_CODES = ImmutableMap.<String, ExternalSystem.SystemValue>builder()
			.put("A", ExternalSystem.SystemValue.Alberta)
			.put("S6", ExternalSystem.SystemValue.Shopware6)
			.put("Other", ExternalSystem.SystemValue.Other)
			.put("WOO", ExternalSystem.SystemValue.WOO)
			.put("RabbitMQ", ExternalSystem.SystemValue.RabbitMQ)
			.put("GRS", ExternalSystem.SystemValue.GRSSignum)
			.put("LM", ExternalSystem.SystemValue.LeichUndMehl)
			.put("PC", ExternalSystem.SystemValue.PrintingClient)
			.put("PCM", ExternalSystem.SystemValue.ProCareManagement)
			.build();

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ExternalSystemId, ExternalSystem> systemsById = CCache.<ExternalSystemId, ExternalSystem>builder()
			.tableName(I_ExternalSystem.Table_Name)
			.build();

	private final CCache<String, ExternalSystem> systemsByValue = CCache.<String, ExternalSystem>builder()
			.tableName(I_ExternalSystem.Table_Name)
			.build();

	public ExternalSystem getByValue(@NonNull final String value)
	{
		return getOptionalByValue(value)
				.orElseThrow(() -> new AdempiereException("Unknown externalSystemValue" + value));
	}

	public Optional<ExternalSystem> getOptionalByValue(@NonNull final String value)
	{
		return Optional.ofNullable(systemsByValue.getOrLoad(value, this::retrieveByValue));
	}

	@Nullable
	private ExternalSystem retrieveByValue(@NonNull final String value)
	{
		final ExternalSystem externalSystem = queryBL.createQueryBuilder(I_ExternalSystem.class)
				.addEqualsFilter(I_ExternalSystem.COLUMNNAME_Value, value)
				.create()
				.firstOnlyOptional()
				.map(this::fromRecord)
				.orElse(null);

		if(externalSystem != null)
		{
			final ExternalSystemId externalSystemId = Check.assumeNotNull(externalSystem.getExternalSystemId(), "externalSystemId should not be null");
			systemsById.put(externalSystemId, externalSystem);
		}

		return externalSystem;
	}

	@NonNull
	public ExternalSystem getById(@NonNull final ExternalSystemId externalSystemId)
	{
		//noinspection DataFlowIssue
		return systemsById.getOrLoad(externalSystemId, this::retrieveById);
	}

	@NonNull
	private ExternalSystem retrieveById(@NonNull final ExternalSystemId externalSystemId)
	{
		final ExternalSystem externalSystem = queryBL.createQueryBuilder(I_ExternalSystem.class)
				.addEqualsFilter(I_ExternalSystem.COLUMNNAME_ExternalSystem_ID, externalSystemId)
				.create()
				.firstOnlyOptional()
				.map(this::fromRecord)
				.orElseThrow(() -> new AdempiereException("No externalSystem found for Id " + externalSystemId.getRepoId()));

		systemsByValue.put(externalSystem.getValue(), externalSystem);

		return externalSystem;
	}

	private ExternalSystem fromRecord(@NonNull final I_ExternalSystem externalSystemRecord)
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
		if(externalSystem.getExternalSystemId() == null)
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
		externalSystem.toBuilder().externalSystemId(externalSystemId);
		systemsById.put(externalSystemId, externalSystem);
		systemsByValue.put(externalSystem.getValue(), externalSystem);

		return externalSystem;
	}

	@Nullable
	public ExternalSystem getByLegacyCodeOrValueOrNull(@NonNull final String value)
	{
		final ExternalSystem.SystemValue systemValue = SYSTEM_VALUE_BY_LEGACY_CODES.get(value);
		return CoalesceUtil.coalesce(getOptionalByValue(value).orElse(null), getOptionalByValue(systemValue.getValue()).orElse(null));
	}
}
