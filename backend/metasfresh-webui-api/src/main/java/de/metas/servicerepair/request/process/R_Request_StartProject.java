/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.request.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.project.ProjectId;
import de.metas.request.RequestId;
import de.metas.request.api.IRequestBL;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_R_Request;

public class R_Request_StartProject extends JavaProcess implements IProcessPrecondition
{
	private final ServiceRepairProjectService serviceRepairProjectService = SpringContextHolder.instance.getBean(ServiceRepairProjectService.class);
	private final IRequestBL requestBL = Services.get(IRequestBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final RequestId requestId = RequestId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (requestId == null)
		{
			return ProcessPreconditionsResolution.reject();
		}

		final I_R_Request request = requestBL.getById(requestId);
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(request.getC_Project_ID());
		if (projectId != null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("project already created");
		}

		// TODO: check if it's an Service/Repair Request

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final RequestId requestId = RequestId.ofRepoId(getRecord_ID());
		final ProjectId projectId = serviceRepairProjectService.createProjectFromRequest(requestId);

		getResult().setRecordToOpen(ProcessExecutionResult.RecordsToOpen.builder()
				.record(TableRecordReference.of(I_C_Project.Table_Name, projectId))
				.adWindowId(String.valueOf(ServiceRepairProjectService.AD_WINDOW_ID.getRepoId()))
				.target(ProcessExecutionResult.RecordsToOpen.OpenTarget.SingleDocument)
				.targetTab(ProcessExecutionResult.RecordsToOpen.TargetTab.NEW_TAB)
				.build());

		return MSG_OK;
	}
}
