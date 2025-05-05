/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.other.export.project.workorder.interceptor;

import de.metas.externalsystem.other.export.project.workorder.ExportWorkOrderProjectToOtherService;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Project_WO_Step.class)
@Component
public class C_Project_WO_Step
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull
	private final ExportWorkOrderProjectToOtherService exportWorkOrderProjectToOtherService;

	public C_Project_WO_Step(@NonNull final ExportWorkOrderProjectToOtherService exportWorkOrderProjectToOtherService)
	{
		this.exportWorkOrderProjectToOtherService = exportWorkOrderProjectToOtherService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void triggerSyncWOProjectWithExternalSystem(@NonNull final I_C_Project_WO_Step woStep)
	{
		final ProjectId projectId = ProjectId.ofRepoId(woStep.getC_Project_ID());

		trxManager.runAfterCommit(() -> exportWorkOrderProjectToOtherService.enqueueProjectSync(projectId));
	}
}
