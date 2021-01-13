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

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.servicerepair.project.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.hu_to_issue.HUsToIssueViewContext;
import de.metas.servicerepair.project.hu_to_issue.HUsToIssueViewFactory;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class HUsToIssueView_IssueHUs extends HUEditorProcessTemplate
{
	private final ServiceRepairProjectService projectService = SpringContextHolder.instance.getBean(ServiceRepairProjectService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return super.checkPreconditionsApplicable();
	}

	@Override
	protected String doIt()
	{
		final HUsToIssueViewContext husToIssueViewContext = getHusToIssueViewContext();
		final ServiceRepairProjectTaskId taskId = husToIssueViewContext.getTaskId();
		final ImmutableSet<HuId> huIds = streamSelectedHUIds(HUEditorRowFilter.Select.ONLY_TOPLEVEL).collect(ImmutableSet.toImmutableSet());

		projectService.reserveSparePartsFromHUs(taskId, huIds);

		return MSG_OK;
	}

	private HUsToIssueViewContext getHusToIssueViewContext()
	{
		return getView()
				.getParameter(HUsToIssueViewFactory.PARAM_HUsToIssueViewContext, HUsToIssueViewContext.class)
				.orElseThrow(() -> new AdempiereException("No view context"));
	}
}
