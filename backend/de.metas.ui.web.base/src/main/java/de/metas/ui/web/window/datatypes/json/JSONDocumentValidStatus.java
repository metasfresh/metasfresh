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

package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.ui.web.window.model.DocumentValidStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JSONDocumentValidStatus
{
	boolean valid;
	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) Boolean initialValue;
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) String reason;
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) JSONJavaException exception;
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) String fieldName;

	public static JSONDocumentValidStatus of(@NonNull final DocumentValidStatus validStatus, @NonNull final JSONOptions jsonOpts)
	{
		return builder()
				.valid(validStatus.isValid())
				.initialValue(validStatus.getInitialValue())
				.reason(validStatus.getReason() != null ? validStatus.getReason().translate(jsonOpts.getAdLanguage()) : null)
				.exception(JSONJavaException.ofNullable(validStatus.getException(), jsonOpts))
				.fieldName(validStatus.getFieldName())
				.build();
	}

}
