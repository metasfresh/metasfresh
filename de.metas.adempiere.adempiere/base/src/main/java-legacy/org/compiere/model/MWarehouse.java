/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Warehouse Model
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com
 * @see FR [ 1966337 ] New Method to get the Transit Warehouse based in ID Org http://sourceforge.net/tracker/index.php?func=detail&aid=1966337&group_id=176962&atid=879335
 * @author Teo Sarca, http://www.arhipac.ro
 *         <li>BF [ 1874419 ] JDBC Statement not close in a finally block
 * @version $Id: MWarehouse.java,v 1.3 2006/07/30 00:58:05 jjanke Exp $
 */
public class MWarehouse extends X_M_Warehouse
{
	/**
	 *
	 */
	private static final long serialVersionUID = -848214135445693460L;

	/**
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param M_Warehouse_ID id
	 * @param trxName transaction
	 */
	public MWarehouse(Properties ctx, int M_Warehouse_ID, String trxName)
	{
		super(ctx, M_Warehouse_ID, trxName);
		if (is_new())
		{
			// setValue (null);
			// setName (null);
			// setC_Location_ID (0);
			setSeparator("*");	// *
		}
	}	// MWarehouse

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MWarehouse(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MWarehouse

	/**
	 * Organization Constructor
	 * 
	 * @param org parent
	 */
	public MWarehouse(final MOrg org)
	{
		this(org.getCtx(), 0, org.get_TrxName());
		setClientOrg(org);
		setValue(org.getValue());
		setName(org.getName());
		// metas: 03084: we are no longer setting the location from AD_OrgInfo
		// check org.compiere.process.BPartnerOrgLink.configureWarehouse(I_M_Warehouse, I_C_BPartner)
		// if (org.getInfo() != null)
		// setC_Location_ID (org.getInfo().getC_Location_ID());
	}	// MWarehouse

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (newRecord && success)
		{
			insert_Accounting("M_Warehouse_Acct", "C_AcctSchema_Default", null);
		}

		return success;
	}

	@Override
	protected boolean beforeDelete()
	{
		return delete_Accounting("M_Warehouse_Acct");
	}

}	// MWarehouse
