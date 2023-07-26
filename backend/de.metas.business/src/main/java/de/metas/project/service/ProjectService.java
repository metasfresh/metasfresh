/*
 * #%L
 * de.metas.business
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

package de.metas.project.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.logging.LogManager;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.project.ProjectAndLineId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectLine;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.service.listeners.CompositeProjectStatusListener;
import de.metas.project.service.listeners.ProjectStatusListener;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.compiere.model.X_C_Project;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService
{
	private static final Logger logger = LogManager.getLogger(ProjectService.class);
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final ProjectTypeRepository projectTypeRepository;
	private final ProjectRepository projectRepository;
	private final ProjectLineRepository projectLineRepository;
	private final CompositeProjectStatusListener projectStatusListeners;

	public ProjectService(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final ProjectTypeRepository projectTypeRepository,
			@NonNull final ProjectRepository projectRepository,
			@NonNull final ProjectLineRepository projectLineRepository,
			@NonNull final Optional<List<ProjectStatusListener>> projectStatusListeners)
	{
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.projectTypeRepository = projectTypeRepository;
		this.projectRepository = projectRepository;
		this.projectLineRepository = projectLineRepository;
		this.projectStatusListeners = CompositeProjectStatusListener.ofList(projectStatusListeners.orElseGet(ImmutableList::of));
		logger.info("projectClosedListeners: {}", projectStatusListeners);
	}

	public I_C_Project getById(@NonNull final ProjectId id)
	{
		return projectRepository.getById(id);
	}

	public ProjectType getProjectTypeById(@NonNull final ProjectTypeId id)
	{
		return projectTypeRepository.getById(id);
	}

	public List<ProjectLine> getLines(@NonNull final ProjectId projectId)
	{
		return projectLineRepository.retrieveLines(projectId);
	}

	public ProjectLine getLineById(@NonNull final ProjectAndLineId projectLineId)
	{
		return projectLineRepository.retrieveLineById(projectLineId);
	}

	public List<ProjectLine> getLinesByOrderId(@NonNull final OrderId orderId)
	{
		return projectLineRepository.retrieveLinesByOrderId(orderId);
	}

	public ProjectId createProject(@NonNull final CreateProjectRequest request)
	{
		final I_C_Project project = InterfaceWrapperHelper.newInstance(I_C_Project.class);
		project.setAD_Org_ID(request.getOrgId().getRepoId());
		project.setCommittedAmt(BigDecimal.ZERO);
		project.setCommittedQty(BigDecimal.ZERO);
		project.setInvoicedAmt(BigDecimal.ZERO);
		project.setInvoicedQty(BigDecimal.ZERO);
		project.setIsSummary(false);
		project.setPlannedAmt(BigDecimal.ZERO);
		project.setPlannedMarginAmt(BigDecimal.ZERO);
		project.setPlannedQty(BigDecimal.ZERO);
		project.setProcessed(false);
		project.setProjectBalanceAmt(BigDecimal.ZERO);
		project.setProjectLineLevel(X_C_Project.PROJECTLINELEVEL_Project);
		project.setProjInvoiceRule(X_C_Project.PROJINVOICERULE_ProductQuantity);

		//
		// Project Type (and related)
		final ProjectTypeId projectTypeId = projectTypeRepository.getFirstIdByProjectCategoryAndOrg(
				request.getProjectCategory(),
				request.getOrgId());
		project.setC_ProjectType_ID(projectTypeId.getRepoId());
		updateFromProjectType(project);

		//
		// BPartner & Location
		project.setC_BPartner_ID(request.getBpartnerAndLocationId().getBpartnerId().getRepoId());
		project.setC_BPartner_Location_ID(request.getBpartnerAndLocationId().getRepoId());
		project.setAD_User_ID(BPartnerContactId.toRepoId(request.getContactId()));

		//
		// Pricing Info
		project.setC_Currency_ID(request.getCurrencyId().getRepoId());
		project.setM_PriceList_Version_ID(request.getPriceListVersionId().getRepoId());
		project.setM_Warehouse_ID(request.getWarehouseId().getRepoId());

		projectRepository.save(project);
		final ProjectId projectId = ProjectId.ofRepoId(project.getC_Project_ID());

		for (final CreateProjectRequest.ProjectLine lineRequest : request.getLines())
		{
			projectLineRepository.createProjectLine(lineRequest, projectId, request.getOrgId());
		}

		return projectId;
	}

	public void updateFromProjectType(@NonNull final I_C_Project projectRecord)
	{
		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoIdOrNull(projectRecord.getC_ProjectType_ID());
		if (projectTypeId == null)
		{
			return;
		}

		final String projectValue = computeNextProjectValue(projectRecord);
		if (projectValue != null)
		{
			projectRecord.setValue(projectValue);
		}
		if (Check.isEmpty(projectRecord.getName()))
		{
			projectRecord.setName(projectValue != null ? projectValue : ".");
		}

		final ProjectType projectType = getProjectTypeById(projectTypeId);
		projectRecord.setProjectCategory(projectType.getProjectCategory().getCode());
	}

	@Nullable
	private String computeNextProjectValue(final I_C_Project projectRecord)
	{
		return documentNoBuilderFactory
				.createValueBuilderFor(projectRecord)
				.setFailOnError(false)
				.build();
	}

	public void createProjectLine(@NonNull final CreateProjectLineRequest request)
	{
		projectLineRepository.createProjectLine(request);
	}

	public ProjectLine changeProjectLine(@NonNull final ChangeProjectLineRequest request)
	{
		return projectLineRepository.changeProjectLine(
				request.getProjectLineId(),
				projectLine -> changeProjectLine(request, projectLine));

	}

	private void changeProjectLine(final ChangeProjectLineRequest request, final ProjectLine projectLine)
	{
		if (request.getCommittedQtyToAdd() != null)
		{
			projectLine.addCommittedQty(request.getCommittedQtyToAdd());
		}
	}

	public void linkToOrderLine(
			@NonNull final ProjectAndLineId projectLineId,
			@NonNull final OrderAndLineId orderLineId)
	{
		projectLineRepository.linkToOrderLine(projectLineId, orderLineId);
	}

	public boolean isClosed(@NonNull final ProjectId projectId)
	{
		final I_C_Project projectRecord = getById(projectId);
		return projectRecord.isProcessed();
	}

	public void closeProject(@NonNull final ProjectId projectId)
	{
		final I_C_Project projectRecord = getById(projectId);
		if (projectRecord.isProcessed())
		{
			throw new AdempiereException("Project already closed: " + projectId);
		}

		projectStatusListeners.onBeforeClose(projectRecord);

		projectLineRepository.markLinesAsProcessed(projectId);

		projectRecord.setProcessed(true);
		InterfaceWrapperHelper.saveRecord(projectRecord);

		projectStatusListeners.onAfterClose(projectRecord);
	}

	public void uncloseProject(@NonNull final ProjectId projectId)
	{
		final I_C_Project projectRecord = getById(projectId);
		if (!projectRecord.isProcessed())
		{
			throw new AdempiereException("Project not closed: " + projectId);
		}

		projectStatusListeners.onBeforeUnClose(projectRecord);

		projectLineRepository.markLinesAsNotProcessed(projectId);

		projectRecord.setProcessed(false);
		InterfaceWrapperHelper.saveRecord(projectRecord);

		projectStatusListeners.onAfterUnClose(projectRecord);
	}

}
