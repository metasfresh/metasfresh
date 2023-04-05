/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.project.step.process;

import com.google.common.collect.ImmutableList;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.project.ProjectId;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.ui.web.project.step.ResolveReservationViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.RelatedProcessDescriptorUtil;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class C_Project_WO_Step_ResolveReservationView_Launcher extends JavaProcess implements IProcessPrecondition
{
	public static final String VIEW_FACTORY_PARAM_WO_PROJECT_ID = "WOProjectID";

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final WOProjectService woProjectService = SpringContextHolder.instance.getBean(WOProjectService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final ProjectId projectId = ProjectId.ofRepoId(context.getSingleSelectedRecordId());
		final ProjectId parentProjectId = woProjectService.getParentProjectId(projectId)
				.orElse(null);

		if (parentProjectId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("ParentProject is missing");
		}

		if (woProjectService.isReservationOrder(projectId) && !woProjectService.hasResourcesAssigned(projectId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("ProjectWOSteps are missing!");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		try
		{
			final ImmutableList<RelatedProcessDescriptor> relatedProcesses = ImmutableList.of(RelatedProcessDescriptorUtil.viewQuickAction(adProcessDAO, C_Project_WO_Step_ResolveHours.class),
																							  RelatedProcessDescriptorUtil.viewQuickAction(adProcessDAO, C_Project_WO_Step_UndoResolution.class));

			final ViewId viewId = viewsFactory.createView(CreateViewRequest.builder(ResolveReservationViewFactory.WINDOWID)
																  .setParameter(VIEW_FACTORY_PARAM_WO_PROJECT_ID, ProjectId.ofRepoId(getRecord_ID()))
																  .addAdditionalRelatedProcessDescriptors(relatedProcesses)
																  .build())
					.getViewId();

			getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
												   .viewId(viewId.getViewId())
												   .target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
												   .build());
		}
		catch (final Exception exception)
		{
			log.error("Error encountered while launching ResolveReservationModal:", exception);

			throw AdempiereException.wrapIfNeeded(exception);
		}

		return MSG_OK;
	}
}
