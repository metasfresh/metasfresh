/*
 * #%L
 * de-metas-common-procurement
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.procurement.sync.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutConfirmationToMetasfreshRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutConfirmationToProcurementWebRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQCloseEventsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQsRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = PutBPartnersRequest.class, name = "putBPartnersRequest"),
		@JsonSubTypes.Type(value = PutProductsRequest.class, name = "putProductsRequest"),
		@JsonSubTypes.Type(value = PutInfoMessageRequest.class, name = "putInfoMessageRequest"),
		@JsonSubTypes.Type(value = PutConfirmationToMetasfreshRequest.class, name = "putConfirmationRequest"),
		@JsonSubTypes.Type(value = PutRfQsRequest.class, name = "putRfQsRequest"),
		@JsonSubTypes.Type(value = PutRfQCloseEventsRequest.class, name = "putRfQCloseEventsRequest"),
		@JsonSubTypes.Type(value = PutConfirmationToProcurementWebRequest.class, name = "putConfirmationToProcurementWebRequest")
})
public abstract class RequestToProcurementWeb
{
}
