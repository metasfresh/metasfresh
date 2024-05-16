/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.externalservice.model;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem_Service;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExternalSystemServiceRepository implements ISingletonService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	@Cached(cacheName = I_ExternalSystem_Service.Table_Name + "#by#" + I_ExternalSystem_Service.COLUMNNAME_ExternalSystem_Service_ID)
	public ExternalSystemServiceModel getById(@NonNull final ExternalSystemServiceId serviceId)
	{
		final I_ExternalSystem_Service record = InterfaceWrapperHelper.load(serviceId, I_ExternalSystem_Service.class);

		return ofServiceRecord(record);
	}

	@NonNull
	@Cached(cacheName = I_ExternalSystem_Service.Table_Name + "#by#" + I_ExternalSystem_Service.COLUMNNAME_Type)
	public List<ExternalSystemServiceModel> getAllByType(@NonNull final ExternalSystemType systemType)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Service.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Service.COLUMNNAME_Type, systemType.getCode())
				.create()
				.stream()
				.map(ExternalSystemServiceRepository::ofServiceRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	@Cached(cacheName = I_ExternalSystem_Service.Table_Name + "#by#" + I_ExternalSystem_Service.COLUMNNAME_Value)
	public Optional<ExternalSystemServiceModel> getByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Service.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Service.COLUMNNAME_Value, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Service.class)
				.map(ExternalSystemServiceRepository::ofServiceRecord);
	}

	@NonNull
	private static ExternalSystemServiceModel ofServiceRecord(@NonNull final I_ExternalSystem_Service record)
	{
		return ExternalSystemServiceModel.builder()
				.id(ExternalSystemServiceId.ofRepoId(record.getExternalSystem_Service_ID()))
				.name(record.getName())
				.description(record.getDescription())
				.serviceValue(record.getValue())
				.externalSystemType(ExternalSystemType.ofCode(record.getType()))
				.disableCommand(record.getDisableCommand())
				.enableCommand(record.getEnableCommand())
				.build();
	}
}
