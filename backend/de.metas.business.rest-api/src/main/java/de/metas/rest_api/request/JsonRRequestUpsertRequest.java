/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.rest_api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.request.JsonConfidentialType;
import de.metas.common.rest_api.request.JsonRequestPriority;
import de.metas.common.rest_api.v2.SwaggerDocConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * A JSON object used to create a new {@code R_Request} record.
 */
@Builder(toBuilder = true)
@Jacksonized
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonRRequestUpsertRequest
{
	@ApiModelProperty(value = SwaggerDocConstants.ORG_CODE_PARAMETER_DOC)
	@NonNull String orgCode;

	@NonNull String requestType;

	@ApiModelProperty(value = SwaggerDocConstants.BPARTNER_VALUE_DOC)
	@Nullable String bpartnerIdentifier;

	@ApiModelProperty(value = SwaggerDocConstants.CONTACT_IDENTIFIER_DOC)
	@Nullable String userIdentifier;

	@Nullable JsonRequestPriority priority;
	@NonNull String summary;
	@Nullable JsonConfidentialType confidentialityLevel;

	@ApiModelProperty(value = SwaggerDocConstants.BPARTNER_VALUE_DOC)
	@Nullable String vendorIdentifier;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Nullable LocalDate dateDelivered;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Nullable LocalDate dateTrx;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Nullable LocalDate reminderDate;

	@Nullable String projectValue;

	@ApiModelProperty(value = SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC)
	@Nullable String productIdentifier;

	@Nullable JsonMetasfreshId orderId;
	@Nullable JsonMetasfreshId inOutId;
	@Nullable JsonMetasfreshId invoiceId;
	@Nullable JsonMetasfreshId paymentId;

	@Nullable String qualityNote;

	@ApiModelProperty(value = SwaggerDocConstants.CONTACT_IDENTIFIER_DOC)
	@Nullable String salesRepIdentifier;

	@Nullable String statusName;
}
