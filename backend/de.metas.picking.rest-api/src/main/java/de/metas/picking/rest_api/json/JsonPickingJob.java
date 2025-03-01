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

package de.metas.picking.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.job.model.PickingJob;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

@Value
@Builder
@Jacksonized
public class JsonPickingJob
{
	@NonNull JsonCompleteStatus completeStatus;
	@Nullable JsonLUPickingTarget pickTarget;
	@Nullable JsonTUPickingTarget tuPickTarget;
	@NonNull List<JsonPickingJobLine> lines;
	@NonNull List<JsonPickFromAlternative> pickFromAlternatives;

	public static JsonPickingJob of(
			@NonNull final PickingJob pickingJob,
			@NonNull final Function<PickingJob, List<JsonPickingJobLine>> getJsonPickingLines)
	{
		return builder()
				.completeStatus(JsonCompleteStatus.of(pickingJob.getProgress()))
				.pickTarget(pickingJob.getLuPickTarget().map(JsonLUPickingTarget::of).orElse(null))
				.tuPickTarget(pickingJob.getTuPickTarget().map(JsonTUPickingTarget::of).orElse(null))
				.lines(getJsonPickingLines.apply(pickingJob))
				.pickFromAlternatives(pickingJob.getPickFromAlternatives()
						.stream()
						.map(JsonPickFromAlternative::of)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
