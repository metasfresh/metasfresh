/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project.workorder.resource;

import com.google.common.collect.ImmutableList;
import de.metas.project.workorder.calendar.WOProjectSimulationService;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Interceptor(I_C_Project_WO_Resource.class)
public class C_Project_WO_Resource
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final WOProjectSimulationService woProjectSimulationService;

	public C_Project_WO_Resource(@NonNull final WOProjectSimulationService woProjectSimulationService)
	{
		this.woProjectSimulationService = woProjectSimulationService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void createSimulationEntries(@NonNull final I_C_Project_WO_Resource record)
	{
		final WOProjectResource woProjectResource = WOProjectResourceRepository.ofRecord(record);
		if (woProjectResource.getDateRange() != null)
		{
			return;
		}

		trxManager.accumulateAndProcessAfterCommit(
				"C_Project_WO_Resource.createSimulationEntries",
				ImmutableList.of(record),
				this::createSimulationForResources);
	}

	private void createSimulationForResources(@NonNull final List<I_C_Project_WO_Resource> resources)
	{
		if (resources.isEmpty())
		{
			return;
		}

		//one trx => same updatedBy for all resources
		final UserId fallbackResponsibleUserId = UserId.ofRepoId(resources.get(0).getUpdatedBy());

		final ImmutableList<WOProjectResource> projectResources = resources
				.stream()
				.map(WOProjectResourceRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());

		woProjectSimulationService.initializeSimulationForResources(fallbackResponsibleUserId, projectResources);
	}
}
