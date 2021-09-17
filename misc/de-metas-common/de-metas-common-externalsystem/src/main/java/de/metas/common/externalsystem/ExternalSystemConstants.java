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
	public static final String PARAM_BPARTNER_GROUP = "BPartnerGroup";
	public static final String PARAM_UPDATED_AFTER = "UpdatedAfter";
	public static final String PARAM_UPDATE_AFTER_DOCUMENT = "UpdatedAfterDocument";
	public static final String PARAM_UPDATE_AFTER_ATTACHMENT = "UpdatedAfterAttachment";
	public static final String PARAM_CLIENT_ID = "ClientId";
	public static final String PARAM_CLIENT_SECRET = "ClientSecret";
	public static final String PARAM_CHILD_CONFIG_VALUE = "ChildConfigValue";
	public static final String PARAM_JSON_PATH_CONSTANT_BPARTNER_ID = "JSONPathConstantBPartnerID";
	public static final String PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID = "JSONPathConstantBPartnerLocationID";
	public static final String PARAM_JSON_PATH_SALES_REP_ID = "JSONPathConstantSalesRepID";
	public static final String PARAM_CONFIG_MAPPINGS = "ConfigMappings";

	public static final String PARAM_FREIGHT_COST_NORMAL_VAT_RATES = "FreightCost_NormalVAT_Rates";
	public static final String PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID = "M_FreightCost_NormalVAT_Product_ID";

	public static final String PARAM_FREIGHT_COST_REDUCED_VAT_RATES = "FreightCost_Reduced_VAT_Rates";
	public static final String PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID = "M_FreightCost_ReducedVAT_Product_ID";

	public static final String PARAM_UPDATED_AFTER_OVERRIDE = "UpdatedAfterOverride";
	public static final String PARAM_ROOT_BPARTNER_ID_FOR_USERS = "RootBPartnerID";

	public static final String PARAM_ALBERTA_ID = "Alberta_Id";
	public static final String PARAM_ALBERTA_ROLE = "Alberta_Role";

	public static final String PARAM_CAMEL_HTTP_RESOURCE_AUTH_KEY = "CamelHttpResourceAuthKey";

	public static final String PARAM_ORDER_NO = "OrderNo"; // if set, then this shall override PARAM_UPDATED_AFTER*
	public static final String QUEUE_NAME_MF_TO_ES = "MF_TO_ExternalSystem";

	public static final String PARAM_RABBITMQ_HTTP_URL = "RemoteURL";
	public static final String PARAM_RABBITMQ_HTTP_ROUTING_KEY = "RoutingKey";
	public static final String PARAM_BPARTNER_ID = "BPartnerId";
	public static final String PARAM_RABBIT_MQ_AUTH_TOKEN = "RabbitMQAuthToken";

	public static final String HEADER_PINSTANCE_ID = "x-adpinstanceid";
	public static final String HEADER_EXTERNALSYSTEM_CONFIG_ID = "x-externalsystemconfigid";
}
