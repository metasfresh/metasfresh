/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 *	GL Distribution Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MDistribution.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MDistribution extends X_GL_Distribution
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1020880569780353403L;

	public MDistribution (Properties ctx, int GL_Distribution_ID, String trxName)
	{
		super (ctx, GL_Distribution_ID, trxName);
		if (GL_Distribution_ID == 0)
		{
		//	setC_AcctSchema_ID (0);
		//	setName (null);
			//
			setAnyAcct (true);	// Y
			setAnyActivity (true);	// Y
			setAnyBPartner (true);	// Y
			setAnyCampaign (true);	// Y
			setAnyLocFrom (true);	// Y
			setAnyLocTo (true);	// Y
			setAnyOrg (true);	// Y
			setAnyOrgTrx (true);	// Y
			setAnyProduct (true);	// Y
			setAnyProject (true);	// Y
			setAnySalesRegion (true);	// Y
			setAnyUser1 (true);	// Y
			setAnyUser2 (true);	// Y
			//
			setIsValid (false);	// N
			setPercentTotal (BigDecimal.ZERO);
		}
	}	//	MDistribution

	public MDistribution (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MDistribution
}	//	MDistribution
