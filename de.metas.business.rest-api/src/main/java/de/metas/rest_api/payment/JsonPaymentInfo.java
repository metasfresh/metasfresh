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

package de.metas.rest_api.payment;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.rest_api.common.JsonExternalId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonPaymentInfo
{
	@ApiModelProperty(required = true, //
			dataType = "java.lang.String", //
			value = "This translates to `C_BPartner.ExternalId`.")

	@NonNull
	String externalBpartnerId;

	@NonNull
	String targetIBAN;

	@ApiModelProperty(required = true, //
			dataType = "java.lang.String", //
			value = "This translates to `C_Order.ExternalId`.")
	@NonNull
	String externalOrderId;

	@NonNull
	BigDecimal amount;

	@NonNull
	String currencyCode;

	@ApiModelProperty(required = false)
	@Nullable
	adorgid the_org_value;

	@ApiModelProperty(required = false,
	dataType = "java.time.LocalDate",
	value = "If this is sent, it is used for both `acconting date` and `payment date`.")
	@Nullable
	LocalDate transactionDate;
}

