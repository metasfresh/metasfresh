/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.adempiere.exceptions;


/**
 * Throw when there is no Warehouse for Organization.
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 * 			<li>FR [ 2457781 ] Introduce WarehouseInvalidForOrgException
 * 			<li>https://sourceforge.net/tracker/index.php?func=detail&aid=2877521&group_id=176962&atid=879335
 */
public class WarehouseInvalidForOrgException extends AdempiereException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8637554073291880392L;

	/**
	 * @param productName M_Product Name
	 */
	public WarehouseInvalidForOrgException(String wname ,String oname)
	{
		super(wname+ " @M_Warehouse_ID@  @NotValid@  @AD_Org_ID@  "+ oname);
	}
}
