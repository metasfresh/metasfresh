/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl;

import org.compiere.model.X_M_Shipper;

public class DhlConstants
{
	public static final String SHIPPER_GATEWAY_ID = X_M_Shipper.SHIPPERGATEWAY_DHL;

	//they don't use X12DE355
	public static final String KILOGRAM_UOM = "kg";

	public static final String DHL_API_KEY_HTTP_HEADER = "Dhl-Api-Key";
	public static final String STANDARD_GRUPPENPROFIL = "STANDARD_GRUPPENPROFIL";
	public static final String REMOVED = "<<removed>>";
}
