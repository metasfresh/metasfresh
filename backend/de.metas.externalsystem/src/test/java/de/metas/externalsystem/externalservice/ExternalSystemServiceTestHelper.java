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

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.externalservice.externalserviceinstance.ExternalSystemServiceInstanceId;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceId;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Service;
import de.metas.externalsystem.model.I_ExternalSystem_Service_Instance;
import lombok.Builder;
import lombok.NonNull;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ExternalSystemServiceTestHelper
{
	@NonNull
	@Builder(builderMethodName = "createExternalConfigBuilder")
	public static ExternalSystemParentConfigId createExternalConfig(
			@NonNull final String type,
			@NonNull final String name)
	{
		final I_ExternalSystem_Config record = newInstance(I_ExternalSystem_Config.class);
		record.setName(name);
		record.setType(type);
		saveRecord(record);

		return ExternalSystemParentConfigId.ofRepoId(record.getExternalSystem_Config_ID());
	}

	@NonNull
	@Builder(builderMethodName = "createExternalSystemServiceBuilder")
	public static ExternalSystemServiceId createExternalSystemService(
			@NonNull final String type,
			@NonNull final String name,
			@NonNull final String value)
	{
		final I_ExternalSystem_Service record = newInstance(I_ExternalSystem_Service.class);
		record.setName(name);
		record.setType(type);
		record.setValue(value);
		record.setDescription("Description");
		record.setDisableCommand("DisableCommand");
		record.setEnableCommand("EnableCommand");
		saveRecord(record);

		return ExternalSystemServiceId.ofRepoId(record.getExternalSystem_Service_ID());
	}

	@NonNull
	@Builder(builderMethodName = "createExternalServiceInstanceBuilder")
	public static ExternalSystemServiceInstanceId createExternalServiceInstance(
			@NonNull final Integer configId,
			@NonNull final Integer serviceId,
			@NonNull final String expectedStatus)
	{
		final I_ExternalSystem_Service_Instance record = newInstance(I_ExternalSystem_Service_Instance.class);
		record.setExternalSystem_Config_ID(configId);
		record.setExternalSystem_Service_ID(serviceId);
		record.setExpectedStatus(expectedStatus);
		saveRecord(record);

		return ExternalSystemServiceInstanceId.ofRepoId(record.getExternalSystem_Service_Instance_ID());
	}
}
