/**
 * 
 */
package de.metas.adempiere.model;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.document.model.IDocumentLocation;
import org.compiere.model.I_C_BPartner_Location;

public interface I_OrderOrInOut extends IDocumentLocation
{
	@Override
	int getC_BPartner_ID();

	@Override
	org.compiere.model.I_C_BPartner getC_BPartner();

	@Override
	int getC_BPartner_Location_ID();

	@Override
	I_C_BPartner_Location getC_BPartner_Location();

	String getDeliveryViaRule();

	int getM_Shipper_ID();

	String getDocumentNo();

	int getAD_Org_ID();
	
	
	
	String getFreightCostRule();

}