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

package de.metas.externalsystem.externalservice.externalserviceinstance;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.externalservice.common.ExternalStatus;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceId;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceModel;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceRepository;
import de.metas.externalsystem.model.I_ExternalSystem_Service_Instance;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ExternalSystemServiceInstanceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ExternalSystemServiceRepository serviceRepository;

	public ExternalSystemServiceInstanceRepository(@NonNull final ExternalSystemServiceRepository serviceRepository)
	{
		this.serviceRepository = serviceRepository;
	}

	@NonNull
	public ExternalSystemServiceInstance create(@NonNull final CreateServiceInstanceRequest request)
	{
		final I_ExternalSystem_Service_Instance externalSysInstanceRecord = newInstance(I_ExternalSystem_Service_Instance.class);

		externalSysInstanceRecord.setExternalSystem_Service_ID(request.getServiceId().getRepoId());
		externalSysInstanceRecord.setExternalSystem_Config_ID(request.getConfigId().getRepoId());
		externalSysInstanceRecord.setExpectedStatus(request.getExpectedStatus().getCode());

		saveRecord(externalSysInstanceRecord);

		return ofRecord(externalSysInstanceRecord);
	}

	@NonNull
	public ExternalSystemServiceInstance update(@NonNull final ExternalSystemServiceInstance instance)
	{
		final I_ExternalSystem_Service_Instance externalSysInstanceRecord = InterfaceWrapperHelper.load(instance.getId(), I_ExternalSystem_Service_Instance.class);

		externalSysInstanceRecord.setExternalSystem_Service_ID(instance.getService().getId().getRepoId());
		externalSysInstanceRecord.setExternalSystem_Config_ID(instance.getConfigId().getRepoId());
		externalSysInstanceRecord.setExpectedStatus(instance.getExpectedStatus().getCode());

		saveRecord(externalSysInstanceRecord);

		return ofRecord(externalSysInstanceRecord);
	}

	@NonNull
	public Optional<ExternalSystemServiceInstance> getByConfigAndServiceId(@NonNull final ExternalSystemParentConfigId configId, @NonNull final ExternalSystemServiceId serviceId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Service_Instance.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Service_Instance.COLUMN_ExternalSystem_Config_ID, configId)
				.addEqualsFilter(I_ExternalSystem_Service_Instance.COLUMN_ExternalSystem_Service_ID, serviceId)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Service_Instance.class)
				.map(this::ofRecord);
	}

	@NonNull
	public Stream<ExternalSystemServiceInstance> streamByConfigIds(@NonNull final Set<ExternalSystemParentConfigId> configIds)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Service_Instance.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_ExternalSystem_Service_Instance.COLUMNNAME_ExternalSystem_Config_ID, configIds)
				.create()
				.stream()
				.map(this::ofRecord);
	}

	@NonNull
	private ExternalSystemServiceInstance ofRecord(@NonNull final I_ExternalSystem_Service_Instance record)
	{
		final ExternalSystemServiceModel service = serviceRepository.getById(ExternalSystemServiceId.ofRepoId(record.getExternalSystem_Service_ID()));

		return ExternalSystemServiceInstance.builder()
				.id(ExternalSystemServiceInstanceId.ofRepoId(record.getExternalSystem_Service_Instance_ID()))
				.service(service)
				.configId(ExternalSystemParentConfigId.ofRepoId(record.getExternalSystem_Config_ID()))
				.expectedStatus(ExternalStatus.ofCode(record.getExpectedStatus()))
				.build();
	}
}

