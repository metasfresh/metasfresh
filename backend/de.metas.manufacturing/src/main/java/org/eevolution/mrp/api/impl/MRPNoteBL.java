package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.Arrays;
import java.util.List;

import org.eevolution.mrp.api.IMRPNoteBL;

public class MRPNoteBL implements IMRPNoteBL
{
	// FIXME: HARDCODED
	private static final List<String> MRPCodes_ERROR = Arrays.asList(
			"DRP-001" // Do not exist default Locator for this Warehouse
			, "DRP-010" // Do not exist Transit Warehouse to this Organization
			, "DRP-020" // Org targer do not linked to BPartner
			, "DRP-030" // Do not exist Shipper for Create Distribution Order
			// , "DRP-040" // Shipment Due
			// , "DRP-050" // Shipment Past Due
			, "DRP-060" // No source of supply
			, "MRP-001" // Beginning Available Less Than Zero
			// , "MRP-020" // Create
			// , "MRP-030" // De-Expedite
			// , "MRP-040" // Expedite
			// , "MRP-050" // Cancel
			// , "MRP-060" // Release Due For
			// , "MRP-070" // Release Past Due For
			// , "MRP-080" // Quantity Less than Minimum
			// , "MRP-090" // Quantity Less than Maximum
			// , "MRP-100" // Time Fence Conflict
			// , "MRP-110" // Past Due
			, "MRP-120" // No Data Planning
			, "MRP-130" // No Current Vendor Selected
			, "MRP-140" // Beginning Quantity Less Than Zero
			// , "MRP-150" // Past Due Demand
			, "MRP-160" // Cannot Create Document
			, "MRP-999" // Unknown MRP Error
	);

	@Override
	public boolean isError(final String mrpCode)
	{
		return MRPCodes_ERROR.contains(mrpCode);
	}

	@Override
	public List<String> getMRPErrorCodes()
	{
		return MRPCodes_ERROR;
	}
}
