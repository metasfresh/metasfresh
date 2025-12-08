/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.picking.pickingslot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.order.OrderId;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
import lombok.NonNull;

import java.util.Optional;
import java.util.function.Function;

/**
 * Immutable pojo that contains the HU editor as retrieved from {@link HUEditorViewRepository} plus the the {@code processed} value from the respective picking candidate.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
// the fully qualified annotations are a workaround for a javac problem with maven
@lombok.Value
@lombok.AllArgsConstructor
class PickedHUEditorRow
{
	@NonNull
	public static PickedHUEditorRow ofProcessedRow(@NonNull final HUEditorRow huEditorRow)
	{
		return new PickedHUEditorRow(huEditorRow, true, ImmutableMap.of());
	}

	@NonNull
	public static PickedHUEditorRow ofUnprocessedRow(
			@NonNull final HUEditorRow huEditorRow,
			@NonNull final ImmutableMap<HuId, ImmutableSet<OrderId>> huIds2OpenPickingOrderIds)
	{
		return new PickedHUEditorRow(huEditorRow, false, huIds2OpenPickingOrderIds);
	}

	@NonNull HUEditorRow huEditorRow;
	boolean processed;
	@NonNull ImmutableMap<HuId, ImmutableSet<OrderId>> huIds2OpenPickingOrderIds;

	@NonNull
	public HuId getHUId()
	{
		return huEditorRow.getHuId();
	}

	@NonNull
	public ImmutableMap<HuId, ImmutableSet<OrderId>> getPickingOrderIdsFor(@NonNull final ImmutableSet<HuId> huIds)
	{
		return huIds.stream()
				.collect(ImmutableMap.toImmutableMap(Function.identity(),
													 huId -> Optional.ofNullable(huIds2OpenPickingOrderIds.get(huId))
															 .orElse(ImmutableSet.of())));
	}

	public boolean containsHUId(@NonNull final HuId packToHUId)
	{
		return huEditorRow.getAllHuIds().contains(packToHUId);
	}
}
