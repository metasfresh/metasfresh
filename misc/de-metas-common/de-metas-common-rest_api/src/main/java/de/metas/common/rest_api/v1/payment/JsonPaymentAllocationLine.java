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

package de.metas.common.rest_api.v1.payment;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.v1.SwaggerDocConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = JsonPaymentAllocationLine.JsonPaymentAllocationLineBuilder.class)
public class JsonPaymentAllocationLine
{
	@NonNull
	@ApiModelProperty(required = true,
			dataType = "java.lang.String",
			value = SwaggerDocConstants.INVOICE_IDENTIFIER_DOC)
	String invoiceIdentifier;

	@ApiModelProperty(position = 10)
	@Nullable
	String docBaseType;

	@ApiModelProperty(position = 20)
	@Nullable
	String docSubType;

	@ApiModelProperty(position = 30)
	@Nullable
	BigDecimal amount;

	@ApiModelProperty(position = 40)
	@Nullable
	BigDecimal discountAmt;

	@ApiModelProperty(position = 50)
	@Nullable
	BigDecimal writeOffAmt;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonPaymentAllocationLineBuilder
	{
	}
}
