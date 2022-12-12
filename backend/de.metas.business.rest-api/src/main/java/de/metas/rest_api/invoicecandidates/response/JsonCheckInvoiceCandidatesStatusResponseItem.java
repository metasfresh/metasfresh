package de.metas.rest_api.invoicecandidates.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonWorkPackageStatus;
import de.metas.rest_api.utils.MetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class JsonCheckInvoiceCandidatesStatusResponseItem
{
	@ApiModelProperty(position = 10, dataType = "java.lang.String")
	JsonExternalId externalHeaderId;

	@ApiModelProperty(position = 20, dataType = "java.lang.String")
	JsonExternalId externalLineId;

	@ApiModelProperty(position = 30, dataType = "java.lang.String")
	MetasfreshId metasfreshId;

	@ApiModelProperty(position = 40)
	@Nullable
	BigDecimal qtyEntered;

	@ApiModelProperty(position = 50)
	@Nullable
	BigDecimal qtyToInvoice;

	@ApiModelProperty(position = 60)
	@Nullable
	BigDecimal qtyInvoiced;

	@ApiModelProperty(position = 70)
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	LocalDate dateInvoiced;

	@ApiModelProperty(position = 80)
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	LocalDate dateToInvoice;

	@ApiModelProperty(position = 90)
	boolean processed;

	@ApiModelProperty(position = 100)
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<JsonInvoiceStatus> invoices;

	@ApiModelProperty(position = 110)
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<JsonWorkPackageStatus> workPackages;

	@Builder
	@JsonCreator
	public JsonCheckInvoiceCandidatesStatusResponseItem(
			@JsonProperty("externalHeaderId") final JsonExternalId externalHeaderId,
			@JsonProperty("externalLineId") final JsonExternalId externalLineId,
			@JsonProperty("metasfreshId") final MetasfreshId metasfreshId,
			@JsonProperty("qtyEntered") @Nullable final BigDecimal qtyEntered,
			@JsonProperty("qtyToInvoice") @Nullable final BigDecimal qtyToInvoice,
			@JsonProperty("qtyInvoiced") @Nullable final BigDecimal qtyInvoiced,
			@JsonProperty("dateInvoiced") @Nullable final LocalDate dateInvoiced,
			@JsonProperty("dateToInvoice") @Nullable final LocalDate dateToInvoice,
			@JsonProperty("processed") final boolean processed,
			@JsonProperty("invoices") @Nullable final List<JsonInvoiceStatus> invoices,
			@JsonProperty("workPackages") @Nullable final List<JsonWorkPackageStatus> workPackages)
	{
		this.externalHeaderId = externalHeaderId;
		this.externalLineId = externalLineId;
		this.metasfreshId = metasfreshId;
		this.qtyEntered = qtyEntered;
		this.qtyToInvoice = qtyToInvoice;
		this.qtyInvoiced = qtyInvoiced;
		this.dateInvoiced = dateInvoiced;
		this.dateToInvoice = dateToInvoice;
		this.processed = processed;
		this.invoices = invoices;
		this.workPackages = workPackages;
	}
}
