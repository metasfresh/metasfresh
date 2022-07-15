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

import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class WOProjectStep
{
	@Nullable
	@Getter
	WOProjectStepId woProjectStepId;

	@NonNull
	String name;

	@With
	@Nullable
	ProjectId projectId;

	@Nullable
	String description;

	@Nullable
	Integer seqNo;

	@Nullable
	LocalDate dateStart;

	@Nullable
	LocalDate dateEnd;

	@Nullable
	ExternalId externalId;
	
	@Singular
	List<WOProjectResource> projectResources;

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

}