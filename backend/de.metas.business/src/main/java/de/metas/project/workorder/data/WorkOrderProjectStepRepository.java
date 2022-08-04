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

package de.metas.project.workorder.data;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectStepRepository;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
@VisibleForTesting
public class WorkOrderProjectStepRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WOProjectStepRepository woProjectStepRepository;
	private final WorkOrderProjectResourceRepository workOrderProjectResourceRepository;

	public WorkOrderProjectStepRepository(
			@NonNull final WOProjectStepRepository woProjectStepRepository,
			@NonNull final WorkOrderProjectResourceRepository workOrderProjectResourceRepository)
	{
		this.woProjectStepRepository = woProjectStepRepository;
		this.workOrderProjectResourceRepository = workOrderProjectResourceRepository;
	}

	@NonNull
	public List<WOProjectStep> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectId.getRepoId())
				.create()
				.stream()
				.map(this::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public WOProjectStep save(@NonNull final WOProjectStep stepData)
	{
		final WOProjectStepId existingWoProjectStepId;
		if (stepData.getWoProjectStepId() != null)
		{
			existingWoProjectStepId = stepData.getWOProjectStepIdNonNull();
		}
		else if (stepData.getExternalId() != null)
		{
			existingWoProjectStepId = getByProjectId(stepData.getProjectIdNonNull())
					.stream()
					.filter(s -> Objects.equals(s.getExternalId(), stepData.getExternalId()))
					.findAny()
					.map(WOProjectStep::getWoProjectStepId)
					.orElse(null);
		}
		else
		{
			existingWoProjectStepId = null;
		}

		final I_C_Project_WO_Step stepRecord = InterfaceWrapperHelper.loadOrNew(existingWoProjectStepId, I_C_Project_WO_Step.class);

		if (Check.isNotBlank(stepData.getName()))
		{
			stepRecord.setName(stepData.getName());
		}

		final ProjectId projectId = stepData.getProjectIdNonNull();
		if (stepData.getSeqNo() == null)
		{
			stepRecord.setSeqNo(woProjectStepRepository.getNextSeqNo(projectId));
		}
		else
		{
			stepRecord.setSeqNo(stepData.getSeqNo());
		}

		stepRecord.setDateEnd(TimeUtil.asTimestamp(stepData.getDateEnd()));
		stepRecord.setDateStart(TimeUtil.asTimestamp(stepData.getDateStart()));
		stepRecord.setDescription(stepData.getDescription());
		stepRecord.setC_Project_ID(projectId.getRepoId());

		stepRecord.setExternalId(ExternalId.toValue(stepData.getExternalId()));

		stepRecord.setWOPartialReportDate(TimeUtil.asTimestamp(stepData.getWoPartialReportDate()));
		stepRecord.setWOPlannedResourceDurationHours(NumberUtils.asInt(stepData.getWoPlannedResourceDurationHours(), -1));
		stepRecord.setWODeliveryDate(TimeUtil.asTimestamp(stepData.getDeliveryDate()));
		stepRecord.setWOTargetStartDate(TimeUtil.asTimestamp(stepData.getWoTargetStartDate()));
		stepRecord.setWOTargetEndDate(TimeUtil.asTimestamp(stepData.getWoTargetEndDate()));
		stepRecord.setWOPlannedPersonDurationHours(NumberUtils.asInt(stepData.getWoPlannedPersonDurationHours(), -1));
		stepRecord.setWOFindingsReleasedDate(TimeUtil.asTimestamp(stepData.getWoFindingsReleasedDate()));
		stepRecord.setWOFindingsCreatedDate(TimeUtil.asTimestamp(stepData.getWoFindingsCreatedDate()));

		if (stepData.getWoStepStatus() != null)
		{
			stepRecord.setWOStepStatus(stepData.getWoStepStatus().getCode());
		}

		saveRecord(stepRecord);

		final WOProjectStepId woProjectStepId = WOProjectStepId.ofRepoId(projectId, stepRecord.getC_Project_WO_Step_ID());
		for (final WOProjectResource woProjectResource : stepData.getProjectResources())
		{
			workOrderProjectResourceRepository.save(woProjectResource.withWoProjectStepId(woProjectStepId));
		}

		return ofRecord(stepRecord);
	}

	@NonNull
	private WOProjectStep ofRecord(@NonNull final I_C_Project_WO_Step stepRecord)
	{
		final OrgId orgId = OrgId.ofRepoId(stepRecord.getAD_Org_ID());

		final ProjectId projectId = ProjectId.ofRepoId(stepRecord.getC_Project_ID());

		final WOProjectStepId woProjectStepId = WOProjectStepId.ofRepoId(
				projectId,
				stepRecord.getC_Project_WO_Step_ID());

		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		return WOProjectStep.builder()
				.woProjectStepId(woProjectStepId)
				.name(stepRecord.getName())
				.description(stepRecord.getDescription())
				.seqNo(stepRecord.getSeqNo())
				.dateStart(TimeUtil.asInstant(stepRecord.getDateStart(), timeZone))
				.dateEnd(TimeUtil.asInstant(stepRecord.getDateEnd(), timeZone))
				.projectId(projectId)
				.externalId(ExternalId.ofOrNull(stepRecord.getExternalId()))
				.woPartialReportDate(TimeUtil.asInstant(stepRecord.getWOPartialReportDate(), timeZone))
				.woPlannedResourceDurationHours(stepRecord.getWOPlannedResourceDurationHours())
				.deliveryDate(TimeUtil.asInstant(stepRecord.getWODeliveryDate(), timeZone))
				.woTargetStartDate(TimeUtil.asInstant(stepRecord.getWOTargetStartDate(), timeZone))
				.woTargetEndDate(TimeUtil.asInstant(stepRecord.getWOTargetEndDate(), timeZone))
				.woPlannedPersonDurationHours(stepRecord.getWOPlannedPersonDurationHours())
				.woStepStatus(WOStepStatus.ofNullableCode(stepRecord.getWOStepStatus()))
				.woFindingsReleasedDate(TimeUtil.asInstant(stepRecord.getWOFindingsReleasedDate(), timeZone))
				.woFindingsCreatedDate(TimeUtil.asInstant(stepRecord.getWOFindingsCreatedDate(), timeZone))
				.projectResources(workOrderProjectResourceRepository.getByStepId(woProjectStepId))
				.build();
	}
}
