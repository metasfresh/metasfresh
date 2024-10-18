/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.handlingunits.HuPackingInstructionsId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class TUPickingTarget
{
	@NonNull String id;
	@NonNull String caption;
	@NonNull HuPackingInstructionsId tuPIId;
	boolean isDefaultPacking;

	@Builder(toBuilder = true)
	private TUPickingTarget(
			@NonNull final String caption,
			@NonNull final HuPackingInstructionsId tuPIId,
			final boolean isDefaultPacking)
	{
		this.caption = caption;
		this.tuPIId = tuPIId;
		this.id = "new-" + tuPIId.getRepoId();
		this.isDefaultPacking = isDefaultPacking;
	}

	public static boolean equals(@Nullable final TUPickingTarget o1, @Nullable final TUPickingTarget o2)
	{
		return Objects.equals(o1, o2);
	}

	@NonNull
	public static TUPickingTarget ofPackingInstructions(@NonNull final HuPackingInstructionsId tuPIId, @NonNull final String caption)
	{
		return builder().tuPIId(tuPIId).caption(caption).build();
	}
}
