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

package de.metas.costrevaluation;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * M_CostRevaluationLine_ID
 */
@Value
public class CostRevaluationLineId implements RepoIdAware
{
	int repoId;

	@NonNull CostRevaluationId costRevaluationId;

	public static CostRevaluationLineId ofRepoId(@NonNull final CostRevaluationId costRevaluationId, final int costRevaluationLineId)
	{
		return new CostRevaluationLineId(costRevaluationId, costRevaluationLineId);
	}

	public static CostRevaluationLineId ofRepoId(final int costRevaluationId, final int costRevaluationLineId)
	{
		return new CostRevaluationLineId(CostRevaluationId.ofRepoId(costRevaluationId), costRevaluationLineId);
	}

	public static CostRevaluationLineId ofRepoIdOrNull(
			@Nullable final CostRevaluationId costRevaluationId,
			final int costRevaluationLineId)
	{
		return costRevaluationId != null && costRevaluationLineId > 0 ? ofRepoId(costRevaluationId, costRevaluationLineId) : null;
	}

	private CostRevaluationLineId(@NonNull final CostRevaluationId costRevaluationId, final int costRevaluationLineId)
	{
		this.repoId = Check.assumeGreaterThanZero(costRevaluationLineId, "M_CostRevaluationLine_ID");
		this.costRevaluationId = costRevaluationId;
	}

}
