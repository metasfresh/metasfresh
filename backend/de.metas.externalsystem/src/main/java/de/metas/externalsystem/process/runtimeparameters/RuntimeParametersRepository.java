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

package de.metas.externalsystem.process.runtimeparameters;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_RuntimeParameter;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class RuntimeParametersRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public RuntimeParameter upsertRuntimeParameter(@NonNull final RuntimeParameterUpsertRequest request)
	{
		final RuntimeParamUniqueKey uniqueKey = request.getRuntimeParamUniqueKey();

		final I_ExternalSystem_RuntimeParameter record = getRecordByUniqueKey(uniqueKey)
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_ExternalSystem_RuntimeParameter.class));

		record.setExternalSystem_Config_ID(uniqueKey.getExternalSystemParentConfigId().getRepoId());
		record.setExternal_Request(uniqueKey.getRequest());
		record.setName(uniqueKey.getName());
		record.setValue(request.getValue());

		saveRecord(record);

		return recordToModel(record);
	}

	@NonNull
	public ImmutableList<RuntimeParameter> getByConfigIdAndRequest(@NonNull final ExternalSystemParentConfigId configId, @NonNull final String externalRequest)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_RuntimeParameter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_RuntimeParameter.COLUMNNAME_ExternalSystem_Config_ID, configId.getRepoId())
				.addEqualsFilter(I_ExternalSystem_RuntimeParameter.COLUMNNAME_External_Request, externalRequest)
				.create()
				.list()
				.stream()
				.map(this::recordToModel)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private RuntimeParameter recordToModel(@NonNull final I_ExternalSystem_RuntimeParameter record)
	{
		return RuntimeParameter.builder()
				.runtimeParameterId(RuntimeParameterId.ofRepoId(record.getExternalSystem_RuntimeParameter_ID()))
				.externalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoId(record.getExternalSystem_Config_ID()))
				.request(record.getExternal_Request())
				.name(record.getName())
				.value(record.getValue())
				.build();
	}

	@NonNull
	private Optional<I_ExternalSystem_RuntimeParameter> getRecordByUniqueKey(@NonNull final RuntimeParamUniqueKey uniqueKey)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_RuntimeParameter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_RuntimeParameter.COLUMNNAME_External_Request, uniqueKey.getRequest())
				.addEqualsFilter(I_ExternalSystem_RuntimeParameter.COLUMNNAME_Name, uniqueKey.getName())
				.addEqualsFilter(I_ExternalSystem_RuntimeParameter.COLUMNNAME_ExternalSystem_Config_ID, uniqueKey.getExternalSystemParentConfigId().getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_RuntimeParameter.class);
	}
}
