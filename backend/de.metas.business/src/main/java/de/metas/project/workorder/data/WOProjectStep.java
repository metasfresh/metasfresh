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

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepRequest;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectStepId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
public class WOProjectStep
{
	@Nullable
	@Getter(AccessLevel.NONE)
	WOProjectStepId woProjectStepId;

	@NonNull
	String name;

	@NonNull
	ProjectId projectId;

	@Nullable
	String description;

	@Nullable
	Integer seqNo;

	@Nullable
	LocalDate dateStart;

	@Nullable
	LocalDate dateEnd;

	@NonNull
	public WOProjectStepId getWOProjectStepIdNonNull()
	{
		if (woProjectStepId == null)
		{
			throw new AdempiereException("WOProjectStepId cannot be null at this stage!");
		}
		return woProjectStepId;
	}

	@NonNull
	public Integer getSeqNoNonNull()
	{
		if (seqNo == null)
		{
			throw new AdempiereException("WOProjectStep SeqNo cannot be null at this stage!");
		}
		return seqNo;
	}

	@NonNull
	public static WOProjectStep ofRecord(@NonNull final I_C_Project_WO_Step stepRecord)
	{
		return WOProjectStep.builder()
				.woProjectStepId(WOProjectStepId.ofRepoId(stepRecord.getC_Project_WO_Step_ID()))
				.name(stepRecord.getName())
				.description(stepRecord.getDescription())
				.seqNo(stepRecord.getSeqNo())
				.dateStart(TimeUtil.asLocalDate(stepRecord.getDateStart()))
				.dateEnd(TimeUtil.asLocalDate(stepRecord.getDateEnd()))
				.projectId(ProjectId.ofRepoId(stepRecord.getC_Project_ID()))
				.build();
	}

	@NonNull
	public static WOProjectStep fromJson(@NonNull final JsonWorkOrderStepRequest jsonStep, @NonNull final ProjectId projectId)
	{
		return WOProjectStep.builder()
				.woProjectStepId(WOProjectStepId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(jsonStep.getStepId())))
				.name(jsonStep.getName())
				.description(jsonStep.getDescription())
				.dateEnd(jsonStep.getDateEnd())
				.dateStart(jsonStep.getDateStart())
				.seqNo(jsonStep.getSeqNo())
				.projectId(projectId)
				.build();
	}
}