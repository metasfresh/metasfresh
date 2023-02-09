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
 *	GL Distribution Line Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MDistributionLine.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MDistributionLine extends X_GL_DistributionLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2883983359935482254L;

	public MDistributionLine (Properties ctx, int GL_DistributionLine_ID, String trxName)
	{
		super (ctx, GL_DistributionLine_ID, trxName);
		if (GL_DistributionLine_ID == 0)
		{
		//	setGL_Distribution_ID (0);		//	Parent
		//	setLine (0);
			//
			setOverwriteAcct (false);
			setOverwriteActivity (false);
			setOverwriteOrder(false);
			setOverwriteSectionCode(false);
			setOverwriteBPartner (false);
			setOverwriteCampaign (false);
			setOverwriteLocFrom (false);
			setOverwriteLocTo (false);
			setOverwriteOrg (false);
			setOverwriteOrgTrx (false);
			setOverwriteProduct (false);
			setOverwriteProject (false);
			setOverwriteSalesRegion (false);
			setOverwriteUser1 (false);
			setOverwriteUser2 (false);
			//
			setPercent (BigDecimal.ZERO);
		}	
	}	//	MDistributionLine

	public MDistributionLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MDistributionLine
}	//	MDistributionLine
