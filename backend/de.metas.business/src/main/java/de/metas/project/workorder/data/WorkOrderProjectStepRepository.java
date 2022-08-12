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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
@VisibleForTesting
public class WorkOrderProjectStepRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@NonNull
	public Optional<WOProjectStep> getById(@NonNull final WOProjectStepId projectStepId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectStepId.getProjectId().getRepoId())
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_WO_Step_ID, projectStepId.getRepoId())
				.create()
				.firstOptional(I_C_Project_WO_Step.class)
				.map(this::ofRecord);
	}

	@NonNull
	public List<WOProjectStep> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectId.getRepoId())
				.create()
				.stream()
				.map(this::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<WOProjectStep> updateAll(@NonNull final List<WOProjectStep> stepList)
	{
		final Set<Integer> stepIds = stepList.stream()
				.map(WOProjectStep::getWoProjectStepId)
				.map(WOProjectStepId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final List<I_C_Project_WO_Step> stepRecords = InterfaceWrapperHelper.loadByIds(stepIds, I_C_Project_WO_Step.class);

		final Map<Integer, I_C_Project_WO_Step> stepId2Record = Maps.uniqueIndex(stepRecords, I_C_Project_WO_Step::getC_Project_WO_Step_ID);

		final ImmutableList.Builder<WOProjectStep> savedSteps = ImmutableList.builder();

		for (final WOProjectStep step : stepList)
		{
			final I_C_Project_WO_Step record = stepId2Record.get(step.getWoProjectStepId().getRepoId());

			if (record == null)
			{
				throw new AdempiereException("Missing C_Project_WO_Step for repoId:" + step.getWoProjectStepId());
			}

			record.setSeqNo(step.getSeqNo());
			record.setName(step.getName());
			record.setDateEnd(TimeUtil.asTimestamp(step.getDateEnd()));
			record.setDateStart(TimeUtil.asTimestamp(step.getDateStart()));
			record.setDescription(step.getDescription());
			record.setC_Project_ID(step.getProjectId().getRepoId());

			record.setExternalId(ExternalId.toValue(step.getExternalId()));

			record.setWOPartialReportDate(TimeUtil.asTimestamp(step.getWoPartialReportDate()));
			record.setWOPlannedResourceDurationHours(NumberUtils.asInt(step.getWoPlannedResourceDurationHours(), -1));
			record.setWODeliveryDate(TimeUtil.asTimestamp(step.getDeliveryDate()));
			record.setWOTargetStartDate(TimeUtil.asTimestamp(step.getWoTargetStartDate()));
			record.setWOTargetEndDate(TimeUtil.asTimestamp(step.getWoTargetEndDate()));
			record.setWOPlannedPersonDurationHours(NumberUtils.asInt(step.getWoPlannedPersonDurationHours(), -1));
			record.setWOFindingsReleasedDate(TimeUtil.asTimestamp(step.getWoFindingsReleasedDate()));
			record.setWOFindingsCreatedDate(TimeUtil.asTimestamp(step.getWoFindingsCreatedDate()));

			if (step.getWoStepStatus() != null)
			{
				record.setWOStepStatus(step.getWoStepStatus().getCode());
			}

			saveRecord(record);

			savedSteps.add(ofRecord(record));
		}

		return savedSteps.build();
	}

	@NonNull
	public List<WOProjectStep> createAll(@NonNull final List<CreateWOProjectStepRequest> createWOProjectStepRequests)
	{
		final ImmutableList.Builder<WOProjectStep> savedSteps = ImmutableList.builder();

		for (final CreateWOProjectStepRequest createWOProjectStepRequest : createWOProjectStepRequests)
		{
			final I_C_Project_WO_Step record = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Step.class);

			record.setSeqNo(createWOProjectStepRequest.getSeqNo());
			record.setName(createWOProjectStepRequest.getName());
			record.setDateEnd(TimeUtil.asTimestamp(createWOProjectStepRequest.getDateEnd()));
			record.setDateStart(TimeUtil.asTimestamp(createWOProjectStepRequest.getDateStart()));
			record.setDescription(createWOProjectStepRequest.getDescription());
			record.setC_Project_ID(createWOProjectStepRequest.getProjectId().getRepoId());

			record.setExternalId(ExternalId.toValue(createWOProjectStepRequest.getExternalId()));

			record.setWOPartialReportDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoPartialReportDate()));
			record.setWOPlannedResourceDurationHours(NumberUtils.asInt(createWOProjectStepRequest.getWoPlannedResourceDurationHours(), -1));
			record.setWODeliveryDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getDeliveryDate()));
			record.setWOTargetStartDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoTargetStartDate()));
			record.setWOTargetEndDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoTargetEndDate()));
			record.setWOPlannedPersonDurationHours(NumberUtils.asInt(createWOProjectStepRequest.getWoPlannedPersonDurationHours(), -1));
			record.setWOFindingsReleasedDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoFindingsReleasedDate()));
			record.setWOFindingsCreatedDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoFindingsCreatedDate()));

			if (createWOProjectStepRequest.getWoStepStatus() != null)
			{
				record.setWOStepStatus(createWOProjectStepRequest.getWoStepStatus().getCode());
			}

			saveRecord(record);

			savedSteps.add(ofRecord(record));
		}

		return savedSteps.build();
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
				.orgId(OrgId.ofRepoId(stepRecord.getAD_Org_ID()))
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
				.build();
	}
}
