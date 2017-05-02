package org.eevolution.mrp.jmx;

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


import org.eevolution.model.I_PP_MRP;

import de.metas.material.planning.IMRPSegment;

public interface JMXMRPStatusMBean
{
	/**
	 * Gets info about all {@link IMRPSegment}s which are considered by MRP.
	 * 
	 * @return
	 */
	String[] getAllMRPSegments();

	void setMRPLogLevel(String logLevel);

	String getMRPLogLevel();

	/**
	 * Helper method to check forward DD Order demands for a given {@link I_PP_MRP} demand.
	 * 
	 * @param ppMRPDemandId
	 * @task http://dewiki908/mediawiki/index.php/07961_Handelsware_DD_Order_automatisieren_%28101259925191%29
	 */
	void checkForwardDDOrderDemandsByDDOrderDocumentNo(String ddOrderDocumentNo);
	
	String checkBackwardDDOrdersForPPOrderDocumentNo(String ppOrderDocumentNo);
}
