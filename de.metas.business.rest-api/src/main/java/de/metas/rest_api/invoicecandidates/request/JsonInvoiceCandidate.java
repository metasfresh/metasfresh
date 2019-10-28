package de.metas.rest_api.invoicecandidates.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.ImmutableList;

import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandCreateRequest.JsonInvoiceCandCreateRequestBuilder;
import de.metas.rest_api.ordercandidates.request.JsonDocTypeInfo;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.rest_api.ordercandidates.request.JsonOrganization;
import de.metas.rest_api.ordercandidates.request.JsonProductInfo;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact;
import de.metas.util.rest.ExternalId;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder;
import io.swagger.annotations.ApiModelProperty;
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
@Value
public final class JsonInvoiceCandidate
{
	@ApiModelProperty(allowEmptyValue = true, value = "Inherited from order line candidates. Used to select which invoice candidates which have these invoice line ids should be enqueued.")
	@JsonInclude(Include.NON_EMPTY)
	List<ExternalId> externalLineIds;
	@ApiModelProperty(allowEmptyValue = false, value = "Used to select which invoice candidates should be enqueued.")
	String externalHeaderId;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonInvoiceCandidate(
			@JsonProperty("externalLineIds") @Singular final List<ExternalId> externalLineIds,
			@JsonProperty("externalHeaderId") final String externalHeaderId)
	{
		this.externalLineIds = ImmutableList.copyOf(externalLineIds);
		this.externalHeaderId = externalHeaderId;
	}

}
