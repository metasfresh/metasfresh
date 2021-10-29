/*
 * #%L
 * de-metas-camel-externalsystems-common
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

package de.metas.camel.externalsystems.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExternalSystemCamelConstants
{
	public static final String HEADER_ORG_CODE = "orgCode";
	public static final String HEADER_BPARTNER_IDENTIFIER = "bPartnerIdentifier";
	public static final String HEADER_PINSTANCE_ID = "pInstanceId";
	public static final String HEADER_PRICE_LIST_VERSION_IDENTIFIER = "priceListVersionIdentifier";

	public static String MF_UPSERT_BPARTNER_CAMEL_URI = "metasfresh.upsert-bpartner.camel.uri";

	public static String MF_UPSERT_BPARTNER_V2_CAMEL_URI = "metasfresh.upsert-bpartner-v2.camel.uri";

	public static String MF_UPSERT_BPARTNER_LOCATION_V2_CAMEL_URI = "metasfresh.upsert-bpartnerlocation-v2.camel.uri";

	public static String MF_UPSERT_PRODUCT_V2_CAMEL_URI = "metasfresh.upsert-product-v2.camel.uri";

	public static String MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI = "metasfresh.upsert-product-price-v2.camel.uri";

	public static String MF_UPSERT_BOM_V2_CAMEL_URI = "metasfresh.upsert-bom-v2.camel.uri";

	public static String MF_CREATE_PURCHASE_CANDIDATE_V2_CAMEL_URI = "metasfresh.create-purchase-candidate-v2.camel.uri";

	public static String MF_UPSERT_BPRELATION_CAMEL_URI = "metasfresh.upsert-bprelation.camel.uri";

	public static String MF_CREATE_EXTERNALREFERENCE_CAMEL_URI = "metasfresh.create-externalreference.camel.uri";

	public static String MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI = "metasfresh.lookup-externalreference.camel.uri";

	public static String MF_EXTERNAL_SYSTEM_V2_URI = "metasfresh.externalsystem.v2.api.uri";

	public static String MF_UPSERT_EXTERNALREFERENCE_CAMEL_URI = "metasfresh.upsert-externalreference.camel.uri";

	public final String MF_ERROR_ROUTE_ID = "Error-Route";

	public final static String ERROR_WRITE_TO_ADISSUE = "Error-Route-writeToAdIssue";

	public final String MF_LOG_MESSAGE_ROUTE_ID = "Log-Message-Route";

	public static String MF_GET_PRODUCTS_ROUTE_ID = "To-MF_GetProducts-Route";

	public static String MF_PUSH_OL_CANDIDATES_ROUTE_ID = "To-MF_PushOLCandidates-Route";

	public static String MF_CLEAR_OL_CANDIDATES_ROUTE_ID = "To-MF_ClearOLCandidates-Route";

	public static String MF_CREATE_ORDER_PAYMENT_ROUTE_ID = "To-MF_CreateOrderPayment-Route";

	public static String MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID = "To-MF_UpsertRuntimeParameters-Route";

	public static String MF_ATTACHMENT_ROUTE_ID = "To-MF_PushAttachment-Route";

	public static String MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI = "To-MF_Enqueue_Purchases_Candidate-Route";

	public static String REST_API_AUTHENTICATE_TOKEN = "Core-registerToken";
	public static String REST_API_EXPIRE_TOKEN = "Core-expireToken";
}
