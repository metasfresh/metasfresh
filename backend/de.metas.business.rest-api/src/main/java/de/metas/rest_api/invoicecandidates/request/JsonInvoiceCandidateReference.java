package de.metas.rest_api.invoicecandidates.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v2.JsonDocTypeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

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
@Value
public class JsonInvoiceCandidateReference
{
	@ApiModelProperty(position = 10, allowEmptyValue = true, dataType = "java.lang.String", example = "ExternalHeaderId_1",//
			value = "Used to select which invoice candidates should be enqueued.")
	JsonExternalId externalHeaderId;

	@ApiModelProperty(position = 20, allowEmptyValue = true, dataType = "java.lang.String", example = "[\"ExternalLineId_2\", \"ExternalLineId_3\"]", //
			value = "Optional, used to select which invoice candidates which have these `C_Invoice_Candidate.ExternalLineId`s should be enqueued.\n"
					+ "Inherited from order line candidates.\n"
					+ "If not specified, then all invoice candidate with the specified `externalHeaderId` are matched")
	@JsonInclude(Include.NON_EMPTY)
	List<JsonExternalId> externalLineIds;

	@ApiModelProperty(position = 30, allowEmptyValue = true, dataType = "java.lang.String", example = "001",//
			value = "The `AD_Org.Value` of the `C_Invoice_Candidate`'s AD_Org_ID")
	String orgCode;

	@ApiModelProperty(position = 40, allowEmptyValue = true, //
			value = "Can be set if the orders' document type is already known. When specified, orgCode of the document type also has to be specified")
	JsonDocTypeInfo orderDocumentType;

	@ApiModelProperty(position = 50, allowEmptyValue = true, dataType = "java.lang.String", example = "8393",//
			value = "Used to select which invoice candidates should be enqueued, based on the referenced order's document no.")
	String orderDocumentNo;

	@ApiModelProperty(position = 60, allowEmptyValue = true, dataType = "java.lang.Integer", example = "[\"10\", \"20\"]", //
			value = "Optional, used to select invoice candidates which reference order lines that have these `C_OrderLine.Line`s " 
					+ "and are part of the order with the`orderDocumentNo` from above.\n"
					+ "If not specified, then all invoice candidates that reference the order with the specified `orderDocumentNo` are matched")
	@JsonInclude(Include.NON_EMPTY)
	List<Integer> orderLines;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonInvoiceCandidateReference(
			@JsonProperty("externalHeaderId") @Nullable final JsonExternalId externalHeaderId,
			@JsonProperty("externalLineIds") @Singular @Nullable final List<JsonExternalId> externalLineIds,
			@JsonProperty("orderDocumentNo") @Nullable final String orderDocumentNo,
			@JsonProperty("orgCode") @Nullable final String orgCode,
			@JsonProperty("orderDocumentType") @Nullable final JsonDocTypeInfo orderDocumentType,
			@JsonProperty("orderLines") @Singular @Nullable final List<Integer> orderLines)
	{
		this.externalHeaderId = externalHeaderId;
		this.externalLineIds = externalLineIds == null ? ImmutableList.of() : ImmutableList.copyOf(externalLineIds);
		this.orderDocumentNo = orderDocumentNo;
		this.orgCode = orgCode;
		this.orderDocumentType = orderDocumentType;
		this.orderLines = orderLines == null ? ImmutableList.of() : ImmutableList.copyOf(orderLines);
	}
}
