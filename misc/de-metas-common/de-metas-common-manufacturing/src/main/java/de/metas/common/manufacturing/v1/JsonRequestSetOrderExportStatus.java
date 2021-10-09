/*
 * #%L
 * de-metas-common-manufacturing
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

package de.metas.common.manufacturing.v1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.JsonError;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonRequestSetOrderExportStatus.JsonRequestSetOrderExportStatusBuilder.class)
public class JsonRequestSetOrderExportStatus
{
	JsonMetasfreshId orderId;

	Outcome outcome;

	@JsonInclude(Include.NON_NULL)
	JsonError error;

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonRequestSetOrderExportStatusBuilder
	{
	}

	public JsonRequestSetOrderExportStatus withError()
	{
		return outcome != Outcome.ERROR
				? toBuilder().outcome(Outcome.ERROR).build()
				: this;
	}
}
