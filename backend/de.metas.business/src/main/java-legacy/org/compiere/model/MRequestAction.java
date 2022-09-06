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
import java.util.Properties;

/**
 * Request History Model
 *
 * @author Jorg Janke
 * @version $Id: MRequestAction.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MRequestAction extends X_R_RequestAction
{
	/**
	 *
	 */
	private static final long serialVersionUID = 2902231219773596011L;

	public MRequestAction(Properties ctx, int R_RequestAction_ID, String trxName)
	{
		super(ctx, R_RequestAction_ID, trxName);
	}    //	MRequestAction

	@SuppressWarnings("unused")
	public MRequestAction(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}    //	MRequestAction

	/**
	 * Parent Action Constructor
	 *
	 * @param request parent
	 */
	public MRequestAction(final MRequest request)
	{
		this(request.getCtx(), 0, request.get_TrxName());
		setClientOrg(request);
		setR_Request_ID(request.getR_Request_ID());
	}    //	MRequestAction

	void addNullColumn(String columnName)
	{
		String nc = getNullColumns();
		if (nc == null)
		{
			setNullColumns(columnName);
		}
		else
		{
			setNullColumns(nc + ";" + columnName);
		}
	}    //	addNullColumn

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		return true;
	}    //	beforeSave
}    //	MRequestAction
