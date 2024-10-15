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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.common.JsonWorkPackageStatus;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonPurchaseCandidate
{
	@JsonProperty("externalHeaderId")
	JsonExternalId externalHeaderId;

	@JsonProperty("externalLineId")
	JsonExternalId externalLineId;
	
	@JsonProperty("externalPurchaseOrderUrl")
	String externalPurchaseOrderUrl;

	@JsonProperty("metasfreshId")
	JsonMetasfreshId metasfreshId;

	@JsonProperty("processed")
	boolean processed;

	@JsonProperty("purchaseOrders")
	List<JsonPurchaseOrder> purchaseOrders;

	@JsonProperty("workPackages")
	List<JsonWorkPackageStatus> workPackages;

	@Builder
	@JsonCreator
	private JsonPurchaseCandidate(
			@JsonProperty("externalHeaderId") @NonNull final JsonExternalId externalHeaderId,
			@JsonProperty("externalLineId") @NonNull final JsonExternalId externalLineId,
			@JsonProperty("externalPurchaseOrderUrl") @Nullable final String externalPurchaseOrderUrl,
			@JsonProperty("metasfreshId") @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty("processed") final boolean processed,
			@JsonProperty("purchaseOrders") @Nullable @Singular final List<JsonPurchaseOrder> purchaseOrders,
			@JsonProperty("workPackages") @Nullable @Singular final List<JsonWorkPackageStatus> workPackages)
	{
		this.externalHeaderId = externalHeaderId;
		this.externalLineId = externalLineId;
		this.externalPurchaseOrderUrl = externalPurchaseOrderUrl;
		this.metasfreshId = metasfreshId;
		this.processed = processed;
		this.purchaseOrders = CoalesceUtil.coalesce(purchaseOrders, Collections.emptyList());
		this.workPackages = CoalesceUtil.coalesce(workPackages, Collections.emptyList());
	}

}
