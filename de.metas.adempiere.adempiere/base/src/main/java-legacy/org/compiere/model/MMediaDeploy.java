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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * 	Media Deployment Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MMediaDeploy.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MMediaDeploy extends X_CM_MediaDeploy
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -339938737506660238L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CM_MediaDeploy_ID id
	 *	@param trxName transaction
	 */
	public MMediaDeploy (Properties ctx, int CM_MediaDeploy_ID, String trxName)
	{
		super (ctx, CM_MediaDeploy_ID, trxName);
	}	//	MMediaDeploy

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName tansaction
	 */
	public MMediaDeploy (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MMediaDeploy
	
	/**
	 * 	Deployment Parent Constructor
	 *	@param server server
	 *	@param media media
	 */
	public MMediaDeploy (MMediaServer server, MMedia media)
	{
		this (server.getCtx(), 0, server.get_TrxName());
		setCM_Media_Server_ID(server.getCM_Media_Server_ID());
		setCM_Media_ID(media.getCM_Media_ID());
		setClientOrg(server);
		//
		setIsDeployed(true);
		setLastSynchronized(new Timestamp(System.currentTimeMillis()));
	}	//	MMediaDeploy
	
}	//	MMediaDeploy
