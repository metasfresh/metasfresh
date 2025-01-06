/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.rest_api.json;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonTUPickingTarget
{
	@NonNull String id;
	@NonNull String caption;
	@NonNull HuPackingInstructionsId tuPIId;
	boolean isDefault;

	public static JsonTUPickingTarget of(@NonNull final TUPickingTarget target)
	{
		return builder()
				.id(target.getId())
				.caption(target.getCaption())
				.tuPIId(target.getTuPIId())
				.isDefault(target.isDefaultPacking())
				.build();
	}

	public TUPickingTarget unbox()
	{
		return TUPickingTarget.builder()
				.caption(caption)
				.tuPIId(tuPIId)
				.build();
	}
}
