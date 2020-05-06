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
 *	Serial Number Control Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MSerNoCtl.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MSerNoCtl extends X_M_SerNoCtl
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6746210722573475011L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_SerNoCtl_ID id
	 *	@param trxName transaction
	 */
	public MSerNoCtl(Properties ctx, int M_SerNoCtl_ID, String trxName)
	{
		super(ctx, M_SerNoCtl_ID, trxName);
		if (M_SerNoCtl_ID == 0)
		{
		//	setM_SerNoCtl_ID (0);
			setStartNo (1);
			setCurrentNext (1);
			setIncrementNo (1);
		//	setName (null);
		}
	}	//	MSerNoCtl

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MSerNoCtl(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MSerNoCtl

	/**
	 * 	Create new Lot.
	 * 	Increments Current Next and Commits
	 *	@return saved Lot
	 */
	public String createSerNo ()
	{
		StringBuilder name = new StringBuilder();
		if (getPrefix() != null)
			name.append(getPrefix());
		int no = getCurrentNext();
		name.append(no);
		if (getSuffix() != null)
			name.append(getSuffix());
		//
		no += getIncrementNo();
		setCurrentNext(no);
		save();
		return name.toString();
	}	//	createSerNo

}	//	MSerNoCtl
