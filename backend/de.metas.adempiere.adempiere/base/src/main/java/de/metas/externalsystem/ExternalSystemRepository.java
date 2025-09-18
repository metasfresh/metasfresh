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

import com.google.common.annotations.VisibleForTesting;
import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class ExternalSystemRepository
{
	@VisibleForTesting
	public static ExternalSystemRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ExternalSystemRepository();
	}

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

	public ExternalSystem getByType(@NonNull final ExternalSystemType type)
	{
		return getMap().getByType(type);
	}

	public Optional<ExternalSystem> getOptionalByType(@NonNull final ExternalSystemType type)
	{
		return getMap().getOptionalByType(type);
	}

	public Optional<ExternalSystem> getOptionalByValue(@NonNull final String value)
	{
		return getMap().getOptionalByType(ExternalSystemType.ofValue(value));
	}

	@NonNull
	public ExternalSystem getById(@NonNull final ExternalSystemId id)
	{
		return getMap().getById(id);
	}

	private static ExternalSystem fromRecord(@NonNull final I_ExternalSystem externalSystemRecord)
	{
		return ExternalSystem.builder()
				.id(ExternalSystemId.ofRepoId(externalSystemRecord.getExternalSystem_ID()))
				.type(ExternalSystemType.ofValue(externalSystemRecord.getValue()))
				.name(externalSystemRecord.getName())
				.build();
	}

	public ExternalSystem create(@NonNull final ExternalSystemCreateRequest request)
	{
		final I_ExternalSystem record = InterfaceWrapperHelper.newInstance(I_ExternalSystem.class);
		record.setName(request.getName());
		record.setValue(request.getType().getValue());
		InterfaceWrapperHelper.save(record);
		return fromRecord(record);
	}

	@Nullable
	public ExternalSystem getByLegacyCodeOrValueOrNull(@NonNull final String value)
	{
		final ExternalSystemMap map = getMap();
		return CoalesceUtil.coalesceSuppliers(
				() -> map.getByTypeOrNull(ExternalSystemType.ofValue(value)),
				() -> {
					final ExternalSystemType externalSystemType = ExternalSystemType.LEGACY_CODES.inverse().get(value);
					return externalSystemType != null ? map.getByTypeOrNull(externalSystemType) : null;
				}
		);
	}
}
