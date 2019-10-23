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

import java.sql.Timestamp;
import java.util.Properties;

/**
 *	Replication Model
 *
 *  @author Jorg Janke
 *  @version $Id: MReplication.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MReplication extends X_AD_Replication
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7363193371925559167L;

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param AD_Replication_ID id
	 */
	public MReplication (Properties ctx, int AD_Replication_ID, String trxName)
	{
		super (ctx, AD_Replication_ID, trxName);
	}	//	MReplication

	/**
	 *	Public Access
	 * 	@param DateLastRun date last run
	 */
	public void setDateLastRun (Timestamp DateLastRun)
	{
		super.setDateLastRun (DateLastRun);
	}	//	setDateLastRun

}	//	MReplication
