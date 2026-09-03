/*
 * #%L
 * de-metas-common-bpartner
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class JsonResponseBPartnerCreditLimit
{
	public static final String METASFRESH_ID = "metasfreshId";
	public static final String BPARTNER_ID = "bpartnerId";
	public static final String AMOUNT = "amount";
	public static final String DATE_FROM = "dateFrom";
	public static final String CREDITLIMITTYPE = "creditLimitType";

	@ApiModelProperty( //
			required = true, //
			dataType = "java.lang.Integer", //
			value = "This translates to `C_BPartner_CreditLimit.C_BPartner_CreditLimit_ID`.")
	@JsonProperty(METASFRESH_ID)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId metasfreshId;

	@ApiModelProperty(value = "This translates to `C_BPartner_CreditLimit.Amount`.")
	@JsonProperty(AMOUNT)
	BigDecimal amount;

	@ApiModelProperty(value = "This translates to `C_BPartner_CreditLimit.C_BPartner_ID`.")
	@JsonProperty(BPARTNER_ID)
	@NonNull
	JsonMetasfreshId metasfreshBPartnerId;

	@ApiModelProperty(value = "This translates to `C_BPartner_CreditLimit.DateFrom`.")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	LocalDate dateFrom;

	@ApiModelProperty(value = "This translates to `C_BPartner_CreditLimit.C_CreditLimit_Type_ID`.")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	JsonResponseCreditLimitType creditLimitType;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonResponseBPartnerCreditLimit(
			@JsonProperty(METASFRESH_ID) @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty(BPARTNER_ID) @NonNull final JsonMetasfreshId metasfreshBPartnerId,
			@JsonProperty(AMOUNT) final BigDecimal amount,
			@JsonProperty(DATE_FROM) @Nullable final LocalDate dateFrom,
			@JsonProperty(CREDITLIMITTYPE) @Nullable final JsonResponseCreditLimitType creditLimitType)
	{
		this.metasfreshId = metasfreshId;
		this.metasfreshBPartnerId = metasfreshBPartnerId;
		this.amount = amount;
		this.dateFrom = dateFrom;
		this.creditLimitType = creditLimitType;
	}
}
