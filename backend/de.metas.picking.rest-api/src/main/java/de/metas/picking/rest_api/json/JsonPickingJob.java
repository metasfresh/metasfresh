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
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonPickingJob
{
	@NonNull PickingJobAggregationType aggregationType;
	@NonNull JsonCompleteStatus completeStatus;
	@Nullable JsonDisplayableQRCode pickFromHU;
	boolean isLineLevelPickTarget;
	@Nullable JsonLUPickingTarget luPickingTarget;
	@Nullable JsonTUPickingTarget tuPickingTarget;
	@NonNull List<JsonPickingJobLine> lines;
	@NonNull List<JsonPickFromAlternative> pickFromAlternatives;

	@NonNull JsonRejectReasonsList qtyRejectedReasons;

	boolean isAllowSkippingRejectedReason;
	boolean isPickWithNewLU;
	boolean isAllowNewTU;
	boolean isShowPromptWhenOverPicking;

	public static JsonPickingJobBuilder builderFrom(@NonNull final PickingJob pickingJob)
	{
		return builder()
				.aggregationType(pickingJob.getAggregationType())
				.completeStatus(JsonCompleteStatus.of(pickingJob.getProgress()))
				.pickFromHU(pickingJob.getPickFromHU().map(HUInfo::toQRCodeRenderedJson).orElse(null))
				.isLineLevelPickTarget(pickingJob.isLineLevelPickTarget())
				.luPickingTarget(pickingJob.getLuPickingTarget(null).map(JsonLUPickingTarget::of).orElse(null))
				.tuPickingTarget(pickingJob.getTuPickingTarget(null).map(JsonTUPickingTarget::of).orElse(null))
				.pickFromAlternatives(pickingJob.getPickFromAlternatives()
						.stream()
						.map(JsonPickFromAlternative::of)
						.collect(ImmutableList.toImmutableList()))
				;
	}
}
