/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.JsonPagingDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(builderMethodName = "_builder")
public class JsonResponseCompositeList
{

	@JsonInclude(Include.NON_NULL)
	@JsonUnwrapped
	JsonPagingDescriptor pagingDescriptor;

	@JsonInclude(Include.NON_NULL)
	List<JsonResponseComposite> items;

	@JsonInclude(Include.NON_EMPTY)
	@Singular
	List<JsonErrorItem> errors;

	public static JsonResponseCompositeList ok(
			@NonNull final JsonPagingDescriptor pagingDescriptor,
			@NonNull final List<JsonResponseComposite> items)
	{
		return _builder()
				.pagingDescriptor(pagingDescriptor)
				.items(items)
				.build();
	}

	public static JsonResponseCompositeList error(@NonNull final JsonErrorItem error)
	{
		return _builder()
				.error(error)
				.build();
	}
}
