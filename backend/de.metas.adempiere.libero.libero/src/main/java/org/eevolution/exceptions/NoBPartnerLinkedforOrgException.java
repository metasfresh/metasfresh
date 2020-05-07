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
package org.eevolution.exceptions;

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


import org.compiere.model.MOrg;

import de.metas.material.planning.pporder.LiberoException;

/**
 * Throw when do not exist Business Partner link for organization:
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 * <li>RF2878448 Implementation NoBPartnerLikedforOrgException  
 * <li>https://sourceforge.net/tracker/?func=detail&aid=2878448&group_id=176962&atid=879335
 */
public class NoBPartnerLinkedforOrgException extends LiberoException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8354155558569979580L;
	
	public NoBPartnerLinkedforOrgException(MOrg org)
	{
		super ("@NotExistsBPLinkedforOrgError@ " + org.getName());
	}
}
