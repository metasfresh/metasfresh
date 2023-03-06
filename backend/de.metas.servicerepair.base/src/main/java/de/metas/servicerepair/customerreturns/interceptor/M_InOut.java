/*
 * #%L
 * de.metas.servicerepair.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.servicerepair.customerreturns.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.project.ProjectId;
import de.metas.servicerepair.customerreturns.RepairCustomerReturnsService;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOut.class)
@Component
public class M_InOut
{
	private static final AdMessageKey MSG_PROJECT_ACTIVE_REVERSE_NOT_POSSIBLE = AdMessageKey.of("project_still_active_reverse_not_possible");

	private final RepairCustomerReturnsService repairCustomerReturnsService;
	private final ServiceRepairProjectService serviceRepairProjectService;

	public M_InOut(
			@NonNull final RepairCustomerReturnsService repairCustomerReturnsService,
			@NonNull final ServiceRepairProjectService serviceRepairProjectService)
	{
		this.repairCustomerReturnsService = repairCustomerReturnsService;
		this.serviceRepairProjectService = serviceRepairProjectService;
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REVERSECORRECT)
	public void beforeReverseCorrect(final I_M_InOut inout)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(inout.getC_Project_ID());
		if (projectId == null)
		{
			return;
		}

		if (!repairCustomerReturnsService.isRepairCustomerReturns(inout))
		{
			return;
		}

		final ServiceRepairProjectInfo project = serviceRepairProjectService.getByIdIfRepairProject(projectId).orElse(null);
		if (project == null)
		{
			return;
		}

		if (project.isActive())
		{
			throw new AdempiereException(MSG_PROJECT_ACTIVE_REVERSE_NOT_POSSIBLE);
		}
	}
}
