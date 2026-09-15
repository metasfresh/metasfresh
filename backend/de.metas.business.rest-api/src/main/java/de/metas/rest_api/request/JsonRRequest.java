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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * A JSON object that represents a {@code R_Request}.
 */
@Builder(toBuilder = true)
@Jacksonized
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonRRequest
{
	@NonNull JsonMetasfreshId id;

	@NonNull String orgCode;
	@NonNull String requestType;
	@Nullable JsonMetasfreshId bpartnerId;
	@Nullable JsonMetasfreshId userId;

	@Nullable JsonRequestPriority priority;
	@NonNull String summary;
	@Nullable JsonConfidentialType confidentialityLevel;

	@Nullable JsonMetasfreshId vendorId;
	@Nullable JsonMetasfreshId salesRepId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Nullable LocalDate dateDelivered;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Nullable LocalDate dateTrx;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Nullable LocalDate reminderDate;

	@Nullable JsonMetasfreshId projectId;
	@Nullable JsonMetasfreshId productId;

	@Nullable JsonMetasfreshId orderId;
	@Nullable JsonMetasfreshId inOutId;
	@Nullable JsonMetasfreshId invoiceId;
	@Nullable JsonMetasfreshId paymentId;

	@Nullable String qualityNoteValue;

	@Nullable String statusName;

}
