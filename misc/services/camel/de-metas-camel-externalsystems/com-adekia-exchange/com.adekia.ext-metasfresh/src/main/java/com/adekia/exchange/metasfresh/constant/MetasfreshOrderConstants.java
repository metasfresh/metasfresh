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

@Deprecated // todo transform this class in metasfreshContextHelper @see AmazonCtxHelper
public class MetasfreshOrderConstants {
    public final static String MF_DELIVERY_RULE = "F";
    public final static String MF_ORDER_DOC_BASE_TYPE = "ARI";
    public final static String MF_PAY_DOC_BASE_TYPE = "ARR";
    public final static String MF_PAY_DOC_SUB_TYPE = "Zahlungsausgang";
    public final static String MF_ORG_CODE = "001";
    public final static String MF_PAYMENT_RULE = "K";    // CreditCard
    public final static String MF_PAYMENT_TERM = "sofort";
    public final static String MF_PRICING_SYSTEM_CODE = "amazon";
    public final static String MF_SALES_PARTNER_CODE = "amazon";
    public final static String MF_UOM_CODE = "PCE";
    public final static String MF_DATA_SOURCE_INT_AMAZON = "int-Amazon";

}
