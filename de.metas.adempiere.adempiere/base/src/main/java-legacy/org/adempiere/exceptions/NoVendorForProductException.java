/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
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

/**
 * Throwed when there is no Vendor Info for given Product.
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2457781 ] Introduce NoVendorForProductException
 */
public class NoVendorForProductException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3412903630540562323L;

	/**
	 * @param productName M_Product Name
	 */
	public NoVendorForProductException(String productName)
	{
		super("@NoVendorForProduct@ "+productName);
	}
}
