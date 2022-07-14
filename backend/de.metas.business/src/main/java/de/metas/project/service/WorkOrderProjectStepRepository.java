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

package de.metas.project.service;

import com.google.common.collect.ImmutableList;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectStepRepository;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class WorkOrderProjectStepRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final WOProjectStepRepository woProjectStepRepository;

	public WorkOrderProjectStepRepository(@NonNull final WOProjectStepRepository woProjectStepRepository)
	{
		this.woProjectStepRepository = woProjectStepRepository;
	}

	@NonNull
	public Optional<WOProjectStep> getOptionalById(@NonNull final WOProjectStepId id)
	{
		return Optional.ofNullable(getRecordById(id))
				.map(WOProjectStep::ofRecord);
	}

	@Nullable
	public I_C_Project_WO_Step getRecordById(@NonNull final WOProjectStepId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_Project_WO_Step.class);
	}

	@NonNull
	public List<WOProjectStep> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addEqualsFilter(I_C_Project_WO_Step.COLUMNNAME_C_Project_ID, projectId.getRepoId())
				.create()
				.stream()
				.map(WOProjectStep::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public WOProjectStep update(@NonNull final WOProjectStep step)
	{
		final I_C_Project_WO_Step stepRecord = getRecordById(step.getWOProjectStepIdNonNull());

		if (stepRecord == null)
		{
			throw new AdempiereException("No C_Project_WO_Step record found for id.")
					.appendParametersToMessage()
					.setParameter("WOProjectStepId", step);
		}

		return save(stepRecord, step);
	}

	@NonNull
	public WOProjectStep create(@NonNull final WOProjectStep stepData)
	{
		final I_C_Project_WO_Step record = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Step.class);

		return save(record, stepData);
	}

	@NonNull
	private WOProjectStep save(
			@NonNull final I_C_Project_WO_Step stepRecord,
			@NonNull final WOProjectStep stepData)
	{

		if (Check.isNotBlank(stepData.getName()))
		{
			stepRecord.setName(stepData.getName());
		}
		if (stepData.getSeqNo() == null)
		{
			stepRecord.setSeqNo(woProjectStepRepository.getNextSeqNo(stepData.getProjectId()));
		}
		else
		{
			stepRecord.setSeqNo(stepData.getSeqNo());
		}

		stepRecord.setDateEnd(TimeUtil.asTimestamp(stepData.getDateEnd()));
		stepRecord.setDateStart(TimeUtil.asTimestamp(stepData.getDateStart()));
		stepRecord.setDescription(stepData.getDescription());
		stepRecord.setC_Project_ID(stepData.getProjectId().getRepoId());

		saveRecord(stepRecord);

		return WOProjectStep.ofRecord(stepRecord);

	}
}
