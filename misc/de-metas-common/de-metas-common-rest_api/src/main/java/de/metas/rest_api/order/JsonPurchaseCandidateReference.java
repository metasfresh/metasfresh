/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.rest_api.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v1.JsonExternalId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class JsonPurchaseCandidateReference
{
	@ApiModelProperty(position = 10, allowEmptyValue = false, dataType = "java.lang.String", example = "ExternalHeaderId_1",//
			value = "Used to select which invoice candidates should be enqueued.")
	JsonExternalId externalHeaderId;

	@ApiModelProperty(position = 20, allowEmptyValue = true, dataType = "java.lang.String", example = "[\"ExternalLineId_2\", \"ExternalLineId_3\"]", //
			value = "Optional, used to select which invoice candidates which have these `C_PurchaseCandidate.ExternalLineId`s should be enqueued.\n"
					+ "Inherited from order line candidates.\n"
					+ "If not specified, then all purchase candidate with the specified `externalHeaderId` are matched")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<JsonExternalId> externalLineIds;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonPurchaseCandidateReference(
			final @JsonProperty("externalHeaderId") @NonNull JsonExternalId externalHeaderId,
			final @JsonProperty("externalLineIds") @Nullable @Singular List<JsonExternalId> externalLineIds)
	{
		this.externalHeaderId = externalHeaderId;
		this.externalLineIds = externalLineIds == null ? ImmutableList.of() : ImmutableList.copyOf(externalLineIds);
	}

}
