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

package de.metas.project.workorder.step;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
@VisibleForTesting
public class WOProjectStepRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public WOProjectStep getById(@NonNull final WOProjectStepId projectStepId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectStepId.getProjectId().getRepoId())
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_WO_Step_ID, projectStepId.getRepoId())
				.create()
				.firstOptional(I_C_Project_WO_Step.class)
				.map(WOProjectStepRepository::ofRecord)
				.orElseThrow(() -> new AdempiereException("NO WOProjectStep found for projectStepId=" + projectStepId));
	}

	@NonNull
	public Map<WOProjectStepId, WOProjectStep> getMapByIds(@NonNull final Set<WOProjectStepId> projectStepIds)
	{
		final List<I_C_Project_WO_Step> steps = InterfaceWrapperHelper.loadByRepoIdAwares(projectStepIds, I_C_Project_WO_Step.class);

		if (steps.size() != projectStepIds.size())
		{
			throw new AdempiereException("Some of the WOProjectStep could not be found!")
					.appendParametersToMessage()
					.setParameter("projectStepIds", projectStepIds)
					.setParameter("foundRepoIds", steps.stream().map(I_C_Project_WO_Step::getC_Project_WO_Step_ID).map(String::valueOf).collect(Collectors.joining()));
		}

		final Map<Integer, WOProjectStepId> stepRepoId2StepId = Maps.uniqueIndex(projectStepIds, WOProjectStepId::getRepoId);

		return steps.stream()
				.peek(step -> {
					final WOProjectStepId woProjectStepId = stepRepoId2StepId.get(step.getC_Project_WO_Step_ID());

					if (woProjectStepId.getProjectId().getRepoId() != step.getC_Project_ID())
					{
						throw new AdempiereException("C_Project_WO_Step_ID found under a different project than expected!")
								.appendParametersToMessage()
								.setParameter("C_Project_WO_Step_ID", step.getC_Project_WO_Step_ID())
								.setParameter("Expecting C_Project_ID", woProjectStepId.getProjectId())
								.setParameter("Actual C_Project_ID", step.getC_Project_ID());
					}
				})
				.map(WOProjectStepRepository::ofRecord)
				.collect(ImmutableMap.toImmutableMap(WOProjectStep::getWoProjectStepId, Function.identity()));
	}

	public ImmutableList<WOProjectStep> getByIds(final Set<WOProjectStepId> stepIds)
	{
		if (stepIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL
				.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addInArrayFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_WO_Step_ID, stepIds)
				.stream()
				.map(WOProjectStepRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ImmutableList<WOProjectStep> getByProjectId(@NonNull final ProjectId projectId)
	{
		// TODO: replace it with getStepsByProjectId and rename that one to getByProjectId
		return queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectId.getRepoId())
				.create()
				.stream()
				.map(WOProjectStepRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public WOProjectSteps getStepsByProjectId(@NonNull final ProjectId projectId)
	{
		return getByProjectIds(ImmutableSet.of(projectId)).getByProjectId(projectId);
	}

	public WOProjectStepsCollection getByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		if (projectIds.isEmpty())
		{
			return WOProjectStepsCollection.EMPTY;
		}

		final ImmutableListMultimap<ProjectId, WOProjectStep> stepsByProjectId = queryBL
				.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addInArrayFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectIds)
				.stream()
				.map(WOProjectStepRepository::ofRecord)
				.collect(ImmutableListMultimap.toImmutableListMultimap(WOProjectStep::getProjectId, step -> step));

		final ImmutableMap<ProjectId, WOProjectSteps> map = projectIds.stream()
				.map(projectId -> WOProjectSteps.builder()
						.projectId(projectId)
						.steps(stepsByProjectId.get(projectId))
						.build())
				.collect(ImmutableMap.toImmutableMap(WOProjectSteps::getProjectId, steps -> steps));

		return WOProjectStepsCollection.ofMap(map);
	}

	@NonNull
	public List<WOProjectStep> updateAll(@NonNull final Collection<WOProjectStep> stepList)
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

			record.setC_Project_ID(step.getProjectId().getRepoId());
			record.setSeqNo(step.getSeqNo());

			record.setName(step.getName());
			record.setDescription(step.getDescription());
			record.setExternalId(ExternalId.toValue(step.getExternalId()));

			record.setDateEnd(step.getEndDate()
					.map(TimeUtil::asTimestamp)
					.orElse(null));
			record.setDateStart(step.getStartDate()
					.map(TimeUtil::asTimestamp)
					.orElse(null));

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
	public WOProjectStep create(@NonNull final CreateWOProjectStepRequest createWOProjectStepRequest)
	{
		final I_C_Project_WO_Step record = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Step.class);

		record.setC_Project_ID(createWOProjectStepRequest.getProjectId().getRepoId());
		record.setAD_Org_ID(createWOProjectStepRequest.getOrgId().getRepoId());
		record.setSeqNo(createWOProjectStepRequest.getSeqNo());

		record.setName(createWOProjectStepRequest.getName());
		record.setDescription(createWOProjectStepRequest.getDescription());
		record.setExternalId(ExternalId.toValue(createWOProjectStepRequest.getExternalId()));

		record.setDateEnd(TimeUtil.asTimestamp(createWOProjectStepRequest.getDateEnd()));
		record.setDateStart(TimeUtil.asTimestamp(createWOProjectStepRequest.getDateStart()));
		record.setWOPartialReportDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoPartialReportDate()));
		record.setWODeliveryDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getDeliveryDate()));
		record.setWOTargetStartDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoTargetStartDate()));
		record.setWOTargetEndDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoTargetEndDate()));
		record.setWOFindingsReleasedDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoFindingsReleasedDate()));
		record.setWOFindingsCreatedDate(TimeUtil.asTimestamp(createWOProjectStepRequest.getWoFindingsCreatedDate()));

		record.setWOPlannedResourceDurationHours(NumberUtils.asInt(createWOProjectStepRequest.getWoPlannedResourceDurationHours(), 0));
		record.setWOPlannedPersonDurationHours(NumberUtils.asInt(createWOProjectStepRequest.getWoPlannedPersonDurationHours(), 0));

		if (createWOProjectStepRequest.getWoStepStatus() != null)
		{
			record.setWOStepStatus(createWOProjectStepRequest.getWoStepStatus().getCode());
		}

		saveRecord(record);

		return ofRecord(record);
	}

	@NonNull
	public List<WOProjectStep> getByQuery(@NonNull final WOProjectStepQuery query)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_AD_Org_ID, query.getOrgId())
				.addInArrayFilter(I_C_Project_WO_Step.COLUMNNAME_ExternalId, query.getExternalIds())
				.create()
				.stream()
				.map(WOProjectStepRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public int getNextSeqNo(@NonNull final ProjectId projectId)
	{
		final int lastSeqNo = queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectId)
				.create()
				.maxInt(I_C_Project_WO_Step.COLUMNNAME_SeqNo);

		return lastSeqNo > 0
				? lastSeqNo / 10 * 10 + 10
				: 10;
	}

	public void updateStepDateRanges(@NonNull final Map<WOProjectStepId, CalendarDateRange> stepDateRanges)
	{
		final Set<WOProjectStepId> stepIds = stepDateRanges.keySet();
		if (stepIds.isEmpty())
		{
			return;
		}

		final ImmutableMap<WOProjectStepId, I_C_Project_WO_Step> recordsById = queryBL
				.createQueryBuilder(I_C_Project_WO_Step.class)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addInArrayFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_WO_Step_ID, stepIds)
				.create()
				.map(WOProjectStepRepository::extractWOProjectStepId);

		for (final WOProjectStepId stepId : stepIds)
		{
			final I_C_Project_WO_Step record = recordsById.get(stepId);
			if (record == null)
			{
				throw new AdempiereException("No project resource not found for " + stepId);
			}

			final CalendarDateRange dateRange = stepDateRanges.get(stepId);

			updateRecordFromDateRange(record, dateRange);
			InterfaceWrapperHelper.save(record);
		}
	}

	@NonNull
	public static WOProjectStepId extractWOProjectStepId(final I_C_Project_WO_Step record)
	{
		return WOProjectStepId.ofRepoId(record.getC_Project_ID(), record.getC_Project_WO_Step_ID());
	}

	@NonNull
	public static WOProjectStep ofRecord(@NonNull final I_C_Project_WO_Step stepRecord)
	{
		final Instant startDate = TimeUtil.asInstant(stepRecord.getDateStart());
		final Instant endDate = TimeUtil.asInstant(stepRecord.getDateEnd());

		final CalendarDateRange dateRange;
		if (startDate == null || endDate == null)
		{
			dateRange = null;
		}
		else
		{
			dateRange = CalendarDateRange.builder()
					.startDate(startDate)
					.endDate(endDate)
					.build();
		}

		return WOProjectStep.builder()
				.woProjectStepId(extractWOProjectStepId(stepRecord))
				.name(stepRecord.getName())
				.orgId(OrgId.ofRepoId(stepRecord.getAD_Org_ID()))
				.description(stepRecord.getDescription())
				.seqNo(stepRecord.getSeqNo())
				.dateRange(dateRange)
				.externalId(ExternalId.ofOrNull(stepRecord.getExternalId()))
				.woPartialReportDate(TimeUtil.asInstant(stepRecord.getWOPartialReportDate()))
				.woPlannedResourceDurationHours(stepRecord.getWOPlannedResourceDurationHours())
				.deliveryDate(TimeUtil.asInstant(stepRecord.getWODeliveryDate()))
				.woTargetStartDate(TimeUtil.asInstant(stepRecord.getWOTargetStartDate()))
				.woTargetEndDate(TimeUtil.asInstant(stepRecord.getWOTargetEndDate()))
				.woPlannedPersonDurationHours(stepRecord.getWOPlannedPersonDurationHours())
				.woStepStatus(WOStepStatus.ofNullableCode(stepRecord.getWOStepStatus()))
				.woFindingsReleasedDate(TimeUtil.asInstant(stepRecord.getWOFindingsReleasedDate()))
				.woFindingsCreatedDate(TimeUtil.asInstant(stepRecord.getWOFindingsCreatedDate()))
				.build();
	}

	private static void updateRecordFromDateRange(@NonNull final I_C_Project_WO_Step record, @NonNull final CalendarDateRange from)
	{
		record.setDateStart(TimeUtil.asTimestamp(from.getStartDate()));
		record.setDateEnd(TimeUtil.asTimestamp(from.getEndDate()));
	}
}
