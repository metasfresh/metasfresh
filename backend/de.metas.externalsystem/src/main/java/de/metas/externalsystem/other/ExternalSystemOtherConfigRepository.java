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

package de.metas.externalsystem.other;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.model.I_ExternalSystem_Other_ConfigParameter;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExternalSystemOtherConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ExternalSystemOtherConfig getById(@NonNull final ExternalSystemOtherConfigId externalSystemOtherConfigId)
	{
		final List<ExternalSystemOtherConfigParameter> parameters = queryBL.createQueryBuilder(I_ExternalSystem_Other_ConfigParameter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Other_ConfigParameter.COLUMNNAME_ExternalSystem_Config_ID, externalSystemOtherConfigId.getRepoId())
				.create()
				.list()
				.stream()
				.map(this::recordToParameterModel)
				.collect(ImmutableList.toImmutableList());

		if (parameters.isEmpty())
		{
			throw new AdempiereException("No ExternalSystem_Other_ConfigParameter found for ExternalSystem_Config_ID=" + externalSystemOtherConfigId.getRepoId());
		}

		return ExternalSystemOtherConfig.builder()
				.id(externalSystemOtherConfigId)
				.parameters(parameters)
				.build();
	}

	@NonNull
	public ExternalSystemOtherConfig getByValue(@NonNull final String value)
	{
		final ExternalSystemOtherValue externalSystemOtherValue = ExternalSystemOtherValue.ofString(value);
		return getById(externalSystemOtherValue.getExternalSystemOtherConfigId());
	}

	@NonNull
	private ExternalSystemOtherConfigParameter recordToParameterModel(@NonNull final I_ExternalSystem_Other_ConfigParameter configParameter)
	{
		return ExternalSystemOtherConfigParameter.builder()
				.id(ExternalSystemOtherConfigParameterId.ofRepoId(configParameter.getExternalSystem_Other_ConfigParameter_ID()))
				.name(configParameter.getName())
				.value(configParameter.getValue())
				.build();
	}
}
