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

package de.metas.externalsystem.externalservice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.externalsystem.status.JsonExternalStatusResponseItem;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.externalservice.common.ExternalStatus;
import de.metas.externalsystem.externalservice.externalserviceinstance.CreateServiceInstanceRequest;
import de.metas.externalsystem.externalservice.externalserviceinstance.ExternalSystemServiceInstance;
import de.metas.externalsystem.externalservice.externalserviceinstance.ExternalSystemServiceInstanceRepository;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceId;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceModel;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceRepository;
import de.metas.externalsystem.externalservice.status.ExternalSystemStatus;
import de.metas.externalsystem.externalservice.status.ExternalSystemStatusRepository;
import de.metas.externalsystem.externalservice.status.StoreExternalSystemStatusRequest;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ExternalServices
{
	private final ExternalSystemServiceRepository serviceRepo;
	private final ExternalSystemServiceInstanceRepository serviceInstanceRepo;
	private final ExternalSystemStatusRepository statusRepo;
	private final ExternalSystemConfigRepo externalSystemConfigRepo;

	public ExternalServices(@NonNull final ExternalSystemServiceRepository serviceRepo,
			@NonNull final ExternalSystemServiceInstanceRepository serviceInstanceRepo,
			@NonNull final ExternalSystemStatusRepository statusRepo,
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo)
	{
		this.serviceRepo = serviceRepo;
		this.serviceInstanceRepo = serviceInstanceRepo;
		this.statusRepo = statusRepo;
		this.externalSystemConfigRepo = externalSystemConfigRepo;
	}

	public void handleStatusUpdateIfRequired(@NonNull final ExternalSystemParentConfigId configId, @NonNull final String command)
	{
		final List<ExternalSystemServiceInstance> matchingServiceInstances = serviceInstanceRepo.streamByConfigIds(ImmutableSet.of(configId))
				.filter(serviceInstance -> serviceInstance.getService().matchesCommand(command))
				.collect(ImmutableList.toImmutableList());

		if (matchingServiceInstances.size() > 1)
		{
			//dev-note: should never happen hence "command" is an InvokeExternalSystemProcess#externalRequest meant to uniquely identify a camel service route
			throw new AdempiereException("More than one ExternalSystemServiceInstance found for given configId and command!"
												 + "This is most probably a misconfiguration issue.")
					.appendParametersToMessage()
					.setParameter("configId", configId)
					.setParameter("command", command);
		}

		if (matchingServiceInstances.isEmpty())
		{
			createInstanceIfRequired(configId, command);
			return;
		}

		final ExternalSystemServiceInstance matchedInstance = matchingServiceInstances.get(0);
		final ExternalStatus expectedStatus = matchedInstance.getService().getStatusByCommand(command);

		final ExternalSystemServiceInstance instanceWithRefreshedStatus = matchedInstance.toBuilder().expectedStatus(expectedStatus).build();

		serviceInstanceRepo.update(instanceWithRefreshedStatus);
	}

	@NonNull
	public List<JsonExternalStatusResponseItem> getStatusInfo(@NonNull final ExternalSystemType externalSystemType)
	{
		final List<ExternalSystemParentConfig> externalSystemConfigs = externalSystemConfigRepo.getActiveByType(externalSystemType);

		final Map<ExternalSystemParentConfigId, String> configId2ChildValue = externalSystemConfigs.stream()
				.collect(ImmutableMap.toImmutableMap(ExternalSystemParentConfig::getId,
													 parentConfig -> parentConfig.getChildConfig().getValue()));

		return serviceInstanceRepo.streamByConfigIds(configId2ChildValue.keySet())
				.map(serviceInstance -> {
					final ExternalSystemServiceModel service = serviceInstance.getService();

					return JsonExternalStatusResponseItem.builder()
							.externalSystemConfigType(service.getExternalSystemType().getCode())
							.serviceValue(service.getServiceValue())
							.externalSystemChildValue(configId2ChildValue.get(serviceInstance.getConfigId()))
							.expectedStatus(JsonExternalStatus.valueOf(serviceInstance.getExpectedStatus().getCode()))
							.build();
				})
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ExternalSystemStatus storeExternalSystemStatus(
			@NonNull final ExternalSystemParentConfigId configId,
			@NonNull final StoreExternalSystemStatusRequest statusRequest)
	{
		final ExternalSystemServiceModel externalService = getServiceNotNull(statusRequest.getServiceValue());

		final ExternalSystemServiceInstance serviceInstance = getInstanceByConfigIdAndServiceId(configId, externalService.getId());

		return statusRepo.create(serviceInstance.getId(), statusRequest);
	}

	public void initializeServiceInstancesIfRequired(@NonNull final ExternalSystemParentConfigId configId)
	{
		final ExternalSystemType externalSystemType = ExternalSystemType.ofCode(externalSystemConfigRepo.getParentTypeById(configId));

		serviceRepo.getAllByType(externalSystemType)
				.stream()
				.filter(service -> { //dev-note: just a guard for the future
					final Optional<ExternalSystemServiceInstance> instance = serviceInstanceRepo.getByConfigAndServiceId(configId, service.getId());

					return !instance.isPresent();
				})
				.map(service -> CreateServiceInstanceRequest.builder()
						.configId(configId)
						.serviceId(service.getId())
						.expectedStatus(ExternalStatus.INACTIVE)
						.build())
				.forEach(serviceInstanceRepo::create);
	}

	@NonNull
	private ExternalSystemServiceInstance getInstanceByConfigIdAndServiceId(@NonNull final ExternalSystemParentConfigId configId, @NonNull final ExternalSystemServiceId serviceId)
	{
		return serviceInstanceRepo.getByConfigAndServiceId(configId, serviceId)
				.orElseThrow(() -> new AdempiereException("No ExternalSystemServiceInstance found by given configId and serviceId!")
						.appendParametersToMessage()
						.setParameter("ExternalSystemConfigId", configId)
						.setParameter("ExternalSystemServiceId", serviceId));
	}

	@NonNull
	private ExternalSystemServiceModel getServiceNotNull(@NonNull final String serviceValue)
	{
		return serviceRepo.getByValue(serviceValue)
				.orElseThrow(() -> new AdempiereException("No ExternalSystemService found by given value!")
						.appendParametersToMessage()
						.setParameter("ExternalSystemService.value", serviceValue));
	}

	private void createInstanceIfRequired(@NonNull final ExternalSystemParentConfigId configId, @NonNull final String command)
	{
		final String parentConfigType = externalSystemConfigRepo.getParentTypeById(configId);

		getServiceByTypeAndCommand(ExternalSystemType.ofCode(parentConfigType), command)
				.map(service -> CreateServiceInstanceRequest.builder()
						.configId(configId)
						.serviceId(service.getId())
						.expectedStatus(service.getStatusByCommand(command))
						.build())
				.ifPresent(serviceInstanceRepo::create);
	}

	@NonNull
	private Optional<ExternalSystemServiceModel> getServiceByTypeAndCommand(@NonNull final ExternalSystemType type, @NonNull final String command)
	{
		final List<ExternalSystemServiceModel> externalSystemServices = serviceRepo.getAllByType(type)
				.stream()
				.filter(service -> service.matchesCommand(command))
				.collect(ImmutableList.toImmutableList());

		if (externalSystemServices.size() > 1)
		{
			//dev-note: should never happen hence "command" is an InvokeExternalSystemProcess#externalRequest meant to uniquely identify a camel service route
			throw new AdempiereException("More than one ExternalSystemService found for given type and command! This is most probably a misconfiguration issue.")
					.appendParametersToMessage()
					.setParameter("type", type)
					.setParameter("command", command);
		}

		if (externalSystemServices.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(externalSystemServices.get(0));
	}
}
