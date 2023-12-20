package de.metas.rest_api.invoicecandidates.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.rest_api.utils.MetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
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
@Builder
public class JsonCloseInvoiceCandidatesResponseItem
{
	@Schema
	JsonExternalId externalHeaderId;

	@Schema
	JsonExternalId externalLineId;

	@Schema(description = "The metasfresh-ID of the upserted record")
	@NonNull
	MetasfreshId metasfreshId;

	@Schema(type = "java.lang.String")
	CloseInvoiceCandidateStatus status;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(type = "java.lang.String")
	JsonErrorItem error;

	public enum CloseInvoiceCandidateStatus
	{
		Closed("Closed"), Error("Error)");

		@Getter
		private final String code;

		CloseInvoiceCandidateStatus(final String code)
		{
			this.code = code;
		}
	}
}
