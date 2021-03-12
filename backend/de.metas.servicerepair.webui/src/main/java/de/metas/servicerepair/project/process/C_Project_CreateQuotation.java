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

package de.metas.servicerepair.project.process;

import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.servicerepair.project.service.requests.CreateQuotationFromProjectRequest;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;

public class C_Project_CreateQuotation
		extends ServiceOrRepairProjectBasedProcess
		implements IProcessPrecondition
{
	@Param(parameterName = "ServiceRepair_Product_ID", mandatory = true)
	private ProductId serviceProductId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (projectId == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		return checkIsServiceOrRepairProject(projectId);
	}

	@Override
	protected String doIt()
	{
		final ProjectId projectId = getProjectId();
		checkIsServiceOrRepairProject(projectId).throwExceptionIfRejected();

		final OrderId quotationId = projectService.createQuotationFromProject(CreateQuotationFromProjectRequest.builder()
				.projectId(projectId)
				.serviceProductId(serviceProductId)
				.build());

		setRecordToOpen(quotationId);

		return MSG_OK;
	}

	private void setRecordToOpen(final OrderId quotationId)
	{
		getResult().setRecordToOpen(ProcessExecutionResult.RecordsToOpen.builder()
				.record(TableRecordReference.of(I_C_Order.Table_Name, quotationId))
				.target(ProcessExecutionResult.RecordsToOpen.OpenTarget.SingleDocument)
				.targetTab(ProcessExecutionResult.RecordsToOpen.TargetTab.NEW_TAB)
				.build());
	}
}
