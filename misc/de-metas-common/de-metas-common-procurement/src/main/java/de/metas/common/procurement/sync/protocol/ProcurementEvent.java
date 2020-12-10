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

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = SyncInfoMessageRequest.class, name = "syncInfoMessageRequest"),
		@JsonSubTypes.Type(value = SyncProductsRequest.class, name = "syncProductsRequest"),
		@JsonSubTypes.Type(value = SyncConfirmationRequest.class, name = "syncConfirmationRequest"),
		@JsonSubTypes.Type(value = SyncRfQsRequest.class, name = "syncRfQsRequest"),
		@JsonSubTypes.Type(value = SyncRfQCloseEventsRequest.class, name = "syncRfQCloseEventsRequest"),
		@JsonSubTypes.Type(value = SyncRfQsRequest.class, name = "syncRfQsRequest"),
		@JsonSubTypes.Type(value = SyncBPartnersRequest.class, name = "syncBPartnersRequest"),
		@JsonSubTypes.Type(value = SyncWeeklySupplyRequest.class, name = "syncWeeklySupplyRequest"),
		@JsonSubTypes.Type(value = SyncProductSuppliesRequest.class, name = "syncProductSuppliesRequest"),
		@JsonSubTypes.Type(value = SyncRfQChangeRequest.class, name = "syncRfQChangeRequest"),
		@JsonSubTypes.Type(value = GetAllBPartnersRequest.class, name = "getAllBPartnersRequest"),
		@JsonSubTypes.Type(value = GetAllProductsRequest.class, name = "getAllProductsRequest"),
		@JsonSubTypes.Type(value = GetInfoMessageRequest.class, name = "getInfoMessageRequest")
})
public abstract class ProcurementEvent
{
}
