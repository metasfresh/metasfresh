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

	String MOCK_EUR_CODE = "EUR";
	String MOCK_CURRENCY_ID = "currencyId";

	int MOCK_NORMAL_VAT_PRODUCT_ID = 1001;
	int MOCK_REDUCED_VAT_PRODUCT_ID = 1002;

	String MOCK_NORMAL_VAT_RATES = "7.7,19,0";
	String MOCK_REDUCED_VAT_RATES = "5.5,12";

	String MOCK_BPARTNER_UPSERT = "mock:bPartnerUpsert";
	String MOCK_OL_CAND_CREATE = "mock:olCandCreate";
	String MOCK_OL_CAND_CLEAR = "mock:olCandClear";
	String MOCK_CREATE_PAYMENT = "mock:createPayment";
	String MOCK_UPSERT_RUNTIME_PARAMETERS = "mock:upsertRuntimeParams";
	String MOCK_STORE_RAW_DATA = "mock:storeRawData";
}
