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
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutConfirmationToMetasfreshRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutWeeklySupplyRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = GetAllBPartnersRequest.class, name = "getAllBPartnersRequest"),
		@JsonSubTypes.Type(value = GetAllProductsRequest.class, name = "getAllProductsRequest"),
		@JsonSubTypes.Type(value = GetInfoMessageRequest.class, name = "getInfoMessageRequest"),
		@JsonSubTypes.Type(value = PutProductSuppliesRequest.class, name = "putProductSuppliesRequest"),
		@JsonSubTypes.Type(value = PutWeeklySupplyRequest.class, name = "putWeeklySupplyRequest"),
		@JsonSubTypes.Type(value = PutRfQChangeRequest.class, name = "putRfQChangeRequest"),
		@JsonSubTypes.Type(value = PutConfirmationToMetasfreshRequest.class, name = "putConfirmationToMetasfreshRequest")
})
public abstract class RequestToMetasfresh
{
}
