package org.adempiere.inout.shipment;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_InOut;

/**
 * Service that creates shipments ({@link I_M_InOut}s)
 * 
 * @author ts
 * 
 */
public interface IShipmentBL extends ISingletonService {

	/**
	 * Create shipments and adds them to the given {@link IShipmentCandidates}
	 * instance. The shipments (and lines) aren't saved to database.
	 * 
	 * @param shipmentParams
	 * @param candidates
	 * @param trxName
	 */
	void addShipments(Properties ctx, ShipmentParams shipmentParams, IShipmentCandidates candidates, String trxName);

	/**
	 * Create and process shipments
	 * @param ctx
	 * @param params
	 * @param docAction doc action to be used when processing
	 * @param trxName
	 * @return generated shipments
	 */
	Iterator<de.metas.inout.model.I_M_InOut> generateShipments(Properties ctx, ShipmentParams params, String docAction, String trxName);
}
