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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model;

import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPLocationCamelRequest;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.common.v2.ProductPriceUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.PurchaseCandidateCamelRequest;
import de.metas.camel.externalsystems.common.v2.UpsertProductPriceList;
import de.metas.camel.externalsystems.common.v2.WarehouseUpsertCamelRequest;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.handlingunits.JsonHUAttributesRequest;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandProcessRequest;
import de.metas.common.rest_api.v2.JsonPurchaseCandidatesRequest;
import de.metas.common.rest_api.v2.adprocess.JsonAdProcessRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.order.JsonOrderPaymentCreateRequest;
import de.metas.common.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum CamelServiceRouteIdWithRequestType
{
	/**
	 * keep in sync with the routes in {@link de.metas.camel.externalsystems.core.to_mf.v2}
	 */
	MF_AD_Process_ROUTE_ID(ExternalSystemCamelConstants.MF_AD_Process_ROUTE_ID, JsonAdProcessRequest.class, false),
	MF_ATTACHMENT_ROUTE_ID(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID, JsonAttachmentRequest.class, false),
	MF_UPSERT_BPARTNER_LOCATION_V2_CAMEL_URI(ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_LOCATION_V2_CAMEL_URI, BPLocationCamelRequest.class, true),
	MF_UPSERT_BPARTNER_V2_CAMEL_URI(ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI, BPUpsertCamelRequest.class, true),
	MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI(ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI, ExternalStatusCreateCamelRequest.class, true),
	MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID(ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID, JsonESRuntimeParameterUpsertRequest.class, false),
	MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_ROUTE_ID(ExternalSystemCamelConstants.MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_ROUTE_ID, JsonHUAttributesRequest.class, false),
	MF_CLEAR_HU_V2_CAMEL_ROUTE_ID(ExternalSystemCamelConstants.MF_CLEAR_HU_V2_CAMEL_ROUTE_ID, JsonSetClearanceStatusRequest.class, false),
	MF_PUSH_OL_CANDIDATES_ROUTE_ID(ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID, JsonOLCandCreateBulkRequest.class, false),
	MF_PROCESS_OL_CANDIDATES_ROUTE_ID(ExternalSystemCamelConstants.MF_PROCESS_OL_CANDIDATES_ROUTE_ID, JsonOLCandProcessRequest.class, false),
	MF_CREATE_ORDER_PAYMENT_ROUTE_ID(ExternalSystemCamelConstants.MF_CREATE_ORDER_PAYMENT_ROUTE_ID, JsonOrderPaymentCreateRequest.class, false),
	MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI(ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI, ProductPriceUpsertCamelRequest.class, true),
	MF_PRICE_LIST_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI(ExternalSystemCamelConstants.MF_PRICE_LIST_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI, UpsertProductPriceList.class, true),
	MF_UPSERT_PRODUCT_V2_CAMEL_URI(ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_V2_CAMEL_URI, ProductUpsertCamelRequest.class, true),
	MF_CREATE_PURCHASE_CANDIDATE_V2_CAMEL_URI(ExternalSystemCamelConstants.MF_CREATE_PURCHASE_CANDIDATE_V2_CAMEL_URI, PurchaseCandidateCamelRequest.class, false),
	MF_UPSERT_WAREHOUSE_V2_CAMEL_URI(ExternalSystemCamelConstants.MF_UPSERT_WAREHOUSE_V2_CAMEL_URI, WarehouseUpsertCamelRequest.class, true),
	MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI(ExternalSystemCamelConstants.MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI, JsonPurchaseCandidatesRequest.class, false);

	@NonNull
	private final String routeId;
	@NonNull
	private final Class<?> requestType;

	/**
	 * If true, this endpoint's URI is loaded from a property file. If false, the URI is created as "{@code direct:}{@link #getRouteId()}"
	 */
	private final boolean isProperty;

	@NonNull
	public static CamelServiceRouteIdWithRequestType ofRouteId(@NonNull final String routeId)
	{
		return ofRouteIdOptional(routeId)
				.orElseThrow(() -> new RuntimeException("No CamelRoute could be found for routeId " + routeId + "!"));
	}

	@NonNull
	public static Optional<CamelServiceRouteIdWithRequestType> ofRouteIdOptional(@Nullable final String routeId)
	{
		if (Check.isBlank(routeId))
		{
			return Optional.empty();
		}

		return Optional.ofNullable(routeId2RouteIdWithRequestType.getOrDefault(routeId, null));
	}

	private final static ImmutableMap<String, CamelServiceRouteIdWithRequestType> routeId2RouteIdWithRequestType = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(
					CamelServiceRouteIdWithRequestType::getRouteId,
					Function.identity()
			));
}
