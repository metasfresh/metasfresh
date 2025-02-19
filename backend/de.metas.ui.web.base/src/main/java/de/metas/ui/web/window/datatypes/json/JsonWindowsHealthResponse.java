/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class JsonWindowsHealthResponse
{
	String took;
	int countTotal;
	int countErrors;
	int countSkipped;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<Entry> errors;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String errorWindowIds;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<Entry> skipped;

	@Builder
	@Jacksonized
	private JsonWindowsHealthResponse(
			@Nullable final String took,
			final int countTotal,
			@Nullable final List<Entry> errors,
			@Nullable final List<Entry> skipped)
	{
		this.took = took;
		this.errors = errors != null ? errors : ImmutableList.of();
		this.skipped = skipped != null ? skipped : ImmutableList.of();
		this.countTotal = countTotal;
		this.countErrors = this.errors.size();
		this.countSkipped = this.skipped.size();

		this.errorWindowIds = this.errors.stream()
				.map(entry -> entry.getWindowId().toJson())
				.collect(Collectors.joining(","));
	}

	@Value
	@Builder
	@Jacksonized
	public static class Entry
	{
		@NonNull WindowId windowId;
		@Nullable String windowName;

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		@Nullable String errorMessage;

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		@Nullable JsonErrorItem error;
	}
}