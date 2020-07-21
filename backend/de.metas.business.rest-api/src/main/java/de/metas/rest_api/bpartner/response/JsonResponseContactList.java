package de.metas.rest_api.bpartner.response;

import static de.metas.common.util.CoalesceUtil.coalesce;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.collect.ImmutableList;

import de.metas.rest_api.common.JsonPagingDescriptor;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ApiModel("Resonse to a request for contact master data.")
@Value
public class JsonResponseContactList
{
	@JsonUnwrapped
	JsonPagingDescriptor pagingDescriptor;

	List<JsonResponseContact> contacts;

	@Builder
	private JsonResponseContactList(
			@NonNull final JsonPagingDescriptor pagingDescriptor,
			@Singular final List<JsonResponseContact> contacts)
	{
		this.pagingDescriptor = pagingDescriptor;
		this.contacts = coalesce(contacts, ImmutableList.of());
	}

}
