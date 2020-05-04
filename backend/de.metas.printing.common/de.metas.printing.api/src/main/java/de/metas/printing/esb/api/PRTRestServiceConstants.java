package de.metas.printing.esb.api;

/*
 * #%L
 * de.metas.printing.esb.api
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/**
 *
 * NOTE: when adding/changing OPERATIONs or PATHs, please also check "de.metas.printing.esb.camel.inout.cxf.jaxrs.PRTRestService".
 */
public final class PRTRestServiceConstants
{
	public static final String PARAM_SessionId = "sessionId";
	public static final String PARAM_TransactionId = "transactionId";

	public static final String OPERATION_Login = "login";
	public static final String PATH_Login = "/" + OPERATION_Login + "/{" + PARAM_SessionId + "}";

	public static final String OPERATION_AddPrinterHW = "addPrinterHW";
	public static final String PATH_AddPrinterHW = "/" + OPERATION_AddPrinterHW + "/session/{" + PARAM_SessionId + "}";

	public static final String OPERATION_GetNextPrintPackage = "getNextPrintPackage";
	public static final String PATH_GetNextPrintPackage = "/" + OPERATION_GetNextPrintPackage + "/{" + PARAM_SessionId + "}/{" + PARAM_TransactionId + "}";

	public static final String OPERATION_GetPrintPackageData = "getPrintPackageData";
	public static final String PATH_GetPrintPackageData = "/" + OPERATION_GetPrintPackageData + "/{" + PARAM_SessionId + "}/{" + PARAM_TransactionId + "}";

	public static final String OPERATION_SendPrintPackageResponse = "sendPrintPackageResponse";
	public static final String PATH_SendPrintPackageResponse = "/" + OPERATION_SendPrintPackageResponse + "/{" + PARAM_SessionId + "}/{" + PARAM_TransactionId + "}";

	private PRTRestServiceConstants()
	{
		super();
		// disable constructor
	}
}
