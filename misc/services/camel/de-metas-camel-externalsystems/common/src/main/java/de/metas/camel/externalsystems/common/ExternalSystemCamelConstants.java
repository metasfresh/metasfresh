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
	public static final String HEADER_PRICE_LIST_IDENTIFIER = "priceListIdentifier";
	public static final String HEADER_TARGET_ROUTE = "targetRoute";
	public static final String HEADER_EXTERNAL_SYSTEM_VALUE = "externalSystemValue";
	public static final String HEADER_AUDIT_TRAIL = "auditTrailEndpoint";
	public static final String HEADER_TRACE_ID = "traceId";
	public static final String HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE = "externalSystemConfigType";
	public static final String HEADER_EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE = "externalSystemChildConfigValue";
	public static final String HEADER_EXTERNAL_SERVICE_VALUE = "serviceValue";
	public static final String HEADER_EXTERNAL_SYSTEM_REQUEST = "externalSystemRequest";
	public static final String HEADER_HU_ID = "M_HU_ID";
	public static final String HEADER_PP_ORDER_ID = "PP_ORDER_ID";
	public static final String HEADER_PRODUCT_IDENTIFIER = "productIdentifier";

	public static final String MF_API_BASE_URL_PROPERTY = "metasfresh.api.baseurl";

	public static final String MF_UPSERT_BPARTNER_CAMEL_URI = "metasfresh.upsert-bpartner.camel.uri";

	public static final String MF_UPSERT_BPARTNER_V2_CAMEL_URI = "metasfresh.upsert-bpartner-v2.camel.uri";

	public static final String MF_UPSERT_WAREHOUSE_V2_CAMEL_URI = "metasfresh.upsert-warehouse-v2.camel.uri";

	public static final String MF_UPSERT_BPARTNER_LOCATION_V2_CAMEL_URI = "metasfresh.upsert-bpartnerlocation-v2.camel.uri";

	public static final String MF_RETRIEVE_BPARTNER_V2_CAMEL_URI = "metasfresh.retrieve-bpartner-v2.camel.uri";

	public static final String MF_UPSERT_PRODUCT_V2_CAMEL_URI = "metasfresh.upsert-product-v2.camel.uri";

	public static final String MF_UPSERT_BPRELATION_V2_CAMEL_URI = "metasfresh.upsert-bprelation-v2.camel.uri";

	public static final String MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI = "metasfresh.upsert-product-price-v2.camel.uri";
	public static final String MF_PRICE_LIST_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI = "metasfresh.price-list-upsert-product-price-v2.camel.uri";

	public static final String MF_UPSERT_BOM_V2_CAMEL_URI = "metasfresh.upsert-bom-v2.camel.uri";

	public static final String MF_CREATE_PURCHASE_CANDIDATE_V2_CAMEL_URI = "metasfresh.create-purchase-candidate-v2.camel.uri";

	public static final String MF_UPSERT_BPRELATION_CAMEL_URI = "metasfresh.upsert-bprelation.camel.uri";

	public static final String MF_CREATE_EXTERNALREFERENCE_CAMEL_URI = "metasfresh.create-externalreference.camel.uri";

	public static final String MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI = "metasfresh.lookup-externalreference.camel.uri";

	public static final String MF_EXTERNAL_SYSTEM_V2_URI = "metasfresh.externalsystem.v2.api.uri";

	public static final String MF_UPSERT_EXTERNALREFERENCE_CAMEL_URI = "metasfresh.upsert-externalreference.camel.uri";

	public static final String MF_ERROR_ROUTE_ID = "Error-Route";

	public static final String ERROR_WRITE_TO_ADISSUE = "Error-Route-writeToAdIssue";

	public static final String MF_LOG_MESSAGE_ROUTE_ID = "Log-Message-Route";

	public static final String MF_GET_PRODUCTS_ROUTE_ID = "To-MF_GetProducts-Route";

	public static final String MF_PUSH_OL_CANDIDATES_ROUTE_ID = "To-MF_PushOLCandidates-Route";

	public static final String MF_GET_BPARTNER_PRODUCTS_ROUTE_ID = "To-MF_GetBPartnerProducts-Route";

	public static final String MF_PROCESS_OL_CANDIDATES_ROUTE_ID = "To-MF_ProcessOLCandidates-Route";

	public static final String MF_CREATE_ORDER_PAYMENT_ROUTE_ID = "To-MF_CreateOrderPayment-Route";

	public static final String MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID = "To-MF_UpsertRuntimeParameters-Route";
	public static final String MF_SEQ_NO_ROUTE_ID = "To-MF_Seq_No-Route";

	public static final String MF_SEQ_NO_V2_URI = "metasfresh.seqno.v2.api.uri";

	public static final String MF_ATTACHMENT_ROUTE_ID = "To-MF_PushAttachment-Route";

	public static final String MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI = "To-MF_Enqueue_Purchases_Candidate-Route";

	public static final String MF_LOOKUP_EXTERNALREFERENCE_V2_CAMEL_URI = "To-MF_Lookup-ExternalReference_V2";


	public static final String REST_API_AUTHENTICATE_TOKEN = "Core-registerToken";
	public static final String REST_API_EXPIRE_TOKEN = "Core-expireToken";


	public static final String MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI= "metasfresh.create-service-status-v2.camel.uri";
	public static final String MF_GET_SERVICE_STATUS_V2_CAMEL_URI = "metasfresh.service-status-v2.camel.uri";
	public static final String MF_INVOKE_EXTERNAL_SYSTEM_ACTION_V2_CAMEL_URI= "metasfresh.invoke-external-system-action-v2.camel.uri";

	public static final String MF_RETRIEVE_HU_V2_CAMEL_ROUTE_ID = "To-MF_retrieveHU_V2-Route";
	public static final String MF_RETRIEVE_HU_V2_CAMEL_URI = "metasfresh.retrieve-hu-v2.api.uri";

	public static final String MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_ROUTE_ID = "To-MF_updateHUAttributes_V2-Route";
	public static final String MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_URI = "metasfresh.update-hu-attributes-v2.api.uri";


	public static final String MF_GET_EXTERNAL_SYSTEM_INFO = "metasfresh.external-system-info-v2.camel.uri";

	public static final String MF_CLEAR_HU_V2_CAMEL_ROUTE_ID = "To-MF_clearHU_V2-Route";
	public static final String MF_CLEAR_HU_V2_URI = "metasfresh.clear-hu-v2.api.uri";

	public static final String MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID = "To-MF_retrieve_PPOrder_V2-Route";
	public static final String MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID = "To-MF_retrieve_Material_ProductInfo_V2-Route";
	public static final String MF_SEARCH_PRODUCT_PRICES_V2_CAMEL_ROUTE_ID = "To-MF_Search_ProductPrices_V2-Route";

	public static final String MF_GET_PRINTING_DATA_ROUTE_ID = "To-MF_getPrintingData_V2_Route";
	public static final String MF_SET_PRINTING_RESULT_ROUTE_ID = "To-MF_setPrintingResult_V2_Route";
	public static final String HEADER_PRINTING_QUEUE_ID = "printingQueueId";
	public static final String MF_PRINT_V2_BASE = "metasfresh.print.v2.api.base.uri";
}
