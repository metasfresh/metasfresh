/*
 * #%L
 * de-metas-camel-scriptedadapter
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf;

import com.fasterxml.jackson.databind.JsonNode;
import de.metas.common.externalsystem.endpoint.JsonExternalSystemEndpoint;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class MsgFromMfContext
{
	@NonNull private final String orgCode;
	@NonNull private final String scriptingRequestBody;
	@NonNull private final String scriptIdentifier;

	private String script;
	private String scriptReturnValue;

	@NonNull private final JsonExternalSystemEndpoint endpointParameters;

	@NonNull private final String outboundRecordTableName;
	@NonNull private final String outboundRecordId;

	/** DocumentNo of the outbound record (e.g., shipment or invoice number). May be null if the table has no DocumentNo column. */
	@Nullable private final String outboundDocumentNo;

	/**
	 * Array-mode fan-out: when the JS transform returns a JSON array AND the endpoint has
	 * {@link JsonExternalSystemEndpoint#getArrayFanOut()} == TRUE, this holds the parsed array
	 * and one downstream HTTP/SFTP call is dispatched per element.
	 * <p>
	 * When null, the route falls through to single-request behaviour.
	 */
	@Nullable private JsonNode fanOutArray;

	/** Total number of elements in {@link #fanOutArray}; null when fan-out is not active. */
	@Nullable private Integer fanOutTotal;
}
