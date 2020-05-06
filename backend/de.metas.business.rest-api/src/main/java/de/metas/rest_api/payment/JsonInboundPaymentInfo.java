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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.rest_api.bpartner.SwaggerDocConstants;
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
@JsonDeserialize(builder = JsonInboundPaymentInfo.JsonInboundPaymentInfoBuilder.class)
public class JsonInboundPaymentInfo
{
	@ApiModelProperty(required = true, //
			dataType = "java.lang.String", //
			value = SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC)
	@NonNull
	String bpartnerIdentifier;

	@ApiModelProperty(required = true, //
			dataType = "java.lang.String")
	@NonNull
	String targetIBAN;

	@ApiModelProperty(required = true, //
			dataType = "java.lang.String", //
			value = "This translates to `C_Order.ExternalId`.")
	@NonNull
	String externalOrderId;

	@ApiModelProperty(required = true)
	@NonNull
	BigDecimal amount;

	@ApiModelProperty(required = true, //
			dataType = "java.lang.String")
	@NonNull
	String currencyCode;

	@ApiModelProperty(position = 10, //
			value = "Optional, to specify the `AD_Org_ID`.\n"
					+ "This property needs to be set to the `AD_Org.Value` of an organisation that the invoking user is allowed to access\n"
					+ "or the invoking user needs to belong to an organisation, which is then used.")
	@Nullable
	String orgCode;

	@ApiModelProperty(dataType = "java.time.LocalDate",
			value = "If this is sent, it is used for both `accounting date` and `payment date`.")
	@Nullable
	LocalDate transactionDate;

	@JsonIgnoreProperties(ignoreUnknown = true) // the annotation to ignore properties should be set on the deserializer method (on the builder), and not on the base class
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonInboundPaymentInfoBuilder
	{
	}

}

