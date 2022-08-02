/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6;

public interface ShopwareTestConstants
{
	String MOCK_ORG_CODE = "orgCode";
	String MOCK_TRACE_ID = "traceId";

	String MOCK_EUR_CODE = "EUR";
	String MOCK_UNIT_CODE = "kg";
	String MOCK_CURRENCY_ID = "currencyId";
	String MOCK_UNIT_ID = "unitId";

	String MOCK_SALUTATION_ID = "salutationId";
	String MOCK_BILLING_SALUTATION_ID = "billingSalutationId";
	String MOCK_SALUTATION_DISPLAY_NAME = "salutationDisplayName";
	String MOCK_BILLING_SALUTATION_DISPLAY_NAME = "billingSalutationDisplayName";

	int MOCK_NORMAL_VAT_PRODUCT_ID = 1001;
	int MOCK_REDUCED_VAT_PRODUCT_ID = 1002;
	String MOCK_ORDER_ID = "1111";
	String MOCK_ORDER_NO = "2222";

	String MOCK_NORMAL_VAT_RATES = "7.7,19,0";
	String MOCK_REDUCED_VAT_RATES = "5.5,12";
	String MOCK_JSON_EMAIL_PATH = "/customFields/deliveryNotificationEmailAddress";

	String MOCK_JSON_METASFRESH_ID_PATH = "/customFields/externalReference";
	String MOCK_JSON_SHOPWARE_ID_PATH = "/customFields/userId";
	String MOCK_TARGET_PRICE_LIST_ID = "121";
	String MOCK_IS_TAX_INCLUDED = "true";
	String MOCK_BILLING_ADDRESS_HTTP_URL = "https://www.matter.com/api/order-address/billingAddressId";
	String MOCK_JSON_PATH_SALES_REP_ID = "/orderCustomer/customFields/customSalesRepId";

	String MOCK_GET_CUSTOMERS_HTTP_URL = "https://www.matter.com/api/search/customer";
	String MOCK_CUSTOMER_ADDRESS_HTTP_URL = "https://www.matter.com/api/customer-address/customerAddressId";
	String MOCK_CUSTOMER_GROUP_HTTP_URL = "https://www.matter.com/api/customer-group/customerGroupId";

	String MOCK_BPARTNER_UPSERT = "mock:bPartnerUpsert";
	String MOCK_OL_CAND_CREATE = "mock:olCandCreate";
	String MOCK_OL_CAND_PROCESS = "mock:olCandProcess";
	String MOCK_CREATE_PAYMENT = "mock:createPayment";
	String MOCK_UPSERT_RUNTIME_PARAMETERS = "mock:upsertRuntimeParams";
}
