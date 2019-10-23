/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.exceptions;

import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

/**
 * Throwed when a document warehouse does not match with document or document line locator.
 * @author Teo Sarca, www.arhipac.ro
 */
public class WarehouseLocatorConflictException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4812283712626432829L;

	/**
	 * @param wh warehouse
	 * @param locator locator 
	 * @param lineNo Document Line#
	 */
	public WarehouseLocatorConflictException(I_M_Warehouse wh, I_M_Locator locator, int lineNo)
	{
		super ("@WarehouseLocatorConflict@"
				+" @M_Warehouse_ID@: "+(wh != null ? wh.getName() : "?")
				+" @M_Locator_ID@: "+(locator != null ? locator.getValue() : "?")
				+" @Line@: "+lineNo
		);
	}
}
