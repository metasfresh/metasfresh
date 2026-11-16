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

package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class JsonNewTUTargetList
{
	@NonNull List<JsonNewTUTarget> values;
	@Nullable String emptyReason;

	public static JsonNewTUTargetList ofList(@NonNull final List<JsonNewTUTarget> values)
	{
		return builder().values(values).build();
	}

	public static JsonNewTUTargetList emptyBecause(@NonNull final String emptyReason)
	{
		Check.assumeNotEmpty(emptyReason, "emptyReason");
		return builder().emptyReason(emptyReason).build();
	}

	@Builder
	@Jacksonized
	private JsonNewTUTargetList(
			@Nullable final List<JsonNewTUTarget> values,
			@Nullable final String emptyReason)
	{
		this.values = values != null ? ImmutableList.copyOf(values) : ImmutableList.of();
		this.emptyReason = emptyReason;
	}
}
