/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExternalSystemConstants
{
	public static final String PARAM_API_KEY = "APIKey";
	public static final String PARAM_BASE_PATH = "BasePath";
	public static final String PARAM_TENANT = "Tenant";

	public static final String PARAM_UPDATED_AFTER = "UpdatedAfter";
	public static final String PARAM_UPDATE_AFTER_DOCUMENT = "UpdatedAfterDocument";
	public static final String PARAM_UPDATE_AFTER_ATTACHMENT = "UpdatedAfterAttachment";
	public static final String PARAM_CLIENT_ID = "ClientId";
	public static final String PARAM_CLIENT_SECRET = "ClientSecret";
	public static final String PARAM_CHILD_CONFIG_VALUE = "ChildConfigValue";

	public static final String PARAM_JSON_PATH_CONSTANT_METASFRESH_ID = "JSONPathConstantMetasfreshID";
	public static final String PARAM_JSON_PATH_CONSTANT_SHOPWARE_ID = "JSONPathConstantShopwareID";
	public static final String PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID = "JSONPathConstantBPartnerLocationID";
	public static final String PARAM_JSON_PATH_SALES_REP_ID = "JSONPathConstantSalesRepID";
	public static final String PARAM_JSON_PATH_EMAIL = "JSONPathEmail";
	public static final String PARAM_PRODUCT_LOOKUP = "ProductLookup";
	public static final String PARAM_CONFIG_MAPPINGS = "ConfigMappings";
	public static final String PARAM_UOM_MAPPINGS = "UOMMappings";
	public static final String PARAM_JSON_EXPORT_DIRECTORY_SETTINGS = "JsonExportDirectorySettings";

	public static final String PARAM_NORMAL_VAT_RATES = "NormalVAT_Rates";
	public static final String PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID = "M_FreightCost_NormalVAT_Product_ID";

	public static final String PARAM_REDUCED_VAT_RATES = "Reduced_VAT_Rates";
	public static final String PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID = "M_FreightCost_ReducedVAT_Product_ID";

	public static final String PARAM_TARGET_PRICE_LIST_ID = "TargetPriceListId";
	public static final String PARAM_IS_TAX_INCLUDED = "PriceList_IsTaxIncluded";
	public static final String PARAM_PRICE_LIST_CURRENCY_CODE = "PriceListCurrencyCode";

	public static final String PARAM_UPDATED_AFTER_OVERRIDE = "UpdatedAfterOverride";
	public static final String PARAM_ORDER_NO = "OrderNo"; // if set, then this shall override PARAM_UPDATED_AFTER*
	public static final String PARAM_ORDER_ID = "OrderId"; // if set, then this shall override PARAM_UPDATED_AFTER*
	public static final String PARAM_ROOT_BPARTNER_ID_FOR_USERS = "RootBPartnerID";

	public static final String PARAM_ALBERTA_ID = "Alberta_Id";
	public static final String PARAM_ALBERTA_ROLE = "Alberta_Role";

	public static final String PARAM_CAMEL_HTTP_RESOURCE_AUTH_KEY = "CamelHttpResourceAuthKey";

	public static final String QUEUE_NAME_MF_TO_ES = "MF_TO_ExternalSystem";

	public static final String QUEUE_NAME_ES_TO_MF_CUSTOM = "Custom_ExternalSystem_To_Metasfresh";

	public static final String QUEUE_NAME_MF_TO_ES_CUSTOM = "Custom_Metasfresh_To_ExternalSystem";

	public static final String PARAM_RABBITMQ_HTTP_URL = "RemoteURL";
	public static final String PARAM_RABBITMQ_HTTP_ROUTING_KEY = "RoutingKey";
	public static final String PARAM_EXTERNAL_SYSTEM_HTTP_URL = "RemoteURL";
	public static final String PARAM_EXTERNAL_SYSTEM_AUTH_TOKEN = "AuthToken";
	public static final String PARAM_BPARTNER_ID = "BPartnerId";
	public static final String PARAM_HU_ID = "HUId";
	public static final String PARAM_TENANT_ID = "TenantId";
	public static final String PARAM_RABBIT_MQ_AUTH_TOKEN = "RabbitMQAuthToken";
	public static final String PARAM_JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST = "JsonExternalReferenceLookupRequest";

	public static final String PARAM_PP_ORDER_ID = "PP_Order_ID";
	public static final String PARAM_PRODUCT_BASE_FOLDER_NAME = "Product_BaseFolderName";
	public static final String PARAM_TCP_PORT_NUMBER = "TCP_PortNumber";
	public static final String PARAM_TCP_HOST = "TCP_Host";
	public static final String PARAM_PLU_FILE_CONFIG = "PluFileConfig";
	public static final String PARAM_PLU_FILE_EXPORT_AUDIT_ENABLED = "PluFileExportAuditEnabled";

	public static final String HEADER_PINSTANCE_ID = "x-adpinstanceid";
	public static final String HEADER_EXTERNALSYSTEM_CONFIG_ID = "x-externalsystemconfigid";
	public static final String PARAM_JSON_AVAILABLE_FOR_SALES = "JsonAvailableForSales";

	public static final int DEFAULT_SW6_ORDER_PAGE_SIZE = 100;

	public static final String API_REQUEST_AUDIT_TABLE_NAME = "API_Request_Audit";

	public static final String PARAM_JSON_AVAILABLE_STOCK = "JsonAvailableStock";

	public static final String PARAM_FEEDBACK_RESOURCE_URL = "FeedbackResourceURL";
	public static final String PARAM_FEEDBACK_RESOURCE_AUTH_TOKEN = "FeedbackResourceAuthToken";

	public static final String PARAM_LOCAL_FILE_ROOT_LOCATION = "LocalFileRootLocation";
	public static final String PARAM_LOCAL_FILE_PROCESSED_DIRECTORY = "LocalFileProcessedDirectory";
	public static final String PARAM_LOCAL_FILE_ERRORED_DIRECTORY = "LocalFileErroredDirectory";
	public static final String PARAM_LOCAL_FILE_POLLING_FREQUENCY_MS = "LocalFilePollingFrequencyInMs";

	public static final String PARAM_LOCAL_FILE_PRODUCT_FILE_NAME_PATTERN = "LocalFileProductFileNamePattern";

	public static final String PARAM_LOCAL_FILE_BPARTNER_FILE_NAME_PATTERN = "LocalFilePartnerFileNamePattern";

	public static final String PARAM_LOCAL_FILE_WAREHOUSE_FILE_NAME_PATTERN = "LocalFileWarehouseFileNamePattern";
	
	public static final String PARAM_LOCAL_FILE_PURCHASE_ORDER_FILE_NAME_PATTERN = "LocalFilePurchaseOrderFileNamePattern";

	public static final String PARAM_TAX_CATEGORY_MAPPINGS = "TaxCategoryMappings";

	public static final String PARAM_TARGET_DIRECTORY = "TargetDirectory";
	public static final String PARAM_PRINTING_QUEUE_ID = "PrintingQueueId";
}
