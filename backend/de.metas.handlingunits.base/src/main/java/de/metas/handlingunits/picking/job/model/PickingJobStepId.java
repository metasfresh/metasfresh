/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PickingJobStepId implements RepoIdAware
{
	int repoId;

	private PickingJobStepId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Picking_Job_Step_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public String getAsString() {return String.valueOf(getRepoId());}

	public static int toRepoId(final PickingJobStepId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@JsonCreator
	public static PickingJobStepId ofRepoId(final int repoId)
	{
		return new PickingJobStepId(repoId);
	}

	public static PickingJobStepId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PickingJobStepId(repoId) : null;
	}

	@Nullable
	public static PickingJobStepId ofNullableString(@Nullable final String string)
	{
		return StringUtils.trimBlankToOptional(string)
				.map(PickingJobStepId::ofString)
				.orElse(null);
	}

	@NonNull
	public static PickingJobStepId ofString(@NonNull final String string)
	{
		try
		{
			return ofRepoId(Integer.parseInt(string));
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid id string: `" + string + "`", ex);
		}
	}

	public static boolean equals(@Nullable final PickingJobStepId id1, @Nullable final PickingJobStepId id2) {return Objects.equals(id1, id2);}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(I_M_Picking_Job_Step.Table_Name, repoId);
	}
}
