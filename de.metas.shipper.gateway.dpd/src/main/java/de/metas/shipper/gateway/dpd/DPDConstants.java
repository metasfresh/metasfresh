/*
 * #%L
 * de.metas.shipper.gateway.dpd
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dpd;

public class DPDConstants
{
	// TODO
	public static final String SHIPPER_GATEWAY_ID = "dpd"; // X_M_Shipper.SHIPPERGATEWAY_DPD;

	public static final String DEFAULT_MESSAGE_LANGUAGE = "en_EN";

	public static class DpdPrinterOptions
	{
		public static final String PAPER_FORMAT = "A4";
		public static final String PRINTER_LANGUAGE = "PDF";
	}

	public static class DpdNotificationChannel
	{
		public static int EMAIL = 1;
		public static int SMS = 3;
	}
}
