/*
 * #%L
 * ext-metasfresh
 * %%
 * Copyright (C) 2022 Adekia
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

package com.adekia.exchange.metasfresh.constant;

import de.metas.common.rest_api.v2.JsonInvoiceRule;

@Deprecated // todo transform this class in metasfreshContextHelper @see AmazonCtxHelper
public class MetasfreshOrderBPartnerConstant {
    public final static String MF_BP_CODE_PREFIX = "AZ-BP-";
    public final static String MF_BP_CONTACT_CODE_PREFIX = "AZ-US-";
    public final static String MF_BP_GROUP = "Amazon";
    public final static JsonInvoiceRule MF_BP_INVOICE_RULE = JsonInvoiceRule.AfterDelivery;
    public final static String MF_BP_LANGUAGE = "de_DE";
}
