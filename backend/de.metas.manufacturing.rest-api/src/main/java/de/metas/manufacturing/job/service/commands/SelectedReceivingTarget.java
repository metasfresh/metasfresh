/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.manufacturing.job.service.commands;

import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonHUQRCodeTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonLUReceivingTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonTUReceivingTarget;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
public class SelectedReceivingTarget
{
	@Nullable JsonLUReceivingTarget luReceivingTarget;
	@Nullable JsonTUReceivingTarget tuReceivingTarget;

	@Builder
	public SelectedReceivingTarget(
			@Nullable final JsonLUReceivingTarget luReceivingTarget,
			@Nullable final JsonTUReceivingTarget tuReceivingTarget)
	{
		Check.assumeSingleNonNull("Exactly one target must be set!", tuReceivingTarget, luReceivingTarget);

		this.luReceivingTarget = luReceivingTarget;
		this.tuReceivingTarget = tuReceivingTarget;
	}

	@Nullable
	public JsonNewLUTarget getReceiveToNewLU()
	{
		return Optional.ofNullable(luReceivingTarget)
				.map(JsonLUReceivingTarget::getNewLU)
				.orElse(null);
	}

	@Nullable
	public JsonHUQRCodeTarget getReceiveToQRCode()
	{
		return Optional.ofNullable(luReceivingTarget)
				.map(JsonLUReceivingTarget::getExistingLU)
				.orElse(null);
	}


	@Nullable
	public JsonNewTUTarget getReceiveToNewTU()
	{
		return Optional.ofNullable(tuReceivingTarget)
				.map(JsonTUReceivingTarget::getNewTU)
				.orElse(null);
	}
}
