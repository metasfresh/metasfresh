/**
 *
 */
package de.metas.inout.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.compiere.model.I_M_Warehouse;

import de.metas.document.model.IDocumentDeliveryLocation;
import de.metas.document.model.IDocumentLocation;
import de.metas.shipping.model.I_M_ShipperTransportation;

/**
 * @author tsa
 *
 */
public interface I_M_InOut extends org.compiere.model.I_M_InOut, IDocumentLocation, IDocumentDeliveryLocation
{
	// @formatter:off
	String COLUMNNAME_BPartnerAddress = "BPartnerAddress";
	@Override
	String getBPartnerAddress();
	@Override
	void setBPartnerAddress(String BPartnerAddress);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";
	I_M_Warehouse getM_Warehouse_Dest();
	void setM_Warehouse_Dest_ID(int M_Warehouse_Dest_ID);
	int getM_Warehouse_Dest_ID();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_M_ShipperTransportation = "M_ShipperTransportation_ID";
	I_M_ShipperTransportation getM_ShipperTransportation();
	void setM_ShipperTransportation_ID(int M_ShipperTransportation_ID);
	int getM_ShipperTransportation_ID();
	// @formatter:on
	
	// @formatter:off
	String COLUMNNAME_IsInOutApprovedForInvoicing = "IsInOutApprovedForInvoicing";
	void setIsInOutApprovedForInvoicing(boolean IsInOutApprovedForInvoicing);
	boolean isInOutApprovedForInvoicing();
	// @formatter:on
	
	
	// @formatter:off	
	// 08524
	// this one is only used in MRAs
	String COLUMNNAME_IsManual = "IsManual";
	void setIsManual(boolean IsManual);
	boolean isManual();
	// @formatter:on
}
