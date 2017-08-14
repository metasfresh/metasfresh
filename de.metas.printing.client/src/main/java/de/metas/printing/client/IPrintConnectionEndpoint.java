package de.metas.printing.client;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.io.InputStream;

import de.metas.printing.client.endpoint.LoginFailedPrintConnectionEndpointException;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrinterHWList;

public interface IPrintConnectionEndpoint
{
	/**
	 * Login.
	 *
	 * @return valid login response (with {@link LoginResponse#getSessionId()} filled).
	 * @throws LoginFailedPrintConnectionEndpointException in case something went wrong.
	 */
	LoginResponse login(final LoginRequest loginRequest) throws LoginFailedPrintConnectionEndpointException;

	/**
	 * Send printer hardware configuration to ADempiere.
	 *
	 * @param printerHWList
	 */
	void addPrinterHW(PrinterHWList printerHWList);

	/**
	 * Retrieve the next print package.
	 *
	 * @return next {@link PrintPackage} or null if there is no next print package
	 */
	PrintPackage getNextPrintPackage();

	/**
	 * Send printPackage data request to camel->AD.
	 *
	 * @param printPackage
	 * @return PDF Stream, already decoded
	 */
	InputStream getPrintPackageData(PrintPackage printPackage);

	/**
	 * Send printPackage response to camel->AD after the print data is sent to printer.
	 *
	 * @param response
	 */
	void sendPrintPackageResponse(PrintPackage printPackage, PrintJobInstructionsConfirm response);
}
