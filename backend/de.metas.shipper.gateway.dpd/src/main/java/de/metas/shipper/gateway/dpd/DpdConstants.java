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

import org.compiere.model.X_M_Shipper;

import de.metas.uom.X12DE355;

public class DpdConstants
{
	public static final String SHIPPER_GATEWAY_ID = X_M_Shipper.SHIPPERGATEWAY_DPD;

	public static final String DEFAULT_MESSAGE_LANGUAGE = "en_EN";

	public static final String DEFAULT_PRINTER_LANGUAGE = "PDF";

	public static final String TRACKING_URL = "https://tracking.dpd.de/status/en_US/parcel/";

	public static final X12DE355 DEFAULT_PACKAGE_DIMENSIONS_UOM = X12DE355.CENTIMETRE;
}
