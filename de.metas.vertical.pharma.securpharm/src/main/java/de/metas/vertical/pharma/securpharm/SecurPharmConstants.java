/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm;

public class SecurPharmConstants
{

	public static final String AUTH_PARAM_CLIENT_ID_VALUE = "securPharm";
	public static final String AUTH_PARAM_GRANT_TYPE = "grant_type";
	public static final String AUTH_PARAM_GRANT_TYPE_VALUE = "password";
	public static final String AUTH_PARAM_CLIENT_ID = "client_id";
	public static final String AUTH_RELATIVE_PATH = "/auth/realms/testentity/protocol/openid-connect/token";
	public static final String API_RELATIVE_PATH_UIS = "/uis/";
	public static final String QUERY_PARAM_CTX = "ctx";
	public static final String QUERY_PARAM_SID = "sid";
	public static final String QUERY_PARAM_TRX = "trx";
	public static final String QUERY_PARAM_LOT = "lot";
	public static final String QUERY_PARAM_EXP = "exp";
	public static final String QUERY_PARAM_PCS = "pcs";
	public static final String QUERY_PARAM_ACT = "act";
	public static final String API_RELATIVE_PATH_PRODUCTS = "/products";
	public static final String API_RELATIVE_PATH_PACKS = "packs";
	public static final String DELIMITER = ",";
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String AUTHORIZATION_TYPE_BEARER = "Bearer ";
	public static final String AUTHORIZATION_FAILED_MESSAGE = "Authorization failed";
	public static final String ERROR = "error";
	public static final String ERROR_DESCRIPTION = "errorDescription";
	public static final String HTTP_STATUS = "HTTPStatus";
	public static final String SECURPHARM_SCAN_RESULT_ERROR_NOTIFICATION_MESSAGE_KEY = "SecurpharmScanResultErrorNotificationMessage";
	public static final String SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE_KEY = "SecurpharmActionResultErrorNotificationMessage";
	public static final String SCAN_PROCESS_DATAMATRIX_PARAM = "dataMatrix";

}
